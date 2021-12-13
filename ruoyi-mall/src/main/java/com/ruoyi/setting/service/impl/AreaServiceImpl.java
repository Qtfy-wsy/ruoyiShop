package com.ruoyi.setting.service.impl;


import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.setting.domain.LsCity;
import com.ruoyi.setting.domain.LsDistrict;
import com.ruoyi.setting.domain.LsProvince;
import com.ruoyi.setting.mapper.AreaMapper;
import com.ruoyi.setting.service.AreaService;
import com.ruoyi.setting.vo.AreaItem;
import com.ruoyi.setting.vo.Locality;
import com.ruoyi.util.RedisCahceKey;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 伊甸园商城 on 17/5/15
 * 地区服务实现接口
 */
@Service
public class AreaServiceImpl implements AreaService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    /**
     * 地区数据库接口
     */
    @Autowired
    private AreaMapper areaMapper;

    @Cacheable(value = RedisCahceKey.AREA, key = "'AREA'+#areaItems.hashCode()")
    @Override
    public List<LsProvince> queryAllProvinces(AreaItem... areaItems) {
        logger.debug("queryAllProvinces.....areaItems:{}", areaItems);

        List<LsProvince> provinces = areaMapper.queryAllProvinces();

        // 如果没有省则直接返回
        if (CollectionUtils.isEmpty(provinces)) {
            return provinces;
        }

        // 设置省下面的市
        if (ArrayUtils.contains(areaItems, AreaItem.CITY)) {
            provinces.parallelStream().forEach(province -> province.setChild(this.queryCityByProvinceId(province.getId())));
        }

        // 设置市下面的区
        if (ArrayUtils.contains(areaItems, AreaItem.DISTRICT)) {
            provinces.parallelStream().forEach(province -> province.getChild().stream().forEach(city -> city.setChild(this.queryDistrictByCityId(city.getId()))));
        }

        // 查询所有省市区
        if (ArrayUtils.contains(areaItems, AreaItem.ALL)) {
            provinces.parallelStream().forEach(province -> province.setChild(this.queryCityByProvinceId(province.getId())));
            provinces.parallelStream().forEach(province -> province.getChild().stream().forEach(city -> city.setChild(this.queryDistrictByCityId(city.getId()))));
        }

        return provinces;
    }


    /**
     * 查询所有地区信息(特殊数据接口)
     *
     * @return 所有地区信息
     */
    @Override
    public List<Locality> queryAllLocality() {
        logger.debug("queryAllLocality....");

        List<LsProvince> provinceList = SpringUtils.getBean(AreaService.class).queryAllProvinces(AreaItem.ALL);

        //初始化返回集合
        List<Locality> localities = new ArrayList<>();

        //构建数据结构
        for (int i = 0, size = provinceList.size(); i < size; i++) {
            //省份
            localities.add(new Locality(provinceList.get(i).getName(), "P-" + provinceList.get(i).getId(), "0", provinceList.get(i).getId()));
            for (int k = 0; k < provinceList.get(i).getChild().size(); k++) {
                //城市
                localities.add(new Locality(provinceList.get(i).getChild().get(k).getName(), "C-" + provinceList.get(i).getChild().get(k).getId(), "P-" + provinceList.get(i).getChild().get(k).getProvinceId(), provinceList.get(i).getChild().get(k).getId()));
                for (int j = 0; j < provinceList.get(i).getChild().get(k).getChild().size(); j++) {
                    //地区
                    localities.add(new Locality(provinceList.get(i).getChild().get(k).getChild().get(j).getName(), "D-" + provinceList.get(i).getChild().get(k).getChild().get(j).getId(), "C-" + provinceList.get(i).getChild().get(k).getId(), provinceList.get(i).getChild().get(k).getChild().get(j).getId()));
                }
            }
        }

        return localities;

    }


    @Override
    public List<LsCity> queryCityByProvinceId(long provinceId) {
        logger.debug("queryCityByProvinceId and provinceId:{}", provinceId);
        return areaMapper.queryCityByProvinceId(provinceId);
    }

    @Override
    public List<LsDistrict> queryDistrictByCityId(long cityId) {
        logger.debug("queryDistrictByCityId and cityId:{}", cityId);
        return areaMapper.queryDistrictByCityId(cityId);
    }

    @CacheEvict(value = RedisCahceKey.AREA, allEntries = true)
    @Override
    public int addProvince(LsProvince province) {
        logger.debug("addProvince and province:{}", province);

        if (Objects.isNull(province)) {
            logger.error("addProvince fail due to province is null....");
            return 0;
        }
        return areaMapper.addProvince(province);
    }

    @CacheEvict(value = RedisCahceKey.AREA, allEntries = true)
    @Override
    public int addCity(LsCity city) {
        logger.debug("addCity and city :{}", city);
        if (Objects.isNull(city)) {
            logger.error("addCity fail due to city is null....");
            return 0;
        }
        return areaMapper.addCity(city);
    }

    @CacheEvict(value = RedisCahceKey.AREA, allEntries = true)
    @Override
    public int addDistrict(LsDistrict district) {

        logger.debug("addDistrict and district:{}", district);

        if (Objects.isNull(district)) {
            logger.error("addDistrict fail due to district is null...");
            return 0;
        }
        return areaMapper.addDistrict(district);
    }

    @CacheEvict(value = RedisCahceKey.AREA, allEntries = true)
    @Override
    public int deleteProvinceById(long provinceId) {
        logger.debug("deleteProvinceById and provinceId:{}", provinceId);

        if (!isProvinceCanDelete(provinceId)) {
            logger.error("deleteProvinceById fail due to province has city...");
            return -1;
        }

        return areaMapper.deleteProvinceById(provinceId);
    }

    @CacheEvict(value = RedisCahceKey.AREA, allEntries = true)
    @Override
    public int deleteCityById(long cityId) {
        logger.debug("deleteCityById and cityId:{}", cityId);

        if (!isCityCanDelete(cityId)) {
            logger.error("deleteCityById fail due to city has district");
            return -1;
        }
        return areaMapper.deleteCityById(cityId);
    }

    @CacheEvict(value = RedisCahceKey.AREA, allEntries = true)
    @Override
    public int deleteDistrictById(long districtId) {
        logger.debug("deleteDistrictById and districtId:{}", districtId);
        return areaMapper.deleteDistrictById(districtId);
    }


    @Override
    public LsProvince queryProvinceById(long id) {
        logger.debug("queryProvinceById and id:{}", id);
        return areaMapper.queryProvinceById(id);
    }

    @Override
    public LsCity queryCityById(long id) {
        logger.debug("queryCityById and id:{}", id);
        return areaMapper.queryCityById(id);
    }

    @Override
    public LsDistrict queryDistrictById(long id) {
        logger.debug("queryDistrictById and id :{}", id);
        return areaMapper.queryDistrictById(id);
    }

    @CacheEvict(value = RedisCahceKey.AREA, allEntries = true)
    @Override
    public int updateProvince(LsProvince province) {
        logger.debug("updateProvince and province:{}", province);
        if (Objects.isNull(province)) {
            logger.error("updateProvince fail ...");
            return 0;
        }
        return areaMapper.updateProvince(province);
    }

    @CacheEvict(value = RedisCahceKey.AREA, allEntries = true)
    @Override
    public int updateCity(LsCity city) {
        logger.debug("updateCity and city:{}", city);

        if (Objects.isNull(city)) {
            logger.error("updateCity fail...");
            return 0;
        }
        return areaMapper.updateCity(city);
    }

    @CacheEvict(value = RedisCahceKey.AREA, allEntries = true)
    @Override
    public int updateDistrict(LsDistrict district) {
        logger.debug("updateDistrict and district:{}", district);

        if (Objects.isNull(district)) {
            logger.error("updateDistrict fail....");
            return 0;
        }
        return areaMapper.updateDistrict(district);
    }

    @Override
    public long queryCityIdByName(String cityName) {
        logger.debug("queryCityIdByName and cityName:{}", cityName);
        Long cityId = areaMapper.queryCityIdByName(cityName);
        return Objects.isNull(cityId) ? -1 : cityId;
    }


    /**
     * 判断市是否可以删除
     *
     * @param cityId 市id
     * @return 可以返回true  不可以返回false  市下面有区则不能删除
     */
    private boolean isCityCanDelete(long cityId) {
        return CollectionUtils.isEmpty(this.queryDistrictByCityId(cityId));
    }

    /**
     * 判断省份是否可以删除
     *
     * @param provinceId 省份id
     * @return 可以删除 返回true  不可以删除 返回false (省份下面有市则不能删除)
     */
    private boolean isProvinceCanDelete(long provinceId) {
        return CollectionUtils.isEmpty(this.queryCityByProvinceId(provinceId));
    }
}

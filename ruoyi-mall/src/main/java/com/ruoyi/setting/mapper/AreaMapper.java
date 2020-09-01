package com.ruoyi.setting.mapper;


import com.ruoyi.setting.domain.LsCity;
import com.ruoyi.setting.domain.LsDistrict;
import com.ruoyi.setting.domain.LsProvince;

import java.util.List;

/**
 * Created by 魔金商城 on 17/5/15.
 * 地区数据库接口
 */
public interface AreaMapper {

    /**
     * 查询所有省份
     *
     * @return 返回所有省份
     */

    List<LsProvince> queryAllProvinces();

    /**
     * 根据省份id查询该省下面的市
     *
     * @param provinceId 省id
     * @return 返回省下面的市
     */

    List<LsCity> queryCityByProvinceId(long provinceId);


    /**
     * 根据市id查询下面的区
     *
     * @param cityId 市id
     * @return 返回该市下面的区
     */

    List<LsDistrict> queryDistrictByCityId(long cityId);

    /**
     * 添加省份
     *
     * @param province 省份信息
     * @return 成功返回1  失败返回0
     */

    int addProvince(LsProvince province);

    /**
     * 添加市
     *
     * @param city 市信息
     * @return 成功返回1  失败返回0
     */

    int addCity(LsCity city);

    /**
     * 添加区
     *
     * @param district 区信息
     * @return 成功返回1  失败返回0
     */

    int addDistrict(LsDistrict district);


    /**
     * 根据省份id删除省份信息
     *
     * @param provinceId 省份id
     * @return 成功返回1  失败返回0
     */

    int deleteProvinceById(long provinceId);

    /**
     * 根据市id删除市信息
     *
     * @param cityId 市id
     * @return 成功返回1  失败返回0
     */

    int deleteCityById(long cityId);

    /**
     * 根据区id删除区信息
     *
     * @param districtId 区id
     * @return 成功返回1  失败返回0
     */

    int deleteDistrictById(long districtId);

    /**
     * 根据省id查询省份信息
     *
     * @param id 省id
     * @return 返回省份信息
     */

    LsProvince queryProvinceById(long id);

    /**
     * 根据市id查询市信息
     *
     * @param id 市id
     * @return 返回市信息
     */

    LsCity queryCityById(long id);

    /**
     * 根据区id查询区信息
     *
     * @param id 区id
     * @return 返回区信息
     */

    LsDistrict queryDistrictById(long id);

    /**
     * 修改省份信息
     *
     * @param province 修改省份
     * @return 成功返回1  失败返回0
     */

    int updateProvince(LsProvince province);

    /**
     * 修改市信息
     *
     * @param city 市信息
     * @return 成功返回1 失败返回0
     */

    int updateCity(LsCity city);

    /**
     * 修改区信息
     *
     * @param district 区信息
     * @return 成功返回1  失败返回0
     */

    int updateDistrict(LsDistrict district);

    /**
     * 根据名称查询市id
     *
     * @param cityName 市名称
     * @return 市id
     */

    Long queryCityIdByName(String cityName);
}

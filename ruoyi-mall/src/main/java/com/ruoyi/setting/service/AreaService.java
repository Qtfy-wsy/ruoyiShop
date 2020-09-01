package com.ruoyi.setting.service;


import com.ruoyi.setting.domain.LsCity;
import com.ruoyi.setting.domain.LsDistrict;
import com.ruoyi.setting.domain.LsProvince;
import com.ruoyi.setting.vo.AreaItem;
import com.ruoyi.setting.vo.Locality;

import java.util.List;

/**
 * Created by 魔金商城 on 17/5/15.
 * 地区服务接口
 */
public interface AreaService {

    /**
     * 查询所有省份
     *
     * @param areaItems 查询参数 不带只查询省
     * @return 返回所有省份
     */
    List<LsProvince> queryAllProvinces(AreaItem... areaItems);


    /**
     * 查询所有地区信息(特殊数据接口)
     *
     * @return 所有地区信息
     */
    List<Locality> queryAllLocality();


    /**
     * 根据省份id查询下面的市
     *
     * @param provinceId 省份id
     * @return 返回该省下面的市
     */
    List<LsCity> queryCityByProvinceId(long provinceId);

    /**
     * 根据市id查询下面的区县
     *
     * @param cityId 市id
     * @return 返回市下面的区县
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
     * @param city 市
     * @return 成返回1  失败返回0
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
     * 根据省份id删除省信息
     *
     * @param provinceId 省份id
     * @return 1 成功 0 失败  -1 下面有市不能删除
     */
    int deleteProvinceById(long provinceId);

    /**
     * 根据市id删除市信息
     *
     * @param cityId 市id
     * @return 返回 1 成功  0 失败  -1 下面有区 不能删除
     */
    int deleteCityById(long cityId);

    /**
     * 根据区id删除区
     *
     * @param districtId 区id
     * @return 返回 1 成功  0 失败
     */
    int deleteDistrictById(long districtId);

    /**
     * 根据省份id查询省份信息
     *
     * @param id 省份id
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
     * 修改省份
     *
     * @param province 省份信息
     * @return 成功返回1  失败返回0
     */
    int updateProvince(LsProvince province);

    /**
     * 修改市
     *
     * @param city 市信息
     * @return 成功返回1  失败返回0
     */
    int updateCity(LsCity city);

    /**
     * 修改区
     *
     * @param district 区信息
     * @return 成功返回1  失败返回0
     */
    int updateDistrict(LsDistrict district);

    /**
     * 根据市名称查找市id
     *
     * @param cityName 市名称
     * @return 市id
     */
    long queryCityIdByName(String cityName);
}

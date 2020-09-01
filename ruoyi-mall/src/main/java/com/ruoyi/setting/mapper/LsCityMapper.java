package com.ruoyi.setting.mapper;

import com.ruoyi.setting.domain.LsCity;

import java.util.List;

/**
 * 区域市Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-29
 */
public interface LsCityMapper {
    /**
     * 查询区域市
     *
     * @param id 区域市ID
     * @return 区域市
     */
    public LsCity selectLsCityById(Long id);

    /**
     * 查询区域市列表
     *
     * @param lsCity 区域市
     * @return 区域市集合
     */
    public List<LsCity> selectLsCityList(LsCity lsCity);

    /**
     * 新增区域市
     *
     * @param lsCity 区域市
     * @return 结果
     */
    public int insertLsCity(LsCity lsCity);

    /**
     * 修改区域市
     *
     * @param lsCity 区域市
     * @return 结果
     */
    public int updateLsCity(LsCity lsCity);

    /**
     * 删除区域市
     *
     * @param id 区域市ID
     * @return 结果
     */
    public int deleteLsCityById(Long id);

    /**
     * 批量删除区域市
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLsCityByIds(Long[] ids);
}

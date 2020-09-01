package com.ruoyi.setting.mapper;

import com.ruoyi.setting.domain.LsProvince;

import java.util.List;

/**
 * 区域省Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-29
 */
public interface LsProvinceMapper {
    /**
     * 查询区域省
     *
     * @param id 区域省ID
     * @return 区域省
     */
    public LsProvince selectLsProvinceById(Long id);

    /**
     * 查询区域省列表
     *
     * @param lsProvince 区域省
     * @return 区域省集合
     */
    public List<LsProvince> selectLsProvinceList(LsProvince lsProvince);

    /**
     * 新增区域省
     *
     * @param lsProvince 区域省
     * @return 结果
     */
    public int insertLsProvince(LsProvince lsProvince);

    /**
     * 修改区域省
     *
     * @param lsProvince 区域省
     * @return 结果
     */
    public int updateLsProvince(LsProvince lsProvince);

    /**
     * 删除区域省
     *
     * @param id 区域省ID
     * @return 结果
     */
    public int deleteLsProvinceById(Long id);

    /**
     * 批量删除区域省
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLsProvinceByIds(Long[] ids);
}

package com.ruoyi.setting.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.setting.domain.LsDistrict;
import com.ruoyi.setting.mapper.LsDistrictMapper;
import com.ruoyi.setting.service.ILsDistrictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域区Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-29
 */
@Service
public class LsDistrictServiceImpl implements ILsDistrictService {
    private static final Logger logger = LoggerFactory.getLogger(LsDistrictServiceImpl.class);
    @Autowired
    private LsDistrictMapper lsDistrictMapper;

    /**
     * 查询区域区
     *
     * @param id 区域区ID
     * @return 区域区
     */
    @Override
    public LsDistrict selectLsDistrictById(Long id) {
        return lsDistrictMapper.selectLsDistrictById(id);
    }

    /**
     * 查询区域区列表
     *
     * @param lsDistrict 区域区
     * @return 区域区
     */
    @Override
    public List<LsDistrict> selectLsDistrictList(LsDistrict lsDistrict) {
        return lsDistrictMapper.selectLsDistrictList(lsDistrict);
    }

    /**
     * 新增区域区
     *
     * @param lsDistrict 区域区
     * @return 结果
     */
    @Override
    public int insertLsDistrict(LsDistrict lsDistrict) {
        lsDistrict.setCreateTime(DateUtils.getNowDate());
        return lsDistrictMapper.insertLsDistrict(lsDistrict);
    }

    /**
     * 修改区域区
     *
     * @param lsDistrict 区域区
     * @return 结果
     */
    @Override
    public int updateLsDistrict(LsDistrict lsDistrict) {
        return lsDistrictMapper.updateLsDistrict(lsDistrict);
    }

    /**
     * 批量删除区域区
     *
     * @param ids 需要删除的区域区ID
     * @return 结果
     */
    @Override
    public int deleteLsDistrictByIds(Long[] ids) {
        return lsDistrictMapper.deleteLsDistrictByIds(ids);
    }

    /**
     * 删除区域区信息
     *
     * @param id 区域区ID
     * @return 结果
     */
    @Override
    public int deleteLsDistrictById(Long id) {
        return lsDistrictMapper.deleteLsDistrictById(id);
    }
}

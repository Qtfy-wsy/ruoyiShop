package com.ruoyi.setting.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.setting.domain.LsCity;
import com.ruoyi.setting.mapper.LsCityMapper;
import com.ruoyi.setting.service.ILsCityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域市Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-29
 */
@Service
public class LsCityServiceImpl implements ILsCityService {
    private static final Logger logger = LoggerFactory.getLogger(LsCityServiceImpl.class);
    @Autowired
    private LsCityMapper lsCityMapper;

    /**
     * 查询区域市
     *
     * @param id 区域市ID
     * @return 区域市
     */
    @Override
    public LsCity selectLsCityById(Long id) {
        return lsCityMapper.selectLsCityById(id);
    }

    /**
     * 查询区域市列表
     *
     * @param lsCity 区域市
     * @return 区域市
     */
    @Override
    public List<LsCity> selectLsCityList(LsCity lsCity) {
        return lsCityMapper.selectLsCityList(lsCity);
    }

    /**
     * 新增区域市
     *
     * @param lsCity 区域市
     * @return 结果
     */
    @Override
    public int insertLsCity(LsCity lsCity) {
        lsCity.setCreateTime(DateUtils.getNowDate());
        return lsCityMapper.insertLsCity(lsCity);
    }

    /**
     * 修改区域市
     *
     * @param lsCity 区域市
     * @return 结果
     */
    @Override
    public int updateLsCity(LsCity lsCity) {
        return lsCityMapper.updateLsCity(lsCity);
    }

    /**
     * 批量删除区域市
     *
     * @param ids 需要删除的区域市ID
     * @return 结果
     */
    @Override
    public int deleteLsCityByIds(Long[] ids) {
        return lsCityMapper.deleteLsCityByIds(ids);
    }

    /**
     * 删除区域市信息
     *
     * @param id 区域市ID
     * @return 结果
     */
    @Override
    public int deleteLsCityById(Long id) {
        return lsCityMapper.deleteLsCityById(id);
    }
}

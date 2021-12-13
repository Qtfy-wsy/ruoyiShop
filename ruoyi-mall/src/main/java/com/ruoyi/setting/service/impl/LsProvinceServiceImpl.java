package com.ruoyi.setting.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.setting.domain.LsProvince;
import com.ruoyi.setting.mapper.LsProvinceMapper;
import com.ruoyi.setting.service.ILsProvinceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域省Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-29
 */
@Service
public class LsProvinceServiceImpl implements ILsProvinceService {
    private static final Logger logger = LoggerFactory.getLogger(LsProvinceServiceImpl.class);
    @Autowired
    private LsProvinceMapper lsProvinceMapper;

    /**
     * 查询区域省
     *
     * @param id 区域省ID
     * @return 区域省
     */
    @Override
    public LsProvince selectLsProvinceById(Long id) {
        return lsProvinceMapper.selectLsProvinceById(id);
    }

    /**
     * 查询区域省列表
     *
     * @param lsProvince 区域省
     * @return 区域省
     */
    @Override
    public List<LsProvince> selectLsProvinceList(LsProvince lsProvince) {
        return lsProvinceMapper.selectLsProvinceList(lsProvince);
    }

    /**
     * 新增区域省
     *
     * @param lsProvince 区域省
     * @return 结果
     */
    @Override
    public int insertLsProvince(LsProvince lsProvince) {
        lsProvince.setCreateTime(DateUtils.getNowDate());
        return lsProvinceMapper.insertLsProvince(lsProvince);
    }

    /**
     * 修改区域省
     *
     * @param lsProvince 区域省
     * @return 结果
     */
    @Override
    public int updateLsProvince(LsProvince lsProvince) {
        return lsProvinceMapper.updateLsProvince(lsProvince);
    }

    /**
     * 批量删除区域省
     *
     * @param ids 需要删除的区域省ID
     * @return 结果
     */
    @Override
    public int deleteLsProvinceByIds(Long[] ids) {
        return lsProvinceMapper.deleteLsProvinceByIds(ids);
    }

    /**
     * 删除区域省信息
     *
     * @param id 区域省ID
     * @return 结果
     */
    @Override
    public int deleteLsProvinceById(Long id) {
        return lsProvinceMapper.deleteLsProvinceById(id);
    }
}

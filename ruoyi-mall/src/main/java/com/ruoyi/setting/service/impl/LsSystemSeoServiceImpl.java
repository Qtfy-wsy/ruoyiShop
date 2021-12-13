package com.ruoyi.setting.service.impl;

import com.ruoyi.setting.domain.LsSystemSeo;
import com.ruoyi.setting.mapper.LsSystemSeoMapper;
import com.ruoyi.setting.service.ILsSystemSeoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统seo设置Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-29
 */
@Service
public class LsSystemSeoServiceImpl implements ILsSystemSeoService {
    private static final Logger logger = LoggerFactory.getLogger(LsSystemSeoServiceImpl.class);
    @Autowired
    private LsSystemSeoMapper lsSystemSeoMapper;

    /**
     * 查询系统seo设置
     *
     * @param id 系统seo设置ID
     * @return 系统seo设置
     */
    @Override
    public LsSystemSeo selectLsSystemSeoById(Long id) {
        return lsSystemSeoMapper.selectLsSystemSeoById(id);
    }

    /**
     * 查询系统seo设置列表
     *
     * @param lsSystemSeo 系统seo设置
     * @return 系统seo设置
     */
    @Override
    public List<LsSystemSeo> selectLsSystemSeoList(LsSystemSeo lsSystemSeo) {
        return lsSystemSeoMapper.selectLsSystemSeoList(lsSystemSeo);
    }

    /**
     * 新增系统seo设置
     *
     * @param lsSystemSeo 系统seo设置
     * @return 结果
     */
    @Override
    public int insertLsSystemSeo(LsSystemSeo lsSystemSeo) {
        return lsSystemSeoMapper.insertLsSystemSeo(lsSystemSeo);
    }

    /**
     * 修改系统seo设置
     *
     * @param lsSystemSeo 系统seo设置
     * @return 结果
     */
    @Override
    public int updateLsSystemSeo(LsSystemSeo lsSystemSeo) {
        return lsSystemSeoMapper.updateLsSystemSeo(lsSystemSeo);
    }

    /**
     * 批量删除系统seo设置
     *
     * @param ids 需要删除的系统seo设置ID
     * @return 结果
     */
    @Override
    public int deleteLsSystemSeoByIds(Long[] ids) {
        return lsSystemSeoMapper.deleteLsSystemSeoByIds(ids);
    }

    /**
     * 删除系统seo设置信息
     *
     * @param id 系统seo设置ID
     * @return 结果
     */
    @Override
    public int deleteLsSystemSeoById(Long id) {
        return lsSystemSeoMapper.deleteLsSystemSeoById(id);
    }
}

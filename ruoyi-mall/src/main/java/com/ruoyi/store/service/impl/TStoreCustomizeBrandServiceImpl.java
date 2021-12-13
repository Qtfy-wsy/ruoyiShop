package com.ruoyi.store.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.store.domain.TStoreCustomizeBrand;
import com.ruoyi.store.mapper.TStoreCustomizeBrandMapper;
import com.ruoyi.store.service.ITStoreCustomizeBrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 店铺自定义品牌列Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Service
public class TStoreCustomizeBrandServiceImpl implements ITStoreCustomizeBrandService {
    private static final Logger logger = LoggerFactory.getLogger(TStoreCustomizeBrandServiceImpl.class);
    @Autowired
    private TStoreCustomizeBrandMapper tStoreCustomizeBrandMapper;

    /**
     * 查询店铺自定义品牌列
     *
     * @param id 店铺自定义品牌列ID
     * @return 店铺自定义品牌列
     */
    @Override
    public TStoreCustomizeBrand selectTStoreCustomizeBrandById(Long id) {
        return tStoreCustomizeBrandMapper.selectTStoreCustomizeBrandById(id);
    }

    /**
     * 查询店铺自定义品牌列列表
     *
     * @param tStoreCustomizeBrand 店铺自定义品牌列
     * @return 店铺自定义品牌列
     */
    @Override
    public List<TStoreCustomizeBrand> selectTStoreCustomizeBrandList(TStoreCustomizeBrand tStoreCustomizeBrand) {
        return tStoreCustomizeBrandMapper.selectTStoreCustomizeBrandList(tStoreCustomizeBrand);
    }

    /**
     * 新增店铺自定义品牌列
     *
     * @param tStoreCustomizeBrand 店铺自定义品牌列
     * @return 结果
     */
    @Override
    public int insertTStoreCustomizeBrand(TStoreCustomizeBrand tStoreCustomizeBrand) {
        tStoreCustomizeBrand.setCreateTime(DateUtils.getNowDate());
        return tStoreCustomizeBrandMapper.insertTStoreCustomizeBrand(tStoreCustomizeBrand);
    }

    /**
     * 修改店铺自定义品牌列
     *
     * @param tStoreCustomizeBrand 店铺自定义品牌列
     * @return 结果
     */
    @Override
    public int updateTStoreCustomizeBrand(TStoreCustomizeBrand tStoreCustomizeBrand) {
        return tStoreCustomizeBrandMapper.updateTStoreCustomizeBrand(tStoreCustomizeBrand);
    }

    /**
     * 批量删除店铺自定义品牌列
     *
     * @param ids 需要删除的店铺自定义品牌列ID
     * @return 结果
     */
    @Override
    public int deleteTStoreCustomizeBrandByIds(Long[] ids) {
        return tStoreCustomizeBrandMapper.deleteTStoreCustomizeBrandByIds(ids);
    }

    /**
     * 删除店铺自定义品牌列信息
     *
     * @param id 店铺自定义品牌列ID
     * @return 结果
     */
    @Override
    public int deleteTStoreCustomizeBrandById(Long id) {
        return tStoreCustomizeBrandMapper.deleteTStoreCustomizeBrandById(id);
    }
}

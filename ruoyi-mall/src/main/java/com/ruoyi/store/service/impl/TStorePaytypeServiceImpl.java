package com.ruoyi.store.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.store.domain.TStorePaytype;
import com.ruoyi.store.mapper.TStorePaytypeMapper;
import com.ruoyi.store.service.ITStorePaytypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 门店支付类型Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Service
public class TStorePaytypeServiceImpl implements ITStorePaytypeService {
    private static final Logger logger = LoggerFactory.getLogger(TStorePaytypeServiceImpl.class);
    @Autowired
    private TStorePaytypeMapper tStorePaytypeMapper;

    /**
     * 查询门店支付类型
     *
     * @param id 门店支付类型ID
     * @return 门店支付类型
     */
    @Override
    public TStorePaytype selectTStorePaytypeById(Long id) {
        return tStorePaytypeMapper.selectTStorePaytypeById(id);
    }

    /**
     * 查询门店支付类型列表
     *
     * @param tStorePaytype 门店支付类型
     * @return 门店支付类型
     */
    @Override
    public List<TStorePaytype> selectTStorePaytypeList(TStorePaytype tStorePaytype) {
        return tStorePaytypeMapper.selectTStorePaytypeList(tStorePaytype);
    }

    /**
     * 新增门店支付类型
     *
     * @param tStorePaytype 门店支付类型
     * @return 结果
     */
    @Override
    public int insertTStorePaytype(TStorePaytype tStorePaytype) {
        tStorePaytype.setCreateTime(DateUtils.getNowDate());
        return tStorePaytypeMapper.insertTStorePaytype(tStorePaytype);
    }

    /**
     * 修改门店支付类型
     *
     * @param tStorePaytype 门店支付类型
     * @return 结果
     */
    @Override
    public int updateTStorePaytype(TStorePaytype tStorePaytype) {
        return tStorePaytypeMapper.updateTStorePaytype(tStorePaytype);
    }

    /**
     * 批量删除门店支付类型
     *
     * @param ids 需要删除的门店支付类型ID
     * @return 结果
     */
    @Override
    public int deleteTStorePaytypeByIds(Long[] ids) {
        return tStorePaytypeMapper.deleteTStorePaytypeByIds(ids);
    }

    /**
     * 删除门店支付类型信息
     *
     * @param id 门店支付类型ID
     * @return 结果
     */
    @Override
    public int deleteTStorePaytypeById(Long id) {
        return tStorePaytypeMapper.deleteTStorePaytypeById(id);
    }
}

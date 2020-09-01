package com.ruoyi.store.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.store.domain.TStoreReservation;
import com.ruoyi.store.mapper.TStoreReservationMapper;
import com.ruoyi.store.service.ITStoreReservationService;
import com.ruoyi.util.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 门店预约Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Slf4j
@Service
public class TStoreReservationServiceImpl implements ITStoreReservationService {
    private static final Logger logger = LoggerFactory.getLogger(TStoreReservationServiceImpl.class);
    @Autowired
    private TStoreReservationMapper tStoreReservationMapper;

    /**
     * 注入门店预约数据库接口
     */
    @Autowired
    private TStoreReservationMapper reservationMapper;


    /**
     * 查询门店预约列表
     *
     * @param pageHelper 分页帮助类
     * @param customerId 会员id
     * @param skuName    单品名称
     * @return 门店预约列表
     */
    @Override
    public PageHelper<TStoreReservation> queryReservationList(PageHelper<TStoreReservation> pageHelper, long customerId, String skuName) {
        log.debug("queryReservationList and pageHelper :{} \r\n customerId :{} \r\n skuName :{}", pageHelper, customerId, skuName);

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("skuName", skuName);
        return pageHelper.setListDates(reservationMapper.queryReservationList(pageHelper.getQueryParams(params, reservationMapper.queryReservationListCount(params))));
    }

    /**
     * 取消门店预约
     *
     * @param ids        门店预约id数组
     * @param customerId 会员id
     * @param storeId    门店id
     * @return 成功>0 失败0
     */
    @Override
    public int cancelReservation(Long[] ids, long customerId, long storeId) {
        log.debug("cancelReservation and ids :{} \r\n customerId :{} \r\n storeId :{}", ids, customerId, storeId);

        if (ArrayUtils.isEmpty(ids)) {
            log.error("cancelReservation fail due to ids is empty");
            return 0;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("customerId", customerId);
        params.put("storeId", storeId);

        return reservationMapper.cancelReservation(params);
    }

    /**
     * 查询门店预约列表（门店用，不带分页）
     *
     * @param customerId 会员id
     * @param storeId    门店id
     * @return 门店预约集合
     */
    @Override
    public List<TStoreReservation> queryReservationListForStore(long customerId, long storeId) {
        log.debug("queryReservationListForStore and csutomerId :{} \r\n storeId :{}", customerId, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("storeId", storeId);

        return reservationMapper.queryReservationListForStore(params);
    }

    /**
     * 通过id查询门店预约列表
     *
     * @param ids     预约id数组
     * @param storeId 门店id
     * @return 门店预约集合
     */
    @Override
    public List<TStoreReservation> queryReservationListForStoreByIds(Long[] ids, long storeId) {
        log.debug("queryReservationListForStoreByIds and ids :{} \r\n storeId :{}", ids, storeId);

        if (ArrayUtils.isEmpty(ids)) {
            log.error("queryReservationListForStoreByIds fail due to ids is empty");
            return Collections.emptyList();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("storeId", storeId);

        return reservationMapper.queryReservationListForStoreByIds(params);
    }


    /**
     * 修改预约商品数量
     *
     * @param num     预约商品数量
     * @param id      预约id
     * @param storeId 门店id
     * @return 成功1 失败0
     */
    @Override
    public int updateReservationNum(int num, long id, long storeId) {
        log.debug("updateReservationNum and num :{} \r\n id :{} \r\n storeId :{}", num, id, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("num", num);
        params.put("id", id);
        params.put("storeId", storeId);
        return reservationMapper.updateReservationNum(params);
    }

    /**
     * 查询门店预约
     *
     * @param id 门店预约ID
     * @return 门店预约
     */
    @Override
    public TStoreReservation selectTStoreReservationById(Long id) {
        return tStoreReservationMapper.selectTStoreReservationById(id);
    }

    /**
     * 查询门店预约列表
     *
     * @param tStoreReservation 门店预约
     * @return 门店预约
     */
    @Override
    public List<TStoreReservation> selectTStoreReservationList(TStoreReservation tStoreReservation) {
        return tStoreReservationMapper.selectTStoreReservationList(tStoreReservation);
    }

    /**
     * 新增门店预约
     *
     * @param tStoreReservation 门店预约
     * @return 结果
     */
    @Override
    public int insertTStoreReservation(TStoreReservation tStoreReservation) {
        tStoreReservation.setCreateTime(DateUtils.getNowDate());
        return tStoreReservationMapper.insertTStoreReservation(tStoreReservation);
    }

    /**
     * 修改门店预约
     *
     * @param tStoreReservation 门店预约
     * @return 结果
     */
    @Override
    public int updateTStoreReservation(TStoreReservation tStoreReservation) {
        return tStoreReservationMapper.updateTStoreReservation(tStoreReservation);
    }

    /**
     * 批量删除门店预约
     *
     * @param ids 需要删除的门店预约ID
     * @return 结果
     */
    @Override
    public int deleteTStoreReservationByIds(Long[] ids) {
        return tStoreReservationMapper.deleteTStoreReservationByIds(ids);
    }

    /**
     * 删除门店预约信息
     *
     * @param id 门店预约ID
     * @return 结果
     */
    @Override
    public int deleteTStoreReservationById(Long id) {
        return tStoreReservationMapper.deleteTStoreReservationById(id);
    }
}

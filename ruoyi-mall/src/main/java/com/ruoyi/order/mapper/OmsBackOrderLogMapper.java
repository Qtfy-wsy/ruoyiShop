package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.OmsBackOrderLog;

import java.util.List;

/**
 * 退款退货操作日志Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface OmsBackOrderLogMapper {
    /**
     * 查询退款退货操作日志
     *
     * @param id 退款退货操作日志ID
     * @return 退款退货操作日志
     */
    public OmsBackOrderLog selectOmsBackOrderLogById(Long id);

    /**
     * 查询退款退货操作日志列表
     *
     * @param omsBackOrderLog 退款退货操作日志
     * @return 退款退货操作日志集合
     */
    public List<OmsBackOrderLog> selectOmsBackOrderLogList(OmsBackOrderLog omsBackOrderLog);

    List<OmsBackOrderLog> queryByBackOrderId(long bacKOrderId);

    /**
     * 新增退款退货操作日志
     *
     * @param omsBackOrderLog 退款退货操作日志
     * @return 结果
     */
    public int insertOmsBackOrderLog(OmsBackOrderLog omsBackOrderLog);

    /**
     * 修改退款退货操作日志
     *
     * @param omsBackOrderLog 退款退货操作日志
     * @return 结果
     */
    public int updateOmsBackOrderLog(OmsBackOrderLog omsBackOrderLog);

    /**
     * 删除退款退货操作日志
     *
     * @param id 退款退货操作日志ID
     * @return 结果
     */
    public int deleteOmsBackOrderLogById(Long id);

    /**
     * 批量删除退款退货操作日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOmsBackOrderLogByIds(Long[] ids);
}

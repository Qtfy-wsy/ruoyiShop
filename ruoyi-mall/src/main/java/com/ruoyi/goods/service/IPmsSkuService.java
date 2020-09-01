package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.PmsSkuItem;
import com.ruoyi.util.PageHelper;

import java.math.BigDecimal;
import java.util.List;

/**
 * 单品Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IPmsSkuService {
    /**
     * 查询单品
     *
     * @param id 单品ID
     * @return 单品
     */
    public PmsSku selectPmsSkuById(String id);

    /**
     * 查询单品列表
     *
     * @param pmsSku 单品
     * @return 单品集合
     */
    public List<PmsSku> selectPmsSkuList(PmsSku pmsSku);

    /**
     * 新增单品
     *
     * @param pmsSku 单品
     * @return 结果
     */
    public int insertPmsSku(PmsSku pmsSku);

    /**
     * 修改单品
     *
     * @param pmsSku 单品
     * @return 结果
     */
    public int updatePmsSku(PmsSku pmsSku);

    /**
     * 批量删除单品
     *
     * @param ids 需要删除的单品ID
     * @return 结果
     */
    public int deletePmsSkuByIds(String[] ids);

    /**
     * 删除单品信息
     *
     * @param id 单品ID
     * @return 结果
     */
    public int deletePmsSkuById(String id);

    /**
     * 根据商品id查询单品信息
     *
     * @param spuId   商品id
     * @param storeId 店铺id
     * @param skuItem 查询条件
     * @return 返回单品信息
     */
    List<PmsSku> querySkuBySpuId(long spuId, long storeId, PmsSkuItem... skuItem);

    /**
     * 根据商品id修改单品上下架状态
     *
     * @param spuIds  商品id
     * @param status  上下架状态
     * @param storeId 店铺id
     * @return 成功返回>1 失败返回0
     */
    int updateShelvesStatus(List<Long> spuIds, String status, long storeId);

    /**
     * 添加单品
     *
     * @param skus 单品信息
     */
    void addSkus(List<PmsSku> skus);


    /**
     * 根据商品id删除单品信息
     *
     * @param spuId   商品id
     * @param storeId 店铺id
     */
    void deleteBySpuId(long spuId, long storeId);

    /**
     * 更新单品信息
     *
     * @param skus           单品集合
     * @param spuId          商品id
     * @param storeId        店铺id
     * @param commissionRate 佣金比例
     */
    void updateSkus(List<PmsSku> skus, long spuId, long storeId, BigDecimal commissionRate);

    /**
     * 根据单品id查询单品信息
     *
     * @param skuId   单品id
     * @param storeId 店铺id
     * @return 返回单品信息(包括单品的规格信息)
     */
    PmsSku querySkuByIdWithSpecs(String skuId, long storeId);

    /**
     * 分页查询单品信息
     *
     * @param pageHelper 分页帮助类
     * @param storeId    店铺id
     * @param name       单品名称
     * @param id         单品编号
     * @param skuItemse  要设置的属性枚举
     * @return 返回单品信息 (包括单品的规格信息)
     */
    PageHelper<PmsSku> querySkusWithSpecs(PageHelper<PmsSku> pageHelper, long storeId, String name, String id, PmsSkuItem... skuItemse);

    /**
     * 分页查询分销单品
     *
     * @param pageHelper 分页帮助类
     * @return 返回分销单品信息
     */
    PageHelper<PmsSku> queryCommissionSkuList(PageHelper<PmsSku> pageHelper);

    /**
     * 单品审核通过
     *
     * @param spuId 商品id
     * @return 成功返回1 失败返回0
     */
    int auditPass(long spuId);

    /**
     * 单品审核拒绝
     *
     * @param spuId  商品id
     * @param reason 拒绝原因
     * @return 成功返回1 失败返回0
     */
    int auditRefuse(String reason, long spuId);

    /**
     * 根据店铺id查询前几条单品数据
     *
     * @param storeId 店铺id
     * @return 单品集合
     */
    List<PmsSku> queryFiveDataForAttentionStore(long storeId);

    /**
     * 根据单品id查询单品信息
     *
     * @param skuId 单品id
     * @return 返回单品信息(只有单品信息)
     */
    PmsSku querySkuById(String skuId);

    /**
     * 设置单品的详情
     *
     * @param sku      单品
     * @param skuItems 要设置的属性枚举
     * @return 返回单品
     */
    PmsSku setSkuDetail(PmsSku sku, PmsSkuItem... skuItems);

    /**
     * 根据商品id查询所有单品的规格信息
     *
     * @param spuId 商品id
     * @return 返回所有单品的规格信息
     */
    List<PmsSku> queryAllSkuSpecs(long spuId);

    /**
     * 根据单品id查询单品信息
     *
     * @param skuIds 单品id
     * @return 返回单品信息
     */
    List<PmsSku> querySkuByIds(String[] skuIds);

    /**
     * 减去单品的库存
     *
     * @param skuId 单品id
     * @param stock 库存
     * @return 成功返回>0  失败返回0
     */
    int reduceSkuStock(String skuId, int stock);

    /**
     * 增加单品的库存
     *
     * @param skuId 单品id
     * @param stock 库存
     * @return 成功返回>0  失败返回0
     */
    int increaseSkuStock(String skuId, int stock);

    /**
     * 查询店铺最近3天上架的商品数量
     *
     * @param storeId 店铺id
     * @return 返回店铺最近7天上架的商品数量
     */
    int lastUpSkusNum(long storeId);

    /**
     * 查询店铺有促销的单品总数
     *
     * @param storeId 店铺id
     * @return 返回店铺有促销的单品总数
     */
    int marketSkusNum(long storeId);

    /**
     * 查询最近3天上架的商品
     *
     * @param pageHelper 分页帮助类
     * @param storeId    店铺id
     * @return 返回单品数据
     */
    PageHelper<PmsSku> queryLastUpSkus(PageHelper<PmsSku> pageHelper, long storeId);

    /**
     * 查询有促销的单品数据
     *
     * @param pageHelper 分页帮助类
     * @param storeId    店铺id
     * @return 返回单品数据
     */
    PageHelper<PmsSku> queryMarketSkus(PageHelper<PmsSku> pageHelper, long storeId);

    /**
     * 查询商品详细信息(按照数组的顺序)
     *
     * @param skuIds   单品id数组
     * @param skuItems 附加单品信息
     * @return 商品信息
     */
    List<PmsSku> querySkusBySkuIdsSort(String[] skuIds, PmsSkuItem... skuItems);

    /**
     * 佣金设置
     *
     * @param spuId           商品id
     * @param commissionRate  佣金比例
     * @param sCommissionRate 二级佣金比例
     * @param storeId         店铺id
     * @return 成功返回>0 失败返回0
     */
    int updateCommission(long spuId, BigDecimal commissionRate, BigDecimal sCommissionRate, long storeId);

    /**
     * 根据店铺id修改商品上下架状态
     *
     * @param status   状态
     * @param storeIds 店铺id集合
     * @return 成功返回 >1 失败返回0
     */
    int updateShelvesStatusByStoreIds(String status, List<Long> storeIds);

    /**
     * 根据店铺id查询商品数量
     *
     * @param storeId 店铺id
     * @return 店铺下商品数量
     */
    int querySkuCountByStoreId(long storeId);

    /**
     * 修改单品为待审核状态
     *
     * @param spuIds  商品id
     * @param storeId 店铺id
     * @return 成功》0
     */
    int updateSkuToAudit(List<Long> spuIds, long storeId);

    /**
     * 修改单品物流模版id
     *
     * @param logisticsTemplateId        物流模版id
     * @param defaultLogisticsTemplateId 默认物流模版id
     * @param storeId                    店铺id
     * @return 成功>0 否则失败
     */
    int updateSkuLogisticsTemplateId(long logisticsTemplateId, long defaultLogisticsTemplateId, long storeId);
}

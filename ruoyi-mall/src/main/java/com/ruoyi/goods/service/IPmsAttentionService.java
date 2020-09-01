package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsAttention;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 商品关注Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IPmsAttentionService {
    /**
     * 分页查询会员的商品关注
     *
     * @param pageHelper 分页帮助类
     * @param customerId 会员id
     * @return 返回会员的商品关注
     */
    PageHelper<PmsAttention> queryAttentions(PageHelper<PmsAttention> pageHelper, long customerId);

    /**
     * 分页查询会员的商品关注-会员中心
     *
     * @param pageHelper 分页帮助类
     * @param customerId 会员id
     * @return 返回会员的商品关注
     */
    PageHelper<PmsAttention> queryAttentionsForCustomerCentre(PageHelper<PmsAttention> pageHelper, long customerId);

    /**
     * 取消关注商品
     *
     * @param skuId      单品id
     * @param customerId 会员id
     * @return 返回删除码
     */
    int cancelAttention(String skuId, long customerId);

    /**
     * 添加商品关注
     *
     * @param customerId 会员id
     * @param skuId      单品id
     * @return 成功返回1 失败返回0
     */
    int addAttention(long customerId, String skuId);

    /**
     * 用户是否关注了单品
     *
     * @param customerId 用户id
     * @param skuId      单品id
     * @return 关注返回true  没有关注返回false
     */
    boolean hasAttention(long customerId, String skuId);

    /**
     * 用户关注商品的总数
     *
     * @param customerId 会员id
     * @return 返回用户关注商品的总数
     */
    int queryAttentionCount(long customerId);

    /**
     * 查询商品关注
     *
     * @param id 商品关注ID
     * @return 商品关注
     */
    public PmsAttention selectPmsAttentionById(Long id);

    /**
     * 查询商品关注列表
     *
     * @param pmsAttention 商品关注
     * @return 商品关注集合
     */
    public List<PmsAttention> selectPmsAttentionList(PmsAttention pmsAttention);

    /**
     * 新增商品关注
     *
     * @param pmsAttention 商品关注
     * @return 结果
     */
    public int insertPmsAttention(PmsAttention pmsAttention);

    /**
     * 修改商品关注
     *
     * @param pmsAttention 商品关注
     * @return 结果
     */
    public int updatePmsAttention(PmsAttention pmsAttention);

    /**
     * 批量删除商品关注
     *
     * @param ids 需要删除的商品关注ID
     * @return 结果
     */
    public int deletePmsAttentionByIds(Long[] ids);

    /**
     * 删除商品关注信息
     *
     * @param id 商品关注ID
     * @return 结果
     */
    public int deletePmsAttentionById(Long id);
}

package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsAttention;

import java.util.List;
import java.util.Map;

/**
 * 商品关注Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsAttentionMapper {
    /**
     * 查询商品关注总数
     *
     * @param params 查询参数
     * @return 返回商品关注总数
     */

    int queryAttentionCount(Map<String, Object> params);

    /**
     * 查询商品关注
     *
     * @param params 查询参数
     * @return 返回商品关注
     */

    List<PmsAttention> queryAttentions(Map<String, Object> params);

    /**
     * 取消关注商品
     *
     * @param attention 商品关注实体类
     * @return 返回删除结果
     */

    int cancelAttention(PmsAttention attention);

    /**
     * 添加商品关注
     *
     * @param attention 商品关注
     * @return 成功返回1 失败返回0
     */

    int addAttention(PmsAttention attention);

    /**
     * 查询用户是否关注了指定的单品
     *
     * @param params 参数
     * @return >1 关注  0 未关注
     */

    int queryCustomerAttenion(Map<String, Object> params);

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
     * 删除商品关注
     *
     * @param id 商品关注ID
     * @return 结果
     */
    public int deletePmsAttentionById(Long id);

    /**
     * 批量删除商品关注
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsAttentionByIds(Long[] ids);
}

package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsGoods;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.PmsSkuItem;
import com.ruoyi.goods.domain.StoreSpu;
import com.ruoyi.goods.vo.SpuSearchCondition;
import com.ruoyi.util.PageHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

/**
 * 商品Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IPmsGoodsService {

	/**
	 * 查询所有店铺的商品 (该分页商关联店铺进行查询) (目前用在平台查询店铺的商品和审核的商品)
	 *
	 * @param pageHelper         分页帮助类
	 * @param spuSearchCondition 搜索条件
	 * @return 返回所有店铺的商品
	 */
	PageHelper<PmsGoods> queryAllStoreSpus(PageHelper<PmsGoods> pageHelper, SpuSearchCondition spuSearchCondition);

	/**
	 * 分页查询商品信息(目前用在平台和店铺的促销商品选择列表查询)
	 *
	 * @param pageHelper         分页帮助类
	 * @param spuSearchCondition 商品搜索条件
	 * @return 返回商品信息
	 */
	PageHelper<PmsGoods> querySimpleSpus(PageHelper<PmsGoods> pageHelper, SpuSearchCondition spuSearchCondition);


	/**
	 * 查询商品
	 *
	 * @param id 商品ID
	 * @return 商品
	 */
	public PmsGoods selectPmsGoodsById(Long id);

	List<PmsGoods> querySpus(PmsGoods pmsGoods);

	/**
	 * 分页查询商品信息(目前用在平台和店铺的商品列表查询)
	 *
	 * @param pageHelper         分页帮助类
	 * @param spuSearchCondition 商品搜索条件
	 * @return 返回商品信息
	 */
	PageHelper<PmsGoods> querySpus(PageHelper<PmsGoods> pageHelper, SpuSearchCondition spuSearchCondition);

	/**
	 * 查询商品列表
	 *
	 * @param pmsGoods 商品
	 * @return 商品集合
	 */
	public List<PmsGoods> selectPmsGoodsList(PmsGoods pmsGoods);

	/**
	 * 新增商品
	 *
	 * @param pmsGoods 商品
	 * @return 结果
	 */
	public int insertPmsGoods(PmsGoods pmsGoods);

	/**
	 * 修改商品
	 *
	 * @param pmsGoods 商品
	 * @return 结果
	 */
	public int updatePmsGoods(PmsGoods pmsGoods);

	/**
	 * 批量删除商品
	 *
	 * @param ids 需要删除的商品ID
	 * @return 结果
	 */
	public int deletePmsGoodsByIds(Long[] ids);

	/**
	 * 删除商品信息
	 *
	 * @param id 商品ID
	 * @return 结果
	 */
	public int deletePmsGoodsById(Long id);

	/**
	 * 查询商品信息
	 *
	 * @param id      商品id
	 * @param storeId 店铺id
	 * @return 返回商品信息
	 */
	PmsGoods querySpu(long id, long storeId);


	/**
	 * 分页查询单品信息(目前用在社区团购新增团购页面选择列表查询)
	 *
	 * @param pageHelper 分页帮助类
	 * @param storeId    店铺id
	 * @param name       单品名称
	 * @param skuNo      单品编号
	 * @param skuItemse  要设置的属性枚举
	 * @return 返回单品信息 (包括单品的规格信息)
	 */
	PageHelper<PmsSku> querySkus(PageHelper<PmsSku> pageHelper, long storeId, String name, String skuNo, PmsSkuItem... skuItemse);


	/**
	 * 分页查询未关联店铺三级分类的商品
	 *
	 * @param pageHelper 分页帮助类
	 * @param name       商品名称
	 * @param spuId      商品id
	 * @param storeId    店铺id
	 * @return 返回商品信息
	 */
	PageHelper<PmsGoods> queryAllSpusWithoutStoreCategory(PageHelper<PmsGoods> pageHelper, String name, Long spuId, long storeId);

	/**
	 * 根据店铺三级分类分页查询商品信息
	 *
	 * @param pageHelper   分页帮助类
	 * @param name         商品名称
	 * @param spuId        商品id
	 * @param storeId      店铺id
	 * @param storeTcateId 店铺三级分类id
	 * @return 返回商品信息
	 */
	PageHelper<PmsGoods> queryAllSpusByStoreCategory(PageHelper<PmsGoods> pageHelper, String name, Long spuId, long storeId, long storeTcateId);

	/**
	 * 根据店铺三级分类查询商品id
	 *
	 * @param storeId      店铺id
	 * @param storeTcateId 店铺三级分类id
	 * @return 返回商品id数组
	 */
	Long[] queryAllSpuIdByStoreCategory(long storeId, long storeTcateId);

	/**
	 * 修改店铺三级分类关联商品
	 *
	 * @param ids          商品id数组
	 * @param storeFcateId 店铺商品一级分类
	 * @param storeScateId 店铺商品二级分类
	 * @param storeTcateId 店铺商品三级分类
	 * @param storeId      店铺id
	 * @return 成功>0 失败0
	 */
	int updateSpuWithStoreCategory(Long[] ids, long storeFcateId, long storeScateId, long storeTcateId, long storeId);

	/**
	 * 取消店铺三级分类关联商品
	 *
	 * @param ids     商品id数组
	 * @param storeId 店铺id
	 * @return 成功>0 失败0
	 */
	int cancelSpuWithStoreCategory(Long[] ids, long storeId);

	/**
	 * 根据三级分类id取消关联商品
	 *
	 * @param storeTcateId 三级分类id
	 * @param storeId      店铺id
	 * @return 成功>0 失败0
	 */
	int cancelSpuWithStoreCategoryByStoreTcateId(long storeTcateId, long storeId);

	/**
	 * 删除商品信息
	 *
	 * @param spu 商品信息
	 * @return 成功返回1  失败返回0
	 */
	int deleteSpu(PmsGoods spu);

	/**
	 * 批量删除商品信息
	 *
	 * @param spus 商品信息
	 * @return 成功返回1 失败返回0
	 */
	int deleteSpus(List<PmsGoods> spus);

	/**
	 * 根据商品id改变单品的上下架状态
	 *
	 * @param spuIds   商品id集合
	 * @param status   上下架状态  0下架 1上架
	 * @param storeId  店铺id
	 * @param consumer 回调通知es 建立索引
	 * @return 成功返回>1  失败返回0
	 */
	int updateShelvesStatus(List<Long> spuIds, String status, long storeId, Consumer<Long> consumer);

	/**
	 * 更新商品信息
	 *
	 * @param spu      商品信息
	 * @param consumer 回调通知es 建立索引
	 * @return 成功返回1 失败返回0
	 */
	int updateSpu(PmsGoods spu, Consumer<Long> consumer);

	/**
	 * 查询手机端的详情
	 *
	 * @param id 商品id
	 * @return 返回手机端的详情
	 */
	String queryMobileDesc(long id);


	/**
	 * 查询商品信息
	 *
	 * @param spuId   商品id
	 * @param storeId 店铺id 如果不需要根据店铺id过滤 则带-1
	 * @return 返回商品信息
	 */
	PmsGoods querySimpleSpu(long spuId, long storeId);

	/**
	 * 根据三级分类id查询是否关联商品总条数
	 *
	 * @param ThirdCateId 商品三级分类id
	 * @return 返回 0 即表示该三级分类不关联商品 >0 即表示该三级分类关联商品
	 */
	int querySpuByThirdCateId(long ThirdCateId);

	/**
	 * 查询商品总数 (es使用)
	 *
	 * @return 返回商品的总数
	 */
	int querySpuCountForEs();

	/**
	 * 查询商品信息(es使用)
	 *
	 * @param start 开始纪录数
	 * @param size  查询纪录的大小
	 * @return 返回商品信息
	 */
	List<PmsGoods> querySpuForEs(int start, int size);

	/**
	 * 佣金设置
	 *
	 * @param id              商品id
	 * @param commissionRate  佣金比例
	 * @param sCommissionRate 二级佣金比例
	 * @param storeId         店铺id
	 * @return 成功返回>0 失败返回0
	 */
	int updateCommission(long id, BigDecimal commissionRate, BigDecimal sCommissionRate, long storeId);

	/**
	 * 批量佣金设置
	 *
	 * @param ids             商品id数组
	 * @param commissionRate  佣金比例
	 * @param sCommissionRate 二级佣金比例
	 * @param storeId         店铺id
	 * @return 成功返回1 失败返回0
	 */
	int updateCommissions(List<Long> ids, BigDecimal commissionRate, BigDecimal sCommissionRate, long storeId);


	/**
	 * 商品审核通过
	 *
	 * @param spuId    商品id
	 * @param consumer 回调
	 * @return 成功返回1 失败返回0
	 */
	int auditPass(Consumer<Long> consumer, long spuId);

	/**
	 * 商品审核拒绝
	 *
	 * @param spuId    商品id
	 * @param consumer 回调
	 * @param reason   拒绝原因
	 * @return 成功返回1 失败返回0
	 */
	int auditRefuse(String reason, Consumer<Long> consumer, long spuId);


	/**
	 * 根据店铺id修改商品上下架状态
	 *
	 * @param status   状态
	 * @param storeIds 店铺id集合
	 * @return 成功返回 >1 失败返回0
	 */
	int updateShelvesStatusByStoreIds(String status, List<Long> storeIds);

	/**
	 * 根据单品id查询分类扣率
	 *
	 * @param skuId 单品id
	 * @return 返回分类扣率
	 */
	BigDecimal queryCateRateBySkuId(String skuId);

	/**
	 * 查找类型关联的商品数量
	 *
	 * @param typeId 类型id
	 * @return 类型关联的商品数量
	 */
	int querySpuCountByTypeId(long typeId);

	/**
	 * 根据spuId查找seo设置(仅包含seo信息)
	 *
	 * @param spuId 商品id
	 * @return 商品实体
	 */
	PmsGoods querySeoBySpuId(long spuId);

	/**
	 * 分页查询门店商品信息
	 *
	 * @param pageHelper 分页帮助类
	 * @param name       商品名称
	 * @param storeId    门店id
	 * @return 返回商品信息
	 */
	PageHelper<StoreSpu> queryStoreSpuList(PageHelper<StoreSpu> pageHelper, String name, long storeId);

	/**
	 * 分页查询门店在售商品信息
	 *
	 * @param pageHelper 分页帮助类
	 * @param name       商品名称
	 * @param storeId    门店id
	 * @return 返回门店在售商品信息
	 */
	PageHelper<StoreSpu> queryStoreOnSaleSpuList(PageHelper<StoreSpu> pageHelper, String name, long storeId);


	/**
	 * 查询门店商品信息
	 *
	 * @param spuId   商品id
	 * @param storeId 门店id
	 * @return 返回商品信息
	 */
	StoreSpu queryStoreSpu(long spuId, long storeId);

	/**
	 * 根据id查询商品信息（导出用）
	 *
	 * @param ids     ids
	 * @param storeId 店铺id
	 * @return 商品信息
	 */
	List<PmsGoods> querySpuByIdsForExport(List<Long> ids, long storeId);

	/**
	 * 查询所有商品信息（导出用）
	 *
	 * @param storeId 店铺id
	 * @return 商品信息
	 */
	List<PmsGoods> queryAllSpuForExport(long storeId);

	/**
	 * 修改商品物流模版id
	 *
	 * @param logisticsTemplateId        物流模版id
	 * @param defaultLogisticsTemplateId 默认物流模版id
	 * @param storeId                    店铺id
	 * @return 成功>0 否则失败
	 */
	int updateSpuLogisticsTemplateId(long logisticsTemplateId, long defaultLogisticsTemplateId, long storeId);

	/**
	 * 分页查询所有店铺待审核的商品
	 * @return 商品集合
	 */
	List<PmsGoods> queryAllStoreToBeAuditdSpus();

	/**
	 * 分页查询所有店铺的商品信息
	 * @param pmsGoods 商品
	 * @return 商品集合
	 */
	List<PmsGoods> queryAllStoreSpusList(PmsGoods pmsGoods);
}

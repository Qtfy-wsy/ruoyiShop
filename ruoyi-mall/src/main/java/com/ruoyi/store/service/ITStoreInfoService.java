package com.ruoyi.store.service;

import com.ruoyi.goods.domain.PmsBrand;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.vo.OnSaleStoreQueryParam;
import com.ruoyi.store.vo.StoreBusiness;
import com.ruoyi.store.vo.StoreBusinessInfo;
import com.ruoyi.store.vo.StoreReview;
import com.ruoyi.util.PageHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

/**
 * 店铺信息Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public interface ITStoreInfoService {
    /**
     * 查询店铺信息
     *
     * @param id 店铺信息ID
     * @return 店铺信息
     */
    public TStoreInfo selectTStoreInfoById(Long id);

    /**
     * 查询店铺信息列表
     *
     * @param tStoreInfo 店铺信息
     * @return 店铺信息集合
     */
    public List<TStoreInfo> selectTStoreInfoList(TStoreInfo tStoreInfo);

    /**
     * 新增店铺信息
     *
     * @param tStoreInfo 店铺信息
     * @return 结果
     */
    public int insertTStoreInfo(TStoreInfo tStoreInfo);

    /**
     * 修改店铺信息
     *
     * @param tStoreInfo 店铺信息
     * @return 结果
     */
    public int updateTStoreInfo(TStoreInfo tStoreInfo);

    /**
     * 批量删除店铺信息
     *
     * @param ids 需要删除的店铺信息ID
     * @return 结果
     */
    public int deleteTStoreInfoByIds(Long[] ids);

    /**
     * 删除店铺信息信息
     *
     * @param id 店铺信息ID
     * @return 结果
     */
    public int deleteTStoreInfoById(Long id);

    /**
     * 根据店铺id查询店铺信息
     *
     * @param storeId 店铺id
     * @return 店铺信息
     */
    TStoreInfo queryStoreInfo(Long storeId);

    /**
     * 开店处理店铺信息
     *
     * @param storeInfo  店铺实体类
     * @param customerId 会员ID
     * @return 返回值跳转页面
     */
    int dealStoreInfo(TStoreInfo storeInfo, long customerId);


    /**
     * 查询开店时填写的信息
     *
     * @param customerId
     * @return 店铺信息
     */
    TStoreInfo findOpenStoreInfo(long customerId);

    /**
     * 处理店铺经营类型
     *
     * @param customerId  会员Id
     * @param storeName   店铺名称
     * @param categoryIds 分类ids
     * @param brandIds    品牌ids
     * @param brands      自定义品牌集合
     * @return 添加返回码
     */
    int dealStoreBusinessInfo(long customerId, String storeName, long[] categoryIds, long[] brandIds, List<PmsBrand> brands);

    /**
     * 新增店铺（admin端新增）
     *
     * @param storeBusiness 店铺信息实体
     * @return -1用户不存在 1成功 -2 该用户下已有店铺
     */
    int addStore(StoreBusiness storeBusiness);

    /**
     * 开店查询店铺信息
     *
     * @param storeId 店铺id
     * @param status  品牌状态 状态  0 申请中  1通过 2 拒绝
     * @return 店铺信息
     */
    StoreBusinessInfo queryStoreBusinessInfo(long storeId, String status);

    /**
     * 开店查询店铺信息
     *
     * @param customerId 会员id
     * @param status     品牌状态 状态  0 申请中  1通过 2 拒绝
     * @return 店铺信息
     */
    StoreBusinessInfo queryStoreBusinessInfoForOpneStore(long customerId, String status);

    /**
     * 查询已审核/未审核商家集合
     *
     * @param pageHelper     分页帮助类
     * @param status         店铺状态 0填写资料中 1店铺审核中 2审核通过 3审核不通过 4店铺关闭
     * @param companyName    公司名称
     * @param storeName      店铺名称
     * @param createTime     创建时间
     * @param customerMobile 用户手机号
     * @param provinceId     省份id
     * @return 已审核/未审核商家集合
     */
    PageHelper<TStoreInfo> queryStoreInfoForAuditList(PageHelper<TStoreInfo> pageHelper, String status, String companyName, String storeName, String createTime, String customerMobile, long provinceId);


    /**
     * 编辑店铺有效期,结算周期,是否关店
     *
     * @param storeInfo 店铺信息
     * @return 编辑返回码
     */
    int editStoreTimeAndIsClose(TStoreInfo storeInfo, Consumer<Integer> consumer);

    /**
     * 通过商家审核
     *
     * @param storeInfo 商家实例
     * @return 成功返回1，失败返回0
     */
    int passStoreAudit(TStoreInfo storeInfo);

    /**
     * 拒绝商家审核
     *
     * @param storeInfo 商家实例
     * @return 成功返回1，失败返回0
     */
    int refuseStoreAudit(TStoreInfo storeInfo);

    /**
     * 删除商家
     *
     * @param id 商家id
     * @return 成功返回1，失败返回0
     */
    int deleteStore(long id);

    /**
     * 编辑店铺信息-客服QQ-公司信息-银行信息
     *
     * @param storeInfo 店铺信息实体类
     * @param flag      1客服QQ 2公司信息 3银行信息
     * @return -1参数错误编辑失败 1 编辑成功
     */
    int editMyStoreInfo(TStoreInfo storeInfo, String flag);

    /**
     * @param storeId 店铺id
     * @return 返回码 1：正常营业  -1：storeId为空，参数不全  -2:store不存在 -3 店铺被删除
     * -4 店铺审核中 -5 审核不通过 -6 店铺关闭 -7 超过有效期
     */
    int queryStoreState(Long storeId);

    /**
     * 根据条件搜索店铺
     *
     * @param pageHelper 分页帮助类
     * @param keyword    关键字
     * @param orderBy    排序条件 0:综合 1:销量 2:评论数
     * @return 店铺集合
     */
    PageHelper<TStoreInfo> queryStoreInfoForSearch(PageHelper<TStoreInfo> pageHelper, String keyword, int orderBy);

    /**
     * 批量关店
     *
     * @param ids      店铺id
     * @param consumer 回调
     * @return 成功返回>0 失败返回0
     */
    int closeStores(List<Long> ids, Consumer<Integer> consumer);

    /**
     * 校验店铺名是否存在
     *
     * @param storeName 店铺名
     * @return >0存在 否则不存在
     */
    int checkStoreNameExist(String storeName, long storeId);

    /**
     * 校验公司名是否存在
     *
     * @param companyName 公司名
     * @return >0存在 否则不存在
     */
    int checkCompanyNameExist(String companyName, long storeId);


    /**
     * 校验公司名是否存在(store端开店用)
     *
     * @param companyName 店铺名称
     * @param customerId  用户ID
     * @return 0 可用  1 不可用
     */
    int checkCompanyNameForOpenStore(String companyName, long customerId);


    /**
     * 根据店铺id查询店铺信息(门店评分)
     *
     * @param storeId 商家ID
     * @return 店铺信息
     */
    TStoreInfo selStoreInfo(Long storeId);

    /**
     * 查找一个在售门店
     *
     * @param skuId  单品id
     * @param cityId 市id
     * @return 门店信息
     */
    TStoreInfo queryOneOnSaleStore(String skuId, long cityId);

    /**
     * 查找在售门店
     *
     * @param skuId  单品id
     * @param cityId 市id
     * @return 门店信息集合
     */
    List<TStoreInfo> queryOnSaleStoreList(String skuId, long cityId);


    /**
     * 查找一个在售门店(根据经纬度距离排序)
     *
     * @param onSaleStoreQueryParam 在售门店搜索参数
     * @return 门店信息
     */
    TStoreInfo queryOneOnSaleStoreByCoordinate(OnSaleStoreQueryParam onSaleStoreQueryParam);

    /**
     * 查找在售门店(根据经纬度距离排序)
     *
     * @param onSaleStoreQueryParam 在售门店搜索参数
     * @return 门店信息集合
     */
    List<TStoreInfo> queryOnSaleStoreListByCoordinate(OnSaleStoreQueryParam onSaleStoreQueryParam);


    /**
     * 根据定位查询附近门店列表
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param distance  范围（km）
     * @return 门店信息
     */
    List<TStoreInfo> queryNearByStoreList(BigDecimal longitude, BigDecimal latitude, int distance);

    /**
     * 搜索门店列表
     *
     * @param pageHelper     分页帮助类
     * @param companyName    公司名称
     * @param storeName      店铺名称
     * @param customerMobile 手机号码
     * @return 返回门店列表
     */
    PageHelper<TStoreInfo> queryStoreInfoList(PageHelper<TStoreInfo> pageHelper, String companyName, String storeName, String customerMobile);

    /**
     * 开启店铺（门店用）
     *
     * @param storeId 店铺id
     * @return 1成功 否则失败
     */
    int openStoreForOutLetStore(long storeId);


    /**
     * 判断店铺是否有效
     *
     * @param storeId 店铺id
     * @return 有效返回true, 否则返回false
     */
    boolean isEffective(long storeId);

    /**
     * 根据定位查询附近店铺列表
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param distance  范围（km）
     * @return 店铺信息
     */
    List<TStoreInfo> queryNearStoreList(BigDecimal longitude, BigDecimal latitude, int distance);


    /**
     * 查看店铺的审核状态
     *
     * @param customer 当前登录的会员信息
     * @return 返回店铺的审核状态
     */
    StoreReview queryStoreReview(UmsMember customer);
}

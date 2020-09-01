package com.ruoyi.store.mapper;

import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.vo.NewStoreInfoStatistics;
import com.ruoyi.store.vo.OnSaleStoreQueryParam;
import com.ruoyi.store.vo.StoreInfoAreaStatistics;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 店铺信息Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public interface TStoreInfoMapper {
    /**
     * 根据店铺id查询店铺信息
     *
     * @param storeId 店铺id
     * @return 店铺信息
     */

    TStoreInfo queryStoreInfo(long storeId);

    /**
     * 根据店铺id查询店铺审核通过的信息
     *
     * @param storeId 店铺id
     * @return 店铺审核通过的信息
     */

    TStoreInfo queryAuditPassStoreInfo(long storeId);

    /**
     * 开店-添加店铺信息(第一步)
     *
     * @param storeInfo 店铺信息实体类
     * @return 添加返回码
     */

    int addStoreInfo(TStoreInfo storeInfo);

    /**
     * 开店-编辑店铺信息(第二步)
     *
     * @param storeInfo 店铺信息实体类
     * @return 编辑返回码
     */

    int editStoreInfo(TStoreInfo storeInfo);

    /**
     * 编辑店铺名称
     *
     * @param storeInfo 店铺信息实体类
     * @return 编辑返回码
     */

    int editStoreName(TStoreInfo storeInfo);

    /**
     * 根据店铺名称查询店铺
     *
     * @param storeName 店铺名称
     * @return 店铺信息实体类
     */

    TStoreInfo queryStoreInfoByName(String storeName);

    /**
     * 查询已审核/未审核商家个数
     *
     * @param map 查询参数
     * @return 个数
     */

    int queryStoreInfoForAuditListCount(Map<String, Object> map);

    /**
     * 查询已审核/未审核商家集合
     *
     * @param map 查询参数
     * @return 已审核/未审核商家集合
     */

    List<TStoreInfo> queryStoreInfoForAuditList(Map<String, Object> map);

    /**
     * 编辑店铺有效期,结算周期,是否关店
     *
     * @param storeInfo 店铺信息
     * @return 编辑返回码
     */

    int editStoreTimeAndIsClose(TStoreInfo storeInfo);

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
     * 编辑店铺信息-客服QQ
     *
     * @param storeInfo 店铺信息实体类
     * @return 编辑返回码
     */

    int editStoreInfoForServiceQQ(TStoreInfo storeInfo);

    /**
     * 编辑店铺信息-公司信息
     *
     * @param storeInfo 店铺信息实体类
     * @return 编辑返回码
     */

    int editStoreInfoForCompanyInfo(TStoreInfo storeInfo);

    /**
     * 编辑店铺信息-银行信息
     *
     * @param storeInfo 店铺信息实体类
     * @return 编辑返回码
     */

    int editStoreInfoForBankInfo(TStoreInfo storeInfo);

    /**
     * 根据条件搜索店铺
     *
     * @param params 搜索条件：排序条件，关键字
     * @return 店铺集合
     */

    List<TStoreInfo> queryStoreInfoForSearch(Map params);

    /**
     * 根据条件搜索店铺数量
     *
     * @param params 搜索条件：排序条件，关键字
     * @return 数量
     */

    int queryStoreInfoForSearchCount(Map params);

    /**
     * 统计新增店铺数量（按日期分组）
     *
     * @param params 查询参数
     * @return 返回按日期分组的新增店铺数量
     */

    List<NewStoreInfoStatistics> queryNewStoreInfoStatistics(Map<String, Object> params);

    /**
     * 统计新增店铺数量（按日期分组）总组数
     *
     * @param params 查询参数
     * @return 返回总组数
     */

    int queryNewStoreInfoStatisticsCount(Map<String, Object> params);

    /**
     * 分页统计新增店铺数量（按日期分组）
     *
     * @param params 查询参数
     * @return 返回按日期分组的新增店铺数量（带分页）
     */

    List<NewStoreInfoStatistics> queryNewStoreInfoStatisticsWithPage(Map<String, Object> params);

    /**
     * 统计店铺地区数量（按省级日期分组）
     *
     * @param params 查询参数
     * @return 返回店铺地区数量统计
     */

    List<StoreInfoAreaStatistics> queryStoreInfoAreaStatistics(Map<String, Object> params);

    /**
     * 批量关闭店铺
     *
     * @param ids 店铺id集合
     * @return >0成功，否则失败
     */

    int closeStores(List<Long> ids);

    /**
     * 开启店铺(门店用)
     *
     * @param storeId 店铺id
     * @return 1成功，否则失败
     */

    int openStoreForOutLetStore(@Param("storeId") long storeId);

    /**
     * 根据店铺名查询店铺数量
     *
     * @param params 查询参数
     * @return 店铺数量
     */

    int queryStoreCountByStoreName(Map<String, Object> params);

    /**
     * 根据公司名查询店铺数量
     *
     * @param params 查询参数
     * @return 店铺数量
     */

    int queryStoreCountByCompanyName(Map<String, Object> params);

    /**
     * 添加店铺
     *
     * @param storeInfo 店铺信息实体
     * @return 店铺id
     */

    long addStore(TStoreInfo storeInfo);

    /**
     * 查找一个在售门店
     *
     * @param skuId  单品id
     * @param cityId 市id
     * @return 门店信息
     */

    TStoreInfo queryOneOnSaleStore(@Param("skuId") String skuId, @Param("cityId") long cityId);

    /**
     * 查找在售门店
     *
     * @param skuId  单品id
     * @param cityId 市id
     * @return 门店信息集合
     */

    List<TStoreInfo> queryOnSaleStoreList(@Param("skuId") String skuId, @Param("cityId") long cityId);

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
     * @param distance  距离
     * @return 门店信息
     */

    List<TStoreInfo> queryNearByStoreList(@Param("longitude") BigDecimal longitude, @Param("latitude") BigDecimal latitude, @Param("distance") int distance);

    /**
     * 查询门店列表
     *
     * @param params 参数
     * @return 返回门店列表
     */

    List<TStoreInfo> queryStoreInfoList(Map<String, Object> params);

    /**
     * 查询门店列表总数
     *
     * @param params 参数
     * @return 返回门店列表总数
     */

    int queryStoreInfoListCount(Map<String, Object> params);

    /**
     * 根据定位查询附近店铺列表
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param distance  距离
     * @return 店铺信息
     */

    List<TStoreInfo> queryNearStoreList(@Param("longitude") BigDecimal longitude, @Param("latitude") BigDecimal latitude, @Param("distance") int distance);

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
     * 删除店铺信息
     *
     * @param id 店铺信息ID
     * @return 结果
     */
    public int deleteTStoreInfoById(Long id);

    /**
     * 批量删除店铺信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTStoreInfoByIds(Long[] ids);
}

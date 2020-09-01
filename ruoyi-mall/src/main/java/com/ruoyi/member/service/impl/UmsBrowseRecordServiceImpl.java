package com.ruoyi.member.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.PmsSkuItem;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.member.domain.UmsBrowseRecord;
import com.ruoyi.member.mapper.UmsBrowseRecordMapper;
import com.ruoyi.member.service.IUmsBrowseRecordService;
import com.ruoyi.member.vo.BrowseRecordList;
import com.ruoyi.util.PageHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 会员浏览记录Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
@Service
public class UmsBrowseRecordServiceImpl implements IUmsBrowseRecordService {
    @Autowired
    private UmsBrowseRecordMapper umsBrowseRecordMapper;
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(UmsBrowseRecordServiceImpl.class);


    /**
     * 自动注入浏览历史数据库接口
     */
    @Autowired
    private UmsBrowseRecordMapper browseRecordMapper;

    /**
     * 注入单品服务
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 查询浏览历史列表
     *
     * @param customerId 会员id
     * @return 浏览历史列表
     */
    @Override
    public List<BrowseRecordList> queryBrowseRecord(long customerId) {
        logger.debug("queryBrowseRecord and customerId :{}", customerId);
        List<BrowseRecordList> browseRecordLists = new ArrayList<>();
        List<UmsBrowseRecord> list = browseRecordMapper.queryBrowseRecord(customerId);
        if (CollectionUtils.isEmpty(list)) {
            return browseRecordLists;
        }
        list.forEach(this::setSkuDetail);
        list.stream().collect(Collectors.groupingBy(UmsBrowseRecord::getGroupByTime)).forEach((key, value) -> {
            BrowseRecordList browseRecordList = new BrowseRecordList();
            browseRecordList.setTime(key);
            browseRecordList.setBrowseRecordList(value);
            browseRecordLists.add(browseRecordList);
        });
        Collections.sort(browseRecordLists);
        return browseRecordLists;
    }

    /**
     * 按天删除浏览历史
     *
     * @param customerId 会员id
     * @param createTime 日期
     * @return 成功返回>=1，失败返回0
     */
    @Override
    public int deleteBrowseRecordByDay(long customerId, String createTime) {
        logger.debug("deleteBrowseRecordByDay and customerId \r\n and createTime :{}", customerId, createTime);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("createTime", createTime);
        return browseRecordMapper.deleteBrowseRecordByDay(params);
    }

    /**
     * 根据id删除浏览历史
     *
     * @param id 浏览历史id
     * @return 成功返回1，失败返回0
     */
    @Override
    public int deleteBrowseRecordById(long id) {
        logger.debug("deleteBrowseRecordById and id :{}", id);
        return browseRecordMapper.deleteBrowseRecordById(id);
    }

    @Override
    public int addBrowseRecord(UmsBrowseRecord browseRecord) {

        logger.debug("addBrowseRecord and browseRecord:{}", browseRecord);

        if (Objects.isNull(browseRecord)) {
            logger.error("addBrowseRecord fail due to params is null...");
            return 0;
        }

        // 如果浏览记录已经存在则直接返回成功
        if (hasRecord(browseRecord)) {
            logger.warn("browseRecord has exist: browseRecord:{}", browseRecord);
            return 1;
        }

        return browseRecordMapper.addBrowseRecord(browseRecord);
    }

    @Override
    public PageHelper<UmsBrowseRecord> queryBrowseRecords(PageHelper<UmsBrowseRecord> pageHelper, long customerId) {
        logger.debug("queryBrowseRecords and pageHelper:{} \r\n customerId:{}", pageHelper, customerId);

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);

        return pageHelper.setListDates(browseRecordMapper.queryBrowseRecords(pageHelper.getQueryParams(params, queryCustomerBrowseRecordCount(customerId)))
                .stream().peek(this::setSkuDetail).collect(Collectors.toList()));
    }

    @Override
    public int deleteByCustomerId(long customerId) {
        logger.debug("deleteByCustomerId and customerId:{}", customerId);
        return browseRecordMapper.deleteByCustomerId(customerId);
    }

    @Override
    public int queryCustomerBrowseRecordCount(long customerId) {
        logger.debug("queryCustomerBrowseRecordCount and customerId:{}", customerId);

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        return browseRecordMapper.queryBrowseRecordsCount(params);
    }

    @Override
    public Set<Long> queryBrowseSkuType(long customerId) {
        logger.debug("queryBrowseSkuType and customerId:{}", customerId);
        List<UmsBrowseRecord> browseRecords = browseRecordMapper.queryBrowseSkuType(customerId);
        return CollectionUtils.isEmpty(browseRecords) ? new HashSet<>() : browseRecords.stream().map(UmsBrowseRecord::getSkuTypeId).collect(Collectors.toSet());
    }

    /**
     * 判断是否有浏览记录
     *
     * @param browseRecord 浏览记录
     * @return 有返回true  没有返回false
     */
    private boolean hasRecord(UmsBrowseRecord browseRecord) {
        return browseRecordMapper.queryByCustomerIdAndSkuId(browseRecord) != 0;
    }

    /**
     * 设置单品详细信息
     *
     * @param browseRecord 浏览记录实体
     */
    private void setSkuDetail(UmsBrowseRecord browseRecord) {
        if (browseRecord.getSku()!=null){
            browseRecord.getSku().setId(browseRecord.getSkuId());
            skuService.setSkuDetail(browseRecord.getSku(), PmsSkuItem.BATCH);
        }
    }

    /**
     * 查询会员浏览记录
     *
     * @param id 会员浏览记录ID
     * @return 会员浏览记录
     */
    @Override
    public UmsBrowseRecord selectUmsBrowseRecordById(Long id) {
        return umsBrowseRecordMapper.selectUmsBrowseRecordById(id);
    }

    /**
     * 查询会员浏览记录列表
     *
     * @param umsBrowseRecord 会员浏览记录
     * @return 会员浏览记录
     */
    @Override
    public List<UmsBrowseRecord> selectUmsBrowseRecordList(UmsBrowseRecord umsBrowseRecord) {
        return umsBrowseRecordMapper.selectUmsBrowseRecordList(umsBrowseRecord);
    }

    /**
     * 新增会员浏览记录
     *
     * @param umsBrowseRecord 会员浏览记录
     * @return 结果
     */
    @Override
    public int insertUmsBrowseRecord(UmsBrowseRecord umsBrowseRecord) {
        umsBrowseRecord.setCreateTime(DateUtils.getNowDate());
        return umsBrowseRecordMapper.insertUmsBrowseRecord(umsBrowseRecord);
    }

    /**
     * 修改会员浏览记录
     *
     * @param umsBrowseRecord 会员浏览记录
     * @return 结果
     */
    @Override
    public int updateUmsBrowseRecord(UmsBrowseRecord umsBrowseRecord) {
        return umsBrowseRecordMapper.updateUmsBrowseRecord(umsBrowseRecord);
    }

    /**
     * 批量删除会员浏览记录
     *
     * @param ids 需要删除的会员浏览记录ID
     * @return 结果
     */
    @Override
    public int deleteUmsBrowseRecordByIds(Long[] ids) {
        return umsBrowseRecordMapper.deleteUmsBrowseRecordByIds(ids);
    }

    /**
     * 删除会员浏览记录信息
     *
     * @param id 会员浏览记录ID
     * @return 结果
     */
    @Override
    public int deleteUmsBrowseRecordById(Long id) {
        return umsBrowseRecordMapper.deleteUmsBrowseRecordById(id);
    }
}

package com.ruoyi.sms.service.impl;

import com.ruoyi.common.enums.StatusEnum;
import com.ruoyi.goods.mapper.PmsBrandMapper;
import com.ruoyi.sms.domain.SmsHomeBrand;
import com.ruoyi.sms.mapper.SmsHomeBrandMapper;
import com.ruoyi.sms.service.ISmsHomeBrandService;
import com.ruoyi.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页推荐品牌Service业务层处理
 *
 * @author é­éåå
 * @date 2020-08-06
 */
@Slf4j
@Service
public class SmsHomeBrandServiceImpl implements ISmsHomeBrandService {

    @Autowired
    private SmsHomeBrandMapper smsHomeBrandMapper;
    @Autowired
    private PmsBrandMapper brandMapper;
    /**
     * 查询首页推荐品牌
     *
     * @param id 首页推荐品牌ID
     * @return 首页推荐品牌
     */
    @Override
    public SmsHomeBrand selectSmsHomeBrandById(Long id) {
        return smsHomeBrandMapper.selectSmsHomeBrandById(id);
    }

    /**
     * 查询首页推荐品牌列表
     *
     * @param smsHomeBrand 首页推荐品牌
     * @return 首页推荐品牌
     */
    @Override
    public List<SmsHomeBrand> selectSmsHomeBrandList(SmsHomeBrand smsHomeBrand) {
        return smsHomeBrandMapper.selectSmsHomeBrandList(smsHomeBrand);
    }

    /**
     * 新增首页推荐品牌
     *
     * @param smsHomeBrand 首页推荐品牌
     * @return 结果
     */
    @Override
    public int insertSmsHomeBrand(SmsHomeBrand smsHomeBrand) {
        return smsHomeBrandMapper.insertSmsHomeBrand(smsHomeBrand);
    }

    /**
     * 修改首页推荐品牌
     *
     * @param smsHomeBrand 首页推荐品牌
     * @return 结果
     */
    @Override
    public int updateSmsHomeBrand(SmsHomeBrand smsHomeBrand) {
        return smsHomeBrandMapper.updateSmsHomeBrand(smsHomeBrand);
    }

    /**
     * 批量删除首页推荐品牌
     *
     * @param ids 需要删除的首页推荐品牌ID
     * @return 结果
     */
    @Override
    public int deleteSmsHomeBrandByIds(Long[] ids) {
        return smsHomeBrandMapper.deleteSmsHomeBrandByIds(ids);
    }

    /**
     * 删除首页推荐品牌信息
     *
     * @param id 首页推荐品牌ID
     * @return 结果
     */
    @Override
    public int deleteSmsHomeBrandById(Long id) {
        return smsHomeBrandMapper.deleteSmsHomeBrandById(id);
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        SmsHomeBrand homeBrand = new SmsHomeBrand();
        homeBrand.setId(id);
        homeBrand.setSort(sort);
        return smsHomeBrandMapper.updateSmsHomeBrand(homeBrand);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        SmsHomeBrand record = new SmsHomeBrand();
        record.setId(ids.get(0));
        record.setRecommendStatus(recommendStatus);
        return smsHomeBrandMapper.updateSmsHomeBrand(record);
    }


    @Override
    public int create(List<SmsHomeBrand> homeBrandList) {
        for (SmsHomeBrand smsHomeBrand : homeBrandList) {
            smsHomeBrand.setRecommendStatus(StatusEnum.YesNoType.YES.code());
            smsHomeBrand.setSort(0);
            smsHomeBrand.setStoreId(CommonConstant.ADMIN_STOREID);
            SmsHomeBrand query = new SmsHomeBrand();
            query.setBrandId(smsHomeBrand.getBrandId());
            List<SmsHomeBrand> list = smsHomeBrandMapper.selectSmsHomeBrandList(query);
            if (list!=null && list.size()>0){

            }else {
                smsHomeBrandMapper.insertSmsHomeBrand(smsHomeBrand);
            }
        }
        return homeBrandList.size();
    }
}

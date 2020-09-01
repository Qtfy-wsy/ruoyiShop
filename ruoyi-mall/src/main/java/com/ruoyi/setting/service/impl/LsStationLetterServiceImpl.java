package com.ruoyi.setting.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.setting.domain.LsStationLetter;
import com.ruoyi.setting.mapper.LsStationLetterMapper;
import com.ruoyi.setting.service.ILsStationLetterService;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 站内信Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-29
 */
@Service
public class LsStationLetterServiceImpl implements ILsStationLetterService {
    private static final Logger logger = LoggerFactory.getLogger(LsStationLetterServiceImpl.class);
    @Autowired
    private LsStationLetterMapper lsStationLetterMapper;

    /**
     * 查询站内信
     *
     * @param id 站内信ID
     * @return 站内信
     */
    @Override
    public LsStationLetter selectLsStationLetterById(Long id) {
        return lsStationLetterMapper.selectLsStationLetterById(id);
    }

    /**
     * 查询站内信列表
     *
     * @param lsStationLetter 站内信
     * @return 站内信
     */
    @Override
    public List<LsStationLetter> selectLsStationLetterList(LsStationLetter lsStationLetter) {
        return lsStationLetterMapper.selectLsStationLetterList(lsStationLetter);
    }

    /**
     * 新增站内信
     *
     * @param lsStationLetter 站内信
     * @return 结果
     */
    @Override
    public int insertLsStationLetter(LsStationLetter lsStationLetter) {
        lsStationLetter.setCreateTime(DateUtils.getNowDate());
        return lsStationLetterMapper.insertLsStationLetter(lsStationLetter);
    }

    /**
     * 修改站内信
     *
     * @param lsStationLetter 站内信
     * @return 结果
     */
    @Override
    public int updateLsStationLetter(LsStationLetter lsStationLetter) {
        return lsStationLetterMapper.updateLsStationLetter(lsStationLetter);
    }

    /**
     * 批量删除站内信
     *
     * @param ids 需要删除的站内信ID
     * @return 结果
     */
    @Override
    public int deleteLsStationLetterByIds(Long[] ids) {
        return lsStationLetterMapper.deleteLsStationLetterByIds(ids);
    }


    @Override
    public int addStationLetters(List<LsStationLetter> stationLetters) {
        logger.debug("addStationLetters and stationLetters:{}", stationLetters);

        if (CollectionUtils.isEmpty(stationLetters)) {
            logger.error("addStationLetters fail due to params is null...");
            return 0;
        }
        return lsStationLetterMapper.addStationLetters(stationLetters);
    }

    /**
     * 删除站内信
     *
     * @param id 站内信id
     * @return 成功返回1，失败返回0
     */
    @Override
    public int deleteStationLetters(long id) {
        logger.debug("deleteStationLetters and id :{}", id);
        return lsStationLetterMapper.deleteStationLetters(id);
    }

    /**
     * 更新会员的站内信的阅读状态
     *
     * @param id 站内信id
     * @return 成功返回1，失败返回0
     */
    @Override
    public int updateStationLettersIsRead(long id, long customerId) {
        logger.debug("updateStationLettersIsRead and id :{} \r\n customerId:{}", id, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("customerId", customerId);
        return lsStationLetterMapper.updateStationLettersIsRead(params);
    }

    @Override
    public PageHelper<LsStationLetter> queryStationLettersByCustomerId(PageHelper<LsStationLetter> pageHelper, long customerId) {
        logger.debug("queryStationLettersByCustomerId and customerId:{} and pageHelper:{}", customerId, pageHelper);
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        return pageHelper.setListDates(lsStationLetterMapper.queryStationLettersByCustomerId(pageHelper.getQueryParams(params, lsStationLetterMapper.queryStationLettersCountByCustomerId(params))));
    }

    @Override
    public int unReadNum(long customerId) {
        logger.debug("unReadNum and customerId:{}", customerId);
        return lsStationLetterMapper.unReadNum(customerId);
    }

    /**
     * 删除站内信信息
     *
     * @param id 站内信ID
     * @return 结果
     */
    @Override
    public int deleteLsStationLetterById(Long id) {
        return lsStationLetterMapper.deleteLsStationLetterById(id);
    }
}

package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsGoodsServiceSupport;
import com.ruoyi.goods.domain.SpuServiceSupportItem;
import com.ruoyi.goods.mapper.PmsGoodsServiceSupportMapper;
import com.ruoyi.goods.service.IPmsGoodsServiceSupportService;
import com.ruoyi.goods.service.IPmsServiceSupportService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 商品和服务支持的关联Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsGoodsServiceSupportServiceImpl implements IPmsGoodsServiceSupportService {
    @Autowired
    private PmsGoodsServiceSupportMapper pmsGoodsServiceSupportMapper;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsGoodsServiceSupportServiceImpl.class);

    /**
     * 商品服务支持
     */
    @Autowired
    private PmsGoodsServiceSupportMapper spuServicceSupportServiceMapper;

    /**
     * 注入服务支持
     */
    @Autowired
    private IPmsServiceSupportService serviceSupportService;

    @Override
    public void addSpuServicceSupport(List<PmsGoodsServiceSupport> spuServiceSupports) {

        logger.debug("addSpuServicceSupport and spuServiceSupports:{}", spuServiceSupports);

        if (CollectionUtils.isEmpty(spuServiceSupports)) {
            logger.warn("do not need to addSpuServicceSupport");
            return;
        }

        spuServicceSupportServiceMapper.addSpuServicceSupportServices(spuServiceSupports);
    }

    @Override
    public List<PmsGoodsServiceSupport> queryBySpuId(long spuId, SpuServiceSupportItem... spuServiceSupportItems) {
        logger.debug("queryBySpuId and spuId:{} and spuServiceSupportItems:{}", spuId, spuServiceSupportItems);

        List<PmsGoodsServiceSupport> spuServiceSupports = spuServicceSupportServiceMapper.queryBySpuId(spuId);

        if (CollectionUtils.isEmpty(spuServiceSupports)) {
            return spuServiceSupports;
        }

        // 查询服务支持
        if (ArrayUtils.contains(spuServiceSupportItems, SpuServiceSupportItem.SERVICE_SUPPORT)) {
            spuServiceSupports.stream().forEach(spuServiceSupport -> spuServiceSupport.setServiceSupport(serviceSupportService.queryServiceSupportById(spuServiceSupport.getServiceSupportId())));
        }


        return spuServiceSupports;
    }

    @Override
    public void deleteBySpuId(long spuId) {
        logger.debug("deleteBySpuId and spuId:{}", spuId);

        spuServicceSupportServiceMapper.deleteBySpuId(spuId);
    }

    @Override
    public void updateSpuServiceSupport(List<PmsGoodsServiceSupport> spuServiceSupports, long spuId) {
        logger.debug("updateSpuServiceSupport and spuServiceSupports:{} \r\n spuId:{}", spuServiceSupports, spuId);

        // 删除商品服务支持(物理)
        spuServicceSupportServiceMapper.deleteBySpuIdPhysical(spuId);

        if (CollectionUtils.isEmpty(spuServiceSupports)) {
            logger.warn("not need to updateSpuServiceSupport");
            return;
        }

        // 新增商品服务支持
        addSpuServicceSupport(spuServiceSupports);
    }

    /**
     * 查询商品和服务支持的关联
     *
     * @param id 商品和服务支持的关联ID
     * @return 商品和服务支持的关联
     */
    @Override
    public PmsGoodsServiceSupport selectPmsGoodsServiceSupportById(Long id) {
        return pmsGoodsServiceSupportMapper.selectPmsGoodsServiceSupportById(id);
    }

    /**
     * 查询商品和服务支持的关联列表
     *
     * @param pmsGoodsServiceSupport 商品和服务支持的关联
     * @return 商品和服务支持的关联
     */
    @Override
    public List<PmsGoodsServiceSupport> selectPmsGoodsServiceSupportList(PmsGoodsServiceSupport pmsGoodsServiceSupport) {
        return pmsGoodsServiceSupportMapper.selectPmsGoodsServiceSupportList(pmsGoodsServiceSupport);
    }

    /**
     * 新增商品和服务支持的关联
     *
     * @param pmsGoodsServiceSupport 商品和服务支持的关联
     * @return 结果
     */
    @Override
    public int insertPmsGoodsServiceSupport(PmsGoodsServiceSupport pmsGoodsServiceSupport) {
        return pmsGoodsServiceSupportMapper.insertPmsGoodsServiceSupport(pmsGoodsServiceSupport);
    }

    /**
     * 修改商品和服务支持的关联
     *
     * @param pmsGoodsServiceSupport 商品和服务支持的关联
     * @return 结果
     */
    @Override
    public int updatePmsGoodsServiceSupport(PmsGoodsServiceSupport pmsGoodsServiceSupport) {
        return pmsGoodsServiceSupportMapper.updatePmsGoodsServiceSupport(pmsGoodsServiceSupport);
    }

    /**
     * 批量删除商品和服务支持的关联
     *
     * @param ids 需要删除的商品和服务支持的关联ID
     * @return 结果
     */
    @Override
    public int deletePmsGoodsServiceSupportByIds(Long[] ids) {
        return pmsGoodsServiceSupportMapper.deletePmsGoodsServiceSupportByIds(ids);
    }

    /**
     * 删除商品和服务支持的关联信息
     *
     * @param id 商品和服务支持的关联ID
     * @return 结果
     */
    @Override
    public int deletePmsGoodsServiceSupportById(Long id) {
        return pmsGoodsServiceSupportMapper.deletePmsGoodsServiceSupportById(id);
    }
}

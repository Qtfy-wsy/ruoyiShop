package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsSkuImage;
import com.ruoyi.goods.mapper.PmsSkuImageMapper;
import com.ruoyi.goods.service.IPmsSkuImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 单品和图片的关联Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsSkuImageServiceImpl implements IPmsSkuImageService {
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsSkuImageServiceImpl.class);

    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    private PmsSkuImageMapper skuImageMapper;

    /**
     * 查询单品和图片的关联
     *
     * @param id 单品和图片的关联ID
     * @return 单品和图片的关联
     */
    @Override
    public PmsSkuImage selectPmsSkuImageById(Long id) {
        return pmsSkuImageMapper.selectPmsSkuImageById(id);
    }

    /**
     * 查询单品和图片的关联列表
     *
     * @param pmsSkuImage 单品和图片的关联
     * @return 单品和图片的关联
     */
    @Override
    public List<PmsSkuImage> selectPmsSkuImageList(PmsSkuImage pmsSkuImage) {
        return pmsSkuImageMapper.selectPmsSkuImageList(pmsSkuImage);
    }

    /**
     * 新增单品和图片的关联
     *
     * @param pmsSkuImage 单品和图片的关联
     * @return 结果
     */
    @Override
    public int insertPmsSkuImage(PmsSkuImage pmsSkuImage) {
        return pmsSkuImageMapper.insertPmsSkuImage(pmsSkuImage);
    }

    /**
     * 修改单品和图片的关联
     *
     * @param pmsSkuImage 单品和图片的关联
     * @return 结果
     */
    @Override
    public int updatePmsSkuImage(PmsSkuImage pmsSkuImage) {
        return pmsSkuImageMapper.updatePmsSkuImage(pmsSkuImage);
    }

    /**
     * 批量删除单品和图片的关联
     *
     * @param ids 需要删除的单品和图片的关联ID
     * @return 结果
     */
    @Override
    public int deletePmsSkuImageByIds(Long[] ids) {
        return pmsSkuImageMapper.deletePmsSkuImageByIds(ids);
    }

    /**
     * 删除单品和图片的关联信息
     *
     * @param id 单品和图片的关联ID
     * @return 结果
     */
    @Override
    public int deletePmsSkuImageById(Long id) {
        return pmsSkuImageMapper.deletePmsSkuImageById(id);
    }

    @Override
    public void addSkuImages(List<PmsSkuImage> skuImages) {
        logger.debug("addSkuImages and skuImages:{}", skuImages);

        if (CollectionUtils.isEmpty(skuImages)) {
            logger.warn("not need to addSkuImages due to skuImages is empty....");
            return;
        }

        skuImageMapper.addSkuImages(skuImages);
    }

    @Override
    public List<PmsSkuImage> queryBySkuId(String skuId) {
        logger.debug("queryBySkuId and skuId:{}", skuId);
        return skuImageMapper.queryBySkuId(skuId);
    }

    @Override
    public void deleteBySpuId(long spuId) {
        logger.debug("deleteBySpuId and spuId:{}", spuId);
        skuImageMapper.deleteBySpuId(spuId);
    }

    @Override
    public void deleteBySpuIdPhysical(long spuId) {
        logger.debug("deleteBySpuIdPhysical and spuId:{}", spuId);

        skuImageMapper.deleteBySpuIdPhysical(spuId);
    }
}

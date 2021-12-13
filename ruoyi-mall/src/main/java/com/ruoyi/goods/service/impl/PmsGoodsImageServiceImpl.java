package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsGoodsImage;
import com.ruoyi.goods.mapper.PmsGoodsImageMapper;
import com.ruoyi.goods.service.IPmsGoodsImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 商品的图片Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsGoodsImageServiceImpl implements IPmsGoodsImageService {
    @Autowired
    private PmsGoodsImageMapper pmsGoodsImageMapper;
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsGoodsImageServiceImpl.class);

    /**
     * 注入商品图片
     */
    @Autowired
    private PmsGoodsImageMapper spuImageMapper;

    @Override
    public void addSpuImages(List<PmsGoodsImage> spuImages) {
        logger.debug("addSpuImages and spuImages:{}", spuImages);

        if (CollectionUtils.isEmpty(spuImages)) {
            logger.warn("addSpuImages not need....");
            return;
        }

        spuImageMapper.addSpuImages(spuImages);
    }

    @Override
    public void deleteBySpuId(long spuId) {
        logger.debug("deleteBySpuId and spuId:{}", spuId);
        spuImageMapper.deleteBySpuId(spuId);
    }

    @Override
    public List<PmsGoodsImage> queryBySpuId(long spuId) {
        logger.debug("queryBySpuId and spuId:{}", spuId);
        return spuImageMapper.queryBySpuId(spuId);
    }

    @Override
    public void updateSpuImages(List<PmsGoodsImage> spuImages, long spuId) {
        logger.debug("updateSpuImages and spuImages:{} \r\n spuId:{}", spuImages, spuId);

        // 删除图片和商品的关联关系(物理)
        spuImageMapper.deleteBySpuIdPhysical(spuId);

        if (CollectionUtils.isEmpty(spuImages)) {
            logger.warn("not need to updateSpuImages");
            return;
        }

        // 新增
        addSpuImages(spuImages);
    }

    /**
     * 查询商品的图片
     *
     * @param id 商品的图片ID
     * @return 商品的图片
     */
    @Override
    public PmsGoodsImage selectPmsGoodsImageById(Long id) {
        return pmsGoodsImageMapper.selectPmsGoodsImageById(id);
    }

    /**
     * 查询商品的图片列表
     *
     * @param pmsGoodsImage 商品的图片
     * @return 商品的图片
     */
    @Override
    public List<PmsGoodsImage> selectPmsGoodsImageList(PmsGoodsImage pmsGoodsImage) {
        return pmsGoodsImageMapper.selectPmsGoodsImageList(pmsGoodsImage);
    }

    /**
     * 新增商品的图片
     *
     * @param pmsGoodsImage 商品的图片
     * @return 结果
     */
    @Override
    public int insertPmsGoodsImage(PmsGoodsImage pmsGoodsImage) {
        return pmsGoodsImageMapper.insertPmsGoodsImage(pmsGoodsImage);
    }

    /**
     * 修改商品的图片
     *
     * @param pmsGoodsImage 商品的图片
     * @return 结果
     */
    @Override
    public int updatePmsGoodsImage(PmsGoodsImage pmsGoodsImage) {
        return pmsGoodsImageMapper.updatePmsGoodsImage(pmsGoodsImage);
    }

    /**
     * 批量删除商品的图片
     *
     * @param ids 需要删除的商品的图片ID
     * @return 结果
     */
    @Override
    public int deletePmsGoodsImageByIds(Long[] ids) {
        return pmsGoodsImageMapper.deletePmsGoodsImageByIds(ids);
    }

    /**
     * 删除商品的图片信息
     *
     * @param id 商品的图片ID
     * @return 结果
     */
    @Override
    public int deletePmsGoodsImageById(Long id) {
        return pmsGoodsImageMapper.deletePmsGoodsImageById(id);
    }
}

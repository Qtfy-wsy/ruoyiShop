package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsSkuImage;

import java.util.List;

/**
 * 单品和图片的关联Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IPmsSkuImageService {
    /**
     * 查询单品和图片的关联
     *
     * @param id 单品和图片的关联ID
     * @return 单品和图片的关联
     */
    public PmsSkuImage selectPmsSkuImageById(Long id);

    /**
     * 查询单品和图片的关联列表
     *
     * @param pmsSkuImage 单品和图片的关联
     * @return 单品和图片的关联集合
     */
    public List<PmsSkuImage> selectPmsSkuImageList(PmsSkuImage pmsSkuImage);

    /**
     * 新增单品和图片的关联
     *
     * @param pmsSkuImage 单品和图片的关联
     * @return 结果
     */
    public int insertPmsSkuImage(PmsSkuImage pmsSkuImage);

    /**
     * 修改单品和图片的关联
     *
     * @param pmsSkuImage 单品和图片的关联
     * @return 结果
     */
    public int updatePmsSkuImage(PmsSkuImage pmsSkuImage);

    /**
     * 批量删除单品和图片的关联
     *
     * @param ids 需要删除的单品和图片的关联ID
     * @return 结果
     */
    public int deletePmsSkuImageByIds(Long[] ids);

    /**
     * 删除单品和图片的关联信息
     *
     * @param id 单品和图片的关联ID
     * @return 结果
     */
    public int deletePmsSkuImageById(Long id);

    /**
     * 新增单品图片
     *
     * @param skuImages 单品图片
     */
    void addSkuImages(List<PmsSkuImage> skuImages);

    /**
     * 根据单品id查询单品图片
     *
     * @param skuId 单品id
     * @return 返回单品的图片
     */
    List<PmsSkuImage> queryBySkuId(String skuId);

    /**
     * 根据商品id删除单品图片
     *
     * @param spuId 商品id
     */
    void deleteBySpuId(long spuId);

    /**
     * 根据商品id删除单品图片(物理删除)
     *
     * @param spuId 商品id
     */
    void deleteBySpuIdPhysical(long spuId);
}

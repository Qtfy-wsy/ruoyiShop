package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsCommentPicture;

import java.util.List;

/**
 * 单品评论下的图片Service接口
 *
 * @author 商城
 */
public interface IPmsCommentPictureService {
    /**
     * 查询单品评论下的图片
     *
     * @param id 单品评论下的图片ID
     * @return 单品评论下的图片
     */
    public PmsCommentPicture selectPmsCommentPictureById(Long id);

    /**
     * 查询单品评论下的图片列表
     *
     * @param pmsCommentPicture 单品评论下的图片
     * @return 单品评论下的图片集合
     */
    public List<PmsCommentPicture> selectPmsCommentPictureList(PmsCommentPicture pmsCommentPicture);

    /**
     * 新增单品评论下的图片
     *
     * @param pmsCommentPicture 单品评论下的图片
     * @return 结果
     */
    public int insertPmsCommentPicture(PmsCommentPicture pmsCommentPicture);

    /**
     * 修改单品评论下的图片
     *
     * @param pmsCommentPicture 单品评论下的图片
     * @return 结果
     */
    public int updatePmsCommentPicture(PmsCommentPicture pmsCommentPicture);

    /**
     * 批量删除单品评论下的图片
     *
     * @param ids 需要删除的单品评论下的图片ID
     * @return 结果
     */
    public int deletePmsCommentPictureByIds(Long[] ids);

    /**
     * 删除单品评论下的图片信息
     *
     * @param id 单品评论下的图片ID
     * @return 结果
     */
    public int deletePmsCommentPictureById(Long id);
}

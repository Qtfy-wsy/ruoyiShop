package com.ruoyi.goods.service.impl;

import com.ruoyi.goods.domain.PmsCommentPicture;
import com.ruoyi.goods.mapper.PmsCommentPictureMapper;
import com.ruoyi.goods.service.IPmsCommentPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 单品评论下的图片Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class PmsCommentPictureServiceImpl implements IPmsCommentPictureService {
    @Autowired
    private PmsCommentPictureMapper pmsCommentPictureMapper;

    /**
     * 查询单品评论下的图片
     *
     * @param id 单品评论下的图片ID
     * @return 单品评论下的图片
     */
    @Override
    public PmsCommentPicture selectPmsCommentPictureById(Long id) {
        return pmsCommentPictureMapper.selectPmsCommentPictureById(id);
    }

    /**
     * 查询单品评论下的图片列表
     *
     * @param pmsCommentPicture 单品评论下的图片
     * @return 单品评论下的图片
     */
    @Override
    public List<PmsCommentPicture> selectPmsCommentPictureList(PmsCommentPicture pmsCommentPicture) {
        return pmsCommentPictureMapper.selectPmsCommentPictureList(pmsCommentPicture);
    }

    /**
     * 新增单品评论下的图片
     *
     * @param pmsCommentPicture 单品评论下的图片
     * @return 结果
     */
    @Override
    public int insertPmsCommentPicture(PmsCommentPicture pmsCommentPicture) {
        return pmsCommentPictureMapper.insertPmsCommentPicture(pmsCommentPicture);
    }

    /**
     * 修改单品评论下的图片
     *
     * @param pmsCommentPicture 单品评论下的图片
     * @return 结果
     */
    @Override
    public int updatePmsCommentPicture(PmsCommentPicture pmsCommentPicture) {
        return pmsCommentPictureMapper.updatePmsCommentPicture(pmsCommentPicture);
    }

    /**
     * 批量删除单品评论下的图片
     *
     * @param ids 需要删除的单品评论下的图片ID
     * @return 结果
     */
    @Override
    public int deletePmsCommentPictureByIds(Long[] ids) {
        return pmsCommentPictureMapper.deletePmsCommentPictureByIds(ids);
    }

    /**
     * 删除单品评论下的图片信息
     *
     * @param id 单品评论下的图片ID
     * @return 结果
     */
    @Override
    public int deletePmsCommentPictureById(Long id) {
        return pmsCommentPictureMapper.deletePmsCommentPictureById(id);
    }
}

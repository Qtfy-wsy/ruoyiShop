package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsCommentReplay;

import java.util.List;

/**
 * 评论回复Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface IPmsCommentReplayService {
    /**
     * 查询评论回复
     *
     * @param id 评论回复ID
     * @return 评论回复
     */
    public PmsCommentReplay selectPmsCommentReplayById(Long id);

    /**
     * 查询评论回复列表
     *
     * @param pmsCommentReplay 评论回复
     * @return 评论回复集合
     */
    public List<PmsCommentReplay> selectPmsCommentReplayList(PmsCommentReplay pmsCommentReplay);

    /**
     * 新增评论回复
     *
     * @param pmsCommentReplay 评论回复
     * @return 结果
     */
    public int insertPmsCommentReplay(PmsCommentReplay pmsCommentReplay);

    /**
     * 修改评论回复
     *
     * @param pmsCommentReplay 评论回复
     * @return 结果
     */
    public int updatePmsCommentReplay(PmsCommentReplay pmsCommentReplay);

    /**
     * 批量删除评论回复
     *
     * @param ids 需要删除的评论回复ID
     * @return 结果
     */
    public int deletePmsCommentReplayByIds(Long[] ids);

    /**
     * 删除评论回复信息
     *
     * @param id 评论回复ID
     * @return 结果
     */
    public int deletePmsCommentReplayById(Long id);
}

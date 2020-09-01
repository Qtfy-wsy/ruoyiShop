package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsCommentReplay;

import java.util.List;
import java.util.Map;

/**
 * 评论回复Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsCommentReplayMapper {
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
     * 更改是否显示
     *
     * @param map 店铺id,评论id
     */
    int updateIsShow(Map map);

    /**
     * 查看回复
     */
    List<PmsCommentReplay> queryCommentReply(Map map);

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
     * 删除评论回复
     *
     * @param id 评论回复ID
     * @return 结果
     */
    public int deletePmsCommentReplayById(Long id);

    /**
     * 批量删除评论回复
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsCommentReplayByIds(Long[] ids);
}

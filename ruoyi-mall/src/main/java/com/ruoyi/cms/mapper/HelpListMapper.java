package com.ruoyi.cms.mapper;

import com.ruoyi.cms.bean.HelpList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 帮助列表数据库接口
 *
 * Created by 魔金商城 on 2017/5/27.
 */
@Repository
public interface HelpListMapper {

    /**
     * 分页查询帮助列表
     *
     * @param params 查询参数
     * @return       返回帮助列表
     */

    List<HelpList> queryHelpList(Map<String, Object> params);

    /**
     * 查询帮助列表的总记录数
     *
     * @param parmas 查询参数
     * @return       返回总记录数
     */

    int queryHelpListCount(Map<String, Object> parmas);

    /**
     * 通过id查找帮助
     *
     * @param id 帮助id
     * @return   帮助
     */

    HelpList queryHelpById(long id);

    /**
     * 添加帮助
     *
     * @param helpList 帮助
     * @return         成功返回1，失败返回0
     */

    int addHelp(HelpList helpList);

    /**
     * 删除帮助
     *
     * @param id 帮助id
     * @return   成功返回1  失败返回0
     */

    int deleteHelp(long id);

    /**
     * 批量删除
     *
     * @param ids 帮助id数组
     * @return    成功返回1  失败返回0
     */

    int batchDeleteHelp(long [] ids);

    /**
     * 修改帮助
     *
     * @param helpList  帮助
     * @return         成功返回1，失败返回0
     */

    int updateHelp(HelpList helpList);

    /**
     * 查找所有服务
     *
     * @return 返回服务集合
     */

    List<HelpList> queryHelp();

    /**
     * 根据分类id查找帮助
     *
     * @param id 分类id
     * @return   帮助
     */

    List<HelpList> queryHelpByCateId(long id);
}

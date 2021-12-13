package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsSpec;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 规格Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface IPmsSpecService {
    /**
     * 新增规格
     *
     * @param spec 规格信息
     * @return 成功返回 1 失败返回0
     */
    int addSpec(PmsSpec spec);

    /**
     * 修改规格信息
     *
     * @param spec
     * @return 成功返回1  失败返回0 -1 商品已经使用规格不能更新
     */
    int updateSpec(PmsSpec spec);

    /**
     * 分页查询规格信息
     *
     * @param pageHelper 分页帮助类
     * @param name       条件查询名称
     * @return 返回规格信息
     */
    PageHelper<PmsSpec> querySpecs(PageHelper<PmsSpec> pageHelper, String name);

    /**
     * 根据规格id查询规格信息
     *
     * @param id 规格id
     * @return 返回规格信息(包含规格值)
     */
    PmsSpec querySpecById(long id);

    /**
     * 根据规格id查询规格信息
     *
     * @param id 规格id
     * @return 返回规格信息 只有规格信息
     */
    PmsSpec querySpecByIdSimple(long id);

    /**
     * 删除规格
     *
     * @param spec 规格
     * @return 成功返回1  失败返回0 -1 商品已经使用规格不能删除
     */
    int deleteSpec(PmsSpec spec);

    /**
     * 批量删除规格
     *
     * @param specs 规格信息
     * @return 成功返回1  失败返回0 -1 商品已经使用规格不能删除
     */
    int deleteSpecs(List<PmsSpec> specs);

    /**
     * 查询所有的规格信息
     *
     * @return 返回所有的规格信息
     */
    List<PmsSpec> queryAllSpec();

    /**
     * 根据规格id查询规格信息
     *
     * @param ids 规格id集合
     * @return 返回规格信息(包含规格值)
     */
    List<PmsSpec> querySpecsByIds(Long[] ids);

    /**
     * 查询商品的规格信息
     *
     * @param spuId 规格id
     * @return 返回商品的所有规格信息
     */
    List<PmsSpec> querySpuSpecs(long spuId);

    /**
     * 查询规格
     *
     * @param id 规格ID
     * @return 规格
     */
    public PmsSpec selectPmsSpecById(Long id);

    /**
     * 查询规格列表
     *
     * @param pmsSpec 规格
     * @return 规格集合
     */
    public List<PmsSpec> selectPmsSpecList(PmsSpec pmsSpec);

    /**
     * 新增规格
     *
     * @param pmsSpec 规格
     * @return 结果
     */
    public int insertPmsSpec(PmsSpec pmsSpec);

    /**
     * 修改规格
     *
     * @param pmsSpec 规格
     * @return 结果
     */
    public int updatePmsSpec(PmsSpec pmsSpec);

    /**
     * 批量删除规格
     *
     * @param ids 需要删除的规格ID
     * @return 结果
     */
    public int deletePmsSpecByIds(Long[] ids);

    /**
     * 删除规格信息
     *
     * @param id 规格ID
     * @return 结果
     */
    public int deletePmsSpecById(Long id);
}

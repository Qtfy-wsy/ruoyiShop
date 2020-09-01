package com.ruoyi.integral.service.impl;

import com.ruoyi.integral.domain.PointCate;
import com.ruoyi.integral.domain.PointSku;
import com.ruoyi.integral.mapper.PointSkuMapper;
import com.ruoyi.integral.service.PointCateService;
import com.ruoyi.integral.service.PointMallOrderService;
import com.ruoyi.integral.service.PointSkuService;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 积分商品服务实现类
 */
@Service
public class PointSkuServiceImpl implements PointSkuService {

    /**
     * 注入积分商品数据库接口
     */
    @Autowired
    private PointSkuMapper pointSkuMapper;

    /**
     * 注入积分商城分类服务接口
     */
    @Autowired
    private PointCateService pointCateService;

    /**
     * 注入积分商城订单服务
     */
    @Autowired
    private PointMallOrderService pointMallOrderService;


    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(PointSkuServiceImpl.class);

    @Override
    public int addPointSku(PointSku pointSku) {
        logger.debug("addPointSku and pointSku:{}", pointSku);
        if (StringUtils.isEmpty(pointSku.getCode())) {
            logger.error("addPointSku fail : no code");
            return -1;
        }
        if (!ObjectUtils.isEmpty(pointSkuMapper.queryPointSkuByCode(pointSku.getCode()))) {
            logger.error("addPointSku fail :  code already exist");
            return -2;
        }
        return pointSkuMapper.addPointSku(pointSku);
    }

    @Override
    public int updatePointSku(PointSku pointSku) {
        logger.debug("updatePointSku and pointSku:{}", pointSku);
        if (StringUtils.isEmpty(pointSku.getCode())) {
            logger.error("updatePointSku fail : no code");
            return -1;
        }
        PointSku old = pointSkuMapper.queryPointSkuById(pointSku.getId());
        if (!old.getCode().equals(pointSku.getCode())) {
            if (!ObjectUtils.isEmpty(pointSkuMapper.queryPointSkuByCode(pointSku.getCode()))) {
                logger.error("updatePointSku fail :  code already exist");
                return -2;
            }
        }
        return pointSkuMapper.updatePointSku(pointSku);
    }

    @Override
    public PageHelper<PointSku> queryPointSkus(PageHelper<PointSku> pointSkuPageHelper, Long cateId, String name, boolean isFilterStatus) {
        logger.debug("queryPointSkus and cateId:{}\r\n name:{} \r\n filterStatus:{}", cateId, name, isFilterStatus);
        Map<String, Object> params = new HashMap<>();
        params.put("cateId", cateId);
        if (isFilterStatus) {
            //需要过滤，只返回发布的积分商品
            params.put("status", "1");
        }
        params.put("name", name);
        return pointSkuPageHelper.setListDates(fillCateName(pointSkuMapper.queryPointSkus(pointSkuPageHelper.getQueryParams(params, pointSkuMapper.queryPointSkusCount(params)))));
    }

    @Override
    public PointSku queryPointSkuById(long id) {
        logger.debug("queryPointSkuById and id:{}", id);
        return pointSkuMapper.queryPointSkuById(id);
    }

    @Override
    public int deletePointSku(long id) {
        logger.debug("deletePointSku and id:{}", id);
        return pointSkuMapper.deletePointSku(id);
    }

    @Override
    public int deletePointSkus(Long... ids) {
        logger.debug("deletePointSkus and ids:{}", ids);
        if (ObjectUtils.isEmpty(ids)) {
            logger.error("deletePointSkus fail:ids is empty");
            return 0;
        }
        return pointSkuMapper.deletePointSkus(Arrays.asList(ids));
    }

    @Override
    public int reducePointSkuStock(long id, int num) {
        logger.debug("reducePointSkuStock and id:{}\r\n num:{}", id, num);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("num", num);
        return pointSkuMapper.reducePointSkuStock(params);
    }

    @Override
    public List<PointSku> queryHotPointSkus(int num) {
        logger.debug("queryHotPointSkus....");
        List<PointSku> hotPointSkus = new ArrayList<>();
        pointMallOrderService.queryHotPointSkusId().stream().forEach(pointMallOrder -> {
            PointSku pointSku = new PointSku();
            pointSku.setId(pointMallOrder.getSkuId());
            hotPointSkus.add(pointSku);
        });
        //如果查询出的数量小于需要的数量，直接返回
        if (hotPointSkus.size() <= num) {
            //查询积分商品信息
            return fillPointSkuDetail(hotPointSkus);
        } else {
            //否则随机抽取所需数量
            return fillPointSkuDetail(createRandomList(hotPointSkus, num));
        }
    }

    /**
     * 填充积分商品分类名
     *
     * @param pointSkus 积分商品集合
     * @return 积分商品集合
     */
    private List<PointSku> fillCateName(List<PointSku> pointSkus) {
        return pointSkus.stream().map(pointSku ->
        {
            PointCate pointCate = null;
            if (!ObjectUtils.isEmpty(pointSku.getCateId())) {
                pointCate = pointCateService.queryPointCateById(pointSku.getCateId());
            }
            if (!ObjectUtils.isEmpty(pointCate)) {
                return pointSku.fillCateName(pointCateService.queryPointCateById(pointSku.getCateId()).getName());
            }
            return pointSku;
        }).collect(Collectors.toList());
    }

    /**
     * 从list中随机抽取元素
     *
     * @param list 原始数据
     * @param num  抽取元素数量
     */
    private <T> List<T> createRandomList(List<T> list, int num) {
        List<T> listNew = new ArrayList();
        Collections.shuffle(list);
        for (int i = 0; i < num; i++) {
            listNew.add(list.get(i));
        }
        return listNew;
    }

    /**
     * 填充积分商品详细信息
     *
     * @param pointSkus 积分商品集合
     * @return 积分商品集合
     */
    private List<PointSku> fillPointSkuDetail(List<PointSku> pointSkus) {
        //查询积分商品信息
        return pointSkus.stream().map(hotPointSku -> {
            PointSku pointSku = pointSkuMapper.queryPointSkuById(hotPointSku.getId());
            //判断商品是否存在和是否上架
            if (!ObjectUtils.isEmpty(pointSku) && pointSku.isOnSale()) {
                BeanUtils.copyProperties(pointSku, hotPointSku);
            }
            return hotPointSku;
        }).collect(Collectors.toList()).stream().filter(PointSku::hasDetailInfo).collect(Collectors.toList());

    }

}

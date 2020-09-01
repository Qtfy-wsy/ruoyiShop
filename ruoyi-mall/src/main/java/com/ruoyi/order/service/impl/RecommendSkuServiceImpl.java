package com.ruoyi.order.service.impl;


import com.ruoyi.goods.domain.PmsSkuItem;
import com.ruoyi.goods.service.IPmsCommentService;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.order.domain.OmsOrderSku;
import com.ruoyi.order.service.IOmsOrderSkuService;
import com.ruoyi.order.service.RecommendSkuService;
import com.ruoyi.order.vo.RecommendSku;
import com.ruoyi.store.service.ITStoreOrderSkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 推荐单品服务实现类
 */
@Service
public class RecommendSkuServiceImpl implements RecommendSkuService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(RecommendSkuServiceImpl.class);

    /**
     * 注入单品服务
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 注入订单单品服务
     */
    @Autowired
    private IOmsOrderSkuService orderSkuService;

    /**
     * 注入回复服务
     */
    @Autowired
    private IPmsCommentService commentService;

    /**
     * 注入门店订单单品服务接口
     */
    @Autowired
    private ITStoreOrderSkuService storeOrderSkuService;

    @Override
    public List<RecommendSku> queryRecommendSkus(int num) {
        logger.debug("queryRecommentSkus and num:{}", num);
        List<RecommendSku> recommendSkus = new ArrayList<>();
        //查询推荐商品
        List<OmsOrderSku> orderSkuList = orderSkuService.queryRecommentSkus(num + 5);
        if (CollectionUtils.isEmpty(orderSkuList)) {
            return Collections.emptyList();
        }
        orderSkuList.stream().forEach(orderSku -> {
            RecommendSku recommendSku = new RecommendSku();
            BeanUtils.copyProperties(orderSku, recommendSku);
            recommendSkus.add(recommendSku);
        });
        if (CollectionUtils.isEmpty(recommendSkus)) {
            return Collections.emptyList();
        }
        // 过滤删除和下架的单品
        List<RecommendSku> tempRecommendSkus = recommendSkus.stream().map(recommendSku ->
                recommendSku.setSkuDetail(skuService.querySkuById(recommendSku.getSkuId()))
        ).collect(Collectors.toList()).stream().filter(RecommendSku::hasOnSaleSku).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(tempRecommendSkus)) {
            return Collections.emptyList();
        }
        //如果查询出的数量小于需要的数量，直接返回
        if (tempRecommendSkus.size() <= num) {
            logger.info("queryRecommendSkus and return recommendSkuList...");
            //查询单品信息及评论数
            return buildSkuDetailAndCommentCount(tempRecommendSkus);
        } else {
            logger.info("queryRecommendSkus and random return recommendSkuList...");
            //否则随机抽取所需数量
            return buildSkuDetailAndCommentCount(createRandomList(tempRecommendSkus, num));
        }
    }

    /**
     * 查询店铺30天内热销商品
     *
     * @param storeId 店铺id
     * @return 热销商品
     */
    @Override
    public List<RecommendSku> queryRecommentSkusThirtyDays(long storeId) {
        logger.debug("queryRecommentSkusThirtyDays and storeId :{}", storeId);
        List<RecommendSku> recommendSkuList = new ArrayList<>();
        //查询店铺30天内热销商品
        orderSkuService.queryRecommentSkusThirtyDays(storeId).forEach(orderSku -> {
            RecommendSku recommendSku = new RecommendSku();
            BeanUtils.copyProperties(orderSku, recommendSku);
            recommendSkuList.add(recommendSku);
        });
        recommendSkuList.stream().forEach(recommendSku -> recommendSku.setSkuDetail(skuService.querySkuById(recommendSku.getSkuId())));
        return recommendSkuList;
    }

    /**
     * 查询门店30天内热销商品
     *
     * @param storeId 店铺id
     * @return 热销商品
     */
    @Override
    public List<RecommendSku> queryStoreRecommentSkusThirtyDays(long storeId) {
        logger.debug("queryStoreRecommentSkusThirtyDays and storeId :{}", storeId);
        List<RecommendSku> recommendSkuList = new ArrayList<>();
        //查询门店30天内热销商品
        storeOrderSkuService.queryStoreRecommentSkusThirtyDays(storeId).forEach(storeOrderSku -> {
            RecommendSku recommendSku = new RecommendSku();
            BeanUtils.copyProperties(storeOrderSku, recommendSku);
            recommendSkuList.add(recommendSku);
        });
        recommendSkuList.stream().forEach(recommendSku -> recommendSku.setSkuDetail(skuService.querySkuById(recommendSku.getSkuId())));
        return recommendSkuList;
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
     * 查询单品信息及评论数
     *
     * @param list 推荐商品集合
     * @return 推荐商品集合
     */
    private List<RecommendSku> buildSkuDetailAndCommentCount(List<RecommendSku> list) {
        //查询单品信息及评论数
        return list.stream().peek(recommendSku -> {
            recommendSku.setSkuDetail(skuService.setSkuDetail(recommendSku.getSku(), PmsSkuItem.BATCH));
            recommendSku.initCommentCount(commentService.queryCommentCountBySkuId(recommendSku.getSkuId()));
        }).collect(Collectors.toList());
    }

}

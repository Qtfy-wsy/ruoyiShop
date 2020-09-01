<template>
	<view class="product">
		<view class="rf-product-detail">
    		<!--顶部返回按钮-->
    		<!--#ifdef MP-WEIXIN-->
    		<text class="back-btn iconfont iconzuo" @tap="navBack"></text>
    		<!--#endif-->
    		<!--header-->
    		<view class="detail" v-if="productDetail.name">
    			<!--顶部商品轮播图-->
    			<view class="carousel">
    				<swiper indicator-dots circular="true" duration="400" controls touchable>

    					<swiper-item
    						class="swiper-item"
    					>
    						<view class="image-wrapper">
    							<image :src="productDetail.pics" class="loaded" mode="aspectFill"></image>
    						</view>
    						<uni-tag class="content" circle size="small"></uni-tag>
    					</swiper-item>
    				</swiper>
    			</view>
    			<!--商品信息-->
    			<view class="introduce-section">
    				<view class="introduce-first-line">
    					<view
    						class="price-box point-box"
    					>
    						该商品仅需
    						<text class="price">{{ productDetail.point }} 积分</text>
    					</view>


    				</view>
    				<view class="introduce-second-line">
    					<view class="title">
    						<text>{{ productDetail.name }}</text>
    						<text class="sketch">{{ productDetail.sketch }}</text>
    					</view>
    					<view class="share">
    						<rf-tag
    							type="gray"
    							size="small"
    							tui-tag-class="tui-tag-share tui-size"
    							shape="circleLeft"
    						>
    							<button class="share-btn" open-type="share" :class="'text-' + themeColor.name" @tap.stop="share()">
    								<text class="iconfont iconfenxiang"></text>
    								<text class="tui-share-text tui-gray">分享</text>
    							</button>
    						</rf-tag>
    					</view>
    				</view>
    				<view class="product-tag">
    					<uni-tag
    						@tap="openPoster"
    						class="tag"
    						circle
    						type="base"
    						text="生成海报"
    						size="small"
    					/>
    				</view>
    				<view class="data" v-if="productDetail">
    					<text class="item">快递: {{ productDetail.shipping_type === '1' ? '包邮' : '买家自付' }}</text>
    					<text class="item">月销 {{ productDetail.total_sales }}</text>
    					<text v-if="productDetail.address_name" class="item in1line">{{ productDetail.address_name }}</text>
    				</view>
    			</view>
    			<!--商品参数-->
    			<view class="c-list">
    				<!--商品库存-->
    				<rf-item-popup
    					title="商品库存"
    					v-if="parseInt(productDetail.is_stock_visible, 10) == 1"
    					:isEmpty="parseInt(currentStock, 10) === 0"
    					empty="库存不足"
    				>
    					<view slot="content">
    						{{ currentStock || productDetail.stock || 0 }} {{ productDetail.unit || '件' }}
    					</view>
    				</rf-item-popup>


    				<!--积分活动-->
    				<rf-item-popup title="积分活动">
    					<view slot="content" class="con-list">

    						<text v-if="parseInt(productDetail.give_point, 10) > 0">下单可获得: {{ product | givePointFilter }}积分</text>
    						<text v-if="productDetail.point != 0">兑换所需积分: {{ productDetail.point }}
    						</text>
    						<text v-if="productDetail.max_use_point != 0">可使用抵扣积分: {{ productDetail.max_use_point }}</text>
    						<text
    							class="buy-now"
    							@tap="addCart('buy', true)"

    							>积分兑换 >>
    						</text>
    					</view>
    				</rf-item-popup>

    			</view>

    			<!--底部商品详情-->
    			<view class="detail-desc">
    				<view class="d-header">
    					<text>商品详情</text>
    				</view>
    				<rf-parser :html="productDetail.mobileDesc" lazy-load></rf-parser>
    			</view>
    			<!-- 底部操作菜单 -->
    			<view class="page-bottom">
    				<view class="page-bottom-bth-wrapper">
    					<navigator
    						url="/pages/index/index"
    						open-type="switchTab"
    						class="p-b-btn"
    					>
    						<i class="iconfont iconzhuyedefuben"></i>
    						<text>首页</text>
    					</navigator>


    				</view>
    				<view
    					class="action-btn-group"
    					v-if="parseInt(this.currentStock || this.productDetail.stock, 10) > 0"
    				>
    					<button
    						class="action-btn"
    						:class="'bg-' + themeColor.name"
    						:disabled="buyBtnDisabled"
    						@tap="addCart(productDetail)"
    					>
    						积分兑换
    					</button>

    				</view>
    				<view class="action-btn">
    					<button
    						v-if="parseInt(this.currentStock || this.productDetail.stock, 10) === 0"
    						class="action-btn-submit"
    						:disabled="buyBtnDisabled"
    					>
    						库存不足
    					</button>
    				</view>
    			</view>
    		</view>

    		<!--回到顶部-->
    		<rf-live v-if="productDetail.name"></rf-live>
    		<!--#ifdef MP-->
        <rf-nav></rf-nav>
    		<!--#endif-->

    	</view>
    <!--回到顶部-->
		<rf-back-top :scrollTop="scrollTop"></rf-back-top>
		<!-- 404页面 -->
		<view v-if="!productDetail.name && !loading">
			<rf-no-data :custom="true">
				<view class="no-data-title">
					{{ errorInfo || '暂无数据' }}
				</view>
				<view
					@tap="getProductDetail(productDetail.id)"
					slot="refresh"
					class="spec-color"
					>重新加载</view
				>
			</rf-no-data>
		</view>
		<!--顶部下拉菜单-->
		<rf-nav-detail
			@hide="hideNavDetail"
			:popupShow="navDetailShow"
		>
		</rf-nav-detail>
		<!--页面加载动画-->
		<rfLoading isFullScreen :active="loading"></rfLoading></view>
</template>
<script>
/**
 * @des 商品详情
 *
 * @author stav stavyan@qq.com
 * @date 2020-03-23 15:04
 * @copyright 2019
 */

import { querypointskudetail } from '@/api/userInfo';
import { addbrowserecord } from '@/api/userInfo';
	import rfItemPopup from '@/components/rf-item-popup';
	import rfLive from '@/components/rf-live';
import rfProductDetail from '@/components/rf-product-detail';
import rfBackTop from '@/components/rf-back-top';
import rfNoData from '@/components/rf-no-data';
export default {
	components: {
		rfProductDetail,rfLive,rfItemPopup,
		rfBackTop,
		rfNoData
	},
	data() {
		return {
			productDetail: {},
			loading: true,
			currentStock:0,
			errorInfo: '',
			userInfo: {},
			scrollTop: 0,
			currentUrl: '',
			navDetailShow: false,
			appName: this.$mSettingConfig.appName
		};
	},
	// #ifndef MP
	onNavigationBarButtonTap(e) {
		const index = e.index;
		if (index === 0) {
			this.navDetailShow = true;
		}
	},
	// #endif
	onPageScroll(e) {
		this.scrollTop = e.scrollTop;
	},
	async onLoad(options) {
		this.productId = options.id;
		this.userInfo = uni.getStorageSync('userInfo') || {};
		await this.initData();
	},
	computed: {

        type() {
        console.log(this.product)
  				return 'buy_now';
        },
  			// 购买按钮禁用
  			buyBtnDisabled() {
  				return parseInt(this.currentStock) === 0;
  			},

  			// 最小购买数量
  			minNum() {
  				return 1;
  			},
  			// 最小购买数量
  			maxNum() {
  				let maxNum = 0;
  				return maxNum;
  			},



  		},
	methods: {


  			addCart(row) {
  				this.$mRouter.push({
            					route: `/pages/order/create/createJifenOrder?id=${row.id}&point=${row.point}`
            	});
  			},

		// 隐藏顶部导航
		hideNavDetail() {
			this.navDetailShow = false;
		},
		// 数据初始化
		async initData() {
      if (this.userInfo.promo_code) {
        this.currentUrl = `${this.$mConfig.hostUrl}/pages/product/product?id=${this.productId}&promo_code=${this.userInfo.promo_code}`;
      } else {
        this.currentUrl = `${this.$mConfig.hostUrl}/pages/product/product?id=${this.productId}`;
      }
			this.hasLogin = this.$mStore.getters.hasLogin;
			await this.getProductDetail();



		},
		// 获取产品详情
		async getProductDetail() {

			await this.$http
				.get(`${querypointskudetail}`, {
				 id: this.productId

				})
				.then(async r => {
					this.loading = false;
					this.productDetail = r;
		this.currentStock=r.stock
					uni.setNavigationBarTitle({ title: r.sku.name });
				//	await this.$mHelper.handleWxH5Share(this.appName, r.sku.name, this.currentUrl, r.picture);
				})
				.catch(err => {
					this.loading = false;
					this.errorInfo = err;
				});
		},
		async addbrowserecord(skuId) {

    			await this.$http
    				.get(`${addbrowserecord}`, {
    				 skuId: skuId

    				})
    				.then(async r => {

    				})
    				.catch(err => {
    					this.loading = false;
    					this.errorInfo = err;
    				});
    		}
	}
};
</script>
<style scoped lang="scss">
page {
	background: $page-color-base;
}
</style>

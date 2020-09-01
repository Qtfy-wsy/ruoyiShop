<template>
	<view class="order">
		<!--搜索导航栏-->
		<view class="order-search" @tap="navTo(`/pages/index/search/search?keyword=搜索我的商品&type=order`)">
			<view class="order-search-input">请输入商品关键字</view>
			<view class="iconfont iconsousuo2"></view>
		</view>
		<!--导航栏-->
		<view class="navbar">
			<view
				v-for="(item, index) in navList"
				:key="index"
				class="nav-item"
				:class="tabCurrentIndex === index ? `text-${themeColor.name} current` : ''"
				@tap="tabClick(index)"
			>
				{{ item.name }}
			</view>
		</view>
		<!--订单列表-->
		<swiper
			class="swiper-box"
			duration="300"
			@change="changeTab"
		>
			<swiper-item
				class="tab-content"
				v-for="(tabItem, tabIndex) in navList"
				:key="tabIndex"
			>
				<scroll-view
					class="list-scroll-content"
					scroll-y
					@scrolltolower="getMoreOrderList"
				>
					<view
						class="rf-order-item"
					>
						<view
							class="goods-box-single"
							@tap.stop="
								navTo(`/pages/product/product?id=${item.spuId}`)
							"
							v-for="(goodsItem, goodsIndex) in orderList"
							:key="goodsIndex"
						>
							<image
								class="goods-img"
								:src="goodsItem.url"
								mode="aspectFill"
							></image>
							<view class="right">
								<text class="title in2line">{{ goodsItem.name }}</text>
								<text class="attr-box">{{ goodsItem.subtitle || singleSkuText }}*{{ goodsItem.stock }}</text>
								<text v-if="goodsItem.point_exchange_type == 2">
									<text class="point" :class="'text-' + themeColor.name">{{ goodsItem.product_money }} + {{ item.point }}积分 </text>
								</text>
								<text v-else-if="goodsItem.point_exchange_type == 4">
									<text class="point">{{ item.point }}积分 </text>
								</text>
								<text class="price" v-else>
									<text :class="'text-' + themeColor.name"
										>{{ moneySymbol }}{{ goodsItem.price }}
										<text v-if="goodsItem.gift_flag === 0">
											+ {{ item.point + '积分' || '' }}</text
										></text
									>
								</text>
							</view>
						</view>
					</view>
					<rf-load-more
						:status="loadingType"
						v-if="orderList.length > 0"
					></rf-load-more>
					<rf-empty
						:list="guessYouLikeList"
						info="暂无订单"
						v-if="orderList.length === 0 && !loading"
					></rf-empty>
				</scroll-view>
			</swiper-item>
		</swiper>
		<rfLoading isFullScreen :active="loading"></rfLoading>
	</view>
</template>
<script>
/**
 * @des 订单管理
 *
 * @author stav stavyan@qq.com
 * @date 2020-01-15 11:54
 * @copyright 2019
 */
import rfLoadMore from '@/components/rf-load-more/rf-load-more';
import moment from '@/common/moment';
import { querypresalepic,querypresalecates,querypresales,querypresalerule,tryList } from '@/api/sms';
import rfCountDown from '@/components/rf-count-down';
import { orderClose, guessYouLikeList } from '@/api/product';
import mConstData from '@/config/constData.config';
export default {
	components: {
		rfLoadMore,
		rfCountDown
	},
	data() {
		return {
			tabCurrentIndex: null,
			loadingType: 'more',
			navList: [],
			moneySymbol: this.moneySymbol,
			orderList: [],
			prePic:{},
			preRule:{},
			storeId:0,
			pageNum : 0,
			loading: true,
			isRefreshing: true,
			guessYouLikeList: [],
			singleSkuText: this.singleSkuText
		};
	},
	computed: {
		// 计算倒计时时间
		second() {
			return function(val) {
				return Math.floor(val - new Date() / 1000);
			};
		}
	},
	filters: {
		// 时间格式化
		time(val) {
		return val;
			//return moment(val * 1000).format('YYYY-MM-DD HH:mm:ss');
		},
		// 状态显示格式化
		orderStatusFilter(orderStatus) {
			let state;
			mConstData.orderStatus.forEach(orderItem => {
				if (orderItem.key === parseInt(orderStatus, 10)) {
					state = orderItem.value;
				}
			});
			return state;
		}
	},
	onShow(option) {

	},
	onLoad(options) {
		/**
		 * 修复app端点击除全部订单外的按钮进入时不加载数据的问题
		 * 替换onLoad下代码即可
		 */
		this.tabCurrentIndex = parseInt(options.state, 10) + 1;
		 this.storeId=options.storeId;
    		this.initData();
	},
	// 下拉刷新
	onPullDownRefresh() {
		this.pageNum = 0;
		this.orderList.length = 0;
		this.getOrderList('refresh');
	},
	// 加载更多
	onReachBottom() {
    if (this.loadingType === 'nomore' || this.orderList.length === 0) return;
		this.pageNum++
		this.getOrderList();
	},
	methods: {
		// 倒计时关闭订单
		timeUp(item) {
			if (this.isRefreshing) {
				this.isRefreshing = false;
				this.handleOrderClose(item.id);
			}
		},

		// 跳转页面
		navTo(route) {
			this.$mRouter.push({ route });
		},

		// 数据初始化
		async initData() {
			this.pageNum = 0;
			this.orderList.length = 0;
			await this.getNavList();

		},
			async getNavList() {
			await this.$http
              				.get(`${querypresalepic}`, {storeId:this.storeId})
              				.then(async r => {
              						this.prePic=r;
              					this.loading = false;
              				})
              				.catch(() => {
              					this.loading = false;
              				});

					await this.$http
        				.get(`${querypresalecates}`, {storeId:this.storeId})
        				.then(async r => {
        						this.navList=r;
        						this.navList = [{ id: 0, name: '全部分类' }, ...this.navList];
        						console.log(this.navList)
        					this.loading = false;
        				})
        				.catch(() => {
        					this.loading = false;
        				});
await this.getOrderList();
			},
		// 获取订单列表
		async getOrderList(type) {
			let navItem = this.navList[this.tabCurrentIndex];
			const params = {};
console.log(navItem)
	params.cateId=0
			if(!navItem){
		  	params.cateId=0
			}else{
					if (navItem.id) {
							params.cateId = navItem.id;
					}
			}
			params.storeId=this.storeId,
			params.pageNum = this.pageNum ;
			params.type = 3 ;
			await this.$http
				.get(`${tryList}`, params)
				.then(async r => {
					if (type === 'refresh') {
						uni.stopPullDownRefresh();
					}
					this.loadingType = r.list.length === 10 ? 'more' : 'nomore';
					this.orderList = [...this.orderList, ...r.list];
console.log(this.orderList)
					if (this.orderList.length === 0) {
						await this.getGuessYouLikeList();
					}
					this.loading = false;
				})
				.catch(() => {
					this.loading = false;
					if (type === 'refresh') {
						uni.stopPullDownRefresh();
					}
				});
		},
		async getGuessYouLikeList() {
			await this.$http.get(`${guessYouLikeList}`,{num:6}).then(r => {
				this.guessYouLikeList = r.list;
			});
		},
		// 监听swiper切换
		changeTab(e) {
			this.pageNum = 0;
			this.orderList.length = 0;
			this.tabCurrentIndex = e.target.current;
			this.loading = true;
			this.getOrderList();
		},
		// 顶部tab点击
		tabClick(index) {
			this.pageNum = 0;
			this.orderList.length = 0;
			this.tabCurrentIndex = index;
			this.loading = true;
			this.getOrderList();
		},
		// 顶部tab点击
		getMoreOrderList() {
      if (this.loadingType === 'nomore' || this.orderList.length === 0) return;
			this.pageNum++
			this.getOrderList();
		}
	}
};
</script>
<style lang="scss">
page,
.order {
	background: $page-color-base;
	height: 100%;
	.order-search {
		padding: $spacing-sm $spacing-base;
		position: relative;
		.order-search-input {
			width: 100%;
			background-color: $color-white;
			border-radius: 40upx;
			padding: $spacing-sm 0 $spacing-sm $spacing-lg;
			color: $font-color-light;
		}
		.iconfont {
			position: absolute;
			right: 48upx;
			top: 22upx;
		}
	}
	.swiper-box {
		height: calc(100% - 160upx);
	}
	.list-scroll-content {
		height: 100%;
	}
	.uni-swiper-item {
		height: auto;
	}
}
</style>

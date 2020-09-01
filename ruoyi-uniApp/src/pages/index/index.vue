<template>
	<view class="rf-index">
		<!--搜索导航栏-->
		<rf-search-bar
			@search="navToSearch"
			title="扫一扫"
			icon="iconsaomiao"
			:placeholder="hotSearchDefault"
		/>
		<scroll-view scroll-x class="index-cate" :style="{top: customBar +'px'}" v-if="isOpenIndexCate && categoryList.length > 0">
			<view
				v-for="(item, index) in categoryList"
				:key="index"
				class="index-cate-item"
				:class="tabCurrentIndex === index ? `text-${themeColor.name} active` : ''"
				@tap.stop="tabClick(index, item.id)"
			>
				{{ item.name }}
			</view>
		</scroll-view>
		<view :class="isOpenIndexCate && categoryList.length > 0 ? 'main-wrapper' : ''">
			<block v-if="currentCate === 0">
				<!-- 轮播图1 -->
				<view class="swiper">
					<view class="swiper-box">
						<rf-swipe-dot
							:info="index_top"
							mode="nav"
							:current="current"
							field="title"
						>
							<swiper @change="handleDotChange" autoplay="true">
								<swiper-item
									v-for="(item, index) in index_top"
									@tap="indexTopToDetailPage(item)"
									:key="index"
								>
									<view class="swiper-item">
										<image :src="item.pic" mode="aspectFill" />
									</view>
								</swiper-item>
							</swiper>
						</rf-swipe-dot>
					</view>
				</view>
				<!-- 分类列表 -->
				<swiper
        					:indicator-active-color="themeColor.color"
        					indicator-color="#666"
        					:indicator-dots="markingList.length > 10"
        					:style="{height: markingList.length <= 5 ? '200rpx' : '400rpx'}"
        					class="category-list-wrapper"
        					v-if="markingList.length > 0">
        					<swiper-item
        						class="category-list"
        					>
        						<view
        							class="category"
        							v-for="(sItem, sIndex) in markingList" :key="sIndex" @tap.stop="navToByStoreId(sItem.url)">
        							<view class="img">
        								<text class="iconfont" :style="{color: sItem.color}" :class="sItem.icon"></text>
        							</view>
        							<view class="text in1line">{{ sItem.title}}</view>
        						</view>
        					</swiper-item>
        				</swiper>
				<!--新闻通知-->
				<rf-swiper-slide v-if="announceList.length > 0" :list="announceList" class="rf-skeleton" @navTo="navTo('/pages/index/notice/notice')">
					<view slot="header" class="swiper-slide-header">
						<text class="iconfont icongonggao" :class="'text-'+themeColor.name"></text>
					</view>
				</rf-swiper-slide>
				<!-- 爆款推荐 -->
				<view class="hot-recommend">
					<view class="left">
						<image class="hot-recommend-image" @tap="navTo(hotRecommendList[0].url)" :src="hotRecommendList[0].pic"></image>
					</view>
					<view class="right">
						<image class="hot-recommend-image" @tap.stop="navTo(hotRecommendList[1].url)" :src="hotRecommendList[1].pic"></image>
						<image class="hot-recommend-image" @tap.stop="navTo(hotRecommendList[2].url)" :src="hotRecommendList[2].pic"></image>
					</view>
				</view>

				<!--推荐商品-->
				<rf-floor-index
					icon="icontuijian21"
					:list="recommendProductList"
					:header="{ title: '推荐商品', desc: 'Recommend Product' }"
					@toBanner="indexTopToDetailPage"
					@toList="
					navTo(
						`/pages/product/list?param=${JSON.stringify({ is_recommend: 1 })}`
					)
				"
					@detail="navToDetailPage"
					:banner="index_recommend && index_recommend[0]"
				/>
				<!--热门商品-->
				<rf-floor-index
					icon="iconremen2"
					:list="hotProductList"
					:header="{ title: '热门商品', desc: 'Hot Product' }"
					@toBanner="indexTopToDetailPage"
					@toList="
					navTo(`/pages/product/list?param=${JSON.stringify({ is_hot: 1 })}`)
				"
					@detail="navToDetailPage"
					:banner="index_hot && index_hot[0]"
				/>
				<!--猜您喜欢-->
				<rf-floor-p-index
					v-if="guessYouLikeProductList.length > 0"
					icon="iconcainixihuan2"
					:list="guessYouLikeProductList"
					:isLink="false"
					:header="{ title: '新品上线', desc: 'Guess You Like It' }"
					@detail="navToDetailPage"
					:bannerShow="false"
				/>
					<rf-load-more
        				class="load-more"
        				:status="loadingType"
        				v-if="guessYouLikeProductList.length > 0"
        			></rf-load-more>
				<!--网站备案号-->
				<!--#ifdef H5-->
				<view class="copyright" v-if="config.web_site_icp">
					{{ config.copyright_desc }}
					<a href="http://www.beian.miit.gov.cn">{{ config.web_site_icp }}</a>
				</view>
				<!-- #endif -->
			</block>
			<view v-else class="index-cate-product-list">
				<rf-product-list :bottom="bottom" :list="categoryProductList"></rf-product-list>
				<rf-load-more
					:status="loadingType"
					v-if="categoryProductList.length > 0"
				></rf-load-more>
				<rf-empty
					:bottom="bottom"
					:info="'暂无该分类产品~'"
					v-if="categoryProductList.length === 0 && !productLoading"
				></rf-empty>
			</view>
		</view>
		<!--页面加载动画-->
		<rfLoading isFullScreen :active="loading"></rfLoading>
		<rf-back-home></rf-back-home>
		<rf-back-top :scrollTop="scrollTop"></rf-back-top>
	</view>
</template>
<script>
/**
 * @des 微商城首页
 *
 * @author stav stavyan@qq.com
 * @date 2020-01-08 14:14
 * @copyright 2019
 */
import {
	indexList,
	productList
} from '@/api/product';
import {
bannerList,
	homeRecommendSubject,
	homeRecommendProduct,
	homeBrand,noticeList,typeList,
	homeNewProduct} from '@/api/sms';
import rfSwipeDot from '@/components/rf-swipe-dot';
import rfFloorIndex from '@/components/rf-floor-index';
import rfFloorPIndex from '@/components/rf-floor-p-index';
import rfSearchBar from '@/components/rf-search-bar';
import rfSwiperSlide from '@/components/rf-swiper-slide';
import rfProductList from '@/components/rf-product-list';
import { mapMutations } from 'vuex';
export default {
	components: {
		rfFloorIndex,rfFloorPIndex,
		rfSwipeDot,
		rfProductList,
		rfSearchBar,
		rfSwiperSlide
	},
	data() {
		return {
			current: 0, // 轮播图index
			carouselList: {}, // 广告图
			index_hot: [], // 广告图
                        				index_new: [], // 广告图
                        				index_recommend: [], // 广告图
                        				index_top: [], // 广告图
			hotProductList: [], // 热门商品列表
			brandList:[],
			recommendProductList: [], // 推荐商品列表
			guessYouLikeProductList: [], // 猜您喜欢商品列表
			newProductList: [], // 新品上市商品列表
			productCateList: [], // 商品栏目
			markingList: this.$mConstDataConfig.markingList,
			config: {}, // 商户配置
			announceList: [], // 公告列表
			share: {},
			loading: true,
			scrollTop: 0,
			kefuShow: true,
			loadingType: 'more',
			hotSearchDefault: '请输入关键字',
			newsBg: this.$mAssetsPath.newsBg,
			errorImage: this.$mAssetsPath.errorImage,
			appName: this.$mSettingConfig.appName,
			tabCurrentIndex: 0,
			categoryList: [], // 分类列表
			categoryProductList: [], // 分类列表
			pageNum :0,
			currentCate: 0,
      hotRecommendList: this.$mConstDataConfig.hotRecommendList,
			productLoading: true,
			customBar: this.CustomBar,
			isOpenIndexCate: this.$mSettingConfig.isOpenIndexCate,
			moneySymbol: this.moneySymbol
		};
	},
	onPageScroll(e) {
		this.scrollTop = e.scrollTop;
	},
	onShow() {
		// 初始化购物车数量
		this.setCartNum(uni.getStorageSync('cartNum'));
	},
	onLoad() {
		this.initData();
	},
	computed: {
		// 计算倒计时时间
		second() {
			return function(val) {
				return Math.floor(15 * 60 - (new Date() / 1000 - val));
			};
		},
		bottom () {
			let bottom = 0;
			/*  #ifdef H5  */
      bottom = 90;
			/*  #endif */
			return bottom;
		},
		swipeCateList() {
			const list = this.brandList;
			let result = [];
			for (let i = 0, len = list.length; i < len; i += 10) {
				result.push(list.slice(i, i + 10));
			}
			console.log(result)
			return result;
		}
	},
	onShareAppMessage() {
    let shareParams = { title: this.share.share_title || `欢迎来到${this.appName}`, path: '/pages/index/index' };
    return shareParams;
	},
	filters: {
		filterDiscountPrice(val) {
			const price = val.product && (val.product.price * val.discount) / 100;
			switch (val.decimal_reservation_number) {
				case 0:
					return (Math.floor(price * 100) / 100).toFixed(2);
				case 1:
					return (Math.floor(price * 100) / 100).toFixed(0);
				case 2:
					return (Math.floor(price * 100) / 100).toFixed(1);
				default:
					return (Math.floor(price * 100) / 100).toFixed(2);
			}
		},
		filterTotalSales(val) {
			if (val > 10000) {
				val = `${(val / 10000).toFixed(2)}w`;
			}
			return val;
		}
	},
	// 下拉刷新
	onPullDownRefresh() {
			this.pageNum = 0;
  		this.integralList = [];
			this.getNewProductList('refresh');
	},
	// 加载更多
	onReachBottom() {
	if (this.loadingType === 'nomore') return;
		if (this.currentCate === 0) {
				this.pageNum++
    		this.getNewProductList();
    		return;
		}

		this.pageNum++
		this.getProductList(this.currentCate);
	},
	methods: {
		// 顶部tab点击
		tabClick(index, id) {
			this.currentCate = id;
			this.tabCurrentIndex = index;
			if (id === 0) return;
			this.loading = true;
			this.pageNum = 0;
			this.productLoading = true;
			this.categoryProductList = [];
			this.getProductList(id);
		},
		// 获取产品列表
		async getProductList(id) {
			await this.$http
				.get(`${productList}`, {
					typeId: id,
					pageNum: this.pageNum
				})
				.then(async r => {
					this.loading = false;
					this.productLoading = false;
					this.loadingType = r.list.length === 10 ? 'more' : 'nomore';
					this.categoryProductList = [...this.categoryProductList, ...r.list];
				}).catch(() => {
					this.loading = false;
					this.productLoading = false;
				});
		},
		...mapMutations(['setCartNum']),
		// 监听轮播图切换
		handleDotChange(e) {
			this.current = e.detail.current;
		},
		// 数据初始化
		initData() {
			// 设置购物车数量角标
			this.getBannerList();
      	//
      		this.getannounceList();
      		this.getTypeList();
      	//	this.getBrandList();
      			this.loading = false;

      			this.getHotProductList();
            this.getRecommendProductList();
		//	this.getIndexList();
		this.getNewProductList();
			this.initCartItemCount();
		},
		// 设置购物车数量角标
		async initCartItemCount() {
			if (
				this.$mStore.getters.hasLogin &&
				parseInt(uni.getStorageSync('cartNum'), 10) > 0
			) {
				uni.setTabBarBadge({
					index: this.$mConstDataConfig.cartIndex,
					text: uni.getStorageSync('cartNum').toString()
				});
			} else {
				uni.removeStorageSync('cartNum');
				uni.removeTabBarBadge({ index: this.$mConstDataConfig.cartIndex });
			}
		},
		// 通用跳转
		navTo(route) {
			this.$mRouter.push({ route });
		},
		navToByStoreId(route) {
    		this.navTo(route+`?storeId=0`);
    		},
		// 跳转至分类模块
		navToCategory(id) {
			if (this.$mSettingConfig.appCateType === '2') {
        uni.setStorageSync('indexToCateId', id);
        this.$mRouter.reLaunch({ route: '/pages/category/category' });
			} else {
        this.navTo(`/pages/product/list?cate_id=${id}`);
			}
		},
		// 通用跳转
		navToSearch() {
			this.$mRouter.push({
				route: `/pages/index/search/search?data=${JSON.stringify(this.search)}`
			});
		},
		// 跳至广告图指定页面
		indexTopToDetailPage(item) {
		var url = route.url
    	 	this.navTo(url+`?storeId=`+this.storeId);
		//	this.$mHelper.handleBannerNavTo(item.jump_type, item.jump_link, item.id);
		},
		// 获取主页数据
		async getIndexList(type) {
			await this.$http
				.get(`${indexList}`, {})
				.then(async r => {
					uni.setNavigationBarTitle({ title: this.appName });
					if (type === 'refresh') {
						uni.stopPullDownRefresh();
					}
					// 首页参数赋值
					this.initIndexData(r);
					this.loading = false;
				})
				.catch(() => {
					this.loading = false;
					if (type === 'refresh') {
						uni.stopPullDownRefresh();
					}
				});
		},
		// 首页参数赋值
		initIndexData(data) {
			this.announceList = data.announce || [];
			this.productCateList = data.cate;
			this.categoryList = [{ id: 0, title: '首页' }, ...this.productCateList];
		//	this.carouselList = data.adv;

			//this.search = data.search;
			this.share = data.share;
			uni.setStorageSync('search', this.search);
			this.hotSearchDefault = data.search.hot_search_default || '请输入关键字';
			uni.setStorage({
				key: 'hotSearchDefault',
				data: data.search.hot_search_default
			});
		//	this.hotProductList = data.product_hot;

			//this.recommendProductList = data.product_recommend;
			this.guessYouLikeProductList = data.guess_you_like;
			this.newProductList = data.product_new;
			this.config = data.config;
			this.$mHelper.handleWxH5Share(this.share.share_title || this.appName, this.share.share_desc || `欢迎来到${this.appName}商城`, this.share.share_link || this.$mConfig.hostUrl, this.share.share_cover || this.$mSettingConfig.appLogo);
		},
// 推荐品牌
    		async getBrandList() {
    			await this.$http.get(`${homeBrand}`,{storeId:0}).then(r => {
    				this.brandList = r;
    			});
    		},
    		// 属性类别
            		async getTypeList() {
            			await this.$http.get(`${typeList}`).then(r => {
            				this.productCateList = r;
            				this.categoryList = [{ id: 0, name: '首页' }, ...this.productCateList];
            			});
            		},
		// 轮播图列表 1 轮播图 2 新品推荐广告 3 人气推荐广告 4热门推荐广告 5 分类广告
                  		async getBannerList() {
                  			await this.$http.get(`${bannerList}`,{storeId:0}).then(r => {

                  				r.forEach((item, index) => {
                          			if (item.orderCount === 1) {
                          					this.index_top.push(item)
                          			}else if (item.orderCount === 2) {
                                            			this.index_new.push(item)
                                   		}else if (item.orderCount === 3) {
                                                    	this.index_recommend.push(item)
                                                 		}else if (item.orderCount === 4) {
                                                             					this.index_hot.push(item)
                                               		}
                          		})


                  			});
                  		},

    				// 公告列表
              		async getannounceList() {
              			await this.$http.get(`${noticeList}`,{storeId:0}).then(r => {
              				this.announceList = r;
              			});
              		},
			// 热门商品列表
    		async getHotProductList() {
    			await this.$http.get(`${homeNewProduct}`,{storeId:0}).then(r => {
    				this.hotProductList = r;
    			});
    		},
    		// 推荐商品列表
            		async getRecommendProductList() {
            			await this.$http.get(`${homeRecommendProduct}`,{storeId:0}).then(r => {
            				this.recommendProductList = r;
            			});
            		},

                    		// 猜您喜欢商品列表


                                			async getNewProductList(type) {
                                			console.log('获取商品列表')
                                				await this.$http
                                					.get(`${productList}`, {
                                						...this.productParams,
                                						pageNum: this.pageNum
                                					})
                                					.then(async r => {
                                					console.log(r)
                                						this.loading = false;
                                						if (type === 'refresh') {
                                							uni.stopPullDownRefresh();
                                						}
                                						console.log(r)
                                						if(r.list){
                                							this.loadingType = r.list.length === 10 ? 'more' : 'nomore';
                                            	this.guessYouLikeProductList = [...this.guessYouLikeProductList, ...r.list];
                                						}

                                					})
                                					.catch(err => {
                                						this.errorInfo = err;
                                						this.loading = false;
                                						if (type === 'refresh') {
                                							uni.stopPullDownRefresh();
                                						}
                                					});
                                			},
		// 跳转至商品详情页
		navToDetailPage(data) {
			const { id } = data;
			if (!id) return;
			this.$mRouter.push({ route: `/pages/product/product?id=${id}` });
		},
		// 跳转至分类页
		toCategory() {
			this.$mRouter.switchTab({ route: '/pages/category/category' });
		}
	}
};
</script>
<style lang="scss">
page {
	background-color: $page-color-base;
}
.rf-index {
	background-color: $color-white;
	/*爆款拼团*/
	.wrapper {
		border-radius: 10upx;
		.h-list {
			background-color: $page-color-base;
			white-space: nowrap;
			padding: 0 $spacing-sm;
			.h-item {
				margin: $spacing-sm $spacing-sm $spacing-sm 0;
				display: inline-block;
				background-color: $color-white;
				font-size: $font-sm;
				width: 280upx;
				border-radius: 6upx;
				.h-item-img {
					display: inline-block;
					width: 280upx;
					height: 300upx;
					border-top-left-radius: 12upx;
					border-top-right-radius: 12upx;
					border-bottom: 1upx solid rgba(0, 0, 0, 0.01);
				}
				.title {
					width: 280upx;
					white-space : normal;
					height: 60upx;
					line-height: 30upx;
					font-size: $font-sm;
					padding: 0 $spacing-sm;
					margin: $spacing-sm 0;
				}
				.last-line {
					padding: 0 $spacing-sm $spacing-base;
					margin-bottom: 5upx;
					display: flex;
					justify-content: space-between;
					align-items: center; /* 垂直居中 */
					.red {
						font-size: $font-sm;
						margin-right: 4upx;
					}
				}
				.price {
					font-size: $font-base - 2upx;
					line-height: 1;
					.m-price {
						margin-left: 8upx;
						color: $font-color-light;
						font-size: $font-sm;
						text-decoration: line-through;
					}
				}
			}
		}
	}
	/*轮播图*/
	.swiper {
		width: 100%;
		margin-bottom: 20upx;
		display: flex;
		justify-content: center;
		.swiper-box {
			width: 92%;
			height: 40vw;
			overflow: hidden;
			border-radius: 15upx;
			box-shadow: 0upx 8upx 25upx rgba(0, 0, 0, 0.2);
			//兼容ios，微信小程序
			position: relative;
			z-index: 1;
			swiper {
				width: 100%;
				height: 40vw;
				swiper-item {
					image {
						width: 100%;
						height: 40vw;
					}
				}
			}
		}
	}
  /* 爆款推荐 */
  .hot-recommend {
		background-color: $color-white;
    display: flex;
    padding: $spacing-base $spacing-lg 0;
    .hot-recommend-image {
      width: 100%;
      height: 100%;
    }
    .left {
      flex: 3;
      height: 350upx;
      margin-right: 15upx;
    }
    .right {
      flex: 4;
      .hot-recommend-image {
        height: 170upx;
      }
    }
  }
	/*轮播图2*/
	.swiper-item-text {
		position: absolute;
		bottom: 16upx;
		left: 30upx;
		height: 48upx;
		line-height: 48upx;
		background: rgba(0, 0, 0, 0.2);
		width: 90%;
		color: $color-white;
		border-bottom-left-radius: 12upx;
		padding-left: 20upx;
	}
	/*新闻通知*/
	.swiper-slide-header {
		.picfont {
			font-size: $font-lg + 8upx;
			font-weight: 600;
		}
	}
	/*分类列表*/
	.category-list-wrapper {
		.category-list {
			width: 100%;
			padding: $spacing-base;
			display: flex;
			justify-content: space-between;
			flex-wrap: wrap;
			.category {
				margin-top: 10upx;
				width: calc(20% - 20upx);
				height: 132upx;
				display: flex;
				text-align: center;
				flex-wrap: wrap;
				.img {
					width: 100%;
					display: flex;
					justify-content: center;
					image {
						width: 12vw;
						height: 12vw;
						border-radius: 50%;
					}
				}
				.text {
					margin-top: 16upx;
					width: 100%;
					display: flex;
					justify-content: center;
					font-size: 24upx;
					color: #3c3c3c;
				}
			}
		}
	}
	/*版权显示*/
	.copyright {
		margin: 10upx 0;
		width: 100%;
		text-align: center;
		color: #666;

		a {
			display: block;
			color: #666;
			text-decoration: none;
		}
	}
	/*限时抢购*/
	.order-item {
		display: flex;
		flex-direction: column;
		background: #fff;
		padding: 0 30upx;
		margin-bottom: 20upx;

		.goods-box-single {
			display: flex;
			padding: 40upx 10upx 10upx;
			height: 330upx;
			border-radius: 12upx;
			margin-top: 20upx;
			box-shadow: 2px 2px 10px rgba(66, 135, 193, 0.2); // 阴影
			border-bottom: 1px solid rgba(0, 0, 0, 0.05); // 边框
			position: relative;

			.goods-img {
				display: block;
				border-radius: 12upx;
				width: 190upx;
				height: 200upx;
			}

			.right {
				flex: 1;
				display: flex;
				flex-direction: column;
				padding: 0 30upx 0 24upx;
				overflow: hidden;

				.title {
					font-weight: bold;
					line-height: 1.2;
					margin: 10upx 0;
				}

				.attr-box {
					line-height: 1.2;
					margin-bottom: 8upx;
					margin-left: 10upx;
				}

				.last-line {
					margin-top: 3upx;
					display: flex;
					justify-content: space-between;
					align-items: center; /* 垂直居中 */
					.red {
						margin-right: 4upx;
					}
				}

				.price {
					font-size: $font-lg;
					line-height: 1;
					.m-price {
						margin-left: 8upx;
						color: $font-color-light;
						font-size: $font-sm;
						text-decoration: line-through;
					}
				}

				.triangle-wrapper {
					position: absolute;
					overflow: hidden;
					top: 0;
					right: 0;
					border-radius: 12upx;

					.triangle {
						color: #5eba8f;
						width: 0;
						height: 0;
						border-top: 120upx solid;
						opacity: 0.8;
						border-left: 120upx solid transparent;
					}

					.triangle-content {
						position: absolute;
						top: 28upx;
						right: 0;
						transform: rotate(45deg);
						font-size: $font-sm - 2upx;
						color: #fff;
					}
				}
			}
		}

		.action-box {
			display: flex;
			justify-content: flex-end;
			align-items: center;
			height: 90upx;
			position: relative;

			.discount-time {
				font-size: $font-sm;
				color: $uni-color-success;
				margin-right: 20upx;
			}
		}
	}
	.index-cate {
		white-space: nowrap;
		z-index: 10;
		background-color: $color-white;
		/*#ifndef MP*/
		position: fixed;
		/*#endif*/
		margin-bottom: $spacing-sm;
		.index-cate-item {
			display: inline-block;
			height: 75upx;
			line-height: 75upx;
			margin: 0 15upx;
			text-align: center;
			width: 120upx;
			font-size: $font-base;
		}
		.active {
			font-weight: 500;
			border-bottom: 2px solid;
		}
	}
	.main-wrapper {
		margin-top: 85upx;
		/*#ifdef MP*/
		margin-top: 0;
		/*#endif*/
	}
	.index-cate-product-list {
		padding-top: $spacing-sm;
		background-color: $page-color-base;
		.no-data {
			margin: 48upx 0;
			color: $font-color-light;
			display: flex;
			justify-content: center;
			align-items: center;
			.picfont {
				margin-right: 20upx;
			font-size: $font-lg + 16upx;
			}
		}
	}
}
</style>

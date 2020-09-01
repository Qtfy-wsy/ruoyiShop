<template>
	<view class="cart">
		<!--<view class="header" :style="{ position: headerPosition, top: headerTop }">-->
		<!--<view class="title">购物车</view>-->
		<!--</view>-->
		<!--&lt;!&ndash; 占位 &ndash;&gt;-->
		<!--<view class="place"></view>-->
		<!-- 购物车为空 -->
		<view v-if="cartList.length === 0" class="empty">
			<text class="iconfont icongouwuche" :class="'text-'+themeColor.name"></text>
			<view v-if="hasLogin" class="empty-tips">
				空空如也
				<navigator class="navigator" :class="'text-'+themeColor.name" v-if="hasLogin" url="../category/category" open-type="switchTab">随便逛逛></navigator>
			</view>
			<view v-else class="empty-tips">
				空空如也
				<view class="navigator" :class="'text-'+themeColor.name" @tap="navTo('/pages/public/logintype')">登录/注册 ></view>
			</view>
		</view>
		<!-- 购物车列表 -->
		<view class="goods-list" v-else>
			<view class="btn-clear" :class="'text-'+themeColor.name" @tap="clearCart({ lose_status: 1 })">清空失效商品</view>
			<view class="goods-section" v-for="(storeSettlement, index1) in cartList" :key="index1">
				<view class="g-header b-b">
					<text class="name"> 店铺 【{{storeSettlement.storeName }}】</text>
				</view>

				<view class="rf-cart-row" v-for="(row, index) in storeSettlement.normalSkus" :key="index">

					<!-- 删除按钮 -->
					<view class="menu" @tap.stop="deleteCartItem(row.skuId, 'one')" :class="'bg-'+themeColor.name">
						<text class="iconfont icon iconiconfontshanchu1"></text>
					</view>
					<!-- 商品 -->
					<view class="carrier" :class="[
						theIndex === index ? 'open' : oldIndex === index ? 'close' : ''
					]"
					 @touchstart="touchStart(index, $event)" @touchmove="touchMove(index, $event)" @touchend="touchEnd(index, $event)">
						<!-- checkbox -->
						<view class="checkbox-box" @tap="selected(index1,index, row)">
							<view class="checkbox" :class="[
								true ? `checkbox-disabled ${'text-'+themeColor.name}` : 'text-'+themeColor.name
							]">
								<view :class="[row.selected ? `on ${'bg-'+themeColor.name}` : '']"></view>
							</view>
						</view>
						<!-- 商品信息 -->
						<view class="goods-info">
							<view class="img">
								<image :src="row.image" @tap="navTo(`/pages/product/product?id=${row.spuId}`)"></image>
							</view>
							<view class="info">
								<view class="title in2line" >
									{{ row.name }}
								</view>
								<view class="state-wrapper">
									<view class="spec" @tap.stop="toggleSpec(row)">选择营销</view>
									<view class="state" v-if="parseInt(row.status, 10) === 0">
										已失效
									</view>
								</view>
								<view class="price-number">
									<view class="price">{{ moneySymbol }}{{
									 row.price
								}}</view>

									<view class="number">
										<view class="sub" @tap.stop="sub(row, index,index1)">
											<text class="iconfont icon icon-jianhao"></text>
										</view>
										<view class="input" @tap.stop="discard">
											<input type="number" :class="'text-'+themeColor.name" v-model="row.num" />
										</view>
										<view class="add" @tap.stop="add(row, index,index1)">
											<text class="iconfont icon iconjia1"></text>
										</view>
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 脚部菜单 -->
		<view class="footer" :style="{ bottom: footerbottom }" v-if="cartList.length !== 0">
			<view class="checkbox-box" @tap="allSelect">
				<view class="checkbox" :class="'text-'+themeColor.name">
					<view :class="[isAllselected ? `on ${'bg-'+themeColor.name}` : '']"></view>
				</view>
				<view class="text">全选</view>
			</view>
			<view class="delBtn" :class="'text-'+themeColor.name" @tap="deleteCartItem" v-if="selectedList.length > 0">删除</view>
			<view class="delBtn" :class="'text-'+themeColor.name" @tap="clearCart()" v-if="selectedList.length > 0">清空</view>
			<view class="settlement">
				<view class="sum">合计:
					<view class="money">{{ moneySymbol }}{{ sumPrice }}</view>
				</view>
				<view class="btn" :class="'bg-'+themeColor.name" @tap="createOrder">结算({{ selectedList.length }})</view>
			</view>
		</view>

		<!--营销类型-->
		<rf-item-popup title="" @hide="hideService" :specClass="couponClass" @show="showPopupService('couponClass', marketing)"
		 >
			<view slot="right" v-if="marketing.length > 0"><text class="iconfont iconyou"></text></view>
			<view slot="popup" class="service">
				<!-- 营销类型列表 -->
				<view class="sub-list valid">
					<view class="row" v-for="(item, index) in marketing" :key="index" @tap.stop="getCoupon(item)">
						<view class="carrier">
							<view class="title">
								<view>
									<text class="cell-title">{{ item.name }}</text>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</rf-item-popup>
		<!--页面加载动画-->
		<rfLoading isFullScreen :active="loading"></rfLoading>
	</view>
</template>
<script>
	import {
		cartItemClear,
		cartItemDel,
		cartItemList,
		cartItemUpdateNum,
		cartItemUpdateSku,
		productDetail,
		marketings,
		updatecartmarketing
	} from '@/api/product';
	import rfItemPopup from '@/components/rf-item-popup';
	import rfAttrContent from '@/components/rf-attr-content';
	import {
		mapMutations
	} from 'vuex';
	export default {
		components: {
			rfAttrContent,
			rfItemPopup
		},
		data() {
			return {
				sumPrice: '0.00',
				headerPosition: 'fixed',
				headerTop: null,
				statusTop: null,
				marketingType: this.$mConstDataConfig.marketingType,
				selectCartId:0,
				showHeader: true,
				selectedList: [],
				cartLenght: 0,
				isAllselected: false,
				// 控制滑动效果
				theIndex: null,
				oldIndex: null,
				couponClass: 'none', // 优惠券弹窗
				isStop: false,
				cartList: [],
				hasLogin: null,
				footerbottom: 0,
				specClass: 'none',
				marketing: [],
				productDetail: {
					base_attribute_format: [],
					sku: []
				},
				specSelected: [],
				specChildList: [],
				specList: [],
				currentSkuId: '',
				moneySymbol: this.moneySymbol,
				loading: true,
				singleSkuText: this.singleSkuText
			};
		},
		onPageScroll(e) {
			// 兼容iOS端下拉时顶部漂移
			this.headerPosition = e.scrollTop >= 0 ? 'fixed' : 'absolute';
			this.headerTop = e.scrollTop >= 0 ? null : 0;
			this.statusTop = e.scrollTop >= 0 ? null : -this.statusHeight + 'px';
		},
		// 下拉刷新，需要自己在page.json文件中配置开启页面下拉刷新 "enablePullDownRefresh": true
		onPullDownRefresh() {
			this.selectedList.length = 0;
			this.isAllselected = false;
			this.sumPrice = 0;
			this.getCartItemList('refresh');
		},
		onShow() {
			// 兼容H5下结算条位置
			// #ifdef H5
			this.footerbottom =
				document.getElementsByTagName('uni-tabbar')[0].offsetHeight + 'px';
			// #endif
			// #ifdef APP-PLUS
			this.showHeader = false;
			// eslint-disable-next-line
			this.statusHeight = plus.navigator.getStatusbarHeight();
			// #endif
			this.initData();
		},
		methods: {
			...mapMutations(['setCartNum']),

			// 获取产品详情
			async getProductDetail(row) {
				this.currentSkuId = row.skuId;
				await this.$http
					.get(`${marketings}`, {
						skuId: row.skuId
					})
					.then(async r => {

						this.marketing = r;
						this.marketing = [{
							marketingId: 0,
							name: '不使用优惠'
						}, ...this.marketing];
						console.log(this.marketing)
						this.specClass = 'show';
					});
			},
			// 选中商品
      			selected(indexStore, index, item) {

      				this.cartList[indexStore].normalSkus[index].selected = !this.cartList[indexStore].normalSkus[index].selected;
      				console.log(this.cartList)
      				let i = this.selectedList.indexOf(this.cartList[indexStore].normalSkus[index].cartId);
      				i > -1 ?
      					this.selectedList.splice(i, 1) :
      					this.selectedList.push(this.cartList[indexStore].normalSkus[index].cartId);
      				console.log(this.selectedList)
      				this.isAllselected = this.selectedList.length === this.cartLenght;
      				this.sum();
      			},
			// 规格弹窗开关
			toggleSpec(row) {
			console.log(row)
				this.selectCartId=row.cartId;
				if (this.specClass === 'show') {
					if (!this.hasLogin) {
						this.specClass = 'none';
						this.$mHelper.toast('请先登录！');
						return;
					}
					this.handleCartItemUpdateSku(this.currentSkuId, row.skuId);
					this.specClass = 'hide';
					setTimeout(() => {
						this.specClass = 'none';
					}, 250);
				} else if (this.specClass === 'none') {
					if (row) {
						this.couponClass = 'show';
						this.getProductDetail(row);
					}
				}
			},
			// 弹窗显示
			showPopupService(type, list) {
				if (list.length === 0) return;
				this[type] = 'show';
			},
			// 关闭服务弹窗
			hideService() {
				this.couponClass = 'none';
			},
			// 获取优惠券
			async getCoupon(item) {
				if (!this.hasLogin) {
					await this.$mHelper.backToLogin();
					return;
				}
				await this.$http
					.get(`${updatecartmarketing}`, {
						id: this.selectCartId,
						marketingId:item.id
					})
					.then(() => {
						this.$mHelper.toast('切换成功');
						this.getCartItemList();
					});
			},
			hideSpec() {
				this.specClass = 'hide';
				setTimeout(() => {
					this.specClass = 'none';
				}, 250);
			},
			stopPrevent() {},
			// 删除选中购物车商品
			async deleteCartItem(id, type) {
				const skuIds = [];
				if (type) {
					skuIds.push(parseInt(id, 10));
				} else {
					for (let i = 0; i < this.cartList.length; i++) {
						if (this.cartList[i].selected) {
							skuIds.push(parseInt(this.cartList[i].skuId, 10));
						}
					}
				}
				await this.$http
					.post(`${cartItemDel}`, {
						skuIds: JSON.stringify(skuIds)
					})
					.then(() => {
						this.selectedList.length = 0;
						this.isAllselected = false;
						this.sumPrice = 0;
						this.getCartItemList();
						this.oldIndex = null;
						this.theIndex = null;
					});
			},
			// 修改购物车商品sku
			async handleCartItemUpdateSku(skuId, newSkuId) {
				await this.$http
					.post(`${cartItemUpdateSku}`, {
						skuId: skuId,
						new_skuId: newSkuId
					})
					.then(() => {
						this.selectedList.length = 0;
						this.isAllselected = false;
						this.sumPrice = 0;
						this.getCartItemList();
					});
			},
			// 数据初始化
			initData() {
				this.hasLogin = this.$mStore.getters.hasLogin;
				if (this.hasLogin) {
					this.theIndex = null;
					this.oldIndex = null;
					this.selectedList.length = 0;
					this.isAllselected = false;
					this.sumPrice = 0;
					this.getCartItemList();
				} else {
					this.cartList = [];
					this.selectedList.length = 0;
					this.loading = false;
				}
			},
			// 通用跳转
			navTo(route) {
				if (!this.$mStore.getters.hasLogin) {
					uni.setStorageSync('backToPage', JSON.stringify({
						route: '/pages/cart/cart'
					}));
				}
				this.$mRouter.push({
					route
				});
			},
			// 获取购物车列表
			async getCartItemList(type) {
				await this.$http
					.get(`${cartItemList}`)
					.then(r => {
						this.loading = false;
						if (type === 'refresh') {
							uni.stopPullDownRefresh();
						}
						this.cartList = r;
						let cartNum = 0; //
						let cartLenght = 0; //
						r.forEach(item => {
							for (let j = 0; j < item.normalSkus.length; j++) {
								cartNum += item.normalSkus[j].num;
								cartLenght += 1;
							}
						});
						console.log(cartNum + ',' + cartLenght)
						this.cartLenght = cartLenght;
						this.setCartNum(cartNum);
					})
					.catch(() => {
						this.cartList = [];
						this.loading = false;
						if (type === 'refresh') {
							uni.stopPullDownRefresh();
						}
					});
			},
			// 清空购物车
			clearCart(params) {
				const content = params ? '清空失效商品？' : '清空购物车？';
				uni.showModal({
					content,
					success: async e => {
						if (e.confirm) {
							await this.$http.post(`${cartItemClear}`, params).then(() => {
								this.selectedList.length = 0;
								this.isAllselected = false;
								this.sumPrice = 0;
								this.getCartItemList();
							});
						}
					}
				});
			},
			// 控制左滑删除效果
			touchStart(index, event) {
				// 多点触控不触发
				if (event.touches.length > 1) {
					this.isStop = true;
					return;
				}
				this.oldIndex = this.theIndex;
				this.theIndex = null;
				// 初始坐标
				this.initXY = [event.touches[0].pageX, event.touches[0].pageY];
			},
			touchMove(index, event) {
				// 多点触控不触发
				if (event.touches.length > 1) {
					this.isStop = true;
					return;
				}
				let moveX = event.touches[0].pageX - this.initXY[0];
				let moveY = event.touches[0].pageY - this.initXY[1];

				if (this.isStop || Math.abs(moveX) < 5) {
					return;
				}
				if (Math.abs(moveY) > Math.abs(moveX)) {
					// 竖向滑动-不触发左滑效果
					this.isStop = true;
					return;
				}

				if (moveX < 0) {
					this.theIndex = index;
					this.isStop = true;
				} else if (moveX > 0) {
					if (this.theIndex != null && this.oldIndex === this.theIndex) {
						this.oldIndex = index;
						this.theIndex = null;
						this.isStop = true;
						setTimeout(() => {
							this.oldIndex = null;
						}, 150);
					}
				}
			},
			touchEnd(index, $event) {
				// 结束禁止触发效果
				this.isStop = false;
			},

			// 全选商品
			allSelect() {
				let len = this.cartList.length;
				let arr = [];
				for (let i = 0; i < len; i++) {
					for (let j = 0; j < this.cartList[i].normalSkus.length; j++) {
						// 当商品
						console.log(!this.isAllselected)
						this.cartList[i].normalSkus[j].selected = !this.isAllselected;
						arr.push(this.cartList[i].normalSkus[j].cartId);
					}
				}
				this.selectedList = this.isAllselected ? [] : arr;
				this.isAllselected = !(this.isAllselected || arr.length === 0);
				if (arr.length > 0) {
					this.sum();
				}
			},
			// 减少数量(执行接口)
			sub(item, index, indexStore) {
				if (this.cartList[indexStore].normalSkus[index].num <= 1) {
					return;
				}
				this.cartList[indexStore].normalSkus[index].num--;
				//	item.num--;
				this.numberChange(item);
			},
			// 增加数量(执行接口)
			add(item, index, indexStore) {
				this.cartList[indexStore].normalSkus[index].num++;
				//item.num++
				this.numberChange(item, undefined, index, 'add');
			},
			// 控制可输入购物车商品数量
			discard() {},
			// 监听购物车商品数量改变
			async numberChange(item, data, index, type) {

				await this.$http
					.post(`${cartItemUpdateNum}`, {
						id: item.cartId,
						num: item.num
					})
					.then(r => {
						if (r.code === 200) {
							this.sum();
						} else {

							this.$mHelper.toast(r.message);
						}
					})
					.catch(() => {
						if (type === 'add') {
							// this.cartList[index].num--;
						}
					});
			},
			// 创建订单
			async createOrder() {
				if (this.selectedList.length === 0) return;
				const data = {};
				const ids = [];

				let len = this.cartList.length;
				const arr = [];
				for (let i = 0; i < len; i++) {

					for (let j = 0; j < this.cartList[i].normalSkus.length; j++) {

						if (this.cartList[i].normalSkus[j].selected) {
							ids.push(this.cartList[i].normalSkus[j].cartId);
						}
					}

				}
				data.type = 'cart';
				data.ids = ids.join(',');
				data.isGroup = 0;
				data.skuInfo = '';
				data.groupId = 0;
				this.selectedList.length = 0;
				this.isAllselected = false;
				this.sumPrice = 0;
				this.navTo(`/pages/order/create/order?data=${JSON.stringify(data)}`);
			},
			// 合计
			sum() {
				this.sumPrice = 0;
				let len = this.cartList.length;
				const arr = [];
				for (let i = 0; i < len; i++) {
					for (let j = 0; j < this.cartList[i].normalSkus.length; j++) {
						// 当商品
						arr.push(this.cartList[i].normalSkus[j].cartId);
						if (this.cartList[i].normalSkus[j].selected) {
							this.sumPrice = this.sumPrice + this.cartList[i].normalSkus[j].price * this.cartList[i].normalSkus[j].num;
						}
					}
				}

				this.sumPrice = this.sumPrice.toFixed(2);
			},
			// 向下舍去小数点后两位
			floor(val) {
				return Math.floor(val * 100) / 100;
			},

		}
	};
</script>
<style lang="scss">
	page {
		background-color: #fff;
	}

	.checkbox-box {
		display: flex;
		align-items: center;

		.checkbox {
			width: 35upx;
			height: 35upx;
			border-radius: 100%;
			border: solid 2upx;
			display: flex;
			justify-content: center;
			align-items: center;

			.on {
				width: 25upx;
				height: 25upx;
				border-radius: 100%;
			}
		}

		.checkbox-disabled {
			border: solid 2upx $font-color-disabled;
		}

		.text {
			font-size: 28upx;
			margin-left: 10upx;
		}
	}

	.status {
		width: 100%;
		height: 0;
		position: fixed;
		z-index: 10;
		background-color: #fff;
		top: 0;
		/*  #ifdef  APP-PLUS  */
		height: var(--status-bar-height); //覆盖样式
		/*  #endif  */
	}

	.header {
		width: 92%;
		padding: 0 4%;
		height: 100upx;
		display: flex;
		align-items: center;
		position: fixed;
		top: 0;
		z-index: 10;
		background-color: #fff;
		/*  #ifdef  APP-PLUS  */
		top: var(--status-bar-height);

		/*  #endif  */
		.title {
			font-size: 36upx;
		}
	}

	.place {
		background-color: #fff;
		height: 100upx;
		/*  #ifdef  APP-PLUS  */
		margin-top: var(--status-bar-height);
		/*  #endif  */
	}

	.goods-list {
		width: 100%;
		padding: 0 0 100upx 0;

		.btn-clear {
			text-align: right;
			padding: 0 $spacing-lg;
			font-size: $font-base + 2upx;
		}

		.tis {
			width: 100%;
			height: 60upx;
			display: flex;
			justify-content: center;
			align-items: center;
			font-size: 32upx;
		}
	}

	.empty {
		position: fixed;
		left: 0;
		top: 0;
		width: 100%;
		height: 100vh;
		padding-bottom: 100upx;
		display: flex;
		justify-content: center;
		flex-direction: column;
		align-items: center;
		background: #fff;

		.icongouwuche {
			font-size: $font-lg + 100upx;
		}

		.empty-tips {
			display: flex;
			font-size: $font-sm + 2upx;
			color: $font-color-disabled;

			.navigator {
				margin-left: 16upx;
			}
		}
	}

	.footer {
		width: 100%;
		padding: 0 2%;
		background-color: #fbfbfb;
		height: 100upx;
		display: flex;
		justify-content: space-between;
		align-items: center;
		font-size: 28upx;
		position: fixed;
		bottom: 0upx;
		z-index: 5;

		.delBtn {
			border: solid 1upx;
			padding: 0 24upx;
			height: 42upx;
			border-radius: 24upx;
			font-size: $font-sm;
			display: flex;
			justify-content: center;
			align-items: center;
		}

		.settlement {
			width: 52%;
			display: flex;
			justify-content: flex-end;
			align-items: center;

			.sum {
				font-size: $font-base;
				margin-right: 8upx;
				display: flex;
				justify-content: flex-end;

				.money {
					font-weight: 600;
				}
			}

			.btn {
				padding: 0 26upx;
				height: 50upx;
				color: #fff;
				display: flex;
				justify-content: center;
				align-items: center;
				border-radius: 30upx;
			}
		}
	}

	.popup .layer {
		// #ifndef H5
		bottom: 0;
		// #endif
	}

	.service {
		padding: $spacing-base $spacing-lg 0;

		.row {
			font-size: $font-lg;
			margin-bottom: $spacing-sm;
		}
	}

	.selected-text {
		margin-right: 4upx;
	}

	.sub-list {
		margin: 40upx 0 80upx;

		.row {
			width: 100%;
			margin-bottom: $spacing-lg;
		}
	}
</style>

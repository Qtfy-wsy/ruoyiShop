<template>
	<view class="rf-product-list">
		<view class="rf-product-list-container">
			<block v-for="(item, index) in list" :key="index">
				<!--商品列表-->
				<view
					v-if="(index + 1) % 2 !== 0 || isList"
					class="rf-product-item"
					@tap.stop="navTo(`/pages/product/product?id=${item.id}`)"
					:class="[isList ? 'rf-product-flex-list' : '']"
					hover-class="hover"
					:hover-start-time="150"
				>
					<view class="rf-product-image-wrapper">
						<image :src="item.url" mode="widthFix" :preview="false" :class="[isList?'rf-product-list-img':'rf-product-img']" ></image>
						<text class="sketch in1line">{{ item.sketch }}</text>
						<view class="triangle-wrapper">
							<image class="triangle-tag" :src="item | filterTagName"></image>
						</view>
					</view>
					<view class="rf-pro-content">
						<view class="rf-pro-tit">{{ item.name }}</view>
						<view v-if="item">
							<view class="rf-pro-price">
								<text class="rf-sale-price" :class="'text-'+themeColor.name">{{ moneySymbol }}{{ item.price }}</text>
								<text class="rf-factory-price" >{{ moneySymbol }}{{ item.price }}</text>
							</view>
							<view class="rf-pro-pay">
								<text
									><text :class="'text-'+themeColor.name">{{
										item.storeName
									}}</text>
									</text
								>
								<text
									@tap.stop="toggleSpec(item)"
									class="iconfont icongouwuche2"
									:class="'text-'+themeColor.name"
								></text>
							</view>
						</view>
					</view>
				</view>
				<!--商品列表-->
			</block>
		</view>
		<view class="rf-product-list-container" v-if="!isList">
			<block v-for="(item, index) in list" :key="index">
				<!--商品列表-->
				<view
					v-if="(index + 1) % 2 === 0"
					class="rf-product-item"
					@tap.stop="navTo(`/pages/product/product?id=${item.id}`)"
					:class="[isList ? 'rf-product-flex-list' : '']"
					hover-class="hover"
					:hover-start-time="150"
				>
					<view class="rf-product-image-wrapper">
						<image :src="item.url" mode="widthFix" :preview="false" :class="[isList?'rf-product-list-img':'rf-product-img']" ></image>
						<text class="sketch in1line">{{ item.sketch }}</text>
						<view class="triangle-wrapper" v-if="item.shipping_type === '1' || item.isVirtual === '1' || item.commissionRate || item.is_open_presell === '1'">
							<image class="triangle-tag" :src="item | filterTagName"></image>
						</view>
					</view>
					<view class="rf-pro-content">
						<view class="rf-pro-tit">{{ item.name }}</view>
						<view v-if="item">
							<view class="rf-pro-price">
								<text class="rf-sale-price" :class="'text-'+themeColor.name">{{ moneySymbol }}{{ item.price }}</text>
								<text class="rf-factory-price" >{{ moneySymbol }}{{ item.price }}</text>
							</view>
							<view class="rf-pro-pay">
								<text
									><text :class="'text-'+themeColor.name">{{
										item.storeName
									}}</text>
									</text
								>
								<text
									@tap.stop="toggleSpec(item)"
									:class="'text-'+themeColor.name"
									class="iconfont icongouwuche2"
								></text>
							</view>
						</view>
					</view>
				</view>
				<!--商品列表-->
			</block>
		</view>
		<!-- 规格-模态层弹窗 -->
		<view
			class="popup spec show"
			v-if="specClass === 'show'"
			@touchmove.stop.prevent="stopPrevent"
			@tap="hideSpec"
		>
			<!-- 遮罩层 -->
			<view class="mask" @tap="hideSpec"></view>
			<view class="layer" :style="{bottom: bottom ? `${bottom}upx` : 0}" @tap.stop="stopPrevent">
				<rf-attr-content
					:product="productDetail"
					@toggle="toggleSpec"
					:showBuyBtn="true"
				></rf-attr-content>
			</view>
		</view>
	</view>
</template>
<script>
/**
 * @des 商品列表
 *
 * @author stav stavyan@qq.com
 * @date 2020-05-15 10:49
 */
import { productDetail, cartItemCount, cartItemCreate } from '@/api/product';
import rfAttrContent from '@/components/rf-attr-content';
import $mAssetsPath from '@/config/assets.config';
import { mapMutations } from 'vuex';
export default {
	name: 'rfProductList',
	props: {
		list: {
			type: Array,
			default() {
				return [];
			}
		},
		// 是否以列表展示 | 列表或大图
		isList: {
			type: Boolean,
			default: false
		},
		// 是否以列表展示 | 列表或大图
		bottom: {
			type: Number,
			default: 0
		}
	},
	components: {
		rfAttrContent
	},
	data() {
		return {
			specClass: 'none',
      moneySymbol: this.moneySymbol,
			productDetail: {}
		};
	},
	filters: {
		filterTotalSales(val) {
			if (val > 10000) {
				val = `${(val / 10000).toFixed(2)}w`;
			}
			return val;
		},
		filterTagName(val) {
			if (val.commissionRate) {
				// 分销商品
				return $mAssetsPath.distribution;
			} else if (val.isVirtual === '1') {
				// 虚拟产品
				return $mAssetsPath.virtual;
			} else if (val.shipping_type === '1') {
				// 包邮产品
				return $mAssetsPath.pinkage;
			}
		}
	},
	methods: {
    ...mapMutations(['setCartNum']),
		// 跳转详情
		navTo(route) {
			this.$mRouter.push({ route });
		},
		// 获取产品详情
		async getProductDetail(row) {
			await this.$http
				.get(`${productDetail}`, {
					goodsId: row.id
				})
				.then(async r => {
					this.productDetail = await r;

					this.specClass = 'show';
				});
		},
		// 规格弹窗开关
		toggleSpec(row) {

			if (this.specClass === 'show') {
				this.specClass = 'hide';
				if (row.stock === 0) {
					this.$mHelper.toast('库存不足');
					return;
				}
				if (row.type.toString() === '1') {
					// 加入购物车
					this.handleCartItemCreate(row.skuId, row.cartCount,row.spuId);
				} else if (row.type.toString() === '2') {
					// 立即购买
					const list = {};
					const data = {};
					data.skuId = row.skuId;
					data.num = row.cartCount;
					if (
						this.productDetail.point_exchange_type.toString() === '2' ||
						this.productDetail.point_exchange_type.toString() === '4' ||
						(this.productDetail.point_exchange_type.toString() === '3' &&
							this.isPointExchange)
					) {
						list.type = 'point_exchange';
					} else {
						list.type = 'buy_now';
					}
					list.data = JSON.stringify(data);
					 const params = {};
                            params.skuInfo=row.skuId + ',' + row.cartCount;
                            				params.isGroup=0;
                            				params.ids = '';
          					this.navTo(`/pages/order/create/order?data=${JSON.stringify(params)}`);

				}
				setTimeout(() => {
					this.specClass = 'none';
				}, 250);
			} else if (this.specClass === 'none') {
				this.getProductDetail(row);
			}
		},
		stopPrevent() {},
		hideSpec() {
			this.specClass = 'hide';
			setTimeout(() => {
				this.specClass = 'none';
			}, 250);
		},
		// 添加商品至购物车
		async handleCartItemCreate(skuId, cartCount,spuId) {
			await this.$http
				.post(`${cartItemCreate}`, {
					spuId:spuId,
					skuId: skuId,
					num: cartCount
				})
				.then(async () => {
					await this.$http.get(`${cartItemCount}`).then(async r => {
					console.log(r)
					// 1 成功 0 失败 -1 库存不足  -2 单品不存在 -3 参数错误 -4 单品已下架 -5 超出商品抢购限购数量 -6 预售商品不能加入购物车 -7 店铺状态异常
					if(r==1){
						this.setCartNum(r);
						this.$mHelper.toast('添加购物车成功');
					}else if(r==-1){
						this.$mHelper.toast('库存不足');
						return false;
					}else if(r==-2){
           						this.$mHelper.toast('单品不存在');
           						return false;
          	}else if(r==-3){
             						this.$mHelper.toast('参数错误');
             						return false;
             					}else if(r==-4){
                       						this.$mHelper.toast('单品已下架');
                       						return false;
                       					}else if(r==-5){
                                 						this.$mHelper.toast('超出商品抢购限购数量');
                                 						return false;
              	}else if(r==-6){
                                                  						this.$mHelper.toast('预售商品不能加入购物车');
                                                  						return false;
                               	}else if(r==-7){
                                                                  						this.$mHelper.toast('店铺状态异常');
                                                                  						return false;
                                               	}

					});

				});
		}
	}
};
</script>

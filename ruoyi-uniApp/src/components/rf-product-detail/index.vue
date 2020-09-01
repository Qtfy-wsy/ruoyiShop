<template>
	<view class="rf-product-detail">
		<!--顶部返回按钮-->
		<!--#ifdef MP-WEIXIN-->
		<text class="back-btn iconfont iconzuo" @tap="navBack"></text>
		<!--#endif-->
		<!--header-->
		<view class="detail" v-if="product.sku.name">
			<!--顶部商品轮播图-->
			<view class="carousel">
				<swiper indicator-dots circular="true" duration="400" controls touchable>
					<!--#ifdef APP-PLUS-->
					<swiper-item class="swiper-item" v-if="product.video">
						<video muted :poster="product.videoPic" object-fit="contain" :src="product.video"></video>
					</swiper-item>
					<!--#endif-->
					<swiper-item class="swiper-item" v-for="(item, index) in product.images" :key="index">
						<view class="image-wrapper">
							<image :src="item" class="loaded" mode="aspectFill"></image>
						</view>
						<uni-tag class="content" circle size="small" :text="`${index + 1}  / ${product.images.length}`"></uni-tag>
					</swiper-item>
				</swiper>
			</view>
			<!--商品信息-->
			<view class="introduce-section">
				<view class="introduce-first-line">
					<view class="price-box point-box" v-if="product.typeId == 0">
						该商品仅需
						<text class="price">123 积分</text>
					</view>
					<view class="price-box" v-else>
						<view class="price-first-line">
							<image class="member-level" v-if="product.memberPrice" mode="aspectFit" :src="vipPrice">
							</image>
							<text class="tag" :class="'text-' + themeColor.name" v-if="product.marketings[0]">{{product.marketings[0].type |filterMarkingType}}</text>
							<text class="price" :class="'text-' + themeColor.name">{{ moneySymbol }}{{ currentProductPrice }}
								<span v-if="product.sku.commissionRate>0"> 一级佣金 {{ moneySymbol }}{{ currentProductPrice*product.sku.commissionRate }}</span></text>
						</view>
						<view class="m-price-wrapper">
							价格 <text class="m-price">{{ moneySymbol }}{{ product.sku.price }}</text>
						</view>
					</view>
					<view class="collect" @tap="toFavorite">
						<view class="iconfont" :class="[ favorite ? `text-${themeColor.name} iconshixin1` : 'iconguanzhu']"></view>
						<text>收藏</text>
					</view>
				</view>
				<view class="introduce-second-line">
					<view class="title">
						<text>{{ product.name }}</text>
						<text class="sketch">{{ product.sketch }}</text>
					</view>
					<view class="share">
						<rf-tag type="gray" size="small" tui-tag-class="tui-tag-share tui-size" shape="circleLeft">
							<button class="share-btn" open-type="share" :class="'text-' + themeColor.name" @tap.stop="share()">
								<text class="iconfont iconfenxiang"></text>
								<text class="tui-share-text tui-gray">分享</text>
							</button>
						</rf-tag>
					</view>
				</view>

				<view class="data" v-if="product">
					<text class="item">快递: {{ product.shipping_type === '1' ? '包邮' : '买家自付' }}</text>
					<text class="item">库存 {{ product.stock }}</text>
					<text  class="item in1line">{{Areaaddress}}</text>
				</view>
			</view>
			<!--商品参数-->
			<view class="c-list">
				<!--商品库存-->
				<rf-item-popup title="商品库存"  :isEmpty="currentStock === 0"
				 empty="库存不足">
					<view slot="content">
						{{ currentStock || product.stock || 0 }} {{ product.unit || '件' }}
					</view>
				</rf-item-popup>
				<!--满减送
				<rf-item-popup
					v-if="product.fullGiveRule.length > 0"
					title="满减送"
					@hide="hideService"
					@show="
						showPopupService(
							'fullGiveClass',
							product.fullGiveRule
						)
					"
					:specClass="fullGiveClass"
				>
					<view slot="content" class="con-list">
						<text :class="'text-' + themeColor.name">{{ product.fullGiveRule[0] }}</text>
					</view>
					<view slot="right" v-if="product.fullGiveRule.length > 0"
						><text class="iconfont iconyou"></text
					></view>
					<view slot="popup" class="service">
						<view class="content">
							<view
								class="row"
								v-for="(item, index) in product.fullGiveRule"
								:key="index"
							>
								<text>{{ item }}</text>
							</view>
						</view>
						<button class="btn" :class="'bg-' + themeColor.name" @tap="hideService">完成</button>
					</view>
				</rf-item-popup>
				-->
				<!--满包邮
				<rf-item-popup
					v-if="product.fullMail && product.fullMail.is_open === '1' && product.shipping_type !== '1'"
					title="满包邮"
				>
					<view slot="content" :class="'text-' + themeColor.name">满{{ product.fullMail.full_mail_money }}元包邮</view>
				</rf-item-popup> -->
				<!--购买类型-->
				<rf-item-popup title="购买类型" @hide="hideService" :specClass="specClass" @show="toggleSpec">
					<view slot="content">
						<text class="selected-text" v-if="currentSkuName === singleSkuText">{{ currentCartCount }} {{ product.unit || '件' }}</text>
						<text class="selected-text" v-else-if="currentSkuName">{{ currentSkuName }} * {{ currentCartCount }}</text>
						<text class="selected-text" v-else>请选择规格</text>
					</view>
					<view slot="right"><text class="iconfont iconyou"></text></view>
					<view slot="popup" @click.stop="stopPrevent">
						<rf-attr-content :type="type" :product="product" :minNum="minNum" :maxNum="maxNum" @toggle="toggleSpec"></rf-attr-content>
					</view>
				</rf-item-popup>

				<!--优惠券-->
				<rf-item-popup title="优惠券" @hide="hideService" :specClass="couponClass" @show="showPopupService('couponClass', product.coupons)"
				 :isEmpty="product.coupons.length === 0" empty="暂无可领取优惠券">
					<view slot="content">
						<text class="con t-r">领取优惠券</text>
					</view>
					<view slot="right" v-if="product.coupons.length > 0"><text class="iconfont iconyou"></text></view>
					<view slot="popup" class="service">
						<!-- 优惠券列表 -->
						<view class="sub-list valid">
							<view class="row" v-for="(item, index) in product.coupons" :key="index" @tap.stop="getCoupon(item)">
								<view class="carrier">
									<view class="title">
										<view>
											<text class="cell-icon" :class="'bg-' + themeColor.name">{{
												parseInt(item.type, 10) === 1 ? '满减' : '直降'
											}}</text>
											<text class="cell-title">{{ item.name }}</text>
										</view>
										<view :class="'text-' + themeColor.name">
											<text class="price" v-if="item.type === 1">{{ moneySymbol }}{{ item.couponFull.price }}</text>
											<text class="price-discount" v-else>直降{{ item.couponFall && item.couponFall.price }}</text>
										</view>
									</view>
									<view class="term">
										<text>{{ item.startTime  }} ~ {{ item.endTime  }}</text>
										<text class="at_least" v-if="item.type === 1">满{{ item.couponFull.fullPrice }}可用</text>
									</view>
									<view class="usage">
										<text>
											{{
												parseInt(item.status, 10) === 1
													? '已领完'
													: '未领完'
											}}
										</text>
										<view>
											{{
												 `每人限领${item.limitNum}`
											}}
											总数 {{ item.num }}
											<text class="last" v-if="item.percentage">剩余{{ item.percentage }}%</text>
										</view>
									</view>
								</view>
							</view>
						</view>
					</view>
				</rf-item-popup>

				<!--限购说明-->
				<rf-item-popup title="限购说明" v-if="type === 'buy_now' && parseInt(product.max_buy, 10) > 0">
					<view slot="content">
						<text>{{ `${product.max_buy} ${product.unit || '件'}` }}</text>
					</view>
				</rf-item-popup>
				<!--积分活动-->
				<rf-item-popup title="积分活动" v-if="product.point_exchange_type == '1'">
					<view slot="content" class="con-list">
						<text v-if="product.point_exchange_type">兑换类型: {{ product.point_exchange_type | pointExchangeTypeFilter }}</text>
						<text v-if="parseInt(product.give_point, 10) > 0">赠送类型: {{ product.integral_give_type | integralGiveTypeFilter }}</text>
						<text v-if="parseInt(product.give_point, 10) > 0">下单可获得: {{ product | givePointFilter }}积分</text>
						<text v-if="product.point_exchange != 0">兑换所需积分: {{ product.point_exchange }}
						</text>
						<text v-if="product.max_use_point != 0">可使用抵扣积分: {{ product.max_use_point }}</text>
						<text class="buy-now" @tap="addCart('buy', true)" v-if="product.point_exchange_type == 3">积分兑换 >>
						</text>
					</view>
				</rf-item-popup>
				<!--服务-->
				<rf-item-popup v-if="product.spuServiceSupports.length > 0" title="服务" @hide="hideService" @show="showPopupService('serviceClass', product.spuServiceSupports)"
				 :specClass="serviceClass">
					<view slot="content">
						<text>{{ product.spuServiceSupports[0].serviceSupport.name }}</text>
					</view>
					<view slot="right" v-if="product.spuServiceSupports.length > 0"><text class="iconfont iconyou"></text></view>
					<view slot="popup" class="service">
						<view class="content">
							<view class="row" v-for="(item, index) in product.spuServiceSupports" :key="index">
								<view class="description">{{ item.serviceSupport.name }}</view>
							</view>
						</view>
						<button class="btn" :class="'bg-' + themeColor.name" @tap="hideService">完成</button>
					</view>
				</rf-item-popup>
				<!--阶梯优惠-->
				<rf-item-popup title="阶梯优惠" @hide="hideService" @show="
						showPopupService(
							'ladderPreferentialClass',
							product.skuList
						)
					"
				 :specClass="ladderPreferentialClass" v-if="product.skuList.length > 110">
					<view slot="content" class="con-list">
						<text>
							满{{
								product.skuList &&
									product.skuList[0] &&
									product.skuList[0].quantity
							}}{{ product.unit || '件' }}
							<text v-if="
									parseInt(
										product.skuList &&
											product.skuList[0] &&
											product.skuList[0].type,
										10
									) === 1
								">
								每{{ product.unit || '件' }}减{{
									product.skuList &&
										product.skuList[0] &&
										product.skuList[0].price
								}}元</text>
							<text v-if="
									parseInt(
										product.skuList &&
											product.skuList[0] &&
											product.skuList[0].type,
										10
									) === 2
								">
								每{{ product.unit || '件' }}{{
									parseInt(
										product.skuList &&
											product.skuList[0] &&
											product.skuList[0].price,
										10
									)
								}}折</text>
						</text>
					</view>
					<view slot="right" v-if="product.skuList.length > 0"><text class="iconfont iconyou"></text></view>
					<view slot="popup" class="service">
						<view class="content">
							<view class="row" v-for="(item, index) in product.skuList" :key="index">
								<view class="title">满{{ item.quantity }}{{ product.unit || '件' }}
									<text v-if="parseInt(item.type, 10) === 1">每{{ product.unit || '件' }}减{{ item.price }}元</text>
									<text v-if="parseInt(item.type, 10) === 2">每{{ product.unit || '件' }}{{ parseInt(item.price, 10) }}折</text>
								</view>
							</view>
						</view>
						<button class="btn" :class="'bg-' + themeColor.name" @tap="hideService">完成</button>
					</view>
				</rf-item-popup>
				<!--商品参数-->
				<rf-item-popup title="商品参数" @hide="hideService" @show="
						showPopupService(
							'attributeValueClass',
							product.spuAttributeValues
						)
					"
				 :specClass="attributeValueClass" v-if="product.spuAttributeValues.length > 0">
					<view slot="content">
						<text>
							{{
								`${product.spuAttributeValues &&
									product.spuAttributeValues[0] &&
									product.spuAttributeValues[0]
										.attributeName}: ${product.spuAttributeValues &&
									product.spuAttributeValues[0] &&
									product.spuAttributeValues[0].attributeValue}`
							}}</text>
					</view>
					<view slot="right" v-if="product.spuAttributeValues.length > 0"><text class="iconfont iconyou"></text></view>
					<view slot="popup" class="service">
						<view class="content">
							<view class="row" v-for="(item, index) in product.spuAttributeValues" :key="index">
								<view class="title">
									{{ `${item.attributeName}: ${item.attributeValue}` }}
								</view>
							</view>
						</view>
						<button class="btn" :class="'bg-' + themeColor.name" @tap="hideService">完成</button>
					</view>
				</rf-item-popup>
			</view>
			<!-- 评价 -->
			<view class="eva-section" @tap="toEvaluateList">
				<view class="e-header">
					<text class="tit">评价({{ commentCount.allCommentCount || 0 }})</text>
					<text class="tip" v-if="commentList">好评率 {{ commentCount.goodPert || 0 }}%</text>
					<text class="tip" v-else>暂无评价信息</text>
					<i class="iconfont iconyou"></i>
				</view>
				<view class="eva-box" v-if="commentList && commentList.length > 0">
					<image class="portrait" :src="
							(commentList &&
								commentList[0] &&
								commentList[0].customerImage) ||
								headImg
						"
					 mode="aspectFill"></image>
					<view class="right">
						<view class="name">
							<text>
								{{
									(commentList &&
										commentList[0] &&
										commentList[0].customerName) ||
										'匿名用户'
								}}
							</text>
							<rf-rate v-if="evaluateList.length > 0" size="16" disabled="true" :value="evaluateList[0].score" :active-color="themeColor.color" />
						</view>
						<text class="con in2line">{{
							(commentList &&
								commentList[0] &&
								commentList[0].comment) ||
								'这个人很懒，什么都没留下~'
						}}</text>
						<view class="bot">
							<text class="attr">购买类型：{{
									(commentList &&
										commentList[0] &&
										commentList[0].sku_name) ||
										singleSkuText
								}}</text>
							<text class="time">{{
								commentList &&
									commentList[0] &&
									commentList[0].createTime
							}}</text>
						</view>
					</view>
				</view>
			</view>
			<!--底部商品详情-->
			<view class="detail-desc">
				<view class="d-header">
					<text>商品详情</text>
				</view>
				<rf-parser :html="product.sku.mobileDesc" lazy-load></rf-parser>
			</view>
			<!-- 底部操作菜单 -->
			<view class="page-bottom">
				<view class="page-bottom-bth-wrapper">
					<navigator url="/pages/index/index" open-type="switchTab" class="p-b-btn">
						<i class="iconfont iconzhuyedefuben"></i>
						<text>首页</text>
					</navigator>
					<navigator url="/pages/cart/cart" open-type="switchTab" class="p-b-btn cart">
						<i class="iconfont icongouwuche2"></i>
						<text>购物车</text>
						<rf-badge v-if="hasLogin && cartNum && cartNum > 0" type="error" size="small" class="badge" :text="cartNum"></rf-badge>
					</navigator>
					<!-- #ifdef MP-WEIXIN -->
					<view @tap="kefuShow = true" class="p-b-btn">
						<i class="iconfont iconkefu2"></i>
						<text>客服</text>
					</view>
					<!-- #endif -->
				</view>
				<view class="action-btn-group" v-if="currentStock > 0">
					<button v-if="product.marketings[0] && product.marketings[0].type=='7'" class="p-b-btn" :class="'bg-' + themeColor.name"
					 :disabled="buyBtnDisabled" @tap="addCart('buy')">
						<text>支付定金</text>
					</button>

					<button v-if="!product.marketings[0] || (product.marketings[0] && (product.marketings[0].type!='10' && product.marketings[0].type!='7'))"
					 :disabled="addCartBtnDisabled" class="action-btn" :class="'bg-' + themeColor.name" @tap="addCart('cart')">
						加入购物车
					</button>
				</view>
				<button v-if="!product.marketings[0] " class="action-btn" :class="'bg-' + themeColor.name"
        				 :disabled="buyBtnDisabled" @tap="addCart('buy')">
        					立即购买
       	</button>
				<button v-if="product.marketings[0] && product.marketings[0].type=='10'" class="p-b-btn" :class="'bg-' + themeColor.name"
				 :disabled="buyBtnDisabled" @tap="addCart('buy')">
					<text>{{ currentProductPrice }}购买</text>
				</button>
				<button v-if="product.marketings[0] && product.marketings[0].type=='10'" class="btnIn2Line" :class="'bg-' + themeColor.name"
				 :disabled="buyBtnDisabled" @tap="addCart('buy')">
					{{ product.marketings[0].groupMarketing.price }}拼单
				</button>
				<view class="action-btn">
					<button v-if="currentStock === 0" class="action-btn-submit" :disabled="buyBtnDisabled">
						库存不足
					</button>
				</view>
			</view>
		</view>
		<!-- 选择颜色模态框 -->
		<view class="cu-modal" :class="{ show: colorModal }">
			<view class="cu-dialog">
				<view class="cu-bar justify-end solid-bottom">
					<view class="content">参与{{groupUser.customerName}}的拼团</view>
					<view class="action" @tap="colorModal = false">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="h-list">
					<view class="h-item" v-for="(item, index) in groupUserList" :key="index" @tap="SetColor(item)">
						<image class="h-item-img" :src="item.customerPic  || headImg" mode="aspectFill"></image>
					</view>
				</view>
				<button v-if="product.marketings[0] && product.marketings[0].type=='10'" class="btnIn2Line" :class="'bg-' + themeColor.name"
				 @tap="addCart('buy', true,groupUser.groupId)">
					<span class="text"> {{ moneySymbol }}{{ product.marketings[0].groupMarketing.price }}</span>
					<span class="after"> 参与拼单</span>
				</button>
			</view>
		</view>
		<!-- 分享引导 -->
		<view class="popup spec show" v-if="shareClass === 'show'" @touchmove.stop.prevent="stopPrevent" @tap="hideShareSpec">
			<!-- 遮罩层 -->
			<view class="mask" @tap="hideShareSpec"></view>
			<view class="share-bg">
				<image :src="shareBg"></image>
			</view>
		</view>
		<view class="hideCanvasView" v-if="canvasShow">
			<canvas class="hideCanvas" canvas-id="default_PosterCanvasId" :style="{width: (poster.width||10) + 'px', height: (poster.height||10) + 'px'}"></canvas>
		</view>
		<!--回到顶部-->
		<rf-live v-if="product.sku.name"></rf-live>
		<!--#ifdef MP-->
		<rf-nav></rf-nav>
		<!--#endif-->
		<view class="popup spec show" v-if="kefuShow" @touchmove.stop.prevent="stopPrevent" @tap="hide">
			<!-- 遮罩层 -->
			<view class="mask" @tap="hide"></view>
			<view class="kefu-bg">
				<image :src="appServiceQr"></image>
			</view>
		</view>
	</view>
</template>
<script>
	/**
	 *@des 封装商品详情
	 *@author stav stavyan@qq.com
	 *@blog https://stavtop.club
	 *@date 2020/05/15 16:22:24
	 */
	import mConstData from '@/config/constData.config';
	import rfItemPopup from '@/components/rf-item-popup';
	import moment from '@/common/moment';
	import rfAttrContent from '@/components/rf-attr-content';
	import rfRate from '@/components/rf-rate/rf-rate';
	import rfBadge from '@/components/rf-badge/rf-badge';
	import uniTag from '@/components/uni-tag/uni-tag';
	import rfNav from '@/components/rf-nav';
	import rfCountDown from '@/components/rf-count-down';
	import rfLive from '@/components/rf-live';
	import {
		cartItemCount,
		cartItemCreate,
		queryskucomments,
		querycommentsummarize,
		productGroupDetail
	} from '@/api/product';
	import {
		collectCreate,
		collectDel,
		pickupPointIndex,
		transmitCreate
	} from '@/api/basic';
	import {
		couponReceive,
		addressList
	} from '@/api/userInfo';
	import {
		couponList,
		acceptCoupon
	} from '@/api/sms';
	import {
		mapMutations
	} from 'vuex';
	export default {
		name: 'rfProductDetail',
		props: {
			product: {
				type: Object,
				default () {
					return {};
				}
			},
			userInfo: {
				type: Object,
				default () {
					return {};
				}
			},
			url: {
				type: String,
				default: ''
			},
			marketType: {
				type: String,
				default: 'buy_now'
			}
		},
		components: {
			rfNav,
			rfCountDown,
			rfItemPopup,
			rfBadge,
			rfLive,
			rfRate,
			uniTag,
			rfAttrContent
		},
		data() {
			return {
				appServiceQr: this.$mSettingConfig.appServiceQr,
				kefuShow: false,
				addressClass: 'none',
				canvasShow: true,
				logo: this.$mSettingConfig.appLogo,
				vipPrice: this.$mAssetsPath.vipPrice,
				posterShow: false,
				serviceClass: 'none', // 服务弹窗
				ladderPreferentialClass: 'none', // 阶梯优惠弹窗
				attributeValueClass: 'none', // 商品参数弹窗
				colorModal: false,
				specClass: 'none', // 商品参数弹窗
				couponClass: 'none', // 优惠券弹窗
				marketingClass: 'none', // 促销弹窗
				shareClass: 'none', // 分享引导弹窗
				fullGiveClass: 'none', // 满减送弹窗
				cartType: null, // 下单类型
				couponList: [], // 优惠券列表
				productGroupList: [], // 拼团列表
				groupUser: {},
				groupUserList: [],
				commentCount: {},
				commentList: [],
				currentStock: 0,
				currentSkuPrice: 0,
				currentSkuName: null,
				currentSkuId: 0,
				currentCartCount: 1,
				evaluateList: [],
				Areaaddress:'',
				hasLogin: this.$mStore.getters.hasLogin,
				cartNum: uni.getStorageSync('cartNum'),
				addressTypeList: this.$mConstDataConfig.addressTypeList,
				tabCurrentIndex: 0,
				loading: true,
				errorInfo: '',
				marketingType: this.$mConstDataConfig.marketingType,
				headImg: this.$mAssetsPath.headImg,
				isPointExchange: false,
				groupId: 0,
				shareBg: this.$mAssetsPath.shareBg,
				appServiceType: this.$mSettingConfig.appServiceType,
				productPosterQrType: this.$mSettingConfig.productPosterQrType,
				appName: this.$mSettingConfig.appName,
				shareFrom: '',
				poster: {},
				promoCode: '',
				addressList: [],
				moneySymbol: this.moneySymbol,
				state: 1,
				singleSkuText: this.singleSkuText,
				thirdPartyQrCodeImg: ''
			};
		},
		async onShareAppMessage() {
			// #ifdef MP
			await this.$http.post(`${transmitCreate}`, {
				topic_type: 'product',
				topic_id: this.productId
			}).then(() => {
				return {
					title: this.productDetail.sku.name,
					path: `/pages/product/product?id=${this.productId}`
				};
			});
			// #endif
		},
		filters: {
			time(val) {
				return moment(val * 1000).format('YYYY-MM-DD HH:mm');
			},
			filterMarkingType(val) {
			console.log(val)
				let state;
				mConstData.marketingType.forEach(orderItem => {
					if (orderItem.key === parseInt(val, 10)) {
						state = orderItem.value;
					}
				});
				return state;


			},

			pointExchangeTypeFilter(val) {
				const type = [
					'',
					'非积分兑换',
					'积分加现金',
					'积分兑换或直接购买',
					'只支持积分兑换'
				];
				return type[parseInt(val, 10)];
			},
			integralGiveTypeFilter(val) {
				const type = ['固定积分', '百分比'];
				return type[parseInt(val, 10)];
			},
			givePointFilter(val) {
				return val.integral_give_type === '1' ?
					Math.round((parseInt(val.give_point, 10) / 100) * parseInt(val.minSkuPrice, 10)) :
					parseInt(val.give_point, 10);
			}
		},

		async mounted() {
		console.log(this.product)
		console.log(this.product.sku)
			this.currentSkuId = this.product.sku.id
			this.currentStock= this.product.sku.stock
			await this.getLocationInfo();
			await this.getCoommentList();

			if (this.product.marketings[0] && this.product.marketings[0].type == '10') {
				await this.getProductGroupList();
			}

		},
		computed: {

			type() {
				return 'buy_now';
			},
			// 购买按钮禁用
			buyBtnDisabled() {
				return parseInt(this.currentStock || this.product.stock, 10) === 0;
			},
			// 添加购物车按钮禁用
			addCartBtnDisabled() {
				return (
					this.product.point_exchange_type === '2' ||
					this.product.point_exchange_type === '4' ||
					parseInt(this.currentStock || this.product.stock, 10) === 0 ||
					this.product.isVirtual === '1'
				);
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
			favorite() {

				return !!this.product.hasAtten;
			},
			// 计算倒计时时间
			second() {
				return function(val) {
					var dateTime = new Date(val);
					dateTime = dateTime.setDate(dateTime.getDate() + 1);
					dateTime = new Date(dateTime);

					return (dateTime - new Date()) * 1000;
				};
			},
			currentProductPrice() {
				let price;
				if (this.type === 'buy_now') {
					if (this.product.memberDiscount && this.product.memberDiscount.length !== 0) {
						// eslint-disable-next-line
						this.product.minSkuPrice = this.product.minSkuPrice * (1 - this.product.memberDiscount.discount / 100).toFixed(2);
						// eslint-disable-next-line
						this.product.maxSkuPrice = this.product.maxSkuPrice ? (this.product.maxSkuPrice * (1 - this.product.memberDiscount
							.discount / 100)).toFixed(2) : 0;
					}
					// eslint-disable-next-line
					console.log('price' + this.currentSkuPrice)
					price = this.currentSkuPrice || this.product.price.toFixed(2);
					if (this.product.marketingPrice && this.product.marketings) {
						if (this.product.marketings[0].type == 1) {
							price = price - this.product.marketings[0].fall.price
							price = price < 0 ? 0 : price
						} else if (this.product.marketings[0].type == 7) {
							price = '定金:' + price * this.product.marketings[0].preSale.depositPre + ';尾款:' + (price * (1 - this.product.marketings[
								0].preSale.depositPre)).toFixed(2)
						} else if (this.product.marketings[0].type == 14) {
							price = '定金:' + price * this.product.marketings[0].preSale.depositPre + ';尾款:' + (price * (1 - this.product.marketings[
								0].preSale.depositPre)).toFixed(2)
						}
					}

					return price;
				}

				return parseFloat(price || '0').toFixed(1);
			}
		},
		methods: {
			...mapMutations(['setCartNum']),
			// 返回上一页
			navBack() {
				this.$mRouter.back();
			},
			showColorModal(item) {

				this.groupUser = item;
				var userInfo = uni.getStorageSync('userInfo');
				item.groupMemberOrders = [item, ...item.groupMemberOrders];
				item.groupMemberOrders = [...item.groupMemberOrders, {
					groupHead: 1,
					customerName: userInfo.username,
					customerPic: userInfo.image
				}];
				this.groupUserList = item.groupMemberOrders;
				console.log(this.groupUserList)
				this.colorModal = true;
			},
			// 评论类型 -1 全部 1 好评 2 中评 3 差评
			async getCoommentList() {

				await this.$http
					.get(`${querycommentsummarize}`, {
						spuId: this.product.spuId,
						type: -1
					})
					.then(async r => {
						this.commentCount = r;
					})
					.catch(err => {
						this.errorInfo = err;
					});
				await this.$http
					.get(`${queryskucomments}`, {
						spuId: this.product.spuId,
						type: -1
					})
					.then(async r => {
						this.commentList = r.list;
					})
					.catch(err => {
						this.errorInfo = err;
					});
			},

			async getCouponList() {
				await this.$http
					.get(`${couponList}`, {
						storeId: this.product.storeId
					})
					.then(async r => {
						this.couponList = r.list;
					})
					.catch(err => {
						this.errorInfo = err;
					});
			},
			async getProductGroupList() {
				await this.$http
					.get(`${productGroupDetail}`, {
						marketingId: this.product.marketings[0].id,
						skuId: this.currentSkuId
					})
					.then(async r => {
						this.productGroupList = r.list;
					})
					.catch(err => {
						this.errorInfo = err;
					});
			},
			hide() {
				this.kefuShow = false;
			},
			// 分享商品
			share() {
				// #ifdef H5
				if (this.$mPayment.isWechat()) {
					this.shareClass = 'show';
				} else {
					this.$mHelper.h5Copy(this.url);
				}
				// #endif
				// #ifdef APP-PLUS
				this.$mHelper.handleAppShare(this.url, this.appName, this.product.sku.name, this.product.picture);
				// #endif
			},
			// 通用跳转
			navTo(route) {
				if (this.appServiceType === '1' && route === '/pages/product/service/index') {
					this.kefuShow = true;
					return;
				}
				if (!this.hasLogin) {
					this.$mHelper.backToLogin();
				} else {
					if (this.appServiceType === '0') {
						this.$mHelper.toast('暂不提供客服功能');
					} else {
						this.$mRouter.push({
							route
						});
					}
				}
			},
			// 弹窗显示
			showPopupService(type, list) {
				if (list.length === 0) return;
				 this[type] = 'show';
				//this.couponClass = 'show';
			},
			// 关闭服务弹窗
			hideService() {
				this.specClass = 'none';
				this.couponClass = 'none';
				this.marketingClass = 'none';
				this.serviceClass = 'none';
				this.ladderPreferentialClass = 'none';
				this.attributeValueClass = 'none';
				this.fullGiveClass = 'none';
			},
			// 获取优惠券
			async getCoupon(item) {
				if (!this.hasLogin) {
					await this.$mHelper.backToLogin();
					return;
				}
				await this.$http
					.get(`${acceptCoupon}`, {
						id: item.id
					})
					.then(r => {
						// 成功 -1：参数不全 -2：活动已过期 -3：优惠券已领完 -4：用户领取的优惠券已达上限 -5优惠券已失效(删除状态) -6 系统繁忙，请重试
						if (r == 1) {
							this.$mHelper.toast('领取成功');
						} else if (r == -1) {
							this.$mHelper.toast('参数不全');
							return false;
						} else if (r == -2) {
							this.$mHelper.toast('活动已过期');
							return false;
						} else if (r == -3) {
							this.$mHelper.toast('优惠券已领完');
							return false;
						} else if (r == -4) {
							this.$mHelper.toast('用户领取的优惠券已达上限');
							return false;
						} else if (r == -5) {
							this.$mHelper.toast('优惠券已失效(删除状态)');
							return false;
						} else if (r == -6) {
							this.$mHelper.toast('系统繁忙，请重试');
							return false;
						}

					});
			},
		async	getLocationInfo() {
			console.log('getLocationInfo')
      				var _this = this;
      				uni.getLocation({
      					type: 'wgs84',
      					success(res) {
      						console.log(res)
      						let latitude, longitude;
      						latitude = res.latitude.toString();
      						longitude = res.longitude.toString();
      						uni.request({
      							header: {
      								"Content-Type": "application/text"
      							},
      							url: 'http://apis.map.qq.com/ws/geocoder/v1/?location=' + latitude + ',' + longitude + '&key=' + this.$mConfig.qqMapKey,
      							success(re) {
      								console.log("中文位置")
      								console.log(re)
      								_this.Areaaddress = re.data.result.address;
      								if (re.statusCode === 200) {
      									console.log("获取中文街道地理位置成功")
      								} else {
      									console.log("获取信息失败，请重试！")
      								}
      							}
      						});
      					}
      				});
      			},
			// 跳转至评价列表
			toEvaluateList() {
				if (!this.commentListStat || parseInt(this.product.comment_num, 10) === 0) return;
				this.$mRouter.push({
					route: `/pages/order/evaluation/list?comment_num=${
						this.product.comment_num
					}&evaluateStat=${JSON.stringify(this.commentListStat)}`
				});
			},
			// 顶部tab点击
			tabClick(index, state) {
				this.pageNum = 0;
				this.addressList.length = 0;
				this.tabCurrentIndex = index;
				this.state = state;
				const api = (this.state === 1 ? addressList : pickupPointIndex);
				this.getAddressList(api);
			}, // 获取收货地址列表
			async getAddressList(api) {
				await this.$http
					.get(api, {})
					.then(r => {
						this.addressList = r;
					});
			},
			// 规格弹窗开关
			toggleSpec(row) {
				if (!this.product.id) return;
				if (this.specClass === 'show') {
				console.log('toggleSpec')
					console.log(row)
					this.currentStock = row.stock;
					this.currentSkuPrice = row.price;
					this.currentSkuName = row.skuName;
					this.currentCartCount = row.cartCount;
					const skuId = row.skuId;
					this.currentSkuId = row.skuId;
					if (this.product.marketings[0] && this.product.marketings[0].type == '10') {
						this.getProductGroupList();
					}
					console.log(this.currentStock)
					if (parseInt(this.currentStock, 10) === 0) {
						this.$mHelper.toast('库存不足');
						return;
					}
					if (this.cartType === 'cart') {
						this.handleCartItemCreate(skuId, this.product.spuId);
					} else if (this.cartType === 'buy') {
						this.buy(skuId);
					}
					this.cartType = null;
					this.specClass = 'hide';
					setTimeout(() => {
						this.specClass = 'none';
					}, 250);
				} else if (this.specClass === 'none') {
					this.specClass = 'show';
				}
			},
			// 海报弹窗开关
			async openPoster() {
				this.$mHelper.toast('该版本不支持生成海报');
			},
			hideSpec() {
				this.specClass = 'hide';
				setTimeout(() => {
					this.specClass = 'none';
				}, 250);
			},
			hideShareSpec() {
				this.shareClass = 'hide';
				setTimeout(() => {
					this.shareClass = 'none';
				}, 250);
			},
			// 添加商品至购物车
			async handleCartItemCreate(skuId, spuId) {
				await this.$http
					.post(`${cartItemCreate}`, {
						spuId: spuId,
						skuId: skuId,
						num: this.currentCartCount
					})
					.then(() => {
						this.$mHelper.toast('添加购物车成功');
						this.$http.get(`${cartItemCount}`).then(r => {
							this.setCartNum(r);
							this.cartNum = r;
						});
					});
			},
			// 收藏
			async toFavorite() {
				if (!this.product.id) return;
				if (!this.hasLogin) {
					this.specClass = 'none';
					await this.$mHelper.backToLogin();
				} else {
					this.favorite ? this.handleCollectDel() : this.handleCollectCreate();
				}
			},
			// 收藏商品
			async handleCollectCreate() {
				await this.$http
					.get(`${collectCreate}`, {
						skuId: this.product.id

					})
					.then(() => {
						this.$mHelper.toast('收藏成功');
						this.$emit('product');
					});
			},
			// 取消收藏商品
			async handleCollectDel() {
				await this.$http
					.delete(`${collectDel}?skuId=${this.product.id}`)
					.then(() => {
						this.$mHelper.toast('取消收藏成功');
						this.$emit('product');
					});
			},
			async buy(skuId) {
				const params = {};
				params.skuInfo = skuId + ',' + this.currentCartCount;
				params.isGroup = 0;
				params.ids = '';


				if (this.product.marketings[0] && this.product.marketings[0].type == '10') {
					params.isGroup = 1;
					if (this.groupId != 0) {
						params.groupId = this.groupId;
					}
				}

				if (
					this.product.point_exchange_type === '2' ||
					this.product.point_exchange_type === '4' ||
					(this.product.point_exchange_type === '3' &&
						this.isPointExchange)
				) {
					params.type = 'point_exchange';
				} else {
					params.type = this.type;
				}

				this.$mRouter.push({
					route: `/pages/order/create/order?data=${JSON.stringify(params)}&promo_code=${this.promoCode}`
				});
			},
			addCart(type, isPointExchange, groupId) {
				this.colorModal = false;
				if (!this.product.id) return;
				if (!this.hasLogin) {
					this.$mHelper.backToLogin();
					return;
				}
				this.specClass = 'show';
				this.cartType = type;
				this.groupId = groupId;

				this.isPointExchange = isPointExchange;
			},
			stopPrevent() {}
		}
	};
</script>
<style lang="scss">
	.rf-product-detail {
		.back-btn {
			position: fixed;
			left: 40upx;
			z-index: 9999;
			padding-top: var(--status-bar-height);
			top: 40upx;
			font-size: 40upx;
			color: $font-color-dark;
		}

		.carousel {
			height: 722upx;
			position: relative;

			swiper {
				height: 100%;
			}

			.image-wrapper {
				width: 100%;
				height: 100%;
			}

			.swiper-item {
				display: flex;
				justify-content: center;
				align-content: center;
				height: 750upx;
				overflow: hidden;
				border-bottom: 1upx solid rgba(0, 0, 0, 0.01);

				image {
					width: 100%;
					height: 100%;
				}

				.content {
					position: absolute;
					right: $spacing-base;
					bottom: $spacing-base;
				}
			}
		}

		.detail {
			padding-bottom: 60upx;
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

		.share-bg {
			image {
				position: fixed;
				z-index: 100;
				width: 70vw;
				height: 45vw;
				right: $spacing-base;
				top: $spacing-base;
			}
		}

		.layer {
			position: fixed;
			z-index: 99;
			bottom: 0;
			width: 100%;
			border-radius: 10upx 10upx 0 0;
			background-color: #fff;

			.rf-list {
				max-height: 60vh;
				padding-bottom: 0;
				margin-bottom: $spacing-sm;
			}
		}

		// 拼团公告
		.rf-swiper-slide {
			margin-top: 20upx;

			.label {
				margin-left: 10upx;
			}
		}

		// 玩法介绍
		.play-way {
			background-color: $color-white;
			padding: 0 20upx;
			margin: 20upx 0;
			font-size: $font-base;

			.title {
				border-bottom: 1px solid #eee;
				padding: $spacing-base 0;
				display: flex;
				justify-content: space-between;

				.iconfont {
					margin-left: 0.13rem;
					font-size: 0.28rem;
					color: #717171;
				}
			}

			.way {
				font-size: $font-base - 2upx;
				padding: 20upx 0;
				display: flex;

				.item {
					flex: 1;
					text-align: center;

					.tip {
						font-size: 0.22rem;
						color: #a5a5a5;
					}
				}

				.arrow {
					width: 40upx;

					.iconfont {
						color: $font-color-light;
						font-weight: 100;
					}
				}
			}
		}

		.assemble {
			background-color: #fff;

			.assemble-item {
				height: 120upx;
				border-bottom: 1px solid #f0f0f0;

				.pictxt {
					display: flex;
					justify-content: space-between;

					.picture {
						display: flex;

						image {
							width: 80upx;
							height: 80upx;
							margin: 20upx 0;
							border-radius: 50%;
						}

						.text {
							line-height: 120upx;
							margin-left: 20upx;
						}
					}

					.right {
						display: flex;
						align-items: center;

						.time-wrapper {
							text-align: right;
							margin-right: 20upx;

							.lack {
								font-size: $font-sm;

								.font-color-red {
									margin: 0 4upx;
								}
							}

							.time {
								font-size: $font-sm;
								color: $font-color-light;
							}
						}

						.spellBnt {
							font-size: $font-sm;
							width: 120upx;
							height: 48upx;
							display: flex;
							justify-content: center;
							align-items: center;
							border-radius: 48upx;
						}
					}
				}
			}
		}

		.c-list {
			font-size: $font-sm + 2upx;
			color: $font-color-base;
			background: #fff;

			.c-row {
				display: flex;
				align-items: center;
				padding: 20upx 30upx;
				position: relative;
			}

			.tit {
				width: 140upx;
			}

			.con {
				flex: 1;
				color: $font-color-dark;

				.selected-text {
					margin-right: 10upx;
				}
			}

			.bz-list {
				height: 40upx;
				font-size: $font-sm + 2upx;
				color: $font-color-dark;

				text {
					display: inline-block;
					margin-right: 30upx;
				}
			}

			.con-list {
				flex: 1;
				display: flex;
				flex-direction: column;
				color: $font-color-dark;
				line-height: 40upx;

				.buy-now {
					color: $uni-color-primary;
				}
			}

			.red {
				color: $uni-color-primary;
			}
		}

		.kefu-bg {
			height: 100vh;
			display: flex;
			justify-content: center;
			align-items: center;
			z-index: 98;

			image {
				width: 60vw;
				height: 60vw;
				border-radius: 12upx;
				z-index: 98;
			}
		}
	}

	.user-info-box {
		height: 180upx;
		display: flex;
		align-items: center;
		position: relative;
		z-index: 1;
		justify-content: space-between;

		.portrait-box {
			display: flex;
			align-items: center;

			.portrait {
				width: 130upx;
				height: 130upx;
				border: 5upx solid #fff;
				border-radius: 50%;
			}

			.username {
				font-size: $font-lg + 6upx;
				color: $color-white;
				margin-left: 20upx;
			}

			button {
				background-color: transparent;
				font-size: $font-lg + 6upx;
				color: $font-color-dark;
				border: none;
			}

			button::after {
				border: none;
			}
		}
	}

	.h-list {
		white-space: nowrap;
		padding: 20upx 30upx 0;

		.h-item {
			display: inline-block;
			font-size: $font-sm;
			color: $font-color-base;
			width: 160upx;
			margin-right: 20upx;
			border-radius: 10upx;

			.h-item-img {
				width: 160upx;
				height: 160upx;
			}

			.h-item-text {
				font-size: $font-sm;
			}
		}
	}
</style>

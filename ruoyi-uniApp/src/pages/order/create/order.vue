<template>
	<view class="rf-create-order">
		<view class="order" v-if="settlement">
			<!--选择地址-->
			<navigator url="/pages/user/address/address?source=1" class="rf-address-section">
				<view class="order-content">
					<i class="iconfont iconshouhuodizhi"></i>
					<view v-if="settlement.customerAddress" class="cen">
						<view class="top">
							<text class="name">{{ settlement.customerAddress.name }}</text>
							<text class="mobile">{{ settlement.customerAddress.mobile }}</text>
						</view>
						<text class="address in1line">{{ settlement.customerAddress.address }} {{ settlement.customerAddress.detailAddress }}</text>
					</view>
					<view class="no-default-address" v-else>
						请选择收货地址
					</view>
					<i class="iconfont iconyou"></i>
				</view>
				<image class="a-bg" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAu4AAAAFCAYAAAAaAWmiAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6Rjk3RjkzMjM2NzMxMTFFOUI4RkU4OEZGMDcxQzgzOEYiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6Rjk3RjkzMjQ2NzMxMTFFOUI4RkU4OEZGMDcxQzgzOEYiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDpGOTdGOTMyMTY3MzExMUU5QjhGRTg4RkYwNzFDODM4RiIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDpGOTdGOTMyMjY3MzExMUU5QjhGRTg4RkYwNzFDODM4RiIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PrEOZlQAAAiuSURBVHjazJp7bFvV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"></image>
			</navigator>
			<!--店铺列表-->
			<view class="goods-section" v-for="(storeSettlement, index1) in settlement.storeSettlements" :key="index1">
				<view class="g-header b-b">
					<image class="logo" :src="storeSettlement.storeLogo "></image>
					<text class="name"> 店铺 【{{storeSettlement.storeName }}】</text>
				</view>
				<view class="rf-goods-section">
					<view class="g-header b-b">
						<image class="logo" :src="logo"></image>
						<text class="name">商品列表</text>
					</view>
					<!-- 带赠品商品列表 -->
					<view v-if="storeSettlement.shoppingCartResponse.marketings[0]">
					<view v-if="storeSettlement.shoppingCartResponse.marketings[0].skus" class="g-item" v-for="(item, index) in storeSettlement.shoppingCartResponse.marketings[0].skus " :key="index" @tap="navTo(`/pages/product/product?id=${item.product_id}`)">
						<rf-image :preview="false" mode="aspectFit" :src="item.image"></rf-image>
						<view class="right">
							<text class="title clamp in2line">
								<uni-tag class="pickup-tag" v-if="item.gift_flag === '1'" type="error" text="赠品" size="small" />
								{{ item.name }}
							</text>
							<text class="spec">{{ item.name || singleSkuText }} * {{ item.num }}</text>
							<view class="price-box" v-if="storeSettlement.preMarketing">
								<text class="price-wrapper" v-if="storeSettlement.preMarketing.type=='7'">
									<text class="tip"></text><text :class="'text-' + themeColor.name">支付定金{{ moneySymbol }}{{ item.price*storeSettlement.preMarketing.preSale.depositPre }}</text>

									<text class="number"></text>
								</text>
							</view>
							<view class="price-box" v-else>
								<text class="price-wrapper">
									<text class="tip"></text><text :class="'text-' + themeColor.name">{{ moneySymbol }}{{ item.price }}</text>
									<text class="number"></text>
								</text>
							</view>
						</view>
					</view>
					</view>
					<view v-if="storeSettlement.shoppingCartResponse.marketings[0] && storeSettlement.shoppingCartResponse.marketings[0].marketingType==2" >
					<view   v-for="(item10, index) in storeSettlement.shoppingCartResponse.marketings[0].fullGifts[0].giftSkuInfos " :key="index"
					 class="g-item"
					 >
          						<rf-image :preview="false" mode="aspectFit" :src="item10.sku.url"></rf-image>
          						<view class="right">
          							<text class="title clamp in2line">
          								<uni-tag class="pickup-tag"  type="error" text="赠品" size="small" />
          								{{ item10.sku.name }}
          							</text>
          							<text class="spec">{{ item10.sku.name || singleSkuText }} * {{ item10.sku.num }}</text>

          							<view class="price-box">
          								<text class="price-wrapper">
          									<text class="tip"></text><text :class="'text-' + themeColor.name">{{ moneySymbol }}{{ item10.sku.price }}</text>
          									<text class="number"></text>
          								</text>
          							</view>
          						</view>
          					</view>
          						</view>
					<!-- 商品列表 end -->
						<!-- 商品列表 -->
          					<view v-if="!storeSettlement.shoppingCartResponse.marketings[0]" class="g-item" v-for="(item, index) in storeSettlement.shoppingCartResponse.normalSkus" :key="index" @tap="navTo(`/pages/product/product?id=${item.product_id}`)">
          						<rf-image :preview="false" mode="aspectFit" :src="item.image"></rf-image>
          						<view class="right">
          							<text class="title clamp in2line">
          								<uni-tag class="pickup-tag" v-if="item.gift_flag === '1'" type="error" text="赠品" size="small" />
          								{{ item.name }}
          							</text>
          							<text class="spec">{{ item.name || singleSkuText }} * {{ item.num }}</text>
          							<view class="price-box" v-if="storeSettlement.preMarketing">
          								<text class="price-wrapper" v-if="storeSettlement.preMarketing.type=='7'">
          									<text class="tip"></text><text :class="'text-' + themeColor.name">支付定金{{ moneySymbol }}{{ item.price*storeSettlement.preMarketing.preSale.depositPre }}</text>

          									<text class="number"></text>
          								</text>
          							</view>
          							<view class="price-box" v-else>
          								<text class="price-wrapper">
          									<text class="tip"></text><text :class="'text-' + themeColor.name">{{ moneySymbol }}{{ item.price }}</text>
          									<text class="number"></text>
          								</text>
          							</view>
          						</view>
          					</view>
          					<!-- 商品列表 end -->
				</view>
				<!-- 优惠明细 -->
				<view class="yt-list" v-if="storeSettlement.canUseCouponInfo && storeSettlement.canUseCouponInfo.length>0">
					<view class="yt-list-cell b-b" @tap="toggleMask('show')">
						<view class="cell-icon" :class="'bg-' + themeColor.name">
							券
						</view>
						<text class="cell-tit clamp">优惠券</text>
						<text class="cell-tip" :class="'text-' + themeColor.name">
							{{ couponItem.title || '选择优惠券' }}
						</text>
						<text v-if="couponItem.title" @tap.stop="clearCoupon" class="iconfont iconshanchu4" :class="'text-' + themeColor.name"></text>
					</view>
					<view class="yt-list-cell b-b" @tap="showSinglePicker" v-if="pickerShippingType.length > 0">
						<view class="cell-icon" :class="'bg-' + themeColor.name">
							寄
						</view>
						<text class="cell-tit clamp">配送方式</text>
						<text class="cell-tip" :class="'text-' + themeColor.name">
							{{ currentShippingType.label || '选择配送方式' }}
						</text>
						<text class="cell-more wanjia wanjia-gengduo-d"></text>
					</view>
					<view class="yt-list-cell b-b" @tap="showCompanyPicker" v-if="
						parseInt(currentShippingType.value, 10) === 1 &&
							orderDetail.company.length > 0
					">
						<view class="cell-icon" :class="'bg-' + themeColor.name">
							递
						</view>
						<text class="cell-tit clamp">快递公司</text>
						<text class="cell-tip" :class="'text-' + themeColor.name">
							{{ (currentCompany && currentCompany.label) || '选择快递公司' }}
						</text>
						<text class="cell-more wanjia wanjia-gengduo-d"></text>
					</view>
					<view class="yt-list-cell b-b" @tap="showPickupPointPicker" v-if="parseInt(currentShippingType.value, 10) === 2">
						<view class="cell-icon" :class="'bg-' + themeColor.name">
							提
						</view>
						<text class="cell-tit clamp">门店自提点</text>
						<text class="cell-tip in1line" :class="(currentPickupPoint && currentPickupPoint.label) ? 'text-' + themeColor.name : ''">
							{{
							(currentPickupPoint && currentPickupPoint.label) || '请选择自提点'
						}}
						</text>
						<text class="cell-more wanjia wanjia-gengduo-d"></text>
					</view>
					<view class="yt-list-cell b-b" v-if="pointExchangeType[0] == 2 || pointExchangeType[0] == 4">
						<view class="cell-icon" :class="'bg-' + themeColor.name">
							分
						</view>
						<text class="cell-tit clamp">需要使用 {{ (orderDetail.preview && orderDetail.preview.point) || 0 }} 积分</text>
						<text class="cell-tip disabled"></text>
						<view class="cell-tip" :class="'text-' + themeColor.name">
							<label class="radio">
								<radio siza="mini" :color="themeColor.color" :disabled="true" :checked="true" />
							</label>
						</view>
					</view>
					<view class="yt-list-cell b-b" v-if="
						(pointExchangeType[0] == 1 || pointExchangeType[0] == 3) &&
							pointConfig.is_open === 1 && maxUsePointFee > 0
					">
						<view class="cell-icon" :class="'bg-' + themeColor.name">
							减
						</view>
						<text class="cell-tit clamp">可用{{ maxUsePoint }}积分抵用{{ maxUsePointFee }}元</text>
						<text class="cell-tip disabled"></text>
						<view class="cell-tip" :class="'text-' + themeColor.name">
							<label class="radio">
								<radio siza="mini" :color="themeColor.color" @tap="handleIsUsePoint" :disabled="isUsePointDisabled" :checked="isUsePoint" />
							</label>
						</view>
					</view>
				</view>
			</view>
			<!-- 金额明细 -->
			<view class="yt-list">
				<view class="yt-list-cell b-b">
					<text class="cell-tit clamp">商品总价</text>
					<text class="cell-tip" :class="'text-' + themeColor.name">{{ moneySymbol }}{{ (settlement.orderTotalPrice).toFixed(2) }}</text>
				</view>
				<view class="yt-list-cell b-b" v-if="discountAmount > 0">
					<text class="cell-tit clamp">优惠金额</text>
					<text class="cell-tip" :class="'text-' + themeColor.name">-{{ moneySymbol }}{{ orderDiscountPrice }}</text>
				</view>
				<!--营销金额优惠-->
				<view v-for="(item, index) in orderDetail.marketing_details" :key="index">
					<view v-if="item.discount_money > 0 || item.give_point > 0" class="yt-list-cell b-b">
						<text class="cell-tit clamp">{{ item.marketing_name }}</text>
						<text class="cell-tip">
							<text v-if="item.marketing_type === 'give_point'">{{ givePoint(item.give_point) }} 积分</text>
							<text v-else :class="'text-' + themeColor.name">-{{ moneySymbol }}{{item.discount_money }}</text>
						</text>
					</view>
				</view>

				<view class="yt-list-cell b-b">
					<text class="cell-tit clamp">运费</text>
					<text class="cell-tip" :class="'text-' + themeColor.name">
						<text>+{{ moneySymbol }}{{(settlement.orderTotalFright).toFixed(2)}}</text>
					</text>
				</view>
				<view class="yt-list-cell b-b" v-if="invoiceAmount > 0">
					<text class="cell-tit clamp">发票税费</text>
					<text class="cell-tip" :class="'text-' + themeColor.name">
						<text>{{ moneySymbol }}{{ invoiceAmount }}</text>
					</text>
				</view>
				<navigator url="/pages/set/invoice/invoice?source=1">
					<view class="yt-list-cell b-b">
						<text class="cell-tit clamp">开具发票</text>
						<view class="cell-tip">
							<view class="invoice" v-if="invoiceItem.type">
								{{
									`电子发票 - ${
										parseInt(invoiceItem.type, 10) === 1 ? '公司' : '个人'
									} - ${invoiceItem.title}`
								}}
								<text @tap.stop="closeInvoice" class="iconfont iconshanchu4" :class="'text-' + themeColor.name"></text>
							</view>
							<text v-else>本次不开具发票</text>
							<view v-if="invoiceItem.type && orderDetail.invoice &&
											orderDetail.invoice.list.length > 0">
								<radio-group name="gender" class="gender">
									<label class="gender-item" v-for="(item, index) in orderDetail.invoice &&
											orderDetail.invoice.list"
									 :key="index">
										<radio @click.stop="handleInvoiceChange(item)" style="transform:scale(0.7)" class="gender-item-radio" :color="themeColor.color"
										 :checked="index === 0" />
										<text class="gender-item-text">{{ item }}</text>
									</label>
								</radio-group>
							</view>
						</view>
					</view>
				</navigator>
				<view class="yt-list-cell desc-cell">
					<text class="cell-tit clamp">备注</text>
					<input class="desc" type="text" v-model="buyerMessage" placeholder="请填写备注信息" placeholder-class="placeholder" />
				</view>
			</view>
			<!-- 底部 -->
			<view class="footer">
				<view class="price-content in1line">
					<text>实付款</text>
					<text class="price-tip" :class="'text-' + themeColor.name">{{ moneySymbol }}</text>
					<text class="price" :class="'text-' + themeColor.name">{{needPayPrice}}</text>
				</view>
				<!--			orderDetail.preview.point-->
				<button class="submit" :class="'bg-' + themeColor.name" @tap="submit" :disabled="btnLoading" :loading="btnLoading">
					提交订单
				</button>

			</view>
		</view>
		<!-- 404页面 -->
		<view v-if="!settlement && !loading">
			<rf-no-data class="rf-no-data" :custom="true">
				<view class="title">
					{{ errorInfo || '订单不存在' }}
				</view>
				<view @tap="getOrderDetail" slot="refresh" class="spec-color">重新加载</view>
			</rf-no-data>
		</view>
		<!--页面加载动画-->
		<rfLoading isFullScreen :active="loading"></rfLoading>
		<!-- 优惠券面板 -->
		<view class="mask" :class="maskState === 1 ? 'show' : 'none'" @tap="toggleMask">
			<view class="mask-content" @tap.stop.prevent="stopPrevent">
				<!-- 优惠券列表 -->
				<view class="sub-list valid">
					<view class="row" v-for="(item, index) in orderDetail.coupons" :key="index" @tap.stop="selectCoupon(item)">
						<view class="carrier" :class="parseFloat(amountGoods) > parseFloat(item.at_least) ? '' : 'rf-opacity'">
							<view class="title">
								<view>
									<text class="cell-icon" :class="'bg-' + themeColor.name">{{
										parseInt(item.range_type, 10) === 2 ? '限' : '全'
									}}</text>
									<text class="cell-title">{{ item.title }}</text>
								</view>
								<view>
									<text class="price" :class="'text-' + themeColor.name" v-if="item.type === '1'">{{ moneySymbol }}{{ item.money }}</text>
									<text class="price-discount" :class="'text-' + themeColor.name" v-else>{{
										`${item.discount / 10}折`
									}}</text>
								</view>
							</view>
							<view class="term">
								<text>{{ item.start_time | time }} ~ {{ item.end_time | time }}</text>
								<text class="at_least">满{{ item.at_least }}可用</text>
							</view>
							<view class="usage">
								<text>
									{{
										parseInt(item.range_type, 10) === 2
											? '部分产品使用'
											: '全场产品使用'
									}}
								</text>
							</view>
						</view>
					</view>
				</view>
				<!-- 优惠券页面，仿mt -->
				<text class="no-coupon" v-if="orderDetail.coupons && orderDetail.coupons.length === 0">暂无优惠券</text>
			</view>
		</view>
		<rf-picker :themeColor="themeColor.color" ref="shippingTypePicker" mode="selector" :deepLength="1" @onConfirm="onShippingConfirm"
		 :pickerValueArray="pickerShippingType" />
		<rf-picker :themeColor="themeColor.color" ref="companyTypePicker" mode="selector" :deepLength="1" @onConfirm="onCompanyConfirm"
		 :pickerValueArray="orderDetail.company" />
		<rf-picker :themeColor="themeColor.color" ref="pickupPointPicker" mode="selector" :deepLength="1" @onConfirm="onPickupPointConfirm"
		 :pickerValueArray="
				orderDetail.pickup_point_config && orderDetail.pickup_point_config.list
			" />
	</view>
</template>
<script>
	import {
		cartItemCount,
		orderCreate,
		orderFreightFee,
		orderPreview
	} from '@/api/product';
	import rfPicker from '@/components/rf-picker';
	import moment from '@/common/moment';
	import rfNoData from '@/components/rf-no-data';
	import {
		mapMutations
	} from 'vuex';
	export default {
		components: {
			rfPicker,
			rfNoData
		},
		data() {
			return {
				maskState: 0, // 优惠券面板显示状态
				isFullPayment: 0, // 是否全款预定，默认全款预定
				desc: '', // 备注
				payType: 1, // 1微信 2支付宝
				needPayPrice: 0,
				orderDetail: {},
				pointExchangeType: [],
				fullGifts:[],
				loadingType: 'more', // 加载更多状态
				pickerShippingType: [],
				btnLoading: false,
				currentShippingType: {
					value: ''
				},
				currentCompany: {},
				currentPickupPoint: {},
				cartIds: null,
				invoiceItem: {},
				addressData: {},
				couponItem: {},
				pointConfig: {},
				product: null,
				shippingMoney: 0,
				isUsePoint: false,
				isUsePointDisabled: false,
				data: {},
				userInfo: {},
				use_point: 0,
				invoiceContent: null,
				loading: true,
				errorInfo: '',
				buyerMessage: '',
				promoCode: '',
				moneySymbol: this.moneySymbol,
				logo: this.$mSettingConfig.appLogo,
				needRefresh: true,
				isLoading: true,
				// 订单提交错误
				orderError: {
					'R-000006': '订单中缺失商品',
					'R-000007': '请选择收货地址',
					'R-000005': '商品库存不足',
					'R-000011': '超出限购数量',
					'R-000015': '商品没参加众筹',
					'R-000016': '实体商品和虚拟商品不能同时结算',
					'R-000017': '存在批发商品实购数量小于最小批发数量',
					'R-000018': '订单中存在已过期的商品',
					'R-000019': '请不要重复提交'
				},
				isIphoneX: false,
				orderDiscountPrice: 0,
				orderTotalFright: 0,
				orderLastTotalPrice: 0,
				orderSubmitCount: 0,
				preMarketing: null,
				maxPointUse: 0,
				settlement: null,
				maxPointMoney: 0,
				canUseRedEnvelope: [],
				payType: '在线支付',
				remark: {},
				addressId: 0,
				redEnvelopePrice: 0,
				invoiceType: {
					'0': '不开发票',
					'1': '增值税普票',
					'2': '增值税专票'
				},
				agreeToPay: false,
				// 发票信息
				invoice: {
					type: 0,
					title: '',
					content: 1,
					taxId: '',
					titleType: 1,
					invoiceCompanyName: '',
					invoiceRegisterAddress: '',
					invoiceRegisterMobile: '',
					invoiceOpenBank: '',
					invoiceBankAccount: ''
				},
				singleSkuText: this.singleSkuText
			};
		},
		computed: {
			// 计算商品金额
			amountGoods() {
				return this.orderDetail.preview && this.orderDetail.preview.product_money;
			},
			// 计算优惠金额
			discountAmount() {
				const discountMoney = this.floor(
					((100 - this.couponItem.discount) / 100) * this.amountGoods
				);
				return parseInt(this.couponItem.type, 10) === 2 ?
					discountMoney :
					this.couponItem.money || 0;
			},
			// 计算实付金额
			realAmount() {
				const realAmount =
					this.amountGoods -
					this.discountAmount +
					parseFloat(this.shippingMoney) -
					(this.isUsePoint ? this.maxUsePointFee : 0);
				return (parseFloat(this.invoiceAmount) + realAmount > 0 ? (parseFloat(this.invoiceAmount) + realAmount) : 0).toFixed(
					2);
			},
			// 计算发票税费
			invoiceAmount() {
				const realAmount =
					this.amountGoods -
					this.discountAmount -
					(this.isUsePoint ? this.maxUsePointFee : 0);
				return this.invoiceItem.type ?
					this.floor((this.orderDetail.invoice.order_invoice_tax / 100) * realAmount) :
					0;
			},
			// 计算可用积分
			maxUsePoint() {
				return 0;
			},
			// 计算最大积分可抵扣金额
			maxUsePointFee() {
				return 0;
			},
			givePoint() {
				return 0;
			}
		},
		filters: {
			time(val) {
				return moment(val * 1000).format('YY/MM/DD HH:mm');
			}
		},
		onShow() {
			if (this.addressData && this.addressData.realname) {
				this.getOrderFreightFee();
			}
		},
		onLoad(options) {
			this.initData(options);
		},
		methods: {
			...mapMutations(['setCartNum']),
			handleIsFullPayment(e) {
				this.isFullPayment = e.detail.value;
			},
			handleInvoiceChange(val) {
				this.invoiceContent = val;
			},
			navTo(route) {
				this.$mRouter.push({
					route
				});
			},
			// 不使用发票
			closeInvoice() {
				this.invoiceItem = {};
			},
			// 不使用优惠券
			clearCoupon() {
				this.couponItem = {};
			},
			// 向下取整
			floor(val) {
				return Math.floor(val * 100) / 100;
			},
			// 是否使用积分抵扣
			handleIsUsePoint() {
				if (this.isUsePoint) {
					this.isUsePoint = false;
					this.use_point = 0;
				} else {
					this.isUsePoint = true;
					this.use_point = this.maxUsePoint;
				}
			},
			// 选择物流
			showSinglePicker() {
				this.$refs.shippingTypePicker.show();
			},
			// 选择快递公司
			showCompanyPicker() {
				this.$refs.companyTypePicker.show();
			},
			// 选择自提点
			showPickupPointPicker() {
				this.$refs.pickupPointPicker.show();
			},
			// 确定选择物流
			onShippingConfirm(e) {
				e.value = e.value[0];
				this.currentShippingType = e;
				if (this.currentShippingType.value.toString() === '2') {
					if (
						parseFloat(this.realAmount) >
						parseFloat(this.orderDetail.pickup_point_config.pickup_point_freight)
					) {
						this.shippingMoney = 0;
					} else {
						this.shippingMoney =
							parseFloat(this.orderDetail.pickup_point_config.pickup_point_fee) ||
							0;
					}
				} else {
					this.currentCompany = this.orderDetail.company[0];
					this.getOrderFreightFee();
				}
			},
			// 确定选择快递公司
			async onCompanyConfirm(e) {
				e.value = e.value[0];
				this.currentCompany = e;
				if (this.orderDetail.is_full_mail) {
					this.shippingMoney = 0;
					return;
				}
				this.getOrderFreightFee();
			},
			// 确定选择自提点
			async onPickupPointConfirm(e) {
				e.value = e.value[0];
				this.currentPickupPoint = e;
				if (this.currentPickupPoint) {
					this.shippingMoney = parseFloat(this.orderDetail.pickup_point_config.pickup_point_fee) || 0;
				}
			},
			// 计算运费
			async getOrderFreightFee() {
				const params = {};
				if (this.addressData) {
					params.address_id = this.addressData.id;
				}
				if (this.currentCompany) {
					params.company_id = this.currentCompany.value;
				}
				await this.$http
					.get(`${orderFreightFee}`, {
						...params,
						...this.data
					})
					.then(r => {
						this.shippingMoney = r.shipping_money || 0;
					});
			},
			// 数据初始化
			async initData(options) {
				console.log(options)
				this.promoCode = options.promo_code;
				this.data = await JSON.parse(options.data);
				this.userInfo = uni.getStorageSync('userInfo');

				await this.getOrderDetail();
			},
			// 获取订单详情
			async getOrderDetail() {
				await this.$http
					.get(`${orderPreview}`, {
						skuInfo: this.data.skuInfo,
						isGroup: this.data.isGroup,
						groupId: this.data.groupId,
						ids: this.data.ids
					})
					.then(data => {
						this.loading = false;
						/** this.orderDetail = r;
					this.pointConfig = this.orderDetail.point_config;
					this.addressData = this.orderDetail.address;
					this.couponItem = (this.orderDetail && this.orderDetail.coupons[0]) || {};
					this.orderDetail.company.forEach(item => {
						item.label = item.title;
						item.value = item.id;
					});
					this.currentCompany = this.orderDetail.company[0];
					this.pointExchangeType = [];
					this.orderDetail.products.forEach(item => {
						this.pointExchangeType.push(item.point_exchange_type);
					});
					this.shippingMoney = r.preview.shipping_money;
					if (this.orderDetail.is_open_logistics === '1') {
						this.pickerShippingType.push({ label: '物流配送', value: 1 });
					}
					if (this.orderDetail.pickup_point_config.buyer_self_lifting === '1') {
						this.orderDetail.pickup_point_config.list.forEach(item => {
							item.label = `${item.contact || '无名'} - ${item.name ||
								'未知'} - ${item.address || '未知'}`;
							item.value = item.id;
						});
						this.currentPickupPoint = this.orderDetail.pickup_point_config.list[0] || {};
						this.pickerShippingType.push({ label: '买家自提', value: 2 });
					} else {
						this.orderDetail.pickup_point_config.list = [];
					}
					if (this.pickerShippingType.length > 0) {
            this.currentShippingType = this.pickerShippingType[0];
					} **/
						// 有结算信息

						if (data.storeSettlements && data.storeSettlements.length > 0) {
							this.settlement = data;
							this.setOrderFirstPrice();
							this.setPreMarketing();
							this.calcPrice();
							this.dealCustomerAddress();
							console.log(this.settlement)
						} else {
							// app.showToastError('获取结算信息失败');
							// app.navigateBack(500)
						}
					})
					.catch(err => {
						this.loading = false;
						this.errorInfo = err;
					});
			},
			dealCustomerAddress: function() {
				if (!!this.settlement.customerAddress)
					this.settlement.customerAddress.address = this.settlement.customerAddress.address.replace(/,/g, '')
			},
			setOrderFirstPrice: function() {
				// 设置订单的初始价格

				this.orderLastTotalPrice = this.settlement.orderLastTotalPrice,
					this.orderDiscountPrice = this.settlement.orderDiscountPrice,
					this.orderTotalFright = this.settlement.orderTotalFright

			},
			setPreMarketing: function() {
				// 设置预售促销
				let preMarketing = null,
					storeSettlements = this.settlement.storeSettlements;
				for (let i = 0; i < storeSettlements.length; i++) {
					if (storeSettlements[i].preMarketing) {
						preMarketing = storeSettlements[i].preMarketing;
					}
				}

				this.preMarketing = preMarketing ? preMarketing : null

			},
			isDepositPresaleMarketing: function() {
				return !!this.settlement.preMarketing && this.settlement.preMarketing.type == '7'
			},
			calcPrice: function() {
				if (this.preMarketing) {
					let firstPrice = this.settlement.storeSettlements[0].shoppingCartResponse.normalSkus[0].price * this.preMarketing
						.preSale.depositPre
					// console.log(firstPrice+this.settlement.orderTotalFright)
					this.needPayPrice = firstPrice
				} else {
					this.needPayPrice = this.settlement.orderLastTotalPrice
				}
				//  使用积分的金额  （目前主要是平台的金额）
				let pointMoney = 0;

				// 包邮减去的价格
				let freeShipPrice = 0;

				// 每个店铺的订单
				let storeSettlements = this.settlement.storeSettlements;

				let settlement = this.settlement;

				// 使用优惠券的总价格
				let couponPrice = 0;

				for (let i = 0; i < storeSettlements.length; i++) {
					// 店铺结算信息
					let storeSettlement = storeSettlements[i];
					// 店铺一共优惠的价格
					let discountPrice = storeSettlement.totalDiscountPrice;

					// 如果选择了优惠券则设置优惠券的优惠金额
					if (!!storeSettlement.choosedCoupon) {
						// 优惠券的价格
						let couponLastPrice = storeSettlement.choosedCoupon.lastPrice;

						// 如果优惠券减去的价格比订单的价格大则减去的价格试用订单的价格 防止出现负数
						if (couponLastPrice > storeSettlement.totalPrice) {
							couponLastPrice = storeSettlement.totalPrice;
						}

						discountPrice = discountPrice + couponLastPrice;
						couponPrice = couponPrice + couponLastPrice;
					}

					// 如果使用了积分 则设置积分的金额
					if (storeSettlement.usePoints !== -1) {
						discountPrice = discountPrice + (storeSettlement.pointSeting.offsetMoney / storeSettlement.pointSeting.usePoint) *
							storeSettlement.usePoints;
						pointMoney = pointMoney + (storeSettlement.pointSeting.offsetMoney / storeSettlement.pointSeting.usePoint) *
							storeSettlement.usePoints;
					}

					// 店铺的总金额 ＝ 原始金额－优惠 （优惠券和积分）
					let storePrice = storeSettlement.originTotalPrice - discountPrice;


					// 店铺最后不算运费的价格
					storeSettlement.storePrice = storePrice;
					// 店铺最后不算运费的价格拷贝,计算红包的时候使用
					storeSettlement.storePriceCopy = storePrice;


					// 如果有包邮促销
					if (!!storeSettlement.freeShip && storeSettlement.freeShip.freeShip.isUse === '1') {
						// 看店铺的促销后的价格 是否满足了包邮的条件
						if (storePrice >= storeSettlement.freeShip.freeShip.fullPrice) {
							freeShipPrice = freeShipPrice + storeSettlement.freightPrice;
						}
					}

					// 计算可以使用的积分数量  目前只有平台有积分  积分在优惠券使用后计算
					if (storeSettlement.pointCanUse && storeSettlement.usePoints === -1) {
						storeSettlement.canUsePoints = this.calcCanUsePoints(storeSettlement, storePrice);
					}
				}

				// 所有优惠的金额
				settlement.orderDiscountPrice = this.settlement.orderDiscountPrice + couponPrice + pointMoney;
				// 总的运费
				settlement.orderTotalFright = this.settlement.orderTotalFright - freeShipPrice;
				// 结算的最后金额＝ 后端返回的最后结算金额 － 前端优惠金额（优惠券和积分） － 免费的包邮
				settlement.orderLastTotalPrice = this.settlement.orderLastTotalPrice - couponPrice - pointMoney - freeShipPrice;


				// 拷贝一份价格 方便后面红包使用
				settlement.orderDiscountPriceCopy = settlement.orderDiscountPrice;
				settlement.orderTotalFrightCopy = settlement.orderTotalFright;
				settlement.orderLastTotalPriceCopy = settlement.orderLastTotalPrice;

				//设置积分
				this.setCanUsePoint(settlement);

				// 只要价格发生变动 就取消红包的使用
				this.settlement.redEnvelopeCode = '';
				// 计算可以使用的红包
				this.calcCanUseRedEnvelope();

				this.settlement = this.settlement

			},
			calcCanUseRedEnvelope: function() {
				let canUseRedEnvelope = [];
				let settlement = this.settlement;

				// 判断是否有可以使用的红包
				if (settlement.redEnvelopeCodes && settlement.redEnvelopeCodes.length > 0) {
					// 如果有可以使用的红包 则判断 是否满足使用的条件
					for (let i = 0; i < settlement.redEnvelopeCodes.length; i++) {
						// 店铺总的价格
						let price = 0;
						for (let j = 0; j < settlement.redEnvelopeCodes[i].redEnvelopeStoreId.length; j++) {
							price += this.getStorePrice(settlement.redEnvelopeCodes[i].redEnvelopeStoreId[j]);
						}

						// 判断店铺总的价格是否满足红包的条件
						if (price >= settlement.redEnvelopeCodes[i].redEnvelope.fullPrice) {
							canUseRedEnvelope.push(settlement.redEnvelopeCodes[i]);
						}
					}
				} else {
					canUseRedEnvelope = [];
				}

				this.canUseRedEnvelope = canUseRedEnvelope

			},
			// 获得店铺不算运费的总价
			getStorePrice: function(storeId) {
				let price = 0;
				let settlement = this.settlement;
				for (let i = 0; i < settlement.storeSettlements.length; i++) {
					if (settlement.storeSettlements[i].storeId === storeId) {
						return settlement.storeSettlements[i].storePrice;
					}
				}
				return price;
			},
			setCanUsePoint: function(settlement) {
				//设置可以使用的最大积分
				if (settlement && settlement.storeSettlements.length > 0) {
					for (let i = 0; i < settlement.storeSettlements.length; i++) {
						// 设置最大使用积分  只有平台可以用
						if (settlement.storeSettlements[i].pointCanUse) {

							this.maxPointUse = settlement.storeSettlements[i].canUsePoints,
								this.maxPointMoney = (settlement.storeSettlements[i].canUsePoints * ((settlement.storeSettlements[i].pointSeting
									.offsetMoney / settlement.storeSettlements[i].pointSeting.usePoint))).toFixed(2)

						}
					}
				}
			},

			// 计算可以使用的积分数量
			calcCanUsePoints(storeSettlement, money) {

				// 如果积分没有开启 则直接返回
				if (storeSettlement.pointSeting.isOpen === '0') {
					return 0;
				}

				// 如果用户没有积分 则直接返回0
				if (storeSettlement.customerAllPoints === 0) {
					return 0;
				}

				// 计算当前订单金额能使用的最大积分
				let maxPoint = money / (storeSettlement.pointSeting.offsetMoney / storeSettlement.pointSeting.usePoint);

				//如果用户拥有的积分 大于等于订单能使用的积分  则直接返回订单能使用的积分
				if (storeSettlement.customerAllPoints >= maxPoint) {
					return maxPoint;
				}

				// 计算用户能抵消使用的积分  如果积分设置是 1 10 100积分的 则直接返回用户手机的积分
				if (storeSettlement.pointSeting.usePoint !== 1000) {
					return storeSettlement.customerAllPoints;
				}

				// 如果积分设置是 1000 积分的 则计算用户能返回的最大积分
				if (storeSettlement.customerAllPoints >= 100) {
					return storeSettlement.customerAllPoints;
				}

				return Math.floor(storeSettlement.customerAllPoints / 10) * 10;
			},

			// 显示优惠券面板
			toggleMask(type) {
				if ('combination,wholesale,group_buy'.indexOf(this.settlement.type) !== -1) {
					this.$mHelper.toast('套餐购买/拼团/团购下单不支持选择购物券');
					return;
				}
				let timer = type === 'show' ? 10 : 300;
				let state = type === 'show' ? 1 : 0;
				this.maskState = 2;
				setTimeout(() => {
					this.maskState = state;
				}, timer);
			},
			numberChange(data) {
				this.number = data.number;
			},
			changePayType(type) {
				this.payType = type;
			},
			async submit() {
				if (this.orderSubmitCount !== 0) {
					this.$mHelper.toast('请不要重复提交订单');
					return;
				} // 判断有没有收货地址


				if (!this.settlement.customerAddress) {
					this.$mHelper.toast("请选择收货地址");
					return false;
				} // 如果是定金预售 判断是否是否选择了条款


				if (this.isDepositPresaleMarketing()) {
					if (!this.agreeToPay) {
						this.$mHelper.toast('预定商品定金恕不退换，请同意支付定金');
						return false;
					}
				}

				this.orderSubmitCount++;
				let that = this;

				this.btnLoading = true;
				await this.$http
					.post(`${orderCreate}`,
						JSON.stringify(this.getSubmitOrderParams())
					)
					.then(res => {
						// 订单提交成功
						console.log(res)
						if (res.result === "0") {
							// 需要付款跳转到支付页面
							if (res.needPay === 0) {
								uni.redirectTo({
									url: `/pages/user/money/pay?money=${res.orderMoney.toFixed(2)}&orderCode=${res.orderCode}`
									//	url: `/pages/user/money/success?type=${this.settlement.type}&id=${r.wholesale_id}`
								});
							} else {
								// 不需要付款 跳转到货到付款页面
								//  app.globalData.redirectTo('OrderNoPaySuccess', '?money=' + res.orderMoney.toFixed(2));
							}
						} else {
							that.orderSubmitCount = 0;
							uni.redirectTo({
								url: `/pages/user/money/pay?money=${res.orderMoney.toFixed(2)}&orderCode=${res.orderCode}`
							});
						}


					})
					.catch(() => {
						this.btnLoading = false;
					});
			},
			getSubmitOrderParams: function() {
				let params = {};
				let settlement = this.settlement;
				params.addressId = settlement.customerAddress.id;
				params.invoice = this.invoice;
				params.storeInfos = this.getStoreInfos();
				params.ids = settlement.ids;
				params.skuInfo = this.data.skuInfo;
				params.redEnvelopeCode = settlement.redEnvelopeCode;
				params.isGroup = this.data.isGroup;
				params.groupId = this.data.groupId;
				// 1pc 2 h5 3 app 4 applet
				// #ifdef H5
				params.source = 2;
				// #endif
				// #ifdef MP-WEIXIN
				params.source = 4;
				// #endif
				// #ifdef APP-PLUS
				params.source = 3;
				// #endif
				return params;
			},
			getStoreInfos: function() {
				let storeinfos = [];
				let settlement = this.settlement;

				for (let i = 0; i < settlement.storeSettlements.length; i++) {
					let storeinfo = {};
					storeinfo.storeId = settlement.storeSettlements[i].storeId;
					storeinfo.payType = settlement.storeSettlements[i].choosedPayType;
					storeinfo.remark = !!this.remark[storeinfo.storeId] ? this.remark[storeinfo.storeId] : '';

					if (settlement.storeSettlements[i].choosedCoupon) {
						storeinfo.couponCode = settlement.storeSettlements[i].choosedCoupon.code;
					}

					storeinfo.usePoints = settlement.storeSettlements[i].usePoints;
					storeinfos.push(storeinfo);
				}

				return storeinfos;
			},
			// 设置购物车数量角标
			async setCartItemCount() {
				await this.$http.get(`${cartItemCount}`).then(r => {
					if (parseInt(r, 10) > 0) {
						this.setCartNum(r);
					} else {
						uni.removeStorageSync('cartNum');
						uni.removeTabBarBadge({
							index: this.$mConstDataConfig.cartIndex
						});
					}
				});
			},
			stopPrevent() {},
			selectCoupon(item) {
				if (parseFloat(item.at_least) > parseFloat(this.amountGoods)) {
					this.$mHelper.toast('不满足优惠券使用条件~');
					return;
				}
				this.maskState = 0;
				this.couponItem = item;
			}
		}
	};
</script>
<style lang="scss">
	page {
		background: $page-color-base;
	}

	.order {
		padding-bottom: 100upx;

		.price-wrapper {
			margin-bottom: 10upx;
			flex: 1;

			.tip {
				font-size: $font-sm;
				color: $font-color-light;
			}

			.price {
				font-size: $font-sm;
				margin-bottom: 4upx;
			}

			.number {
				font-size: $font-sm;
				color: $font-color-base;
				margin-left: $font-sm;
			}

			.original-price {
				font-size: $font-sm;
				color: $font-color-light;
				margin-left: $spacing-sm;
				text-decoration: line-through;
			}
		}
	}

	.yt-list {
		margin-top: 16upx;
		background: #fff;

		.iconshanchu4 {
			margin-left: $spacing-sm;
		}
	}

	.yt-list-cell {
		display: flex;
		align-items: center;
		padding: 10upx 30upx 10upx 40upx;
		line-height: 70upx;
		position: relative;

		&.cell-hover {
			background: #fafafa;
		}

		&.b-b:after {
			left: 30upx;
		}

		.cell-icon {
			height: 32upx;
			width: 32upx;
			font-size: 22upx;
			text-align: center;
			line-height: 32upx;
			border-radius: 4upx;
			margin-right: 12upx;

			&.hb {
				background: #ffaa0e;
			}

			&.lpk {
				background: #3ab54a;
			}
		}

		.cell-more {
			align-self: center;
			font-size: 24upx;
			color: $font-color-light;
			margin-left: 8upx;
			margin-right: -10upx;
		}

		.cell-tit {
			flex: 1;
			font-size: 26upx;
			color: $font-color-light;
			margin-right: 10upx;
		}

		.cell-tip {
			max-width: 70%;
			font-size: 26upx;

			&.disabled {
				color: $font-color-light;
			}

			&.active {
				color: $base-color;
			}

			&.red {
				color: $base-color;
			}

			.invoice {
				display: flex;
				justify-content: space-between;
			}
		}

		&.desc-cell {
			.cell-tit {
				max-width: 90upx;
			}
		}

		.desc {
			flex: 1;
			font-size: $font-base;
			color: $font-color-dark;
		}
	}

	.footer {
		position: fixed;
		left: 0;
		bottom: 0;
		z-index: 98;
		display: flex;
		align-items: center;
		width: 100%;
		height: 90upx;
		justify-content: space-between;
		font-size: 30upx;
		background-color: #fff;
		color: $font-color-base;
		box-shadow: 0 -1px 5px rgba(0, 0, 0, 0.1);

		.price-content {
			padding-left: 30upx;
		}

		.price-tip {
			margin-left: 8upx;
		}

		.price {
			font-size: $font-lg;
		}

		.disabled {
			background-color: $border-color-dark;
		}
	}

	/* 优惠券面板 */
	.mask {
		display: flex;
		align-items: flex-end;
		position: fixed;
		left: 0;
		top: var(--window-top);
		bottom: 0;
		width: 100%;
		background: rgba(0, 0, 0, 0);
		z-index: 99;
		transition: 0.3s;

		.mask-content {
			width: 100%;
			max-height: 70vh;
			min-height: 16vh;
			background: #f3f3f3;
			transform: translateY(100%);
			transition: 0.3s;
			overflow-y: scroll;

			.no-coupon {
				display: flex;
				justify-content: center;
				/* 水平居中 */
				line-height: 16vh;
			}
		}

		&.none {
			display: none;
		}

		&.show {
			background: rgba(0, 0, 0, 0.4);

			.mask-content {
				transform: translateY(0);
			}
		}
	}

	.rf-no-data {
		height: calc(100vh - 90upx);
	}

	.goods-section {
		margin-top: 16upx;
		background: #fff;
		padding-bottom: 1px;

		.g-header {
			display: flex;
			align-items: center;
			height: 84upx;
			padding: 0 30upx;
			position: relative;
		}

		.logo {
			display: block;
			width: 50upx;
			height: 50upx;
			border-radius: 100px;
		}

		.name {
			font-size: 30upx;
			color: $font-color-base;
			margin-left: 24upx;
		}

		.g-item {
			display: flex;
			margin: 20upx 30upx;

			image {
				flex-shrink: 0;
				display: block;
				width: 140upx;
				height: 140upx;
				border-radius: 4upx;
			}

			.right {
				flex: 1;
				padding-left: 24upx;
				overflow: hidden;
			}

			.title {
				font-size: 30upx;
				color: $font-color-dark;
			}

			.spec {
				font-size: 26upx;
				color: $font-color-light;
			}

			.price-box {
				display: flex;
				align-items: center;
				font-size: 32upx;
				color: $font-color-dark;
				padding-top: 10upx;

				.price {
					margin-bottom: 4upx;
				}

				.number {
					font-size: 26upx;
					color: $font-color-base;
					margin-left: 20upx;
				}
			}

			.step-box {
				position: relative;
			}
		}
	}
</style>

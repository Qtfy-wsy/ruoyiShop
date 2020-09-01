<template>
	<view class="pay" v-if="!loading">
		<cu-custom bgColor="" :isBack="true" :class="'bg-' + themeColor.name">
			<block slot="content">支付</block>
		</cu-custom>
		<view class="pay-section" :class="'bg-' + themeColor.name">
			<image class="bg" :src="moneyBg"></image>
			<view class="tips-wrapper">
				<text class="title">支付金额</text>
				<view class="tip">{{ moneySymbol }}{{ money }}</view>
			</view>
		</view>
		<view class="pay-type-list">
			<view
				class="type-item"
				:class="payType === 1 ? 'type-item-active text-' + themeColor.name : ''"
				@tap="changePayType(1)"
				v-if="parseInt(payTypeList.order_wechat_pay, 10) === 1"
			>
				<i class="iconfont iconweixinzhifu" :style="{color: payType === 1 ? '#36cb59' : ''}"></i>
				<view class="con">
					<text class="tit" :class="payType === 1 ? 'text-' + themeColor.name : ''">微信支付</text>
					<text>推荐使用微信支付</text>
				</view>
				<label class="radio">
					<radio size="12" value="" :color="themeColor.color" :checked="payType == 1" />
				</label>
			</view>
			<!-- #ifndef MP-WEIXIN -->
			<view
				class="type-item"
				:class="payType === 2 ? 'type-item-active text-' + themeColor.name : ''"
				@tap="changePayType(2)"
				v-if="parseInt(payTypeList.order_ali_pay, 10) === 1 && !isWechat"
			>
				<i class="iconfont iconalipay" :style="{color: payType === 2 ? '#01aaef' : ''}"></i>
				<view class="con">
					<text class="tit" :class="payType === 2 ? 'text-' + themeColor.name : ''">支付宝支付</text>
					<text>推荐使用支付宝支付</text>
				</view>
				<label class="radio">
					<radio value="" :color="themeColor.color" :checked="payType == 2" />
				</label>
			</view>
			<!-- #endif-->
			<view
				class="type-item"
				:class="payType === 5 ? 'type-item-active text-' + themeColor.name : ''"
				@tap="changePayType(5)"

			>
				<i class="iconfont iconerjiye-yucunkuan" :style="{color: payType === 5 ? '#fe8e2e' : ''}"></i>
				<view class="con">
					<text class="tit" :class="payType === 5 ? 'text-' + themeColor.name : ''">预存款支付</text>
					<text>可用余额 {{ moneySymbol }}{{  blance }}</text>
				</view>

				<label class="radio">
					<radio value="" :color="themeColor.color" :checked="payType === 5" />
				</label>
			</view>
			<view class="type-item" v-if="payType === 5">
      					<input 	class="login-type-input"	name="password"
                                								v-model="password"
                                								placeholder="请输入支付密码"
                                								maxlength="11" type="password"
                                							/>
      	</view>
		</view>
		<button
			class="confirm-btn"
			:class="'bg-' + themeColor.name"
			:disabled="btnLoading"
			:loading="btnLoading"
			@tap="confirm"
		>
			确认支付
		</button>
	</view>
</template>

<script>
import { orderDetail, querypredepositcount } from '@/api/userInfo';
import { configList } from '@/api/basic';
export default {
	data() {
		return {
			isWechat: this.$mPayment.isWechat(),
			payType: 1,
			payTypeList: {order_ali_pay: 1,
                    order_balance_pay: 1,
                    order_wechat_pay: 1},
			money: 0,
			userInfo: {
        account: {}
			},
			moneySymbol: this.moneySymbol,
			orderDetail: {},
			password:'',
			orderInfo: {},
			blance:0,
			orderCode:0,
			btnLoading: false,
			loading: true,
			code: '',
			type: '',
			moneyBg: this.$mAssetsPath.moneyBg,
			marketingId: ''
		};
	},
	computed: {},
	onLoad(options) {
	this.money=options.money;
	this.orderCode=options.orderCode;
		this.initData(options);
			this.getMemberInfo();
			/*  #ifdef H5  */
			this.code = options.code && options.code.split(',')[options.code.split(',').length - 1];
			if (this.$mPayment.isWechat()) {
				if (!this.code) {
						const href = window.location.href;
						window.location.href = `https://open.weixin.qq.com/connect/oauth2/authorize?
																appid=${this.$mConfig.weixinAppId}&
																redirect_uri=${href}&
																response_type=code&
																scope=snsapi_userinfo&
																state=STATE#wechat_redirect`;
				}
			}
			/*  #endif  */
	},
	methods: {
		// 数据初始化
		async initData(options) {
			this.orderInfo['order_id'] = parseInt(options.id, 10);
			this.type = options.type;
		//	await this.getOrderDetail(options.id);
			// #ifdef H5
			if (uni.getSystemInfoSync().platform === 'android') {
				await this.$mPayment.wxConfigH5(window.location.href);
			}
			// #endif
		},
		// 获取用户信息
		async getMemberInfo() {
			await this.$http
				.get(querypredepositcount)
				.then(async r => {
					this.loading = false;
					this.blance = r;
				});
		},
		// 选择支付方式
		changePayType(type) {
			this.payType = type;
		},
		// 获取订单费用
		async getOrderDetail(orderId) {
			await this.$http
				.get(`${orderDetail}`, {
					orderId, // 订单id
					simplify: 1 // 获取简化订单详情
				})
				.then(r => {
					this.marketingId = r.wholesale_id;

				}).catch(() => {
					this.loading = false;
				});
		},
		// 获取支付类型列表
		async getPayTypeList() {
			await this.$http
				.get(`${configList}`, {
					field: 'order_balance_pay,order_wechat_pay,order_ali_pay'
				})
				.then(r => {
					this.payTypeList = r;
				});
		},
		// 确认支付
		async confirm() {
			this.btnLoading = true;
			const route = `/pages/user/money/success?type=1&id=${this.marketingId}&orderCode=${this.orderCode}`;
			setTimeout(() => {
				this.btnLoading = false;
			}, 0.5 * 1000);
			switch (parseInt(this.payType, 10)) {
				case 1:
					await this.$mPayment.weixinPay('order', {orderCode:this.orderCode,type:1,orderId:this.orderCode,orderType:1}, route, this.code);
					break;
				case 2:
					await this.$mPayment.aliPay('order', {orderCode:this.orderCode,type:1}, route);
					break;
				case 5:
					if (!this.password) {
          				this.$mHelper.toast('请填写支付密码');
          				return;
          			}
					await this.$mPayment.balancePay(JSON.stringify({orderCode:this.orderCode,type:1,password:this.password}), route);
					break;
			}
		}
	}
};
</script>

<style lang="scss">
page {
	background-color: $color-white;
}

.pay {
	width: 100%;
	.pay-section {
		height: 400upx;
		overflow: hidden;
		display: flex;
		align-items: center;
		justify-content: center;
		.bg {
			position: absolute;
			left: 0;
			top: 0;
			width: 100vw;
			height: 60vw;
		}
		.tips-wrapper {
			text-align: center;
			.title {
				font-size: $font-lg;
				margin-bottom: $spacing-base;
			}
			.tip {
				font-size: $font-lg + 42upx;
			}
		}
	}
}

.price-box {
	background-color: #fff;
	height: 265upx;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	font-size: 28upx;
	color: #909399;

	.price {
		font-size: 50upx;
		color: #303133;
		margin-top: 12upx;
	}
}
.pay-type-list {
	margin: 60upx 0;
	background-color: #fff;
	padding: 0 40upx;
	.type-item {
		padding: 0 $spacing-lg;
		height: 100upx;
		border-radius: 100upx;
		margin-bottom: $spacing-lg;
		display: flex;
		justify-content: space-between;
		align-items: center;
		font-size: 30upx;
		position: relative;
		border: 2upx solid rgba(0, 0, 0, 0.12);
	}

	.type-item-active {
		border: 2upx solid;
	}

	.iconfont {
		font-size: 45upx;
		margin: 0 $spacing-base;
		color: $font-color-light;
	}

	.tit {
		font-size: $font-base;
		margin-bottom: 4upx;
	}
	.tit-active {
		color: $font-color-dark;
	}

	.con {
		flex: 1;
		font-size: $font-sm;
		color: $font-color-light;
		.tit {
			margin-right: $spacing-base;
		}
	}
	.radio {
		radio {
			transform:scale(0.7)
		}
	}
}
.confirm-btn {
	height: 86upx;
	line-height: 86upx;
	width: 70%;
	margin-top: 144upx;
}
</style>

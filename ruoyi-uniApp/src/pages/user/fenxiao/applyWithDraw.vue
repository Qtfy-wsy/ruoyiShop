<template>
	<view class="address-manage rf-row-wrapper">
		<view class="row b-b">
			<text class="tit">提现金额</text>
			<input
				class="input"
				type="text"
				v-model="addressData.money"
				@blur="handlenameChange"
				placeholder="提现金额"
				placeholder-class="placeholder"
			/>
		</view>
		<view class="row b-b">
			<text class="tit">支付宝账号</text>
			<input
				class="input"

				v-model="addressData.account"
				@blur="handleMobileChange"
				placeholder="支付宝账号"
				placeholder-class="placeholder"
			/>
		</view>

		<view class="row b-b">
			<text class="tit">姓名</text>
			<input
				class="input"
				type="text"
				v-model="addressData.name"
				@blur="bindAddressDetailsChange"
				placeholder="请输入姓名"
				placeholder-class="placeholder"
			/>
		</view>
		<view class="row b-b">
    			<text class="tit">支付密码</text>
    			<input
    				class="input"
    				type="password"
    				v-model="addressData.password"
    				@blur="bindPasswordChange"
    				placeholder="请输入支付密码"
    				placeholder-class="placeholder"
    			/>
    		</view>
		<button
			class="add-btn"
			:class="'bg-' + themeColor.name"
			:disabled="btnLoading"
			:loading="btnLoading"
			@tap="confirm"
		>
			提交
		</button>

		<!--加载动画-->
		<rfLoading isFullScreen :active="loading"></rfLoading>
	</view>
</template>

<script>
/**
 * @des 收货地址列表
 *
 * @author stav stavyan@qq.com
 * @date 2020-03-10 18:00
 * @copyright 2019
 */
import { addwithdrawrecord } from '@/api/sms';

export default {

	data() {
		return {
			addressData: {
				name: '',
				account: '',
				name: '',
				address_name: '请选择地址',
				address: '',
				isDefault: false,
				provinceId: '',
				cityId: '',
				countryId: ''
			},
			btnLoading: false,
			multiArray: [[], [], []],
			multiIndex: [0, 0, 0],
			loading: true
		};
	},
	onLoad(options) {
		this.initData(options);
	},
	methods: {

		// 数据初始化
		async initData(options) {
this.loading = false;
		},

		handlenameChange(e) {
			this.addressData.money = e.detail.value;
		},
		bindAddressDetailsChange(e) {
			this.addressData.name = e.detail.value;
		},
		bindPasswordChange(e) {
    			this.addressData.password = e.detail.value;
    		},

		handleMobileChange(e) {
			this.addressData.account = e.detail.value;
		},
		switchChange(e) {
			this.addressData.isDefault = e.detail.value;
		},
		// 提交
		confirm() {
			let data = this.addressData;
			if (!data.name) {
				this.$mHelper.toast('请填写姓名');
				return;
			}

			this.btnLoading = true;
			this.handleAddressCreate(data);
		},
		async handleAddressUpdate(data) {
			await this.$http
				.put(`${addressUpdate}`, {
					id:data.id,
					money: data.money,
					account: data.account,
					name: data.name,
					password: data.password
				})
				.then(() => {
					this.btnLoading = false;
					this.$mHelper.toast('收货地址修改成功！');
					this.$mRouter.back();
				})
				.catch(() => {
					this.btnLoading = false;
				});
		},
		async handleAddressCreate(data) {
			await this.$http
				.get(`${addwithdrawrecord}`, {

          					money: data.money,
          					account: data.account,
          					name: data.name,
          					password: data.password
				})
				.then(r => {
				// 1成功 0失败 -1用户不存在 -2用户没有设置支付密码 -3支付密码不正确 -4参数不全 -5佣金不足
					this.btnLoading = false;
						if(r==1){
                              						this.$mHelper.toast('领取成功');
                              					}else if(r==-1){
                              						this.$mHelper.toast('用户不存在');
                              						return false;
                              					}else if(r==-2){
                                         						this.$mHelper.toast('用户没有设置支付密码');
                                         						return false;
                                        	}else if(r==-3){
                                           						this.$mHelper.toast('支付密码不正确');
                                           						return false;
                                           					}else if(r==-4){
                                                     						this.$mHelper.toast('参数不全');
                                                     						return false;
                                                     					}else if(r==-5){
                                                               						this.$mHelper.toast('佣金不足)');
                                                               						return false;
                                            	}
					this.$mRouter.back();
				})
				.catch(() => {
					this.btnLoading = false;
				});
		}
	}
};
</script>

<style lang="scss">
page {
	background: $page-color-base;
}
.address-manage {
	padding-top: 16upx;
}
</style>

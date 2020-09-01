<template>
	<view class="userinfo">

		<view class="input-content">
			<view class="input-item">
				<text class="tit">手机号</text>
				<input
					type="number"
					v-model="profileInfo.mobile"

					placeholder="请输入手机号码"
				/>
			</view>
			<view class="input-item">
				<text class="tit">收货人</text>
				<input
					type="text"
					v-model="profileInfo.receiver"
					placeholder="请输入您的收货人"
				/>
			</view>

	<view class="input-item">
			<text class="tit">选择地址</text>
			<!--地址三级联动-->
			<rf-pick-regions
				:addressData="profileInfo"
				@getRegions="handleGetRegions"
			>
			</rf-pick-regions>
		</view>

			<view class="input-item">
				<text class="tit">详细地址</text>
				<input v-model="profileInfo.address" placeholder="请输入您的详细地址" />
			</view>
			<view class="input-item">
      				<text class="tit">固定电话</text>
      				<input v-model="profileInfo.phone" placeholder="请输入您的固定电话" />
      			</view>
      			<view class="input-item">
            				<text class="tit">邮编</text>
            				<input v-model="profileInfo.zipCode" placeholder="请输入您的邮编" />
            			</view>
			<button
				class="confirm-btn"
				:class="'bg-' + themeColor.name"
				:disabled="btnLoading"
				:loading="btnLoading"
				@tap="toUpdateInfo"
			>
				兑换
			</button>
		</view>

		<!--加载动画-->
		<rfLoading isFullScreen :active="loading"></rfLoading>

		<!--进度条加载-->
		<rf-load-progress
			:height="CustomBar"
			:progress="loadProgress"
		></rf-load-progress>
	</view>
</template>

<script>
/**
 * @des 修改用户信息
 *
 * @author stav stavyan@qq.com
 * @date 2020-01-10 14:28
 * @copyright 2019
 */
import { memberInfo, memberUpdate, uploadImage ,submitpointmallorder} from '@/api/userInfo';
import avatar from '@/components/rf-avatar/rf-avatar';
import moment from '@/common/moment';
import rfPickRegions from '@/components/rf-pick-regions';

export default {
	components: { avatar,rfPickRegions },
	data() {
		return {
			loadProgress: 0,
			CustomBar: this.CustomBar,
			profileInfo: {},
			skuId:0,
			token: null,
			loading: true,
			headImg: this.$mAssetsPath.headImg,
			userBg: this.$mAssetsPath.userBg,
			btnLoading: false
		};
	},
	onLoad(option) {
	this.profileInfo.skuId=option.id;
		this.profileInfo.point=option.point;
		this.initData();
		this.loading = false
	},
	methods: {
	// 获取选择的地区
  		handleGetRegions(e) {
  		console.log(e)
  			this.profileInfo.provinceId = e.provinceId;
  			this.profileInfo.cityId = e.cityId;
  			this.profileInfo.countryId = e.countryId;
  		},


		// 数据初始化
		initData() {
			this.token = uni.getStorageSync('accessToken') || undefined;
		},

		// 更新用户信息
		async toUpdateInfo() {
			this.handleUpdateInfo();
		},
		// 更新用户信息
		async handleUpdateInfo() {
			this.btnLoading = true;

			this.loadProgress = this.loadProgress + 6;
			const timer = setInterval(() => {
				this.loadProgress = this.loadProgress + 6;
			}, 50);
			await this.$http
				.post(`${submitpointmallorder}`, {
					...this.profileInfo
				})
				.then(() => {
					clearInterval(timer);
					this.loadProgress = 0;
					this.$mHelper.toast('恭喜您, 兑换成功!');
					 uni.redirectTo({
                                         	url: `/pages/user/account/integral`
                                         			});
				})
				.catch(() => {
					this.btnLoading = false;
				});
		}
	}
};
</script>

<style lang="scss" scoped>
page {
	background-color: $color-white;
}

.userinfo {
	.user-section {
		display: flex;
		align-items: center;
		justify-content: center;
		height: 320upx;
		padding: 40upx 30upx 0;
		overflow: hidden;
		position: relative;
		.bg {
			position: absolute;
			left: 0;
			top: 0;
			width: 100vw;
			opacity: 0.84;
		}

		.portrait-box {
			clear: both;
			z-index: 2;
		}

		.portrait {
			position: relative;
			width: 200upx;
			height: 200upx;
			border-radius: 50%;
			border: 6upx solid #fff;
		}

		.yticon {
			position: absolute;
			line-height: 1;
			z-index: 5;
			font-size: 48upx;
			color: #fff;
			padding: 4upx 6upx;
			border-radius: 6upx;
			background: rgba(0, 0, 0, 0.4);
		}

		.pt-upload-btn {
			right: 0;
			bottom: 10upx;
		}

		.bg-upload-btn {
			right: 20upx;
			bottom: 16upx;
		}
	}

	.input-content {
		padding: 40upx 60upx;

		.input-item {
			display: flex;
			padding: 0 30upx;
			background: $page-color-light;
			height: 80upx;
			line-height: 80upx;
			border-radius: 4px;
			margin-bottom: 30upx;

			&:last-child {
				margin-bottom: 0;
			}

			.tit {
				width: 120upx;
				font-size: $font-sm + 2upx;
				color: $font-color-base;
			}

			input {
				width: calc(100% - 100upx);
				height: 80upx;
				line-height: 80upx;
				font-size: $font-base + 2upx;
				color: $font-color-dark;
			}

			.date {
				height: 80upx;
				line-height: 80upx;
				font-size: $font-base + 2upx;
				color: $font-color-dark;
			}

			.gender-item {
				margin-right: 20upx;

				.gender-item-text {
					padding-left: 10upx;
				}

				radio .wx-radio-input.wx-radio-input-checked {
					background: $uni-color-primary;
					border-color: $uni-color-primary;
				}
			}
		}
	}
}
</style>

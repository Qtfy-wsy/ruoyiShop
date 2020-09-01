<template>
	<view class="account-bill">
		<!--顶部账单类型 全部/充值/消费-->
		<view class="navbar">
			<view
				v-for="(item, index) in billTypeList"
				:key="index"
				class="nav-item"
				:class="tabCurrentIndex === index ? `text-${themeColor.name} current` : ''"
				@tap="tabClick(index, item.state)"
			>
				{{ item.text }}
			</view>
		</view>
		<!--账单明细列表-->
		<view class="rf-bill-list" v-if="integralList.length > 0">
			<view
				class="rf-list-item"
				v-for="(item, index) in integralList"
				:key="index"
			>
				<view class="mid">
					<view class="title">
						支付宝账号:{{ item.account }}
					</view>
					<view class="time">
						{{ item.createTime }}
					</view>
				</view>
				<view class="right">
					<text
						class="change-num"
						:class="
							(item.status == 2 ) ? 'change-num-reduce' : 'change-num-add'
						"
						>{{ item.money | numFilter }}
					</text>
				</view>
			</view>
			<rf-load-more
				class="load-more"
				:status="loadingType"
				v-if="integralList.length > 0"
			></rf-load-more>
		</view>
		<rf-empty
			:info="'暂无账单记录'"
			v-if="integralList.length === 0 && !loading"
		></rf-empty>
		<!--加载动画-->
		<rfLoading isFullScreen :active="loading"></rfLoading>
	</view>
</template>
<script>
/**
 * @des 账单明细
 *
 * @author stav stavyan@qq.com
 * @date 2020-01-13 11:18
 * @copyright 2019
 */
import { querywithdrawrecords } from '@/api/sms';
import rfLoadMore from '@/components/rf-load-more/rf-load-more.vue';
import moment from '@/common/moment';

export default {
	components: {
		rfLoadMore
	},
	data() {
		return {
			loading: true,
			typeClass: 'valid',
			state: -1,
			tabCurrentIndex: 0,
			billTypeList: [ // 0 申请  1 审核通过 2 拒绝 3 已打款
				{
					state: -1,
					text: '全部'
				},
				{
					state: 0,
					text: '申请'
				},
				{
					state: 1,
					text: '审核通过'
				},{
          					state: 2,
          					text: '拒绝'
          				},
          				{
          					state: 3,
          					text: '已打款'
          				}
			],
			integralList: [],
			loadingType: 'more',
			pageNum: 0
		};
	},
	filters: {
		time(val) {
			return moment(val * 1000).format('YYYY-MM-DD HH:mm:ss');
		},
		numFilter(val) {
			return val >= 0 ? `+${val.toString()}` : val;
		}
	},
	// 下拉刷新
	onPullDownRefresh() {
		this.pageNum = 0;
		this.integralList = [];
		this.getIntegralListList('refresh');
	},
	// 加载更多
	onReachBottom() {
  if (this.loadingType === 'nomore') return;
		this.pageNum++
		this.getIntegralListList();
	},
	onLoad(options) {
		this.initData(options);
	},
	methods: {
		// 顶部tab点击
		tabClick(index, state) {
			this.pageNum = 0;
			this.integralList.length = 0;
			this.loading = true;
			this.tabCurrentIndex = index;
			this.state = state;
			this.getIntegralListList();
		},
		// 数据初始化
		initData(options) {
			this.state = parseInt(options.state, 10);
			if (this.state === 2) {
				this.tabClick(1, 2);
				return;
			} else if (this.state === 3) {
				this.tabClick(2, 3);
				return;
			}
			this.getIntegralListList();
		},
		// 获取积分
		async getIntegralListList(type) {
			const params = {};
			console.log(this.state)

			if (this.state === -1 || this.state ===NaN || params.status ===NaN) {
			console.log(this.state)
				params.status =''
			} else  {
				params.status = this.state;
			}
			params.pageNum = this.pageNum ;
			await this.$http
				.get(`${querywithdrawrecords}`, {
					...params
				})
				.then(r => {
					this.loading = false;
					if (type === 'refresh') {
						uni.stopPullDownRefresh();
					}
					if(r.list){
					this.loadingType = r.list.length === 10 ? 'more' : 'nomore';
					this.integralList = [...this.integralList, ...r.list];
					}
				})
				.catch(() => {
					this.loading = false;
					if (type === 'refresh') {
						uni.stopPullDownRefresh();
					}
				});
		}
	}
};
</script>
<style scoped lang="scss">
page {
	background-color: $page-color-base;
}
.account-bill {
	.rf-bill-list {
		.rf-list-item {
			background-color: $color-white;
			padding: $spacing-base $spacing-lg;
			border: 1upx solid rgba(0, 0, 0, .02);
			display: flex;
			justify-content: space-between;
			align-items: center;
			.mid {
				text-align: left;
				.title {
					margin-top: 10upx;
					font-size: $font-base + 2upx;
					color: $font-color-dark;
				}
				.time {
					margin-top: 10upx;
					font-size: $font-base;
					margin-left: $spacing-sm;
					color: $font-color-light;
				}
			}
			.right {
				.change-num {
					font-size: $font-lg + 4upx;
					color: $font-color-base;
				}
				.change-num-add {
					color: #16ac57;
				}
				.change-num-reduce {
					color: #fc4141;
				}
			}
		}
	}
}
</style>

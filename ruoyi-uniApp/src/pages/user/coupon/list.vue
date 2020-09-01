<template>
	<view class="coupon-center">
		<view class="coupon-list">
			<!-- 优惠券列表 -->
			<view class="sub-list valid">
				<view
					class="row"
					v-for="(item, index) in couponList"
					:key="index"
					@tap.stop="getCoupon(item)"
				>
					<view class="carrier" :class="item.is_get === 0 ? 'rf-opacity' : ''">
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
									parseInt(item.status, 10) === 2
										? '已领完'
										: '未领完'
								}}
							</text>
							<view>
								{{
									`每人限领${item.limitNum}`
								}}
									总数 {{ item.num }}
                											<text class="last" v-if="item.percentage"
                												>剩余{{ item.percentage }}%</text
                											>
							</view>
						</view>
					</view>
				</view>
			</view>
			<rf-load-more
				:status="loadingType"
				v-if="couponList.length > 0"
			></rf-load-more>
		</view>
		<rf-empty
			:info="errorInfo || '暂无优惠券'"
			v-if="couponList.length === 0 && !loading"
		></rf-empty>
		<!--页面加载动画-->
		<rfLoading isFullScreen :active="loading"></rfLoading>
	</view>
</template>

<script>
/**
 * @des 领取中心
 *
 * @author stav stavyan@qq.com
 * @date 2020-01-13 11:28
 * @copyright 2019
 */
import { couponList } from '@/api/userInfo';
import { acceptCoupon } from '@/api/sms';
import rfLoadMore from '@/components/rf-load-more/rf-load-more';
import moment from '@/common/moment';
export default {
	components: {
		rfLoadMore
	},
	data() {
		return {
			couponList: [],
			type: null,
			storeId:0,
			loadingType: 'more',
			pageNum :0,
			loading: true,
			moneySymbol: this.moneySymbol,
			errorInfo: ''
		};
	},
	filters: {
		time(val) {
			return moment(val * 1000).format('YYYY-MM-DD HH:mm');
		}
	},
	onLoad(options) {
		this.type = options.type;
		this.storeId = options.storeId;
		this.initData();
	},
	// 下拉刷新
	onPullDownRefresh() {
		this.pageNum = 0;
		this.couponList.length = 0;
		this.getCouponList('refresh');
	},
	// 加载更多
	onReachBottom() {
  if (this.loadingType === 'nomore') return;
		this.pageNum++
		this.getCouponList();
	},
	methods: {
		// 数据初始化
		initData() {
			this.getCouponList();
		},
		// 获取收货地址列表
		async getCouponList(type) {
			await this.$http
				.get(`${couponList}`, {
					pageNum: this.pageNum,
					storeId:this.storeId
				})
				.then(r => {
					this.loading = false;
					if (type === 'refresh') {
						uni.stopPullDownRefresh();
					}
					if(r.list){
					this.loadingType = r.list.length === 10 ? 'more' : 'nomore';
					this.couponList = [...this.couponList, ...r.list];
					}
				})
				.catch(err => {
					this.couponList.length = 0;
					this.errorInfo = err;
					this.loading = false;
					if (type === 'refresh') {
						uni.stopPullDownRefresh();
					}
				});
		},
		// 获取优惠券
		async getCoupon(item) {

			await this.$http
				.get(`${acceptCoupon}`, {
        						id: item.id
        					})
				.then(r => {
				if(r==1){
                    						this.$mHelper.toast('领取成功');
                    					}else if(r==-1){
                    						this.$mHelper.toast('参数不全');
                    						return false;
                    					}else if(r==-2){
                               						this.$mHelper.toast('活动已过期');
                               						return false;
                              	}else if(r==-3){
                                 						this.$mHelper.toast('优惠券已领完');
                                 						return false;
                                 					}else if(r==-4){
                                           						this.$mHelper.toast('用户领取的优惠券已达上限');
                                           						return false;
                                           					}else if(r==-5){
                                                     						this.$mHelper.toast('优惠券已失效(删除状态)');
                                                     						return false;
                                  	}else if(r==-6){
                                                                      						this.$mHelper.toast('系统繁忙，请重试');
                                                                      						return false;
                                                   	}
					this.pageNum = 0;
					this.couponList.length = 0;

					this.getCouponList();
				});
		}
	}
};
</script>
<style lang="scss"></style>

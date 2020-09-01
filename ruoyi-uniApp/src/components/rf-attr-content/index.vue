<template>
	<view class="attr-content">
		<view class="a-t">
			<image class="image" mode="widthFix" :src="picture || product.sku.url"></image>
			<view class="right">
				<text class="title in2line">{{ skuName }}</text>
				<view class="sku-info-wrapper">
				<!--	<view class="price-wrapper">
						<image mode="aspectFit" class="image" v-if="product.memberDiscount && product.memberDiscount.length !== 0 && product.memberDiscount.discount > 0 && price > 0" :src="vipPrice"></image>
						<text :class="'text-' + themeColor.name" v-if="price">{{ moneySymbol }}{{ currentProductPrice }}</text>
					</view> -->
					<text class="stock" v-if="stock">库存：{{ stock }}{{ product.unit || '件' }}</text>
					<view class="selected in2line" v-if="specSelected.length > 0">
						已选：
						<text
							class="selected-text"
							v-for="(sItem, sIndex) in specSelected"
							:key="sIndex"
						>
							{{ sItem.name }}
						</text>
						<text v-if="specSelected.length > 0"> * {{ cartCount }} </text>
					</view>
				</view>
			</view>
		</view>
		<scroll-view class="attr-content-scroll-view" scroll-y>
			<view v-for="(item, index) in specList" :key="index" class="attr-list">
				<text>{{ item.name }}</text>
				<view class="item-list">
					<view
						v-for="(childItem, childIndex) in specChildList"
						:key="childIndex"
						@tap="
							selectSpec(childIndex, childItem.specId, item.show_type)
						"
					>
						<view
							v-if="childItem.specId === item.id"
							:class="[childItem.selected ? 'bg-' + themeColor.name : 'tit-normal', childItem.disabled ? 'disabled' : '']"
							:style="
								childItem.selected && parseInt(item.show_type) === 2
									? styleObject
									: ''
							"
							class="tit"
						>
							<text >
								{{ childItem.name }}
							</text>
							<text v-if="parseInt(item.show_type) === 2">
								{{ childItem.name }}
							</text>
							<view v-if="parseInt(item.show_type) === 3">
								<image
									class="img"
									:src="childItem.data || product.picture"
									mode="aspectFill"
								></image>
								{{ childItem.title }}
							</view>
						</view>
					</view>
				</view>
			</view>
			<view class="select-count" v-if="isSelectedNum">
				<text>购买数量</text>
<!--				<rf-number-box-->
<!--					v-if="parseInt(stock || product.stock, 10) === 0"-->
<!--					class="step"-->
<!--					:disabled="true"-->
<!--					:min="0"-->
<!--					:max="0"-->
<!--					@eventChange="numberChange"-->
<!--				></rf-number-box>-->
				<rf-number-box
					class="step"
					:min="parseInt(product.min_buy, 10) || minNum"
					:max="parseInt(product.max_buy, 10) || (maxNum === 0 ? parseInt(stock, 10) : maxNum)"
					:value="cartCount"
					@eventChange="numberChange"
				></rf-number-box>

			</view>
		</scroll-view>
		<button v-if="!showBuyBtn" class="btn" :class="'bg-' + themeColor.name" @tap="toggleSpec">完成</button>
		<view class="btn-group" v-else>
			<button class="btn" :class="'bg-' + themeColor.name" @tap="toggleSpec(1)" v-if="product.marketings[0].type!='10'">加入购物车</button>
			<button class="btn" :class="'bg-' + themeColor.name" @tap="toggleSpec(2)" v-if="product.marketings[0].type!='10'">立即购买</button>
			<button class="btn" :class="'bg-' + themeColor.name" @tap="toggleSpec(1)" v-if="product.marketings[0].type=='10'">加入购物车1</button>
      			<button class="btn" :class="'bg-' + themeColor.name" @tap="toggleSpec(2)" v-if="product.marketings[0].type=='10'">立即购买1</button>
		</view>
	</view>
</template>
<script>
/**
 *@des 商品规范组件
 *@author stav stavyan@qq.com
 *@blog https://stavtop.club
 *@date 2020/05/03 19:17:15
 */
import rfNumberBox from '@/components/rf-number-box';
export default {
	name: 'rfAttrContent',
	components: { rfNumberBox },
	props: {
		showBuyBtn: {
			type: Boolean,
			default: false
		},
    isSelectedNum: {
			type: Boolean,
			default: true
    },
    type: {
			type: String,
			default: 'buy_now'
    },
		product: {
			type: Object,
			default() {
				return {};
			}
		},
		minNum: {
			type: Number,
			default: 1
		},
		maxNum: {
			type: Number,
			default: 0
		}
	},
	data() {
		return {
			styleObject: null,
			specList: [],
			specChildList: [],
			skuId: this.product && this.product.sku.id,
			price: null,
			stock: null,
			cartCount: parseInt(this.product.min_buy, 10) || this.minNum || 1,
			picture: null,
			specSelected: [],
			vipPrice: this.$mAssetsPath.vipPrice,
      moneySymbol: this.moneySymbol,
			skuName: null
		};
	},
	computed: {
		currentDiscountPrice() {
      const decimal = 1;
      const discount = 2;
      const price = this.price;
			switch (parseInt(decimal, 10)) {
				case -1:
					return (price * discount / 100).toFixed(2);
				case 0:
					return (price * discount / 100).toFixed(0);
				case 1:
					return (price * discount / 100).toFixed(1);
				default:
					return (price * discount / 100).toFixed(2);
			}
		},
		currentProductPrice () {
      let price = this.price;
      if (this.type === 'discount') {
        price = this.currentSkuPrice || this.currentDiscountPrice;
      }
      if (this.type === 'group_buy') {
        price = this.currentSkuPrice || this.product.marketing.ladder.price;
      }
      if (this.product.memberDiscount && this.product.memberDiscount.length !== 0) {
        price = price * (1 - this.product.memberDiscount.discount / 100);
      }
      return parseFloat(price || '0').toFixed(2);
		}
	},
	async mounted() {
		await this.initData();
	},
  methods: {
    initData() {

    this.product.skuSpecValues.forEach(item => {
    			this.specList.push(item.spec)
    			});
		//	this.specList = this.product.base_attribute_format;
			this.specList.forEach(item => {
				this.specChildList = [...this.specChildList, ...item.specValues];
				// if (!this.product.sku_name) {
				// 	item.value[0].selected = true;
				// 	this.specSelected.push(item.value[0]);
				// }
			});

				this.specChildList.forEach(item => {
				console.log(this.product.sku.subtitle)
					if (this.product.sku.subtitle.indexOf(item.name) !== -1) {
						item.selected = true;

						this.specSelected.push(item);
					}
				});

			let skuStrArr = [];
			this.specSelected.forEach(item => {
				skuStrArr.push(item.name);
			});

			this.product.skuList.forEach(item => {

				if (item.subtitle === skuStrArr.join('-')) {

					this.stock = item.stock;
          if (this.type === 'buy_now') {
            this.price = item.price;
          } else {
           // this.price = this.product.marketing_type === 'wholesale' ? item.wholesale_price : item.price;
          }
					this.skuName = item.name;
					this.skuId = item.id;
				}
			});
				console.log(this.specSelected)
    },
		numberChange(data) {
      this.cartCount = parseInt(data.number, 10);
		},
		// 选择规格
		selectSpec(index, pid, type) {
			let list = this.specChildList;
				console.log(pid)
				console.log(this.specChildList)
			list.forEach(item => {
				if (item.specId === pid) {
					this.$set(item, 'selected', false);
				}
			});
			if (parseInt(type, 10) === 3) {
				this.picture = list[index].data;
			}
			if (parseInt(type, 10) === 2) {
				this.styleObject = {
					color: list[index].data,
					border: `1px solid ${list[index].data}`
				};
			}
			this.$set(list[index], 'selected', true);
			// 存储已选择
			/**
			 * 修复选择规格存储错误
			 * 将这几行代码替换即可
			 * 选择的规格存放在specSelected中
			 */
			this.specSelected = [];
			list.forEach(item => {
				if (item.selected === true) {
					this.specSelected.push(item);
				}
			});
			let skuStr = [];
			this.specSelected.forEach(item => {
				skuStr.push(item.name);
			});
			console.log(this.specSelected)
			this.product.skuList.forEach(item => {
				if (item.subtitle === skuStr.join('-')) {
					console.log(item)
					this.picture = item.picture;
					this.stock = item.stock;
					this.price = this.product.marketing_type === 'wholesale' ? item.wholesale_price : item.price;
					this.skuId = item.id;
					this.skuName = item.name;
				}

			});
		},
		toggleSpec(type) {
			if (!this.skuId) {
				this.$mHelper.toast('请选择规格');
				return;
			}
      if (this.stock < 1) {
				this.$mHelper.toast('库存不足');
				return;
      }
			this.$emit('toggle', {
				stock: this.stock,
				skuInfo:this.skuId + ',' + this.cartCount,
				skuId: this.skuId,
				cartCount: this.cartCount,
				skuName: this.skuName || this.singleSkuText,
				price: this.price,
				type: type
			});
		}
	}
};
</script>
<style scoped lang="scss">
	.sku-info-wrapper {
		width: 100%;
		padding-bottom: $spacing-sm;
	}
	.price-wrapper {
		height: 38upx;
		display: flex;
		align-items: center;
		margin: $spacing-sm 0;
		.image {
			width: 120upx;
			height: 38upx;
		}
		.base-color {
			margin-top: 2upx;
		}
	}
	.btn-group {
		display: flex;
		justify-content: space-between;
		.btn {
			width: 40vw;
		}
	}
</style>

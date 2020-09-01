<template>
<view>
<template name="noLoginCart">
    <view class="shopcart_empty_box">
        <view class="shopcart_login_bar">登录后可同步账户购物车中的商品
            <view class="shopcart_login_bar_btn" @tap="toLogin">登录</view>
        </view>
        <view class="shopcart_empty">
            <image src="/static/img/cart_empty.png"></image>
            <view>购物车是空的,您可以
                <navigator url="/pages/index/index" open-type="switchTab">去逛逛</navigator>
            </view>
        </view>
    </view>
</template>

<template name="emptyCart">
    <view class="shopcart_empty_box">
        <view class="shopcart_empty">
            <image src="/static/img/cart_empty.png"></image>
            <view>购物车是空的,您可以
                <navigator url="/pages/index/index" open-type="switchTab">去逛逛</navigator>
            </view>
        </view>
    </view>
</template>

<template name="loginCart">
    <view class="listContent">
        <view class="weui-cells weui-cells_after-title">
            <view class="weui-cell" style="justify-content: space-between">
                <view></view>
                <view style="text-align: right" @tap="editCart">{{editMode?'完成':'编辑'}}</view>
            </view>
        </view>
        <block v-for="(storeCart, index) in carts" :key="index">
        <view class="cart_section">
            <view class="head_wrap">
                <view :class="'head shop_head ' + (storeCart.checked?'selected':'')">
                    <icon class="icon_select" @tap="checkStore" :data-storeid="storeCart.storeId"></icon>
                    <icon class="icon_shop" v-if="storeCart.storeId!==0"></icon>
                    <view class="title oneline">{{storeCart.storeName}}
                        <icon class="icon_arrow_right"></icon>
                    </view>
                </view>
                <block v-for="(marketing, index2) in storeCart.marketings" :key="index2">
                <view class="item item_connect">
                    <view class="head header">
                        <text class="tag">{{marketingTypeName[marketing.marketingType]}}</text>
                        <text class="title">{{marketing.marketingDesc}}</text>
                    </view>
                    <block v-for="(sku, index) in marketing.skus" :key="index">
                    <view :class="'goods ' + ((index===marketing.skus.length-1)&&(!showGift&&!(giftMarketingShow[marketing.id].length>0))?'goods_last':'') + '   ' + (sku.checked?'selected':'')">
                        <icon class="icon_select" @tap="checkSku" :data-cartid="sku.cartId"></icon>
                        <image class="image" :src="sku.image" @tap="toSkuDetail" :data-skuid="sku.skuId"></image>
                        <view class="image_tag" v-if="!!cart.loadStock(sku.num,sku.stock,sku.limitStock)">{{cart.loadStock(sku.num,sku.stock,sku.limitStock)}}</view>
                        <view class="goodscontent">
                            <view class="name"><text class="mod_sign_tip" v-if="sku.isVirtual == '1'"><text>虚拟</text></text>{{sku.name}}</view>
                            <view @tap="queryMarketing" class="select_sales" :data-skuid="sku.skuId" :data-marketingid="sku.marketingId" :data-cartid="sku.cartId">选择促销</view>
                            <view class="goods_line">
                                <view class="price">¥
                                    <text class="int">{{toFixed(sku.price,0)}}</text>{{cart.getDecimalsPart(sku.price)}}</view>
                                <view class="num_and_more">
                                    <view class="num_wrap">
                                        <text :class="'minus ' + (sku.num<=sku.limitCartNum?'disabled':'')" @tap="minusCartNum" :data-cartid="sku.cartId"></text>
                                        <view class="input_wrap">
                                            <input class="num" type="num" :value="sku.num" @input="inputCartNum" :data-cartid="sku.cartId"></input>
                                        </view>
                                        <text :class="'plus ' + (((sku.limitStock !== -2 && sku.limitStock < sku.num+1)||(sku.stock < sku.num+1))?'disabled':'')" @tap="plusCartNum" :data-cartid="sku.cartId"></text>
                                    </view>
                                </view>
                            </view>
                        </view>
                    </view>
                    </block>
                    <view v-for="(giftSku, giftSkuIndex) in giftMarketingShow[marketing.id]" :key="giftSkuIndex" :hidden="!showGift&&!(giftMarketingShow[marketing.id].length>0)">
                    <view :class="'goods type_additional ' + (marketing.fullGifts.length-1===giftSkuIndex?'goods_last':'')">
                        <icon class="icon_select"></icon>
                        <image class="image" :src="giftSku.sku.url"></image>
                        <view class="goodscontent">
                            <view class="name">
                                <text class="mod_sign_tip"><text>满赠</text></text>{{giftSku.sku.name}}</view>
                            <view class="goods_line" style="margin: 10px 0 14px 0">
                                <view class="price">¥
                                    <text class="int">0</text>.00</view>
                                <view>×{{giftSku.num}}</view>
                            </view>
                        </view>
                    </view>
                    </view>
                </view>
                </block>

                <block v-for="(sku, index2) in storeCart.normalSkus" :key="index2">
                    <view class="item">
                    <view :class="'goods ' + (sku.checked?'selected':'')">
                        <icon class="icon_select" @tap="checkSku" :data-cartid="sku.cartId"></icon>
                        <image class="image" :src="sku.image" @tap="toSkuDetail" :data-skuid="sku.skuId"></image>
                        <view class="image_tag" v-if="!!cart.loadStock(sku.num,sku.stock,sku.limitStock)">{{cart.loadStock(sku.num,sku.stock,sku.limitStock)}}</view>
                        <view class="goodscontent">
                            <view class="name"><text class="mod_sign_tip" v-if="sku.isVirtual == '1'"><text>虚拟</text></text>{{sku.name}}</view>
                            <view @tap="queryMarketing" class="select_sales" :data-skuid="sku.skuId" :data-marketingid="sku.marketingId" :data-cartid="sku.cartId">选择促销</view>
                            <view class="goods_line">
                                <view class="price">¥
                                    <text class="int">{{toFixed(sku.price,0)}}</text>{{cart.getDecimalsPart(sku.price)}}</view>
                                <view class="num_and_more">
                                    <view class="num_wrap">
                                        <text :class="'minus ' + (sku.num<=sku.limitCartNum?'disabled':'')" @tap="minusCartNum" :data-cartid="sku.cartId"></text>
                                        <view class="input_wrap">
                                            <input class="num" type="num" :value="sku.num" @input="inputCartNum" :data-cartid="sku.cartId"></input>
                                        </view>
                                        <text :class="'plus ' + (((sku.limitStock !== -2 && sku.limitStock < sku.num+1)||(sku.stock < sku.num+1))?'disabled':'')" @tap="plusCartNum" :data-cartid="sku.cartId"></text>
                                    </view>
                                </view>
                            </view>
                        </view>
                    </view>
                    </view>
                </block>
            </view>
        </view>
        </block>
    </view>
    <view :class="'fixBar ' + (allCheck?'selected':'') + ' ' + (isIphoneX&&!tabBar?'fixBar-iphoneX':'')">
        <icon class="icon_select" @tap="allCheck">全选</icon>
        <view class="total" :hidden="editMode">
            <view class="p">
                <view>合计：
                    <text class="strong">¥{{toFixed(totalPrice)}}</text>
                </view>
                <view class="small">已优惠¥{{toFixed(totalDiscountPrice)}}</view>
            </view>
            <navigator url class="buy" @tap="toSettlement">去结算
                <text>({{chooseNum}}件)</text>
            </navigator>
        </view>
        <view class="btns" :hidden="!editMode">已选({{chooseNum}})
            <navigator url class="btn_3" @tap="deleteCart">删除</navigator>
        </view>
    </view>
    <view :hidden="salesPicker">
        <view class="weui-mask" @tap="salesClose"></view>
        <view class="weui-picker" :animation="animationData">
            <view class="weui-picker__hd">
                <text>选择促销</text>
                <text class="weui-picker__action" @tap="salesClose">取消</text>
            </view>
            <scroll-view class="weui-picker__bd" scroll-y>
                <view class="weui-cells weui-cells_after-title" style="width:100%">
                    <radio-group @change="radioChange">
                        <label v-for="(item, index) in skuMarketingArray" :key="index" class="weui-cell weui-check__label">
                            <radio class="weui-check" :value="item.value" :checked="item.checked"></radio>
                            <view class="weui-cell__bd">{{item.name}}</view>
                            <view class="weui-cell__ft weui-cell__ft_in-radio" v-if="item.checked">
                                <icon class="weui-icon-checkbox_success" type="success" size="18" color="#ffc927"></icon>
                            </view>
                        </label>
                    </radio-group>
                </view>
            </scroll-view>
        </view>
    </view>
</template>
</view>
</template>

<script module="cart" lang="wxs" src="./cart.wxs"></script>
<script module="toFixed" lang="wxs" src="../../utils/toFixed.wxs"></script>


	<script> 
		
		export default {
			props: []
		}
    </script> 
    
									
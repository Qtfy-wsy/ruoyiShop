/**
 *@des 产品营销
 *@author stav stavyan@qq.com
 *@blog https://stavtop.club
 *@date 2019/11/18 13:57:54
 */
// 首页列表
const indexList = '/tiny-shop/v1/index/index';
const queryAllFirstAndSecondCategory= '/queryAllFirstAndSecondCategory';
const storeList = '/store/searchstore';
// 产品分类列表
const productCate = '/typeList';
// 首页推荐分类
const productCateList = '/tiny-shop/v1/product/cate/list';
// 产品列表
const productList = '/goodsList';
// 产品详情
const productDetail = '/queryGoodsDetail';
const productGroupDetail = '/spudetail/grouporders'; // 产品详情中 拼团列表

// 组合套餐详情
const combinationView = '/tiny-shop/v1/marketing/combination/view';
// 猜您喜欢
const guessYouLikeList = '/recommendskus';
// 添加购物车
const cartItemCreate = '/shoppingcart/addshoppingcart';
// 查询单品的促销信息
const marketings= '/shoppingcart/marketings';
// 购物车列表
const cartItemList = '/shoppingcart/carts';
// 购物车列表
const cartItemCount = '/shoppingcart/count';
// 更新购物车促销信息
const updatecartmarketing= '/shoppingcart/updatecartmarketing';
// 删除购物车商品
const cartItemDel = '/shoppingcart/deletecart';
// 清空购物车
const cartItemClear = '/shoppingcart/clear';
// 修改购物车商品数量
const cartItemUpdateNum = '/shoppingcart/cartnum';
// 修改购物车商品sku
const cartItemUpdateSku = '/shoppingcart/update-sku';

// 品牌列表
const brandIndex = '/brandList';
// 用户中心 订单数量
const orderCountStatisic = '/order/count';

// 订单创建
const orderCreate = '/submitorder';
// 订单预览
const orderPreview = '/settlement';
//查询订单评价详情
const queryorderforevaluation= '/queryorderforevaluation';

const evaluationdetail= '/evaluationdetail';
// 添加订单评论
const addorderevaluation = '/addorderevaluation';
// 取消未支付订单
const orderClose = '/cancelorder';
// 预存款支付
const toprepay = '/toprepay';
// 微信小程序支付
const wechatappletpayparams = '/wechatappletpayparams';
// 支付宝h5支付
const toaliwappay = '/toaliwappay';
// 微信H5支付
const wechath5payparm = '/wechath5payparm';

// 微信公众号支付
const wechatofficialaccountpayparm = '/wechatofficialaccountpayparm';
// 选择快递运费计算
const orderFreightFee = '/calculatefreight';

// 商品评价列表
const evaluateList = '/queryskucomments';

// 商品评价列表
const orderProductExpressDetails =
	'/tiny-shop/v1/member/order-product-express/details';

// 拼团
// 拼团产品
const wholesaleProductIndex = '/tiny-shop/v1/marketing/wholesale-product/index';
// 开团列表
const wholesaleIndex = '/tiny-shop/v1/marketing/wholesale/index';
// 开团详情
const wholesaleView = '/tiny-shop/v1/marketing/wholesale/view';
// 开团详情
const wholesaleGroupState = '/tiny-shop/v1/marketing/wholesale/group-state';
// 我的开团列表
const myWholesaleIndex = '/tiny-shop/v1/member/wholesale/index';

// 限时折扣
// 限时折扣列表
const discountProductIndex = '/tiny-shop/v1/marketing/discount-product/index';

// 团购
// 团购商品列表
const groupBuyIndex = '/tiny-shop/v1/marketing/group-buy/index';

// 砍价
// 砍价商品列表
const bargainProductIndex = '/tiny-shop/v1/marketing/bargain-product/index';
// 创建砍价
const bargainLaunchCreate = '/tiny-shop/v1/marketing/bargain-launch/create';
// 砍价详情
const bargainLaunchView = '/tiny-shop/v1/marketing/bargain-launch/view';
// 帮好友砍价
const bargainPartakeCreate = '/tiny-shop/v1/marketing/bargain-partake/create';
// 我的砍价列表
const bargainLaunchIndex = '/tiny-shop/v1/marketing/bargain-launch/index';

// 我的砍价列表
const miniProgramLiveIndex = '/tiny-shop/v1/marketing/mini-program-live/index';

// 我的砍价列表
const thirdPartyQrCode = '/tiny-shop/v1/third-party/qr-code';

const queryskucomments= '/queryskucomments';//查询单品评论
const querycommentsummarize= '/querycommentsummarize'; // 查询商品的评论概括

export {
	queryAllFirstAndSecondCategory,productGroupDetail,
	storeList,
	updatecartmarketing,
	queryorderforevaluation,
	addorderevaluation,
	evaluationdetail,
	queryskucomments,
	querycommentsummarize,
	indexList,
	productCate,
	productCateList,
	productList,
	guessYouLikeList,
	productDetail,
	combinationView,
	cartItemCreate,
	cartItemList,
	cartItemDel,
	cartItemClear,
	cartItemUpdateNum,
	brandIndex,
	orderCreate,
	orderClose,
	toprepay,
	marketings,
	wechatappletpayparams,
	toaliwappay,
	wechath5payparm,
	wechatofficialaccountpayparm,
	orderFreightFee,
	evaluateList,
	orderCountStatisic,
	orderPreview,
	orderProductExpressDetails,
	cartItemUpdateSku,
	cartItemCount,
	wholesaleProductIndex,
	wholesaleIndex,
	wholesaleView,
	wholesaleGroupState,
	myWholesaleIndex,
	discountProductIndex,
	groupBuyIndex,
	bargainProductIndex,
	bargainLaunchCreate,
	bargainLaunchView,
	bargainPartakeCreate,
	bargainLaunchIndex,
	miniProgramLiveIndex,
	thirdPartyQrCode
};

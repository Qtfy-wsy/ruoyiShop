/**
 *@des 公用基础
 *@author stav stavyan@qq.com
 *@blog https://stavtop.club
 *@date 2019/11/19 14:56:57
 *@param registerByPass
 */
const querybaseinfoset = '/querybaseinfoset';
// 获取省市区列表
const provinceList = '/queryallprovinces';

const cityList = '/querycitybyprovinceid';

const regionList = '/querydistrictbycityid';
// 收藏商品
const collectCreate = '/addattention';
// 删除收藏商品
const collectDel = '/cancelattention';

// 分享/转发
const transmitCreate = '/tiny-shop/v1/common/transmit/create';

// 广告
const advList = '/tiny-shop/v1/common/adv/index';
const advView = '/tiny-shop/v1/common/adv/view';

// 配置
const configList = '/tiny-shop/v1/common/config/index';

// APP支付宝支付
const aliPay = '/aliPay';
// 支付宝h5支付
const toaliwappay = '/toaliwappay';
// 支付宝pc支付
const toalipagepay = '/toalipagepay';
// 获取微信公众号支付
const wechatofficialaccountpayparm = '/wechatofficialaccountpayparm';
// 获取微信H5支付参数
const wechath5payparm = '/wechath5payparm';
// 获取微信小程序支付
const wechatappletpay = '/wechatappletpayparams';
// APP微信支付
const wxpay = '/wxpay';

// 充值配置
const wechatConfig = '/tiny-shop/v1/third-party/wechat-js-sdk';

// 公告
// 公告列表
const notifyAnnounceIndex = '/tiny-shop/v1/common/notify-announce/index';
// 公告详情
const notifyAnnounceView = '/tiny-shop/v1/common/notify-announce/view';

// 版本检测
const versionsIndex = '/app-versions/versions/index';

// 版本检测
const pickupPointIndex = '/tiny-shop/v1/common/pickup-point/index';

// 站点帮助-列表
const helperIndex = '/help/cate';
// 站点帮助-详情
const helperView = '/help/desc';

// 站点帮助-列表
const articleIndex = '/article/column';
// 站点帮助-详情
const articleView = '/article';

// 设备绑定(app推送)
const bindingEquipment = '/tiny-shop/v1/member/auth/binding-equipment';
// 查询绑定状态
const authIsBinding = '/tiny-shop/v1/member/auth/is-binding';
// 第三方绑定
const authCreate = '/tiny-shop/v1/member/auth/create';

export {
	articleIndex,articleView,
	querybaseinfoset,
	provinceList,
	cityList,
	regionList,
	collectCreate,
	collectDel,
	transmitCreate,
	advList,
	advView,

	configList,
	wechatConfig,
	notifyAnnounceIndex,
	notifyAnnounceView,
	versionsIndex,
	pickupPointIndex,
	helperIndex,
	helperView,
	bindingEquipment,
	authIsBinding,
	authCreate,
 aliPay,
// 支付宝h5支付
 toaliwappay,
// 支付宝pc支付
 toalipagepay ,
// 获取微信公众号支付
 wechatofficialaccountpayparm,
// 获取微信H5支付参数
 wechath5payparm,
// 获取微信小程序支付
	wechatappletpay,
// APP微信支付
 wxpay
};

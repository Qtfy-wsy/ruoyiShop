/**
 *@des 公用基础
 *@author stav stavyan@qq.com
 *@blog https://stavtop.club
 *@date 2019/11/19 14:56:57
 *@param registerByPass
 */

// 人气商品
const homeNewProduct = '/homeNewProduct/list';
//推荐品牌
const homeBrand = '/homeBrand/list';
//推荐商品
const homeRecommendProduct = '/homeRecommendProduct/list';
// 推荐文章
const homeRecommendSubject = '/homeRecommendSubject/list';
// 轮播图
const bannerList = '/bannerList';
//查询首页商品属性分类
const typeList = '/type/list';
//查询首页通知
const noticeList = '/notice/list';
const noticeDetail = '/notice/detail';
const couponList= '/getcouponlist';// 优惠券列表
const acceptCoupon= '/receivecoupon';// 领取优惠券

const querypresalepic= '/querypresalepic';// 查询预售促销图片信息
const querypresalecates= '/querypresalecates';// 查询预售促销分类列表
const querypresales= '/querypresales';// 分页查询预售促销列表
const querypresalerule= '/querypresalerule';// 查询预售促销规则信息
const tryspudetail= '/tryspudetail';// 根据试用促销id查询试用商品详情
const storescore= '/storescore';//查看店铺评分
const tryskuapplybytryid= '/tryskuapplybytryid';// 根据试用促销id查询试用申请
const applytrysku= '/applytrysku';// 申请试用
const tryreportlist= '/tryreportlist';// 分页查询试用报告
const tryPic= '/try/pic';// 查询试用促销图片信息
const tryCate= '/tryCate';// 查询试用促销分类列表
const tryList= '/try';// 分页查询试用促销列表
const tryRule= '/tryRule';// 分页查询试用促销列表

const querycommissionrecords= '/querycommissionrecords';// 分页查询会员佣金记录
const querywithdrawrecords= '/querywithdrawrecords';// 分页查询会员提现记录
const addwithdrawrecord= '/addwithdrawrecord';// 增加会员提现记录
export {
	addwithdrawrecord,
	querypresalepic,querypresalecates,querypresales,querypresalerule,querycommissionrecords,querywithdrawrecords,
	tryspudetail,storescore,tryskuapplybytryid,applytrysku,tryreportlist,tryPic,tryCate,tryList,tryRule,
	acceptCoupon,
	couponList,
	typeList,
	noticeList,
	noticeDetail,
	bannerList,
	homeRecommendSubject,
	homeRecommendProduct,
	homeBrand,
	homeNewProduct,

};

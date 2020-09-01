const CONFIG = {
	// 开发环境配置
	development: {
		assetsPath: '/static', // 静态资源路径
		qqMapKey:'MVGBZ-R2U3U-W5CVY-2PQID-AT4VZ-PDF35',
	//	baseUrl: 'https://mojin.51wangshi.com/api-web',
		baseUrl: 'http://localhost:8091', // 后台接口请求地址
		hostUrl: 'https://mojin.51wangshi.com', // H5地址(前端运行地址)
		websocketUrl: '', // websocket服务端地址
		weixinAppId: 'wxd36292db0016c973' // 微信公众号appid
	},
	// 生产环境配置
	production: {
		assetsPath: '/static', // 静态资源路径
		baseUrl: 'https://mojin.51wangshi.com/api-web', // 后台接口请求地址
		hostUrl: 'https://mojin.51wangshi.com', // H5地址(前端运行地址)
		websocketUrl: '', // websocket服务端地址
		weixinAppId: '' // 微信公众号appid
	}

};
export default CONFIG[process.env.NODE_ENV];

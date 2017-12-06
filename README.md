# payshop
### qq 2123957932
qq群  203747031


### 支付聚合 https://gitee.com/catshen/xxpay-master

http://j2shop.tunnel.qydev.com/street/index
http://j2shop.tunnel.qydev.com/login

http://j2shop.tunnel.qydev.com/disShop/index
http://j2shop.tunnel.qydev.com/web/index

http://j2shop.tunnel.qydev.com/web/cms/index
http://j2shop.tunnel.qydev.com/blog/index


- 采用ssm 通用mapper bootstreap beetl模板 妹子ui 商城
- resources.properties 文件中有数据库设置和支付宝 和微信的设置
- pc首页 http://localhost:8080/zsShop
- 后台管理  http://localhost:8080/zsShop/login   admin  admin
-  首页2  http://localhost:8080/zsShop/web1
-  wap http://localhost:8080/zsShop/wap1
-  首页3 http://localhost:8080/zsShop/youhong
 
 20170426
 添加luence搜索
- 1、后端
- 核心框架：Spring Framework 4.0

-    mq通信框架  kafka  redis mongodb
- - 安全框架：Apache Shiro 1.2
   
- 视图框架：Spring MVC 4.0
- 服务端验证：Hibernate Validator 5.1
- 任务调度：Spring Task 4.0
- 持久层框架：MyBatis 3.2
- 数据库连接池：Alibaba Druid 1.0
- 缓存框架：Ehcache 2.6、Redis
- Luence搜索引擎
- 日志管理：SLF4J 1.7、Log4j2   logback
- 工具类：Apache Commons、Jackson 2.2、Xstream 1.4、Dozer 5.3、POI 3.9
- 2、前端
- JS框架：jQuery 1.9。
- CSS框架：bootstrap ace admin框架界面。
- 客户端验证：JQuery Validation Plugin 1.11。
- 富文本：CKEcitor
- 文件管理：CKFinder
- 百度 web upload 图片上传插件手机端框架：Jingle
- 数据表格：jqGrid
- 对话框：jQuery jBox
- 下拉选择框：jQuery Select2
- 树结构控件：jQuery zTree
- 日期控件： My97DatePicker

已完成功能


- 后台  用户管理   角色管理  菜单管理 组织管理 日志管理
- 监控  jvm监控 ehcache监控  durid数据库监控
- 商城  商品管理  首页菜单管理 楼层管理  商品类别  订单管理  文章管理
- 商城前台  主页菜单 楼层 文章 商品展示，商品详情展示 ，购物 添加商品到购物车，结算 微信支付，支付宝支付。

技术要点  


- 登录用户的浏览记录存redis ，hash存储 一周过期
- log4j2 通过配置直接将数据存入logstash ，然后通过elk展示分析

待做功能


- 用户登录或者注册送积分存入kafka，然后一个单独的项目消费kafka数据 ，将数据持久化到数据库
- 日志数据存入mongodb



### 使用技术和后台同 
 
http://git.oschina.net/catshen/cat
### 分布式版本

http://git.oschina.net/JiaGou-XiaoGe/shop-dubbox



商品管理
![输入图片说明](http://git.oschina.net/uploads/images/2017/0412/101314_811d498c_134431.png "在这里输入图片标题")
楼层管理
![输入图片说明](http://git.oschina.net/uploads/images/2017/0412/101322_82a87832_134431.png "在这里输入图片标题")


![输入图片说明](http://git.oschina.net/uploads/images/2017/0412/101514_9c2830ca_134431.png "在这里输入图片标题")
![输入图片说明](http://git.oschina.net/uploads/images/2017/0412/101530_73ba9328_134431.png "在这里输入图片标题")

###  请作者喝杯咖啡

![输入图片说明](https://git.oschina.net/uploads/images/2017/0829/203712_6694b4c1_134431.jpeg "weixin.jpg")
![输入图片说明](https://git.oschina.net/uploads/images/2017/0829/203723_5567bd56_134431.jpeg "alipay.jpg")
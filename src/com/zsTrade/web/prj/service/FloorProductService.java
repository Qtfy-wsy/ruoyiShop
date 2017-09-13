/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.prj.model.FloorProduct;

 /**
 * 
 * @author zsCat 2016-12-22 14:23:22
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	楼层产品管理
 */
public interface FloorProductService extends BaseService<FloorProduct>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param FloorProduct
	 * @return
	 */
	public int saveFloorProduct(FloorProduct FloorProduct) ;
	/**
	 * 删除
	* @param FloorProduct
	* @return
	 */
	public int deleteFloorProduct(FloorProduct FloorProduct);

	public PageInfo<FloorProduct> findPageInfo(Map<String, Object> params);

}

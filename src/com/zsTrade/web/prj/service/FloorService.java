/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.prj.model.Floor;

 /**
 * 
 * @author zsCat 2016-12-22 14:23:55
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	楼层管理
 */
public interface FloorService extends BaseService<Floor>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Floor
	 * @return
	 */
	public int saveFloor(Floor Floor) ;
	/**
	 * 删除
	* @param Floor
	* @return
	 */
	public int deleteFloor(Floor Floor);

	public PageInfo<Floor> findPageInfo(Map<String, Object> params);
	public Integer saveFloorProduct(Floor floor);

}

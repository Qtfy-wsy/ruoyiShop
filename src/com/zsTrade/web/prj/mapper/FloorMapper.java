/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.mapper;
import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.zsTrade.web.prj.model.Floor;


/**
 * 
 * @author zsCat 2016-12-22 14:23:55
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	楼层管理
 */
public interface FloorMapper extends Mapper<Floor>{
	public List<Floor> findPageInfo(Map<String, Object> params);

	public List<Long> findGoodsIdsByFloorId(Long id);

	public void deleteFloorGoodsIdsByFloorId(Long id);

	public void insertFloorGoodsIdsByFloorId(Floor floor);
	
}

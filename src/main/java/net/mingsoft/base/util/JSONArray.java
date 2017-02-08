package net.mingsoft.base.util;

import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * 
 * 铭飞MS平台-重写fastjson JSONArray类
 * @author 铭飞开发团队
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2016年8月23日<br/>
 * 历史修订：<br/>
 */
public class JSONArray{

	 public static final String toJSONString(Object object, SerializeFilter... filters) {
		return com.alibaba.fastjson.JSONArray.toJSONString(object, filters,SerializerFeature.WriteMapNullValue);
	 }
}

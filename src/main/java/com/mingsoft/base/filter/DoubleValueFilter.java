package com.mingsoft.base.filter;

import java.math.BigDecimal;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 
 * 铭飞ms平台－doublue小数位置过滤
 * @author 铭飞开发团队
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2016年8月23日<br/>
 * 历史修订：<br/>
 */
public class DoubleValueFilter implements ValueFilter {

	private int digits=2;
	
	public DoubleValueFilter(){}
	
	/**
	 *  指定保留小数个数
	 * @param digits 默认2位
	 */
	public DoubleValueFilter(int digits){
		this.digits = digits;
	}
	
	@Override
	public Object process(Object object, String name, Object value) {
		// TODO Auto-generated method stub
		if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
			return new BigDecimal(value.toString()).setScale(digits, BigDecimal.ROUND_HALF_UP);
		}
		return value;
	}

}

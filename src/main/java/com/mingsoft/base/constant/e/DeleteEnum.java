package com.mingsoft.base.constant.e;

import com.mingsoft.base.constant.e.BaseEnum;

/**
 * @author yangxy
 * @version 
 * 版本号：【100-000-000】
 * 创建日期：2015年10月18日 
 * 历史修订：
 */
public enum DeleteEnum implements BaseEnum{
	/**
	 * 伪删除（DEL已删除,值为1）
	 */
	DEL(1,"已删除"), 
	
	/**
	 * 伪删除（NOTDEL正常,值为0）
	 */
	NOTDEL(0,"正常");
	
	private String code;
	
	private int id;

	/**
	 * 构造方法
	 * @param id 默认ID
	 * @param code 传入的枚举类型
	 */
	DeleteEnum(int id,String code) {
		this.code = code;
		this.id = id;
	}

	@Override
	public int toInt() {
		return this.id;
	}

	@Override
	public String toString() {
		return this.code.toString();
	}
}
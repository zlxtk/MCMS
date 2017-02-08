/**
The MIT License (MIT) * Copyright (c) 2016 铭飞科技(mingsoft.net)

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mingsoft.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mingsoft.base.constant.e.DeleteEnum;

/**
 * 基础实体类
 * @author 王天培QQ:78750478
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2012-03-15<br/>
 * 历史修订：<br/>
 */
public abstract class  BaseEntity implements Serializable{
	
	/**
	 * 创建用户编号
	 */
	@JsonIgnore
	@XmlTransient	
	protected int createBy;
	/**
	 * 创建日期
	 */
	protected Date createDate;

	/**
	 * 标记
	 */
	@JsonIgnore
	@XmlTransient	
	protected int delFlag;
	
	/**
	 * 实体编号（唯一标识）
	 */
	protected String id;	
	
	/**
	 * 备注
	 */
	protected String remarks;	
	
	/**
	 * 最后更新用户编号
	 */
	@JsonIgnore
	@XmlTransient	
	protected int updateBy;		
	
	/**
	 * 最后更新日期
	 */
	protected Date updateDate;

	/**
	 * 自定义SQL where条件，需要配合对应dao.xml使用
	 */
	@JsonIgnore
	@XmlTransient
	protected String sqlWhere;
	
	/**
	 * 自定义SQL where条件，需要配合对应dao.xml使用
	 */
	@JsonIgnore
	@XmlTransient
	protected String sqlDataScope;
	
	/**
	 * 排序字段
	 */
	@JsonIgnore
	@XmlTransient
	protected String orderBy;

	/**
	 * 排序方式
	 */
	private boolean order = true;

	public int getCreateBy() {
		return createBy;
	}

	public void setCreateBy(int createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(DeleteEnum delFlag) {
		this.delFlag = delFlag.toInt();
	}
	
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(int updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@JsonIgnore
	@XmlTransient
	public String getSqlWhere() {
		return sqlWhere;
	}

	public void setSqlWhere(String sqlWhere) {
		this.sqlWhere = sqlWhere;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isOrder() {
		return order;
	}

	public void setOrder(boolean order) {
		this.order = order;
	}

	public String getSqlDataScope() {
		return sqlDataScope;
	}

	public void setSqlDataScope(String sqlDataScope) {
		this.sqlDataScope = sqlDataScope;
	}
	
	
	
}
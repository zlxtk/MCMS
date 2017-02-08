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

package com.mingsoft.base.biz;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mingsoft.base.constant.e.TableEnum;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.util.PageUtil;

/**
 * 基础业务类
 * 
 * @author 王天培QQ:78750478
 * @version 版本号：100-000-000<br/>
 *          创建日期：2012-03-15<br/>
 *          历史修订：<br/>
 */
public interface IBaseBiz {

	/**
	 * 修改表
	 * 
	 * @param table
	 *            表名称
	 * @param fileds
	 *            key:字段名称 list[0] 类型 list[1]长度 list[2]默认值 list[3]是否不填
	 */
	@SuppressWarnings("rawtypes")
	@Deprecated
	void alterTable(String table, Map fileds, String type);

	/**
	 * 修改表
	 * 
	 * @param table
	 *            表名
	 * @param fileds
	 *            修改字段
	 * @param type
	 *            修改类型 @see TableEnum
	 */
	void alterTable(String table, Map fileds, TableEnum type);

	/**
	 * 查询表中记录总数
	 * 
	 * @param table
	 *            表名称
	 * @param wheres
	 *            条件 都是key-value对应
	 * @return 返回查询总数
	 */
	int countBySQL(String table, Map wheres);

	/**
	 * 创建表
	 * 
	 * @param table
	 *            表名称
	 * @param fileds
	 *            key:字段名称 list[0] 类型 list[1]长度 list[2]默认值 list[3]是否不填
	 */
	@SuppressWarnings("rawtypes")
	void createTable(String table, Map<Object, List> fileds);

	/**
	 * 根据id集合实现批量的删除
	 * 
	 * @param ids
	 *            id集合
	 */
	void delete(int[] ids);

	/**
	 * 动态SQL删除
	 * 
	 * @param table
	 *            表名称
	 * @param wheres
	 *            條件 都是key-value对应
	 */
	@SuppressWarnings("rawtypes")
	void deleteBySQL(String table, Map wheres);

	/**
	 * 根据id删除实体
	 * 
	 * @param ene
	 *            要删除的主键id
	 */
	void deleteEntity(BaseEntity entity);

	/**
	 * 根据id删除实体
	 * 
	 * @param id
	 *            要删除的主键id
	 */
	void deleteEntity(int id);

	/**
	 * 删除表
	 * 
	 * @param table
	 *            表名称
	 */
	void dropTable(String table);

	/**
	 * 导入执行数据
	 * 
	 * @param sql
	 *            sql语句
	 */
	void excuteSql(String sql);

	/**
	 * 更具ID查询实体信息
	 * 
	 * @param id
	 *            实体ID
	 * @return 返回实体
	 */
	BaseEntity getEntity(BaseEntity entity);

	/**
	 * 更具ID查询实体信息
	 * 
	 * @param id
	 *            实体ID
	 * @return 返回实体
	 */
	BaseEntity getEntity(int id);

	/**
	 * 添加记录
	 * 
	 * @param table
	 *            表名称
	 * @param fields
	 *            编号
	 */
	@SuppressWarnings("rawtypes")
	void insertBySQL(String table, Map fields);

	/**
	 * 查询
	 */
	List<BaseEntity> query();

	/**
	 * 查询
	 */
	List<BaseEntity> query(BaseEntity entity);

	/**
	 * 查询所有
	 * 
	 * @return 返回list实体数组
	 */
	List<BaseEntity> queryAll();

	/**
	 * 分页查询
	 * 
	 * @param page
	 *            PageUtil对象，主要封装分页的方法
	 * @param orderBy
	 *            排序字段
	 * @param order
	 *            排序方式true:asc false:desc
	 * @return 返回list实体数组
	 */
	@Deprecated
	List<BaseEntity> queryByPage(PageUtil page, String orderBy, boolean order);

	/**
	 * 动态sql查询
	 * 
	 * @param table
	 *            表名称
	 * @param fields
	 *            list集合
	 * @param wheres
	 *            条件 都是key-value对应
	 * @return 返回list实体数组
	 */
	@SuppressWarnings("rawtypes")
	List queryBySQL(String table, List<String> fields, Map wheres);

	/**
	 * 动态sql查询
	 * 
	 * @param table
	 *            表名称
	 * @param fields
	 *            list集合
	 * @param wheres
	 *            条件 都是key-value对应
	 * @param begin
	 *            开始
	 * @param end
	 *            结束
	 * @return 返回list实体数组
	 */
	@SuppressWarnings("rawtypes")
	List queryBySQL(String table, List<String> fields, Map wheres, Integer begin, Integer end);

	/**
	 * 查询数据表中记录集合总数</br>
	 * 可配合分页使用</br>
	 * 
	 * @return 返回集合总数
	 */
	@Deprecated
	int queryCount();

	/**
	 * 批量新增
	 * 
	 * @param list
	 *            新增数据
	 */
	void saveBatch(List list);

	/**
	 * 保存
	 * 
	 * @param entity
	 *            实体
	 * @return 返回保存后的id
	 */
	int saveEntity(BaseEntity entity);

	/**
	 * 动态SQL更新
	 * 
	 * @param table
	 *            表名称
	 * @param fields
	 *            list集合每个map都是key-value对应
	 * @param wheres
	 *            条件 都是key-value对应
	 */
	@SuppressWarnings("rawtypes")
	void updateBySQL(String table, Map fields, Map wheres);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	void updateEntity(BaseEntity entity);

}
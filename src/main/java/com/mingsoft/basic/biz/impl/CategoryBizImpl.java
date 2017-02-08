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

package com.mingsoft.basic.biz.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.dao.ICategoryDao;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;

/**
 * 类别业务层实现类，继承IBaseBiz，实现ICategoryBiz接口
 * 
 * @author 刘继平
 * @version 版本号：100-000-000<br/>
 *          创建日期：2012-03-15<br/>
 *          历史修订：<br/>
 */
@Service("categoryBiz")
public class CategoryBizImpl extends BaseBizImpl implements ICategoryBiz {

	/**
	 * 注入类别持久化层
	 */
	private ICategoryDao categoryDao;

	/**
	 * 获取类别持久化层
	 * 
	 * @return categoryDao 返回类别持久化层
	 */
	public ICategoryDao getCategoryDao() {
		return categoryDao;
	}

	@Autowired
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	/**
	 * 获取类别持久化层
	 * 
	 * @return categoryDao 返回类别持久话层
	 */
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return categoryDao;
	}

	@Override
	public int saveCategory(CategoryEntity categoryEntity) {
		// TODO Auto-generated method stub
		categoryDao.saveEntity(categoryEntity);
		return saveEntity(categoryEntity);
	}

	@Override
	public int saveCategoryEntity(CategoryEntity categoryEntity) {
		// TODO Auto-generated method stub
		return categoryDao.saveEntity(categoryEntity);
	}

	@Override
	public void deleteCategory(int categoryId) {
		// TODO Auto-generated method stub
		categoryDao.deleteEntity(categoryId);
		deleteEntity(categoryId);
	}

	@Override
	public void deleteCategoryEntity(int categoryId) {
		// TODO Auto-generated method stub
		categoryDao.deleteEntity(categoryId);
	}

	@Override
	public void updateCategory(CategoryEntity categoryEntity) {
		// TODO Auto-generated method stub
		categoryDao.updateEntity(categoryEntity);
		updateEntity(categoryEntity);
	}

	@Override
	public void updateCategoryEntity(CategoryEntity categoryEntity) {
		// TODO Auto-generated method stub
		categoryDao.updateEntity(categoryEntity);
	}

	@Override
	public CategoryEntity getCategory(int categoryId) {
		// TODO Auto-generated method stub
		return (CategoryEntity) categoryDao.getEntity(categoryId);
	}

	@Override
	public List queryByPageList(CategoryEntity category, PageUtil page, String orderBy, boolean order) {
		// TODO Auto-generated method stub
		return categoryDao.queryByPageList(category, page, orderBy, order);
	}

	@Override
	public List<CategoryEntity> queryChilds(CategoryEntity category) {
		// TODO Auto-generated method stub
		return categoryDao.queryChilds(category);
	}

	@Override
	public int count(CategoryEntity category) {
		// TODO Auto-generated method stub
		return categoryDao.count(category);
	}

	@Override
	public List<CategoryEntity> queryByModelId(CategoryEntity category) {
		return categoryDao.queryByModelId(category);
	}
	
	@Override
	public List<Integer> queryCategoryIdByTitle(String categoryTitle, int categoryModelId) {
		// TODO Auto-generated method stub
		return categoryDao.queryCategoryIdByTitle(categoryTitle, categoryModelId);
	}

	@Override
	public List<Integer> queryCategoryIdByCategoryTitle(String categorySchoolName, int schoolModelId,
			int facultyModelId) {
		// TODO Auto-generated method stub
		return categoryDao.queryCategoryIdByCategoryTitle(categorySchoolName, schoolModelId, facultyModelId);
	}

	/**
	 * 根据分类标题和分类的模块ID查询该分类是否存在</br>
	 * 若存在则不持久化直接返回数据库中原来的数据</br>
	 * 若不存在则持久化并返回实体信息
	 * 
	 * @param categoryTitle
	 *            分类标题
	 * @param categoryCategoryId
	 *            父ID
	 * @param categoryModelId
	 *            模块ID
	 * @return 返回分类实体
	 */
	@Override
	public CategoryEntity saveByCategoryTitle(String categoryTitle, int categoryCategoryId, int categoryModelId) {
		// TODO Auto-generated method stub
		CategoryEntity category = new CategoryEntity();
		int categoryId = 0;
		if (StringUtil.isBlank(categoryTitle)) {
			category.setCategoryId(categoryId);
			return category;
		} else {
			// 查询数据库中属否存在该分类数据
			List<Integer> list = queryCategoryIdByTitle(categoryTitle, categoryModelId);
			if (list != null && list.size() > 0) {
				categoryId = list.get(list.size() - 1);
			}
		}

		// 当数据库中不存在该分类数据时则持久化
		if (categoryId == 0) {
			category.setCategoryTitle(categoryTitle);
			category.setCategoryCategoryId(categoryCategoryId);
			category.setCategoryModelId(categoryModelId);
			saveCategoryEntity(category);
		} else {
			category.setCategoryId(categoryId);
		}
		return category;
	}

	@Override
	public List<CategoryEntity> queryBatchCategoryById(List<Integer> listId) {
		// TODO Auto-generated method stub
		return categoryDao.queryBatchCategoryById(listId);
	}

	@Override
	public synchronized int[] queryChildrenCategoryIds(int categoryId, int appId, int modelId) {
		// TODO Auto-generated method stub
		List<CategoryEntity> list = categoryDao.queryChildrenCategoryId(categoryId, appId, modelId);
		int[] ids = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			CategoryEntity category = list.get(i);
			ids[i] = category.getCategoryId();
		}
		return ids;
	}
	/**
	 * 传入一个实体查询出该实体所有的子节点
	 * @param category
	 * @return
	 */
	@Override
	public int[] queryAllchilds(CategoryEntity entity){
		List<CategoryEntity> childs = categoryDao.queryChildren(entity);
		//取出所有的Id集合，并放到数组中
		int[] ids = new int[childs.size()];
		for (int i = 0; i < childs.size(); i++) {
			CategoryEntity categoryEnt = childs.get(i);
			ids[i] = categoryEnt.getCategoryId();
		}
		return ids;
	}
	@Override
	public List<CategoryEntity> queryByAppIdOrModelId(Integer appId, Integer modelId) {
		// TODO Auto-generated method stub
		return categoryDao.queryByAppIdOrModelId(appId, modelId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mingsoft.basic.biz.ICategoryBiz#queryParent(int, int,
	 * java.lang.Integer)
	 */
	@Override
	public List<CategoryEntity> queryParent(int appId, int modelId, Integer categoryId) {
		// TODO Auto-generated method stub
		// 先查出父ids
		String ids = categoryDao.queryParentIds(categoryId);
		if (!StringUtil.isBlank(ids)) {
			List list = new ArrayList();
			String[] _ids = ids.split(",");
			for (int i = 0; i < _ids.length; i++) {
				list.add(Integer.parseInt(_ids[i]));
			}
			return categoryDao.queryBatchCategoryById(list);
		}
		return null;
	}

	@Override
	public List<CategoryEntity> queryByDescription(int appId, int modelId, String categoryDescription) {
		// TODO Auto-generated method stub
		if (StringUtil.isBlank(categoryDescription)) {
			return null;
		}
		return this.categoryDao.queryByDescription(appId, modelId, categoryDescription);
	}

	@Override
	public int[] queryCategoryIdsByModelIdAndAppId(int appId, int modelId) {
		// TODO Auto-generated method stub
		List<CategoryEntity> list = categoryDao.queryByAppIdOrModelId(appId, modelId);
		int ids[] = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			CategoryEntity category = list.get(i);
			ids[i] = Integer.valueOf(category.getCategoryId());
		}
		return ids;
	}

	@Override
	public List<CategoryEntity> queryByCategoryTitle(String[] title) {
		// TODO Auto-generated method stub
		return categoryDao.queryByCategoryTitle(title);
	}

	@Override
	public List<CategoryEntity> queryChildrenCategory(int categoryId, int appId, int modelId) {
		// TODO Auto-generated method stub
		return categoryDao.queryChildrenCategoryId(categoryId, appId, modelId);
	}

	@Override
	public BaseEntity getEntity(int id) {
		// TODO Auto-generated method stub
		CategoryEntity category = (CategoryEntity) super.getEntity(id);
		if (category != null) {
			// 查询所有的子分类
			List<CategoryEntity> childs = categoryDao.queryChildren(category);
			resetChildren(category, childs);
		}
		return category;
	}

	@Override
	public BaseEntity getEntity(BaseEntity entity) {
		// TODO Auto-generated method stub
		CategoryEntity category = (CategoryEntity) super.getEntity(entity);
		// 查询所有的子分类
		List<CategoryEntity> childs = categoryDao.query(category);
		resetChildren(category, childs);
		return category;

	}

	@Override
	public List query(BaseEntity entity) {
		List list = super.query(entity);
		List childList = new ArrayList();
		childList.addAll(list);
		for (int i=0;i<list.size();i++) {
			CategoryEntity c = (CategoryEntity)list.get(i); 
			resetChildren(c, childList);
		}
		return list;
	}

	private void resetChildren(CategoryEntity category, List<CategoryEntity> chrildrenCategoryList) {
		for (CategoryEntity c : chrildrenCategoryList) {
			if (c.getCategoryCategoryId() == category.getCategoryId() && !category.getChildrenCategoryList().contains(c)) {
				category.getChildrenCategoryList().add(c);
				resetChildren(c, chrildrenCategoryList);
			}
		}
	}
	
	/**
	 * 根据字典查询机构
	 * @param category
	 * @return
	 */
	public List queryByDictId(CategoryEntity category) {
		return categoryDao.queryByDictId(category);
	}

}
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

package com.mingsoft.basic.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.basic.constant.ModelCode;
import com.mingsoft.basic.constant.e.CookieConstEnum;
import com.mingsoft.basic.constant.e.GlobalModelCodelEnum;
import com.mingsoft.basic.constant.e.SessionConstEnum;
import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.basic.entity.ManagerEntity;
import com.mingsoft.basic.entity.ManagerSessionEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;

import net.mingsoft.basic.util.BasicUtil;

/**
 * 无限极分类
 * 
 * @author 王天培
 * @version 版本号：100-000-000<br/>
 *          创建日期：2014-6-15<br/>
 *          历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/category")
public class CategoryAction extends BaseAction {
	/**
	 * 业务层的注入
	 */
	@Autowired
	private ICategoryBiz categoryBiz;

	/**
	 * 加载添加栏目页面
	 * 
	 * @param request
	 *            请求对象
	 * @return 添加栏目页面
	 */
	@RequestMapping("/add")
	public String add(@ModelAttribute CategoryEntity category, HttpServletRequest request) {
		if (category == null) {
			category = new CategoryEntity();
		}
		
		CategoryEntity _c =  new CategoryEntity();
		_c.setCategoryManagerId(this.getManagerId(request));
		_c.setCategoryModelId(this.getModelCodeId(request));
		List<CategoryEntity> list = categoryBiz.queryChilds(_c);

		String listJsonString = JSONObject.toJSON(list).toString();

		request.setAttribute("category", category); // 提供给子栏目使用
		request.setAttribute("listCategory", listJsonString);
		request.setAttribute("modelTitle", request.getParameter("modelTitle"));
		request.setAttribute("modelId", request.getParameter("modelId"));
		return view("/category/category_form");
	}

	/**
	 * 添加栏目
	 * 
	 * @param category
	 *            栏目对象
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 */
	@RequestMapping("/save")
	public void save(@ModelAttribute CategoryEntity category, HttpServletRequest request,
			HttpServletResponse response) {

		if (!checkForm(category, response)) {
			return;
		}
		category.setCategoryManagerId(((ManagerEntity) getManagerBySession(request)).getManagerId());
		category.setCategoryDateTime(new Timestamp(System.currentTimeMillis()));
		category.setCategoryAppId(this.getAppId(request));
		category.setCategoryModelId(this.getModelCodeId(request));
		categoryBiz.saveCategoryEntity(category);
		request.removeAttribute("categoryCategoryId");
		request.removeAttribute("categoryLevel");
		this.outJson(response, ModelCode.CMS_COLUMN, true, null, String.valueOf(category.getCategoryId()));
	}

	/**
	 * 加载栏目更新页面
	 * 
	 * @param categoryId
	 *            栏目id
	 * @param request
	 *            请求对象
	 * @return 栏目更新页面地址
	 */
	@RequestMapping("/edit")
	public String edit(@ModelAttribute CategoryEntity category, HttpServletRequest request) {

		CategoryEntity column = (CategoryEntity) categoryBiz.getEntity(category.getCategoryId());
		column.setCategoryLevel(category.getCategoryLevel());
		request.setAttribute("category", column);

		// 站点ID
		List<CategoryEntity> list = new ArrayList<CategoryEntity>();
		// 判断管理员权限,查询其管理的栏目集合
		category.setCategoryManagerId(this.getManagerId(request));
		category.setCategoryModelId(this.getModelCodeId(request));
		category.setCategoryCategoryId(0);
		category.setCategoryId(0);
		list = categoryBiz.queryChilds(category);
		// 获取父栏目对象
		if (column.getCategoryCategoryId() != 0) {
			CategoryEntity columnSuper = (CategoryEntity) categoryBiz.getEntity(column.getCategoryCategoryId());
			request.setAttribute("columnSuper", columnSuper);
		}

		String listJsonString = JSONArray.toJSONString(list);
		request.setAttribute("listCategory", listJsonString);
		request.setAttribute("modelTitle", request.getParameter("modelTitle"));
		request.setAttribute("modelId", request.getParameter("modelId"));
		return view("/category/category_form");
	}

	/**
	 * 栏目更新
	 * 
	 * @param category
	 *            栏目实体
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 */
	@RequestMapping("/update")
	public void update(@ModelAttribute CategoryEntity category, HttpServletRequest request,
			HttpServletResponse response) {
		if (!checkForm(category, response)) {
			return;
		}
		category.setCategoryManagerId(((ManagerEntity) getManagerBySession(request)).getManagerId());
		categoryBiz.updateCategoryEntity(category);
		this.outJson(response, ModelCode.CMS_COLUMN, true, null, JSONArray.toJSONString(category.getCategoryId()));

	}

	/**
	 * 栏目首页面列表显示
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @return 栏目列表页面地址
	 */
	@RequestMapping("/list")
	public String list(@ModelAttribute CategoryEntity categoryEntity, HttpServletRequest request, HttpServletResponse response) {
		String modelId = request.getParameter("modelId");
		this.setSession(request, SessionConstEnum.MANAGER_MODEL_CODE, modelId);
		// 获取登录的session
		ManagerSessionEntity managerSession = (ManagerSessionEntity) getManagerBySession(request);
		// 传入一个实体，提供查询条件
		CategoryEntity category = new CategoryEntity();
		category.setCategoryModelId(Integer.parseInt(modelId));
 
		AppEntity app = this.getApp(request);
		// 查询指定的appId下的分类
		category.setCategoryAppId(app.getAppId());
		// 判断是否为该网站总管理员，如果是管理员查询分类时则可以不受管理员限制，即可以查看所有的分类
		if (managerSession.getManagerId() != app.getAppManagerId()) {
			category.setCategoryManagerId(managerSession.getManagerId());
		}
		PageUtil page = new PageUtil(0, 999999, "");
		List list = categoryBiz.queryByPageList(category, page, "category_id", true);
		// 保存cookie值
		this.setCookie(request, response, CookieConstEnum.BACK_COOKIE, "/manager/category/list.do");
		request.setAttribute("categoryJson", JSONArray.toJSONString(list));
		return view("/category/category_list");
	}

	/**
	 * 根据栏目id删除栏目
	 * 
	 * @param categoryId
	 *            栏目id
	 * @param response
	 *            响应对象
	 * @param request
	 *            请求对象
	 */
	@RequestMapping("/{categoryId}/delete")
	public void delete(@PathVariable int categoryId, HttpServletResponse response, HttpServletRequest request) {
		CategoryEntity category = new CategoryEntity();
		category.setCategoryCategoryId(categoryId);
		category.setCategoryManagerId(this.getManagerId(request));
		category.setCategoryModelId(this.getModelCodeId(request));

		// 查询该栏目是否有子栏目,如果存在子栏目则返回错误提示，否则删除该栏目
		if (categoryBiz.count(category) > 0) {
			this.outJson(response, ModelCode.CMS_COLUMN, true, "0");
		} else {
			categoryBiz.deleteCategoryEntity(categoryId);
			this.outJson(response, ModelCode.CMS_COLUMN, true, "1");
		}
	}

	/**
	 * 验证栏目实体是否合法
	 * 
	 * @param category
	 * @param response
	 * @return true：实体信息合法，false：实体信息不合法
	 */
	private boolean checkForm(CategoryEntity category, HttpServletResponse response) {

		// 栏目标题空值验证
		if (StringUtil.isBlank(category.getCategoryTitle())) {
			this.outJson(response, ModelCode.CMS_COLUMN, false,
					getResString("err.empty", this.getResString("categoryTitle")));
			return false;
		}
		// 栏目标题长度验证
		if (!StringUtil.checkLength(category.getCategoryTitle(), 1, 31)) {
			this.outJson(response, ModelCode.CMS_COLUMN, false,
					getResString("err.length", this.getResString("categoryTitle"), "1", "30"));
			return false;
		}

		return true;
	}

	/**
	 * 根据分类id查找分类实体和它的父分类
	 * 
	 * @param categoryId
	 * @param request
	 * @param mode
	 * @param response
	 */
	/**
	 * 根据分类id查找分类实体和它的父分类
	 * 
	 * @param categoryId
	 *            栏目id
	 * @param request
	 *            请求对象
	 * @param mode
	 * @param response
	 */
	@RequestMapping("/{categoryId}/query")
	public void query(@PathVariable int categoryId, HttpServletRequest request, ModelMap mode,
			HttpServletResponse response) {
		// 根据分类id查询分类实体
		CategoryEntity category = (CategoryEntity) categoryBiz.getEntity(categoryId);
		// 如何分类实体不存在
		if (category == null) {
			return;
		}
		// 查询该分类的父分类
		CategoryEntity categoryCategory = (CategoryEntity) categoryBiz.getEntity(category.getCategoryCategoryId());

		List<CategoryEntity> list = new ArrayList<CategoryEntity>();
		list.add(categoryCategory);
		list.add(category);
		this.outJson(response, JSONObject.toJSONString(list));
	}

	/**
	 * 根据分类id查找分类子分类
	 * 
	 * @param categoryId
	 * @param request
	 * @param mode
	 * @param response
	 */
	@RequestMapping("/{categoryId}/queryChildren")
	public void queryChildren(@PathVariable int categoryId, HttpServletRequest request, ModelMap mode,
			HttpServletResponse response) {
		CategoryEntity category = (CategoryEntity) this.categoryBiz.getEntity(categoryId);
		if (category != null) {
			List<CategoryEntity> list = this.categoryBiz.queryChilds(category);
			this.outJson(response, JSONObject.toJSONString(list));
		}

	}

	/**
	 * 加载栏目列表页并查询所有栏目下子栏目
	 * 
	 * @param categoryId
	 *            栏目id
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @return 栏目列表地址
	 */
	@RequestMapping("/{categoryId}/childList")
	public String childList(@PathVariable int categoryId, HttpServletRequest request, HttpServletResponse response) {
		String modelId = request.getParameter("modelId");
		String categoryCategoryId = request.getParameter("categoryCategoryId");// 提供展开效果使用
		this.setSession(request, SessionConstEnum.MANAGER_MODEL_CODE, modelId);
		// 获取登录的session
		ManagerSessionEntity managerSession = (ManagerSessionEntity) getManagerBySession(request);
		// 传入一个实体，提供查询条件
		CategoryEntity category = new CategoryEntity();
		category.setCategoryModelId(Integer.parseInt(modelId));

		AppEntity app = this.getApp(request);
		int appId = app.getAppId();
		// 查询指定的appId下的分类
		category.setCategoryAppId(appId);
		// 判断是否为该网站总管理员，如果是管理员查询分类时则可以不受管理员限制，即可以查看所有的分类
		if (managerSession.getManagerId() != app.getAppManagerId()) {
			category.setCategoryManagerId(managerSession.getManagerId());
		}

		List<CategoryEntity> list = categoryBiz.queryChildrenCategory(categoryId, appId, this.getModelCodeId(request));

		// 保存cookie值
		this.setCookie(request, response, CookieConstEnum.BACK_COOKIE, "/manager/category/list.do");
		request.setAttribute("categoryCategoryId", categoryCategoryId);
		request.setAttribute("categoryJson", JSONArray.toJSONString(list));
		request.setAttribute("modelId", request.getParameter("modelId"));
		return Const.VIEW + "/category/category_list";
	}
	
	
	/**
	 * 城市
	 * @param categoryId 分类id
	 * @param request HttpServletRequest对象
	 * @param response HttpServletResponse对象
	 */
	@RequestMapping("/city")
	@ResponseBody
	public void city(HttpServletRequest request, HttpServletResponse response){
		List list = this.categoryBiz.queryByAppIdOrModelId(BasicUtil.getAppId(), this.getModelCodeId(request, GlobalModelCodelEnum.CITY));
		this.outJson(response, list);
	}

}
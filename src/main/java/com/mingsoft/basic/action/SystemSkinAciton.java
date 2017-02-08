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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.mingsoft.basic.biz.ISystemSkinBiz;
import com.mingsoft.basic.constant.e.SessionConstEnum;
import com.mingsoft.basic.entity.ManagerSessionEntity;
import com.mingsoft.basic.entity.SystemSkinEntity;

/**
 * 后台主题
 * @author 王天培
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015-1-10<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/systemSkin/")
public class SystemSkinAciton extends BaseAction{

	/**
	 * 主题业务
	 */
	@Autowired
	private ISystemSkinBiz systemSkinBiz;
	
	/**
	 * 通过系统主题id更新主题
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param systemSkinId 系统主题id
	 */
	@RequestMapping("/{systemSkinId}/updateForManager")
	@ResponseBody
	public void updateForManager(HttpServletRequest request,HttpServletResponse response,@PathVariable int systemSkinId){
		ManagerSessionEntity mse = this.getManagerBySession(request);
		SystemSkinEntity sse = systemSkinBiz.updateManagerSystemSkin(mse.getManagerId(), systemSkinId);
		if (sse!=null) {
			mse.setSystemSkin(sse);
			this.setSession(request, SessionConstEnum.MANAGER_SESSION, mse);
		}
	}
	
	/**
	 * 查询所有主题以json格式返回list集合
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	@RequestMapping("/query")
	@ResponseBody
	public void query(HttpServletRequest request,HttpServletResponse response){
		List list = systemSkinBiz.queryAll();
		this.outJson(response, JSONArray.toJSONString(list));
	}
}
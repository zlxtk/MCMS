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

package com.mingsoft.people.action.people;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.people.biz.IPeopleStudentBiz;
import com.mingsoft.people.constant.ModelCode;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.people.entity.PeopleStudentEntity;
import com.mingsoft.people.action.BaseAction;

/**
 * 
 * <p>
 * <b>铭飞科技-会员系统</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 *
 * @author 史爱华
 *               
 *
 * <p>
 * Comments:普通用户详细信息控制层(外部请求接口)
 * </p>
 *
 * <p>
 * Create Date:2015-03-24
 * </p>
 *
 * <p>
 * Modification history:
 * </p>
 */
@Controller("webPeopleStudent")
@RequestMapping("/people/student")
public class PeopleStudentAction extends BaseAction{
	
	@Autowired
	private IPeopleStudentBiz peopleStudentBiz;
	
	/**
	 * 查询学生信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getStudentInfor")
	public void getStudentInfor(HttpServletRequest request,HttpServletResponse response){
		//获取用户session
				PeopleEntity people = this.getPeopleBySession(request); 
				if(people == null){
					//当session不存在返回错误信息
					this.outJson(response,ModelCode.PEOPLE_USER,false,this.getResString("people.session.msg.null.error",com.mingsoft.people.constant.Const.RESOURCES));
					return ;
				}
				
				PeopleStudentEntity peopleStudents = (PeopleStudentEntity) this.peopleStudentBiz.getEntity(people.getPeopleId());
				if(peopleStudents == null){
					//没用用户详细信息
					this.outJson(response, ModelCode.PEOPLE_USER,false,this.getResString("people.user.msg.null.error",com.mingsoft.people.constant.Const.RESOURCES));
					return ;
				}
				//查询学生的具体信息
				this.outJson(response, ModelCode.PEOPLE_USER,true,null,JSONObject.toJSONString(this.peopleStudentBiz.getStudentInfor(people.getPeopleId())));
	}
}
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

package com.mingsoft.people.biz.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.people.biz.IPeopleUserBiz;
import com.mingsoft.people.dao.IPeopleUserDao;
import com.mingsoft.people.entity.PeopleUserEntity;

/**
 * 
 * 
 * <p>
 * <b>铭飞CMS-铭飞内容管理系统</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇铭飞科技有限公司
 * </p>
 * 
 * @author 刘继平
 * 
 * @version 300-001-001
 * 
 * <p>
 * 版权所有 铭飞科技
 * </p>
 *  
 * <p>
 * Comments: 用户信息业务层层实现类，继承PeopleBizImpl，实现IPeoplePrivateBiz接口
 * </p>
 *  
 * <p>
 * Create Date:2014-9-4
 * </p>
 *
 * <p>
 * Modification history:
 * </p>
 */
@Service("peopleUserBiz")
public class PeopleUserBizImpl extends PeopleBizImpl implements IPeopleUserBiz {

	/**
	 * 用户信息持久化层注入
	 */
	@Autowired
	private IPeopleUserDao peopleUserDao;

	@Override
	protected IBaseDao getDao() {
		return peopleUserDao;
	}

	/**
	 * 用户信息实体保存</br>
	 * 只能在有子类继承时调用的</br>
	 */
	public int savePeopleUser(PeopleUserEntity peopleEntity){
		savePeople(peopleEntity);
		return peopleUserDao.saveEntity(peopleEntity);
	}	
	
	/**
	 * 更新用户信息</br>
	 * 只能在有子类时调用</br>
	 * @param peopleEntity 用户信息
	 */
	public void updatePeopleUser(PeopleUserEntity peopleEntity){
		updatePeople(peopleEntity);
		this.peopleUserDao.updateEntity(peopleEntity);
	}

	/**
	 * 删除用户信息</br>
	 * 只能在有子类时调用</br>
	 * @param peopleId 用户ID
	 */
	public void deletePeopleUser(int peopleId){
		deletePeople(peopleId);
		this.peopleUserDao.deleteEntity(peopleId);
	}	
	
	@Override
	public void deletePeopleUsers(int[] peopleIds) {
		if(peopleIds==null){
			return;
		}
		this.deletePeople(peopleIds);
		this.peopleUserDao.deletePeopleUsers(peopleIds);
	}

	
}
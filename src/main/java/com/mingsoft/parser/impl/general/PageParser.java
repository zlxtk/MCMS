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

package com.mingsoft.parser.impl.general;

import java.io.File;

import com.mingsoft.parser.IParser;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;

/**
 * 实现分页功能并进行标签的替换
 * 分页标签
 * 首页：{ms:page.index}
 * 上一页:{ms:page.pre/}
 * 下一页:{ms:page.next/}
 * 尾页:{ms:page.over/}
 * @author 成卫雄
 * QQ:330216230
 * 技术支持：景德镇铭飞科技
 * 官网：www.ming-soft.com
 */
public class PageParser extends IParser{
	
	
	
	/**
	 *  首页 列表分页标签 {ms:page.index}
	 */
	private final String PAGE_INDEX="\\{ms:page.index/\\}";
	
	/**
	 * 上一页 列表分页标签 {ms:page.pre/}
	 */
	private final String PAGE_PRE="\\{ms:page.pre/\\}";
	
	/**
	 * 下一页 列表分页标签 {ms:page.next/}
	 */
	private final String PAGE_NEXT="\\{ms:page.next/\\}";
	
	/**
	 * 尾页 列表分页标签 {ms:page.last/}
	 */
	private final String PAGE_OVER="\\{ms:page.last/\\}";
	
	private PageUtil page;
	/**
	 * 构造替换标签的必须属性
	 * @param urlPage 
	 * 				当前页面的链接 链接地址  http://www.***.com/servlet
	 * @param countPage  
	 * 				需要显示数据的总数量
	 * @param sizePage	
	 * 				 每页显示的信息数量
	 *  @param currentPage 
	 *  			当前页面在数据库中的光标位置
	 * @param htmlContent
	 * 				 原HTML代码
	 */
	public PageParser(String htmlContent,PageUtil page){
		super.htmlCotent = htmlContent;
		this.page = page;
	}
	
	@Override
	public String parse() {
		if (this.page!=null) {
			// TODO Auto-generated method stub
			//替换首页标签:{ms:page.index}
			super.newCotent =this.page.getIndexUrl();//this.page.getIndexUrl().replace("\\", "/");
			String indexHtml = super.replaceAll(PAGE_INDEX);
			//替换上一页标签:{ms:page.pre/}
			super.htmlCotent = indexHtml;
			super.newCotent =  this.page.getPreviousUrl();// this.page.getPreviousUrl().replace("\\", "/");
			String preHtml = super.replaceAll(PAGE_PRE);
			//替换下一页标签：{ms:page.next/}
			super.htmlCotent = preHtml;
			super.newCotent =  this.page.getNextUrl();//this.page.getNextUrl().replace("\\", "/");
			String nextHtml = super.replaceAll(PAGE_NEXT);
			//替换尾页标签：{ms:page.over/}
			super.htmlCotent = nextHtml;
			super.newCotent =  this.page.getLastUrl();//  this.page.getLastUrl().replace("\\", "/");
			String traileHtml = super.replaceAll(PAGE_OVER);			
			return traileHtml;
		} else {
			return htmlCotent;
		}

	
	}
	

	
}
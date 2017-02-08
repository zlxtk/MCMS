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

package com.mingsoft.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mingsoft.util.RegexUtil;
import com.mingsoft.util.StringUtil;

/**
 * 
 * 
 * 
 * 
 * <p>
 * <b>铭飞科技</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * @author killfen
 * 
 *         <p>
 *         Comments:基于模板方法模式，抽出通用标签解析父类,
 *         </p>
 * 
 *         <p>
 *         Create Date:2015-4-18
 *         </p>
 * 
 *         <p>
 *         Modification history:
 *         </p>
 */
public abstract class IParser {

	/**
	 * .do后缀
	 */
	public final static String DO_SUFFIX = ".do";
	
	/**
	 * htm后缀
	 */
	public final static String HTM_SUFFIX = ".htm";
	
	/**
	 * html后缀
	 */
	public final static String HTML_SUFFIX = ".html";
	/**
	 * 定位标签中属性的值
	 */
	protected final static String PROPERTY_VALUE = "=\\s*(\\w*)";

	/**
	 * 定位标签中属性的名称
	 */
	protected final static String PRORETY_NAME = "(\\w*)\\s*=";

	/**
	 * 查询规则匹配次数
	 * 
	 * @param source
	 *            模版
	 * @param regex
	 *            标签
	 * @return 标签的数量
	 */
	protected static int count(String source, String regex) {
		return RegexUtil.count(source, regex);
	}

	/**
	 * 返回所有匹配的结果，并且是find组
	 * 
	 * @return 标签的数量
	 * @param source
	 *            　模版
	 * @param regex
	 *            　规则
	 * @param find
	 *            　第几组
	 * @return
	 */
	protected static List<String> parseAll(String source, String regex, int find) {
		return RegexUtil.parseAll(source, regex, find);
	}

	/**
	 * 返回第一次匹配的结果，并且是find组
	 * 
	 * @param source
	 *            　模版
	 * @param regex
	 *            　标签
	 * @param find
	 * @return 匹配一次的值
	 */
	protected static String parseFirst(String source, String regex, int find) {
		String temp = RegexUtil.parseFirst(source, regex, find);
		return temp==null?"":temp;  
	}

	/**
	 * 原始html内容
	 */
	protected String htmlCotent = null;

	protected String mobilePath = "";

	/**
	 * 替换后的html内容
	 */
	protected String newCotent = null;

	public String getNewCotent() {
		return newCotent;
	}

	/**
	 * 取出栏目标签中的属性
	 * 
	 * @param html
	 *            HTML模版
	 * @return 属性集合
	 */
	public Map<String, String> getProperty(String regex) {
		Map<String, String> listPropertyMap = new HashMap<String, String>();
		String listProperty = parseFirst(this.htmlCotent, regex, 1);
		if (listProperty == null) {
			return listPropertyMap;
		}
		List<String> listPropertyName = parseAll(listProperty, PRORETY_NAME, 1);
		List<String> listPropertyValue = parseAll(listProperty, PROPERTY_VALUE, 1);
		for (int i = 0; i < listPropertyName.size(); i++) {
			listPropertyMap.put(listPropertyName.get(i).toString(), listPropertyValue.get(i).toString());
		}
		return listPropertyMap;
	}

	/**
	 * 标签替换的抽象方法
	 * 
	 * @return 替换完成的HTML模版
	 */
	public abstract String parse();

	/**
	 * 用该内容替换所有当前标签
	 * 
	 * @param regex
	 *            正则
	 * @return 替换后的内容
	 */
	public String replaceAll(String regex) {
		if (StringUtil.isBlank(newCotent)) {
			newCotent = IParserRegexConstant.REGEX_ERRO;
		}
		return RegexUtil.replaceAll(htmlCotent, regex, newCotent);
	}

	/**
	 * 用该内容替换所有当前标签
	 * 
	 * @param content
	 *            新内容
	 * @param regex
	 *            正则
	 * @return 替换后的内容
	 */
	public String replaceAll(String content, String regex) {
		return RegexUtil.replaceAll(htmlCotent, regex, content);
	}

	/**
	 * 用该内容替换第一个当前找到的第一个标签
	 * 
	 * @param htmlCotent
	 *            源内容
	 * @param newCotent
	 *            替换内容
	 * @param regex
	 *            替换规则，更具RegexEnum
	 * @return 替换完成的HTML模版
	 */
	public String replaceFirst(String regex) {
		if (StringUtil.isBlank(newCotent)) {
			newCotent = IParserRegexConstant.REGEX_ERRO;
		}
		return RegexUtil.replaceFirst(htmlCotent, regex, newCotent);
	}

	public void setNewCotent(String newCotent) {
		this.newCotent = newCotent;
	}

}
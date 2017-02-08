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

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mingsoft.parser.IParser;
import com.mingsoft.parser.IParserRegexConstant;
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
 * <p>
 * Comments:时间解析规则
 * </p>
 * 
 * <p>
 * Create Date:2015-4-27
 * </p>
 * 
 * <p>
 * Modification history:
 * </p>
 */
public class DateParser extends IParser {

	/**
	 * 网站路径标签，单标签，主要用于提交列表等HTML的路径到相应的action中 网站全局标签 {ms:global.url/}
	 */
	private String dateReg =  "\\[field.date\\s{0,}(fmt=(.*?))?/]";
	
	private Date date;

	/**
	 * 构造标签的属性
	 * 
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public DateParser(String htmlContent,Date date) {
		super.htmlCotent = htmlContent;
		this.date = date;
	}
	
	/**
	 * 构造标签的属性
	 * 
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 * @param dateReg标签规则
	 */
	public DateParser(String htmlContent,Date date,String dateReg) {
		super.htmlCotent = htmlContent;
		this.date = date;
		this.dateReg = dateReg;
	}
	
	
	/**
	 * 构造标签的属性
	 * 
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public DateParser(String htmlContent, String newContent) {
		super.htmlCotent = htmlContent;
		super.newCotent = newContent;
	}

	@Override
	public String parse() {
		Pattern pattern = Pattern.compile(dateReg);
		Matcher matcher = pattern.matcher(htmlCotent);
		while (matcher.find()) {
			String date = matcher.group();
			htmlCotent = htmlCotent.replace(date, date(date)); 
		}
		return htmlCotent;
	}

	/**
	 * 将时间格式转换成字符串型并改变时间的显示格式
	 * 
	 * @param date
	 *            数据库中的时间
	 * @param htmlCotent
	 *            读取时间格式的HTML代码
	 * @return 时间
	 */
	private String date(String reg) {
		// 从HTML代码中取出时间的显示格式，默认为yyyy-MM-dd hh:mm:ss形式
		String typeDate = IParserRegexConstant.REGEX_DATE;
		String fmt = parseFirst(htmlCotent,dateReg, 2);
		if (!StringUtil.isBlank(fmt)) {
			typeDate = fmt;
		}
		// 将时间转换成String型
		String srtDate = IParserRegexConstant.REGEX_DATE_ERRO;
		if (date != null) {
			try {
				java.text.SimpleDateFormat forDate = new java.text.SimpleDateFormat(typeDate);
				srtDate = forDate.format(date);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return srtDate;
	}
}
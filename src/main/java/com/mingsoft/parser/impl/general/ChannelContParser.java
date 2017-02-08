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

import com.mingsoft.parser.IParser;
import com.mingsoft.parser.IParserRegexConstant;
import com.mingsoft.util.StringUtil;
/**
 * 封面内容
 * 封面标签（单标签）
 * {ms:channelcont titlelen=  typeid=/}
 * @author 成卫雄
 * QQ:330216230
 * 技术支持：景德镇铭飞科技
 * 官网：www.ming-soft.com
 */
public class ChannelContParser extends IParser{
	
	/**
	 * 封面标签
	 */
	private final static String CHANNELCONT="\\{ms:contchannel.*?/}";
	
	/**
	 * 封面标签的Id属性 类型int 默认当前页面的封面（非必填） {ms:channelcont titlelen= typeid=/}
	 */
	private final static String TYPEID_CHANNELCONT="\\{ms:contchannel.*?(typeid\\=(\\d*).{0,})?/}";
	
	/**
	 * 封面标签的内容长度属性 类型int 默认当前的所有内容（非必填） {ms:channelcont titlelen= typeid=/}
	 */
	private final static String TITLELEN_CHANNELCONT="\\{ms:contchannel.*?(titlelen\\=(\\d*).{0,})?/}";
	
	
	/**
	 * 构造标签的属性
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public ChannelContParser(String htmlContent,String newContent){
		super.htmlCotent = htmlContent;
		super.newCotent = channelContTitleLen(newContent,htmlContent);
	}
	
	@Override
	public String parse() {
			// TODO Auto-generated method stub
			return super.replaceAll(CHANNELCONT);
		}
	
	/**
	 *获取模版文件中封面标签的个数 
	 * @param html 文件模版
	 * @return 返回该字符串的个数
	 */
	public static int channelContNum(String html){
		int channelNum = count(html,CHANNELCONT);
		return channelNum;
	}
	
	
	//------------------------解析标签中的属性------------------------
	/**
	 * 解析封面标签中的ID属性
	 * @param htmlContent 原HTML代码
	 */
	public static int channelContTypeId(String htmlContent){
		int channelContTypeId = 0;
		 String typeIdStr = parseFirst(htmlContent,TYPEID_CHANNELCONT,2);
		 if(!StringUtil.isBlank(typeIdStr)){
			 channelContTypeId = Integer.parseInt(typeIdStr);
		 }   
		return channelContTypeId;
	}
	
	/**
	 * 判断封面标签中内容的长度
	 * @param content 标题的内容
	 * @param htmlContent HTML模版
	 * @return  截取后的内容长度 
	 */
	public static String channelContTitleLen(String content,String htmlContent){
		//从HTML代码中内容的长度，默认为全部显示
		int lengthCon = 0;
		String length = parseFirst(htmlContent,TITLELEN_CHANNELCONT, 2);
		if(!StringUtil.isBlank(length) && !StringUtil.isBlank(content)){
			lengthCon = Integer.parseInt(length);
		}
		//根据长度取出内容
		String contentNew = content;
		if(StringUtil.isBlank(content)){
			contentNew = IParserRegexConstant.REGEX_ERRO+"，请检封面查ID";
		}
		if(lengthCon != 0 && !(lengthCon > contentNew.length())){
			StringBuffer strBuff = new StringBuffer(contentNew);
			contentNew = strBuff.substring(0,lengthCon);
		}
		return contentNew;
	}
}
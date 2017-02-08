/*   1:    */ package com.mingsoft.util;
/*   2:    */ 
/*   3:    */ import com.alibaba.fastjson.JSON;
/*   4:    */ import com.alibaba.fastjson.JSONArray;
/*   5:    */ import com.alibaba.fastjson.JSONObject;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ 
/*  12:    */ public class JsonUtil
/*  13:    */ {
/*  14:    */   public static Object getObject4JsonString(String jsonString, Class<?> pojoCalss)
/*  15:    */   {
/*  16: 53 */     JSONObject jsonObject = JSONObject.parseObject(jsonString);
/*  17: 54 */     Object pojo = JSONObject.toJavaObject(jsonObject, pojoCalss);
/*  18: 55 */     return pojo;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static Map<String, Object> getMap4Json(String jsonString)
/*  22:    */   {
/*  23: 65 */     JSONObject jsonObject = JSONObject.parseObject(jsonString);
/*  24: 66 */     Iterator<String> keyIter = jsonObject.keySet().iterator();
/*  25:    */     
/*  26:    */ 
/*  27: 69 */     Map<String, Object> valueMap = new HashMap();
/*  28: 71 */     while (keyIter.hasNext())
/*  29:    */     {
/*  30: 72 */       String key = (String)keyIter.next();
/*  31: 73 */       Object value = jsonObject.get(key);
/*  32: 74 */       valueMap.put(key, value);
/*  33:    */     }
/*  34: 76 */     return valueMap;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static Object[] getObjectArray4Json(String jsonString)
/*  38:    */   {
/*  39: 86 */     JSONArray jsonArray = JSONArray.parseArray(jsonString);
/*  40: 87 */     return jsonArray.toArray();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static String getObjectToJsonObject(Object obj)
/*  44:    */   {
/*  45: 96 */     return JSON.toJSON(obj).toString();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static String[] getStringArray4Json(String jsonString)
/*  49:    */   {
/*  50:107 */     JSONArray jsonArray = JSONArray.parseArray(jsonString);
/*  51:108 */     String[] stringArray = new String[jsonArray.size()];
/*  52:110 */     for (int i = 0; i < jsonArray.size(); i++) {
/*  53:111 */       stringArray[i] = jsonArray.get(i).toString();
/*  54:    */     }
/*  55:114 */     return stringArray;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static <T> T getJsonToObject(String jsonString, Class<T> cls)
/*  59:    */   {
/*  60:126 */     return JSONObject.parseObject(jsonString, cls);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static <T> List<T> queryJsonToList(String jsonString, Class<T> cls)
/*  64:    */   {
/*  65:139 */     List<T> list = JSONArray.parseArray(jsonString, cls);
/*  66:140 */     return list;
/*  67:    */   }
/*  68:    */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-util-1.0.1.jar
 * Qualified Name:     com.mingsoft.util.JsonUtil
 * JD-Core Version:    0.7.0.1
 */
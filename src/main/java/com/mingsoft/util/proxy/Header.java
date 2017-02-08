/*  1:   */ package com.mingsoft.util.proxy;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class Header
/*  7:   */ {
/*  8:   */   private Map headers;
/*  9:   */   
/* 10:   */   public Header(String host, String code)
/* 11:   */   {
/* 12:43 */     this.headers = new HashMap();
/* 13:   */     
/* 14:45 */     this.headers.put("Host", host);
/* 15:46 */     this.headers.put("Connection", "keep-alive");
/* 16:47 */     this.headers.put("Referer", "http://" + host);
/* 17:48 */     this.headers.put("Cache-Control", "max-age=0");
/* 18:49 */     this.headers.put("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.186 Safari/535.1");
/* 19:50 */     this.headers.put("Content-Type", "application/x-www-form-urlencoded");
/* 20:51 */     this.headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
/* 21:52 */     this.headers.put("Accept-Language", "zh-CN,zh;q=0.8");
/* 22:53 */     this.headers.put("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
/* 23:54 */     this.headers.put("Cookie", getCookie());
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getCookie()
/* 27:   */   {
/* 28:66 */     if (this.headers.get("Cookie") != null) {
/* 29:67 */       return (String)this.headers.get("Cookie");
/* 30:   */     }
/* 31:68 */     return null;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setCookie(String cookie)
/* 35:   */   {
/* 36:78 */     this.headers.put("Cookie", cookie);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setHeader(String key, String value)
/* 40:   */   {
/* 41:90 */     this.headers.put(key, value);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public Map getHeaders()
/* 45:   */   {
/* 46:98 */     return this.headers;
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-proxy-1.0.0.jar
 * Qualified Name:     com.mingsoft.util.proxy.Header
 * JD-Core Version:    0.7.0.1
 */
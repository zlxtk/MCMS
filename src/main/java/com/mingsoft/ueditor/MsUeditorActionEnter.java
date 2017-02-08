/*  1:   */ package com.mingsoft.ueditor;
/*  2:   */ 
/*  3:   */ import com.baidu.ueditor.ActionEnter;
/*  4:   */ import com.baidu.ueditor.ConfigManager;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import javax.servlet.http.HttpServletRequest;
/*  8:   */ import org.json.JSONObject;
/*  9:   */ 
/* 10:   */ public class MsUeditorActionEnter
/* 11:   */   extends ActionEnter
/* 12:   */ {
/* 13:   */   public MsUeditorActionEnter(HttpServletRequest request, String rootPath, String jsonConfig)
/* 14:   */   {
/* 15:16 */     super(request, rootPath);
/* 16:18 */     if ((jsonConfig == null) || (jsonConfig.trim().equals("")) || (jsonConfig.length() < 0)) {
/* 17:19 */       return;
/* 18:   */     }
/* 19:21 */     ConfigManager config = getConfigManager();
/* 20:   */     
/* 21:23 */     JSONObject _jsonConfig = new JSONObject(jsonConfig);
/* 22:   */     
/* 23:25 */     JSONObject jsonObject = config.getAllConfig();
/* 24:   */     
/* 25:27 */     Iterator iterator = _jsonConfig.keys();
/* 26:28 */     new ArrayList();
/* 27:29 */     while (iterator.hasNext())
/* 28:   */     {
/* 29:30 */       String key = (String)iterator.next();
/* 30:   */       
/* 31:32 */       jsonObject.put(key, _jsonConfig.get(key));
/* 32:   */     }
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-ueditor-1.0.0-SNAPSHOT.jar
 * Qualified Name:     com.mingsoft.ueditor.MsUeditorActionEnter
 * JD-Core Version:    0.7.0.1
 */
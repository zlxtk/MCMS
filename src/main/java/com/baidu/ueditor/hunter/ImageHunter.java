/*   1:    */ package com.baidu.ueditor.hunter;
/*   2:    */ 
/*   3:    */ import com.baidu.ueditor.PathFormat;
/*   4:    */ import com.baidu.ueditor.define.BaseState;
/*   5:    */ import com.baidu.ueditor.define.MIMEType;
/*   6:    */ import com.baidu.ueditor.define.MultiState;
/*   7:    */ import com.baidu.ueditor.define.State;
/*   8:    */ import com.baidu.ueditor.upload.StorageManager;
/*   9:    */ import java.net.HttpURLConnection;
/*  10:    */ import java.net.InetAddress;
/*  11:    */ import java.net.URL;
/*  12:    */ import java.net.UnknownHostException;
/*  13:    */ import java.util.Arrays;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ 
/*  17:    */ public class ImageHunter
/*  18:    */ {
/*  19: 26 */   private String filename = null;
/*  20: 27 */   private String savePath = null;
/*  21: 28 */   private String rootPath = null;
/*  22: 29 */   private List<String> allowTypes = null;
/*  23: 30 */   private long maxSize = -1L;
/*  24: 32 */   private List<String> filters = null;
/*  25:    */   
/*  26:    */   public ImageHunter(Map<String, Object> conf)
/*  27:    */   {
/*  28: 36 */     this.filename = ((String)conf.get("filename"));
/*  29: 37 */     this.savePath = ((String)conf.get("savePath"));
/*  30: 38 */     this.rootPath = ((String)conf.get("rootPath"));
/*  31: 39 */     this.maxSize = ((Long)conf.get("maxSize")).longValue();
/*  32: 40 */     this.allowTypes = Arrays.asList((String[])conf.get("allowFiles"));
/*  33: 41 */     this.filters = Arrays.asList((String[])conf.get("filter"));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public State capture(String[] list)
/*  37:    */   {
/*  38: 47 */     MultiState state = new MultiState(true);
/*  39: 49 */     for (String source : list) {
/*  40: 50 */       state.addState(captureRemoteData(source));
/*  41:    */     }
/*  42: 53 */     return state;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public State captureRemoteData(String urlStr)
/*  46:    */   {
/*  47: 59 */     HttpURLConnection connection = null;
/*  48: 60 */     URL url = null;
/*  49: 61 */     String suffix = null;
/*  50:    */     try
/*  51:    */     {
/*  52: 64 */       url = new URL(urlStr);
/*  53: 66 */       if (!validHost(url.getHost())) {
/*  54: 67 */         return new BaseState(false, 201);
/*  55:    */       }
/*  56: 70 */       connection = (HttpURLConnection)url.openConnection();
/*  57:    */       
/*  58: 72 */       connection.setInstanceFollowRedirects(true);
/*  59: 73 */       connection.setUseCaches(true);
/*  60: 75 */       if (!validContentState(connection.getResponseCode())) {
/*  61: 76 */         return new BaseState(false, 202);
/*  62:    */       }
/*  63: 79 */       suffix = MIMEType.getSuffix(connection.getContentType());
/*  64: 81 */       if (!validFileType(suffix)) {
/*  65: 82 */         return new BaseState(false, 8);
/*  66:    */       }
/*  67: 85 */       if (!validFileSize(connection.getContentLength())) {
/*  68: 86 */         return new BaseState(false, 1);
/*  69:    */       }
/*  70: 89 */       String savePath = getPath(this.savePath, this.filename, suffix);
/*  71: 90 */       String physicalPath = this.rootPath + savePath;
/*  72:    */       
/*  73: 92 */       State state = StorageManager.saveFileByInputStream(connection.getInputStream(), physicalPath);
/*  74: 94 */       if (state.isSuccess())
/*  75:    */       {
/*  76: 95 */         state.putInfo("url", PathFormat.format(savePath));
/*  77: 96 */         state.putInfo("source", urlStr);
/*  78:    */       }
/*  79: 99 */       return state;
/*  80:    */     }
/*  81:    */     catch (Exception localException) {}
/*  82:102 */     return new BaseState(false, 203);
/*  83:    */   }
/*  84:    */   
/*  85:    */   private String getPath(String savePath, String filename, String suffix)
/*  86:    */   {
/*  87:109 */     return PathFormat.parse(savePath + suffix, filename);
/*  88:    */   }
/*  89:    */   
/*  90:    */   private boolean validHost(String hostname)
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:115 */       InetAddress ip = InetAddress.getByName(hostname);
/*  95:117 */       if (ip.isSiteLocalAddress()) {
/*  96:118 */         return false;
/*  97:    */       }
/*  98:    */     }
/*  99:    */     catch (UnknownHostException localUnknownHostException)
/* 100:    */     {
/* 101:121 */       return false;
/* 102:    */     }
/* 103:124 */     return !this.filters.contains(hostname);
/* 104:    */   }
/* 105:    */   
/* 106:    */   private boolean validContentState(int code)
/* 107:    */   {
/* 108:130 */     return 200 == code;
/* 109:    */   }
/* 110:    */   
/* 111:    */   private boolean validFileType(String type)
/* 112:    */   {
/* 113:136 */     return this.allowTypes.contains(type);
/* 114:    */   }
/* 115:    */   
/* 116:    */   private boolean validFileSize(int size)
/* 117:    */   {
/* 118:141 */     return size < this.maxSize;
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-ueditor-1.0.0-SNAPSHOT.jar
 * Qualified Name:     com.baidu.ueditor.hunter.ImageHunter
 * JD-Core Version:    0.7.0.1
 */
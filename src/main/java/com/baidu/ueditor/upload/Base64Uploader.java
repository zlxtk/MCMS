/*  1:   */ package com.baidu.ueditor.upload;
/*  2:   */ 
/*  3:   */ import com.baidu.ueditor.PathFormat;
/*  4:   */ import com.baidu.ueditor.define.BaseState;
/*  5:   */ import com.baidu.ueditor.define.FileType;
/*  6:   */ import com.baidu.ueditor.define.State;
/*  7:   */ import java.util.Map;
/*  8:   */ import org.apache.commons.codec.binary.Base64;
/*  9:   */ 
/* 10:   */ public final class Base64Uploader
/* 11:   */ {
/* 12:   */   public static State save(String content, Map<String, Object> conf)
/* 13:   */   {
/* 14:17 */     byte[] data = decode(content);
/* 15:   */     
/* 16:19 */     long maxSize = ((Long)conf.get("maxSize")).longValue();
/* 17:21 */     if (!validSize(data, maxSize)) {
/* 18:22 */       return new BaseState(false, 1);
/* 19:   */     }
/* 20:25 */     String suffix = FileType.getSuffix("JPG");
/* 21:   */     
/* 22:27 */     String savePath = PathFormat.parse((String)conf.get("savePath"), 
/* 23:28 */       (String)conf.get("filename"));
/* 24:   */     
/* 25:30 */     savePath = savePath + suffix;
/* 26:31 */     String physicalPath = (String)conf.get("rootPath") + savePath;
/* 27:   */     
/* 28:33 */     State storageState = StorageManager.saveBinaryFile(data, physicalPath);
/* 29:35 */     if (storageState.isSuccess())
/* 30:   */     {
/* 31:36 */       storageState.putInfo("url", PathFormat.format(savePath));
/* 32:37 */       storageState.putInfo("type", suffix);
/* 33:38 */       storageState.putInfo("original", "");
/* 34:   */     }
/* 35:41 */     return storageState;
/* 36:   */   }
/* 37:   */   
/* 38:   */   private static byte[] decode(String content)
/* 39:   */   {
/* 40:45 */     return Base64.decodeBase64(content);
/* 41:   */   }
/* 42:   */   
/* 43:   */   private static boolean validSize(byte[] data, long length)
/* 44:   */   {
/* 45:49 */     return data.length <= length;
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-ueditor-1.0.0-SNAPSHOT.jar
 * Qualified Name:     com.baidu.ueditor.upload.Base64Uploader
 * JD-Core Version:    0.7.0.1
 */
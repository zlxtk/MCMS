/*  1:   */ package com.baidu.ueditor.upload;
/*  2:   */ 
/*  3:   */ import com.baidu.ueditor.PathFormat;
/*  4:   */ import com.baidu.ueditor.define.BaseState;
/*  5:   */ import com.baidu.ueditor.define.FileType;
/*  6:   */ import com.baidu.ueditor.define.State;
/*  7:   */ import java.io.IOException;
/*  8:   */ import java.io.InputStream;
/*  9:   */ import java.util.Arrays;
/* 10:   */ import java.util.List;
/* 11:   */ import java.util.Map;
/* 12:   */ import javax.servlet.http.HttpServletRequest;
/* 13:   */ import org.apache.commons.fileupload.FileItemIterator;
/* 14:   */ import org.apache.commons.fileupload.FileItemStream;
/* 15:   */ import org.apache.commons.fileupload.FileUploadException;
/* 16:   */ import org.apache.commons.fileupload.disk.DiskFileItemFactory;
/* 17:   */ import org.apache.commons.fileupload.servlet.ServletFileUpload;
/* 18:   */ 
/* 19:   */ public class BinaryUploader
/* 20:   */ {
/* 21:   */   public static final State save(HttpServletRequest request, Map<String, Object> conf)
/* 22:   */   {
/* 23:27 */     FileItemStream fileStream = null;
/* 24:28 */     boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;
/* 25:30 */     if (!ServletFileUpload.isMultipartContent(request)) {
/* 26:31 */       return new BaseState(false, 5);
/* 27:   */     }
/* 28:34 */     ServletFileUpload upload = new ServletFileUpload(
/* 29:35 */       new DiskFileItemFactory());
/* 30:37 */     if (isAjaxUpload) {
/* 31:38 */       upload.setHeaderEncoding("UTF-8");
/* 32:   */     }
/* 33:   */     try
/* 34:   */     {
/* 35:42 */       FileItemIterator iterator = upload.getItemIterator(request);
/* 36:44 */       while (iterator.hasNext())
/* 37:   */       {
/* 38:45 */         fileStream = iterator.next();
/* 39:47 */         if (!fileStream.isFormField()) {
/* 40:   */           break;
/* 41:   */         }
/* 42:49 */         fileStream = null;
/* 43:   */       }
/* 44:52 */       if (fileStream == null) {
/* 45:53 */         return new BaseState(false, 7);
/* 46:   */       }
/* 47:56 */       String savePath = (String)conf.get("savePath");
/* 48:57 */       String originFileName = fileStream.getName();
/* 49:58 */       String suffix = FileType.getSuffixByFilename(originFileName);
/* 50:   */       
/* 51:60 */       originFileName = originFileName.substring(0, 
/* 52:61 */         originFileName.length() - suffix.length());
/* 53:62 */       savePath = savePath + suffix;
/* 54:   */       
/* 55:64 */       long maxSize = ((Long)conf.get("maxSize")).longValue();
/* 56:66 */       if (!validType(suffix, (String[])conf.get("allowFiles"))) {
/* 57:67 */         return new BaseState(false, 8);
/* 58:   */       }
/* 59:70 */       savePath = PathFormat.parse(savePath, originFileName);
/* 60:   */       
/* 61:72 */       String physicalPath = (String)conf.get("rootPath") + savePath;
/* 62:   */       
/* 63:74 */       InputStream is = fileStream.openStream();
/* 64:75 */       State storageState = StorageManager.saveFileByInputStream(is, 
/* 65:76 */         physicalPath, maxSize);
/* 66:77 */       is.close();
/* 67:79 */       if (storageState.isSuccess())
/* 68:   */       {
/* 69:80 */         storageState.putInfo("url", PathFormat.format(savePath));
/* 70:81 */         storageState.putInfo("type", suffix);
/* 71:82 */         storageState.putInfo("original", originFileName + suffix);
/* 72:   */       }
/* 73:85 */       return storageState;
/* 74:   */     }
/* 75:   */     catch (FileUploadException localFileUploadException)
/* 76:   */     {
/* 77:87 */       return new BaseState(false, 6);
/* 78:   */     }
/* 79:   */     catch (IOException localIOException) {}
/* 80:90 */     return new BaseState(false, 4);
/* 81:   */   }
/* 82:   */   
/* 83:   */   private static boolean validType(String type, String[] allowTypes)
/* 84:   */   {
/* 85:94 */     List<String> list = Arrays.asList(allowTypes);
/* 86:   */     
/* 87:96 */     return list.contains(type);
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-ueditor-1.0.0-SNAPSHOT.jar
 * Qualified Name:     com.baidu.ueditor.upload.BinaryUploader
 * JD-Core Version:    0.7.0.1
 */
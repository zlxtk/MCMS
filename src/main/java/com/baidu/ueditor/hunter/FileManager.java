/*   1:    */ package com.baidu.ueditor.hunter;
/*   2:    */ 
/*   3:    */ import com.baidu.ueditor.PathFormat;
/*   4:    */ import com.baidu.ueditor.define.BaseState;
/*   5:    */ import com.baidu.ueditor.define.MultiState;
/*   6:    */ import com.baidu.ueditor.define.State;
/*   7:    */ import java.io.File;
/*   8:    */ import java.util.Arrays;
/*   9:    */ import java.util.Collection;
/*  10:    */ import java.util.Map;
/*  11:    */ import org.apache.commons.io.FileUtils;
/*  12:    */ 
/*  13:    */ public class FileManager
/*  14:    */ {
/*  15: 18 */   private String dir = null;
/*  16: 19 */   private String rootPath = null;
/*  17: 20 */   private String[] allowFiles = null;
/*  18: 21 */   private int count = 0;
/*  19:    */   
/*  20:    */   public FileManager(Map<String, Object> conf)
/*  21:    */   {
/*  22: 25 */     this.rootPath = ((String)conf.get("rootPath"));
/*  23: 26 */     this.dir = (this.rootPath + (String)conf.get("dir"));
/*  24: 27 */     this.allowFiles = getAllowFiles(conf.get("allowFiles"));
/*  25: 28 */     this.count = ((Integer)conf.get("count")).intValue();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public State listFile(int index)
/*  29:    */   {
/*  30: 34 */     File dir = new File(this.dir);
/*  31: 35 */     State state = null;
/*  32: 37 */     if (!dir.exists()) {
/*  33: 38 */       return new BaseState(false, 302);
/*  34:    */     }
/*  35: 41 */     if (!dir.isDirectory()) {
/*  36: 42 */       return new BaseState(false, 301);
/*  37:    */     }
/*  38: 45 */     Collection<File> list = FileUtils.listFiles(dir, this.allowFiles, true);
/*  39: 47 */     if ((index < 0) || (index > list.size()))
/*  40:    */     {
/*  41: 48 */       state = new MultiState(true);
/*  42:    */     }
/*  43:    */     else
/*  44:    */     {
/*  45: 50 */       Object[] fileList = Arrays.copyOfRange(list.toArray(), index, index + this.count);
/*  46: 51 */       state = getState(fileList);
/*  47:    */     }
/*  48: 54 */     state.putInfo("start", index);
/*  49: 55 */     state.putInfo("total", list.size());
/*  50:    */     
/*  51: 57 */     return state;
/*  52:    */   }
/*  53:    */   
/*  54:    */   private State getState(Object[] files)
/*  55:    */   {
/*  56: 63 */     MultiState state = new MultiState(true);
/*  57: 64 */     BaseState fileState = null;
/*  58:    */     
/*  59: 66 */     File file = null;
/*  60: 68 */     for (Object obj : files)
/*  61:    */     {
/*  62: 69 */       if (obj == null) {
/*  63:    */         break;
/*  64:    */       }
/*  65: 72 */       file = (File)obj;
/*  66: 73 */       fileState = new BaseState(true);
/*  67: 74 */       fileState.putInfo("url", PathFormat.format(getPath(file)));
/*  68: 75 */       state.addState(fileState);
/*  69:    */     }
/*  70: 78 */     return state;
/*  71:    */   }
/*  72:    */   
/*  73:    */   private String getPath(File file)
/*  74:    */   {
/*  75: 84 */     String path = file.getAbsolutePath();
/*  76:    */     
/*  77: 86 */     return path.replace(this.rootPath, "/");
/*  78:    */   }
/*  79:    */   
/*  80:    */   private String[] getAllowFiles(Object fileExt)
/*  81:    */   {
/*  82: 92 */     String[] exts = null;
/*  83: 93 */     String ext = null;
/*  84: 95 */     if (fileExt == null) {
/*  85: 96 */       return new String[0];
/*  86:    */     }
/*  87: 99 */     exts = (String[])fileExt;
/*  88:    */     
/*  89:101 */     int i = 0;
/*  90:101 */     for (int len = exts.length; i < len; i++)
/*  91:    */     {
/*  92:103 */       ext = exts[i];
/*  93:104 */       exts[i] = ext.replace(".", "");
/*  94:    */     }
/*  95:108 */     return exts;
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-ueditor-1.0.0-SNAPSHOT.jar
 * Qualified Name:     com.baidu.ueditor.hunter.FileManager
 * JD-Core Version:    0.7.0.1
 */
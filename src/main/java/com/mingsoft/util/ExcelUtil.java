/*   1:    */ package com.mingsoft.util;
/*   2:    */ 
/*   3:    */ import java.io.OutputStream;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import javax.servlet.http.HttpServletResponse;
/*   8:    */ import jxl.SheetSettings;
/*   9:    */ import jxl.Workbook;
/*  10:    */ import jxl.format.Alignment;
/*  11:    */ import jxl.format.Border;
/*  12:    */ import jxl.format.BorderLineStyle;
/*  13:    */ import jxl.format.VerticalAlignment;
/*  14:    */ import jxl.write.Label;
/*  15:    */ import jxl.write.WritableCellFormat;
/*  16:    */ import jxl.write.WritableFont;
/*  17:    */ import jxl.write.WritableSheet;
/*  18:    */ import jxl.write.WritableWorkbook;
/*  19:    */ 
/*  20:    */ public class ExcelUtil
/*  21:    */ {
/*  22:    */   public static final String exportExcel(String fileName, String[] titles, List<Object> listContent, HttpServletResponse response)
/*  23:    */   {
/*  24: 62 */     String result = "系统提示：Excel文件导出成功！";
/*  25:    */     try
/*  26:    */     {
/*  27: 66 */       OutputStream os = response.getOutputStream();
/*  28: 67 */       response.reset();
/*  29: 68 */       response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
/*  30:    */       
/*  31: 70 */       response.setContentType("application/msexcel");
/*  32:    */       
/*  33:    */ 
/*  34:    */ 
/*  35: 74 */       WritableWorkbook workbook = Workbook.createWorkbook(os);
/*  36:    */       
/*  37:    */ 
/*  38:    */ 
/*  39: 78 */       WritableSheet sheet = workbook.createSheet("Sheet1", 0);
/*  40:    */       
/*  41:    */ 
/*  42: 81 */       SheetSettings sheetset = sheet.getSettings();
/*  43: 82 */       sheetset.setProtected(false);
/*  44:    */       
/*  45:    */ 
/*  46: 85 */       WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
/*  47: 86 */       WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, 
/*  48: 87 */         WritableFont.BOLD);
/*  49:    */       
/*  50:    */ 
/*  51:    */ 
/*  52: 91 */       WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
/*  53: 92 */       wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN);
/*  54: 93 */       wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  55: 94 */       wcf_center.setAlignment(Alignment.CENTRE);
/*  56: 95 */       wcf_center.setWrap(false);
/*  57:    */       
/*  58:    */ 
/*  59: 98 */       WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
/*  60: 99 */       wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN);
/*  61:100 */       wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  62:101 */       wcf_left.setAlignment(Alignment.LEFT);
/*  63:102 */       wcf_left.setWrap(false);
/*  64:108 */       for (int i = 0; i < titles.length; i++) {
/*  65:109 */         sheet.addCell(new Label(i, 0, titles[i], wcf_center));
/*  66:    */       }
/*  67:112 */       int i = 1;
/*  68:113 */       for (Object obj : listContent)
/*  69:    */       {
/*  70:114 */         int j = 0;
/*  71:115 */         for (Object field : (Object[])obj)
/*  72:    */         {
/*  73:116 */           if ((field instanceof Map))
/*  74:    */           {
/*  75:117 */             if (field != null)
/*  76:    */             {
/*  77:118 */               Map temp = (Map)field;
/*  78:119 */               if ((temp.get("format") != null) && ((temp.get("format") instanceof WritableCellFormat))) {
/*  79:120 */                 sheet.addCell(new Label(j, i, field != null ? String.valueOf(temp.get("value")) : "", (WritableCellFormat)temp.get("format")));
/*  80:    */               } else {
/*  81:122 */                 sheet.addCell(new Label(j, i, field != null ? String.valueOf(field) : "", wcf_left));
/*  82:    */               }
/*  83:    */             }
/*  84:    */           }
/*  85:    */           else {
/*  86:126 */             sheet.addCell(new Label(j, i, field != null ? String.valueOf(field) : "", wcf_left));
/*  87:    */           }
/*  88:129 */           j++;
/*  89:    */         }
/*  90:131 */         i++;
/*  91:    */       }
/*  92:134 */       workbook.write();
/*  93:    */       
/*  94:136 */       workbook.close();
/*  95:    */     }
/*  96:    */     catch (Exception e)
/*  97:    */     {
/*  98:139 */       result = "系统提示：Excel文件导出失败，原因：" + e.toString();
/*  99:140 */       System.out.println(result);
/* 100:141 */       e.printStackTrace();
/* 101:    */     }
/* 102:143 */     return result;
/* 103:    */   }
/* 104:    */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-util-1.0.1.jar
 * Qualified Name:     com.mingsoft.util.ExcelUtil
 * JD-Core Version:    0.7.0.1
 */
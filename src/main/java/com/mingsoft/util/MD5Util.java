/*  1:   */ package com.mingsoft.util;
/*  2:   */ 
/*  3:   */ import java.security.MessageDigest;
/*  4:   */ 
/*  5:   */ public class MD5Util
/*  6:   */ {
/*  7:   */   private static String byteArrayToHexString(byte[] b)
/*  8:   */   {
/*  9:41 */     StringBuffer resultSb = new StringBuffer();
/* 10:42 */     for (int i = 0; i < b.length; i++) {
/* 11:43 */       resultSb.append(byteToHexString(b[i]));
/* 12:   */     }
/* 13:44 */     return resultSb.toString();
/* 14:   */   }
/* 15:   */   
/* 16:   */   private static String byteToHexString(byte b)
/* 17:   */   {
/* 18:53 */     int n = b;
/* 19:54 */     if (n < 0) {
/* 20:55 */       n += 256;
/* 21:   */     }
/* 22:56 */     int d1 = n / 16;
/* 23:57 */     int d2 = n % 16;
/* 24:58 */     return hexDigits[d1] + hexDigits[d2];
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static String MD5Encode(String origin, String charsetname)
/* 28:   */   {
/* 29:68 */     String resultString = null;
/* 30:   */     try
/* 31:   */     {
/* 32:70 */       resultString = new String(origin);
/* 33:71 */       MessageDigest md = MessageDigest.getInstance("MD5");
/* 34:72 */       if ((charsetname == null) || ("".equals(charsetname))) {
/* 35:73 */         resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
/* 36:   */       } else {
/* 37:75 */         resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
/* 38:   */       }
/* 39:   */     }
/* 40:   */     catch (Exception localException) {}
/* 41:78 */     return resultString;
/* 42:   */   }
/* 43:   */   
/* 44:84 */   private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
/* 45:   */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-util-1.0.1.jar
 * Qualified Name:     com.mingsoft.util.MD5Util
 * JD-Core Version:    0.7.0.1
 */
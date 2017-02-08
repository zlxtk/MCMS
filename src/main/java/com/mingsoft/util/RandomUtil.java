/*   1:    */ package com.mingsoft.util;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ 
/*   5:    */ public class RandomUtil
/*   6:    */ {
/*   7:    */   public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
/*   8:    */   public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
/*   9:    */   public static final String numberChar = "0123456789";
/*  10:    */   
/*  11:    */   public static String generateNumber(int length)
/*  12:    */   {
/*  13: 48 */     StringBuffer sb = new StringBuffer();
/*  14: 49 */     Random random = new Random();
/*  15: 50 */     for (int i = 0; i < length; i++) {
/*  16: 51 */       sb.append("0123456789".charAt(random.nextInt("0123456789".length())));
/*  17:    */     }
/*  18: 53 */     return sb.toString();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static String generateString(int length)
/*  22:    */   {
/*  23: 64 */     StringBuffer sb = new StringBuffer();
/*  24: 65 */     Random random = new Random();
/*  25: 66 */     for (int i = 0; i < length; i++) {
/*  26: 67 */       sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
/*  27:    */     }
/*  28: 69 */     return sb.toString();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static String generateMixString(int length)
/*  32:    */   {
/*  33: 80 */     StringBuffer sb = new StringBuffer();
/*  34: 81 */     Random random = new Random();
/*  35: 82 */     for (int i = 0; i < length; i++) {
/*  36: 83 */       sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
/*  37:    */     }
/*  38: 85 */     return sb.toString();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static String generateLowerString(int length)
/*  42:    */   {
/*  43: 96 */     return generateMixString(length).toLowerCase();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static String generateUpperString(int length)
/*  47:    */   {
/*  48:107 */     return generateMixString(length).toUpperCase();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static String generateZeroString(int length)
/*  52:    */   {
/*  53:118 */     StringBuffer sb = new StringBuffer();
/*  54:119 */     for (int i = 0; i < length; i++) {
/*  55:120 */       sb.append('0');
/*  56:    */     }
/*  57:122 */     return sb.toString();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static String toFixdLengthString(long num, int fixdlenth)
/*  61:    */   {
/*  62:135 */     StringBuffer sb = new StringBuffer();
/*  63:136 */     String strNum = String.valueOf(num);
/*  64:137 */     if (fixdlenth - strNum.length() >= 0) {
/*  65:138 */       sb.append(generateZeroString(fixdlenth - strNum.length()));
/*  66:    */     } else {
/*  67:140 */       throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
/*  68:    */     }
/*  69:142 */     sb.append(strNum);
/*  70:143 */     return sb.toString();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static String toFixdLengthString(int num, int fixdlenth)
/*  74:    */   {
/*  75:156 */     StringBuffer sb = new StringBuffer();
/*  76:157 */     String strNum = String.valueOf(num);
/*  77:158 */     if (fixdlenth - strNum.length() >= 0) {
/*  78:159 */       sb.append(generateZeroString(fixdlenth - strNum.length()));
/*  79:    */     } else {
/*  80:161 */       throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
/*  81:    */     }
/*  82:163 */     sb.append(strNum);
/*  83:164 */     return sb.toString();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static int rondomOneNum(int numLength)
/*  87:    */   {
/*  88:173 */     Random random = new Random();
/*  89:174 */     return random.nextInt(numLength);
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-util-1.0.1.jar
 * Qualified Name:     com.mingsoft.util.RandomUtil
 * JD-Core Version:    0.7.0.1
 */
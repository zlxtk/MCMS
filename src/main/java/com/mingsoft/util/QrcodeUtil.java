/*   1:    */ package com.mingsoft.util;
/*   2:    */ 
/*   3:    */ import com.google.zxing.BarcodeFormat;
/*   4:    */ import com.google.zxing.BinaryBitmap;
/*   5:    */ import com.google.zxing.DecodeHintType;
/*   6:    */ import com.google.zxing.EncodeHintType;
/*   7:    */ import com.google.zxing.LuminanceSource;
/*   8:    */ import com.google.zxing.MultiFormatReader;
/*   9:    */ import com.google.zxing.MultiFormatWriter;
/*  10:    */ import com.google.zxing.ReaderException;
/*  11:    */ import com.google.zxing.Result;
/*  12:    */ import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
/*  13:    */ import com.google.zxing.common.BitMatrix;
/*  14:    */ import com.google.zxing.common.HybridBinarizer;
/*  15:    */ import java.awt.image.BufferedImage;
/*  16:    */ import java.io.File;
/*  17:    */ import java.io.IOException;
/*  18:    */ import java.io.PrintStream;
/*  19:    */ import java.util.Hashtable;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.imageio.ImageIO;
/*  22:    */ 
/*  23:    */ public class QrcodeUtil
/*  24:    */ {
/*  25:    */   private static final int BLACK = -16777216;
/*  26:    */   private static final int WHITE = -1;
/*  27:    */   
/*  28:    */   public static void encode(String contents, File file, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints)
/*  29:    */   {
/*  30:    */     try
/*  31:    */     {
/*  32: 69 */       BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, format, width, height);
/*  33: 70 */       BufferedImage image = new BufferedImage(width, height, 2);
/*  34: 71 */       for (int x = 0; x < width; x++) {
/*  35: 72 */         for (int y = 0; y < height; y++) {
/*  36: 73 */           image.setRGB(x, y, bitMatrix.get(x, y) ? -16777216 : -1);
/*  37:    */         }
/*  38:    */       }
/*  39: 76 */       ImageIO.write(image, "png", file);
/*  40:    */     }
/*  41:    */     catch (Exception e)
/*  42:    */     {
/*  43: 78 */       e.printStackTrace();
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void decode(File file)
/*  48:    */   {
/*  49:    */     try
/*  50:    */     {
/*  51:    */       try
/*  52:    */       {
/*  53: 91 */         BufferedImage image = ImageIO.read(file);
/*  54: 92 */         if (image == null) {
/*  55: 93 */           System.out.println("Could not decode image");
/*  56:    */         }
/*  57: 95 */         LuminanceSource source = new BufferedImageLuminanceSource(image);
/*  58: 96 */         BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
/*  59:    */         
/*  60:    */ 
/*  61: 99 */         Hashtable hints = new Hashtable();
/*  62:    */         
/*  63:101 */         hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
/*  64:102 */         Result result = new MultiFormatReader().decode(bitmap, hints);
/*  65:103 */         String resultStr = result.getText();
/*  66:104 */         System.out.println("解析后内容：" + resultStr);
/*  67:    */       }
/*  68:    */       catch (IOException ioe)
/*  69:    */       {
/*  70:106 */         System.out.println(ioe.toString());
/*  71:    */       }
/*  72:    */       catch (ReaderException re)
/*  73:    */       {
/*  74:108 */         System.out.println(re.toString());
/*  75:    */       }
/*  76:113 */       return;
/*  77:    */     }
/*  78:    */     catch (Exception ex)
/*  79:    */     {
/*  80:111 */       System.out.println(ex.toString());
/*  81:    */     }
/*  82:    */   }
/*  83:    */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-util-1.0.1.jar
 * Qualified Name:     com.mingsoft.util.QrcodeUtil
 * JD-Core Version:    0.7.0.1
 */
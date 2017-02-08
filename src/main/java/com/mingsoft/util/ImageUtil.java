/*   1:    */ package com.mingsoft.util;
/*   2:    */ 
/*   3:    */ import com.mortennobel.imagescaling.ResampleOp;
/*   4:    */ import com.sun.image.codec.jpeg.JPEGCodec;
/*   5:    */ import com.sun.image.codec.jpeg.JPEGImageEncoder;
/*   6:    */ import java.awt.Graphics;
/*   7:    */ import java.awt.Image;
/*   8:    */ import java.awt.Rectangle;
/*   9:    */ import java.awt.Toolkit;
/*  10:    */ import java.awt.color.ColorSpace;
/*  11:    */ import java.awt.geom.AffineTransform;
/*  12:    */ import java.awt.image.AffineTransformOp;
/*  13:    */ import java.awt.image.BufferedImage;
/*  14:    */ import java.awt.image.ColorConvertOp;
/*  15:    */ import java.awt.image.CropImageFilter;
/*  16:    */ import java.awt.image.FilteredImageSource;
/*  17:    */ import java.awt.image.ImageFilter;
/*  18:    */ import java.io.ByteArrayInputStream;
/*  19:    */ import java.io.ByteArrayOutputStream;
/*  20:    */ import java.io.File;
/*  21:    */ import java.io.FileInputStream;
/*  22:    */ import java.io.FileNotFoundException;
/*  23:    */ import java.io.FileOutputStream;
/*  24:    */ import java.io.IOException;
/*  25:    */ import java.io.InputStream;
/*  26:    */ import java.io.OutputStream;
/*  27:    */ import java.util.Iterator;
/*  28:    */ import javax.imageio.ImageIO;
/*  29:    */ import javax.imageio.ImageReadParam;
/*  30:    */ import javax.imageio.ImageReader;
/*  31:    */ import javax.imageio.stream.ImageInputStream;
/*  32:    */ import javax.imageio.stream.MemoryCacheImageInputStream;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ 
/*  35:    */ public class ImageUtil
/*  36:    */ {
/*  37: 70 */   protected static Logger LOGGER = Logger.getLogger(ImageUtil.class);
/*  38:    */   
/*  39:    */   public static void batchImageWidthHeight(String directoryPath, int sSize, int tSize)
/*  40:    */   {
/*  41: 82 */     File dir = new File(directoryPath);
/*  42:    */     
/*  43: 84 */     File[] files = dir.listFiles();
/*  44: 87 */     if (files == null) {
/*  45: 88 */       return;
/*  46:    */     }
/*  47: 92 */     for (int i = 0; i < files.length; i++) {
/*  48: 94 */       if (files[i].isDirectory()) {
/*  49: 95 */         batchImageWidthHeight(files[i].getAbsolutePath(), sSize, tSize);
/*  50:    */       } else {
/*  51:    */         try
/*  52:    */         {
/*  53:100 */           files[i].getPath();
/*  54:101 */           if (isPic(files[i].getPath()))
/*  55:    */           {
/*  56:103 */             BufferedImage srcImage = ImageIO.read(files[i]);
/*  57:104 */             getImageWidthHeight(srcImage, sSize, tSize);
/*  58:    */           }
/*  59:    */         }
/*  60:    */         catch (IOException e)
/*  61:    */         {
/*  62:112 */           e.printStackTrace();
/*  63:    */         }
/*  64:    */       }
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static void ChangeImage(String root, double scale)
/*  69:    */     throws IOException
/*  70:    */   {
/*  71:129 */     File file = new File(root);
/*  72:130 */     File[] subFile = file.listFiles();
/*  73:131 */     for (int i = 0; i < subFile.length; i++)
/*  74:    */     {
/*  75:133 */       String name = subFile[i].getName();
/*  76:135 */       if (subFile[i].isDirectory()) {
/*  77:136 */         ChangeImage(subFile[i].getAbsolutePath() + "\\", scale);
/*  78:    */       }
/*  79:138 */       String[] names = name.split("//.");
/*  80:139 */       if (StringUtil.isBlank(names[0])) {
/*  81:    */         break;
/*  82:    */       }
/*  83:141 */       scaleHyaline(root + subFile[i].getName(), root + subFile[i].getName(), scale, true);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static void convert(String source, String result)
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:153 */       File f = new File(source);
/*  92:154 */       f.canRead();
/*  93:155 */       f.canWrite();
/*  94:156 */       BufferedImage src = ImageIO.read(f);
/*  95:157 */       ImageIO.write(src, "JPG", new File(result));
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:159 */       LOGGER.error(e);
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static void createImage(String path, BufferedImage bi)
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:171 */       ImageIO.write(bi, path.substring(path.lastIndexOf("."), path.length()).replace(".", ""), new File(path));
/* 108:    */     }
/* 109:    */     catch (IOException e)
/* 110:    */     {
/* 111:173 */       LOGGER.error(e);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static void createImage(String path, byte[] bt) {}
/* 116:    */   
/* 117:    */   public static void cut(String sourceImagePath, String descDir, int width, int height)
/* 118:    */   {
/* 119:    */     try
/* 120:    */     {
/* 121:200 */       BufferedImage bi = ImageIO.read(new File(sourceImagePath));
/* 122:201 */       int srcWidth = bi.getHeight();
/* 123:202 */       int srcHeight = bi.getWidth();
/* 124:203 */       if ((srcWidth > width) && (srcHeight > height))
/* 125:    */       {
/* 126:204 */         Image image = bi.getScaledInstance(srcWidth, srcHeight, 1);
/* 127:205 */         width = 200;
/* 128:206 */         height = 150;
/* 129:207 */         int cols = 0;
/* 130:208 */         int rows = 0;
/* 131:210 */         if (srcWidth % width == 0) {
/* 132:211 */           cols = srcWidth / width;
/* 133:    */         } else {
/* 134:213 */           cols = (int)Math.floor(srcWidth / width) + 1;
/* 135:    */         }
/* 136:215 */         if (srcHeight % height == 0) {
/* 137:216 */           rows = srcHeight / height;
/* 138:    */         } else {
/* 139:218 */           rows = (int)Math.floor(srcHeight / height) + 1;
/* 140:    */         }
/* 141:222 */         for (int i = 0; i < rows; i++) {
/* 142:223 */           for (int j = 0; j < cols; j++)
/* 143:    */           {
/* 144:226 */             ImageFilter cropFilter = new CropImageFilter(j * 200, i * 150, width, height);
/* 145:227 */             Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
/* 146:228 */             BufferedImage tag = new BufferedImage(width, height, 1);
/* 147:229 */             Graphics g = tag.getGraphics();
/* 148:230 */             g.drawImage(img, 0, 0, null);
/* 149:231 */             g.dispose();
/* 150:    */             
/* 151:233 */             ImageIO.write(tag, "JPEG", new File(descDir + "pre_map_" + i + "_" + j + ".jpg"));
/* 152:    */           }
/* 153:    */         }
/* 154:    */       }
/* 155:    */     }
/* 156:    */     catch (Exception e)
/* 157:    */     {
/* 158:238 */       e.printStackTrace();
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static void decodeImage(String sourceImagePath)
/* 163:    */   {
/* 164:247 */     File file = new File(sourceImagePath);
/* 165:248 */     if (file.exists())
/* 166:    */     {
/* 167:249 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 168:250 */       byte[] buffer = new byte[1024];
/* 169:251 */       int length = -1;
/* 170:    */       try
/* 171:    */       {
/* 172:254 */         InputStream is = new FileInputStream(file);
/* 173:    */         try
/* 174:    */         {
/* 175:256 */           while ((length = is.read(buffer)) != -1) {
/* 176:257 */             baos.write(buffer, 0, length);
/* 177:    */           }
/* 178:259 */           baos.flush();
/* 179:    */         }
/* 180:    */         catch (IOException e)
/* 181:    */         {
/* 182:261 */           e.printStackTrace();
/* 183:    */         }
/* 184:263 */         byte[] data = baos.toByteArray();
/* 185:264 */         data[0] = -1;
/* 186:    */         
/* 187:266 */         OutputStream os = new FileOutputStream(file);
/* 188:    */         try
/* 189:    */         {
/* 190:268 */           os.write(data);
/* 191:269 */           os.flush();
/* 192:270 */           os.close();
/* 193:    */         }
/* 194:    */         catch (IOException e1)
/* 195:    */         {
/* 196:273 */           e1.printStackTrace();
/* 197:    */         }
/* 198:    */         try
/* 199:    */         {
/* 200:276 */           is.close();
/* 201:277 */           baos.close();
/* 202:    */         }
/* 203:    */         catch (IOException e)
/* 204:    */         {
/* 205:279 */           e.printStackTrace();
/* 206:    */         }
/* 207:    */         return;
/* 208:    */       }
/* 209:    */       catch (FileNotFoundException e1)
/* 210:    */       {
/* 211:283 */         e1.printStackTrace();
/* 212:    */       }
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   public static void encodeImage(String sourceImagePath)
/* 217:    */   {
/* 218:293 */     File file = new File(sourceImagePath);
/* 219:294 */     if (file.exists())
/* 220:    */     {
/* 221:295 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 222:296 */       byte[] buffer = new byte[1024];
/* 223:297 */       int length = -1;
/* 224:    */       try
/* 225:    */       {
/* 226:300 */         InputStream is = new FileInputStream(file);
/* 227:    */         try
/* 228:    */         {
/* 229:302 */           while ((length = is.read(buffer)) != -1) {
/* 230:303 */             baos.write(buffer, 0, length);
/* 231:    */           }
/* 232:305 */           baos.flush();
/* 233:    */         }
/* 234:    */         catch (IOException e)
/* 235:    */         {
/* 236:307 */           e.printStackTrace();
/* 237:    */         }
/* 238:309 */         byte[] data = baos.toByteArray();
/* 239:310 */         data[0] = 0;
/* 240:    */         
/* 241:312 */         OutputStream os = new FileOutputStream(file);
/* 242:    */         try
/* 243:    */         {
/* 244:314 */           os.write(data);
/* 245:315 */           os.flush();
/* 246:316 */           os.close();
/* 247:    */         }
/* 248:    */         catch (IOException e1)
/* 249:    */         {
/* 250:319 */           e1.printStackTrace();
/* 251:    */         }
/* 252:    */         try
/* 253:    */         {
/* 254:322 */           is.close();
/* 255:323 */           baos.close();
/* 256:    */         }
/* 257:    */         catch (IOException e)
/* 258:    */         {
/* 259:325 */           e.printStackTrace();
/* 260:    */         }
/* 261:    */         return;
/* 262:    */       }
/* 263:    */       catch (FileNotFoundException e1)
/* 264:    */       {
/* 265:329 */         e1.printStackTrace();
/* 266:    */       }
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   public static String getImageType(byte[] imageBytes)
/* 271:    */   {
/* 272:341 */     ByteArrayInputStream bais = null;
/* 273:342 */     MemoryCacheImageInputStream mcis = null;
/* 274:    */     try
/* 275:    */     {
/* 276:344 */       bais = new ByteArrayInputStream(imageBytes);
/* 277:345 */       mcis = new MemoryCacheImageInputStream(bais);
/* 278:346 */       Iterator<ImageReader> itr = ImageIO.getImageReaders(mcis);
/* 279:347 */       while (itr.hasNext())
/* 280:    */       {
/* 281:348 */         ImageReader reader = (ImageReader)itr.next();
/* 282:349 */         String imageName = reader.getClass().getSimpleName();
/* 283:350 */         if ((imageName != null) && ("JPEGImageReader".equalsIgnoreCase(imageName))) {
/* 284:351 */           return "jpeg";
/* 285:    */         }
/* 286:352 */         if ((imageName != null) && ("JPGImageReader".equalsIgnoreCase(imageName))) {
/* 287:353 */           return "jpg";
/* 288:    */         }
/* 289:354 */         if ((imageName != null) && ("pngImageReader".equalsIgnoreCase(imageName))) {
/* 290:355 */           return "png";
/* 291:    */         }
/* 292:    */       }
/* 293:    */     }
/* 294:    */     catch (Exception e)
/* 295:    */     {
/* 296:359 */       LOGGER.error(e);
/* 297:    */     }
/* 298:361 */     return null;
/* 299:    */   }
/* 300:    */   
/* 301:    */   private static int[] getImageWidthHeight(BufferedImage source, int sourceWidth, int targetWidth)
/* 302:    */   {
/* 303:376 */     double ts = targetWidth / sourceWidth;
/* 304:377 */     int newWidth = (int)(source.getWidth() * ts);
/* 305:378 */     int newHeight = (int)(source.getHeight() * ts);
/* 306:379 */     if (newWidth < 3) {
/* 307:380 */       newWidth = 3;
/* 308:    */     }
/* 309:381 */     if (newHeight < 3) {
/* 310:382 */       newHeight = 3;
/* 311:    */     }
/* 312:383 */     int[] wh = new int[2];
/* 313:384 */     wh[0] = newWidth;
/* 314:385 */     wh[1] = newHeight;
/* 315:386 */     return wh;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public static int[] getImageWidthHeight(String sourceImagePath)
/* 319:    */   {
/* 320:    */     try
/* 321:    */     {
/* 322:398 */       BufferedImage bi = ImageIO.read(new File(sourceImagePath));
/* 323:    */       
/* 324:400 */       int[] wh = new int[2];
/* 325:401 */       wh[0] = bi.getWidth();
/* 326:402 */       wh[1] = bi.getHeight();
/* 327:403 */       return wh;
/* 328:    */     }
/* 329:    */     catch (IOException e)
/* 330:    */     {
/* 331:405 */       LOGGER.error(e);
/* 332:    */     }
/* 333:407 */     return null;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public static void gray(String sourceImagePath, String savePath)
/* 337:    */   {
/* 338:    */     try
/* 339:    */     {
/* 340:418 */       BufferedImage src = ImageIO.read(new File(sourceImagePath));
/* 341:419 */       ColorSpace cs = ColorSpace.getInstance(1003);
/* 342:420 */       ColorConvertOp op = new ColorConvertOp(cs, null);
/* 343:421 */       src = op.filter(src, null);
/* 344:422 */       ImageIO.write(src, "JPEG", new File(savePath));
/* 345:    */     }
/* 346:    */     catch (IOException e)
/* 347:    */     {
/* 348:424 */       e.printStackTrace();
/* 349:    */     }
/* 350:    */   }
/* 351:    */   
/* 352:    */   private static boolean isPic(String sourceImagePath)
/* 353:    */   {
/* 354:437 */     String picSfix = "jpg|png|gif";
/* 355:438 */     String[] temp = picSfix.split("\\|");
/* 356:439 */     if (sourceImagePath.indexOf(".") > 0)
/* 357:    */     {
/* 358:440 */       String fileSfix = sourceImagePath.substring(sourceImagePath.indexOf("."), sourceImagePath.length()).replace(".", "");
/* 359:441 */       for (int i = 0; i < temp.length; i++) {
/* 360:442 */         if (fileSfix.equals(temp[i])) {
/* 361:443 */           return true;
/* 362:    */         }
/* 363:    */       }
/* 364:    */     }
/* 365:446 */     return false;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public static void main(String[] args)
/* 369:    */     throws IOException
/* 370:    */   {}
/* 371:    */   
/* 372:    */   public static BufferedImage resize(String sourceImagePath, int width, int height)
/* 373:    */   {
/* 374:    */     try
/* 375:    */     {
/* 376:478 */       BufferedImage inputBufImage = ImageIO.read(new File(sourceImagePath));
/* 377:479 */       ResampleOp resampleOp = new ResampleOp(Math.min(width, inputBufImage.getWidth()), Math.min(height, inputBufImage.getHeight()));
/* 378:480 */       return resampleOp.filter(inputBufImage, null);
/* 379:    */     }
/* 380:    */     catch (IOException e1)
/* 381:    */     {
/* 382:483 */       e1.printStackTrace();
/* 383:    */     }
/* 384:486 */     return null;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public static boolean resizeImage(File sourceImagePath, String savePath, int width, int height, String sufix)
/* 388:    */   {
/* 389:    */     try
/* 390:    */     {
/* 391:506 */       BufferedImage inputBufImage = ImageIO.read(sourceImagePath);
/* 392:507 */       ResampleOp resampleOp = new ResampleOp(Math.min(width, inputBufImage.getWidth()), Math.min(height, inputBufImage.getHeight()));
/* 393:508 */       BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
/* 394:509 */       ImageIO.write(rescaledTomato, sufix, new File(savePath));
/* 395:510 */       return true;
/* 396:    */     }
/* 397:    */     catch (Exception e)
/* 398:    */     {
/* 399:512 */       LOGGER.error(e);
/* 400:    */     }
/* 401:513 */     return false;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public static byte[] resizeImageForBytes(String sourceImagePath, int targetW, int targetH, String type)
/* 405:    */   {
/* 406:    */     try
/* 407:    */     {
/* 408:529 */       BufferedImage image = resize(sourceImagePath, targetW, targetH);
/* 409:530 */       ByteArrayOutputStream outStream = new ByteArrayOutputStream();
/* 410:    */       
/* 411:    */ 
/* 412:    */ 
/* 413:    */ 
/* 414:535 */       ImageIO.write(image, type, outStream);
/* 415:536 */       outStream.flush();
/* 416:537 */       byte[] result = outStream.toByteArray();
/* 417:538 */       outStream.close();
/* 418:539 */       return result;
/* 419:    */     }
/* 420:    */     catch (Exception ex)
/* 421:    */     {
/* 422:541 */       LOGGER.error(ex);
/* 423:    */     }
/* 424:542 */     return null;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public static boolean resizeImageForEncode(File sourceImageFile, String savePath, Integer width, Integer height, String sufix)
/* 428:    */   {
/* 429:    */     try
/* 430:    */     {
/* 431:564 */       BufferedImage inputBufImage = ImageIO.read(sourceImageFile);
/* 432:565 */       ResampleOp resampleOp = new ResampleOp(Math.min(width.intValue(), inputBufImage.getWidth()), Math.min(height.intValue(), inputBufImage.getHeight()));
/* 433:566 */       BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
/* 434:567 */       ImageIO.write(rescaledTomato, sufix, new File(savePath));
/* 435:568 */       return true;
/* 436:    */     }
/* 437:    */     catch (Exception e)
/* 438:    */     {
/* 439:570 */       LOGGER.error(e);
/* 440:    */     }
/* 441:571 */     return false;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public static void rotate(String sourceImagePath, int direction)
/* 445:    */   {
/* 446:586 */     File file = null;
/* 447:587 */     BufferedImage original = null;
/* 448:588 */     BufferedImage bufOut = null;
/* 449:    */     
/* 450:    */ 
/* 451:591 */     file = new File(sourceImagePath);
/* 452:    */     try
/* 453:    */     {
/* 454:593 */       original = ImageIO.read(file);
/* 455:    */     }
/* 456:    */     catch (Exception localException)
/* 457:    */     {
/* 458:595 */       return;
/* 459:    */     }
/* 460:598 */     int width = original.getWidth();
/* 461:599 */     int height = original.getHeight();
/* 462:600 */     bufOut = new BufferedImage(width, height, original.getType());
/* 463:    */     
/* 464:602 */     AffineTransform atx = new AffineTransform();
/* 465:603 */     switch (direction)
/* 466:    */     {
/* 467:    */     case 0: 
/* 468:605 */       atx.rotate(-1.570796326794897D, width / 2, height / 2);
/* 469:606 */       break;
/* 470:    */     case 1: 
/* 471:608 */       atx.rotate(1.570796326794897D, width / 2, height / 2);
/* 472:    */     }
/* 473:611 */     AffineTransformOp atop = new AffineTransformOp(atx, 3);
/* 474:612 */     atop.filter(original, bufOut);
/* 475:613 */     bufOut = bufOut.getSubimage(0, 0, width, height);
/* 476:    */     try
/* 477:    */     {
/* 478:615 */       ImageIO.write(bufOut, "JPG", new File(sourceImagePath));
/* 479:    */     }
/* 480:    */     catch (IOException e)
/* 481:    */     {
/* 482:617 */       LOGGER.debug(e);
/* 483:    */     }
/* 484:    */   }
/* 485:    */   
/* 486:    */   public static void saveImageAsJpg(String sourceImagePath, String savePath, int width, int hight)
/* 487:    */   {
/* 488:630 */     BufferedImage srcImage = null;
/* 489:631 */     String imgType = "JPEG";
/* 490:632 */     if (sourceImagePath.toLowerCase().endsWith(".png")) {
/* 491:633 */       imgType = "PNG";
/* 492:    */     }
/* 493:635 */     File saveFile = new File(savePath);
/* 494:636 */     File fromFile = new File(sourceImagePath);
/* 495:    */     try
/* 496:    */     {
/* 497:638 */       srcImage = ImageIO.read(fromFile);
/* 498:    */     }
/* 499:    */     catch (IOException e)
/* 500:    */     {
/* 501:640 */       LOGGER.error(e);
/* 502:    */     }
/* 503:642 */     if ((width > 0) || (hight > 0)) {
/* 504:643 */       srcImage = resize(sourceImagePath, width, hight);
/* 505:    */     }
/* 506:    */     try
/* 507:    */     {
/* 508:646 */       ImageIO.write(srcImage, imgType, saveFile);
/* 509:    */     }
/* 510:    */     catch (IOException e)
/* 511:    */     {
/* 512:648 */       LOGGER.error(e);
/* 513:    */     }
/* 514:    */   }
/* 515:    */   
/* 516:    */   public static void scale(String sourceImagePath, String savePath, double scale, boolean flag)
/* 517:    */   {
/* 518:    */     try
/* 519:    */     {
/* 520:666 */       BufferedImage src = ImageIO.read(new File(sourceImagePath));
/* 521:667 */       int width = src.getWidth();
/* 522:668 */       int height = src.getHeight();
/* 523:669 */       if (flag)
/* 524:    */       {
/* 525:671 */         width = (int)(width * scale);
/* 526:672 */         height = (int)(height * scale);
/* 527:    */       }
/* 528:    */       else
/* 529:    */       {
/* 530:675 */         width = (int)(width / scale);
/* 531:676 */         height = (int)(height / scale);
/* 532:    */       }
/* 533:678 */       Image image = src.getScaledInstance(width, height, 1);
/* 534:679 */       BufferedImage tag = new BufferedImage(width, height, 1);
/* 535:680 */       Graphics g = tag.getGraphics();
/* 536:681 */       g.drawImage(image, 0, 0, null);
/* 537:682 */       g.dispose();
/* 538:683 */       ImageIO.write(tag, "JPEG", new File(savePath));
/* 539:    */     }
/* 540:    */     catch (IOException e)
/* 541:    */     {
/* 542:685 */       LOGGER.equals(e);
/* 543:    */     }
/* 544:    */   }
/* 545:    */   
/* 546:    */   public static void scaleHyaline(String sourceImagePath, String savePath, double scale, boolean flag)
/* 547:    */   {
/* 548:702 */     if (isPic(sourceImagePath)) {
/* 549:    */       try
/* 550:    */       {
/* 551:705 */         BufferedImage src = ImageIO.read(new File(sourceImagePath));
/* 552:706 */         BufferedImage dstImage = null;
/* 553:707 */         AffineTransform transform = AffineTransform.getScaleInstance(scale, scale);
/* 554:708 */         AffineTransformOp op = new AffineTransformOp(transform, 2);
/* 555:709 */         dstImage = op.filter(src, null);
/* 556:    */         try
/* 557:    */         {
/* 558:713 */           ImageIO.write(dstImage, "png", new File(savePath));
/* 559:    */         }
/* 560:    */         catch (IOException e)
/* 561:    */         {
/* 562:715 */           e.printStackTrace();
/* 563:    */         }
/* 564:725 */         return;
/* 565:    */       }
/* 566:    */       catch (IOException e)
/* 567:    */       {
/* 568:720 */         LOGGER.equals(e);
/* 569:    */       }
/* 570:    */     }
/* 571:    */   }
/* 572:    */   
/* 573:    */   public static void cut(int x, int y, int width, int height, String sourceImagePath, String savePath)
/* 574:    */   {
/* 575:740 */     FileInputStream is = null;
/* 576:741 */     ImageInputStream iis = null;
/* 577:    */     try
/* 578:    */     {
/* 579:    */       try
/* 580:    */       {
/* 581:746 */         is = new FileInputStream(sourceImagePath);
/* 582:    */       }
/* 583:    */       catch (FileNotFoundException e)
/* 584:    */       {
/* 585:748 */         LOGGER.error("图片未找到:" + sourceImagePath);
/* 586:749 */         LOGGER.error(e);
/* 587:    */       }
/* 588:756 */       Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
/* 589:757 */       ImageReader reader = (ImageReader)it.next();
/* 590:    */       try
/* 591:    */       {
/* 592:761 */         iis = ImageIO.createImageInputStream(is);
/* 593:    */       }
/* 594:    */       catch (IOException e)
/* 595:    */       {
/* 596:763 */         e.printStackTrace();
/* 597:764 */         LOGGER.error("图片未找到:" + sourceImagePath);
/* 598:765 */         LOGGER.error(e);
/* 599:    */       }
/* 600:772 */       reader.setInput(iis, true);
/* 601:    */       
/* 602:    */ 
/* 603:    */ 
/* 604:    */ 
/* 605:    */ 
/* 606:    */ 
/* 607:    */ 
/* 608:    */ 
/* 609:    */ 
/* 610:    */ 
/* 611:    */ 
/* 612:784 */       ImageReadParam param = reader.getDefaultReadParam();
/* 613:    */       try
/* 614:    */       {
/* 615:787 */         reader.read(0);
/* 616:    */       }
/* 617:    */       catch (IOException e)
/* 618:    */       {
/* 619:789 */         LOGGER.error("图片未找到:" + sourceImagePath);
/* 620:790 */         LOGGER.error(e);
/* 621:    */       }
/* 622:797 */       Rectangle rect = new Rectangle(x, y, width, height);
/* 623:    */       
/* 624:    */ 
/* 625:    */ 
/* 626:    */ 
/* 627:    */ 
/* 628:    */ 
/* 629:    */ 
/* 630:    */ 
/* 631:    */ 
/* 632:    */ 
/* 633:    */ 
/* 634:    */ 
/* 635:    */ 
/* 636:    */ 
/* 637:    */ 
/* 638:    */ 
/* 639:    */ 
/* 640:    */ 
/* 641:    */ 
/* 642:    */ 
/* 643:818 */       param.setSourceRegion(rect);
/* 644:    */       
/* 645:    */ 
/* 646:    */ 
/* 647:    */ 
/* 648:    */ 
/* 649:824 */       BufferedImage bi = null;
/* 650:    */       try
/* 651:    */       {
/* 652:826 */         bi = reader.read(0, param);
/* 653:    */       }
/* 654:    */       catch (IOException e)
/* 655:    */       {
/* 656:828 */         LOGGER.error("图片未找到:" + sourceImagePath);
/* 657:829 */         LOGGER.error(e);
/* 658:    */       }
/* 659:    */       try
/* 660:    */       {
/* 661:834 */         ImageIO.write(bi, "jpg", new File(savePath));
/* 662:    */       }
/* 663:    */       catch (IOException e)
/* 664:    */       {
/* 665:836 */         LOGGER.error("输出路径不正确:" + savePath);
/* 666:837 */         LOGGER.error(e);
/* 667:    */       }
/* 668:    */     }
/* 669:    */     finally
/* 670:    */     {
/* 671:844 */       if (is != null) {
/* 672:    */         try
/* 673:    */         {
/* 674:846 */           is.close();
/* 675:    */         }
/* 676:    */         catch (IOException e)
/* 677:    */         {
/* 678:848 */           LOGGER.error(e);
/* 679:    */         }
/* 680:    */       }
/* 681:850 */       if (iis != null) {
/* 682:    */         try
/* 683:    */         {
/* 684:852 */           iis.close();
/* 685:    */         }
/* 686:    */         catch (IOException e)
/* 687:    */         {
/* 688:854 */           LOGGER.error(e);
/* 689:    */         }
/* 690:    */       }
/* 691:    */     }
/* 692:    */   }
/* 693:    */   
/* 694:    */   public static void formatImage(String path, String fix)
/* 695:    */   {
/* 696:869 */     String _path = path;
/* 697:    */     try
/* 698:    */     {
/* 699:871 */       File file = new File(_path);
/* 700:872 */       InputStream is = new FileInputStream(file);
/* 701:873 */       BufferedImage image = ImageIO.read(is);
/* 702:874 */       BufferedImage tag = new BufferedImage(image.getWidth(), image.getHeight(), 1);
/* 703:875 */       tag.getGraphics().drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
/* 704:876 */       FileOutputStream newimage = new FileOutputStream(path.substring(0, path.lastIndexOf(".")) + fix);
/* 705:877 */       JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
/* 706:878 */       encoder.encode(tag);
/* 707:879 */       newimage.close();
/* 708:    */     }
/* 709:    */     catch (Exception ex)
/* 710:    */     {
/* 711:881 */       LOGGER.equals(ex);
/* 712:    */     }
/* 713:    */   }
/* 714:    */   
/* 715:    */   public static void zoom(String sourceImagePath, String savePath, int targetWidth, int targetHeight, boolean more, String exp)
/* 716:    */   {
/* 717:898 */     File file = null;
/* 718:899 */     BufferedImage original = null;
/* 719:900 */     BufferedImage bufOut = null;
/* 720:902 */     if (more)
/* 721:    */     {
/* 722:903 */       file = new File(sourceImagePath);
/* 723:    */       try
/* 724:    */       {
/* 725:905 */         original = ImageIO.read(file);
/* 726:    */       }
/* 727:    */       catch (IOException e)
/* 728:    */       {
/* 729:907 */         LOGGER.debug(e);
/* 730:    */       }
/* 731:909 */       int width = original.getWidth();
/* 732:910 */       int height = original.getHeight();
/* 733:911 */       if ((targetWidth >= width) && (targetHeight >= height))
/* 734:    */       {
/* 735:912 */         targetWidth = width;
/* 736:913 */         targetHeight = height;
/* 737:    */       }
/* 738:915 */       bufOut = new BufferedImage(width, height, original.getType());
/* 739:916 */       double xScale = new Integer(targetWidth).doubleValue() / width;
/* 740:917 */       double yScale = new Integer(targetHeight).doubleValue() / height;
/* 741:918 */       double scale = Math.min(xScale, yScale);
/* 742:919 */       int newWidth = new Double(original.getWidth() * scale).intValue();
/* 743:920 */       int newHeight = new Double(original.getHeight() * scale).intValue();
/* 744:921 */       AffineTransform atx = AffineTransform.getScaleInstance(scale, scale);
/* 745:922 */       AffineTransformOp atop = new AffineTransformOp(atx, 2);
/* 746:923 */       atop.filter(original, bufOut);
/* 747:924 */       bufOut = bufOut.getSubimage(0, 0, newWidth, newHeight);
/* 748:    */       try
/* 749:    */       {
/* 750:926 */         ImageIO.write(bufOut, "JPG", file);
/* 751:    */       }
/* 752:    */       catch (IOException e)
/* 753:    */       {
/* 754:928 */         LOGGER.debug(e);
/* 755:    */       }
/* 756:    */     }
/* 757:    */     else
/* 758:    */     {
/* 759:933 */       if ((sourceImagePath == null) && (sourceImagePath.length() > 0)) {
/* 760:934 */         return;
/* 761:    */       }
/* 762:935 */       String[] filePath = sourceImagePath.split(exp);
/* 763:936 */       if (filePath.length > 0) {
/* 764:937 */         for (int i = 0; i < filePath.length; i++)
/* 765:    */         {
/* 766:939 */           file = new File(savePath);
/* 767:    */           try
/* 768:    */           {
/* 769:941 */             original = ImageIO.read(file);
/* 770:    */           }
/* 771:    */           catch (Exception localException)
/* 772:    */           {
/* 773:943 */             return;
/* 774:    */           }
/* 775:946 */           int width = original.getWidth();
/* 776:947 */           int height = original.getHeight();
/* 777:948 */           if ((targetWidth >= width) && (targetHeight >= height))
/* 778:    */           {
/* 779:949 */             targetWidth = width;
/* 780:950 */             targetHeight = height;
/* 781:    */           }
/* 782:952 */           bufOut = new BufferedImage(width, height, original.getType());
/* 783:953 */           double xScale = new Integer(targetWidth).doubleValue() / width;
/* 784:954 */           double yScale = new Integer(targetHeight).doubleValue() / height;
/* 785:955 */           double scale = Math.min(xScale, yScale);
/* 786:956 */           int newWidth = new Double(original.getWidth() * scale).intValue();
/* 787:957 */           int newHeight = new Double(original.getHeight() * scale).intValue();
/* 788:958 */           AffineTransform atx = AffineTransform.getScaleInstance(scale, scale);
/* 789:959 */           AffineTransformOp atop = new AffineTransformOp(atx, 2);
/* 790:960 */           atop.filter(original, bufOut);
/* 791:961 */           bufOut = bufOut.getSubimage(0, 0, newWidth, newHeight);
/* 792:    */           try
/* 793:    */           {
/* 794:963 */             ImageIO.write(bufOut, "JPG", new File(savePath));
/* 795:    */           }
/* 796:    */           catch (IOException e)
/* 797:    */           {
/* 798:965 */             LOGGER.debug(e);
/* 799:    */           }
/* 800:    */         }
/* 801:    */       }
/* 802:    */     }
/* 803:    */   }
/* 804:    */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-util-1.0.1.jar
 * Qualified Name:     com.mingsoft.util.ImageUtil
 * JD-Core Version:    0.7.0.1
 */
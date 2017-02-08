/*   1:    */ package com.mingsoft.util;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.sql.Timestamp;
/*   5:    */ import java.text.DateFormat;
/*   6:    */ import java.text.ParseException;
/*   7:    */ import java.text.SimpleDateFormat;
/*   8:    */ import java.util.Calendar;
/*   9:    */ 
/*  10:    */ public class DateUtil
/*  11:    */ {
/*  12:    */   private int year;
/*  13:    */   private int month;
/*  14:    */   private int day;
/*  15:    */   private int hour;
/*  16:    */   private int minute;
/*  17:    */   private int second;
/*  18: 75 */   private static final int[] dayArray = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
/*  19: 81 */   public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
/*  20: 87 */   public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");
/*  21: 93 */   public static final SimpleDateFormat DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
/*  22: 99 */   public static final SimpleDateFormat ORA_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
/*  23:105 */   public static final SimpleDateFormat ORA_DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");
/*  24:111 */   public static final SimpleDateFormat ORA_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
/*  25:117 */   public static final SimpleDateFormat CHN_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
/*  26:123 */   public static final SimpleDateFormat CHN_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/*  27:129 */   public static final SimpleDateFormat CHN_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  28:    */   public static final String dtLong = "yyyyMMddHHmmss";
/*  29:    */   public static final String simple = "yyyy-MM-dd HH:mm:ss";
/*  30:    */   public static final String dtShort = "yyyyMMdd";
/*  31:    */   
/*  32:    */   public static String getOrderNum()
/*  33:    */   {
/*  34:146 */     java.util.Date date = new java.util.Date();
/*  35:147 */     DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
/*  36:148 */     return df.format(date);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public DateUtil()
/*  40:    */   {
/*  41:155 */     today();
/*  42:    */   }
/*  43:    */   
/*  44:    */   DateUtil(String inValue)
/*  45:    */   {
/*  46:165 */     SetDate(inValue);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public DateUtil(long mills)
/*  50:    */   {
/*  51:174 */     setTimeInMillis(mills);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public DateUtil(int year, int month, int day, int hour, int minute, int second)
/*  55:    */   {
/*  56:188 */     Calendar calendar = Calendar.getInstance();
/*  57:189 */     calendar.set(year, month - 1, day, hour, minute, second);
/*  58:190 */     this.year = calendar.get(1);
/*  59:191 */     this.month = (calendar.get(2) + 1);
/*  60:192 */     this.day = calendar.get(5);
/*  61:193 */     this.hour = calendar.get(11);
/*  62:194 */     this.minute = calendar.get(12);
/*  63:195 */     this.second = calendar.get(13);
/*  64:    */   }
/*  65:    */   
/*  66:    */   private void SetDate(String inValue)
/*  67:    */   {
/*  68:205 */     if (inValue.length() != 14)
/*  69:    */     {
/*  70:206 */       for (int i = inValue.length(); i < 14; i++) {
/*  71:207 */         inValue = inValue + "0";
/*  72:    */       }
/*  73:209 */       System.out.println(inValue);
/*  74:    */     }
/*  75:    */     try
/*  76:    */     {
/*  77:213 */       int year = Integer.parseInt(inValue.substring(0, 4));
/*  78:214 */       int month = Integer.parseInt(inValue.substring(4, 6));
/*  79:215 */       int day = Integer.parseInt(inValue.substring(6, 8));
/*  80:216 */       int hour = Integer.parseInt(inValue.substring(8, 10));
/*  81:217 */       int minute = Integer.parseInt(inValue.substring(10, 12));
/*  82:218 */       int second = Integer.parseInt(inValue.substring(12));
/*  83:    */       
/*  84:220 */       Calendar calendar = Calendar.getInstance();
/*  85:221 */       calendar.set(year, month - 1, day, hour, minute, second);
/*  86:222 */       this.year = calendar.get(1);
/*  87:223 */       this.month = (calendar.get(2) + 1);
/*  88:224 */       this.day = calendar.get(5);
/*  89:225 */       this.hour = calendar.get(11);
/*  90:226 */       this.minute = calendar.get(12);
/*  91:227 */       this.second = calendar.get(13);
/*  92:    */     }
/*  93:    */     catch (Exception e)
/*  94:    */     {
/*  95:230 */       System.out.println(e.getMessage());
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void today()
/* 100:    */   {
/* 101:238 */     Calendar calendar = Calendar.getInstance();
/* 102:239 */     this.year = calendar.get(1);
/* 103:240 */     this.month = (calendar.get(2) + 1);
/* 104:241 */     this.day = calendar.get(5);
/* 105:242 */     this.hour = calendar.get(11);
/* 106:243 */     this.minute = calendar.get(12);
/* 107:244 */     this.second = calendar.get(13);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String format(SimpleDateFormat DateFormat)
/* 111:    */   {
/* 112:255 */     Calendar calendar = Calendar.getInstance();
/* 113:256 */     calendar.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
/* 114:257 */     return DateFormat.format(calendar.getTime());
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String toString()
/* 118:    */   {
/* 119:265 */     return format(CHN_DATE_TIME_EXTENDED_FORMAT);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public java.util.Date getDate()
/* 123:    */   {
/* 124:274 */     Calendar date = Calendar.getInstance();
/* 125:275 */     date.set(5, getDay());
/* 126:276 */     date.set(2, getMonth() - 1);
/* 127:277 */     date.set(1, getYear());
/* 128:278 */     date.set(11, getHour());
/* 129:279 */     date.set(12, getMinute());
/* 130:280 */     date.set(13, getSecond());
/* 131:281 */     return date.getTime();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public long getTimeInMillis()
/* 135:    */   {
/* 136:291 */     Calendar calendar = Calendar.getInstance();
/* 137:292 */     calendar.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
/* 138:293 */     return calendar.getTime().getTime();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setTimeInMillis(long mills)
/* 142:    */   {
/* 143:302 */     java.util.Date dd = new java.util.Date(mills);
/* 144:303 */     Calendar calendar = Calendar.getInstance();
/* 145:304 */     calendar.setTime(dd);
/* 146:305 */     this.year = calendar.get(1);
/* 147:306 */     this.month = (calendar.get(2) + 1);
/* 148:307 */     this.day = calendar.get(5);
/* 149:308 */     this.hour = calendar.get(11);
/* 150:309 */     this.minute = calendar.get(12);
/* 151:310 */     this.second = calendar.get(13);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean isLeapYear()
/* 155:    */   {
/* 156:320 */     return isLeapYear(this.year);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean isLeapYear(int year)
/* 160:    */   {
/* 161:330 */     if (year % 400 == 0) {
/* 162:331 */       return true;
/* 163:    */     }
/* 164:332 */     if (year % 4 == 0)
/* 165:    */     {
/* 166:333 */       if (year % 100 == 0) {
/* 167:334 */         return false;
/* 168:    */       }
/* 169:336 */       return true;
/* 170:    */     }
/* 171:338 */     return false;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setDateTime(int years, int months, int days, int hours, int minutes, int seconds)
/* 175:    */   {
/* 176:352 */     Calendar calendar = Calendar.getInstance();
/* 177:353 */     calendar.set(this.year + years, this.month - 1 + months, this.day + days, this.hour + hours, this.minute + minutes, this.second + seconds);
/* 178:354 */     setTimeInMillis(calendar.getTime().getTime());
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void addYear(int years)
/* 182:    */   {
/* 183:363 */     if ((this.month == 2) && (this.day == 29))
/* 184:    */     {
/* 185:365 */       if (isLeapYear(this.year + years)) {
/* 186:366 */         setDateTime(years, 0, 0, 0, 0, 0);
/* 187:    */       } else {
/* 188:368 */         setDateTime(years, 0, -1, 0, 0, 0);
/* 189:    */       }
/* 190:    */     }
/* 191:    */     else {
/* 192:370 */       setDateTime(years, 0, 0, 0, 0, 0);
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void addMonth(int months)
/* 197:    */   {
/* 198:379 */     int this_day_end = daysOfMonth();
/* 199:380 */     int that_day_end = dayOfMonth(months);
/* 200:381 */     if (this.day == this_day_end) {
/* 201:382 */       this.day = that_day_end;
/* 202:383 */     } else if (this.day > that_day_end) {
/* 203:384 */       this.day = that_day_end;
/* 204:    */     }
/* 205:386 */     setDateTime(0, months, 0, 0, 0, 0);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void addDay(int days)
/* 209:    */   {
/* 210:395 */     setDateTime(0, 0, days, 0, 0, 0);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void addHour(int hours)
/* 214:    */   {
/* 215:404 */     setDateTime(0, 0, 0, hours, 0, 0);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void addMinute(int minutes)
/* 219:    */   {
/* 220:413 */     setDateTime(0, 0, 0, 0, minutes, 0);
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void addSecond(int seconds)
/* 224:    */   {
/* 225:422 */     setDateTime(0, 0, 0, 0, 0, seconds);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public int daysOfMonth()
/* 229:    */   {
/* 230:431 */     if ((this.month > 12) || (this.month < 0)) {
/* 231:432 */       return 0;
/* 232:    */     }
/* 233:433 */     if ((this.month == 2) && (isLeapYear())) {
/* 234:434 */       return 29;
/* 235:    */     }
/* 236:436 */     return dayArray[(this.month - 1)];
/* 237:    */   }
/* 238:    */   
/* 239:    */   public int dayOfMonth(int monthNumber)
/* 240:    */   {
/* 241:446 */     int yy = monthNumber / 12;
/* 242:447 */     int mm = monthNumber % 12;
/* 243:448 */     int year = this.year + yy;
/* 244:449 */     int month = this.month + mm;
/* 245:451 */     if (month > 12)
/* 246:    */     {
/* 247:452 */       month -= 12;
/* 248:453 */       year++;
/* 249:    */     }
/* 250:455 */     if (month < 1)
/* 251:    */     {
/* 252:456 */       month += 12;
/* 253:457 */       year--;
/* 254:    */     }
/* 255:460 */     if ((month == 2) && (isLeapYear(year))) {
/* 256:461 */       return 29;
/* 257:    */     }
/* 258:463 */     return dayArray[(month - 1)];
/* 259:    */   }
/* 260:    */   
/* 261:    */   public static long diffSec(DateUtil firstDate, DateUtil Lastdate)
/* 262:    */   {
/* 263:474 */     return (firstDate.getTimeInMillis() - Lastdate.getTimeInMillis()) / 1000L;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public static int diffMonth(java.util.Date firstDate, java.util.Date Lastdate)
/* 267:    */   {
/* 268:485 */     if (firstDate.after(Lastdate))
/* 269:    */     {
/* 270:486 */       java.util.Date t = firstDate;
/* 271:487 */       firstDate = Lastdate;
/* 272:488 */       Lastdate = t;
/* 273:    */     }
/* 274:490 */     Calendar startCalendar = Calendar.getInstance();
/* 275:491 */     startCalendar.setTime(firstDate);
/* 276:492 */     Calendar endCalendar = Calendar.getInstance();
/* 277:493 */     endCalendar.setTime(Lastdate);
/* 278:494 */     Calendar temp = Calendar.getInstance();
/* 279:495 */     temp.setTime(Lastdate);
/* 280:496 */     temp.add(5, 1);
/* 281:    */     
/* 282:498 */     int year = endCalendar.get(1) - startCalendar.get(1);
/* 283:499 */     int month = endCalendar.get(2) - startCalendar.get(2);
/* 284:501 */     if ((startCalendar.get(5) == 1) && (temp.get(5) == 1)) {
/* 285:502 */       return year * 12 + month + 1;
/* 286:    */     }
/* 287:503 */     if ((startCalendar.get(5) != 1) && (temp.get(5) == 1)) {
/* 288:504 */       return year * 12 + month;
/* 289:    */     }
/* 290:505 */     if ((startCalendar.get(5) == 1) && (temp.get(5) != 1)) {
/* 291:506 */       return year * 12 + month;
/* 292:    */     }
/* 293:508 */     return year * 12 + month - 1 < 0 ? 0 : year * 12 + month;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public static int diffDay(DateUtil firstDate, DateUtil Lastdate)
/* 297:    */   {
/* 298:520 */     return (int)(firstDate.getTimeInMillis() - Lastdate.getTimeInMillis()) / 1000 / 86400;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public static int diffDay(java.util.Date firstDate, java.util.Date Lastdate)
/* 302:    */   {
/* 303:530 */     firstDate = stringToDate(dateFmtToString(firstDate, "yyyy-MM-dd"), "yyyy-MM-dd");
/* 304:531 */     Lastdate = stringToDate(dateFmtToString(Lastdate, "yyyy-MM-dd"), "yyyy-MM-dd");
/* 305:532 */     int _day = (int)((firstDate.getTime() - Lastdate.getTime()) / 1000L / 86400L);
/* 306:533 */     return _day;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public static int diffDays(Calendar firstDate, Calendar Lastdate)
/* 310:    */   {
/* 311:544 */     if (firstDate.after(Lastdate))
/* 312:    */     {
/* 313:545 */       Calendar swap = firstDate;
/* 314:546 */       firstDate = Lastdate;
/* 315:547 */       Lastdate = swap;
/* 316:    */     }
/* 317:549 */     int days = Lastdate.get(6) - firstDate.get(6);
/* 318:550 */     int y2 = Lastdate.get(1);
/* 319:551 */     if (firstDate.get(1) != y2)
/* 320:    */     {
/* 321:552 */       firstDate = (Calendar)firstDate.clone();
/* 322:    */       do
/* 323:    */       {
/* 324:554 */         days += firstDate.getActualMaximum(6);
/* 325:555 */         firstDate.add(1, 1);
/* 326:556 */       } while (firstDate.get(1) != y2);
/* 327:    */     }
/* 328:558 */     return days;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public static java.util.Date addDays(java.util.Date date, int day)
/* 332:    */   {
/* 333:569 */     if (date == null) {
/* 334:570 */       return null;
/* 335:    */     }
/* 336:571 */     Calendar c = Calendar.getInstance();
/* 337:572 */     c.setTime(date);
/* 338:573 */     c.set(5, c.get(5) + day);
/* 339:574 */     return c.getTime();
/* 340:    */   }
/* 341:    */   
/* 342:    */   public static java.util.Date removeDays(java.util.Date date, int day)
/* 343:    */   {
/* 344:585 */     if (date == null) {
/* 345:586 */       return null;
/* 346:    */     }
/* 347:587 */     Calendar c = Calendar.getInstance();
/* 348:588 */     c.setTime(date);
/* 349:589 */     c.set(5, c.get(5) - day);
/* 350:590 */     return c.getTime();
/* 351:    */   }
/* 352:    */   
/* 353:    */   public static java.util.Date addMonths(java.util.Date date, int month)
/* 354:    */   {
/* 355:601 */     if (date == null) {
/* 356:602 */       return null;
/* 357:    */     }
/* 358:603 */     Calendar c = Calendar.getInstance();
/* 359:604 */     c.setTime(date);
/* 360:605 */     c.set(2, c.get(2) + month);
/* 361:606 */     return c.getTime();
/* 362:    */   }
/* 363:    */   
/* 364:    */   public static java.util.Date removeMonths(java.util.Date date, int month)
/* 365:    */   {
/* 366:617 */     if (date == null) {
/* 367:618 */       return null;
/* 368:    */     }
/* 369:619 */     Calendar c = Calendar.getInstance();
/* 370:620 */     c.setTime(date);
/* 371:621 */     c.set(2, c.get(2) - month);
/* 372:622 */     return c.getTime();
/* 373:    */   }
/* 374:    */   
/* 375:    */   public static String dateFmtToString(java.util.Date date, SimpleDateFormat fmt)
/* 376:    */   {
/* 377:633 */     return fmt.format(date);
/* 378:    */   }
/* 379:    */   
/* 380:    */   public static String dateFmtToString(java.util.Date date)
/* 381:    */   {
/* 382:643 */     return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
/* 383:    */   }
/* 384:    */   
/* 385:    */   public static String dateFmtToString(java.util.Date date, String fmt)
/* 386:    */   {
/* 387:655 */     return new SimpleDateFormat(fmt).format(date);
/* 388:    */   }
/* 389:    */   
/* 390:    */   public static java.util.Date stringToDate(String date)
/* 391:    */   {
/* 392:665 */     return java.sql.Date.valueOf(date);
/* 393:    */   }
/* 394:    */   
/* 395:    */   public static java.util.Date stringToDate(String date, String ftm)
/* 396:    */   {
/* 397:676 */     SimpleDateFormat sdf = new SimpleDateFormat(ftm);
/* 398:    */     try
/* 399:    */     {
/* 400:678 */       return sdf.parse(date);
/* 401:    */     }
/* 402:    */     catch (ParseException e)
/* 403:    */     {
/* 404:681 */       e.printStackTrace();
/* 405:    */     }
/* 406:683 */     return null;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public static java.util.Date stringFmtToDate(String date, String dataFmt)
/* 410:    */   {
/* 411:693 */     SimpleDateFormat df = new SimpleDateFormat(dataFmt);
/* 412:694 */     return java.sql.Date.valueOf(df.format(java.sql.Date.valueOf(date)));
/* 413:    */   }
/* 414:    */   
/* 415:    */   public static Timestamp dateToTimestamp(java.util.Date date)
/* 416:    */   {
/* 417:704 */     String temp = CHN_DATE_TIME_EXTENDED_FORMAT.format(date);
/* 418:705 */     return Timestamp.valueOf(temp);
/* 419:    */   }
/* 420:    */   
/* 421:    */   public int getDay()
/* 422:    */   {
/* 423:714 */     return this.day;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void setDay(int day)
/* 427:    */   {
/* 428:723 */     this.day = day;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public int getHour()
/* 432:    */   {
/* 433:732 */     return this.hour;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void setHour(int hour)
/* 437:    */   {
/* 438:741 */     this.hour = hour;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public int getMinute()
/* 442:    */   {
/* 443:750 */     return this.minute;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void setMinute(int minute)
/* 447:    */   {
/* 448:759 */     this.minute = minute;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public int getMonth()
/* 452:    */   {
/* 453:768 */     return this.month;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void setMonth(int month)
/* 457:    */   {
/* 458:777 */     this.month = month;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public int getSecond()
/* 462:    */   {
/* 463:786 */     return this.second;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void setSecond(int second)
/* 467:    */   {
/* 468:795 */     this.second = second;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public int getYear()
/* 472:    */   {
/* 473:804 */     return this.year;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void setYear(int year)
/* 477:    */   {
/* 478:813 */     this.year = year;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public boolean hasCommon(java.util.Date start1, java.util.Date end1, java.util.Date start2, java.util.Date end2)
/* 482:    */   {
/* 483:825 */     if ((end1.before(start2)) || (end2.before(start1))) {
/* 484:826 */       return false;
/* 485:    */     }
/* 486:828 */     return true;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public static boolean judgeDateMsg(String date)
/* 490:    */   {
/* 491:837 */     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
/* 492:    */     try
/* 493:    */     {
/* 494:839 */       java.sql.Date.valueOf(df.format(java.sql.Date.valueOf(date)));
/* 495:    */     }
/* 496:    */     catch (NumberFormatException localNumberFormatException)
/* 497:    */     {
/* 498:841 */       return false;
/* 499:    */     }
/* 500:843 */     return true;
/* 501:    */   }
/* 502:    */   
/* 503:    */   public static int daysBetween(String beginDate, String endDate)
/* 504:    */   {
/* 505:853 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/* 506:854 */     Calendar cal = Calendar.getInstance();
/* 507:855 */     Calendar cal2 = Calendar.getInstance();
/* 508:    */     try
/* 509:    */     {
/* 510:857 */       cal.setTime(sdf.parse(beginDate));
/* 511:858 */       cal2.setTime(sdf.parse(endDate));
/* 512:859 */       long time1 = cal.getTimeInMillis();
/* 513:860 */       long time2 = cal2.getTimeInMillis();
/* 514:861 */       long between_days = (time2 - time1) / 86400000L;
/* 515:862 */       return (int)between_days;
/* 516:    */     }
/* 517:    */     catch (ParseException e)
/* 518:    */     {
/* 519:865 */       e.printStackTrace();
/* 520:    */     }
/* 521:867 */     return 1;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public static int secondBetween(String date)
/* 525:    */   {
/* 526:877 */     SimpleDateFormat sdfSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 527:    */     try
/* 528:    */     {
/* 529:879 */       long presentDate = sdfSecond.parse(sdfSecond.format(new java.util.Date())).getTime();
/* 530:880 */       long enterDate = sdfSecond.parse(date).getTime();
/* 531:881 */       return (int)((presentDate - enterDate) / 1000L);
/* 532:    */     }
/* 533:    */     catch (ParseException e)
/* 534:    */     {
/* 535:884 */       e.printStackTrace();
/* 536:    */     }
/* 537:886 */     return 1;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public static int secondBetween(java.util.Date date)
/* 541:    */   {
/* 542:896 */     if (date == null) {
/* 543:897 */       return 0;
/* 544:    */     }
/* 545:899 */     SimpleDateFormat sdfSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 546:    */     try
/* 547:    */     {
/* 548:901 */       long presentDate = sdfSecond.parse(sdfSecond.format(new java.util.Date())).getTime();
/* 549:902 */       long enterDate = date.getTime();
/* 550:903 */       return (int)((presentDate - enterDate) / 1000L);
/* 551:    */     }
/* 552:    */     catch (ParseException e)
/* 553:    */     {
/* 554:906 */       e.printStackTrace();
/* 555:    */     }
/* 556:908 */     return 1;
/* 557:    */   }
/* 558:    */   
/* 559:    */   public static String pastTime(java.util.Date date)
/* 560:    */   {
/* 561:917 */     int second = secondBetween(date);
/* 562:918 */     if (second < 60) {
/* 563:919 */       return second + "秒前";
/* 564:    */     }
/* 565:920 */     if ((second > 60) && (second < 1800)) {
/* 566:921 */       return second / 60 + "分钟前";
/* 567:    */     }
/* 568:922 */     if ((second > 1800) && (second < 3600)) {
/* 569:923 */       return "半小时前";
/* 570:    */     }
/* 571:924 */     if ((second > 3600) && (second < 86400)) {
/* 572:925 */       return second / 60 / 60 + "小时前";
/* 573:    */     }
/* 574:927 */     return dateFmtToString(date, "yyyy-MM-dd HH:mm:ss");
/* 575:    */   }
/* 576:    */   
/* 577:    */   public static int secondBetween(String beginDate, String endDate)
/* 578:    */   {
/* 579:941 */     SimpleDateFormat sdfSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 580:    */     try
/* 581:    */     {
/* 582:943 */       long _enginDate = sdfSecond.parse(beginDate).getTime();
/* 583:944 */       long _endDate = sdfSecond.parse(endDate).getTime();
/* 584:945 */       return (int)((_enginDate - _endDate) / 1000L);
/* 585:    */     }
/* 586:    */     catch (ParseException e)
/* 587:    */     {
/* 588:948 */       e.printStackTrace();
/* 589:    */     }
/* 590:950 */     return 1;
/* 591:    */   }
/* 592:    */   
/* 593:    */   public static java.util.Date[] beginEndStringToDate(String date, String split, String fmt)
/* 594:    */   {
/* 595:961 */     if ((StringUtil.isBlank(date)) || (StringUtil.isBlank(split))) {
/* 596:962 */       return null;
/* 597:    */     }
/* 598:964 */     String[] _date = date.split(split);
/* 599:965 */     if (_date.length == 2)
/* 600:    */     {
/* 601:966 */       java.util.Date[] d = new java.util.Date[2];
/* 602:967 */       d[0] = stringFmtToDate(_date[0], fmt);
/* 603:968 */       d[1] = stringFmtToDate(_date[1], fmt);
/* 604:969 */       return d;
/* 605:    */     }
/* 606:971 */     return null;
/* 607:    */   }
/* 608:    */   
/* 609:    */   public static Calendar date2Calendar(java.util.Date date)
/* 610:    */   {
/* 611:980 */     Calendar calendar = Calendar.getInstance();
/* 612:981 */     calendar.setTime(date);
/* 613:982 */     return calendar;
/* 614:    */   }
/* 615:    */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-util-1.0.1.jar
 * Qualified Name:     com.mingsoft.util.DateUtil
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package com.mingsoft.util;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ 
/*   5:    */ @Deprecated
/*   6:    */ public class PageUtil
/*   7:    */ {
/*   8:    */   protected int index;
/*   9:    */   protected String indexUrl;
/*  10:    */   protected int previous;
/*  11:    */   protected String previousUrl;
/*  12:    */   protected int next;
/*  13:    */   protected String nextUrl;
/*  14:    */   protected int last;
/*  15:    */   protected String lastUrl;
/*  16:    */   protected int recordCount;
/*  17:    */   protected String linkUrl;
/*  18:    */   protected int pageNo;
/*  19:    */   protected int pageCount;
/*  20:    */   
/*  21:    */   public void setPageNo(int pageNo)
/*  22:    */   {
/*  23: 95 */     this.pageNo = pageNo;
/*  24:    */   }
/*  25:    */   
/*  26:106 */   protected int pageSize = 10;
/*  27:    */   private boolean hasParams;
/*  28:    */   
/*  29:    */   public int getPageCount()
/*  30:    */   {
/*  31:119 */     return this.pageCount;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setPageCount(int pageCount)
/*  35:    */   {
/*  36:127 */     this.pageCount = pageCount;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public PageUtil(int pageSize)
/*  40:    */   {
/*  41:135 */     this.pageSize = pageSize;
/*  42:136 */     this.pageNo = 0;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public PageUtil(int pageNo, int recordCount, String linkUrl)
/*  46:    */   {
/*  47:149 */     this.pageNo = (pageNo - 1);
/*  48:150 */     this.recordCount = recordCount;
/*  49:151 */     this.linkUrl = (linkUrl == null ? "" : linkUrl);
/*  50:    */     
/*  51:153 */     calculatePageCount();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public PageUtil(int pageNo, int recordCount)
/*  55:    */   {
/*  56:166 */     this.pageNo = (pageNo - 1);
/*  57:167 */     this.recordCount = recordCount;
/*  58:168 */     calculatePageCount();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public PageUtil(int pageNo, int pageSize, int recordCount, String linkUrl)
/*  62:    */   {
/*  63:184 */     this(pageNo, recordCount, linkUrl);
/*  64:185 */     this.pageSize = pageSize;
/*  65:186 */     calculatePageCount();
/*  66:188 */     if ((this.pageNo + 1 > this.pageCount) && (this.pageCount > 1)) {
/*  67:189 */       this.pageNo = (this.pageCount - 1);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   private void calculatePageCount()
/*  72:    */   {
/*  73:198 */     if (this.linkUrl.indexOf("?") > 0) {
/*  74:199 */       this.hasParams = true;
/*  75:    */     }
/*  76:202 */     if (this.recordCount == 0) {
/*  77:203 */       this.pageCount = 0;
/*  78:206 */     } else if (this.recordCount % this.pageSize == 0) {
/*  79:207 */       this.pageCount = (this.recordCount / this.pageSize);
/*  80:    */     } else {
/*  81:209 */       this.pageCount = (this.recordCount / this.pageSize + 1);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIndex()
/*  86:    */   {
/*  87:220 */     return this.index;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIndex(int index)
/*  91:    */   {
/*  92:228 */     this.index = index;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getPrevious()
/*  96:    */   {
/*  97:236 */     return this.previous;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setPrevious(int previous)
/* 101:    */   {
/* 102:244 */     this.previous = previous;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getNext()
/* 106:    */   {
/* 107:252 */     return this.next;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setNext(int next)
/* 111:    */   {
/* 112:260 */     this.next = next;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int getLast()
/* 116:    */   {
/* 117:268 */     return this.last;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setLast(int last)
/* 121:    */   {
/* 122:277 */     this.last = last;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int getRecordCound()
/* 126:    */   {
/* 127:285 */     return this.recordCount;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getPageNo()
/* 131:    */   {
/* 132:293 */     return this.pageNo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getIndexUrl()
/* 136:    */   {
/* 137:301 */     if (this.pageNo == 0) {
/* 138:302 */       this.indexUrl = "#";
/* 139:    */     } else {
/* 140:304 */       this.indexUrl = (this.linkUrl + (this.hasParams ? "&" : "?") + "pageNo=1");
/* 141:    */     }
/* 142:306 */     return this.indexUrl;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getPreviousUrl()
/* 146:    */   {
/* 147:314 */     if (this.pageNo == 0) {
/* 148:315 */       this.previousUrl = "#";
/* 149:    */     } else {
/* 150:317 */       this.previousUrl = (this.linkUrl + (this.hasParams ? "&" : "?") + "pageNo=" + this.pageNo);
/* 151:    */     }
/* 152:319 */     return this.previousUrl;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String getNextUrl()
/* 156:    */   {
/* 157:327 */     if (this.pageNo == this.pageCount) {
/* 158:328 */       this.nextUrl = "#";
/* 159:330 */     } else if (this.pageNo + 2 > this.pageCount) {
/* 160:331 */       this.nextUrl = (this.linkUrl + (this.hasParams ? "&" : "?") + "pageNo=" + this.pageCount);
/* 161:    */     } else {
/* 162:333 */       this.nextUrl = (this.linkUrl + (this.hasParams ? "&" : "?") + "pageNo=" + (this.pageNo + 2));
/* 163:    */     }
/* 164:336 */     return this.nextUrl;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String getLastUrl()
/* 168:    */   {
/* 169:346 */     if (this.pageNo == this.pageCount) {
/* 170:347 */       this.lastUrl = "#";
/* 171:    */     } else {
/* 172:349 */       this.lastUrl = (this.linkUrl + (this.hasParams ? "&" : "?") + "pageNo=" + this.pageCount);
/* 173:    */     }
/* 174:351 */     return this.lastUrl;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int getPageSize()
/* 178:    */   {
/* 179:359 */     return this.pageSize;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String getLinkUrl()
/* 183:    */   {
/* 184:367 */     return this.linkUrl + (this.hasParams ? "&" : "?");
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setLinkUrl(String linkUrl)
/* 188:    */   {
/* 189:376 */     this.linkUrl = linkUrl;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static void main(String[] args)
/* 193:    */   {
/* 194:384 */     String url = "http://localhost/mswx/admin/hotel/listHotel.do?1=1";
/* 195:385 */     System.out.println(url.indexOf("?"));
/* 196:    */   }
/* 197:    */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-util-1.0.1.jar
 * Qualified Name:     com.mingsoft.util.PageUtil
 * JD-Core Version:    0.7.0.1
 */
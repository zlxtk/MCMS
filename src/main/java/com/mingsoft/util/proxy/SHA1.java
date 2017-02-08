/*   1:    */ package com.mingsoft.util.proxy;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ 
/*   5:    */ public class SHA1
/*   6:    */ {
/*   7: 58 */   private int[] tmpData = new int[80];
/*   8: 58 */   private int[] digestInt = new int[5];
/*   9: 58 */   private final int[] abcde = { 1732584193, -271733879, -1732584194, 271733878, -1009589776 };
/*  10:    */   
/*  11:    */   private int process_input_bytes(byte[] bytedata)
/*  12:    */   {
/*  13: 68 */     System.arraycopy(this.abcde, 0, this.digestInt, 0, this.abcde.length);
/*  14:    */     
/*  15: 70 */     byte[] newbyte = byteArrayFormatData(bytedata);
/*  16:    */     
/*  17: 72 */     int MCount = newbyte.length / 64;
/*  18: 74 */     for (int pos = 0; pos < MCount; pos++)
/*  19:    */     {
/*  20: 76 */       for (int j = 0; j < 16; j++) {
/*  21: 77 */         this.tmpData[j] = byteArrayToInt(newbyte, pos * 64 + j * 4);
/*  22:    */       }
/*  23: 80 */       encrypt();
/*  24:    */     }
/*  25: 82 */     return 20;
/*  26:    */   }
/*  27:    */   
/*  28:    */   private byte[] byteArrayFormatData(byte[] bytedata)
/*  29:    */   {
/*  30: 88 */     int zeros = 0;
/*  31:    */     
/*  32: 90 */     int size = 0;
/*  33:    */     
/*  34: 92 */     int n = bytedata.length;
/*  35:    */     
/*  36: 94 */     int m = n % 64;
/*  37: 96 */     if (m < 56)
/*  38:    */     {
/*  39: 97 */       zeros = 55 - m;
/*  40: 98 */       size = n - m + 64;
/*  41:    */     }
/*  42: 99 */     else if (m == 56)
/*  43:    */     {
/*  44:100 */       zeros = 63;
/*  45:101 */       size = n + 8 + 64;
/*  46:    */     }
/*  47:    */     else
/*  48:    */     {
/*  49:103 */       zeros = 63 - m + 56;
/*  50:104 */       size = n + 64 - m + 64;
/*  51:    */     }
/*  52:107 */     byte[] newbyte = new byte[size];
/*  53:    */     
/*  54:109 */     System.arraycopy(bytedata, 0, newbyte, 0, n);
/*  55:    */     
/*  56:111 */     int l = n;
/*  57:    */     
/*  58:113 */     newbyte[(l++)] = -128;
/*  59:115 */     for (int i = 0; i < zeros; i++) {
/*  60:116 */       newbyte[(l++)] = 0;
/*  61:    */     }
/*  62:119 */     long N = n * 8L;
/*  63:120 */     byte h8 = (byte)(int)(N & 0xFF);
/*  64:121 */     byte h7 = (byte)(int)(N >> 8 & 0xFF);
/*  65:122 */     byte h6 = (byte)(int)(N >> 16 & 0xFF);
/*  66:123 */     byte h5 = (byte)(int)(N >> 24 & 0xFF);
/*  67:124 */     byte h4 = (byte)(int)(N >> 32 & 0xFF);
/*  68:125 */     byte h3 = (byte)(int)(N >> 40 & 0xFF);
/*  69:126 */     byte h2 = (byte)(int)(N >> 48 & 0xFF);
/*  70:127 */     byte h1 = (byte)(int)(N >> 56);
/*  71:128 */     newbyte[(l++)] = h1;
/*  72:129 */     newbyte[(l++)] = h2;
/*  73:130 */     newbyte[(l++)] = h3;
/*  74:131 */     newbyte[(l++)] = h4;
/*  75:132 */     newbyte[(l++)] = h5;
/*  76:133 */     newbyte[(l++)] = h6;
/*  77:134 */     newbyte[(l++)] = h7;
/*  78:135 */     newbyte[(l++)] = h8;
/*  79:136 */     return newbyte;
/*  80:    */   }
/*  81:    */   
/*  82:    */   private int f1(int x, int y, int z)
/*  83:    */   {
/*  84:140 */     return x & y | (x ^ 0xFFFFFFFF) & z;
/*  85:    */   }
/*  86:    */   
/*  87:    */   private int f2(int x, int y, int z)
/*  88:    */   {
/*  89:144 */     return x ^ y ^ z;
/*  90:    */   }
/*  91:    */   
/*  92:    */   private int f3(int x, int y, int z)
/*  93:    */   {
/*  94:148 */     return x & y | x & z | y & z;
/*  95:    */   }
/*  96:    */   
/*  97:    */   private int f4(int x, int y)
/*  98:    */   {
/*  99:152 */     return x << y | x >>> 32 - y;
/* 100:    */   }
/* 101:    */   
/* 102:    */   private void encrypt()
/* 103:    */   {
/* 104:157 */     for (int i = 16; i <= 79; i++) {
/* 105:158 */       this.tmpData[i] = f4(this.tmpData[(i - 3)] ^ this.tmpData[(i - 8)] ^ this.tmpData[(i - 14)] ^ 
/* 106:159 */         this.tmpData[(i - 16)], 1);
/* 107:    */     }
/* 108:161 */     int[] tmpabcde = new int[5];
/* 109:162 */     for (int i1 = 0; i1 < tmpabcde.length; i1++) {
/* 110:163 */       tmpabcde[i1] = this.digestInt[i1];
/* 111:    */     }
/* 112:165 */     for (int j = 0; j <= 19; j++)
/* 113:    */     {
/* 114:166 */       int tmp = f4(tmpabcde[0], 5) + 
/* 115:167 */         f1(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] + 
/* 116:168 */         this.tmpData[j] + 1518500249;
/* 117:169 */       tmpabcde[4] = tmpabcde[3];
/* 118:170 */       tmpabcde[3] = tmpabcde[2];
/* 119:171 */       tmpabcde[2] = f4(tmpabcde[1], 30);
/* 120:172 */       tmpabcde[1] = tmpabcde[0];
/* 121:173 */       tmpabcde[0] = tmp;
/* 122:    */     }
/* 123:175 */     for (int k = 20; k <= 39; k++)
/* 124:    */     {
/* 125:176 */       int tmp = f4(tmpabcde[0], 5) + 
/* 126:177 */         f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] + 
/* 127:178 */         this.tmpData[k] + 1859775393;
/* 128:179 */       tmpabcde[4] = tmpabcde[3];
/* 129:180 */       tmpabcde[3] = tmpabcde[2];
/* 130:181 */       tmpabcde[2] = f4(tmpabcde[1], 30);
/* 131:182 */       tmpabcde[1] = tmpabcde[0];
/* 132:183 */       tmpabcde[0] = tmp;
/* 133:    */     }
/* 134:185 */     for (int l = 40; l <= 59; l++)
/* 135:    */     {
/* 136:186 */       int tmp = f4(tmpabcde[0], 5) + 
/* 137:187 */         f3(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] + 
/* 138:188 */         this.tmpData[l] + -1894007588;
/* 139:189 */       tmpabcde[4] = tmpabcde[3];
/* 140:190 */       tmpabcde[3] = tmpabcde[2];
/* 141:191 */       tmpabcde[2] = f4(tmpabcde[1], 30);
/* 142:192 */       tmpabcde[1] = tmpabcde[0];
/* 143:193 */       tmpabcde[0] = tmp;
/* 144:    */     }
/* 145:195 */     for (int m = 60; m <= 79; m++)
/* 146:    */     {
/* 147:196 */       int tmp = f4(tmpabcde[0], 5) + 
/* 148:197 */         f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] + 
/* 149:198 */         this.tmpData[m] + -899497514;
/* 150:199 */       tmpabcde[4] = tmpabcde[3];
/* 151:200 */       tmpabcde[3] = tmpabcde[2];
/* 152:201 */       tmpabcde[2] = f4(tmpabcde[1], 30);
/* 153:202 */       tmpabcde[1] = tmpabcde[0];
/* 154:203 */       tmpabcde[0] = tmp;
/* 155:    */     }
/* 156:205 */     for (int i2 = 0; i2 < tmpabcde.length; i2++) {
/* 157:206 */       this.digestInt[i2] += tmpabcde[i2];
/* 158:    */     }
/* 159:208 */     for (int n = 0; n < this.tmpData.length; n++) {
/* 160:209 */       this.tmpData[n] = 0;
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   private int byteArrayToInt(byte[] bytedata, int i)
/* 165:    */   {
/* 166:215 */     return (bytedata[i] & 0xFF) << 24 | (bytedata[(i + 1)] & 0xFF) << 16 | 
/* 167:216 */       (bytedata[(i + 2)] & 0xFF) << 8 | bytedata[(i + 3)] & 0xFF;
/* 168:    */   }
/* 169:    */   
/* 170:    */   private void intToByteArray(int intValue, byte[] byteData, int i)
/* 171:    */   {
/* 172:221 */     byteData[i] = ((byte)(intValue >>> 24));
/* 173:222 */     byteData[(i + 1)] = ((byte)(intValue >>> 16));
/* 174:223 */     byteData[(i + 2)] = ((byte)(intValue >>> 8));
/* 175:224 */     byteData[(i + 3)] = ((byte)intValue);
/* 176:    */   }
/* 177:    */   
/* 178:    */   private static String byteToHexString(byte ib)
/* 179:    */   {
/* 180:229 */     char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 
/* 181:230 */       'B', 'C', 'D', 'E', 'F' };
/* 182:231 */     char[] ob = new char[2];
/* 183:232 */     ob[0] = Digit[(ib >>> 4 & 0xF)];
/* 184:233 */     ob[1] = Digit[(ib & 0xF)];
/* 185:234 */     String s = new String(ob);
/* 186:235 */     return s;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private static String byteArrayToHexString(byte[] bytearray)
/* 190:    */   {
/* 191:240 */     String strDigest = "";
/* 192:241 */     for (int i = 0; i < bytearray.length; i++) {
/* 193:242 */       strDigest = strDigest + byteToHexString(bytearray[i]);
/* 194:    */     }
/* 195:244 */     return strDigest;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public byte[] getDigestOfBytes(byte[] byteData)
/* 199:    */   {
/* 200:249 */     process_input_bytes(byteData);
/* 201:250 */     byte[] digest = new byte[20];
/* 202:251 */     for (int i = 0; i < this.digestInt.length; i++) {
/* 203:252 */       intToByteArray(this.digestInt[i], digest, i * 4);
/* 204:    */     }
/* 205:254 */     return digest;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String getDigestOfString(byte[] byteData)
/* 209:    */   {
/* 210:259 */     return byteArrayToHexString(getDigestOfBytes(byteData));
/* 211:    */   }
/* 212:    */   
/* 213:    */   public static void main(String[] args)
/* 214:    */   {
/* 215:263 */     String data = "123456";
/* 216:264 */     System.out.println(data);
/* 217:265 */     String digest = new SHA1().getDigestOfString(data.getBytes());
/* 218:266 */     System.out.println(digest);
/* 219:    */   }
/* 220:    */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-proxy-1.0.0.jar
 * Qualified Name:     com.mingsoft.util.proxy.SHA1
 * JD-Core Version:    0.7.0.1
 */
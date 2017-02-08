/*   1:    */ package net.mingsoft.upgrade.action.client;
/*   2:    */ 
/*   3:    */ import com.alibaba.druid.pool.DruidDataSource;
/*   4:    */ import com.mingsoft.basic.action.BasicAction;
/*   5:    */ import com.mingsoft.util.StringUtil;
/*   6:    */ import com.mingsoft.util.proxy.Proxy;
/*   7:    */ import com.mingsoft.util.proxy.Result;
/*   8:    */ import java.io.File;
/*   9:    */ import java.io.FileNotFoundException;
/*  10:    */ import java.io.FileOutputStream;
/*  11:    */ import java.io.FileReader;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.io.InputStream;
/*  14:    */ import java.io.OutputStream;
/*  15:    */ import java.io.UnsupportedEncodingException;
/*  16:    */ import java.sql.SQLException;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Enumeration;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.Iterator;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import java.util.Set;
/*  24:    */ import java.util.zip.ZipEntry;
/*  25:    */ import java.util.zip.ZipException;
/*  26:    */ import java.util.zip.ZipFile;
/*  27:    */ import javax.servlet.http.HttpServletRequest;
/*  28:    */ import javax.servlet.http.HttpServletResponse;
/*  29:    */ import org.apache.http.HttpEntity;
/*  30:    */ import org.apache.http.HttpResponse;
/*  31:    */ import org.apache.http.client.ClientProtocolException;
/*  32:    */ import org.apache.http.client.entity.UrlEncodedFormEntity;
/*  33:    */ import org.apache.http.client.methods.HttpPost;
/*  34:    */ import org.apache.http.impl.client.CloseableHttpClient;
/*  35:    */ import org.apache.http.impl.client.HttpClients;
/*  36:    */ import org.apache.http.message.BasicNameValuePair;
/*  37:    */ import org.apache.ibatis.jdbc.ScriptRunner;
/*  38:    */ import org.springframework.stereotype.Controller;
/*  39:    */ import org.springframework.web.bind.annotation.RequestMapping;
/*  40:    */ import org.springframework.web.bind.annotation.ResponseBody;
/*  41:    */ 
/*  42:    */ @Controller("upgrader")
/*  43:    */ @RequestMapping({"/${managerPath}/upgrader"})
/*  44:    */ public class UpgraderAction
/*  45:    */   extends BasicAction
/*  46:    */ {
/*  47:    */   private com.mingsoft.util.proxy.Header header;
/*  48:    */   private static final String MS_MSTORE_HOST = "http://ms.mingsoft.net/";
/*  49:    */   private static final String UPDATE_PATH_TEM = "upgrader/";
/*  50:    */   private String ck;
/*  51:    */   private static final String UTF8 = "UTF-8";
/*  52:    */   
/*  53:    */   public UpgraderAction()
/*  54:    */   {
/*  55: 87 */     this.header = new com.mingsoft.util.proxy.Header("mingsoft.net", "UTF-8");
/*  56:    */     
/*  57: 89 */     this.header.setHeader("ms", "upgrader");
/*  58: 90 */     this.header.setHeader("ver", "4.6.0");
/*  59:    */   }
/*  60:    */   
/*  61:    */   @ResponseBody
/*  62:    */   @RequestMapping(value={"/sync"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*  63:    */   public void sync(HttpServletRequest request, HttpServletResponse response)
/*  64:    */   {
/*  65:103 */     this.header.setHeader("method", "sync");
/*  66:104 */     Map<String, Object> params = new HashMap();
/*  67:    */     
/*  68:106 */     params = assemblyRequestMap(request);
/*  69:107 */     params.put("userHost", getUrl(request));
/*  70:    */     
/*  71:109 */     Result result = Proxy.post("http://ms.mingsoft.net//ms/sync.do", this.header, assemblyRequestMap(request), "UTF-8");
/*  72:    */     
/*  73:111 */     String content = result.getContent();
/*  74:112 */     if (!StringUtil.isBlank(content)) {
/*  75:113 */       outJson(response, null, true, content);
/*  76:    */     } else {
/*  77:115 */       outJson(response, null, false);
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   @ResponseBody
/*  82:    */   @RequestMapping({"/setup"})
/*  83:    */   public void setup(HttpServletRequest request)
/*  84:    */     throws ClientProtocolException, IOException
/*  85:    */   {
/*  86:129 */     this.header.setHeader("method", "setup");
/*  87:130 */     this.header.setCookie(request.getParameter("user"));
/*  88:131 */     Map<String, Object> params = new HashMap();
/*  89:    */     
/*  90:133 */     params = assemblyRequestMap(request);
/*  91:134 */     params.put("userUrl", getUrl(request));
/*  92:    */     
/*  93:    */ 
/*  94:137 */     CloseableHttpClient httpclient = HttpClients.createDefault();
/*  95:138 */     HttpPost httpPost = new HttpPost("http://ms.mingsoft.net//people/upgrader/upgraderPeopleVersion/down.do");
/*  96:141 */     if (params != null)
/*  97:    */     {
/*  98:142 */       List list = new ArrayList();
/*  99:    */       
/* 100:144 */       Iterator it = params.keySet().iterator();
/* 101:145 */       while (it.hasNext())
/* 102:    */       {
/* 103:146 */         String temp = (String)it.next();
/* 104:147 */         list.add(new BasicNameValuePair(temp, (String)params.get(temp)));
/* 105:    */       }
/* 106:    */       try
/* 107:    */       {
/* 108:151 */         httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
/* 109:    */       }
/* 110:    */       catch (UnsupportedEncodingException e)
/* 111:    */       {
/* 112:153 */         e.printStackTrace();
/* 113:    */       }
/* 114:    */     }
/* 115:157 */     if ((this.header != null) && (this.header.getHeaders().size() > 0)) {
/* 116:158 */       httpPost.setHeaders(Proxy.assemblyHeader(this.header.getHeaders()));
/* 117:    */     }
/* 118:162 */     HttpResponse response = httpclient.execute(httpPost);
/* 119:    */     
/* 120:164 */     HttpEntity entity = response.getEntity();
/* 121:165 */     String destFileName = response.getHeaders("fileName")[0].getValue();
/* 122:166 */     InputStream in = entity.getContent();
/* 123:167 */     File file = new File(getRealPath(request, "upgrader//" + destFileName));
/* 124:    */     try
/* 125:    */     {
/* 126:169 */       FileOutputStream fout = new FileOutputStream(file);
/* 127:170 */       int l = -1;
/* 128:171 */       byte[] tmp = new byte[1024];
/* 129:172 */       while ((l = in.read(tmp)) != -1) {
/* 130:173 */         fout.write(tmp, 0, l);
/* 131:    */       }
/* 132:176 */       fout.flush();
/* 133:177 */       fout.close();
/* 134:    */     }
/* 135:    */     finally
/* 136:    */     {
/* 137:180 */       in.close();
/* 138:    */     }
/* 139:182 */     httpclient.close();
/* 140:    */     
/* 141:184 */     String sqlFile = "";
/* 142:185 */     String entryName = "";
/* 143:    */     try
/* 144:    */     {
/* 145:188 */       ZipFile zipFile = new ZipFile(file);
/* 146:    */       
/* 147:    */ 
/* 148:191 */       File unzipFile = new File(getRealPath(request, "/"));
/* 149:    */       
/* 150:193 */       Enumeration<? extends ZipEntry> zipEnum = zipFile.entries();
/* 151:196 */       while (zipEnum.hasMoreElements())
/* 152:    */       {
/* 153:198 */         ZipEntry entry = (ZipEntry)zipEnum.nextElement();
/* 154:    */         try
/* 155:    */         {
/* 156:200 */           entryName = new String(entry.getName().getBytes("utf-8"));
/* 157:    */         }
/* 158:    */         catch (UnsupportedEncodingException e)
/* 159:    */         {
/* 160:203 */           e.printStackTrace();
/* 161:    */         }
/* 162:206 */         if (entry.isDirectory()) {
/* 163:207 */           new File(unzipFile.getAbsolutePath() + File.separator + entryName).mkdirs();
/* 164:    */         } else {
/* 165:    */           try
/* 166:    */           {
/* 167:211 */             InputStream input = zipFile.getInputStream(entry);
/* 168:212 */             OutputStream output = new FileOutputStream(
/* 169:213 */               new File(unzipFile.getAbsolutePath() + File.separator + entryName));
/* 170:215 */             if (entryName.indexOf(".sql") > 0) {
/* 171:216 */               sqlFile = unzipFile.getAbsolutePath() + File.separator + entryName;
/* 172:    */             }
/* 173:218 */             byte[] buffer = new byte[8192];
/* 174:219 */             int readLen = 0;
/* 175:220 */             while ((readLen = input.read(buffer, 0, 8192)) != -1) {
/* 176:221 */               output.write(buffer, 0, readLen);
/* 177:    */             }
/* 178:223 */             output.flush();
/* 179:224 */             output.close();
/* 180:225 */             input.close();
/* 181:226 */             input = null;
/* 182:227 */             output = null;
/* 183:    */           }
/* 184:    */           catch (IOException e)
/* 185:    */           {
/* 186:230 */             e.printStackTrace();
/* 187:    */           }
/* 188:    */         }
/* 189:    */       }
/* 190:    */     }
/* 191:    */     catch (ZipException e1)
/* 192:    */     {
/* 193:238 */       e1.printStackTrace();
/* 194:    */     }
/* 195:    */     catch (IOException e1)
/* 196:    */     {
/* 197:241 */       e1.printStackTrace();
/* 198:    */     }
/* 199:    */     try
/* 200:    */     {
/* 201:245 */       DruidDataSource dds = (DruidDataSource)getBean(request.getServletContext(), "dataSource");
/* 202:246 */       ScriptRunner runner = new ScriptRunner(dds.getConnection());
/* 203:247 */       runner.setErrorLogWriter(null);
/* 204:248 */       runner.setLogWriter(null);
/* 205:249 */       runner.runScript(new FileReader(new File(sqlFile)));
/* 206:    */     }
/* 207:    */     catch (FileNotFoundException e)
/* 208:    */     {
/* 209:252 */       e.printStackTrace();
/* 210:    */     }
/* 211:    */     catch (SQLException e)
/* 212:    */     {
/* 213:254 */       e.printStackTrace();
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   @RequestMapping({"/r"})
/* 218:    */   public void r(HttpServletRequest request, HttpServletResponse response)
/* 219:    */   {
/* 220:265 */     String url = request.getParameter("url");
/* 221:266 */     this.ck = request.getParameter("ck");
/* 222:    */     
/* 223:268 */     this.header.setCookie(this.ck);
/* 224:269 */     Map<String, String> params = new HashMap();
/* 225:    */     
/* 226:271 */     String userUrl = getUrl(request);
/* 227:272 */     params.put("userUrl", userUrl);
/* 228:    */     
/* 229:274 */     Result result = Proxy.post(url, this.header, params, "UTF-8");
/* 230:    */     
/* 231:276 */     String content = result.getContent();
/* 232:277 */     if (!StringUtil.isBlank(content)) {
/* 233:278 */       outString(response, content);
/* 234:    */     }
/* 235:    */   }
/* 236:    */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-upgrader-1.0.2-SNAPSHOT.jar
 * Qualified Name:     net.mingsoft.upgrade.action.client.UpgraderAction
 * JD-Core Version:    0.7.0.1
 */
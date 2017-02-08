package com.mingsoft.util.proxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Proxy {
	public static Logger log = LoggerFactory.getLogger(Proxy.class);

	public static Result get(String url, Header header, Map params) {
		DefaultHttpClient client = new DefaultHttpClient();

		url = url + (params == null ? "" : assemblyParameter(params));

		HttpGet httpGet = new HttpGet(url);

		httpGet.getParams().setParameter("http.protocol.cookie-policy", "compatibility");
		if ((header != null) && (header.getHeaders().size() > 0)) {
			httpGet.setHeaders(assemblyHeader(header.getHeaders()));
		}
		try {
			HttpResponse response = client.execute(httpGet);

			HttpEntity entity = response.getEntity();

			Result result = new Result();

			result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));

			result.setStatusCode(response.getStatusLine().getStatusCode());

			result.setHeaders(response.getAllHeaders());

			result.setHttpEntity(entity);
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	public static void get(String url, String path) {
		try {
			HttpClient hc = HttpClientBuilder.create().build();
			HttpGet gm = new HttpGet(url);
			HttpResponse response = hc.execute(gm);
			InputStream is = response.getEntity().getContent();
			OutputStream os = new FileOutputStream(path);
			int c = -1;
			while ((c = is.read()) != -1) {
				os.write(c);
			}
			is.close();
			os.flush();
			os.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	public static Result post(String url, Header header, Map params, String encoding) {
		DefaultHttpClient client = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(url);

		httpPost.getParams().setParameter("http.protocol.cookie-policy", "compatibility");
		if (params != null) {
			List list = new ArrayList();

			Iterator it = params.keySet().iterator();
			while (it.hasNext()) {
				String temp = (String) it.next();
				list.add(new BasicNameValuePair(temp,  (String) params.get(temp)));
			}
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(list, encoding));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		if ((header != null) && (header.getHeaders().size() > 0)) {
			httpPost.setHeaders(assemblyHeader(header.getHeaders()));
		}
		HttpResponse response = null;
		try {
			response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();

			Result result = new Result();

			result.setStatusCode(response.getStatusLine().getStatusCode());

			result.setHeaders(response.getAllHeaders());

			result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));

			result.setHttpEntity(entity);
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	public static void getRandCode(String url, Header header, String fileUrl) {
		DefaultHttpClient client = new DefaultHttpClient();

		HttpGet get = new HttpGet(url);

		get.getParams().setParameter("http.protocol.cookie-policy", "compatibility");
		Map _headers = header.getHeaders();
		if ((header != null) && (_headers.size() > 0)) {
			get.setHeaders(assemblyHeader(_headers));
		}
		try {
			HttpResponse response = client.execute(get);

			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();

			header.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
			int temp = 0;

			File file = new File(fileUrl);

			FileOutputStream out = new FileOutputStream(file);
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}
			in.close();
			out.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	public static BasicHeader[] assemblyHeader(Map headers) {
		BasicHeader[] allHeader = new BasicHeader[headers.size()];
		int i = 0;

		Iterator it = headers.keySet().iterator();
		while (it.hasNext()) {
			String str = (String) it.next();
			allHeader[i] = new BasicHeader(str, (String) headers.get(str));
			i++;
		}
		return allHeader;
	}

	public static String assemblyCookie(List cookies) {
		StringBuffer sbu = new StringBuffer();
		for (int i = 0; i < cookies.size(); i++) {
			Cookie cookie = (Cookie) cookies.get(i);
			sbu.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
		}
		if (sbu.length() > 0) {
			sbu.deleteCharAt(sbu.length() - 1);
		}
		return sbu.toString();
	}

	public static String assemblyParameter(Map parameters) {
		String para = "?";

		Iterator it = parameters.keySet().iterator();
		while (it.hasNext()) {
			String str = (String) it.next();
			para = para + str + "=" + parameters.get(str) + "&";
		}
		return para.substring(0, para.length() - 1);
	}
}

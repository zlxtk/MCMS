package com.mingsoft.util.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

public class Result {
	private String cookie;
	private int statusCode;
	private HashMap headerAll;
	private HttpEntity httpEntity;

	public String getCookie() {
		return this.cookie;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public HashMap getHeaders() {
		return this.headerAll;
	}

	public void setHeaders(Header[] headers) {
		this.headerAll = new HashMap();
		for (int i = 0; i < headers.length; i++) {
			Header header = headers[i];
			this.headerAll.put(header.getName(), header.getValue());
		}
	}

	public HttpEntity getHttpEntity() {
		return this.httpEntity;
	}

	public void setHttpEntity(HttpEntity httpEntity) {
		this.httpEntity = httpEntity;
	}

	public String getContent(String charset) {
		try {
			return EntityUtils.toString(this.httpEntity, charset);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getContent() {
		try {
			return EntityUtils.toString(this.httpEntity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getContentForGzip(String charset) {
		if (this.httpEntity.getContentEncoding().getValue().indexOf("gzip") > -1) {
			try {
				GZIPInputStream gzipis = new GZIPInputStream(this.httpEntity.getContent());

				InputStreamReader isr = new InputStreamReader(gzipis, charset);
				BufferedReader br = new BufferedReader(isr);

				StringBuffer sb = new StringBuffer();
				String tempbf;
				while ((tempbf = br.readLine()) != null) {
					sb.append(tempbf);
					sb.append("\r\n");
				}
				gzipis.close();
				isr.close();
				return sb.toString();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public void setStatusCode(int statusCode2) {
		this.statusCode = statusCode2;
	}
}

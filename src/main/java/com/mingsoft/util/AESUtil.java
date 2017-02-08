package com.mingsoft.util;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;

public class AESUtil {
	protected static final Logger LOG = Logger.getLogger(AESUtil.class.getClass());

	public static String decrypt(String decryptStr, String strKey) {
		try {
			if (strKey == null) {
				LOG.debug("Key为空null");
				return null;
			}
			if (strKey.length() != 16) {
				LOG.debug("Key长度不是16位");
				return null;
			}
			byte[] raw = strKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(2, skeySpec);
			byte[] encrypted1 = hex2byte(decryptStr);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				return new String(original);
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return null;
	}

	public static String encrypt(String encryptStr, String strKey) {
		if (strKey == null) {
			LOG.debug("Key为空null");
			return null;
		}
		if (strKey.length() != 16) {
			LOG.debug("Key长度不是16位");
			return null;
		}
		byte[] encrypted = null;
		try {
			byte[] raw = strKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(1, skeySpec);
			encrypted = cipher.doFinal(encryptStr.getBytes());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return byte2hex(encrypted).toLowerCase();
	}

	public static byte[] hex2byte(String str) {
		if (str == null) {
			return null;
		}
		int l = str.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] bytes = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			bytes[i] = ((byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16));
		}
		return bytes;
	}

	public static String byte2hex(byte[] bytes) {
		if (bytes == null) {
			return "";
		}
		String hs = "";
		String stmp = "";
		for (int n = 0; n < bytes.length; n++) {
			stmp = Integer.toHexString(bytes[n] & 0xFF);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
}

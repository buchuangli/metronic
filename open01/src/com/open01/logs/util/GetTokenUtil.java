package com.open01.logs.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GetTokenUtil {

	/**
	 * Token工具类
	 * @param user_id, user_name
	 * @author jeremy
	 * @return
	 */
	private static final String encryModel = "MD5";
	/**
	 * 32λmd5. 32位小写md5加密
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		return encrypt(encryModel, str);
	}

	public static String encrypt(String algorithm, String str) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(str.getBytes());
			StringBuffer sb = new StringBuffer();
			byte[] bytes = md.digest();
			for (int i = 0; i < bytes.length; i++) {
				int b = bytes[i] & 0xFF;
				if (b < 0x10) {
					sb.append('0');
				}
				sb.append(Integer.toHexString(b));
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}
	/**
	 * 
	 * @Description : 身份验证token值算法： 算法是：将特定的某几个参数一map的数据结构传入，  进行字典序排序以后进行md5加密,32位小写加密；
	 * @Method_Name : authentication
	 * @param token 请求传过来的token
	 * @param srcData 约定用来计算token的参数
	 * @return
	 */
	public static String authentication(Map<String, Object> srcData) throws Exception  {
		
		List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(srcData.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
			// 升序排序
			public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});

		StringBuffer srcSb = new StringBuffer();
		for (Map.Entry<String, Object> srcAtom : list) {
			srcSb.append(String.valueOf(srcAtom.getValue()));
		}
		// 计算token
		String token = GetTokenUtil.md5(srcSb.toString());
		return token;
	}
	
	public static String getToken(String user_id, String user_name){
		
		Date date = new Date();
		Long date_now = date.getTime();
		Map<String, Object> srcData = new HashMap<String,Object>();
		srcData.put("user_id", user_id);
		srcData.put("user_name", user_name);
		srcData.put("date_now", date_now);
		
		String token = "";
		try {
			token = GetTokenUtil.authentication(srcData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return token.toUpperCase();
	}
	
	public static void main(String[] args){
		System.out.println(GetTokenUtil.getToken("1", "user"));
	}
}
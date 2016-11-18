package com.open01.logs.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtil {

	/*public static void main(String[] args) {
		String s = "http://qweqwebaidu.com/index.open";  
		String ss = "/index.open";  
	//	getUrl(s);
		System.out.println(getUrl(s));
		System.out.println(getUrl(ss));
		
	}*/
	
	public static String getUrl(String url){

		Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(url);  
		if(m.find()){  
			String str = m.group();
			url= url.replace(str, "").replace("http://", "").replace("www.", "");
		}
		 return url;
	}
	
	public  static List<String> removeDuplicates(List<String> list) {  
		   
	    List<String> tempList= new ArrayList<String>();  
	    for(String i:list){  
	        if(!tempList.contains(i)){  
	            tempList.add(i);  
	        }  
	    } 
	    return tempList;
	}
}

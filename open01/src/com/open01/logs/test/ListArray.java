package com.open01.logs.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListArray {
public static void main(String[] args) {
	String str1 = "aaa";
	String str2 = "bbb";
	String str3 = "ccc";
	List<String> list = new ArrayList<String>();
	list.add(str1);
	list.add(str2);
	list.add(str3);
	String[] arr = list.toArray(new String[0]);
	 Arrays.toString(arr);
}
}
package com.open01.logs.test;

import java.math.BigDecimal;

public class Byteconversion {
	public static String bytes2kb(long bytes) {  
        BigDecimal filesize = new BigDecimal(bytes);  
        BigDecimal megabyte = new BigDecimal(1024 * 1024);  
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)  
                .floatValue();  
            return (returnValue + "M");  
    }  

    public static void main(String[] args) {
        String m = bytes2kb(12033);
        System.out.println(m);
    }
}

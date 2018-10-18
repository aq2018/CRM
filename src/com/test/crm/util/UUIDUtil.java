package com.test.crm.util;

import java.util.UUID;

public class UUIDUtil {
	public static void main(String[] args) {
		System.out.println(get());
	}
	private UUIDUtil(){}
	public static String getUuid(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	public static String get(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
}

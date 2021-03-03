package com.playd.vue.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConvertMap {
	
	/*
	 * 작성자 : 남민우
	 * 작성일자 : 2020.02.24
	 * 설명 : 객체 -> Map, Map->객체로 변환
	 * 
	 * */

	// Object to Map
	public static Map<String, Object> ConverObjectToMap(Object obj) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			Field[] fields = obj.getClass().getDeclaredFields();

			for (Field field : fields) {

				field.setAccessible(true);

				resultMap.put(field.getName(), field.get(obj));

			}

			return resultMap;

		} catch (IllegalArgumentException e) {

			e.printStackTrace();

		} catch (IllegalAccessException e) {

			e.printStackTrace();

		}

		return resultMap;

	}

	// Map to Object
	public static Object convertMapToObject(Map<String, Object> map, Object objClass) {
		
		String keyAttribute = null;
		String setMethodString = "set";
		String methodString = null;
		
		Iterator<String> itr = map.keySet().iterator();
		
		while (itr.hasNext()) {
			
			keyAttribute = (String) itr.next();
			
			methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
			
			try {
				
				Method[] methods = objClass.getClass().getDeclaredMethods();
				
				for (Method method : methods) {
					
					if (methodString.equals(method.getName())) {
						
						System.out.println("invoke : " + methodString);
						
						method.invoke(objClass, map.get(keyAttribute));
						
					}
				}
			} catch (SecurityException e) {
				
				e.printStackTrace();
				
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
				
			} catch (IllegalArgumentException e) {
				
				e.printStackTrace();
				
			} catch (InvocationTargetException e) {
				
				e.printStackTrace();
			}
		}
		return objClass;
	}

}

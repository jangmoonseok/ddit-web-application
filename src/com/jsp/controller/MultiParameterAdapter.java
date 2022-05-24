package com.jsp.controller;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

public class MultiParameterAdapter {
	public static <T>T execute(MultipartHttpServletRequestParser multi, Class<T> className) throws Exception {
		//의존성 확인 및 조힙
		Method[] methods = className.getMethods(); // 파라미터를 담을 클래스의 메서드들을 뜯어서 Method객체로 만든다.
		
		//인스턴스 생성
		T obj = className.newInstance(); // 파라미터 타입이 어떤 타입이든지 모두 담을수 있는 새로운 인스턴스를 만든다.
		
		//setter 확인
		for(Method method : methods) {
			if(method.getName().indexOf("set") == 0) {
				// method가 set메서드인지 검사 후 set을 때고 앞글자를 소문자로 바꾼다.
				String requestParamName = method.getName().replace("set", "");
				requestParamName = (requestParamName.charAt(0) + "").toLowerCase() + requestParamName.substring(1);
				
				// 바꾼 파라미터명으로 request에서 다 꺼낸다. 예) setId -> id -> request.getParameterValues("id")
				String[] paramValues = multi.getParameterValues(requestParamName);
				
				if(paramValues != null && paramValues.length > 0) {
					if(method.getParameterTypes()[0].isArray()) {
						//꺼낸 파라미터가 배열이면 새로운 인스턴스에 배열로 넣는다.
						method.invoke(obj, new Object[] {paramValues});
					}else {
						//배열이 아니면 0번째 인덱스를 꺼내서 넣는다.
						method.invoke(obj, paramValues[0]);
					}
				}
			}
		}
		
		return obj;
	}
}

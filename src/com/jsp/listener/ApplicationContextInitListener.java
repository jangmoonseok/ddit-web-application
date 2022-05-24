package com.jsp.listener;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jsp.context.ApplicationContext;


public class ApplicationContextInitListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent ctxEvent)  { 
         // TODO Auto-generated method stub
    }

	
    public void contextInitialized(ServletContextEvent ctxEvent)  { 
    	ServletContext ctx = ctxEvent.getServletContext();
    	
    	String beanConfigXml = ctx.getInitParameter("contextConfigLocation");
    	
    	if(beanConfigXml == null) return;
    	
    	beanConfigXml = ctx.getRealPath("/") + beanConfigXml.replace("classpath:", "WEB-INF/classes/").replace("/", File.separator);
    
    	try {
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.parse(beanConfigXml);
			
			Element root = document.getDocumentElement();

			if(root == null || !root.getTagName().equals("beans")) return; 
			
			NodeList beans = root.getElementsByTagName("bean");
			Map<String, Object> applicationContext = ApplicationContext.getApplicationContext();
			
			//applicationContext setting
			for(int i = 0; i < beans.getLength(); i++) {
				Node bean = beans.item(i);
				if(bean.getNodeType() == Node.ELEMENT_NODE) {
					Element ele = (Element)bean;
					String id = ele.getAttribute("id");
					String classType = ele.getAttribute("class");
					
					Class<?> cls = Class.forName(classType);
					Object targetObj = cls.newInstance();
					applicationContext.put(id, targetObj);
					
					//System.out.println("id:" + id + ", class:" + targetObj);
				}
			}
			
			//의존주입
			for(int i = 0; i < beans.getLength(); i++) {
				Node bean = beans.item(i);
				if(bean.getNodeType() == Node.ELEMENT_NODE) {
					Element eleBean = (Element)bean;
					
					NodeList properties = bean.getChildNodes();
					for(int j = 0; j < properties.getLength(); j++) {
						//ChildNode가 있는 bean들만 for문 실행
						Node property = properties.item(j);
						// 해당 bean의 ChildNode의 이름이 property인지 검사
						if(!property.getNodeName().equals("property")) continue;
						
						if(property.getNodeType() == Node.ELEMENT_NODE) {
							//property이면 ElementNode인지 검사하고 name과 ref-value를 꺼냄
							Element ele = (Element)property;
							String name = ele.getAttribute("name");
							String ref = ele.getAttribute("ref-value");
							
							//꺼낸 name으로 setMethodName 만들기
							String setMethodName = "set" + name.substring(0,1).toUpperCase() + name.substring(1);
							
							//ChildNode를 꺼낸 해당  bean의 class를 꺼내 classType을 지정
							String className = eleBean.getAttribute("class");
							Class<?> classType = Class.forName(className);
							
							//classType 내부 구조를 분석해 methods를 꺼냄
							Method[] methods = classType.getMethods();
							if(methods != null) for(Method method : methods) {
								//의존성 여부 확인
								//꺼낸 method의 이름이 우리가 만든 setMethodName과 일치하면 invoke해서 의존주입
								if(method.getName().equals(setMethodName)) {
									method.invoke(applicationContext.get(eleBean.getAttribute("id")),
											applicationContext.get(ref));
									
									System.out.println("[invoke]" + applicationContext.get(eleBean.getAttribute("id"))
											+ ":" + applicationContext.get(ref));
								}
							}
						}
					}
				}
			}
			
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
}

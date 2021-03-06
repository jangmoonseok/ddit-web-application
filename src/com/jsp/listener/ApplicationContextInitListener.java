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
			
			//????????????
			for(int i = 0; i < beans.getLength(); i++) {
				Node bean = beans.item(i);
				if(bean.getNodeType() == Node.ELEMENT_NODE) {
					Element eleBean = (Element)bean;
					
					NodeList properties = bean.getChildNodes();
					for(int j = 0; j < properties.getLength(); j++) {
						//ChildNode??? ?????? bean?????? for??? ??????
						Node property = properties.item(j);
						// ?????? bean??? ChildNode??? ????????? property?????? ??????
						if(!property.getNodeName().equals("property")) continue;
						
						if(property.getNodeType() == Node.ELEMENT_NODE) {
							//property?????? ElementNode?????? ???????????? name??? ref-value??? ??????
							Element ele = (Element)property;
							String name = ele.getAttribute("name");
							String ref = ele.getAttribute("ref-value");
							
							//?????? name?????? setMethodName ?????????
							String setMethodName = "set" + name.substring(0,1).toUpperCase() + name.substring(1);
							
							//ChildNode??? ?????? ??????  bean??? class??? ?????? classType??? ??????
							String className = eleBean.getAttribute("class");
							Class<?> classType = Class.forName(className);
							
							//classType ?????? ????????? ????????? methods??? ??????
							Method[] methods = classType.getMethods();
							if(methods != null) for(Method method : methods) {
								//????????? ?????? ??????
								//?????? method??? ????????? ????????? ?????? setMethodName??? ???????????? invoke?????? ????????????
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

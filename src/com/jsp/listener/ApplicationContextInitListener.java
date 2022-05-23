package com.jsp.listener;

import java.io.File;
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
			
			for(int i = 0; i < beans.getLength(); i++) {
				Node bean = beans.item(i);
				if(bean.getNodeType() == Node.ELEMENT_NODE) {
					Element ele = (Element)bean;
					String id = ele.getAttribute("id");
					String classType = ele.getAttribute("class");
					
					Class<?> cls = Class.forName(classType);
					Object targetObj = cls.newInstance();
					applicationContext.put(id, classType);
					
					
				}
			}
			
			
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
}

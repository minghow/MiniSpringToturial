package com.minis;

import com.minis.test.Test1;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassPathXmlApplicationContext {

    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private Map<String, Object> singletions = new HashMap<>();

    //构造器获取外部配置，解析出Bean的定义，形成内存映像
    public ClassPathXmlApplicationContext(String fileName){

        this.readXml(fileName);
        this.instanceBeans();

    }

    private void readXml(String fileName){

        SAXReader saxReader = new SAXReader();
        try {

            System.out.println("fileName:"+fileName);
            //Object objA = this.getClass();
            //ClassLoader loadA = this.getClass().getClassLoader();

            //System.out.println(ClassPathXmlApplicationContext.class.getResource("beans.xml"));
            //System.out.println(ClassPathXmlApplicationContext.class.getResource("/beans.xml"));
            System.out.println(ClassPathXmlApplicationContext.class.getResource(""));
            System.out.println(ClassPathXmlApplicationContext.class.getClassLoader().getResource(""));
            //System.out.println(ClassPathXmlApplicationContext.class.getClassLoader().getResource("beans.xml"));
            //System.out.println(ClassPathXmlApplicationContext.class.getClassLoader().getResource("/beans.xml"));

            URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
            System.out.println("xmlPath"+xmlPath.toString());


            //URL xmlPath = this.getClass().getResource(fileName);
            //System.out.println("xmlPath"+xmlPath.toString());

            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();

            // 对配置文件中的每一个<bean>,进行处理
            for(Element element: (List<Element>) rootElement.elements()){

                //获取Bean的基本信息
                String beanID = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");

                BeanDefinition beanDefinition = new BeanDefinition(beanID,beanClassName);

                //将Bean的定义存放到beanDefinitions
                beanDefinitions.add(beanDefinition);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    //利用反射创建Bean实例，并存储在singletons中
    private void instanceBeans(){

        for(BeanDefinition beanDefinition: beanDefinitions){

            try{
                singletions.put(beanDefinition.getId(),
                        Class.forName(beanDefinition.getClassName()).getDeclaredConstructor().newInstance());


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
    }

    public Object getBean(String beanName){

        return singletions.get(beanName);

    }


}

package com.minis.test;

import com.minis.ClassPathXmlApplicationContext;

public class Test1 {

    public static void main(String[] args){

        String dir = System.getProperty("user.dir");
        System.out.println("User dir:"+dir);

        //System.out.println(Test1.class.getResource("beans.xml"));
        //System.out.println(Test1.class.getResource("/beans.xml"));
        System.out.println(Test1.class.getClassLoader().getResource(""));
        //System.out.println(Test1.class.getClassLoader().getResource("beans.xml"));
        //System.out.println(Test1.class.getClassLoader().getResource("/beans.xml"));

        //ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("C:\\Users\\minghow\\IdeaProjects\\MiniSpringToturial\\beans.xml");
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

        AService aService = (AService) ctx.getBean("aservice");
        aService.sayHello();

    }
}

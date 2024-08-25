package cn.jseok.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContainer {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        //CommonAnnotationBeanPostProcessor 注册
        TestLog testLog = context.getBean("testLog",TestLog.class);
        testLog.write();
        System.out.println("初始化容器");
    }

}

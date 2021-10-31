package com.zzb;

import com.zzb.test.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

import java.io.FileWriter;
import java.io.IOException;

@RunWith(JUnit4.class)
public class StringTest2 {
    @Test
    public void test(){
        ClassPathResource res=new ClassPathResource("spring-beans.xml");
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader=new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(res);
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);
    }

}

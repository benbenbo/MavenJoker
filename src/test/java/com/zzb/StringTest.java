package com.zzb;

import com.zzb.configuration.TestConfiguration;
import com.zzb.test.Info;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RunWith(JUnit4.class)
public class StringTest {
    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        Info info1 = context.getBean("info1", Info.class);
        System.out.println(info1);
        context.close();
    }
}

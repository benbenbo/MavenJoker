package com.zzb.configuration;

import com.zzb.test.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author XBird
 * @date 2021/10/10
 **/
@Configuration
public class TestConfiguration {
    @Bean(value={"info1","info2","info3"},initMethod = "init",destroyMethod = "destory")
    public Info info(){
        System.out.println(">>>>运行开始1");
        Info xh = new Info(1, "xh");
        System.out.println(">>>>运行结束1");
        return xh;
    }
}

package com.zzb;

import com.zzb.model.Address;
import com.zzb.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@RunWith(JUnit4.class)
public class StringTest3 {
    @Test
    public void test(){
        Address address = new Address("广东省","佛山市","南海区");
        User user = new User("郑倩盈","1523",address);
        User user2 = null;
        try {
            System.out.println(getUser(user));
            System.out.println(getUser(user2));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(getUserNew(user));
            System.out.println(getUserNew(user2));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dosomething(User user){
        System.out.println(user.getName());
    }

    public User getUser(User user) throws Exception{
        if(user!=null){
            String name = user.getName();
            if("zhangsan".equals(name)){
                return user;
            }else{
                user = new User();
                user.setName("zhangsan");
                return user;
            }
        }else{
            user = new User();
            user.setName("zhangsan");
            return user;
        }
    }

    public User getUserNew(User user) throws Exception{
        return Optional.ofNullable(user).filter(e->"zhangsan".equals(e.getName())).orElseGet(()->{
            User user2 = new User();
            user2.setName("zhangsan");
            return user2;
        });
    }
}

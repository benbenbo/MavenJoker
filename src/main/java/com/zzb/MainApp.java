package com.zzb;


import com.zzb.test.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MainApp {
    private static int num = 0;

    public static void main(String[] args) {
        User user = new User(1, "wowo");
        User user2 = null;
        List<User> userList= Arrays.asList(user,user2);
        Function<User, String> getName = User::getName;
        userList.forEach(e->{
            String s=Optional.ofNullable(e).map(User::getName).orElse("default");
            System.out.println(s);
        });

    }
}

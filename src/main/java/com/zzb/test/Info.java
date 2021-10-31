package com.zzb.test;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author XBird
 * @date 2021/10/10
 **/
@Data
@AllArgsConstructor
public class Info {
    private Integer val;
    private String name;

    public void init(){
        System.out.println(">>>>运行初始化");
        this.val=55;
        this.name="运行初始化";
    }


    public void destory(){
        System.out.println(">>>>运行销毁");
    }
}

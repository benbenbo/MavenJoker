package com.zzb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XBird
 * @date 2021/10/16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String phone;
    private Address address;
}

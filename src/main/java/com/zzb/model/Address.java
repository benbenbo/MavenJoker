package com.zzb.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author XBird
 * @date 2021/10/16
 **/
@Data
@AllArgsConstructor
public class Address {
    private String province;
    private String city;
    private String area;
}

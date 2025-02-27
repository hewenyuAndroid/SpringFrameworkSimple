package com.example.springmvc_helloworld.pojo;

import lombok.Data;

/**
 * 接收级联的数据
 * <p>
 * username=11&password=000&cellphone=
 * &address.province=zhejiang&address.city=hangzhou&address.area=xihu
 * &sex=男
 * &hobby=足球&hobby=篮球
 * &grade=一年级
 * &agreement=on
 */
@Data
public class PersonVO2 {

    private String username;
    private String password;
    private String cellphone;
    private boolean agreement;

    /** 使用对象接收级联数据 */
    private Address address;
    /** 使用数组接收同一个key的数据 */
    private String[] hobby;
    private String grade;

}

@Data
class Address {
    private String province;
    private String city;
    private String area;
}

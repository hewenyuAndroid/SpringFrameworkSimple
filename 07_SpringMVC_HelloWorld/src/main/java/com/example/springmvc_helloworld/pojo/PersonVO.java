package com.example.springmvc_helloworld.pojo;

import lombok.Data;

/**
 * 使用 pojo 对象接收表单数据
 * 需要保证表单key和pojo的属性保持一致
 */
@Data
public class PersonVO {

    private String username;
    private String password;
    private String cellphone;
    private boolean agreement;

}

package com.example.springmvc_helloworld.controller;

import com.example.springmvc_helloworld.pojo.PersonVO;
import com.example.springmvc_helloworld.pojo.PersonVO2;
import org.springframework.web.bind.annotation.*;

//@Controller
//@ResponseBody
// 使用 @RestController 等效于 @Controller + @ResponseBody
@RestController
public class RequestTestController {

    /**
     * 使用普通变量接收请求参数
     * <p>
     * 请求: http://localhost:8080/handle01?username=zhangsan&password=123456&cellphone=12345678&agreement=on
     * 输出: RequestTestController: handle01(), username: zhangsan, password: 123456, cellphone: 12345678, agreement: true
     * <p>
     * 请求参数: username=zhangsan&password=123456&cellphone=12345678&agreement=on
     * <p>
     * 变量名和参数名称一致时:
     * 1. 可以直接使用方法参数接收请求参数
     * 2. 如果请求参数中没有携带对应参数，此时包装类型的参数值为 null，基础类型的参数值为默认值
     */
    @GetMapping("/handle01")
    public String handle01(
            String username,
            String password,
            String cellphone,
            Boolean agreement
    ) {
        System.out.println("RequestTestController: handle01(), " +
                "username: " + username + ", password: " + password +
                ", cellphone: " + cellphone + ", agreement: " + agreement
        );
        return "handle01";
    }


    /**
     * 使用 @RequestParam 注解，接收请求参数
     * <p>
     * 请求: http://localhost:8080/handle02?username=zhangsan&password=123456&cellphone=12345678&agreement=on
     * 输出: RequestTestController: handle02(), username: zhangsan, password: 123456, cellphone: 12345678, agreement: true
     * <p>
     * 变量名和请求参数名称不一致时，使用 @RequestParam("paramName") 设置请求参数的名称
     * 默认情况下 @RequestParam() 注解的请求参数必须要携带
     * - 通过 required 参数配置是否必须携带
     * - 通过 defaultValue 参数配置默认值，此时参数可以不携带
     */
    @GetMapping("/handle02")
    public String handle02(
            @RequestParam("username") String name,
            @RequestParam(value = "password", defaultValue = "123456") String pwd,
            @RequestParam("cellphone") String phone,
            @RequestParam(value = "agreement", required = false) Boolean flag
    ) {
        System.out.println("RequestTestController: handle02(), " +
                "username: " + name + ", password: " + pwd +
                ", cellphone: " + phone + ", agreement: " + flag
        );
        return "handle02";
    }

    /**
     * post 请求
     * url: http://localhost:8080/handle03
     * 请求体: username=zhangsan&password=112233&cellphone=&agreement=on
     * 输出: RequestTestController: handle03(), username: zhangsan, password: 112233, cellphone: , agreement: true
     * <p>
     * post请求中也可以使用 @RequestParam 参数接收表单数据
     */
//    @PostMapping("/handle03")
    public String handle03_back(
            @RequestParam("username") String name,
            @RequestParam(value = "password", defaultValue = "123456") String pwd,
            @RequestParam("cellphone") String phone,
            @RequestParam(value = "agreement", required = false) Boolean flag) {

        System.out.println("RequestTestController: handle03(), " +
                "username: " + name + ", password: " + pwd +
                ", cellphone: " + phone + ", agreement: " + flag
        );
        return "handle03";
    }

    /**
     * 使用 pojo 封装请求参数
     * <p>
     * url: http://localhost:8080/handle03
     * 请求体: username=zhangsan&password=112233&cellphone=&agreement=on
     * 输出: RequestTestController: handle03(), personVO: PersonVO(username=zhangsan, password=33456, cellphone=1112233, agreement=true)
     * <p>
     * post请求可以使用pojo封装表单数据，需要保证表单key和pojo的属性名保持一致
     */
    @PostMapping("/handle03")
    public String handle03(PersonVO personVO) {
        System.out.println("RequestTestController: handle03(), personVO: " + personVO);
        return "handle03";
    }

    /**
     * 使用 @RequestHeader 获取请求头信息
     * <p>
     * 请求: http://localhost:8080/handle04
     * 输出: RequestTestController: handle04(), host: localhost:8080, ua: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36
     */
    @GetMapping("/handle04")
    public String handle04(
            @RequestHeader("host") String host,
            @RequestHeader("user-agent") String ua
    ) {
        System.out.println("RequestTestController: handle04(), host: " + host + ", ua: " + ua);
        return "handle04";
    }

    /**
     * 使用 @CookieValue 获取 cookie 值，对应参数为cookie的key
     * <p>
     * 请求: http://localhost:8080/handle05
     * 输出: RequestTestController: handle05(), token: zxxcvxcvxc
     */
    @GetMapping("/handle05")
    public String handle05(@CookieValue(value = "token", defaultValue = "emptyCookie") String token) {
        System.out.println("RequestTestController: handle05(), token: " + token);
        return "handle05, token=" + token;
    }

    /**
     * 使用对象接收级联数据
     * <p>
     * 请求: http://localhost:8080/handle06
     * 输出: RequestTestController: handle06(), personV02: PersonVO2(username=zhangsan, password=111223, cellphone=3333, agreement=true, address=Address(province=zhejiang, city=hangzhou, area=xihu), hobby=[足球], grade=一年级)
     */
    @GetMapping("/handle06")
    public String handle06(PersonVO2 personVO2) {
        System.out.println("RequestTestController: handle06(), personV02: " + personVO2);
        return "handle06";
    }

}

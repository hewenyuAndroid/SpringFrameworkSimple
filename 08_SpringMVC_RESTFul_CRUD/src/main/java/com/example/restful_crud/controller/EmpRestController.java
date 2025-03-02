package com.example.restful_crud.controller;

import com.example.restful_crud.commen.R;
import com.example.restful_crud.pojo.Emp;
import com.example.restful_crud.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin    // 允许跨域访问
@Controller
@ResponseBody
public class EmpRestController {

    /**
     * CORS policy: 同源策略
     * 跨源资源共享 ( CORS ) Cross-Origin Resource Sharing
     *      浏览器为了安全，默认会遵循同源策略，( 请求要去的服务器和当前项目所在的服务器必须是同一个源 [ 同一个服务器 ] )，如果不是，请求就会被拦截
     *
     *      复杂的跨域请求会发送两次:
     *          1. options 请求: 预检请求，浏览器会先发送 options 请求，询问服务器是否允许当前域名进行跨域访问;
     *          2. 真正的请求: POST，GET,DELETE等;
     *
     * 浏览器页面所在的地址: http://localhost/emp/base
     * 页面上要发的请求地址: http://localhost:8080/emp
     * / 之前的内容需要完全一致，浏览器才能把请求发送出去 ( http://localhost 和 http://localhost:8080 需要保持一致)
     *
     * 跨域问题解决:
     *      1. 前端解决;
     *      2. 后端解决，允许前端跨域即可;
     *          原理: 服务器给浏览器的响应头中添加字段: Access-Control-Allow-Origin = *
     */

    @Autowired
    private EmpService empService;

    /*
        返回统一的数据格式
        code: 业务的状态码，200 是成功，其他都是失败
        msg: 提示消息
        data: 服务器返回的数据

        {
            "code":200,
            "msg":"success"
            "data":null
        }
     */

    @GetMapping("/emp/{id}")
    public R<Emp> get(@PathVariable("id") int id) {
        Emp empById = empService.getEmpById(id);
        return empById == null ? R.<Emp>error() : R.<Emp>ok(empById);
    }

    @DeleteMapping("/emp/{id}")
    public R delete(@PathVariable("id") int id) {
        int count = empService.deleteEmp(id);
        return count > 0 ? R.ok() : R.error();
    }

    @PostMapping("/emp")
    public R addEmp(@RequestBody Emp emp) {
        int update = empService.addEmp(emp);
        return update > 0 ? R.ok() : R.error();
    }

    @PutMapping("/emp")
    public R updateEmp(@RequestBody Emp emp) {
        int update = empService.updateEmp(emp);
        return update > 0 ? R.ok() : R.error();
    }

    @GetMapping("/emps")
    public R<List<Emp>> getAll() {
        List<Emp> allEmp = empService.getAllEmp();
        return R.ok(allEmp);
    }

}

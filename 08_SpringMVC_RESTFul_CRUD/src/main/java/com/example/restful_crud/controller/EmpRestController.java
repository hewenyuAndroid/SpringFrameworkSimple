package com.example.restful_crud.controller;

import com.example.restful_crud.commen.R;
import com.example.restful_crud.pojo.Emp;
import com.example.restful_crud.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
public class EmpRestController {

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

package com.example.mapper;

import com.example.pojo.Emp;
import org.apache.ibatis.annotations.Param;

public interface EmpMapper {

    /** 根据 empId 查询用户 */
    Emp queryEmpById(@Param("empId") Integer empId);

}

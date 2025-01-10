package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;


public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);
    void save(EmployeeDTO employeeDTO);


    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void enableOrDisable(Integer status, Long id);

    Employee getById(Integer id);

    void updateInfo(EmployeeDTO employeeDTO);
}

package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "Employee related interface")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation(value = "Employee login")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "Employee logout")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * add new employee
     * @param employee
     * @return
     */
    @PostMapping
    @ApiOperation(value = "Save employee")
    public Result save(@RequestBody EmployeeDTO employee){
        log.info("add new employee: {}", employee);
        System.out.println("The current Controller thread id" + Thread.currentThread().getId());
        employeeService.save(employee);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("page query")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("page query", employeePageQueryDTO);
        PageResult result = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(result);
    }


    @PostMapping("/status/{status}")
    @ApiOperation("Enable or disable employee account")
    public Result<T> enableOrDisable(@PathVariable("status") Integer status, Long id) {
        employeeService.enableOrDisable(status, id);
        return Result.success();
    }


    /**
     * Get employee data by id
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @ApiOperation("Retrive user data by id")
    public Result<Employee> getById(@PathVariable Integer id) {
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    @PutMapping()
    @ApiOperation("Update the current employee information")
    public Result<T> updateInfo(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.updateInfo(employeeDTO);
        return Result.success();
    }


//    @GetMapping
//    @ApiOperation()

}

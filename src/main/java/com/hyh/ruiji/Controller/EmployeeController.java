package com.hyh.ruiji.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyh.ruiji.Service.EmployeeService;
import com.hyh.ruiji.common.R;
import com.hyh.ruiji.pojo.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpSession httpSession,@RequestBody Employee employee){
        //获取密码并转为md5
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //根据用户名查询
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee employeeServiceOne = employeeService.getOne(employeeLambdaQueryWrapper);

        //根据用户名查询，为空则失败
        if (employeeServiceOne==null){
            return R.error("登入失败");
        }
        //查看密码是否正确
        if (!employeeServiceOne.getPassword().equals(password)){
            return R.error("登入失败");
        }
        //查看该账户是否被禁用
        if (employeeServiceOne.getStatus()==0){
            return R.error("该账户以被禁用");
        }

        httpSession.setAttribute("employee",employeeServiceOne.getId());

        return R.success(employeeServiceOne);

    }

    @PostMapping("/logout")
    public R<String> logout(HttpSession httpSession){
        httpSession.removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(@RequestBody Employee employee){
        //默认初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //设置创建时间和修改时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
        //设置创建用户和修改用户
//        employee.setCreateUser((Long) httpSession.getAttribute("employee"));
//        employee.setUpdateUser((Long) httpSession.getAttribute("employee"));
        //保存数据
        employeeService.save(employee);
        //return
        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R<Page<Employee>> page(int page,int pageSize,String name){
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        wrapper.orderByDesc(Employee::getUpdateTime);
        return R.success(employeeService.page(new Page<>(page, pageSize), wrapper));
    }

    @PutMapping
    public R<String> update(HttpSession httpSession,@RequestBody Employee employee){
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser((Long) httpSession.getAttribute("employee"));
        employeeService.updateById(employee);
        return R.success("修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable long id){
        return R.success(employeeService.getById(id));
    }

}

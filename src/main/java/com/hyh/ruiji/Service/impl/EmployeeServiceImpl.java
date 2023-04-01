package com.hyh.ruiji.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyh.ruiji.Mapper.EmployeeMapper;
import com.hyh.ruiji.Service.EmployeeService;
import com.hyh.ruiji.pojo.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}

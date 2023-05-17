package org.zj.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.zj.takeout.entity.Employee;
import org.zj.takeout.mapper.EmployeeMapper;
import org.zj.takeout.service.EmployeeService;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}

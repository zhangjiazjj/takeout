package org.zj.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.bcel.internal.generic.RET;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;


import org.springframework.web.bind.annotation.*;
import org.zj.takeout.common.R;
import org.zj.takeout.entity.Employee;
import org.zj.takeout.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
//        对获取到的用户密码进行MD5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
//        根据用户名查询数据库
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername,employee.getUsername());
        Employee employee1 = employeeService.getOne(wrapper);//调用getOne()方法是因为数据库表对这个字段的索引做了唯一性约束
//        如果根据用户名没有查询到用户则返回用户不存在
        if(employee1 == null){
            return R.error("此用户不存在");
        }
//       否则比较密码是否相同，如果不相同则返回密码错误
        if(!employee1.getPassword().equals(password)){
            return R.error("用户密码错误");
        }
//        接着查看用户状态是否为警用状态（0为禁用，1为正常）
        if(employee1.getStatus() == 0){
            return R.error("用户状态已经被禁用");
        }
//        将用户信息放在session中
        request.getSession().setAttribute("employee",employee1.getId());
        Long id = employee1.getId();
        return R.success(employee1);
    }

//    退出登录
    @PostMapping("/logout")
    public R<?> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

//    新增员工
    @PostMapping
    public R<?> addEmployee(HttpServletRequest request ,@RequestBody Employee employee){
        log.info("新增员工{}",employee.toString());
//        使用MD5对密码进行加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        Long employee1 = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(employee1);
//
//        employee.setUpdateUser(employee1);
        boolean save = employeeService.save(employee);
        if (save){
            return R.success(null);
        }
        return R.error("保存失败");
    }

    /**
     * 员工信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */

    @GetMapping("/page")
    public R<Page> page(@RequestParam("page") Long page,@RequestParam("pageSize") Long pageSize,@RequestParam(value = "name",required = false) String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);
//          构造分页对象
        Page page1 = new Page(page,pageSize);

//        构造条件查询器
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
//        添加条件
        wrapper.like(StringUtils.isNotEmpty(name),Employee::getUsername,name);
//        添加排序条件
        wrapper.orderByDesc(Employee::getCreateTime);
//        查询
        employeeService.page(page1,wrapper);

        return R.success(page1);
    }

    /**
     * 修改员工
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
//        这里有一个问题就是前端页面只能展示精度为16位的整数，所以需要自己配置消息转换器，将long型序列化位string型
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employee.setUpdateTime(LocalDateTime.now());
        boolean b = employeeService.updateById(employee);
        return R.success("修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> selectEmployeeByid(@PathVariable String id){
        Employee employee = employeeService.getById(id);
        if (employee!=null){
            return R.success(employee);
        }
        return R.error("查询失败");
    }
}

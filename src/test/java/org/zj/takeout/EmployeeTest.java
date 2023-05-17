package org.zj.takeout;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zj.takeout.service.EmployeeService;

@SpringBootTest
public class EmployeeTest {
    @Autowired
    EmployeeService employeeService;
    @Test
    public void test(){

    }
}

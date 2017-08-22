package com.nicchagil.springjtaexercise.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nicchagil.springjtaexercise.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-jta.xml"})
public class MyTest {

	@Autowired
    private UserService userService;
    
    @Test
    public void c1() {
        this.userService.updateBalanceAndStock();
    }
    
}

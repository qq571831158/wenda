package com.newcoder.wenda.controller;

import com.newcoder.wenda.WendaApplication;
import com.newcoder.wenda.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by apple on 2017/8/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WendaApplication.class)
public class HomeControllerTest {
    @Autowired
    UserService userService;

    @Test
    public void getUser(){
        userService.getUser(6);
    }

}
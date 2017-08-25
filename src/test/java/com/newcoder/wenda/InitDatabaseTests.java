package com.newcoder.wenda;

import com.newcoder.wenda.dao.UserDAO;
import com.newcoder.wenda.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by apple on 2017/8/19.
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

    @Test
    public void contextLoads(){
        userDAO.addUser(new User(1,"1","1","1","1"));
    }
}

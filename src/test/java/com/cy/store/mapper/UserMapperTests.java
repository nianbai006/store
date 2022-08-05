package com.cy.store.mapper;


import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


//表示为测试类，不会被打包
@SpringBootTest
//表示启动这个单元测试类（否则不能运行）需要传递一个参数，必须是springrunner的实例类型
@RunWith(SpringRunner.class)
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("ke");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUsername() {
        User user = userMapper.findByUsername("张三");
        System.out.println(user);
    }

    @Test
    public void findByUid() {
        User user = userMapper.findByUid(2);
        System.out.println(user);
    }

    @Test
    public void updatePasswordByUid() {
        userMapper.updatePasswordByUid(8, "1234", "zhs",
                new Date());
    }
}

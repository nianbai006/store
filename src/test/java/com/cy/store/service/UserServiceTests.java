package com.cy.store.service;


import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//表示为测试类，不会被打包
@SpringBootTest
//表示启动这个单元测试类（否则不能运行）需要传递一个参数，必须是springrunner的实例类型
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired
    private IUserService userService;

    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("cersi");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login() {
        User user = userService.login("Tom", "321");
        System.out.println(user);
    }

    @Test
    public void changePassword() {
        userService.changePassword(7, "Tom", "123", "321");
    }

}

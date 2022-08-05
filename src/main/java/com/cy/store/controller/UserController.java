package com.cy.store.controller;


import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User data = userService.login(username, password);

        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));

        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("logout")
    public JsonResult<Void> logout(HttpSession session) {
        session.removeAttribute("uid");
        session.removeAttribute("username");
        return new JsonResult<>(OK);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        uid = (Integer) session.getAttribute("uid");
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(OK);
    }


}

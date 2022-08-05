package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        if (result != null) {
            throw new UsernameDuplicatedException("用户名被占用");
        }

        String oldPassword = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMD5Password(oldPassword, salt);
        user.setSalt(salt);
        user.setPassword(md5Password);

        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
    }

    @Override
    public User login(String username, String password) {
        User result = userMapper.findByUsername(username);
        if (result == null) {
            throw new UsernameNotFoundException("该用户未注册");
        }
        String rightPassword = result.getPassword();
        String salt = result.getSalt();
        String encryptedPassword = getMD5Password(password, salt);
        if (!rightPassword.equals(encryptedPassword)) {
            throw new PasswordNotMatchException("密码错误");
        }
        if (result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("该用户未注册");
        }
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("该用户未注册");
        }
        String rightPassword = result.getPassword();
        String salt = result.getSalt();
        String encryptedPassword = getMD5Password(oldPassword, salt);
        if (!rightPassword.equals(encryptedPassword)) {
            throw new PasswordNotMatchException("密码错误");
        }
        String newEncryptedPassword = getMD5Password(newPassword, salt);
        Integer rows = userMapper.updatePasswordByUid(uid, newEncryptedPassword, username, new Date());
        if (rows != 1) {
            throw new UpdateException("在用户修改密码过程中产生了未知的异常");
        }
    }


    private String getMD5Password(String password, String salt) {
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}

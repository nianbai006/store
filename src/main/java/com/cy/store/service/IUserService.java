package com.cy.store.service;

import com.cy.store.entity.User;

public interface IUserService {
    void reg(User user);

    User login(String username, String password);

    User updatePasswordByUid();


}

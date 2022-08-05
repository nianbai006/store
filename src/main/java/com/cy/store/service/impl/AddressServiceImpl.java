package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.AddressCountLimitException;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressMapper addressMapper;
    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        System.out.println("addNewAddress");
        User result = userMapper.findByUsername(username);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("该用户未注册");
        }
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("该用户地址数量已达到上限");
        }
        address.setUid(uid);
        address.setIsDefault(count == 0 ? 1 : 0);

        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("在用户添加地址过程中产生了未知的异常");
        }
    }

}

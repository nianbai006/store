package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Mapper
@Repository
/*用户模块的持久层接口*/
public interface UserMapper {
    /**
     * 插入用户数据
     *
     * @param user 用户的数据
     * @return 受影响的行数（增删改，都有受影响的行数做返回值
     * 根据行数判断是否执行成功
     */
    Integer insert(User user);

    /**
     * 根据用户名来查询用户的数据
     *
     * @param username 用户名
     * @return 如果找到对应的用户则返回数据，没则返回null
     */
    User findByUsername(String username);

    User findByUid(Integer uid);

    Integer updatePasswordByUid(Integer uid,
                                String password,
                                String modifiedUser,
                                Date modifiedTime);
}

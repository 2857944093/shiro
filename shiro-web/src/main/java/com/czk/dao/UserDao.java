package com.czk.dao;

import com.czk.vo.User;

import java.util.List;

/**
 * @Author: Created by ChenZK
 * @Create: 2019/12/16 17:06
 */
public interface UserDao {


    User getUserByUserName(String userName);

    List<String> queryRoleByUserName(String userName);
}

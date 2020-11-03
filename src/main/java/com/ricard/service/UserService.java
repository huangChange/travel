package com.ricard.service;


import com.ricard.domain.User;

public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean resgistUser(User user);

    boolean active(String code);

    User login(User user);
}

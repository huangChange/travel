package com.ricard.dao;


import com.ricard.domain.User;

public interface UserDao {
    /**
     * 添加用户
     * @param user
     * @return
     */
    void addUser(User user);

    /**
     * 根据用户名查询用户是否已经存在
     * @param username
     * @return
     */
    public User findByUsername(String username);

    User findByCode(String code);

    void updateStatus(User user);

    User findUserByUsernamePassword(String username, String password);
}

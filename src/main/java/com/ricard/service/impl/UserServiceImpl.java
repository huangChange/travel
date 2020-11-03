package com.ricard.service.impl;


import com.ricard.dao.UserDao;
import com.ricard.dao.impl.UserDaoImpl;
import com.ricard.domain.User;
import com.ricard.service.UserService;
import com.ricard.util.UuidUtils;


public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean resgistUser(User user) {
        // 1.根据用户名查询用户对象
        User find_user = userDao.findByUsername(user.getUsername());

        if(find_user != null) {
            // 注册失败,用户名存在
            return false;
        }
        // 2.保存用户信息
        user.setCode(UuidUtils.getUuid());
        // 2.1设置激活码
        user.setStatus("N");
        userDao.addUser(user);

        // String content = "<a href='http://localhost:8080/travel/user/active?code="+ user.getCode() +"'>点击激活(ricard学院)</a>";
        // MailUtils.sendMail(user.getEmail(), content, "激活邮件");
        // 2.2设置激活状态
        return true;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        // 1.根据激活码查询用户对象
        User user = userDao.findByCode(code);

        if(user != null) {
            // 2.调用dao的修改激活状态的方法
            userDao.updateStatus(user);
            return true;
        }

        return false;
    }

    /**
     * 登录方法
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findUserByUsernamePassword(user.getUsername(), user.getPassword());
    }
}

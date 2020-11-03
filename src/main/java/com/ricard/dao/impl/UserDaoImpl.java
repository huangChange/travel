package com.ricard.dao.impl;

import com.ricard.dao.UserDao;
import com.ricard.domain.User;
import com.ricard.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;



public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        // 定义sql语句
        String sql = "select * from tab_user where username = ?";
        User user = null;
        try {
            // 执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (DataAccessException e) {
        }

        return user;
    }

    /**
     * 根据激活码查询用户对象
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {
        // 定义sql语句
        String sql = "select * from tab_user where code = ?";
        // 执行sql
        try {
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
            return user;
        }catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 修改指定用户的状态
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        // 定义sql语句,修改用户状态
        String sql = "update tab_user set status = 'Y' where uid = ?";

        template.update(sql, user.getUid());
    }

    /**
     * 根据用户名和密码查询
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findUserByUsernamePassword(String username, String password) {
        // 定义sql
        String sql = "select * from tab_user where username = ? and password = ?";
        User user = null;
        try {
            // 执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        }catch (DataAccessException e) {
        }

        return user;
    }

    /**
     * 添加用户
     * @param user
     */
    @Override
    public void addUser(User user) {
        // 定义sql
        String sql = "insert into tab_user(username, password, name, birthday, sex, telephone, email, status, code) values(?,?,?,?,?,?,?,?,?)";

        // 执行sql
        try {
            template.update(sql, user.getUsername(), user.getPassword(),
                    user.getName(), user.getBirthday(), user.getSex(),
                    user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
        }catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}

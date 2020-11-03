package com.ricard.dao.impl;


import com.ricard.dao.CategoryDao;
import com.ricard.domain.Category;
import com.ricard.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        // 定义sql语句
        String sql = "select * from tab_category";
        // 执行sql
        List<Category> list = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));

        return list;
    }
}

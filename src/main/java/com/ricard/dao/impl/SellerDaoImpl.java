package com.ricard.dao.impl;


import com.ricard.dao.SellerDao;
import com.ricard.domain.Seller;
import com.ricard.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Seller findSeller(int sid) {
        // 定义sql
        String sql = "select * from tab_seller where sid = ?";

        return template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
    }
}

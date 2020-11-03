package com.ricard.dao.impl;


import com.ricard.dao.RouteImgDao;
import com.ricard.domain.RouteImg;
import com.ricard.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<RouteImg> findAllImg(int rid) {
        // 定义sql
        String sql = "select * from tab_route_img where rid = ?";

        List<RouteImg> listImg = template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        return listImg;
    }
}

package com.ricard.dao.impl;


import com.ricard.dao.RouteDao;
import com.ricard.domain.Route;
import com.ricard.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int getTotalCount(int cid, String rname) {
        // 定义sql
        // String sql = "select count(*) from  tab_route where cid = ?";
        // 1.定义sql模板
        String sql = "select count(*) from  tab_route where 1 = 1";
        // 2.判断参数是否有值
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        // 保存查询时的参数值
        List<Object> param = new ArrayList<Object>();
        if(cid != 0) {
            sb.append(" and cid = ?");
            param.add(cid);
        }
        if(rname != null && rname.length() > 0 && !rname.equals("null")) {
            sb.append(" and rname like ? ");
            param.add("%" + rname + "%");
        }
        sql = sb.toString();

        //System.out.println(param.toArray());
        Integer totalCount = template.queryForObject(sql, Integer.class, param.toArray());

        return totalCount;
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageCount, String rname) {
        // String sql = "select * from tab_route where cid = ? limit ?, ?";
        // 1.定义sql模板
        String sql = "select * from tab_route where 1 = 1 ";
        // 2.判断参数是否有值
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        // 保存查询时的参数值
        List<Object> param = new ArrayList<Object>();
        if(cid != 0) {
            sb.append(" and cid = ?");
            param.add(cid);
        }
        if(rname != null && rname.length() > 0 && !rname.equals("null")) {
            sb.append(" and rname like ? ");
            param.add("%" + rname + "%");
        }
        sb.append(" limit ?, ?");
        // 给param添加参数
        param.add(start);
        param.add(pageCount);

        sql = sb.toString();
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), param.toArray());

        return list;
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";

        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}

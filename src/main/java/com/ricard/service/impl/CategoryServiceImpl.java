package com.ricard.service.impl;


import com.ricard.dao.CategoryDao;
import com.ricard.dao.impl.CategoryDaoImpl;
import com.ricard.domain.Category;
import com.ricard.service.CategoryService;
import com.ricard.util.JedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        // 1.从redis中查询
        // 1.1获取jedis客户端
        Jedis jedis = JedisUtils.getJedis();
        // 1.2可使用sortedset排序查询
        // Set<String> categories = jedis.zrange("category", 0, -1);
        // 1.3查询sortedset中的分数(cid)和(cname)的值
        Set<Tuple> categories = jedis.zrangeWithScores("category", 0, -1);
        List<Category> list = null;
        // 2.判断是否为空
        if(categories == null || categories.size() == 0) {
            System.out.println("从数据库查询...");
            // 3.为空,从数据库中查询
            list = categoryDao.findAll();
            // 4.将集合数据存储到redis中,key为category
            for(Category category : list) {
                jedis.zadd("category", category.getCid(), category.getCname());
            }
        }else {
            System.out.println("从redis中查询...");
            // 5.如果不为空,将set的数据存入list
            // 将set集合转换为list集合
            list = new ArrayList<Category>();
            for(Tuple tuple : categories) {
                Category category = new Category();
                category.setCid((int)tuple.getScore());
                category.setCname(tuple.getElement());
                list.add(category);
            }
        }

        return list;
    }
}

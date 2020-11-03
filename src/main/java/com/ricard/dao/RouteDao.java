package com.ricard.dao;


import com.ricard.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 根据cid查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    public int getTotalCount(int cid, String rname);

    /**
     * 根据cid,start,pageCount查询当前页的数据集合
     * @param cid
     * @param satrt
     * @param pageCount
     * @param rname
     * @return
     */
    public List<Route> findByPage(int cid, int satrt, int pageCount, String rname);

    /**
     * 根据id查询
     * @param rid
     * @return
     */
    public Route findOne(int rid);
}

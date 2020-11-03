package com.ricard.service;


import com.ricard.domain.PageBean;
import com.ricard.domain.Route;

/**
 * 线路serivce
 */
public interface RouteService {
    /**
     * 根据类别分页查询
     * @param cid
     * @param currentPage
     * @param pageCount
     * @param rname
     * @return
     */
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageCount, String rname);

    public Route findOne(int rid);
}

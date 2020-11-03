package com.ricard.service.impl;


import com.ricard.dao.FavoriteDao;
import com.ricard.dao.RouteDao;
import com.ricard.dao.RouteImgDao;
import com.ricard.dao.SellerDao;
import com.ricard.dao.impl.FavoriteDaoImpl;
import com.ricard.dao.impl.RouteDaoImpl;
import com.ricard.dao.impl.RouteImgDaoImpl;
import com.ricard.dao.impl.SellerDaoImpl;
import com.ricard.domain.PageBean;
import com.ricard.domain.Route;
import com.ricard.domain.RouteImg;
import com.ricard.domain.Seller;
import com.ricard.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();

    private RouteImgDao routeImgDao = new RouteImgDaoImpl();

    private SellerDao sellerDao = new SellerDaoImpl();

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageCount, String rname) {
        // 封装PageBean对象
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage); // 当前页码
        pb.setPageCount(pageCount);     // 页码显示条数
        int totalCount = routeDao.getTotalCount(cid, rname);
        pb.setTotalCount(totalCount);   // 总记录数

        // 开始的记录数
        int start = (currentPage - 1) * pageCount;
        pb.setList(routeDao.findByPage(cid, start, pageCount, rname));    // 要显示的数据
        // 定义总页码数
        int totalPage = totalCount % pageCount == 0 ? totalCount / pageCount : totalCount / pageCount + 1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public Route findOne(int rid) {
        // 1.根据id去route表中查询route对象
        Route route = routeDao.findOne(rid);
        // 2.根据route的id查询图片集合信息
        List<RouteImg> listImg = routeImgDao.findAllImg(rid);
        // 3.给route对象设置属性List<RouteImg>
        route.setRouteImgList(listImg);
        // 4.根据route的sid(商家id)查询商家信息
        Seller seller = sellerDao.findSeller(route.getSid());
        // 5.给route对象设置属性Seller
        route.setSeller(seller);

        // 6.查询收藏次数
        int count = favoriteDao.findCountById(route.getRid());
        route.setCount(count);

        return route;
    }
}

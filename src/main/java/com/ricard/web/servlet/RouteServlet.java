package com.ricard.web.servlet;

import com.ricard.domain.PageBean;
import com.ricard.domain.Route;
import com.ricard.domain.User;
import com.ricard.service.FavoriteService;
import com.ricard.service.RouteService;
import com.ricard.service.impl.FavoriteServiceImpl;
import com.ricard.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    // 分页查询
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageCountStr = request.getParameter("pageCount");
        String cidStr = request.getParameter("cid");

        // 接收线路名称
        String rname = request.getParameter("rname");

        int cid = 0;
        int currentPage = 0;
        int pageCount = 0;
        // 处理参数
        if(cidStr != null && cidStr.length() > 0 && !cidStr.equals("null")) {
            cid = Integer.parseInt(cidStr);
        }
        // 当前页码,如果不传递该参数则默认为1
        if(currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }
        // 每页显示条数,如果不传递参数则默认为5
        if(pageCountStr != null && pageCountStr.length() > 0) {
            pageCount = Integer.parseInt(pageCountStr);
        }else {
            pageCount = 5;
        }


        // 3.调用service查询PageBean对象
        PageBean<Route> route = routeService.pageQuery(cid, currentPage, pageCount, rname);
        // 4.将PageBean对象序列化为json, 返回
        writeValue(route, response);
    }

    /**
     * 根据id查询一个旅游线路的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.接收参数id
        String rid = request.getParameter("rid");
        // 2.调用service查询route对象
        Route route = routeService.findOne(Integer.parseInt(rid));
        // 转为json写回客户端
        writeValue(route, response);
    }

    /**
     * 判断当前登录用户是否收藏该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取线路id
        String rid = request.getParameter("rid");
        // 2.获取当前登录的用户
        User user = (User)request.getSession().getAttribute("user");
        int uid = 0;
        if(user == null) {
            // 用户尚未登录
            uid = 0;
            writeValue(user, response);
            return;
        }else {
            // 用户已经登录
            uid = user.getUid();
        }

        // 3.调用FavoriteService查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);
        writeValue(flag, response);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取线路rid
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        // 2.调用service添加
        favoriteService.add(rid, user.getUid());
    }
}

package com.ricard.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricard.domain.ResultInfo;
import com.ricard.domain.User;
import com.ricard.service.UserService;
import com.ricard.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")  // user/add, user/find
public class UserServlet extends BaseServlet {
    // 声明userService的业务对象
    private UserService userService= new UserServiceImpl();

    // 注册功能
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("check");
        // 获取验证码
        HttpSession session = request.getSession();
        String check_code = (String)session.getAttribute("CHECKCODE_SERVER");
        // 一获取到验证码就将session存储的验证码删除
        session.removeAttribute("CHECKCODE_SERVER");

        // 3.调用service完成注册
        boolean flag = false;
        // 创建响应对象
        ResultInfo info = new ResultInfo();

        if(check_code != null && check_code.equalsIgnoreCase(code)) {
            // 1.获取数据
            Map<String, String[]> map = request.getParameterMap();
            // 2.封装对象
            User user = new User();

            try {
                // 将map中的数据封装到user对象中
                BeanUtils.populate(user, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            flag = userService.resgistUser(user);
            // 4.响应结果
            if(flag) {
                // 注册成功
                info.setFlag(true);
            }else {
                // 注册失败
                info.setFlag(false);
                info.setErrorMsg("注册失败!");
            }
        }else {
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
        }

        String json = this.writeValueAsString(info);

        // 将json数据写回客户端
        // 设置contetx-type
        this.writeValue(json, response);
    }
    // 登录功能
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        // 2.封装user对象
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 3.定义Service对象

        User u = userService.login(user);
        // 定义ResultInfo对象
        ResultInfo info = new ResultInfo();
        if(u == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误!");
        }
        if(u != null && !"Y".equals(u.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("用户还未激活,请先激活!");
        }
        if(u != null && "Y".equals(u.getStatus())) {
            request.getSession().setAttribute("user", u);
            info.setFlag(true);
        }

        // 使用ObjectMapper对象序列化info对象
        ObjectMapper mapper = new ObjectMapper();
        // 设置响应类型和字符编码
        response.setContentType("application/json;charset=utf-8");

        mapper.writeValue(response.getWriter(), info);
    }
    // 查找单个用户功能
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从session中获取用户
        Object user = request.getSession().getAttribute("user");

        this.writeValue(user, response);
    }
    // 退出
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 销毁session
        request.getSession().invalidate();

        // 重定向
        response.sendRedirect(request.getContextPath() + "/index.html");
    }
    // 激活功能
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取激活码
        String code = request.getParameter("code");
        if(code != null) {
            // 2.调用service完成激活
            boolean flag = userService.active(code);
            // 3.判断标记
            String msg = null;
            if (flag) {
                // 激活成功
                msg = "激活成功,请<a href='login.html'>登录</a>";
            } else {
                // 激活失败
                msg = "激活失败,请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

}

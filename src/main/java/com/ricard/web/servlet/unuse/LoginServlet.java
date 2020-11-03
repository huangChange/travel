package com.ricard.web.servlet.unuse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricard.domain.ResultInfo;
import com.ricard.domain.User;
import com.ricard.service.UserService;
import com.ricard.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        UserService service = new UserServiceImpl();

        User u = service.login(user);
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
            request.getSession().setAttribute("name", u.getName());
            info.setFlag(true);
        }

        // 使用ObjectMapper对象序列化info对象
        ObjectMapper mapper = new ObjectMapper();
        // 设置响应类型和字符编码
        response.setContentType("application/json;charset=utf-8");

        mapper.writeValue(response.getWriter(), info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

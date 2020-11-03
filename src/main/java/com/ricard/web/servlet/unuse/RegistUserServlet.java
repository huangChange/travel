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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String code = request.getParameter("check");
        // 获取验证码
        HttpSession session = request.getSession();
        String check_code = (String)session.getAttribute("CHECKCODE_SERVER");
        // 一获取到验证码就将session存储的验证码删除
        session.removeAttribute("CHECKCODE_SERVER");

        // 3.调用service完成注册
        UserService service = null;
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
            service = new UserServiceImpl();
            flag = service.resgistUser(user);
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

        // 将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        // 将json数据写回客户端
        // 设置contetx-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

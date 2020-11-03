package com.ricard.web.servlet.unuse;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从session中获取用户
        Object name = request.getSession().getAttribute("name");
        System.out.println(name);
        // 将name写回客户端
        ObjectMapper mapper = new ObjectMapper();

        // 设置响应格式
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getWriter(), name);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

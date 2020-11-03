package com.ricard.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("service方法被执行了");

        // 完成方法分发
        // 1.获取请求路径·
        String uri = req.getRequestURI();   // user/add
        // System.out.println("请求路径: " + uri);
        // 2.获取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        // System.out.println("方法名称" + methodName);
        // 3.获取方法对象
        // System.out.println(this);   // 谁调用service方法就代表谁
        try {
            // 3.获取方法对象
            // 注意: 需要忽略方法的访问权限修饰符
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 4.执行方法
            // 暴力反射
            // method.setAccessible(true);
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接将传入的对象序列化为json,并写回客户端
     * @param obj
     */
    public void writeValue(Object obj, HttpServletResponse response) throws IOException {
        // 2.序列化list集合
        ObjectMapper mapper = new ObjectMapper();
        // 设置响应格式
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getWriter(), obj);
    }
    /**
     * 将传入的对象序列化为json并返回
     * @param obj
     * @return
     */
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}

package com.ricard.web.servlet;


import com.ricard.domain.Category;
import com.ricard.service.CategoryService;
import com.ricard.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CateGoryServlet extends BaseServlet {
    // 定义一个CateforyService对象
    CategoryService categoryService= new CategoryServiceImpl();
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.调用service查询所有
        List<Category> list = categoryService.findAll();

        this.writeValue(list, response);
    }

}

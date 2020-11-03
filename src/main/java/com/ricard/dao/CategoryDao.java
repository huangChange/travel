package com.ricard.dao;


import com.ricard.domain.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> findAll();
}

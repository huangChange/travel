package com.ricard.dao;


import com.ricard.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

    public List<RouteImg> findAllImg(int rid);
}

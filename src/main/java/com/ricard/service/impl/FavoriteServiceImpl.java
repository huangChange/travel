package com.ricard.service.impl;


import com.ricard.dao.FavoriteDao;
import com.ricard.dao.impl.FavoriteDaoImpl;
import com.ricard.domain.Favorite;
import com.ricard.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        if(favorite == null) {
            return false;
        }
        return true;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.addFavorite(Integer.parseInt(rid), uid);
    }
}

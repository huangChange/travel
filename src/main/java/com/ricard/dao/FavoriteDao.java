package com.ricard.dao;


import com.ricard.domain.Favorite;

public interface FavoriteDao {
    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid, int uid);

    /**
     * 通过rid查询总收藏次数
     * @param rid
     * @return
     */
    public int findCountById(int rid);

    public void addFavorite(int rid, int uid);
}

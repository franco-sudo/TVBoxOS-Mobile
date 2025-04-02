package com.github.tvbox.osc.cache;

import java.util.List;

/**
 * @author pj567
 * @date :2021/1/7
 * @description:
 */
public interface VodCollectDao {
    long insert(VodCollect record);

    List<VodCollect> getAll();

    VodCollect getVodCollect(int id);

    void delete(int id);

    VodCollect getVodCollect(String sourceKey, String vodId);

    int delete(VodCollect record);

    void deleteAll();
}
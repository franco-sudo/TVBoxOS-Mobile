package com.github.tvbox.osc.cache;

import java.util.List;

/**
 * @author pj567
 * @date :2021/1/7
 * @description:
 */
public interface VodRecordDao {
    long insert(VodRecord record);

    List<VodRecord> getAll(int size);

    VodRecord getVodRecord(String sourceKey, String vodId);

    int delete(VodRecord record);

    int getCount();

    /**
     * 保留最新指定条数, 其他删除.
     * @param size 保留条数
     * @return
     */
    int reserver(int size);

    void deleteAll();
}
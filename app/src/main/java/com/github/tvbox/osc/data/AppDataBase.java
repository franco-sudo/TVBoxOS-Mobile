package com.github.tvbox.osc.data;

import com.github.tvbox.osc.cache.CacheDao;
import com.github.tvbox.osc.cache.VodCollectDao;
import com.github.tvbox.osc.cache.VodRecordDao;

/**
 * 类描述:
 *
 * @author pj567
 * @since 2020/5/15
 */
// 移除Room依赖，创建兼容实现
public abstract class AppDataBase {
    public abstract CacheDao getCacheDao();

    public abstract VodRecordDao getVodRecordDao();

    public abstract VodCollectDao getVodCollectDao();
}

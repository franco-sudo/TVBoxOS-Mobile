package com.github.tvbox.osc.cache;

/**
 * 类描述:
 *
 * @author pj567
 * @since 2020/5/15
 */
public interface CacheDao {
    long save(Cache cache);

    byte[] getCache(String key);
}

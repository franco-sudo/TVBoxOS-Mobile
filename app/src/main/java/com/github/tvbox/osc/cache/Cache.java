package com.github.tvbox.osc.cache;

import java.io.Serializable;

/**
 * 类描述:
 *
 * @author pj567
 * @since 2020/5/15
 */
public class Cache implements Serializable {
    public String key;
    public byte[] data;
}

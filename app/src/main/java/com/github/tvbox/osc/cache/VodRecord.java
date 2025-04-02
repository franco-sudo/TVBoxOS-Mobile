package com.github.tvbox.osc.cache;

import java.io.Serializable;

/**
 * @author pj567
 * @date :2021/1/7
 * @description:
 */
public class VodRecord implements Serializable {
    public int id;
    public String vodId;
    public long updateTime;
    public String sourceKey;
    public String dataJson;
}
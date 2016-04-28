package com.cn.guojinhu.v2ex.data;


public class Stats {
    public long topic_max;
    public long member_max;

    public Stats() {
    }

    @Override
    public String toString() {
        return "Stats{" +
                "topic_max=" + topic_max +
                ", member_max=" + member_max +
                '}';
    }
}

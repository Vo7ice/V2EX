package com.cn.guojinhu.v2ex.data;


public class Nodes {

    public int id;
    public String name;
    public String url;
    public String title;
    public String title_alternative;
    public int topics;
    public String header;
    public String footer;
    public long created;

    public Nodes() {
    }

    @Override
    public String toString() {
        return "Nodes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", title_alternative='" + title_alternative + '\'' +
                ", topics=" + topics +
                ", header='" + header + '\'' +
                ", footer='" + footer + '\'' +
                ", created=" + created +
                '}';
    }
}

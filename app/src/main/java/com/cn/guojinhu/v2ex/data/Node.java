package com.cn.guojinhu.v2ex.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Node implements Parcelable{
    public int id;
    public String name;
    public String title;
    public String title_alternative;
    public String url;
    public int topics;
    public String avatar_mini;
    public String avatar_normal;
    public String avatar_large;

    public Node() {
    }

    protected Node(Parcel in) {
        id = in.readInt();
        name = in.readString();
        title = in.readString();
        title_alternative = in.readString();
        url = in.readString();
        topics = in.readInt();
        avatar_mini = in.readString();
        avatar_normal = in.readString();
        avatar_large = in.readString();
    }

    public static final Creator<Node> CREATOR = new Creator<Node>() {
        @Override
        public Node createFromParcel(Parcel in) {
            return new Node(in);
        }

        @Override
        public Node[] newArray(int size) {
            return new Node[size];
        }
    };

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(title);
        dest.writeString(title_alternative);
        dest.writeString(url);
        dest.writeInt(topics);
        dest.writeString(avatar_mini);
        dest.writeString(avatar_normal);
        dest.writeString(avatar_large);
    }
}

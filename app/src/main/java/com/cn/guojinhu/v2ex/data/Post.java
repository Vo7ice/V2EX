package com.cn.guojinhu.v2ex.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable{

    public long id;
    public String title;
    public String url;
    public String content;
    public String content_rendered;
    public int replies;
    public Member member;
    public Node node;
    public long created;
    public long last_modified;
    public long last_touched;

    protected Post(Parcel in) {
        id = in.readLong();
        title = in.readString();
        url = in.readString();
        content = in.readString();
        content_rendered = in.readString();
        replies = in.readInt();
        created = in.readLong();
        last_modified = in.readLong();
        last_touched = in.readLong();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", content_rendered='" + content_rendered + '\'' +
                ", user=" + member +
                ", node=" + node +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(content);
        dest.writeString(content_rendered);
        dest.writeInt(replies);
        dest.writeLong(created);
        dest.writeLong(last_modified);
        dest.writeLong(last_touched);
    }
}

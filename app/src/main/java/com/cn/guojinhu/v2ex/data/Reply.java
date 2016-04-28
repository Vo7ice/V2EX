package com.cn.guojinhu.v2ex.data;

import android.os.Parcel;
import android.os.Parcelable;


public class Reply implements Parcelable {

    public int id;
    public int thanks;
    public String content;
    public String content_rendered;
    public Member member;
    public String created;
    public String last_modified;

    public Reply(int id, int thanks, String content, String content_rendered, Member member, String created, String last_modified) {
        this.id = id;
        this.thanks = thanks;
        this.content = content;
        this.content_rendered = content_rendered;
        this.member = member;
        this.created = created;
        this.last_modified = last_modified;
    }

    public Reply() {
    }

    protected Reply(Parcel in) {
        id = in.readInt();
        thanks = in.readInt();
        content = in.readString();
        content_rendered = in.readString();
        member = in.readParcelable(Member.class.getClassLoader());
        created = in.readString();
        last_modified = in.readString();
    }

    public static final Creator<Reply> CREATOR = new Creator<Reply>() {
        @Override
        public Reply createFromParcel(Parcel in) {
            return new Reply(in);
        }

        @Override
        public Reply[] newArray(int size) {
            return new Reply[size];
        }
    };

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", thanks=" + thanks +
                ", content='" + content + '\'' +
                ", content_rendered='" + content_rendered + '\'' +
                ", member=" + member +
                ", created='" + created + '\'' +
                ", last_modified='" + last_modified + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(thanks);
        dest.writeString(content);
        dest.writeString(content_rendered);
        dest.writeParcelable(member, flags);
        dest.writeString(created);
        dest.writeString(last_modified);
    }
}

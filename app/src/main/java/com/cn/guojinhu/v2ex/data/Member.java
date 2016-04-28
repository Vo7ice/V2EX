package com.cn.guojinhu.v2ex.data;


import android.os.Parcel;
import android.os.Parcelable;

public class Member implements Parcelable{
        public long id;
        public String username;
        public String tagline;
        public String avatar_mini;
        public String avatar_normal;
        public String avatar_large;

    public Member() {
    }

    protected Member(Parcel in) {
        id = in.readLong();
        username = in.readString();
        tagline = in.readString();
        avatar_mini = in.readString();
        avatar_normal = in.readString();
        avatar_large = in.readString();
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    @Override
        public String toString() {
            return "Member{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    '}';
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(username);
        dest.writeString(tagline);
        dest.writeString(avatar_mini);
        dest.writeString(avatar_normal);
        dest.writeString(avatar_large);
    }
}

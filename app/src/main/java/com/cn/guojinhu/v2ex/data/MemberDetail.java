package com.cn.guojinhu.v2ex.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * {
 "status" : "found",
 "id" : 16147,
 "url" : "http://www.v2ex.com/member/djyde",
 "username" : "djyde",
 "website" : "https://djyde.github.io",
 "twitter" : "",
 "location" : "",
 "tagline" : "",
 "bio" : "喜欢摄影和写作的程序员。",
 "avatar_mini" : "//cdn.v2ex.com/avatar/ed4c/1b66/16147_mini.png?m=1329639748",
 "avatar_normal" : "//cdn.v2ex.com/avatar/ed4c/1b66/16147_normal.png?m=1329639748",
 "avatar_large" : "//cdn.v2ex.com/avatar/ed4c/1b66/16147_large.png?m=1329639748",
 "created" : 1328075793
 }
 */
public class MemberDetail implements Parcelable {

    public String status;
    public long id;
    public String url;
    public String username;
    public String website;
    public String twitter;
    public String location;
    public String tagline;
    public String bio;
    public String avatar_mini;
    public String avatar_normal;
    public String avatar_large;
    public long created;

    public MemberDetail() {
    }

    protected MemberDetail(Parcel in) {
        status = in.readString();
        id = in.readLong();
        url = in.readString();
        username = in.readString();
        website = in.readString();
        twitter = in.readString();
        location = in.readString();
        tagline = in.readString();
        bio = in.readString();
        avatar_mini = in.readString();
        avatar_normal = in.readString();
        avatar_large = in.readString();
        created = in.readLong();
    }

    public static final Creator<MemberDetail> CREATOR = new Creator<MemberDetail>() {
        @Override
        public MemberDetail createFromParcel(Parcel in) {
            return new MemberDetail(in);
        }

        @Override
        public MemberDetail[] newArray(int size) {
            return new MemberDetail[size];
        }
    };

    @Override
    public String toString() {
        return "MemberDetail{" +
                "status='" + status + '\'' +
                ", id=" + id +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", website='" + website + '\'' +
                ", twitter='" + twitter + '\'' +
                ", location='" + location + '\'' +
                ", tagline='" + tagline + '\'' +
                ", bio='" + bio + '\'' +
                ", avatar_mini='" + avatar_mini + '\'' +
                ", avatar_normal='" + avatar_normal + '\'' +
                ", avatar_large='" + avatar_large + '\'' +
                ", created=" + created +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeLong(id);
        dest.writeString(url);
        dest.writeString(username);
        dest.writeString(website);
        dest.writeString(twitter);
        dest.writeString(location);
        dest.writeString(tagline);
        dest.writeString(bio);
        dest.writeString(avatar_mini);
        dest.writeString(avatar_normal);
        dest.writeString(avatar_large);
        dest.writeLong(created);
    }
}

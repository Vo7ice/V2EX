package com.cn.guojinhu.v2ex.utils;


import android.util.Log;

import com.cn.guojinhu.v2ex.api.Contants;
import com.cn.guojinhu.v2ex.data.Member;
import com.cn.guojinhu.v2ex.data.MemberDetail;
import com.cn.guojinhu.v2ex.data.Nodes;
import com.cn.guojinhu.v2ex.data.Post;
import com.cn.guojinhu.v2ex.data.Reply;
import com.cn.guojinhu.v2ex.data.Stats;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.net.ContentHandler;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;
import rx.Observer;

public class HttpUtils {

    public interface V2EXService {
        @GET(Contants.API_LASTEST)
        Call<List<Post>> listPosts();

        @Headers({"Origin:v2ex.com", "Referer:v2ex.com/signin", "Content-Type:application/x-www-form-urlencoded"})
        @FormUrlEncoded
        @POST(Contants.API_SIGN_IN)
        Call<ResponseBody> signin(@Field("u") String username, @Field("p") String password, @Field("next") String next, @Field("once") String once);

        @GET("http://v2ex.com/signin")
        Observer<ResponseBody> signon();

        @GET(Contants.API_STATS)
        Call<Stats> getStats();

        @GET(Contants.API_HOT)
        Call<List<Post>> listHotPosts();

        @GET(Contants.API_ALL_NODES)
        Call<List<Nodes>> getAllNodes();

        @GET(Contants.API_POST_ID)
        Call<List<Post>> getPostById(@Query("id") long id);

        @GET(Contants.API_REPLY_ID)
        Observable<List<Reply>> getReplyListById(@Query("topic_id") long id);

        @GET(Contants.API_MEMBER_DETAIL)
        Observable<MemberDetail> getMemberDetailByUsername(@Query("username") String username);
    }

    public static String getOnceStringFromHtmlResponseObject(String content) {
        Pattern pattern = Pattern.compile("<input ty    pe=\"hidden\" value=\"([0-9]+)\" name=\"once\" />");
        final Matcher matcher = pattern.matcher(content);
        if (matcher.find())
            return matcher.group(1);
        return null;
    }

    public static String[] getUrlFromContent(String content) {
        Pattern p = Pattern.compile("^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$",
                Pattern.CASE_INSENSITIVE);
        final Matcher matcher = p.matcher(content);
        if (matcher.find()){
            
        }
        return null;
    }

}

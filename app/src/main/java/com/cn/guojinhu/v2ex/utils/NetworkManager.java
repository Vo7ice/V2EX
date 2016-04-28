package com.cn.guojinhu.v2ex.utils;


import com.cn.guojinhu.v2ex.login.LoginPresenter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;

import java.io.IOException;
import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

public class NetworkManager {
    public static NetworkManager networkManager;
    private OkHttpClient client;
    private Handler mainHandler;


    private NetworkManager() {
        client = new OkHttpClient();
        CookieManager manager = new CookieManager();
        client.setConnectTimeout(3, TimeUnit.SECONDS);
        client.setReadTimeout(3, TimeUnit.SECONDS);
        client.setWriteTimeout(3, TimeUnit.SECONDS);
        client.setCookieHandler(manager);
        client.setFollowRedirects(false);
        client.getCookieHandler();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public static NetworkManager getInstance() {
        if (networkManager == null) {
            networkManager = new NetworkManager();
        }
        return networkManager;
    }

    public Request.Builder builder() {
        Request.Builder builder;
        builder = new Request.Builder()
                .addHeader("Cache-Control", "max-age=0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Accept-Charset", "utf-8,ios-8859-1,utf-16 *;q=0.7")
                .addHeader("Accept-Language", "zh-CN,en-US")
                .addHeader("Host", "v2ex.com")
                .addHeader("X-Requested-With", "com.android.browser")
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.2.1; en-us; M040 Build/JOP40D) " +
                        "AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");

        return builder;
    }

    public void request(Request request, final Callback callback){
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback!=null){
                            callback.onFailure(request,e);
                        }
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (null != callback){
                            try {
                                callback.onResponse(response);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    public void requestString(Request request, final LoginPresenter.OnLoadComplete<String> loadComplete) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadComplete.loadComplete(null);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String result = response.body().string();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadComplete.loadComplete(result);
                    }
                });
            }
        });
    }


}

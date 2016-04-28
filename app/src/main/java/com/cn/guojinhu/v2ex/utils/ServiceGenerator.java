package com.cn.guojinhu.v2ex.utils;


import com.cn.guojinhu.v2ex.api.Contants;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class ServiceGenerator {

    private static OkHttpClient client = new OkHttpClient();

    private ServiceGenerator() {
    }

    private static Retrofit.Builder builder(String baseUrl) {
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder(Contants.BASE_PROTOCOL).client(client).build();
        return retrofit.create(serviceClass);
    }

}

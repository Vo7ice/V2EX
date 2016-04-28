package com.cn.guojinhu.v2ex.login;

import android.os.AsyncTask;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.cn.guojinhu.v2ex.api.Contants;
import com.cn.guojinhu.v2ex.data.Stats;
import com.cn.guojinhu.v2ex.utils.HttpUtils;
import com.cn.guojinhu.v2ex.utils.NetworkManager;
import com.cn.guojinhu.v2ex.utils.ServiceGenerator;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mLoginView;

    private static final int SUCESS = 1;
    private MyHandler mHandler;

    public LoginPresenter(LoginContract.View loginView) {
        mLoginView = checkNotNull(loginView);
        loginView.setPresenter(this);
        mHandler = new MyHandler(this);
    }

    @Override
    public void start() {
        HttpUtils.V2EXService service = ServiceGenerator.createService(HttpUtils.V2EXService.class);
        Call<Stats> stats = service.getStats();
        stats.enqueue(new Callback<Stats>() {
            @Override
            public void onResponse(Response<Stats> response, Retrofit retrofit) {
                Stats stats = response.body();
                Log.i("Vo7ice", "stats:" + stats.toString());
                Message msg = mHandler.obtainMessage();
                msg.what = SUCESS;
                msg.obj = stats;
                msg.sendToTarget();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    @Override
    public void attemptLogin() {
        final String username = mLoginView.getUsername();
        final String password = mLoginView.getPassword();
        Map<String, String> params = new HashMap<>();
        params.put("next", "/");
        params.put("u", username);
        params.put("p", password);
        getOnce(new OnLoadComplete<String>() {
            @Override
            public void loadComplete(String s) {
                if (null != s){
                    Log.i("Vo7ice", "once0:" + s);
                    /*Request.Builder builder = NetworkManager.getInstance().builder();
                    Request request =builder.url("http://v2ex.com/signin").get().build();
                    NetworkManager.getInstance().requestString(request, new OnLoadComplete<String>() {
                        @Override
                        public void loadComplete(String s) {
                            Log.i("Vo7ice", "once1:" + HttpUtils.getOnceStringFromHtmlResponseObject(s));
                        }
                    });*/
                    RequestBody body = new FormEncodingBuilder()
                            .add("next","/")
                            .add("u", username)
                            .add("p", password)
                            .add("once", s)
                            .build();
                    HttpUtils.V2EXService service = ServiceGenerator.createService(HttpUtils.V2EXService.class);
                    Call<ResponseBody> signin = service.signin(username,password,"/",s);
                    signin.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                            Log.i("Vo7ice","respnse:"+response.code());
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
//                    final Request request = NetworkManager.getInstance().builder()
//                            .addHeader("Origin","http://v2ex.com")
//                            .addHeader("Referer","http://v2ex.com/signin")
//                            .addHeader("Content-Type","application/x-www-form-urlencoded")
//                            .url("http://v2ex.com/signin")
//                            .post(body)
//                            .build();
//                    NetworkManager.getInstance().request(request, new com.squareup.okhttp.Callback() {
//                        @Override
//                        public void onFailure(Request request, IOException e) {
//                            Log.i("Vo7ice","onFailure");
//                        }
//
//                        @Override
//                        public void onResponse(com.squareup.okhttp.Response response) throws IOException {
//                            Log.i("Vo7ice","onResponse");
//                            Log.i("Vo7ice","code:"+response.code());
//                        }
//                    });

                }
            }
        });

    }

    public void getOnce(final OnLoadComplete<String> onLoadComplete){
        Request.Builder builder = NetworkManager.getInstance().builder();
        Request request =builder.url("http://v2ex.com/signin").get().build();
        NetworkManager.getInstance().requestString(request, new OnLoadComplete<String>() {
            @Override
            public void loadComplete(String s) {
                onLoadComplete.loadComplete(HttpUtils.getOnceStringFromHtmlResponseObject(s));
            }
        });
    }

    public interface OnLoadComplete<T> {
        void loadComplete(T t);
    }



    private void getDataFromServer(String username, String password, String once) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contants.BASE_PROTOCOL + Contants.API_SIGN_IN)
                .build();
        HttpUtils.V2EXService service = retrofit.create(HttpUtils.V2EXService.class);
        Map<String, String> params = new HashMap<>();
        params.put("next", "/");
        params.put("u", username);
        params.put("once", once);
        params.put("p", password);
//        Call<ResponseBody> signin = service.signin(params);
//        signin.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
//                try {
//                    Log.i("Vo7ice", "body:" + response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//
//            }
//        });


    }

    public static class MyHandler extends android.os.Handler {
        private final WeakReference<LoginPresenter> mPresenter;

        public MyHandler(LoginPresenter presenter) {
            this.mPresenter = new WeakReference<LoginPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginPresenter loginPresenter = mPresenter.get();
            if (null != loginPresenter) {
                if (msg.what == SUCESS) {
                    Stats stats = (Stats) msg.obj;
                    loginPresenter.mLoginView.showStats(stats);
                }
            }
        }
    }
}

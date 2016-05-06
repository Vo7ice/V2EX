package com.cn.guojinhu.v2ex.login;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import com.cn.guojinhu.v2ex.api.Contants;
import com.cn.guojinhu.v2ex.data.Stats;
import com.cn.guojinhu.v2ex.utils.HttpUtils;
import com.cn.guojinhu.v2ex.utils.NetworkManager;
import com.cn.guojinhu.v2ex.utils.ServiceGenerator;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mLoginView;

    private static final int SUCESS = 0;
    private static final int ERROR = -1;
    private static final int STATE = 1;
    private static final int SIGN_IN = 2;
    private static final int RETCODE_SUCESS = 302;
    private static final int RETCODE_FAILURE = 200;

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
                msg.arg1 = STATE;
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
        Log.i("Vo7ice", "start network");
        getOnce(new OnLoadComplete<String>() {
            @Override
            public void loadComplete(String s) {
                if (null != s) {
                    Log.i("Vo7ice", "once0:" + s);
                    RequestBody body = new FormEncodingBuilder()
                            .add("next", "/")
                            .add("u", username)
                            .add("p", password)
                            .add("once", s)
                            .build();
                    Request.Builder builder = NetworkManager.getInstance().builder();
                    final Request request = builder.url("http://v2ex.com/signin").post(body).build();
                    NetworkManager.getInstance().request(request, new com.squareup.okhttp.Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            Message msg = mHandler.obtainMessage();
                            msg.what = ERROR;
                            msg.sendToTarget();
                        }

                        @Override
                        public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                            Log.i("Vo7ice", "response:" + response.code());
                            Message msg = mHandler.obtainMessage();
                            msg.what = SUCESS;
                            msg.arg1 = SIGN_IN;
                            msg.arg2 = response.code();
                            msg.sendToTarget();
                        }
                    });
                }
            }
        });

    }

    public void getOnce(final OnLoadComplete<String> onLoadComplete) {
        Request.Builder builder = NetworkManager.getInstance().builder();
        Request request = builder.url("http://v2ex.com/signin").get().build();
        NetworkManager.getInstance().requestString(request, new OnLoadComplete<String>() {
            @Override
            public void loadComplete(String s) {
                Log.i("Vo7ice", "raw:" + s);
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
    }

    public static class MyAsyn extends AsyncTask<Void, Void, String> {
        HttpUtils.V2EXService service = ServiceGenerator.createService(HttpUtils.V2EXService.class);
        private String username;
        private String password;

        public MyAsyn(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            service.getSignin().enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                    try {
                        String result = response.body().string();
                        String once = HttpUtils.getOnceStringFromHtmlResponseObject(result);
                        Log.i("Vo7ice", "once:" + once);
                        service.postSignin(username, password, "/", once).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                                Log.i("Vo7ice", "response code:" + response.code());
                                Log.i("Vo7ice", "request:" + response.raw().request().body().toString());
                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(String once) {

        }
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
                    switch (msg.arg1) {
                        case STATE:
                            Stats stats = (Stats) msg.obj;
                            loginPresenter.mLoginView.showStats(stats);
                            break;
                        case SIGN_IN:

                            break;
                    }

                }
            }
        }
    }
}

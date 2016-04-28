package com.cn.guojinhu.v2ex.hot;


import android.app.Activity;
import android.os.Message;
import android.view.Menu;

import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.api.Contants;
import com.cn.guojinhu.v2ex.data.Member;
import com.cn.guojinhu.v2ex.data.Node;
import com.cn.guojinhu.v2ex.data.Post;
import com.cn.guojinhu.v2ex.utils.HttpUtils;
import com.cn.guojinhu.v2ex.utils.ServiceGenerator;

import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class HotPresenter implements HotContract.Presenter {

    private HotContract.View mHotView;
    private MyHandler mHandler;

    private static boolean needShow = true;

    private static final int SUCESS = 1;
    private static final int FAILURE = 0;


    public HotPresenter(HotContract.View hotView) {
        mHotView = hotView;
        mHotView.setPresenter(this);

        mHandler = new MyHandler(this);
    }

    @Override
    public void start() {
        mHotView.showProgress(needShow);
        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils.V2EXService service = ServiceGenerator.createService(HttpUtils.V2EXService.class);
        Call<List<Post>> call = service.listHotPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Response<List<Post>> response, Retrofit retrofit) {
                List<Post> hotPosts = response.body();
                Message msg = mHandler.obtainMessage();
                needShow = false;
                msg.what = SUCESS;
                msg.obj = hotPosts;
                msg.sendToTarget();
            }

            @Override
            public void onFailure(Throwable t) {
                Message msg = mHandler.obtainMessage();
                msg.what = FAILURE;
                msg.sendToTarget();
            }
        });
    }

    @Override
    public void setMenuVisible(Activity activity, Menu menu) {
        if (activity.getClass().getName().equals(HotActivity.class.getName())) {
            menu.findItem(R.id.action_hot).setVisible(false);
        } else {
            menu.findItem(R.id.action_hot).setVisible(true);
        }
    }


    /**
     * open post detail
     *
     * @param post
     */
    @Override
    public void openPostDetail(Post post) {
        mHotView.showPostDetailUI(post);
    }

    /**
     * open member detail
     *
     * @param member
     */
    @Override
    public void openMemberDetail(Member member) {
        mHotView.showMemberDetailUI(member);
    }

    /**
     * open node detail
     *
     * @param node
     */
    @Override
    public void openNodeDetail(Node node) {
        mHotView.showNodeDetailUI(node);
    }

    public static class MyHandler extends android.os.Handler {
        private final WeakReference<HotPresenter> mPresenter;

        public MyHandler(HotPresenter presenter) {
            this.mPresenter = new WeakReference<HotPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            HotPresenter hotPresenter = mPresenter.get();
            if (null != hotPresenter) {
                if (msg.what == SUCESS) {
                    hotPresenter.mHotView.showProgress(needShow);
                    List<Post> hotPosts = (List<Post>) msg.obj;
                    hotPresenter.mHotView.showPosts(hotPosts);

                }
            }
        }
    }
}

package com.cn.guojinhu.v2ex.lastest;

import android.app.Activity;
import android.os.Message;
import android.util.Log;
import android.view.Menu;

import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.data.Member;
import com.cn.guojinhu.v2ex.data.Node;
import com.cn.guojinhu.v2ex.data.Post;
import com.cn.guojinhu.v2ex.utils.HttpUtils;
import com.cn.guojinhu.v2ex.utils.ServiceGenerator;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;


public class LastestPresenter implements LastestContract.Presenter {

    private static final String TAG = "LastestPresenter";

    private LastestContract.View mLastestView;

    private MyHandler mHandler;
    private static boolean needShow = true;

    private static final int SUCESS = 1;
    private static final int FAILURE = 0;

    public LastestPresenter(LastestContract.View lastestView) {
        mLastestView = checkNotNull(lastestView, "mLastestView cannot be null!");

        mLastestView.setPresenter(this);
        mHandler = new MyHandler(this);
    }

    @Override
    public void start() {
        mLastestView.showProgress(needShow);
        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils.V2EXService service = ServiceGenerator.createService(HttpUtils.V2EXService.class);
        Call<List<Post>> call = service.listPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Response<List<Post>> response, Retrofit retrofit) {
                List<Post> posts = response.body();
                needShow = false;
                Log.i(TAG, "mPostList:" + posts);
                Log.i(TAG,"pst:"+(posts.get(0).member==null));
                Log.i(TAG,"pst:"+(posts.get(0).node==null));
                Log.i(TAG,"pst:"+posts.get(0).node);
                Message msg = mHandler.obtainMessage();
                msg.what = SUCESS;
                msg.obj = posts;
                msg.sendToTarget();
            }

            @Override
            public void onFailure(Throwable t) {
                Message msg = mHandler.obtainMessage();
                msg.what = FAILURE;
                msg.sendToTarget();
            }
        });

        /*HttpUtils.V2EXService service1 = ServiceGenerator.createService(HttpUtils.V2EXService.class);

        Call<List<Nodes>> allNodes = service1.getAllNodes();
        allNodes.enqueue(new Callback<List<Nodes>>() {
            @Override
            public void onResponse(Response<List<Nodes>> response, Retrofit retrofit) {
                List<Nodes> nodes = response.body();
                for (Nodes n : nodes) {
                    Log.d("Vo7ice","node:"+n);
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("Vo7ice", "onFailure");
            }
        });*/
    }

    @Override
    public void setMenuVisible(Activity activity, Menu menu) {
        if (activity.getClass().getName().equals(LastestActivity.class.getName())) {
            menu.findItem(R.id.action_lastest).setVisible(false);
        } else {
            menu.findItem(R.id.action_lastest).setVisible(true);
        }

    }

    /**
     * open post detail
     *
     * @param post
     */
    @Override
    public void openPostDetail(Post post) {
        mLastestView.showPostDetailUI(post);
    }

    /**
     * open member detail
     *
     * @param member
     */
    @Override
    public void openMemberDetail(Member member) {
        mLastestView.showMemberDetailUI(member);
    }

    /**
     * open node detail
     *
     * @param node
     */
    @Override
    public void openNodeDetail(Node node) {
        mLastestView.showNodeDetailUI(node);
    }

    public static class MyHandler extends android.os.Handler {
        private final WeakReference<LastestPresenter> mPresenter;

        public MyHandler(LastestPresenter presenter) {
            this.mPresenter = new WeakReference<LastestPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            LastestPresenter lastestPresenter = mPresenter.get();
            if (null != lastestPresenter) {
                lastestPresenter.mLastestView.showProgress(needShow);
                if (msg.what == SUCESS) {
                    List<Post> posts = (List<Post>) msg.obj;
                    lastestPresenter.mLastestView.showPosts(posts);
                }
            }
        }
    }

}

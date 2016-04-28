package com.cn.guojinhu.v2ex.postDetail;


import android.util.Log;
import android.view.View;

import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.data.Post;
import com.cn.guojinhu.v2ex.data.Reply;
import com.cn.guojinhu.v2ex.utils.HttpUtils;
import com.cn.guojinhu.v2ex.utils.ServiceGenerator;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;

public class PostDetailPresenter implements PostDetailContract.Presenter {

    private PostDetailContract.View mPostDetailView;

    public PostDetailPresenter(PostDetailContract.View postDetailView) {
        mPostDetailView = postDetailView;

        mPostDetailView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void start(Post post) {
        mPostDetailView.showProgressbar(true);
        HttpUtils.V2EXService service = ServiceGenerator.createService(HttpUtils.V2EXService.class);
        /*final Call<List<Reply>> replyList = service.getReplyListById(post.id);
        replyList.enqueue(new Callback<List<Reply>>() {
            @Override
            public void onResponse(Response<List<Reply>> response, Retrofit retrofit) {
                List<Reply> replies = response.body();
                Log.i("Vo7ice", "size:" + replies.size());
                for (Reply r : replies) {
                    Log.i("Vo7ice","r::::"+r);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });*/
        service.getReplyListById(post.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Reply>>() {
                    @Override
                    public void onCompleted() {
                        mPostDetailView.showProgressbar(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPostDetailView.showProgressbar(false);
                        mPostDetailView.showSnackBar(R.string.error_message);
                    }

                    @Override
                    public void onNext(List<Reply> replyList) {
                        mPostDetailView.showProgressbar(false);
                        Log.i("Vo7ice", "size:" + replyList.size());
                        mPostDetailView.replaceData(replyList);
                        int visibility = mPostDetailView.getHeaderVisibility();
                        Log.i("Vo7ice","visibility:"+visibility);
                    }
                });
    }
}

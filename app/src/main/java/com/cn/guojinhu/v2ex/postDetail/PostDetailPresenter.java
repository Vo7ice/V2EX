package com.cn.guojinhu.v2ex.postDetail;


import android.util.Log;
import android.view.View;

import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.data.Member;
import com.cn.guojinhu.v2ex.data.Node;
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

import static com.google.common.base.Preconditions.checkNotNull;

public class PostDetailPresenter implements PostDetailContract.Presenter {

    private PostDetailContract.View mPostDetailView;
    private Post mPost;
    private Member mMember;
    private Node mNode;

    public PostDetailPresenter(PostDetailContract.View postDetailView, Post post, Member member, Node node) {
        mPostDetailView = checkNotNull(postDetailView,"mPostDetailView cannot be null");
        mPost = checkNotNull(post);
        mMember = checkNotNull(member);
        mNode = checkNotNull(node);

        mPostDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        mPostDetailView.showProgressbar(true);
        HttpUtils.V2EXService service = ServiceGenerator.createService(HttpUtils.V2EXService.class);
        service.getReplyListById(mPost.id)
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

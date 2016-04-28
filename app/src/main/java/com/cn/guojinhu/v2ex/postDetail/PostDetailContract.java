package com.cn.guojinhu.v2ex.postDetail;


import android.view.View;

import com.cn.guojinhu.v2ex.Base.BasePresenter;
import com.cn.guojinhu.v2ex.Base.BaseView;
import com.cn.guojinhu.v2ex.data.Post;
import com.cn.guojinhu.v2ex.data.Reply;

import java.util.List;

public class PostDetailContract {

    interface View extends BaseView<Presenter> {
        void showUI(Post post);

        android.view.View setHeadView();

        void replaceData(List<Reply> replyList);

        void showProgressbar(boolean needShow);

        void showSnackBar(int resId);

        int getHeaderVisibility();
    }

    interface Presenter extends BasePresenter {
        void start(Post post);
    }
}

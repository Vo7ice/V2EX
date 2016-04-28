package com.cn.guojinhu.v2ex.hot;

import android.app.Activity;
import android.view.Menu;

import com.cn.guojinhu.v2ex.Base.BasePresenter;
import com.cn.guojinhu.v2ex.Base.BaseView;
import com.cn.guojinhu.v2ex.data.Member;
import com.cn.guojinhu.v2ex.data.Node;
import com.cn.guojinhu.v2ex.data.Post;

import java.util.List;

public class HotContract {
    interface View extends BaseView<Presenter> {
        void showProgress(boolean needShow);

        void showPosts(List<Post> postList);

        void showPostDetailUI(Post post);

        void showMemberDetailUI(Member member);

        void showNodeDetailUI(Node node);
    }

    interface Presenter extends BasePresenter {
        void setMenuVisible(Activity activity,Menu menu);

        void openPostDetail(Post post);

        void openMemberDetail(Member member);

        void openNodeDetail(Node node);
    }
}

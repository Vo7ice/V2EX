package com.cn.guojinhu.v2ex.memberDetail;

import com.cn.guojinhu.v2ex.Base.BasePresenter;
import com.cn.guojinhu.v2ex.Base.BaseView;

public class MemberDetailContract {

    interface View extends BaseView<Presenter> {

        void showProgressbar(boolean needShow);

        void showSnackBar(int resId);
    }

    interface Presenter extends BasePresenter {

    }
}


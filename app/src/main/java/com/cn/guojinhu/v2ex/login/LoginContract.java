package com.cn.guojinhu.v2ex.login;

import com.cn.guojinhu.v2ex.Base.BasePresenter;
import com.cn.guojinhu.v2ex.Base.BaseView;
import com.cn.guojinhu.v2ex.data.Stats;


public class LoginContract {

    interface View extends BaseView<Presenter> {
        void setMembers(String members);

        void setTopics(String topics);

        void showUI(boolean show);

        void setUsernameError(int ResId);

        void setPasswordError(int ResId);

        void showStats(Stats stats);

        String getUsername();

        String getPassword();

    }

    interface Presenter extends BasePresenter {
        void attemptLogin();
    }
}

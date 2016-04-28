package com.cn.guojinhu.v2ex.memberDetail;

public class MemberDetailPresenter implements MemberDetailContract.Presenter {

    private MemberDetailContract.View mMemberDetailView;

    public MemberDetailPresenter(MemberDetailContract.View memberDetailView) {
        mMemberDetailView = memberDetailView;
        mMemberDetailView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}

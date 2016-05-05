package com.cn.guojinhu.v2ex.memberDetail;

import android.util.Log;

import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.data.Member;
import com.cn.guojinhu.v2ex.data.MemberDetail;
import com.cn.guojinhu.v2ex.utils.HttpUtils;
import com.cn.guojinhu.v2ex.utils.ServiceGenerator;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

public class MemberDetailPresenter implements MemberDetailContract.Presenter {

    private MemberDetailContract.View mMemberDetailView;
    private Member mMember;

    public MemberDetailPresenter(MemberDetailContract.View memberDetailView, Member member) {
        mMemberDetailView = checkNotNull(memberDetailView);
        mMember = checkNotNull(member);

        mMemberDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        HttpUtils.V2EXService service = ServiceGenerator.createService(HttpUtils.V2EXService.class);
        service.getMemberDetailByUsername(mMember.username)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Subscriber<MemberDetail>() {
                   @Override
                   public void onCompleted() {
                       mMemberDetailView.showProgressbar(false);
                   }

                   @Override
                   public void onError(Throwable e) {
                       mMemberDetailView.showProgressbar(false);
                       mMemberDetailView.showSnackBar(R.string.error_message);
                   }

                   @Override
                   public void onNext(MemberDetail memberDetail) {
                       mMemberDetailView.showProgressbar(false);
                       Log.i("Vo7ice","detail:"+memberDetail);
                   }
               });
    }
}

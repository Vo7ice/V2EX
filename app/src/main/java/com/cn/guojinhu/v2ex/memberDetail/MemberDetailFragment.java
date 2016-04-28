package com.cn.guojinhu.v2ex.memberDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cn.guojinhu.v2ex.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by guojin.hu on 2016/4/22.
 */
public class MemberDetailFragment extends Fragment implements MemberDetailContract.View {


    private static MemberDetailFragment instance;
    private MemberDetailContract.Presenter mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static MemberDetailFragment newInstance() {
        if (null == instance) {
            instance = new MemberDetailFragment();
        }
        return instance;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member_detail, container, false);

        return rootView;
    }

    @Override
    public void setPresenter(MemberDetailContract.Presenter p) {
        mPresenter = checkNotNull(p);
    }
}

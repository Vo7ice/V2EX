package com.cn.guojinhu.v2ex.memberDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cn.guojinhu.v2ex.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class MemberDetailFragment extends Fragment implements MemberDetailContract.View {


    private static MemberDetailFragment instance;
    private MemberDetailContract.Presenter mPresenter;


    private View rootView;
    private ProgressBar progressBar;

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
        rootView = inflater.inflate(R.layout.fragment_member_detail, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        return rootView;
    }

    @Override
    public void setPresenter(MemberDetailContract.Presenter p) {
        mPresenter = checkNotNull(p);
    }

    @Override
    public void showProgressbar(boolean needShow) {
        progressBar.setVisibility(needShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showSnackBar(int resId) {
        Snackbar.make(rootView, resId, Snackbar.LENGTH_LONG).show();
    }
}

package com.cn.guojinhu.v2ex.nodeDetail;

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
public class NodeDetailFragment extends Fragment implements NodeDetailContract.View {


    private static NodeDetailFragment instance;
    private NodeDetailContract.Presenter mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static NodeDetailFragment newInstance() {
        if (null == instance) {
            instance = new NodeDetailFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_node_detail, container, false);

        return rootView;
    }

    @Override
    public void setPresenter(NodeDetailContract.Presenter p) {
        mPresenter = checkNotNull(p);
    }
}

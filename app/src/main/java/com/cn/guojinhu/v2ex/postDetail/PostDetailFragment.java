package com.cn.guojinhu.v2ex.postDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.adapter.ReplyAdapter;
import com.cn.guojinhu.v2ex.data.Member;
import com.cn.guojinhu.v2ex.data.Node;
import com.cn.guojinhu.v2ex.data.Post;
import com.cn.guojinhu.v2ex.data.Reply;
import com.cn.guojinhu.v2ex.widget.RecyclerViewHeader;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class PostDetailFragment extends Fragment implements PostDetailContract.View {

    private static PostDetailFragment instance;
    private PostDetailContract.Presenter mPresenter;
    private View rootView;
    private ProgressBar progressBar;
    private RecyclerView recycler_detail;
    private RecyclerViewHeader header;

    private ReplyAdapter mAdapter;

    private static final String KEY_POST = "post";
    private static final String KEY_MEMBER = "member";
    private static final String KEY_NODE = "node";

    private Post post;
    private Member member;
    private Node node;

    @Override
    public void setPresenter(PostDetailContract.Presenter p) {
        mPresenter = checkNotNull(p);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            post = getArguments().getParcelable(KEY_POST);
            member = getArguments().getParcelable(KEY_MEMBER);
            node = getArguments().getParcelable(KEY_NODE);
        }
        mAdapter = new ReplyAdapter(getActivity(),new ArrayList<Reply>());
    }

    public static PostDetailFragment newInstance(Post post, Node node, Member member) {
        if (null == instance) {
            instance = new PostDetailFragment();
        }
        Bundle args = new Bundle();
        args.putParcelable(KEY_POST, post);
        args.putParcelable(KEY_MEMBER, member);
        args.putParcelable(KEY_NODE, node);
        instance.setArguments(args);
        return instance;
    }

    public static PostDetailFragment newInstance(Post post) {
        if (null == instance) {
            instance = new PostDetailFragment();
        }
        Bundle args = new Bundle();
        args.putParcelable(KEY_POST, post);
        instance.setArguments(args);
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
        rootView = inflater.inflate(R.layout.fragment_post_detail, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        recycler_detail = (RecyclerView) rootView.findViewById(R.id.recyler_detail);
        recycler_detail.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler_detail.setAdapter(mAdapter);
        header = (RecyclerViewHeader) rootView.findViewById(R.id.header);
        header.attachTo(recycler_detail);
        return rootView;
    }

    @Override
    public void showUI(Post post) {

    }

    @Override
    public View setHeadView() {
        return View.inflate(getActivity(), R.layout.detail_top, null);
    }

    @Override
    public void replaceData(List<Reply> replyList) {
        mAdapter.replaceData(replyList);
    }

    @Override
    public void showProgressbar(boolean needShow) {
        progressBar.setVisibility(needShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showSnackBar(int resId) {
        Snackbar.make(rootView, resId, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public int getHeaderVisibility() {
        return header.getVisibility();
    }
}

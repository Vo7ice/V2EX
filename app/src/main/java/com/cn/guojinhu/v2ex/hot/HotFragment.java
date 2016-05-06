package com.cn.guojinhu.v2ex.hot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.adapter.PostAdapter;
import com.cn.guojinhu.v2ex.data.Member;
import com.cn.guojinhu.v2ex.data.Node;
import com.cn.guojinhu.v2ex.data.Post;
import com.cn.guojinhu.v2ex.utils.PostItemListener;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class HotFragment extends Fragment implements HotContract.View {

    private static HotFragment instance;

    private HotContract.Presenter mPresenter;

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;

    public static final String KEY_POST = "post";
    public static final String KEY_NODE = "node";
    public static final String KEY_MEMBER = "member";

    public HotFragment() {

    }

    public static HotFragment newInstance() {
        if (null == instance) {
            instance = new HotFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new PostAdapter(new ArrayList<Post>(), getActivity(),mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    /**
     * click for post recyclerview
     */
    PostItemListener mItemListener = new PostItemListener() {
        @Override
        public void onPostItemClick(Post post) {
            mPresenter.openPostDetail(post);
        }

        @Override
        public void onPostMemberClick(Member member) {
            mPresenter.openMemberDetail(member);
        }

        @Override
        public void onPostNodeClick(Node node) {
            mPresenter.openNodeDetail(node);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hot, container, false);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyler_hot);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void setPresenter(HotContract.Presenter p) {
        mPresenter = checkNotNull(p);
    }

    @Override
    public void showProgress(boolean needShow) {
        mProgressBar.setVisibility(needShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showPosts(List<Post> postList) {
        mAdapter.replaceData(postList);
    }

    @Override
    public void showPostDetailUI(Post post) {
        Intent intent = new Intent("android.intent.action.POST_DETAIL");
        intent.putExtra(KEY_POST,post);
        intent.putExtra(KEY_MEMBER,post.member);
        intent.putExtra(KEY_NODE,post.node);
        startActivity(intent);
    }

    @Override
    public void showMemberDetailUI(Member member) {
        Intent intent = new Intent("android.intent.action.MEMBER_DETAIL");
        intent.putExtra(KEY_MEMBER,member);
        startActivity(intent);
    }

    @Override
    public void showNodeDetailUI(Node node) {
        Intent intent = new Intent("android.intent.action.NODE_DETAIL");
        startActivity(intent);
    }
}

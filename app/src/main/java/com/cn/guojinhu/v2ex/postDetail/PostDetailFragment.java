package com.cn.guojinhu.v2ex.postDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.adapter.ReplyAdapter;
import com.cn.guojinhu.v2ex.data.Member;
import com.cn.guojinhu.v2ex.data.Node;
import com.cn.guojinhu.v2ex.data.Post;
import com.cn.guojinhu.v2ex.data.Reply;
import com.cn.guojinhu.v2ex.utils.BitmapUtils;
import com.cn.guojinhu.v2ex.utils.DateUtils;
import com.cn.guojinhu.v2ex.utils.PostItemListener;
import com.cn.guojinhu.v2ex.utils.ReplyItemListener;

import java.util.ArrayList;
import java.util.List;

import space.sye.z.library.RefreshRecyclerView;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;

import static com.google.common.base.Preconditions.checkNotNull;


public class PostDetailFragment extends Fragment implements PostDetailContract.View {

    private static PostDetailFragment instance;
    private PostDetailContract.Presenter mPresenter;
    private View rootView;
    private ProgressBar progressBar;
    private RefreshRecyclerView recycler_detail;
    private View header;

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
        mAdapter = new ReplyAdapter(getActivity(), new ArrayList<Reply>(),mItemListener);
    }


    /**
     * click for post recyclerview
     */
    ReplyItemListener mItemListener = new ReplyItemListener(){

        @Override
        public void onReplyMemberClick(Member member) {
            mPresenter.openMemberDetail(member);
        }
    };


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
        recycler_detail = (RefreshRecyclerView) rootView.findViewById(R.id.recyler_detail);
        //recycler_detail.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //recycler_detail.setAdapter(mAdapter);
        header = View.inflate(getActivity(), R.layout.detail_top, null);
        initHeader(header);
        RecyclerViewManager.with(mAdapter, new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false))
                .setMode(RecyclerMode.NONE)
                .addHeaderView(header, 0)
                .into(recycler_detail, getActivity());
        return rootView;
    }


    @Override
    public void showUI(Post post, Member member) {
        text_title.setText(post.title);
        text_content.setText(post.content);
        text_name.setText(member.username);
        text_modify.setText(DateUtils.longToDate(getActivity(), post.last_modified));
        text_reply.setText(DateUtils.replies(getActivity(), post.replies));
        BitmapUtils.display(getActivity(), image_avatar, member.avatar_large);
    }

    private TextView text_title, text_content, text_name, text_modify, text_reply;
    private ImageView image_avatar;

    private void initHeader(View header) {
        text_title = (TextView) header.findViewById(R.id.text_title);
        text_content = (TextView) header.findViewById(R.id.text_content);
        text_name = (TextView) header.findViewById(R.id.text_name);
        text_modify = (TextView) header.findViewById(R.id.text_modify);
        text_reply = (TextView) header.findViewById(R.id.text_replies);
        image_avatar = (ImageView) header.findViewById(R.id.image_avatar);
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

    @Override
    public void showMemberDetailUI(Member member) {
        Intent intent = new Intent("android.intent.action.MEMBER_DETAIL");
        startActivity(intent);
    }
}

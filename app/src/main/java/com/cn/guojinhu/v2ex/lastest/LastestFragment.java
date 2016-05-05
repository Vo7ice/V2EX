package com.cn.guojinhu.v2ex.lastest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


public class LastestFragment extends Fragment implements LastestContract.View{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String KEY_POST = "post";
    public static final String KEY_NODE = "node";
    public static final String KEY_MEMBER = "member";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LastestContract.Presenter mPresenter;

    private static LastestFragment instance;

    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;

    public LastestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LastestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LastestFragment newInstance(String param1, String param2) {
        LastestFragment fragment = new LastestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static LastestFragment newInstance() {
        if (null == instance) {
            instance= new LastestFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAdapter = new PostAdapter(new ArrayList<Post>(), getActivity(),mItemListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lastest, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyler_lastest);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
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

    @Override
    public void showProgress(boolean needShow) {
        progressBar.setVisibility(needShow ? View.VISIBLE : View.GONE);
    }


    @Override
    public void showPosts(List<Post> postList) {
        mAdapter.replaceData(postList);
        Log.i("Vo7ice", "showPosts");
        mRecyclerView.setVisibility(View.VISIBLE);
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

    @Override
    public void setPresenter(@NonNull LastestContract.Presenter p) {
        mPresenter = checkNotNull(p);
    }


}

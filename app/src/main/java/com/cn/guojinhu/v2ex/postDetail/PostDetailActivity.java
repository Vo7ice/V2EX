package com.cn.guojinhu.v2ex.postDetail;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.data.Member;
import com.cn.guojinhu.v2ex.data.Node;
import com.cn.guojinhu.v2ex.data.Post;
import com.cn.guojinhu.v2ex.utils.ActivityUtils;


public class PostDetailActivity extends AppCompatActivity {

    private static final String KEY_POST = "post";
    private static final String KEY_MEMBER = "member";
    private static final String KEY_NODE = "node";

    private PostDetailPresenter mPresenter;
    private Post post;
    private Member member;
    private Node node;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (null != intent){
            post = intent.getParcelableExtra(KEY_POST);
            member = intent.getParcelableExtra(KEY_MEMBER);
            node = intent.getParcelableExtra(KEY_NODE);
            Log.i("Vo7ice","postdetailActivity:"+post);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(actionBar.getTitle() + " -> " + node.title);

        PostDetailFragment postDetailFragment =
                (PostDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == postDetailFragment){
            postDetailFragment = PostDetailFragment.newInstance(post,node,member);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), postDetailFragment, R.id.contentFrame);
        }


        mPresenter = new PostDetailPresenter(postDetailFragment,post,member,node);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_reply) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

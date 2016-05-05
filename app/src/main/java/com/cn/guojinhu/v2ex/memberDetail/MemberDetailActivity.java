package com.cn.guojinhu.v2ex.memberDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.data.Member;
import com.cn.guojinhu.v2ex.utils.ActivityUtils;

public class MemberDetailActivity extends AppCompatActivity {


    private static final String KEY_MEMBER = "member";

    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MemberDetailFragment memberDetailFragment =
                (MemberDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        Intent intent = getIntent();
        if (null != intent){
            member = intent.getParcelableExtra(KEY_MEMBER);
            Log.i("Vo7ice", "MemberDetailActivity:" + member);
        }

        if (null == memberDetailFragment){
            memberDetailFragment = MemberDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), memberDetailFragment, R.id.contentFrame);
        }

        new MemberDetailPresenter(memberDetailFragment,member);
    }
}

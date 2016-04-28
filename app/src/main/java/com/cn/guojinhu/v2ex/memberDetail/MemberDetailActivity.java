package com.cn.guojinhu.v2ex.memberDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.utils.ActivityUtils;

public class MemberDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MemberDetailFragment memberDetailFragment =
                (MemberDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == memberDetailFragment){
            memberDetailFragment = MemberDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), memberDetailFragment, R.id.contentFrame);
        }

        new MemberDetailPresenter(memberDetailFragment);
    }
}

package com.cn.guojinhu.v2ex.nodeDetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.memberDetail.MemberDetailFragment;
import com.cn.guojinhu.v2ex.memberDetail.MemberDetailPresenter;
import com.cn.guojinhu.v2ex.utils.ActivityUtils;

public class NodeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_detail);

        NodeDetailFragment nodeDetailFragment =
                (NodeDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == nodeDetailFragment){
            nodeDetailFragment = NodeDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), nodeDetailFragment, R.id.contentFrame);
        }

        new NodeDetailPresenter(nodeDetailFragment);
    }
}

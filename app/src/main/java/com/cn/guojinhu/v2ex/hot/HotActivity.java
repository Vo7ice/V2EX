package com.cn.guojinhu.v2ex.hot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.lastest.LastestActivity;
import com.cn.guojinhu.v2ex.utils.ActivityUtils;

public class HotActivity extends AppCompatActivity {

    private HotPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HotFragment hotFragment = (HotFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == hotFragment){
            hotFragment = HotFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),hotFragment,R.id.contentFrame);
        }

        mPresenter =new HotPresenter(hotFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mPresenter.setMenuVisible(this,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_lastest) {
            startActivity(new Intent(HotActivity.this, LastestActivity.class));
            return true;
        }

        if (id == R.id.action_all_nodes) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.cn.guojinhu.v2ex.lastest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.cn.guojinhu.v2ex.hot.HotActivity;
import com.cn.guojinhu.v2ex.login.LoginActivity;
import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.data.Post;
import com.cn.guojinhu.v2ex.utils.ActivityUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LastestActivity extends AppCompatActivity {
    private static final String TAG = "LastestActivity";
    private FloatingActionButton fab;
    private LastestPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.sign_in, Snackbar.LENGTH_LONG)
                        .setAction(R.string.title_activity_login, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(LastestActivity.this, LoginActivity.class));
                            }
                        }).show();
            }
        });

        LastestFragment lastestFragment = (LastestFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == lastestFragment) {
            lastestFragment = LastestFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), lastestFragment, R.id.contentFrame);
            Log.i(TAG, "add done");
        }

        mPresenter = new LastestPresenter(lastestFragment);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*public class MyAsyn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return getDataFromServer(params[0]);
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            Log.i(TAG, "data:" + data);
            Gson gson = new Gson();
            Post mPost = new Post();
            Type type = new TypeToken<List<Post.Post>>() {
            }.getType();
            mPost.mPostList = gson.fromJson(data, type);
            Log.i(TAG, "mPostList:" + mPost);
        }
    }

    private String getDataFromServer(String httpurl) {
        String data = "";
        try {
            URL url = new URL(httpurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.connect();
            int responseCode = conn.getResponseCode();
            Log.i(TAG, "RESPONSECODE:" + responseCode);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                data += inputLine + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "error");
        }
        return data;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mPresenter.setMenuVisible(this, menu);
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


        if (id == R.id.action_hot) {
            startActivity(new Intent(LastestActivity.this, HotActivity.class));
            return true;
        }
        if (id == R.id.action_all_nodes) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

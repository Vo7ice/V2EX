package com.cn.guojinhu.v2ex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.guojinhu.v2ex.R;
import com.cn.guojinhu.v2ex.data.Post;
import com.cn.guojinhu.v2ex.utils.BitmapUtils;
import com.cn.guojinhu.v2ex.utils.DateUtils;
import com.cn.guojinhu.v2ex.utils.PostItemListener;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.LastestViewHolder> {

    private List<Post> mPostList;
    private Context mContext;
    private PostItemListener mPostItemListener;

    public PostAdapter(List<Post> postList, Context context) {
        setPosts(postList);
        mContext = context;
    }

    public PostAdapter(List<Post> postList, Context context, PostItemListener postItemListener) {
        setPosts(postList);
        mContext = context;
        mPostItemListener = postItemListener;
    }

    public void setPosts(List<Post> postList) {
        mPostList = checkNotNull(postList);
    }

    public void replaceData(List<Post> postList) {
        setPosts(postList);
        notifyDataSetChanged();
    }

    @Override
    public LastestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LastestViewHolder(LayoutInflater.from(mContext).inflate
                (R.layout.post_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(LastestViewHolder holder, int position) {
        final Post post = mPostList.get(position);

        holder.text_title.setText(post.title);
        holder.text_node_name.setText(post.node.title);
        holder.text_user_name.setText(post.member.username);
        Log.i("Vo7ice", "modify:" + post.last_modified + ",now:" + System.currentTimeMillis());
        holder.text_last_modify.setText(DateUtils.longToDate(mContext, post.last_modified));
        holder.text_replies.setText(String.valueOf(post.replies));
        Log.i("Vo7ice", "url:" + post.url + ",avatar:" + post.node);
        BitmapUtils.display(mContext, holder.image_avatar, post.member.avatar_large);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPostItemListener.onPostItemClick(post);
            }
        });

        holder.text_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPostItemListener.onPostMemberClick(post.member);
            }
        });

        holder.image_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPostItemListener.onPostMemberClick(post.member);
            }
        });

        holder.text_node_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPostItemListener.onPostNodeClick(post.node);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    public class LastestViewHolder extends RecyclerView.ViewHolder {

        public TextView text_title, text_node_name, text_user_name, text_last_modify, text_replies;
        public ImageView image_avatar;

        public LastestViewHolder(View itemView) {
            super(itemView);
            text_title = (TextView) itemView.findViewById(R.id.text_title);
            text_node_name = (TextView) itemView.findViewById(R.id.text_node_name);
            text_user_name = (TextView) itemView.findViewById(R.id.text_user_name);
            text_last_modify = (TextView) itemView.findViewById(R.id.text_last_modify);
            text_replies = (TextView) itemView.findViewById(R.id.text_replies);
            image_avatar = (ImageView) itemView.findViewById(R.id.image_avatar);
        }
    }

}

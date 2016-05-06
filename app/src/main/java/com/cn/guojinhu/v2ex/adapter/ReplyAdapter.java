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
import com.cn.guojinhu.v2ex.data.Member;
import com.cn.guojinhu.v2ex.data.Reply;
import com.cn.guojinhu.v2ex.utils.BitmapUtils;
import com.cn.guojinhu.v2ex.utils.ReplyItemListener;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder> {
    private Context mContext;
    private List<Reply> mReplyList;
    private ReplyItemListener mReplyItemListener;

    public ReplyAdapter(Context context, List<Reply> replyList) {
        mContext = context;
        setReplies(replyList);
    }

    public ReplyAdapter(Context context, List<Reply> replyList, ReplyItemListener replyItemListener) {
        mContext = context;
        setReplies(replyList);
        mReplyItemListener = replyItemListener;
    }

    private void setReplies(List<Reply> replyList) {
        mReplyList = checkNotNull(replyList);
    }

    public void replaceData(List<Reply> replyList) {
        setReplies(replyList);
        notifyDataSetChanged();
    }

    @Override
    public ReplyAdapter.ReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReplyViewHolder(LayoutInflater.from(mContext).inflate
                (R.layout.reply_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ReplyAdapter.ReplyViewHolder holder, int position) {
        final Reply reply = mReplyList.get(position);
        final Member member = reply.member;
        Log.i("Vo7ice", "reply:" + reply);
        holder.text_reply_name.setText(reply.member.username);
        Log.i("Vo7ice", "modify:" + reply.last_modified + ",now:" + System.currentTimeMillis());
        BitmapUtils.display(mContext, holder.image_avatar, reply.member.avatar_large);
        Log.i("Vo7ice", "thanks:" + reply.thanks);
        if (null != mReplyItemListener) {
            holder.text_reply_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mReplyItemListener.onReplyMemberClick(member);
                }
            });

            holder.image_avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mReplyItemListener.onReplyMemberClick(member);
                }
            });
        }
        holder.text_thanks.setText(String.valueOf(reply.thanks));
        holder.text_reply_content.setText(reply.content);
    }

    @Override
    public int getItemCount() {
        return mReplyList.size();
    }

    public class ReplyViewHolder extends RecyclerView.ViewHolder {

        public TextView text_thanks, text_reply_name, text_reply_create, text_reply_content;
        public ImageView image_avatar, image_reply;

        public ReplyViewHolder(View itemView) {
            super(itemView);
            text_thanks = (TextView) itemView.findViewById(R.id.text_thanks);
            text_reply_name = (TextView) itemView.findViewById(R.id.text_reply_name);
            text_reply_create = (TextView) itemView.findViewById(R.id.text_reply_create);
            text_reply_content = (TextView) itemView.findViewById(R.id.text_reply_content);
            image_avatar = (ImageView) itemView.findViewById(R.id.image_avatar);
            image_reply = (ImageView) itemView.findViewById(R.id.image_reply);
        }
    }
}

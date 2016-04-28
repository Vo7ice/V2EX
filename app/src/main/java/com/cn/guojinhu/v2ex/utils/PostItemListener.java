package com.cn.guojinhu.v2ex.utils;

import com.cn.guojinhu.v2ex.data.Member;
import com.cn.guojinhu.v2ex.data.Node;
import com.cn.guojinhu.v2ex.data.Post;

public interface PostItemListener {
    void onPostItemClick(Post post);

    void onPostMemberClick(Member member);

    void onPostNodeClick(Node node);
}

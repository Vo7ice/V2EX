package com.cn.guojinhu.v2ex.nodeDetail;

public class NodeDetailPresenter implements NodeDetailContract.Presenter {

    private NodeDetailContract.View mNodeDetailView;

    public NodeDetailPresenter(NodeDetailContract.View nodeDetailView) {
        mNodeDetailView = nodeDetailView;

        mNodeDetailView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}

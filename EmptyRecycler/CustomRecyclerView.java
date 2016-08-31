package br.com.anbima.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import br.com.anbima.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bruno on 7/29/16.
 */
public class CustomRecyclerView extends FrameLayout {


    @BindView(R.id.custom_recycler)
    public RecyclerView mRecyclerView;

    @BindView(R.id.custom_load_container)
    public ViewGroup mLoadContainer;

    @BindView(R.id.custom_empty_container)
    public ViewGroup mEmptyContainer;

    @BindView(R.id.custom_error_container)
    public ViewGroup mErrorContainer;

    public CustomRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.view_custom_recycler, this);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomRecyclerView, 0, 0);
            int emptyId = a.getResourceId(R.styleable.CustomRecyclerView_emptyView, R.layout.empty_list_custom);
            int loadId = a.getResourceId(R.styleable.CustomRecyclerView_loadView, R.layout.load_list_custom);
            int errorId = a.getResourceId(R.styleable.CustomRecyclerView_errorView, R.layout.error_list_custom);
            View.inflate(getContext(), emptyId, mEmptyContainer);
            View.inflate(getContext(), loadId, mLoadContainer);
            View.inflate(getContext(), errorId, mErrorContainer);
            a.recycle();
        }

        showLoadView();
    }

    public CustomRecyclerView setErrorViewAction(@IdRes int buttonId, OnClickListener onClickListener) {
        setAction(mErrorContainer, buttonId, onClickListener);
        return this;
    }

    public CustomRecyclerView setEmptyViewAction(@IdRes int buttonId, OnClickListener onClickListener) {
        setAction(mEmptyContainer, buttonId, onClickListener);
        return this;
    }

    private void setAction(ViewGroup container, @IdRes int buttonId, OnClickListener onClickListener) {
        container.findViewById(buttonId).setOnClickListener(onClickListener);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
        checkEmpty();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkEmpty();
            }
        });
    }


    private void checkEmpty() {
        if (mRecyclerView.getAdapter().getItemCount() == 0) {
            showEmptyView();
        } else {
            showListView();
        }
    }

    private void showEmptyView() {
        mEmptyContainer.setVisibility(VISIBLE);

        mRecyclerView.setVisibility(GONE);
        mLoadContainer.setVisibility(GONE);
        mErrorContainer.setVisibility(GONE);
    }

    public void showLoadView() {
        mLoadContainer.setVisibility(VISIBLE);

        mRecyclerView.setVisibility(GONE);
        mEmptyContainer.setVisibility(GONE);
        mErrorContainer.setVisibility(GONE);
    }

    public void showErrorView() {
        mErrorContainer.setVisibility(VISIBLE);

        mRecyclerView.setVisibility(GONE);
        mEmptyContainer.setVisibility(GONE);
        mLoadContainer.setVisibility(GONE);
    }

    public void showListView() {
        mRecyclerView.setVisibility(VISIBLE);

        mErrorContainer.setVisibility(GONE);
        mEmptyContainer.setVisibility(GONE);
        mLoadContainer.setVisibility(GONE);
    }
}
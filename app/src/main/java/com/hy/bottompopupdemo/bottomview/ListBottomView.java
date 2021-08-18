package com.hy.bottompopupdemo.bottomview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hy.bottompopupdemo.R;


/**
 * 编辑模块 MV、滤镜等效果添加的视图
 */
public class ListBottomView extends BaseBottomView {

    private RecyclerView mRecyclerView;
    private OnBottomViewListener mOnMusicSelectOperationListener;

    public ListBottomView(@NonNull Context context) {
        this(context, null);
    }

    public ListBottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListBottomView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(RecyclerView.Adapter adapter) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_list_bottom_view, this);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);
        view.findViewById(R.id.iv_list_bottom_view_cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMusicSelectOperationListener!=null){
                    mOnMusicSelectOperationListener.onCancelClicked();
                }
            }
        });
        view.findViewById(R.id.iv_list_bottom_view_confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMusicSelectOperationListener!=null){
                    mOnMusicSelectOperationListener.onConfirmClicked();
                }
            }
        });
    }

    public void setOnBottomViewListener(OnBottomViewListener listener) {
        mOnMusicSelectOperationListener = listener;
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener onScrollListener){
        mRecyclerView.addOnScrollListener(onScrollListener);
    }

    @Override
    protected void init() {
    }

    @Override
    public boolean isPlayerNeedZoom() {
        return false;
    }

    public interface OnBottomViewListener {
        void onCancelClicked();

        void onConfirmClicked();
    }

}

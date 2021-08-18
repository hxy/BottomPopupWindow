package com.hy.bottompopupdemo.bottomview;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.hy.bottompopupdemo.OnCoverBottomOperateListener;
import com.hy.bottompopupdemo.R;
import com.hy.bottompopupdemo.Utils;
import com.hy.bottompopupdemo.adapter.CoverTextStyleAdapter;
import com.hy.bottompopupdemo.model.CoverTextStyle;

import org.jetbrains.annotations.NotNull;

import me.minetsh.imaging.view.IMGStickerTextView;
import me.minetsh.imaging.view.IMGView;

/**
 * Author: hy
 * Date: 2/4/21
 * Description: 底部封面视图
 */
public class CoverBottomView extends BaseBottomView{
    private RecyclerView mTxtRecyclerView;
    private RecyclerView mImgRecyclerView;

    private ImageView btnClose;
    private ImageView btnOk;
    private OnCoverBottomOperateListener operateListener;
    private IMGView imgEditLayout;
    private EditText inputProxy;
    private IMGStickerTextView currentStickerTextView;

    private static final String GUIDE_TEXT = "请输入文字";

    public CoverBottomView(@NonNull Context context) {
        this(context,null);
    }

    public CoverBottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public CoverBottomView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_cover_bottom, this);
        initView();
    }

    public void initData(IMGView imgEditLayout){
        this.imgEditLayout = imgEditLayout;
    }


    private void initView() {
        inputProxy = findViewById(R.id.id_input_et);
        mTxtRecyclerView = findViewById(R.id.id_rv_txt);
        mTxtRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        CoverTextStyleAdapter coverTextStyleAdapter = new CoverTextStyleAdapter();
        coverTextStyleAdapter.setOnItemClickListener(new CoverTextStyleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(@NotNull CoverTextStyle style) {
                IMGStickerTextView text  = new IMGStickerTextView(mContext);
                text.setText(GUIDE_TEXT);
                text.setTextColor(mContext.getResources().getColor(style.getColorId()));
                text.setTextSize(Utils.dip2px(mContext,8));
                if(style.getBackGroundId()!=0){
                    text.setBgColor(mContext.getResources().getColor(style.getBackGroundId()));
                }
                text.setOperationListener(new IMGStickerTextView.OnOperationListener() {
                    @Override
                    public void onSelected(IMGStickerTextView tv) {
                        currentStickerTextView = tv;
                    }

                    @Override
                    public void onDuplicateSelected(IMGStickerTextView textView) {
                        showKeyboard();
                    }

                    @Override
                    public void onRemove(IMGStickerTextView textView) {
                        hideKeyboard();
                    }
                });
                imgEditLayout.addStickerText(text);
                currentStickerTextView = text;
                showKeyboard();
            }
        });
        mTxtRecyclerView.setAdapter(coverTextStyleAdapter);

        inputProxy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!GUIDE_TEXT.equals(s.toString())){
                    currentStickerTextView.setText(s.toString().replace(GUIDE_TEXT,""));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });












        mImgRecyclerView = (RecyclerView) findViewById(R.id.id_rv_img);
        mImgRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        btnOk = findViewById(R.id.btn_ok);
        btnClose = findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operateListener!=null){
                    operateListener.onCoverClose();
                }
            }
        });
        btnOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operateListener!=null){
                    operateListener.onCoverSave(imgEditLayout.saveBitmap());
                }
            }
        });
    }






    @Override
    public boolean isPlayerNeedZoom() {
        return false;
    }

    @Override
    public void addedToShow() {
        imgEditLayout.setVisibility(VISIBLE);
    }

    @Override
    public void removeSelf() {
        super.removeSelf();
    }

    /**
     * 设置裁剪视图操作监听
     */
    public void setCoverOperateListener(OnCoverBottomOperateListener listener){
        operateListener = listener;
    }



    private void showKeyboard(){
        inputProxy.setText(currentStickerTextView.getTextString());
        inputProxy.setSelection(inputProxy.getText().length());
        Utils.showKeyboard(inputProxy);
        ((Activity)mContext).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    private void hideKeyboard(){
        Utils.hideKeyboard(inputProxy);
    }
}

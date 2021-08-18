package me.minetsh.imaging.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by felix on 2017/11/14 下午7:27.
 */
public class IMGStickerTextView extends IMGStickerView {
    public interface OnOperationListener {
        void onSelected(IMGStickerTextView textView);
        void onDuplicateSelected(IMGStickerTextView textView);
        void onRemove(IMGStickerTextView textView);
    }
    private OnOperationListener operationListener;

    private static final String TAG = "IMGStickerTextView";

    private TextView mTextView;

    private static float mBaseTextSize = -1f;

    private static final int PADDING = 26;

    private static final float TEXT_SIZE_SP = 24f;

    public IMGStickerTextView(Context context) {
        this(context, null, 0);
    }

    public IMGStickerTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IMGStickerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onInitialize(Context context) {
        if (mBaseTextSize <= 0) {
            mBaseTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    TEXT_SIZE_SP, context.getResources().getDisplayMetrics());
        }
        super.onInitialize(context);
    }

    @Override
    public View onCreateContentView(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setPadding(PADDING, PADDING, PADDING, PADDING);
        mTextView = new TextView(context);
        mTextView.setTextSize(mBaseTextSize);
        mTextView.setTextColor(Color.WHITE);
        linearLayout.addView(mTextView);
        return linearLayout;
    }

    @Override
    public void onContentTap() {
        if(operationListener !=null){
            operationListener.onSelected(this);
        }
    }

    @Override
    public void onDuplicateTap() {
        if(operationListener !=null){
            operationListener.onDuplicateSelected(this);
        }
    }

    @Override
    public void onRemove() {
        super.onRemove();
        if(operationListener !=null){
            operationListener.onRemove(this);
        }
    }

    public void setTextSize(float size){
        mTextView.setTextSize(size);
    }

    public void setText(String textString){
        mTextView.setText(textString);
    }

    public void setTextColor(int color){
        mTextView.setTextColor(color);
    }

    public void setBgColor(int color){
        mTextView.setBackgroundColor(color);
    }

    public String getTextString(){
        return mTextView.getText().toString();
    }

    public void setOperationListener(OnOperationListener listener){
        operationListener = listener;
    }
}
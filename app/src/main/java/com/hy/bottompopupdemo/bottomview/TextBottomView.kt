package com.hy.bottompopupdemo.bottomview

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.GridLayoutManager
import com.hy.bottompopupdemo.OnTextBottomOperateListener
import com.hy.bottompopupdemo.R
import com.hy.bottompopupdemo.Utils
import com.hy.bottompopupdemo.adapter.TextColorAdapter
import kotlinx.android.synthetic.main.layout_text_bottom.view.*

/**
 * Author: hy
 * Date: 1/27/21
 * Description: 文字底部视图
 */
class TextBottomView:BaseBottomView{
    //颜色列表adapter
    private var colorAdapter: TextColorAdapter?=null
    //操作监听
    private var operateListener: OnTextBottomOperateListener?=null
    //输入框监听
    private lateinit var textWatcher:TextWatcher
    //布局监听，用来计算键盘高度
    private lateinit var layoutListener:OnLayoutChangeListener
    //键盘高度
    private var softKeyBoardHeight=0

    constructor(context: Context):super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet):super(context, attrs){
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int):super(
        context,
        attrs,
        defStyleAttr
    ){
        init()
    }
    override fun init() {
        //添加整体布局
        LayoutInflater.from(mContext).inflate(R.layout.layout_text_bottom, this)
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

        }
        layoutListener =
            OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom -> if(top<oldTop){softKeyBoardHeight=oldTop-top} }

        //完成按钮点击事件
        btn_ok.setOnClickListener {
            //回调方法
            operateListener?.onOK()
        }

        //键盘按钮点击事件
        btn_input_text.setOnClickListener {
            //调起键盘
            Utils.showKeyboard(input_et)
            btn_input_text.setImageResource(R.mipmap.icon_video_edit_bottom_text_input_light)
            text_cursor.visibility=View.VISIBLE
            btn_text_color.setImageResource(R.mipmap.icon_video_edit_bottom_text_color_dark)
            color_cursor.visibility=View.GONE
            //键盘弹起需要时间，延时等到键盘弹起后再改变键盘布局方式
            postDelayed({
                //设置键盘为重新布局，目的：
                // 1.使布局可以响应用户主动关闭键盘的动作
                (mContext as Activity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                //更新文字按钮点击状态,此时键盘可顶起布局，所以使列表消失
                color_list.visibility=View.GONE
                operateListener?.onTextEditShow()
            },200)

        }

        //颜色按钮点击事件
        btn_text_color.setOnClickListener {
            if(colorAdapter==null){
                //如果adapter为空，说明第一次显示，创建颜色列表
                colorAdapter = TextColorAdapter()
                colorAdapter!!.setColorSelectedListener(object : TextColorAdapter.OnColorSelectedListener{
                    override fun onColorSelected(colorResId: Int) {
                        Toast.makeText(context, "colorResId：$colorResId", Toast.LENGTH_SHORT).show()
                    }
                })
                var layoutManager = GridLayoutManager(mContext, 6)
                color_list.layoutManager=layoutManager
                color_list.adapter = colorAdapter
                //设置颜色列表高度为键盘高度
                if(softKeyBoardHeight>0){
                    color_list.layoutParams.height=softKeyBoardHeight-color_list.marginBottom
                }
            }
            //设置键盘为覆盖，因为此时已经拿到键盘高度，可以使用颜色列表撑起视图；此处修改为覆盖的目的
            //1.在显示颜色列表前改为覆盖可以防止显示列表时键盘顶起整个列表
            //2.在切换为键盘时不使键盘顶起整个布局
            (mContext as Activity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            color_list.visibility=View.VISIBLE
            //收起键盘
            Utils.hideKeyboard(this)
            //更新颜色按钮点击状态
            btn_text_color.setImageResource(R.mipmap.icon_video_edit_bottom_text_color_light)
            color_cursor.visibility=View.VISIBLE
            btn_input_text.setImageResource(R.mipmap.icon_video_edit_bottom_text_input_dark)
            text_cursor.visibility=View.GONE
            operateListener?.onColorListShow()
        }
    }

    /**
     * 设置操作监听
     */
    fun setTextOperateListener(listener: OnTextBottomOperateListener){
        operateListener = listener
    }

    override fun removeSelf() {
        removeOnLayoutChangeListener(layoutListener)
        //清空输入框文字前删除监听，防止清空文字时改变text内容
        input_et.removeTextChangedListener(textWatcher)
        input_et.text=null
        //隐藏键盘
        Utils.hideKeyboard(this)
        super.removeSelf()
    }


    override fun addedToShow(){
        addOnLayoutChangeListener(layoutListener)
        //显示时调起键盘，添加输入框监听
        btn_input_text.performClick()
        input_et.addTextChangedListener(textWatcher)
    }

    override fun isPlayerNeedZoom(): Boolean {
        return false
    }
}
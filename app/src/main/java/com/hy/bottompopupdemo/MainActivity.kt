package com.hy.bottompopupdemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.hy.bottompopupdemo.bottomview.CoverBottomView
import com.hy.bottompopupdemo.bottomview.TextBottomView
import com.hy.bottompopupdemo.bottomview.ViewOperator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    //底部view和title控制器
    private lateinit var viewOperator: ViewOperator

    //文字底部视图
    private var textBottomView: TextBottomView? = null

    //封面底部视图
    private var coverBottomView: CoverBottomView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //设置键盘弹出方式为挤压，这样可以使键盘不覆盖view
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        initView()
        initListener()
    }

    private fun initView() {
        viewOperator = ViewOperator(root_layout, title_layout, ll_bottom_view, image_edit_layout)
        image_edit_layout.setImageBitmap(BitmapFactory.decodeResource(resources, R.mipmap.test))
    }


    private fun initListener() {
        back.setOnClickListener {
            onBackPressed()
        }


        btn_finish.setOnClickListener {

        }

        tv_edit_video_cover.setOnClickListener {
            showCoverBottomView()
        }

        gif_sticker_btn.setOnClickListener {
            showTextBottomView()
        }
    }

    override fun onStop() {
        super.onStop()
        viewOperator.hideBottomView()
    }





    /**
     * 显示底部封面视图
     */
    private fun showCoverBottomView() {
        coverBottomView =
            CoverBottomView(this)
        coverBottomView!!.initData(image_edit_layout)
        coverBottomView!!.setCoverOperateListener(object : OnCoverBottomOperateListener {

            override fun onCoverClose() {
                viewOperator.hideBottomView()
            }

            override fun onCoverSave(bitmap: Bitmap) {
                image_edit_layout.setImageBitmap(bitmap)
                viewOperator.hideBottomView()
            }

        })
        viewOperator.showBottomView(coverBottomView)
    }


    /**
     * 显示底部文字编辑视图
     */
    private fun showTextBottomView() {
        if (textBottomView == null) {
            textBottomView = TextBottomView(this)
        }
        textBottomView!!.setTextOperateListener(object : OnTextBottomOperateListener {
            override fun onOK() {
                viewOperator.hideBottomView()
            }

            override fun onColorListShow() {

            }

            override fun onTextEditShow() {

            }
        })
        viewOperator.showBottomView(textBottomView)
    }
}
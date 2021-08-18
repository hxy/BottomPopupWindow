package com.hy.bottompopupdemo

import android.graphics.Bitmap

/**
 * Author: hy
 * Date: 1/26/21
 * Description: 底部封面视图事件监听
 */
interface OnCoverBottomOperateListener {
    /**
     * 关闭封面视图
     */
    fun onCoverClose()

    /**
     * 封面保存
     * @param bitmap
     */
    fun onCoverSave(bitmap: Bitmap)

}
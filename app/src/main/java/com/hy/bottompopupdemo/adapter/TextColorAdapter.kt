package com.hy.bottompopupdemo.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import com.hy.bottompopupdemo.R

/**
 * Author: hy
 * Date: 1/27/21
 * Description:
 */
class TextColorAdapter: RecyclerView.Adapter<TextColorAdapter.TextColorViewHolder>() {

    interface OnColorSelectedListener{
        fun onColorSelected(colorResId:Int)
    }
    private var colorListener:OnColorSelectedListener?=null
    private var colorDrawables = intArrayOf(
        R.drawable.color_card_d0021b,R.drawable.color_card_f5a623,R.drawable.color_card_f8e71c,
        R.drawable.color_card_8b572a,R.drawable.color_card_7ed321,R.drawable.color_card_bd10e0,
        R.drawable.color_card_4a90e2,R.drawable.color_card_50e3c2,R.drawable.color_card_000000,
        R.drawable.color_card_9d9d9d,R.drawable.color_card_1283ff,R.drawable.color_card_ffffff)
    private var colors = intArrayOf(
        R.color.color_d0021b,R.color.color_f5a623,R.color.color_f8e71c,
        R.color.color_8b572a,R.color.color_7ed321,R.color.color_bd10e0,
        R.color.color_4a90e2,R.color.color_50e3c2,R.color.color_000000,
        R.color.color_9d9d9d,R.color.color_1283ff,R.color.color_ffffff)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextColorViewHolder {
        var itemView = ImageView(parent.context)
        var lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.setMargins(parent.context.resources.getDimensionPixelOffset(R.dimen.dimen_12))
        itemView.layoutParams =lp
        itemView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        itemView.setOnClickListener {
            colorListener?.onColorSelected(itemView.tag as Int)
        }
        return TextColorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TextColorViewHolder, position: Int) {
        holder.colorView.setImageResource(colorDrawables[position])
        holder.colorView.tag = colors[position]
    }

    override fun getItemCount(): Int {
        return colorDrawables.size
    }

    /**
     * 设置颜色选择监听
     */
    fun setColorSelectedListener(listener: OnColorSelectedListener){
        colorListener = listener
    }


    class TextColorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var colorView: ImageView = itemView as ImageView
    }
}
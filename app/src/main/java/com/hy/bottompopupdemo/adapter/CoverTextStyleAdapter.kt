package com.hy.bottompopupdemo.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hy.bottompopupdemo.R
import com.hy.bottompopupdemo.Utils
import com.hy.bottompopupdemo.model.CoverTextStyle

/**
 * Author: hy
 * Date: 7/19/21
 * Description:
 */
class CoverTextStyleAdapter : RecyclerView.Adapter<CoverTextStyleAdapter.CoverTextColorViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(style: CoverTextStyle)
    }
    private var itemClickListener:OnItemClickListener? = null
    private var styleList : ArrayList<CoverTextStyle> = ArrayList()

    init {
        styleList.add(CoverTextStyle(R.mipmap.icon_cover_text_fff,R.color.white,0))
        styleList.add(CoverTextStyle(R.mipmap.icon_cover_text_d66948,R.color.color_D66948,0))
        styleList.add(CoverTextStyle(R.mipmap.icon_cover_text_5a968f,R.color.color_5A968F,0))
        styleList.add(CoverTextStyle(R.mipmap.icon_cover_text_eaaf35,R.color.color_EAAF35,0))
        styleList.add(CoverTextStyle(R.mipmap.icon_cover_text_fff_d66948,R.color.white,R.color.color_D66948))
        styleList.add(CoverTextStyle(R.mipmap.icon_cover_text_fff_5a968f,R.color.white,R.color.color_5A968F))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoverTextColorViewHolder {
        var itemView = ImageView(parent.context)
        var wh = Utils.dip2px(parent.context,50f)
        var lp = ViewGroup.MarginLayoutParams(wh, wh)
        lp.setMargins(Utils.dip2px(parent.context,7f),0,Utils.dip2px(parent.context,7f),0)
        itemView.layoutParams =lp
        itemView.scaleType = ImageView.ScaleType.FIT_XY
        itemView.setOnClickListener {
            itemClickListener?.onItemClick(itemView.tag as CoverTextStyle)
        }
        return CoverTextColorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CoverTextColorViewHolder, position: Int) {
        var style = styleList[position]
        holder.colorView.setImageResource(style.imgId)
        holder.colorView.tag = style
    }

    override fun getItemCount(): Int {
        return styleList.size
    }



    fun setOnItemClickListener(clickListener:OnItemClickListener){
        itemClickListener = clickListener
    }



    class CoverTextColorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var colorView: ImageView = itemView as ImageView
    }
}
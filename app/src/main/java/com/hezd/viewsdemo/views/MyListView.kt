package com.hezd.viewsdemo.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView

/**
 *
 *@author hezd
 *Create on 2021/12/22 16:51
 */
class MyListView(context: Context?, attrs: AttributeSet?) : ListView(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = MeasureSpec.getSize(heightMeasureSpec)
        val newHeightSpec = MeasureSpec.makeMeasureSpec(size,MeasureSpec.EXACTLY)
//        val newHeightSpec = MeasureSpec.makeMeasureSpec(size,MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, newHeightSpec)
    }
}
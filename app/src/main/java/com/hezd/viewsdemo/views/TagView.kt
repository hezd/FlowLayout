package com.hezd.viewsdemo.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.hezd.viewsdemo.R
import kotlin.random.Random

/**
 *
 *@author hezd
 *Create on 2021/12/23 15:17
 */
class TagView(context: Context?, attrs: AttributeSet?) : TextView(context, attrs) {
    private val colorList = arrayListOf(
        Color.BLACK, Color.BLUE, Color.GREEN
    )
    private val provinceList = arrayListOf<String>(
        "北京",
        "上海",
        "杭州",
        "广州",
        "武汉",
        "重庆",
        "上海",
        "深圳",
        "长春",
        "乌鲁木齐",
        "哈尔滨",
        "郑州",
        "成都",
        "大连",
        "南昌",
        "兰州",
        "齐齐哈尔",
        "汕头",
        "苏州",
        "拉萨",
        "南京",
        "呼和浩特",
        "厦门",
        "合肥",
        "沈阳",
        "张家界",
        "贵州",
        "宁夏",
        "济南",
        "天津",
        "石家庄",
        "西安",
        "澳门"
    )

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = colorList[Random(colorList.size - 1).nextInt()]
        textSize = resources.getDimension(R.dimen.tag_size);
    }


}
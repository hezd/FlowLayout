package com.hezd.flowlayout

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

/**
 *
 *@author hezd
 *Create on 2021/12/24 17:08
 */
class FlowLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {
    var lineSpacing: Int
    var itemSpacing: Int

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout)
        lineSpacing = typedArray.getDimensionPixelOffset(R.styleable.FlowLayout_lineSpacing, 0)
        itemSpacing = typedArray.getDimensionPixelOffset(R.styleable.FlowLayout_itemSpacing, 0)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Flowlayout测量尺寸
        var finalHeight = 0
        var finalWidth = 0

        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        // 已使用行宽
        var usedWidth = 0
        // 单行中高度最高的尺寸大小
        var maxLineHeight = 0
        // 是否需要换行
        var isNewLine: Boolean
        for (child in children) {
            // child测量以可用空间为基准
            measureChildWithMargins(
                child, widthMeasureSpec,
                getDefaultUsedWidth(), heightMeasureSpec, paddingTop + paddingBottom
            )
            isNewLine = usedWidth + child.measuredWidth > widthSpecSize
            if (isNewLine) {
                // 如果有换行可确定最大宽度
                finalWidth = widthSpecSize
                // 换行后缺省的最大行高
                maxLineHeight = max(maxLineHeight, child.measuredHeight)
                // 换行后测量高度累加
                finalHeight += maxLineHeight + lineSpacing
                // 状态重置
                usedWidth = getDefaultUsedWidth()
                maxLineHeight = 0
            }
            // 不管是否有换行都需要计算当前最大行高、已使用宽度、最大宽度
            maxLineHeight = max(maxLineHeight, child.measuredHeight)
            usedWidth += child.measuredWidth + itemSpacing
            finalWidth = max(finalWidth, usedWidth)
        }

        // 需要把最后一行的最大高度也添加到finalHeight中
        finalHeight += maxLineHeight

        // 保存测量高度
        setMeasuredDimension(
            resolveSize(finalWidth, widthMeasureSpec),
            resolveSize(finalHeight, heightMeasureSpec)
        )
    }

    private fun getDefaultUsedWidth() = paddingLeft + paddingRight

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // 已使用宽度
        // 因为是水平layout所以，每个子view的left应该从已使用宽度开始计算
        var usedWidth = getDefaultUsedWidth()
        // 当前行起始纵坐标
        var usedHeight = 0
        // 单行中高度最高的尺寸大小
        var maxLineHeight = 0
        var isNeedNewLine: Boolean
        for (child in children) {

            isNeedNewLine = usedWidth + child.measuredWidth > measuredWidth
            if (isNeedNewLine) {
                //计算单行最大高度
                maxLineHeight = max(maxLineHeight, child.measuredHeight)
                // 布局top坐标更新
                usedHeight += maxLineHeight + lineSpacing
                usedWidth = getDefaultUsedWidth()
                maxLineHeight = 0
            }

            // 开始布局
            child.layout(
                usedWidth,
                usedHeight,
                usedWidth + child.measuredWidth,
                usedHeight + child.measuredHeight
            )

            // 更新单行最大高度、已占用行宽度
            maxLineHeight = max(maxLineHeight, child.measuredHeight)
            usedWidth += child.measuredWidth + itemSpacing

        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT)
    }
}
package com.hezd.viewsdemo.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

/**
 *@author hezd
 *Create on 2021/12/23 11:13
 */
class FlowLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private val lineSpacing = 20
    private val itemSpacing = 20

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)

        var lineMaxHeight = 0
        var lineMaxWidth = 0
        var usedWidth = getDefaultMeasureUsedWith()
        var usedHeight: Int = getDefaultMeasureUsedHeight()
        for (child in children) {
            measureChildWithMargins(
                child,
                widthMeasureSpec,
                paddingLeft + paddingRight,
                heightMeasureSpec,
                paddingTop + paddingBottom
            )
            if (usedWidth + child.measuredWidth > widthSpecSize) {
                lineMaxWidth = max(usedWidth, lineMaxWidth)
                usedHeight += max(child.measuredHeight, lineMaxHeight) + lineSpacing
                usedWidth = getDefaultMeasureUsedWith()
                lineMaxHeight = 0
            }
            lineMaxHeight = max(lineMaxHeight, child.measuredHeight)
            usedWidth += child.measuredWidth + itemSpacing
        }
        usedHeight += lineMaxHeight


        setMeasuredDimension(
            resolveSize(lineMaxWidth, widthMeasureSpec),
            resolveSize(usedHeight, heightMeasureSpec)
        )
    }

    private fun getDefaultMeasureUsedHeight() = paddingTop + paddingBottom

    private fun getDefaultMeasureUsedWith() = paddingLeft + paddingRight

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // 复杂逻辑实现步骤由简到繁逐步实现，比如先实现一行，在实现多行，在添加间距
        val layoutSize = measuredWidth
        var lineMaxHeight = 0
        var usedWidth = getDefaultLayoutUsedWidth()
        var usedHeight = getDefaultLayoutUsedHeight()
        for (child in children) {
            val childWith = child.measuredWidth
            val childHeight = child.measuredHeight
            if (usedWidth + child.measuredWidth + paddingRight > layoutSize) {
                usedHeight += lineMaxHeight + lineSpacing
                usedWidth = getDefaultLayoutUsedWidth()
                lineMaxHeight = 0
            }
            lineMaxHeight = max(child.measuredHeight, lineMaxHeight)
            // 先分析一行情况
            child.layout(usedWidth, usedHeight, usedWidth + childWith, usedHeight + childHeight)
            usedWidth += childWith + itemSpacing
        }
    }

    private fun getDefaultLayoutUsedHeight() = paddingTop

    private fun getDefaultLayoutUsedWidth() = paddingLeft

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

}
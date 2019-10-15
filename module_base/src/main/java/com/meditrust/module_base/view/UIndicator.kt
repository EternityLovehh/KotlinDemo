package com.meditrust.module_base.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.meditrust.module_base.R
import com.meditrust.module_base.utils.DpUtils

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/19
 * @desc: 自定义轮播图指示器
 */
class UIndicator : View {

    companion object {
        private val TAG = "UIndicator"
        //指示器样式一 选中未选中都是圆点
        val STYLE_CIRCLR_CIRCLE = 0
        //指示器样式二 选中未选中都是方形
        val STYLE_RECT_RECT = 1
        //指示器样式三 选中方形，未选中圆点
        val STYLE_CIRCLR_RECT = 2

        //横向排列
        val HORIZONTAL = 0
        //纵向排列
        val VERTICAL = 1
    }

    //指示器之间的间距
    private var spacing: Int = 0
    //指示器排列方向
    private var orientation = HORIZONTAL
    //选中与未选中的颜色
    private lateinit var selectedColor: ColorStateList
    private lateinit var normalColor: ColorStateList

    //指示器样式，默认都是圆点
    private var mStyle = STYLE_CIRCLR_CIRCLE

    //样式一 圆点半径大小
    private var circleCircleRadius = 0

    //样式二 方形大小及圆角
    private var rectRectItemWidth = 0
    var rectRectItemHeight = 0
    var rectRectCorner = 0

    //样式三 选中的方形大小及圆角
    private var circleRectItemWidth = 0
    var circleRectItemHeight = 0
    var circleRectCorner = 0
    //样式三 未选中的圆点半径
    private var circleRectRadius = 0

    //画笔
    private lateinit var normalPaint: Paint
    private lateinit var selectedPaint: Paint

    //指示器item的区域
    private lateinit var mRectF: RectF
    //指示器大小
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    //指示器item个数
    private var itemCount = 0
    //当前选中的位置
    private var selection = 0

    private var mContext: Context
    private lateinit var mViewPager2: ViewPager2

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defaultStyle: Int) : super(
        context,
        attributeSet,
        defaultStyle
    ) {
        mContext = context
        initAttr(attributeSet)
        initPaint()
        checkItemCount()
    }

    /**
     * 初始化资源属性
     */
    private fun initAttr(attributeSet: AttributeSet?) {
        val typeArray = mContext.obtainStyledAttributes(attributeSet, R.styleable.Indicator)
        selectedColor = typeArray.getColorStateList(R.styleable.Indicator_selected_color)!!
        normalColor = typeArray.getColorStateList(R.styleable.Indicator_normal_color)!!
        spacing = typeArray.getDimensionPixelSize(R.styleable.Indicator_spacing, DpUtils.dp2px(6f))
        orientation = typeArray.getInt(R.styleable.Indicator_orientation, HORIZONTAL)
        mStyle = typeArray.getInt(R.styleable.Indicator_style, STYLE_CIRCLR_CIRCLE)

        circleCircleRadius =
            typeArray.getDimensionPixelSize(R.styleable.Indicator_circle_circle_radius, DpUtils.dp2px(3f))

        rectRectCorner = typeArray.getDimensionPixelSize(R.styleable.Indicator_rect_rect_corner, 0)
        rectRectItemHeight =
            typeArray.getDimensionPixelSize(R.styleable.Indicator_rect_rect_itemHeight, DpUtils.dp2px(3f))
        rectRectItemWidth =
            typeArray.getDimensionPixelSize(R.styleable.Indicator_rect_rect_itemWidth, DpUtils.dp2px(15f))

        circleRectCorner = typeArray.getDimensionPixelSize(R.styleable.Indicator_circle_rect_corner, 0)
        circleRectRadius = typeArray.getDimensionPixelSize(R.styleable.Indicator_circle_rect_radius, DpUtils.dp2px(3f))
        circleRectItemHeight =
            typeArray.getDimensionPixelSize(R.styleable.Indicator_circle_rect_itemHeight, DpUtils.dp2px(3f))
        circleRectItemWidth =
            typeArray.getDimensionPixelSize(R.styleable.Indicator_circle_rect_itemWidth, DpUtils.dp2px(15f))

        // 解析后释放资源
        typeArray.recycle()
    }

    /**
     * 画笔设置
     */
    private fun initPaint() {
        normalPaint = Paint()
        normalPaint.style = Paint.Style.FILL
        normalPaint.isAntiAlias = true
        normalPaint.color = if (normalColor == null) Color.GRAY else normalColor.getDefaultColor()

        selectedPaint = Paint()
        selectedPaint.style = Paint.Style.FILL
        selectedPaint.isAntiAlias = true
        selectedPaint.color = if (selectedColor == null) Color.RED else selectedColor.getDefaultColor()

        mRectF = RectF()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        when (mStyle) {
            STYLE_CIRCLR_CIRCLE -> {
                if (orientation == HORIZONTAL) {
                    mWidth = 2 * circleCircleRadius * itemCount + (itemCount - 1) * spacing
                    mHeight = Math.max(heightSize, 2 * circleCircleRadius)
                } else {
                    mHeight = 2 * circleCircleRadius * itemCount + (itemCount - 1) * spacing
                    mWidth = Math.max(widthSize, 2 * circleCircleRadius)
                }
            }
            STYLE_RECT_RECT -> {
                if (orientation == HORIZONTAL) {
                    mWidth = rectRectItemWidth * itemCount + (itemCount - 1) * spacing
                    mHeight = Math.max(heightSize, rectRectItemHeight)
                } else {
                    mHeight = rectRectItemHeight * itemCount + (itemCount - 1) * spacing
                    mWidth = Math.max(widthSize, rectRectItemWidth)
                }
            }
            STYLE_CIRCLR_RECT -> {
                if (orientation === HORIZONTAL) {
                    val normalItemWidth = circleRectRadius * 2
                    mWidth = (itemCount - 1) * normalItemWidth + circleRectItemWidth + (itemCount - 1) * spacing
                    val tempHeight = Math.max(circleRectItemHeight, circleRectRadius * 2)
                    mHeight = Math.max(heightSize, tempHeight)
                } else {
                    val normalItemHeight = circleRectRadius * 2
                    mHeight = (itemCount - 1) * normalItemHeight + circleRectItemHeight + (itemCount - 1) * spacing
                    val tempWidth = Math.max(circleRectItemWidth, circleRectRadius * 2)
                    mWidth = Math.max(widthSize, tempWidth)
                }
            }
        }
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (orientation == HORIZONTAL) {
            when (mStyle) {
                STYLE_CIRCLR_CIRCLE -> {
                    for (index in 0 until itemCount) {
                        val cx = (index + 1) * circleCircleRadius + index * spacing
                        canvas?.drawCircle(
                            cx.toFloat(),
                            (height / 2).toFloat(),
                            circleCircleRadius.toFloat(),
                            if (index == selection) selectedPaint else normalPaint
                        )
                    }
                }
                STYLE_RECT_RECT -> {
                    for (index in 0 until itemCount) {
                        val left = index * rectRectItemWidth + index * spacing
                        mRectF?.set(
                            left.toFloat(),
                            0f,
                            left + rectRectItemWidth.toFloat(),
                            rectRectItemHeight.toFloat()
                        )
                        //全部绘制圆角矩形，画笔的区别
                        mRectF?.let {
                            canvas?.drawRoundRect(
                                it,
                                rectRectCorner.toFloat(),
                                rectRectCorner.toFloat(),
                                if (index == selection) selectedPaint else normalPaint
                            )
                        }
                    }
                }

                STYLE_CIRCLR_RECT -> {
                    for (index in 0 until itemCount) {
                        val left = selection * (circleRectRadius * 2 + spacing).toFloat()
                        var top: Float? = 0f
                        if (selection == index) {
                            //选中的绘制圆角矩形
                            top = (height - circleRectItemHeight).toFloat() / 2
                            mRectF?.set(left, top, left + circleRectItemWidth, circleRectItemHeight + top)
                            mRectF?.let {
                                canvas?.drawRoundRect(
                                    it,
                                    circleRectCorner.toFloat(),
                                    circleRectCorner.toFloat(),
                                    selectedPaint
                                )
                            };
                        } else {
                            //未选中的绘制圆点，距离需要判断position在选中的左边或者右边，从而确定cx
                            top = (height - circleRectRadius * 2).toFloat() / 2
                            var cx: Int = 0
                            val cy1 = circleRectRadius + top
                            if (selection < index) {
                                cx =
                                    (index - 1) * circleRectRadius * 2 + index * spacing + circleRectItemWidth + circleRectRadius
                            } else {
                                cx = index * (circleRectRadius * 2) + index * spacing + circleRectRadius
                            }
                            canvas?.drawCircle(cx.toFloat(), cy1, circleRectRadius.toFloat(), normalPaint)
                        }
                    }

                }

            }
        } else {
            when (mStyle) {
                STYLE_CIRCLR_CIRCLE -> {
                    val cx = width / 2
                    for (i in 0 until itemCount) {
                        val cy = i * (circleCircleRadius * 2 + spacing) + circleCircleRadius
                        //全部绘制圆点，画笔的区别
                        canvas?.drawCircle(
                            cx.toFloat(),
                            cy.toFloat(),
                            circleCircleRadius.toFloat(),
                            if (i == selection) selectedPaint else normalPaint
                        )
                    }
                }

                STYLE_RECT_RECT -> for (i in 0 until itemCount) {
                    val top = i * rectRectItemHeight + i * spacing
                    val left = (width - rectRectItemWidth) / 2
                    mRectF?.set(
                        left.toFloat(),
                        top.toFloat(),
                        (left + rectRectItemWidth).toFloat(),
                        (top + rectRectItemHeight).toFloat()
                    )
                    //全部绘制圆角矩形，画笔的区别
                    canvas?.drawRoundRect(
                        mRectF,
                        rectRectCorner.toFloat(),
                        rectRectCorner.toFloat(),
                        if (i == selection) selectedPaint else normalPaint
                    )
                }

                STYLE_CIRCLR_RECT -> for (i in 0 until itemCount) {
                    if (selection == i) {
                        val left = (width - circleRectItemWidth) / 2
                        //选中的绘制圆角矩形
                        val top = selection * (circleRectRadius * 2 + spacing)
                        mRectF.set(
                            left.toFloat(),
                            top.toFloat(),
                            (left + circleRectItemWidth).toFloat(),
                            (top + circleRectItemHeight).toFloat()
                        )
                        canvas?.drawRoundRect(
                            mRectF,
                            circleRectCorner.toFloat(),
                            circleRectCorner.toFloat(),
                            selectedPaint
                        )
                    } else {
                        //未选中的绘制圆点，距离需要判断position在选中的左边或者右边，从而确定cx
                        val cx1 = (width - 2 * circleRectRadius) / 2 + circleRectRadius
                        var cy1 = 0f
                        if (selection < i) {
                            cy1 =
                                ((i - 1) * circleRectRadius * 2 + i * spacing + circleRectItemHeight + circleRectRadius).toFloat()
                        } else {
                            cy1 = (i * (circleRectRadius * 2) + i * spacing + circleRectRadius).toFloat()
                        }
                        canvas?.drawCircle(cx1.toFloat(), cy1, circleRectRadius.toFloat(), normalPaint)
                    }
                }
            }

        }
    }

    /**
     * 绑定viewpage
     */
    fun attachToViewPager2(viewPager2: ViewPager2) {
        this.mViewPager2 = viewPager2
        val pageAdapter: RecyclerView.Adapter<*>? = viewPager2.adapter
        itemCount = pageAdapter!!.itemCount
        pageAdapter?.let {
            selection = viewPager2.currentItem % itemCount
        }

        checkItemCount()

    }

    fun setSelection(position: Int) {
        selection = position

//        checkItemCount()
    }

    private fun checkItemCount() {
        if (selection >= itemCount) {
            selection = itemCount - 1
        }
        visibility = if (itemCount <= 1) GONE else VISIBLE
    }

}

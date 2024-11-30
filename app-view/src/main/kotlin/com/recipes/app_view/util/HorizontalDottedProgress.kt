package com.recipes.app_view.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation

class HorizontalDottedProgress : View {

    companion object {
        private const val DOT_RADIUS: Float = 5f
        private const val BOUNCE_DOT_RADIUS: Float = 8f
        private const val DOT_AMOUNT = 10
        private const val BOUNCE_DURATION = 100L
        private const val PADDING = 10f
        private const val OFFSET = 20
    }

    //to get identified in which position dot has to bounce
    private var mDotPosition = 0

    private val mPaint: Paint = Paint()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs);

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        mPaint.color = Color.parseColor("#fd583f")
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        createDot(canvas, mPaint)
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility == VISIBLE) {
            startAnimation()
        } else {
            clearAnimation()
        }

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (visibility == VISIBLE) {
            startAnimation()
        }
    }

    private fun createDot(canvas: Canvas?, paint: Paint) {
        for (i in 0..DOT_AMOUNT) {
            val radius = if (i == mDotPosition) BOUNCE_DOT_RADIUS else DOT_RADIUS
            canvas?.drawCircle(PADDING + (i * OFFSET), BOUNCE_DOT_RADIUS, radius, paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //calculate the view width
        val calculatedWidth = (OFFSET * DOT_AMOUNT)
        val height: Float = (BOUNCE_DOT_RADIUS * 2)

        //MUST CALL THIS
        setMeasuredDimension(calculatedWidth, height.toInt())
    }

    private fun startAnimation() {
        val bounceAnimation = BounceAnimation()
        bounceAnimation.duration = BOUNCE_DURATION
        bounceAnimation.repeatCount = Animation.INFINITE
        bounceAnimation.interpolator = LinearInterpolator()
        bounceAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // N/A
            }

            override fun onAnimationEnd(animation: Animation?) {
                // N/A
            }


            override fun onAnimationRepeat(animation: Animation) {
                mDotPosition++;
                // when mDotPosition == mDotAmount ,
                // then start again applying animation from 0th positon , i.e  mDotPosition = 0;
                if (mDotPosition == DOT_AMOUNT) {
                    mDotPosition = 0
                }
            }
        })
        startAnimation(bounceAnimation)

    }

    inner class BounceAnimation : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            super.applyTransformation(interpolatedTime, t)
            //call invalidate to redraw your view againg.
            invalidate()
        }
    }

}

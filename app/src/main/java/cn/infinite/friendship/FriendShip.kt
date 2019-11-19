package cn.infinite.friendship

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * @author bug小能手
 * Created on 2019/11/19.
 */
class FriendShip : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, i: Int) : super(context, attrs, i)

    private var mWaveWidth = 1500f
    private var mWaveHeight = 120f
    private val mWavePaint = Paint().apply {
        color = Color.parseColor("#8f00a1ff")
        strokeWidth = 5f
        style = Paint.Style.FILL_AND_STROKE

    }

    private val mShiftWavePaint = Paint().apply {
        color = Color.parseColor("#3f00a1ff")
        strokeWidth = 5f
        style = Paint.Style.FILL_AND_STROKE

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        startAnim()
    }

    private fun startAnim(){
        val valueAnim=ValueAnimator.ofFloat(-mWaveWidth*2,-mWaveWidth)
        valueAnim.duration=600
        valueAnim.repeatMode=ValueAnimator.RESTART
        valueAnim.repeatCount=ValueAnimator.INFINITE
        valueAnim.interpolator=LinearInterpolator()
        valueAnim.addUpdateListener {
            offset=it.animatedValue as Float
            invalidate()
        }
        valueAnim.start()
    }

    private var mPath = Path()
    private var offset = 0f
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        generatePath(canvas!!)
//        canvas?.drawPath(mPath, mWavePaint)
    }

    private val shiftWaveMatrix=Matrix()
    private fun generatePath(canvas: Canvas) {

        val num = measuredWidth / (mWaveWidth).toInt() + 1
        mPath.reset()
        mPath.moveTo(0f + offset, measuredHeight / 2.toFloat())

        for (i in 0 .. num*2) {
                mPath.quadTo(
                    mWaveWidth / 4 + offset+mWaveWidth.times(i), mWaveHeight + measuredHeight / 2,
                    mWaveWidth/2 + offset+mWaveWidth.times(i), measuredHeight / 2.toFloat()
                )

                mPath.quadTo(
                    3 * mWaveWidth / 4 + offset+mWaveWidth.times(i), measuredHeight / 2 - mWaveHeight,
                    mWaveWidth  + offset+mWaveWidth.times(i), measuredHeight / 2.toFloat()
                )

            }

        mPath.rLineTo(0f,measuredHeight/2.toFloat())
        mPath.lineTo(-2*mWaveWidth,measuredHeight.toFloat())
        mPath.lineTo(-2*mWaveWidth+offset, measuredHeight/2.toFloat())
        mPath.close()
        canvas.drawPath(mPath,mWavePaint)

        shiftWaveMatrix.reset()
        shiftWaveMatrix.postTranslate(mWaveWidth*0.6.toFloat(),0f)
        mPath.transform(shiftWaveMatrix)
        mPath.close()
        canvas.drawPath(mPath,mShiftWavePaint)


    }

}
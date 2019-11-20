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
    private var shiftOffset = 900f
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

    private fun startAnim() {
        val valueAnim = ValueAnimator.ofFloat(-mWaveWidth - shiftOffset, -shiftOffset)
        valueAnim.duration = 600
        valueAnim.repeatMode = ValueAnimator.RESTART
        valueAnim.repeatCount = ValueAnimator.INFINITE
        valueAnim.interpolator = LinearInterpolator()
        valueAnim.addUpdateListener {
            offset = it.animatedValue as Float
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

    private val shiftWaveMatrix = Matrix()

    private val pathMeasure = PathMeasure()

    private val boat = BitmapFactory.decodeResource(resources, R.mipmap.ic_boat)

    private fun generatePath(canvas: Canvas) {

        val num = ((measuredWidth + shiftOffset) / mWaveWidth).toInt() + 1
        mPath.reset()
        mPath.moveTo(0f + offset, (measuredHeight-mWaveHeight*2) / 2.toFloat())

        for (i in 0..num) {
            mPath.quadTo(
                mWaveWidth / 4 + offset + mWaveWidth.times(i), mWaveHeight + measuredHeight / 2,
                mWaveWidth / 2 + offset + mWaveWidth.times(i), measuredHeight / 2.toFloat()
            )

            mPath.quadTo(
                3 * mWaveWidth / 4 + offset + mWaveWidth.times(i), measuredHeight / 2 - mWaveHeight,
                mWaveWidth + offset + mWaveWidth.times(i), measuredHeight / 2.toFloat()
            )

        }

        mPath.rLineTo(0f, measuredHeight / 2.toFloat())
        mPath.lineTo(-2 * mWaveWidth, measuredHeight.toFloat())
        mPath.lineTo(-2 * mWaveWidth + offset, measuredHeight / 2.toFloat())
        mPath.close()
        pathMeasure.setPath(mPath, false)

        canvas.drawPath(mPath, mWavePaint)


//        getCenterPos(mPath,canvas)
        canvas.drawBitmap(
            boat,
            (measuredWidth - boat.width) / 2.toFloat(),
            measuredHeight/2 - boat.height + getCenterPos(mPath).toFloat(),
            null
        )


        shiftWaveMatrix.reset()
        shiftWaveMatrix.postTranslate(shiftOffset, 0f)
        mPath.transform(shiftWaveMatrix)
        mPath.close()
        canvas.drawPath(mPath, mShiftWavePaint)


    }

    private fun getCenterPos(path: Path) :Int{
        val region=Region(measuredWidth/2,measuredHeight/2-mWaveHeight.toInt(),measuredWidth/2+100,measuredHeight/2+mWaveHeight.toInt())
        val newRegin=Region()
        val res=newRegin.setPath(path,region)
        val rect=newRegin.bounds
        return rect.top-measuredHeight/2
    }
}
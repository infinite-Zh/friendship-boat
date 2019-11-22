package cn.infinite.friendship

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.abs

/**
 * @author bug小能手
 * Created on 2019/11/19.
 */
class FriendShip : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, i: Int) : super(context, attrs, i)

    private var mWaveWidth = 1500f
    private var mWaveHeight = 150f
    private var shiftOffset = mWaveWidth * 0.5.toFloat()
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

//    private val mHelperPaint = Paint().apply {
//        color = Color.RED
//        strokeWidth = 5f
//        style = Paint.Style.STROKE
//
//        pathEffect = DashPathEffect(floatArrayOf(30f, 3f), 5f)
//
//    }
//
//    private val mPointPaint = Paint().apply {
//        color = Color.BLACK
//        strokeWidth = 10f
//        style = Paint.Style.FILL_AND_STROKE
//
//
//    }
//
//    private val mResultrPaint = Paint().apply {
//        color = Color.GREEN
//        strokeWidth = 5f
//        style = Paint.Style.STROKE
//
//    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        generatePath()
        startAnim()

    }

    private fun startAnim() {
        val valueAnim = ValueAnimator.ofFloat(-mWaveWidth - shiftOffset, -shiftOffset)
        valueAnim.duration = 2000
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

    private val helperPath = Path()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {

            translate(offset, 0f)
            helperPath.reset()
            helperPath.moveTo(measuredWidth / 2  - offset, 0f)
            helperPath.rLineTo(0f, measuredHeight.toFloat() / 2 + mWaveHeight)
            helperPath.rLineTo(-40f, 0f)
            helperPath.rLineTo(0f, -measuredHeight.toFloat() - mWaveHeight )
            helperPath.close()
//            drawPath(helperPath, mHelperPaint)
            helperPath.op(mPath, Path.Op.INTERSECT)

            pathMeasure.setPath(helperPath, false)

            val l = pathMeasure.length
            pathMeasure.getPosTan(l - 20, pos, tan)
            pathMeasure.getMatrix(
                l - 20,
                pathMatrix,
                PathMeasure.POSITION_MATRIX_FLAG or PathMeasure.TANGENT_MATRIX_FLAG
            )

            pathMatrix.postTranslate(-boat.width/2.toFloat(),-boat.height.toFloat())
            drawBitmap(boat, pathMatrix, null)

//            drawPoint(pos[0], pos[1], mPointPaint)
//
//            drawPath(helperPath, mResultrPaint)

            drawPath(mPath, mWavePaint)

            translate(shiftOffset, 0f)
            drawPath(mPath, mShiftWavePaint)

        }

    }


    private val pathMeasure = PathMeasure()

    private val boat = BitmapFactory.decodeResource(resources, R.mipmap.ic_boat)

    private fun generatePath() {

        val num = ((measuredWidth + mWaveWidth + shiftOffset) / mWaveWidth).toInt() + 1
        mPath.reset()
        val startPoint = -mWaveWidth - shiftOffset
        mPath.moveTo(startPoint, (measuredHeight - mWaveHeight * 2) / 2.toFloat())
        for (i in 0..num) {
            mPath.quadTo(
                mWaveWidth / 4 + mWaveWidth.times(i) + startPoint,
                mWaveHeight + measuredHeight / 2,
                mWaveWidth / 2 + mWaveWidth.times(i) + startPoint,
                measuredHeight / 2.toFloat()
            )

            mPath.quadTo(
                3 * mWaveWidth / 4 + mWaveWidth.times(i) + startPoint,
                measuredHeight / 2 - mWaveHeight,
                mWaveWidth + mWaveWidth.times(i) + startPoint,
                measuredHeight / 2.toFloat()
            )
        }

        getCenterPositionForSingle(mPath)

        mPath.rLineTo(0f, measuredHeight / 2.toFloat())
        mPath.lineTo(-2 * mWaveWidth, measuredHeight.toFloat())
        mPath.lineTo(-2 * mWaveWidth, measuredHeight / 2.toFloat())

        mPath.close()

    }

    private var centerGot = false
    private var centerDistance = 0f
    private fun getCenterPositionForSingle(path: Path) {
        if (centerGot) {
            return
        }
        pathMeasure.setPath(path, false)
        val length = pathMeasure.length.toInt()
        for (i in 0 until length) {
            pathMeasure.getPosTan(i.toFloat(), pos, tan)
//            Log.d("center", "x=${pos[0]},y=${pos[1]} distance=$i")
            if (abs(pos[0] - measuredWidth / 2.toFloat()) < 5) {
                Log.e("center", "x=${pos[0]},y=${pos[1]} distance=$i")
                centerDistance = i.toFloat()
                centerGot = true
                break
            }
        }

    }

    private val pos = floatArrayOf(0f, 0f)
    private val tan = floatArrayOf(0f, 0f)
    private var pathMatrix: Matrix = Matrix()

}
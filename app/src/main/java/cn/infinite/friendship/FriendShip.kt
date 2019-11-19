package cn.infinite.friendship

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * @author bug小能手
 * Created on 2019/11/19.
 */
class FriendShip : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, i: Int) : super(context, attrs, i)

    private var mWaveWidth = 100f
    private var mWaveHeight = 100f
    private val mWavePaint = Paint().apply {
        color = Color.parseColor("#00a1ff")
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        offset=-w.toFloat()
    }

    private var mPath = Path()
    private var offset = 0f
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        generatePath()
        canvas?.drawPath(mPath, mWavePaint)

        offset += 1f
        if (offset== 0f) {
            offset = -measuredWidth.toFloat()
        }
        invalidate()
    }

    private fun generatePath() {

        val num = measuredWidth / (mWaveWidth.times(2)).toInt() + 1
        mPath.reset()
        mPath.moveTo(0f + offset, measuredHeight / 2.toFloat())

        for (i in 1 until num.times(2)) {
                mPath.quadTo(
                    mWaveWidth.times(i) / 2 + offset, mWaveHeight + measuredHeight / 2,
                    mWaveWidth.times(i) + offset, measuredHeight / 2.toFloat()
                )

                mPath.quadTo(
                    3 * mWaveWidth.times(i) / 2 + offset, measuredHeight / 2 - mWaveHeight,
                    mWaveWidth.times(i) * 2 + offset, measuredHeight / 2.toFloat()
                )
            }
        }

}
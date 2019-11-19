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
        style=Paint.Style.STROKE
    }

    private var mPath=Path()
    private var mOffset=0f
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.translate(0f,500f)
        mPath.moveTo(0f+mOffset,0f+mOffset)

        mPath.quadTo(mWaveWidth/2+mOffset,mWaveHeight,mWaveWidth+mOffset,0f)
//        mPath.rQuadTo()
        canvas?.drawPath(mPath,mWavePaint)
    }
}
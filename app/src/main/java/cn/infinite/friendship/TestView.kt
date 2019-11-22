package cn.infinite.friendship

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.PI
import kotlin.math.atan2

class TestView: View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, i: Int) : super(context, attrs, i)

    private val pathPaint=Paint().apply {
        color= Color.BLACK
        strokeWidth=5f
        style= Paint.Style.STROKE
    }

    private val regionPaint=Paint().apply {
        color= Color.BLUE
        strokeWidth=5f
        style= Paint.Style.STROKE
    }

    private val path= Path()

    private val pathMeasure=PathMeasure()

    private val pos= floatArrayOf(0f,0f)
    private val tan= floatArrayOf(0f,0f)
    private val region=Region(100,0,300,400)
    private val bitmapMatrix=Matrix()

    private val boat = BitmapFactory.decodeResource(resources, R.mipmap.ic_boat)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        path.moveTo(0f,0f)
//        path.lineTo(100f,100f)
        canvas?.apply {
            drawPoint(200f,200f,pointPaint)
            translate(200f,200f)
            bitmapMatrix.setRotate(
                -90f,boat.width/2.toFloat(),boat.height/2.toFloat()
            )
            drawBitmap(boat,bitmapMatrix,null)

        }



    }

   private val pointPaint=Paint().apply {
        color=Color.DKGRAY
        strokeWidth=20f
        style= Paint.Style.FILL_AND_STROKE
    }

    fun log(msg:String){
        Log.e("test",msg)
    }
}
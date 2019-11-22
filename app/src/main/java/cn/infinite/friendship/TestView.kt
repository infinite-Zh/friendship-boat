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
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        path.moveTo(0f,0f)
//        path.lineTo(100f,100f)
        canvas?.apply {

            path.reset()

            path.moveTo(100f,100f)

            path.quadTo(200f,200f,400f,200f)

            drawCircle(200f,200f,10f,pathPaint)
            drawCircle(400f,200f,10f,pathPaint)

            path.rLineTo(0f,200f)
            path.rLineTo(-400f,0f)
            path.rLineTo(0f,-300f)
            drawPath(path,pathPaint)

            val rect=region.bounds

            drawRect(rect,regionPaint)

            val newRegion=Region()

            newRegion.setPath(path,region)
            regionPaint.color=Color.RED
            val newPath=newRegion.boundaryPath
            drawPath(newPath,regionPaint)

            pathMeasure.setPath(newPath,false)
            val l=pathMeasure.length

            for (i in 0 until l.toInt()){
                pathMeasure.getPosTan(i.toFloat(),pos,tan)
                log("x=${tan[0]},y=${tan[1]}")
                if (i%20==0) {
                    drawCircle(pos[0], pos[1], 10f, pathPaint)
                }
            }

//            log("x=${tan[0]},y=${tan[1]}")
//
//            log("degree=${atan2(tan[1],tan[0])*180/ PI}")



            drawPoint(pos[0],pos[1],pointPaint)
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
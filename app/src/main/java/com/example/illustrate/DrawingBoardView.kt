package com.example.illustrate

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawingBoardView(context:Context,attrs:AttributeSet) : View(context, attrs) {
    private var drawingPath: CustomPath? = null
    private var canvasBitmap: Bitmap? = null
    private var drawingPaint: Paint? = null
    private var canvasPaint: Paint? = null
    private var canvas: Canvas? = null
    private var brushSize:Float = 0.0F
    private var color = Color.RED

    private val pathsList = ArrayList<CustomPath>()

    init {
        setUpDrawing()
    }

    private fun setUpDrawing(){
        drawingPaint = Paint()

        drawingPaint!!.color = color
        drawingPaint!!.style = Paint.Style.STROKE
        drawingPaint!!.strokeJoin = Paint.Join.ROUND
        drawingPaint!!.strokeCap = Paint.Cap.ROUND

        brushSize = 15.0F

        canvasPaint = Paint(Paint.DITHER_FLAG)
        drawingPath = CustomPath(color,brushSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        canvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)

        canvas = Canvas(canvasBitmap!!)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawBitmap(canvasBitmap!!,0F,0F,canvasPaint)
    // for on going display
        if(! drawingPath!!.isEmpty){
            drawingPaint!!.strokeWidth = drawingPath!!.brushThickness
            drawingPaint!!.color = drawingPath!!.color
            canvas?.drawPath(drawingPath!!,drawingPaint!!)
        }
        // for persistant
        for (path in pathsList){
                drawingPaint!!.strokeWidth = path.brushThickness
                drawingPaint!!.color = path.color
                canvas?.drawPath(path,drawingPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                drawingPath!!.color = color
                drawingPath!!.brushThickness = brushSize

                drawingPath!!.reset() //safer side call so that nothing of the previous path is remaining

                drawingPath!!.moveTo(touchX!!, touchY!!)
                /*
                * if(touchX != null && touchY != null){
                *   drawingPath!!.moveTo(touchX,touchY)
                * }
                * */
            }
            MotionEvent.ACTION_MOVE ->{
                if(touchX != null && touchY != null){
                    drawingPath!!.lineTo(touchX, touchY)
                }
            }
            MotionEvent.ACTION_UP -> {
                pathsList.add(drawingPath!!)
                drawingPath = CustomPath(color,brushSize)
            }

            else -> return false
        }

        invalidate()
        return true
    }
    fun setBrushSize(newBrushSize: Float){
        brushSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            newBrushSize,
            resources.displayMetrics
        )
        drawingPaint!!.strokeWidth = brushSize
    }

    fun setColor(newColor: Int){
        color = newColor
        drawingPaint!!.color = newColor
    }

    internal inner class CustomPath(var color: Int, var brushThickness:Float):Path(){

    }
}
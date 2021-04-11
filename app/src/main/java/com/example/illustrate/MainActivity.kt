package com.example.illustrate

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private lateinit var drawingView: DrawingBoardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView = findViewById<DrawingBoardView>(R.id.drawingView)

        drawingView.setBrushSize(5.0F)
    }

    public fun onShowBrushDialog(view: View){
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush)
        brushDialog.setTitle("Select Brush Size")

        brushDialog.show()

        val smallBrushBtn = brushDialog.findViewById<ImageButton>(R.id.btnSmallBrush)
        smallBrushBtn.setOnClickListener{
            drawingView.setBrushSize(4.0F)
            brushDialog.dismiss()
        }

        val mediumBrushBtn = brushDialog.findViewById<ImageButton>(R.id.btnMediumBrush)
        mediumBrushBtn.setOnClickListener{
            drawingView.setBrushSize(8.0F)
            brushDialog.dismiss()
        }

        val largeBrushBtn = brushDialog.findViewById<ImageButton>(R.id.btnLargeBrush)
        largeBrushBtn.setOnClickListener{
            drawingView.setBrushSize(12.0F)
            brushDialog.dismiss()
        }
    }
}
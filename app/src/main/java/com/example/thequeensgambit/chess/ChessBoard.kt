package com.example.thequeensgambit.chess

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.thequeensgambit.R
import kotlin.math.min


private const val TAG = "ChessBoard"

class ChessBoard(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint()
    private val scaleFactor = 1.0f
    private var cellSize = 130f
    private var originX = 20f // From left -> right
    private var originY = 200f // From top -> bottom
    private val lightColor = Color.parseColor("#EEEEEE")
    private val darkColor = Color.parseColor("#BBBBBB")

    private val imgResIDs = setOf(
        R.drawable.bishop_black,
        R.drawable.bishop_white,
        R.drawable.king_black,
        R.drawable.king_white,
        R.drawable.queen_black,
        R.drawable.queen_white,
        R.drawable.rook_black,
        R.drawable.rook_white,
        R.drawable.knight_black,
        R.drawable.knight_white,
        R.drawable.pawn_black,
        R.drawable.pawn_white,
    )

    /**
     * Map resource id and bitmap
     */
    private val bitmaps = mutableMapOf<Int, Bitmap>()

    init {
        loadBitmaps()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val smaller = min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(smaller, smaller)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
        val chessboardSize = min(width, height) * scaleFactor
        cellSize = chessboardSize / 8f
        originX = (width - chessboardSize) / 2f
        originY = (height - chessboardSize) / 2f

        drawChessboard(canvas)

        val white_queen = bitmaps[R.drawable.queen_white]!!
        canvas.drawBitmap(white_queen, null, Rect(0, 0, 600, 600), paint)

    }

    private fun drawChessboard(canvas: Canvas) {
        for (col in 0..7) {
            for (row in 0..7) {
                drawSquare(canvas, col, row, (col + row) % 2 == 1)
            }
        }
    }

    private fun drawSquare(canvas: Canvas, col: Int, row: Int, isDark: Boolean) {
        paint.color =
            if (isDark) darkColor else lightColor
        canvas.drawRect(
            originX + col * cellSize,
            originY + row * cellSize,
            originX + (col + 1) * cellSize,
            originY + (row + 1) * cellSize,
            paint
        )
    }

    private fun loadBitmaps() {
        imgResIDs.forEach {
            bitmaps[it] = BitmapFactory.decodeResource(resources, it)
        }
    }
}
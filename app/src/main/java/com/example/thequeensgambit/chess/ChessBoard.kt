package com.example.thequeensgambit.chess

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.thequeensgambit.R
import kotlin.math.min


const val TAG = "ChessBoard"

/**
 * This is VIEW
 */
class ChessBoard(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint()
    private val scaleFactor = 0.9f
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

    var chessDelegate: ChessDelegate? = null
    var fromCol: Int = -1
    var fromRow: Int = -1
    var movingPieceX: Float = -1f
    var movingPieceY: Float = -1f
    var movingPieceBitmap: Bitmap? = null
    var movingPiece: ChessPiece? = null

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
        drawPieces(canvas)
    }

    private fun drawChessboard(canvas: Canvas?) {
        for (col in 0 until 8)
            for (row in 0 until 8)
                drawSquareAt(canvas, col, row, (col + row) % 2 == 1)
    }

    private fun drawSquareAt(canvas: Canvas?, col: Int, row: Int, isDark: Boolean) {
        paint.color =
            if (isDark) darkColor else lightColor
        canvas?.drawRect(
            originX + col * cellSize,
            originY + row * cellSize,
            originX + (col + 1) * cellSize,
            originY + (row + 1) * cellSize,
            paint
        )
    }

    private fun drawPieces(canvas: Canvas?) {
        for (row in 0 until 8)
            for (col in 0 until 8)
                chessDelegate?.pieceAt(col, row)?.let {
                    if (it != movingPiece)
                        drawPieceAt(canvas, col, row, it.resID)
                }

        movingPieceBitmap?.let {
            canvas?.drawBitmap(
                it,
                null,
                RectF(
                    movingPieceX - cellSize / 2,
                    movingPieceY - cellSize / 2,
                    movingPieceX + cellSize / 2,
                    movingPieceY + cellSize / 2
                ),
                paint
            )
        }
    }

    private fun drawPieceAt(canvas: Canvas?, col: Int, row: Int, resID: Int) {
        val piece = bitmaps[resID]!!
        canvas?.drawBitmap(
            piece,
            null,
            RectF(
                originX + col * cellSize,
                originY + (7 - row) * cellSize,
                originX + (col + 1) * cellSize,
                originY + ((7 - row) + 1) * cellSize
            ),
            paint
        )
    }

    private fun loadBitmaps() {
        imgResIDs.forEach {
            bitmaps[it] = BitmapFactory.decodeResource(resources, it)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                fromCol = ((event.x - originX) / cellSize).toInt()
                fromRow = 7 - ((event.y - originY) / cellSize).toInt()

                chessDelegate?.pieceAt(fromCol, fromRow)?.let {
                    movingPiece = it
                    movingPieceBitmap = bitmaps[it.resID]
                }

            }
            MotionEvent.ACTION_UP -> {
                val col = ((event.x - originX) / cellSize).toInt()
                val row = 7 - ((event.y - originY) / cellSize).toInt()
                if (fromCol != col || fromRow != row) {
                    chessDelegate?.movePieceAt(fromCol, fromRow, col, row)
                }
                movingPiece = null
                movingPieceBitmap = null
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                movingPieceX = event.x
                movingPieceY = event.y
                invalidate()
            }
        }
        return true
    }
}
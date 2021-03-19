package com.example.thequeensgambit

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.thequeensgambit.chess.ChessBoard
import com.example.thequeensgambit.chess.ChessDelegate
import com.example.thequeensgambit.chess.ChessModel
import com.example.thequeensgambit.chess.ChessPiece

const val TAG = "MainActivity"

/**
 * Model - View - Controller
 */
class MainActivity : AppCompatActivity(), ChessDelegate {
    var chessModel = ChessModel()
    lateinit var chessView: ChessBoard
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "$chessModel")

        chessView = findViewById<ChessBoard>(R.id.chess_board).apply {
            this.chessDelegate = this@MainActivity
        }
        findViewById<Button>(R.id.btn_reset).setOnClickListener {
            chessModel.reset()
            chessView.invalidate()
        }
    }

    override fun pieceAt(col: Int, row: Int): ChessPiece? {
        return chessModel.pieceAt(col, row)
    }

    override fun movePieceAt(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {

        chessModel.movePieceAt(fromCol, fromRow, toCol, toRow)
        findViewById<ChessBoard>(R.id.chess_board)?.invalidate()
    }
}
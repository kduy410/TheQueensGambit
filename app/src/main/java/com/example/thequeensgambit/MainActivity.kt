package com.example.thequeensgambit

import android.os.Bundle
import android.util.Log
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "$chessModel")

        var chessBoard = findViewById<ChessBoard>(R.id.chess_board).let {
            it.chessDelegate = this
        }
    }

    override fun pieceAt(col: Int, row: Int): ChessPiece? {
        return chessModel.pieceAt(col, row)
    }
}
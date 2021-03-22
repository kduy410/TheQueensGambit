package com.example.thequeensgambit

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.thequeensgambit.chess.ChessBoard
import com.example.thequeensgambit.chess.ChessDelegate
import com.example.thequeensgambit.chess.ChessGame
import com.example.thequeensgambit.chess.ChessPiece
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import java.util.concurrent.Executors

const val TAG = "MainActivity"

/**
 * Model - View - Controller
 */
class MainActivity : AppCompatActivity(), ChessDelegate {
    private lateinit var chessView: ChessBoard
    private var printWriter: PrintWriter? = null
    private val PORT: Int = 50001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "$ChessGame")

        chessView = findViewById<ChessBoard>(R.id.chess_board).apply {
            this.chessDelegate = this@MainActivity
        }
        findViewById<Button>(R.id.btn_reset).setOnClickListener {
            ChessGame.reset()
            chessView.invalidate()
        }
        /**
         * On Device
         * Settings | Connections(WI-FI) | (active network) | IP address(192.168.232.2)
         *
         * auth vPaivCKgUP4BXE0R
         * redir add tcp:50000:50001
         */

        findViewById<Button>(R.id.btn_connect).setOnClickListener {
            Log.d(TAG, "Socket  client connecting to port:[$PORT]...")
            Executors.newSingleThreadExecutor().execute {
                try {
                    val socket = Socket("192.168.232.2", PORT) // 172.16.2.4
                    receiveMove(socket)
                } catch (e: Exception) {
                    e.printStackTrace()
                    throw e
                }
            }

        }
        findViewById<Button>(R.id.btn_listen).setOnClickListener {
            Log.d(TAG, "Socket  client listening to port:[$PORT]...")
            Executors.newSingleThreadExecutor().execute {
                val serverSocket = ServerSocket(PORT)
                val socket = serverSocket.accept()
                receiveMove(socket)
            }
        }
    }

    override fun pieceAt(col: Int, row: Int): ChessPiece? {
        return ChessGame.pieceAt(col, row)
    }

    override fun movePieceAt(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        Log.d(TAG, "$fromCol,$fromRow,$toCol,$toRow")
        if (fromCol == toCol && fromRow == toRow) {
            return
        }
        ChessGame.movePieceAt(fromCol, fromRow, toCol, toRow)
        chessView.invalidate()
        // When don't use socket it will not create a new Thread
        printWriter?.let {
            val moveStr = "$fromCol,$fromRow,$toCol,$toRow"
            Executors.newSingleThreadExecutor().execute {
                it.println(moveStr)
            }
        }
    }

    override fun receiveMove(socket: Socket?) {
        socket?.let {
            val scanner = Scanner(socket.getInputStream())
            printWriter = PrintWriter(socket.getOutputStream(), true)
            while (scanner.hasNextLine()) {
                val move: List<Int> = scanner.nextLine().split(",").map {
                    it.toInt()
                }
                runOnUiThread {
//                    movePieceAt(move[0], move[1], move[2], move[3]) // L - T - R - B
                    ChessGame.movePieceAt(move[0], move[1], move[2], move[3])
                    chessView.invalidate()
                }
            }
        }
    }
}
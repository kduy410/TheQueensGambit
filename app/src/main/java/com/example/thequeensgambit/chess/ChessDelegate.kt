package com.example.thequeensgambit.chess

import java.net.Socket

interface ChessDelegate {
    fun pieceAt(col: Int, row: Int): ChessPiece?
    fun movePieceAt(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int)
    fun receiveMove(socket: Socket?)
}
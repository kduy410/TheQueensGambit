package com.example.thequeensgambit.chess

interface ChessDelegate {
    fun pieceAt(col: Int, row: Int): ChessPiece?
    fun movePieceAt(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int)
}
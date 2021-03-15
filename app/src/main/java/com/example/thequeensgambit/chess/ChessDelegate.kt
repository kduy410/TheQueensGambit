package com.example.thequeensgambit.chess

interface ChessDelegate {
    fun pieceAt(col: Int, row: Int): ChessPiece?
}
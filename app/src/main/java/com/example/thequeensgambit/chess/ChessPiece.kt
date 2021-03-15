package com.example.thequeensgambit.chess

data class ChessPiece(
    var column: Int,
    var row: Int,
    val player: ChessPlayer,
    val rank: ChessRank
)
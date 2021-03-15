package com.example.thequeensgambit.chess

import com.example.thequeensgambit.R

/**
 * This is MODEL
 */

class ChessModel {
    var pieces = mutableSetOf<ChessPiece>()

    init {
        reset()
    }

    /**
     * Return the piece at row and column
     */
    fun pieceAt(col: Int, row: Int): ChessPiece? {
        for (piece in pieces) {
            if (col == piece.column && row == piece.row) {
                return piece
            }
        }
        return null
    }

    private fun reset() {
        pieces.removeAll(pieces)
        for (i in 0..1) {
            pieces.add(
                ChessPiece(
                    0 + i * 7,
                    0,
                    ChessPlayer.WHITE,
                    ChessRank.ROOK,
                    R.drawable.rook_white
                )
            )
            pieces.add(
                ChessPiece(
                    0 + i * 7,
                    7,
                    ChessPlayer.BLACK,
                    ChessRank.ROOK, R.drawable.rook_black
                )
            )

            pieces.add(
                ChessPiece(
                    1 + (i * 5),
                    0,
                    ChessPlayer.WHITE,
                    ChessRank.KNIGHT,
                    R.drawable.knight_white
                )
            )
            pieces.add(
                ChessPiece(
                    1 + (i * 5),
                    7,
                    ChessPlayer.BLACK,
                    ChessRank.KNIGHT,
                    R.drawable.knight_black
                )
            )

            pieces.add(
                ChessPiece(
                    2 + (i * 3),
                    0,
                    ChessPlayer.WHITE,
                    ChessRank.BISHOP,
                    R.drawable.bishop_white
                )
            )
            pieces.add(
                ChessPiece(
                    2 + (i * 3),
                    7,
                    ChessPlayer.BLACK,
                    ChessRank.BISHOP,
                    R.drawable.bishop_black
                )
            )
        }
        for (i in 0..7) {
            pieces.add(ChessPiece(i, 1, ChessPlayer.WHITE, ChessRank.PAWN, R.drawable.pawn_white))
            pieces.add(ChessPiece(i, 6, ChessPlayer.BLACK, ChessRank.PAWN, R.drawable.pawn_black))
        }

        pieces.add(ChessPiece(3, 0, ChessPlayer.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        pieces.add(ChessPiece(3, 7, ChessPlayer.BLACK, ChessRank.QUEEN, R.drawable.queen_black))

        pieces.add(ChessPiece(4, 0, ChessPlayer.WHITE, ChessRank.KING, R.drawable.king_white))
        pieces.add(ChessPiece(4, 7, ChessPlayer.BLACK, ChessRank.KING, R.drawable.king_black))
    }

    override fun toString(): String {

        var desc = " \n"

        for (row in 7 downTo 0) {
            desc += "$row"
            for (col in 0..7) {
                val piece = pieceAt(col, row)
                if (piece == null) {
                    desc += " ."
                } else {
                    val isWhite = piece.player == ChessPlayer.WHITE
                    desc += " "
                    desc += when (piece.rank) {
                        ChessRank.KING -> {
                            if (isWhite) "k" else "K"
                        }
                        ChessRank.QUEEN -> {
                            if (isWhite) "q" else "Q"
                        }
                        ChessRank.BISHOP -> {
                            if (isWhite) "b" else "B"
                        }
                        ChessRank.ROOK -> {
                            if (isWhite) "r" else "R"
                        }
                        ChessRank.KNIGHT -> {
                            if (isWhite) "n" else "N"
                        }
                        else -> {
                            if (isWhite) "p" else "P"
                        }

                    }
                }
            }
            desc += " \n"
        }

        desc += "  A B C D E F G H"
        return desc
    }

    fun movePieceAt(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        if (fromCol == toCol && fromRow == toRow) return
        // Get piece at touch's location
        val movingPiece = pieceAt(fromCol, fromRow) ?: return
        // Get piece at desired location
        // If another piece is already there -> removing it
        pieceAt(toCol, toRow)?.let {
            if (movingPiece.player == it.player) {
                return
            }
            pieces.remove(it)
        }
        pieces.remove(movingPiece)
        pieces.add(
            ChessPiece(
                toCol,
                toRow,
                movingPiece.player,
                movingPiece.rank,
                movingPiece.resID
            )
        )
    }
}
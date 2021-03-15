package com.example.thequeensgambit.chess

class ChessModel {
    var pieces = mutableSetOf<ChessPiece>()

    init {
        reset()
    }

    /**
     * Return the piece at row and column
     */
    private fun pieceAt(col: Int, row: Int): ChessPiece? {
        pieces.forEach {
            if (col == it.column && row == it.row) {
                return it
            }
        }
        return null
    }

    private fun reset() {
        pieces.removeAll(pieces)
        for (i in 0..1) {
            pieces.add(ChessPiece(0 + i * 7, 0, ChessPlayer.WHITE, ChessRank.ROOK))
            pieces.add(ChessPiece(0 + i * 7, 7, ChessPlayer.BLACK, ChessRank.ROOK))

            pieces.add(ChessPiece(1 + (i * 5), 0, ChessPlayer.WHITE, ChessRank.KNIGHT))
            pieces.add(ChessPiece(1 + (i * 5), 7, ChessPlayer.BLACK, ChessRank.KNIGHT))

            pieces.add(ChessPiece(2 + (i * 3), 0, ChessPlayer.WHITE, ChessRank.BISHOP))
            pieces.add(ChessPiece(2 + (i * 3), 7, ChessPlayer.BLACK, ChessRank.BISHOP))
        }
        for (i in 0..7) {
            pieces.add(ChessPiece(i, 1, ChessPlayer.WHITE, ChessRank.PAWN))
            pieces.add(ChessPiece(i, 6, ChessPlayer.BLACK, ChessRank.PAWN))
        }

        pieces.add(ChessPiece(3, 0, ChessPlayer.WHITE, ChessRank.QUEEN))
        pieces.add(ChessPiece(3, 7, ChessPlayer.BLACK, ChessRank.QUEEN))

        pieces.add(ChessPiece(4, 0, ChessPlayer.WHITE, ChessRank.KING))
        pieces.add(ChessPiece(4, 7, ChessPlayer.BLACK, ChessRank.KING))
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
}
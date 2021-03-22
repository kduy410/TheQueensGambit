package com.example.thequeensgambit.chess

import org.junit.Assert.*
import org.junit.Test

class ChessGameUnitTest {
    @Test
    fun toString_isCorrect() {
        println(ChessGame)
        assertTrue(ChessGame.toString().contains("0 r n b q k b n r"))
    }

    @Test
    fun pieceAt_isCorrect() {
        assertNotNull(ChessGame.pieceAt(0, 0))
        assertEquals(ChessPlayer.WHITE, ChessGame.pieceAt(0, 0)?.player)
    }

    @Test
    fun reset_isCorrect() {
        assertNull(ChessGame.pieceAt(0, 2))
        ChessGame.movePieceAt(0, 1, 0, 2)
        assertNotNull(ChessGame.pieceAt(0, 2))
        ChessGame.reset()
        assertNull(ChessGame.pieceAt(0, 2))
    }

    @Test
    fun movePieceAt_isCorrect() {
        assertNull(ChessGame.pieceAt(0, 2))
        ChessGame.movePieceAt(0, 1, 0, 2)
        assertNotNull(ChessGame.pieceAt(0, 2))
    }
}
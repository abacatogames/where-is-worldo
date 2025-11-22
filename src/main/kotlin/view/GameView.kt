package io.github.cbaumont.view

import io.github.cbaumont.WordGuess

interface GameView {
    fun displayGuess(guess: WordGuess): String
    fun readInput(): String
    fun displayMessage(message: String): Unit

    companion object
}
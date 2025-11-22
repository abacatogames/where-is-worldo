package io.github.cbaumont

import io.github.cbaumont.WordOfTheDay.LocationOfTheDay
import io.github.cbaumont.view.GameView

class GameLoop(
    val gameView: GameView,
    val maxAttempts: Int = 6,
    val gameIntro: String = "$worldo\nWhere is Worldo today?\nStart by making a guess: ",
    proposedWord: String,
) {
    val wordOfTheDay: WordOfTheDay = WordOfTheDay.of(proposedWord, { it.isLocationValid() }, { LocationOfTheDay(it) })

    init {
        gameView.displayMessage(gameIntro)
    }

    fun mainLoop() {
        var guess = gameView.readInput()

        var currentAttempt = 1

        var result = validateGuess(guess)

        while (!result && ++currentAttempt <= maxAttempts) {
            gameView.displayMessage("Make another guess: ")
            guess = gameView.readInput()
            result = validateGuess(guess)
        }

        if (!result) gameView.displayMessage("You lost :(")
    }

    private fun validateGuess(guess: String): Boolean {
        val wordGuess = WordGuess(guess, wordOfTheDay.value)
        val renderedGuess = gameView.displayGuess(wordGuess)
        if (!guess.isLocationValid()) {
            gameView.displayMessage("Invalid location :(")
            return false
        } else {
            gameView.displayMessage(renderedGuess)
        }
        if (wordGuess.fullMatch) {
            gameView.displayMessage("You won!")
            return true
        }
        return false
    }

    fun softValidation(guess: String): WordGuess? {
        if (!guess.isLocationValid()) {
            gameView.displayMessage("Invalid location :(")
            return null
        }
        val wordGuess = WordGuess(guess, wordOfTheDay.value)
        return wordGuess
    }
}

sealed interface WordOfTheDay {
    val value: String

    @JvmInline
    value class LocationOfTheDay internal constructor(override val value: String) : WordOfTheDay

    companion object {
        fun of(
            rawValue: String?,
            validation: (String) -> Boolean,
            constructor: (String) -> WordOfTheDay
        ): WordOfTheDay = rawValue?.takeIf { validation(it) }?.let { constructor(it) }
            ?: error("Unable to start the game with word: $rawValue")
    }
}
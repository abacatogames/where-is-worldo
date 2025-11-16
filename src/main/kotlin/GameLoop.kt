package io.github.cbaumont

import io.github.cbaumont.WordOfTheDay.LocationOfTheDay

class GameLoop(
    private val gameRendering: GameRendering,
    private val maxAttempts: Int = 6,
    proposedWord: String,
    gameIntro: String = "$worldo\nWhere is Worldo today?\nStart by making a guess: ",
) {
    val wordOfTheDay: WordOfTheDay = WordOfTheDay.of(proposedWord, { it.isLocationValid() }, { LocationOfTheDay(it) })

    init {
        gameRendering.showMessage(gameIntro)
    }

    fun mainLoop() {
        var guess = gameRendering.readUserInput()

        var currentAttempt = 1

        var result = validateGuess(guess)

        while (!result && ++currentAttempt <= maxAttempts) {
            gameRendering.showMessage("Make another guess: ")
            guess = gameRendering.readUserInput()
            result = validateGuess(guess)
        }

        if (!result) gameRendering.showMessage("You lost :(")
    }

    private fun validateGuess(guess: String): Boolean {
        val wordGuess = WordGuess(guess, wordOfTheDay.value)
        val renderedGuess = gameRendering.renderGuess(wordGuess)
        if (!guess.isLocationValid()) {
            gameRendering.showMessage("Invalid location :(")
            return false
        } else {
            gameRendering.showMessage(renderedGuess)
        }
        if (wordGuess.fullMatch) {
            gameRendering.showMessage("You won!")
            return true
        }
        return false
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
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

        var wordGuess = WordGuess(guess, wordOfTheDay.value)
        var fullMatch = wordGuess.fullMatch
        var currentAttempt = 1

        while (!fullMatch && currentAttempt <= maxAttempts) {
            if (!guess.isLocationValid()) {
                gameRendering.showMessage("Invalid location :(")
            } else {
                gameRendering.showMessage(gameRendering.renderGuess(wordGuess))
            }
            gameRendering.showMessage("Make another guess: ")
            guess = gameRendering.readUserInput()
            wordGuess = WordGuess(guess, wordOfTheDay.value)
            fullMatch = wordGuess.fullMatch
            currentAttempt++
        }
        if (fullMatch) {
            gameRendering.showMessage(gameRendering.renderGuess(wordGuess))
            gameRendering.showMessage("You won!")
        } else gameRendering.showMessage("You lost :(")
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
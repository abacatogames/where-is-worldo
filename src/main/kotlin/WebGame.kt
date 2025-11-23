package io.github.cbaumont

import io.github.cbaumont.WordOfTheDay.LocationOfTheDay

class WebGame(
    val maxAttempts: Int,
    val proposedWord: String,
    val gameId: String = "test-id",
) {
    private val previousGuesses: MutableList<WordGuess?> = mutableListOf()
    val validGuesses: List<WordGuess>
        get() = previousGuesses.filterNotNull()
    val wordOfTheDay: WordOfTheDay = WordOfTheDay.of(proposedWord, String::isAValidCountry, ::LocationOfTheDay)
    val attemptsLeft: Int
        get() = maxAttempts - validGuesses.size
    val state: GameState
        get() =
            when {
                validGuesses.lastOrNull()?.fullMatch == true -> GameState.WON
                validGuesses.size >= maxAttempts -> GameState.LOST
                previousGuesses.isEmpty() -> GameState.NEW
                else -> GameState.IN_PROGRESS
            }

    val lastGuessWasInvalid: Boolean
        get() = previousGuesses.isNotEmpty() && previousGuesses.lastOrNull() == null

    fun validateAndAddGuess(guess: String) {
        guess.takeIf(String::isAValidCountry)
            ?.let { WordGuess(it, wordOfTheDay.value) }
            .let { previousGuesses.add(it) }
    }
}

enum class GameState {
    NEW, IN_PROGRESS, WON, LOST
}
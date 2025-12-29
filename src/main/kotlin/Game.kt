package io.github.cbaumont

import io.github.cbaumont.VerifiedWord.VerifiedLocation
import kotlinx.serialization.Serializable

@Serializable
class Game(
    val maxAttempts: Int,
    val proposedWord: String,
) {
    private val previousGuesses: MutableList<WordGuess?> = mutableListOf()
    val validGuesses: List<WordGuess>
        get() = previousGuesses.filterNotNull()
    val verifiedWord: VerifiedWord = VerifiedWord.of(proposedWord, String::isAValidCountry, ::VerifiedLocation)
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
            ?.let { WordGuess(it, verifiedWord.value) }
            .let { previousGuesses.add(it) }
    }
}

enum class GameState {
    NEW, IN_PROGRESS, WON, LOST
}
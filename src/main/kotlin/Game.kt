package io.github.cbaumont

import io.github.cbaumont.word.VerifiedWord
import io.github.cbaumont.word.WordGuess

class Game(
    val maxAttempts: Int = 6,
    val proposedWord: String,
    val validator: (String) -> Boolean,
    guesses: List<WordGuess?> = listOf(),
) {
    private val verifiedWord: VerifiedWord =
        VerifiedWord.of(proposedWord, validator) ?: error("Unable to start the game with word: $proposedWord")
    private val mutableGuesses = guesses.toMutableList()

    val allGuesses: List<WordGuess?>
        get() = mutableGuesses.toList()
    val validGuesses: List<WordGuess>
        get() = mutableGuesses.filterNotNull()
    val attemptsLeft: Int
        get() = maxAttempts - validGuesses.size
    val state: GameState
        get() =
            when {
                validGuesses.lastOrNull()?.fullMatch == true -> GameState.WON
                validGuesses.size >= maxAttempts -> GameState.LOST
                mutableGuesses.isEmpty() -> GameState.NEW
                else -> GameState.IN_PROGRESS
            }
    val lastGuessWasInvalid: Boolean
        get() = mutableGuesses.isNotEmpty() && mutableGuesses.lastOrNull() == null

    fun validateAndAddGuess(guess: String) {
        VerifiedWord.of(guess, validator)?.value
            ?.let { WordGuess(it, verifiedWord.value) }
            .let { wordGuess ->
                mutableGuesses.removeAll { it == null }
                mutableGuesses.add(wordGuess)
            }
    }
}

enum class GameState {
    NEW, IN_PROGRESS, WON, LOST
}

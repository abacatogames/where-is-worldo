package io.github.cbaumont

class Game(
    val maxAttempts: Int = 6,
    val proposedWord: String,
    val validator: (String) -> Boolean,
    val previousGuesses: MutableList<WordGuess?> = mutableListOf()
) {
    private val verifiedWord: VerifiedWord =
        VerifiedWord.of(proposedWord, validator) ?: error("Unable to start the game with word: $proposedWord")
    val validGuesses: List<WordGuess>
        get() = previousGuesses.filterNotNull()
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
        VerifiedWord.of(guess, validator)?.value
            ?.let { WordGuess(it, verifiedWord.value) }
            .let { wordGuess ->
                previousGuesses.removeAll { it == null }
                previousGuesses.add(wordGuess)
            }
    }
}

enum class GameState {
    NEW, IN_PROGRESS, WON, LOST
}

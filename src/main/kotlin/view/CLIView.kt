package io.github.cbaumont.view

import io.github.cbaumont.Game
import io.github.cbaumont.GameState
import io.github.cbaumont.WordGuess
import io.github.cbaumont.view.CLIColours.DEFAULT
import io.github.cbaumont.view.CLIColours.GREEN

fun interface CLIView : (Game) -> String {
    companion object {
        fun create(): CLIView =
            object : CLIView {
                override fun invoke(game: Game): String =
                    listOfNotNull(
                        if (!game.lastGuessWasInvalid) gameBoard(game.validGuesses.lastOrNull()) else null,
                        instructions(game)
                    ).joinToString("\n")

                private fun instructions(game: Game): String =
                    when (game.state) {
                        GameState.NEW -> "Start by making a guess."
                        GameState.IN_PROGRESS -> {
                            if (game.lastGuessWasInvalid) {
                                "Invalid country, please make another guess."
                            } else {
                                "You have ${game.attemptsLeft} attempts left. Make another guess."
                            }
                        }

                        GameState.WON -> "Congratulations, you found Worldo!"
                        GameState.LOST -> "You’re out of attempts for today — better luck tomorrow!"
                    }

                private fun gameBoard(guess: WordGuess?): String? {
                    return guess?.matches?.map {
                        if (it.value) {
                            guess.value[it.key].green
                        } else guess.value[it.key]
                    }?.joinToString(" ")
                }
            }
    }

}

private val Char.green
    get() = "${GREEN.code}$this${DEFAULT.code}"

enum class CLIColours(val code: String) {
    GREEN("\u001b[32m"),
    DEFAULT("\u001b[0m")
}

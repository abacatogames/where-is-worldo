package io.github.cbaumont.view

import io.github.cbaumont.GameState
import io.github.cbaumont.WebGame
import io.github.cbaumont.WordGuess
import io.github.cbaumont.styles
import kotlinx.html.FlowContent
import kotlinx.html.FormMethod
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.stream.createHTML
import kotlinx.html.style
import kotlinx.html.textInput
import kotlinx.html.title


fun interface WebView : (WebGame) -> String {
    companion object {
        fun create(): WebView =
            object : WebView {
                override fun invoke(game: WebGame): String =
                    createHTML().html {
                        titleAndStyle()
                        body {
                            div("container") {
                                h1 { +"Where is Worldo today?" }
                                h2 { +"Worldo may be in any country in the world." }
                                instructionsAndInput(game)
                                gameBoard(game.validGuesses)
                            }
                        }
                    }

                private fun HTML.titleAndStyle(): Unit =
                    head {
                        title { +"Where is Worldo?" }
                        style { +styles.toString() }
                    }

                private fun FlowContent.guessForm() =
                    form(action = "/", method = FormMethod.post) {
                        textInput(name = "guess") {
                            placeholder = "TYPE HERE"
                            autoFocus = true
                        }
                    }

                private fun FlowContent.gameBoard(validGuesses: List<WordGuess>) =
                    div("board") {
                        for (guess in validGuesses) {
                            div("row") {
                                guess.matches.map {
                                    val cls = if (it.value) "correct" else "absent"
                                    div("tile $cls") {
                                        +guess.value[it.key].toString()
                                    }
                                }
                            }
                        }
                    }

                private fun FlowContent.instructionsAndInput(game: WebGame) {
                    return when (game.state) {
                        GameState.WON -> h2("won") { +"Congratulations, you found Worldo!" }
                        GameState.LOST -> h2("lost") { +"You’re out of attempts for today — better luck tomorrow!" }
                        GameState.NEW -> {
                            h2 { +"Start by making a guess." }
                            guessForm()
                        }

                        GameState.IN_PROGRESS -> {
                            if (game.lastGuessWasInvalid) {
                                h2("invalid") { +"Invalid country, please make another guess." }
                            } else {
                                h2 { +"You have ${game.attemptsLeft} attempts left. Make another guess." }
                            }
                            guessForm()
                        }
                    }
                }
            }
    }
}
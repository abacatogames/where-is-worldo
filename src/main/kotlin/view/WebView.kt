package io.github.cbaumont.view

import io.github.cbaumont.GameLoop
import io.github.cbaumont.WordGuess
import io.github.cbaumont.styles
import io.ktor.server.request.receiveParameters
import io.ktor.server.routing.RoutingCall
import kotlinx.coroutines.runBlocking
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

fun GameView.Companion.webView(call: RoutingCall? = null): GameView =
    object : GameView {
        override fun displayGuess(guess: WordGuess): String {
            val div =
                createHTML().div("row") {
                    guess.matches.map {
                        val cls = if (it.value) "correct" else "absent"
                        div("tile $cls") { +guess.value[it.key].toString() }
                    }
                }
            return div
        }

        override fun readInput(): String {
            return runBlocking { call?.receiveParameters()?.get("guess") }?.trim() ?: ""
        }

        override fun displayMessage(message: String) {}
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

private fun FlowContent.gameInstructions(previousGuesses: List<WordGuess?>, game: GameLoop) {
    val validGuesses = previousGuesses.filterNotNull()
    return if (validGuesses.lastOrNull()?.fullMatch == true) {
        h2("won") { +"Congratulations, you found Worldo!" }
    } else if (validGuesses.size >= game.maxAttempts) {
        h2("lost") { +"You’re out of attempts for today — better luck tomorrow!" }
    } else {
        if (previousGuesses.isNotEmpty() && previousGuesses.lastOrNull() == null) {
            h2("invalid") { +"Invalid country, please make another guess." }
        } else if (previousGuesses.isEmpty()) {
            h2 { +"Start by making a guess." }
        } else {
            h2 {
                +"You have ${game.maxAttempts - validGuesses.size} attempts left. Make another guess."
            }
        }
        guessForm()
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

fun generatePage(game: GameLoop, previousGuesses: List<WordGuess?>): String =
    createHTML().html {
        titleAndStyle()
        body {
            div("container") {
                h1 { +game.gameIntro }
                h2 { +"Worldo may be in any country in the world." }
                gameInstructions(previousGuesses, game)
                gameBoard(previousGuesses.filterNotNull())
            }
        }
    }


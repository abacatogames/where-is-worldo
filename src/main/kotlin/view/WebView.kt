package io.github.cbaumont.view

import io.github.cbaumont.GameLoop
import io.github.cbaumont.WordGuess
import io.github.cbaumont.styles
import io.ktor.server.request.receiveParameters
import io.ktor.server.routing.RoutingCall
import kotlinx.coroutines.runBlocking
import kotlinx.html.div
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.stream.createHTML
import kotlinx.html.style
import kotlinx.html.title

fun GameView.Companion.webView(call: RoutingCall? = null): GameView =
    object : GameView {
        override fun displayGuess(guess: WordGuess): String {
            val div = createHTML().div("row") {
                guess.matches.map {
                    val cls = if (it.value) "correct" else "absent"
                    div("tile $cls") {
                        +guess.value[it.key].toString()
                    }
                }
            }
            return div
        }

        override fun readInput(): String {
            return runBlocking {
                call?.receiveParameters()?.get("guess")
            }?.trim() ?: ""
        }

        override fun displayMessage(message: String) {
        }
    }

fun headHtml(style: String = styles.toString()): String =
    createHTML().head {
        title {
            +"Where is Worldo?"
        }
        style {
            +style
        }
    }

fun GameLoop.gameInstructions(previousGuesses: List<WordGuess?>): String {
    val validGuesses = previousGuesses.filterNotNull()
    return if (validGuesses.lastOrNull()?.fullMatch == true) {
        createHTML().h2("won") {
            +"Congratulations, you found Worldo!"
        }
    } else if (validGuesses.size >= this.maxAttempts) {
        createHTML().h2("lost") {
            +"You’re out of attempts for today — better luck tomorrow!"
        }
    } else {
        if (previousGuesses.isNotEmpty() && previousGuesses.lastOrNull() == null) {
            createHTML().h2("invalid") {
                +"Invalid country, please make another guess."
            }
        } else if (previousGuesses.isEmpty()) {
            createHTML().h2 { +"Start by making a guess." }
        } else {
            createHTML().h2 { +"You have ${this@gameInstructions.maxAttempts - validGuesses.size} attempts left. Make another guess." }
        }
    }
}
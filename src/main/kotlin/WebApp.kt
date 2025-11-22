package io.github.cbaumont

import io.github.cbaumont.view.GameView
import io.github.cbaumont.view.webView
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.netty.Netty
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.html.FormMethod
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.style
import kotlinx.html.textInput
import kotlinx.html.title

fun main() {
    val previousGuesses = mutableListOf<WordGuess?>()

    embeddedServer(Netty, port = 8080) {
        routing {
            val game = GameLoop(
                gameView = GameView.webView(),
                maxAttempts = 6,
                proposedWord = "GREENLAND",
                gameIntro = "Where is Worldo today?"
            )
            get("/") {
                call.respondHtml {
                    head {
                        title {
                            +"Where is Worldo?"
                        }
                        style {
                            +styles.toString()
                        }
                    }
                    body {
                        div("container") {
                            h1 { +game.gameIntro }
                            h2("instructions") {
                                +"Worldo may be in any country in the world."
                            }

                            val validGuesses = previousGuesses.filterNotNull()

                            if (validGuesses.lastOrNull()?.fullMatch == true) {
                                h2("won") {
                                    +"Congratulations, you found Worldo!"
                                }
                            } else if (validGuesses.size >= game.maxAttempts) {
                                h2("lost") {
                                    +"You’re out of attempts for today — better luck tomorrow!"
                                }
                            } else {
                                if (previousGuesses.isNotEmpty() && previousGuesses.lastOrNull() == null) {
                                    h2("invalid") {
                                        +"Invalid country, please make another guess."
                                    }
                                } else if (previousGuesses.isEmpty()) {
                                    h2 { +"Start by making a guess." }
                                } else {
                                    h2 { +"You have ${game.maxAttempts - validGuesses.size} attempts left. Make another guess." }
                                }
                                form(action = "/", method = FormMethod.post) {
                                    textInput(name = "guess") {
                                        placeholder = "TYPE HERE"
                                        autoFocus = true
                                    }
                                }
                            }
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
                        }
                    }
                }
            }
            post("/") {
                call.receiveParameters()["guess"]
                    ?.trim()
                    ?.takeIf(String::isNotBlank)
                    ?.let(game::softValidation)
                    .let(previousGuesses::add)
                call.respondRedirect("/")
            }
        }
    }.start(wait = true)
}
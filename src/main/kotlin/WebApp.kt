package io.github.cbaumont

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
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.style
import kotlinx.html.textInput
import kotlinx.html.title

fun main() {
    fun GameRendering.Companion.webRendering(): GameRendering = object : GameRendering {
        override fun renderGuess(guess: WordGuess): String {

            TODO("Not yet implemented")
        }

        override fun readUserInput(): String {
            TODO("Not yet implemented")
        }

        override fun showMessage(message: String) {

        }

    }

    val game = GameLoop(
        gameRendering = GameRendering.webRendering(),
        maxAttempts = 7,
        proposedWord = "GREENLAND",
        gameIntro = "Where is Worldo today?"
    )

    val previousGuesses = mutableListOf<WordGuess?>()

    embeddedServer(Netty, port = 8080) {
        routing {
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
                            p("instructions") {
                                +"Start by making a guess."
                            }

                            form(action = "/", method = FormMethod.post) {
                                textInput(name = "guess") {
                                    placeholder = "TYPE HERE"
                                    autoFocus = true
                                }
                            }

                            div("board") {
                                for (guess in previousGuesses.filterNotNull()) {
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
                val params = call.receiveParameters()
                val guess = params["guess"]?.trim().orEmpty()
                if (guess.isNotBlank()) {
                    previousGuesses += game.softValidation(guess)
                }
                call.respondRedirect("/")
            }
        }
    }.start(wait = true)
}
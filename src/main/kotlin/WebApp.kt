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
import kotlinx.html.submitInput
import kotlinx.html.textInput
import kotlinx.html.title

fun main() {
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
                            h1 { +"Unlimited Wordle-Style Game" }
                            p("instructions") {
                                +"Guess the secret word. There is no limit on guess length."
                            }

                            form(action = "/", method = FormMethod.post) {
                                textInput(name = "guess") {
                                    placeholder = "Type your guess here"
                                }
                                submitInput {
                                    value = "Submit"
                                }
                            }

                            div("board") {
//                                for ((guess, result) in previousGuesses) {
//                                    div("row") {
//                                        for (r in result) {
//                                            val cls = when (r.status) {
//                                                Status.CORRECT -> "correct"
//                                                Status.PRESENT -> "present"
//                                                Status.ABSENT -> "absent"
//                                            }
//                                            div("tile $cls") { +r.char.toString() }
//                                        }
//                                    }
//                                }
                            }
                        }
                    }
                }
            }
            post("/") {
                val params = call.receiveParameters()
                val guess = params["guess"]?.trim().orEmpty()
                if (guess.isNotBlank()) {
//                    val result = validateGuess(secret, guess)
//                    previousGuesses += guess to result
                }
                call.respondRedirect("/")
            }
        }
    }.start(wait = true)
}
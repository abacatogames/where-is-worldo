package io.github.cbaumont.view

import io.github.cbaumont.GameLoop
import io.github.cbaumont.WordGuess
import io.ktor.server.request.receiveParameters
import io.ktor.server.routing.RoutingCall
import kotlinx.coroutines.runBlocking
import kotlinx.html.FormMethod
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.h1
import kotlinx.html.stream.createHTML
import kotlinx.html.style
import kotlinx.html.textInput
import kotlinx.html.title
import kotlinx.html.body
import kotlinx.html.div
import io.github.cbaumont.styles


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

fun headHtml(style: String): String =
        createHTML().head {
          title { +"Where is Worldo?" }
          style { +style }
        }

fun gameInstructions(previousGuesses: List<WordGuess?>, game: GameLoop): String {
  val validGuesses = previousGuesses.filterNotNull()
  var res =
          if (validGuesses.lastOrNull()?.fullMatch == true) {
            createHTML().h2("won") { +"Congratulations, you found Worldo!" }
          } else if (validGuesses.size >= game.maxAttempts) {
            createHTML().h2("lost") { +"You’re out of attempts for today — better luck tomorrow!" }
          } else {
            if (previousGuesses.isNotEmpty() && previousGuesses.lastOrNull() == null) {
              createHTML().h2("invalid") { +"Invalid country, please make another guess." }
            } else if (previousGuesses.isEmpty()) {
              createHTML().h2 { +"Start by making a guess." }
            } else {
              createHTML().h2 {
                +"You have ${game.maxAttempts - validGuesses.size} attempts left. Make another guess."
              }
            }
          }

  val input =
          createHTML().form(action = "/", method = FormMethod.post) {
            textInput(name = "guess") {
              placeholder = "TYPE HERE"
              autoFocus = true
            }
          }

  return res + input
}

fun generateHtml(previousGuesses: List<WordGuess?>, game: GameLoop): String {
  
  val body = createHTML().body { div("container") {
    h1 { +game.gameIntro }
    h2("instructions") { +"Worldo may be in any country in the world."}
    +gameInstructions(previousGuesses, game)
  } }
  return headHtml(styles.toString()) + body
}


package io.github.cbaumont.view

import io.github.cbaumont.Game
import io.github.cbaumont.GameState
import io.github.cbaumont.word.WordGuess
import io.github.cbaumont.geo.CardinalDirection
import io.github.cbaumont.geo.Country
import io.github.cbaumont.geo.GeoDistance
import io.ktor.http.HttpStatusCode
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.RoutingCall
import kotlinx.html.FlowContent
import kotlinx.html.FormMethod
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.details
import kotlinx.html.div
import kotlinx.html.footer
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.p
import kotlinx.html.stream.createHTML
import kotlinx.html.style
import kotlinx.html.summary
import kotlinx.html.textInput
import kotlinx.html.title

fun interface WebView : (Game) -> String {
    companion object {
        fun create(geoDistance: GeoDistance = GeoDistance.create()): WebView =
            object : WebView {
                override fun invoke(game: Game): String =
                    createHTML().html {
                        head {
                            title { +"Where is Worldo?" }
                            style { +styles.toString() }
                        }
                        body {
                            div("container") {
                                div("with-image") {
                                    h1 { +"Where is Worldo today?" }
                                    h2 { +"Worldo may be in any country in the world." }
                                    gameInfo(game)
                                }
                                guessForm(game.state)
                                gameBoard(game.validGuesses)
                                howToPlay()
                                footer("footer") { p { +"© 2026 Abacato Games" } }
                            }
                        }
                    }

                private fun FlowContent.guessForm(gameState: GameState) =
                    when (gameState) {
                        GameState.IN_PROGRESS, GameState.NEW -> {
                            div("form-slot") {
                                form(action = "/", method = FormMethod.post) {
                                    textInput(name = "guess") {
                                        placeholder = "TYPE HERE"
                                        autoFocus = true
                                    }
                                }
                            }
                        }

                        else -> {}
                    }

                private fun FlowContent.gameBoard(validGuesses: List<WordGuess>) =
                    div("board") {
                        for (guess in validGuesses.reversed()) {
                            div("row") {
                                guess.matches.forEach {
                                    val cls = if (it.value) "correct" else "absent"
                                    div("tile $cls") {
                                        +guess.value[it.key].toString()
                                    }
                                }
                                if (!guess.fullMatch) distanceHint(guess)
                            }
                        }
                    }

                private fun FlowContent.gameInfo(game: Game) {
                    when (game.state) {
                        GameState.WON -> {
                            h2("won") { +"Congratulations, you found Worldo!" }
                            div("form-slot color-gif") { +"YOU WON!" }
                        }

                        GameState.LOST -> {
                            h2("lost") { +"You’re out of attempts for today — better luck tomorrow!" }
                            div("form-slot color-gif") { +"GAME OVER" }
                        }

                        GameState.NEW -> {
                            h2 { +"Start by making a guess." }
                        }

                        GameState.IN_PROGRESS -> {
                            if (game.lastGuessWasInvalid) {
                                h2("invalid") { +"Invalid country, please make another guess." }
                            } else {
                                h2 { +"You have ${game.attemptsLeft} attempts left. Make another guess." }
                            }
                        }
                    }
                }

                private fun FlowContent.distanceHint(guess: WordGuess) {
                    div("tile hint") {
                        val distance = geoDistance(
                            Country.of(guess.value),
                            Country.of(guess.correctWord)
                        )
                        +distance.direction.toArrow()
                        br
                        +"${distance.km} KM"
                    }
                }

                private fun FlowContent.howToPlay() {
                    details {
                        summary("how-to") { +"How to play?" }
                        p("how-to") {
                            +"Every day Worldo travels to a different country. You have 6 attempts to discover where he is."
                            br
                            +"If a letter in your guess matches a letter in the correct country (regardless of the position), its tile will be green. If it doesn’t, it will be brown."
                            br
                            +"For example, if your guess contains two \"A\"s and only one turns green, that means the correct country has one \"A\" in it."
                            br
                            +"Additionally, after each attempt, you will see a hint: a tile showing the distance* and direction** between your guess and the correct country."
                        }
                        div("board") {
                            div("row") {
                                div("tile correct") { +"G" }
                                div("tile absent") { +"U" }
                                div("tile absent") { +"Y" }
                                div("tile correct") { +"A" }
                                div("tile correct") { +"N" }
                                div("tile absent") { +"A" }
                            }
                        }
                        p("how-to") { +"In the example above, the correct country is Greenland." }
                        p("notes") { +"* Distances are calculated based on the approx center of both countries." }
                        p("notes") { +"** Direction is calculated using a simplified version of the azimuth formula. It might not always be very precise or intuitive, specially on long distances." }
                    }
                }
            }
    }
}

private fun CardinalDirection.toArrow(): String =
    when (this) {
        CardinalDirection.SOUTH -> "⬇️"
        CardinalDirection.WEST -> "⬅️"
        CardinalDirection.NORTH -> "⬆️"
        CardinalDirection.EAST -> "➡️"
        CardinalDirection.SOUTH_WEST -> "↙️"
        CardinalDirection.SOUTH_EAST -> "↘️"
        CardinalDirection.NORTH_WEST -> "↖️"
        CardinalDirection.NORTH_EAST -> "↗️"
    }

suspend fun RoutingCall.gameNotFound() =
    respondHtml(HttpStatusCode.NotFound) {
        head {
            title { +"Game not found" }
            style { +styles.toString() }
        }
        body {
            h1 { +"Game not found :(" }
            h2 { +"Sorry, but the game session was not found. Please contact the game admin." }
        }
    }

package com.abacatogames.view

import com.abacatogames.Game
import com.abacatogames.GameState
import com.abacatogames.geo.CardinalDirection
import com.abacatogames.geo.CardinalDirection.EAST
import com.abacatogames.geo.CardinalDirection.NORTH
import com.abacatogames.geo.CardinalDirection.NORTH_EAST
import com.abacatogames.geo.CardinalDirection.NORTH_WEST
import com.abacatogames.geo.CardinalDirection.SOUTH
import com.abacatogames.geo.CardinalDirection.SOUTH_EAST
import com.abacatogames.geo.CardinalDirection.SOUTH_WEST
import com.abacatogames.geo.CardinalDirection.WEST
import com.abacatogames.geo.Country
import com.abacatogames.geo.GeoDistance
import com.abacatogames.word.WordGuess
import io.ktor.http.HttpStatusCode
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.RoutingCall
import kotlinx.html.FlowContent
import kotlinx.html.FormMethod
import kotlinx.html.LinkAs
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
import kotlinx.html.link
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
                            title { +"Where is Wordo?" }
                            style { +styles.toString() }
                            link {
                                rel = "preload"
                                htmlAs = LinkAs.image
                                href = "/wordov3.webp"
                            }
                        }
                        body {
                            div("container") {
                                div("with-image") { whereIsWordo() }
                                gameInfo(game)
                                guessForm(game.state)
                                gameBoard(game.validGuesses, !game.lastGuessWasInvalid)
                                howToPlay()
                                footer { p { +"© 2026 Abacato Games" } }
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
                                        maxLength = "100"
                                        required = true
                                        minLength = "3"
                                    }
                                }
                            }
                        }

                        else -> {}
                    }

                private fun FlowContent.gameBoard(
                    validGuesses: List<WordGuess>,
                    animationEnabled: Boolean = true,
                    tinyTiles: Boolean = false,
                    hintsEnabled: Boolean = true,
                ) =
                    div("board") {
                        validGuesses.reversed().forEachIndexed { idx, guess ->
                            div("row") {
                                guess.matches.forEach {
                                    val char = guess.value[it.key]
                                    val correctOrAbsent = if (char == ' ' || !it.value) "absent" else "correct"
                                    val slide = if (idx == 0 && animationEnabled) " slide" else ""
                                    val tiny = if (tinyTiles) " tiny" else ""
                                    div("tile $correctOrAbsent$slide$tiny") { +char.toString() }
                                }
                                if (!guess.fullMatch && hintsEnabled) distanceHint(guess)
                            }
                        }
                    }

                private fun FlowContent.gameInfo(game: Game) {
                    when (game.state) {
                        GameState.WON -> {
                            h2("won") { +"Congratulations, you found Wordo!" }
                            div("form-slot color-gif") { +"YOU WON!" }
                        }

                        GameState.LOST -> {
                            h2("lost") { +"You’re out of attempts for today — better luck tomorrow!" }
                            div("form-slot color-gif") { +"GAME OVER" }
                        }

                        GameState.NEW -> {
                            h2 { +"Take a guess, any country will do!" }
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
                        +"${distance.km}"
                        br
                        +"KM"
                    }
                }

                private fun FlowContent.howToPlay() {
                    details {
                        summary("how-to title") { +"How to play?" }
                        p("how-to") {
                            +"""
                                Every day Wordo travels to a different country. You have 6 attempts to discover where he is.
                                If a letter in your guess matches a letter in the correct country (regardless of the position), its tile will be green. If it doesn’t, it will be brown.
                                Additionally, after each attempt, you will see a hint: a tile showing the distance* and direction** between your guess and the correct country.
                            """.trimIndent()
                        }
                        p("how-to") { +"In the following example, \"Guyana\" contains two \"A\"s and only one turns green, meaning that the correct country (Greenland) has only one \"A\"." }
                        gameBoard(validGuesses = listOf(WordGuess("Guyana", "Greenland")), animationEnabled = false)
                        p("how-to notes") {
                            +"* Distances are calculated based on the approx center of both countries."
                            br
                            +"** Direction is calculated using a simplified version of the azimuth formula. It might not always be very precise or intuitive, specially on long distances."
                        }
                    }
                }

                private fun FlowContent.whereIsWordo() {
                    gameBoard(
                        validGuesses = listOf(
                            WordGuess("wordo?", "wrd?"),
                            WordGuess("is", "i"),
                            WordGuess("where", "we"),
                        ),
                        animationEnabled = false,
                        tinyTiles = true,
                        hintsEnabled = false,
                    )
                }
            }
    }
}

private fun CardinalDirection.toArrow(): String =
    when (this) {
        SOUTH -> "⬇️"
        WEST -> "⬅️"
        NORTH -> "⬆️"
        EAST -> "➡️"
        SOUTH_WEST -> "↙️"
        SOUTH_EAST -> "↘️"
        NORTH_WEST -> "↖️"
        NORTH_EAST -> "↗️"
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

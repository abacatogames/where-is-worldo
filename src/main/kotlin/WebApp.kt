package io.github.cbaumont

import io.github.cbaumont.view.WebView
import io.github.cbaumont.view.gameNotFound
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.withCharset
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.netty.EngineMain
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.sessions.SessionTransportTransformerEncrypt
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import io.ktor.util.hex
import java.time.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class GameSession(val dateRef: Long, val guesses: MutableList<WordGuess?>)

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    val secretEncryptKey =
        environment.config.property("secrets.encryptKey").getString()
    val secretSignKey =
        environment.config.property("secrets.signKey").getString()

    install(Sessions) {
        cookie<GameSession>("game_session") {
            cookie.path = "/"
            cookie.httpOnly = true
            cookie.extensions["SameSite"] = "lax"
            transform(SessionTransportTransformerEncrypt(hex(secretEncryptKey), hex(secretSignKey)))
        }
    }
    routing {
        get("/") {
            val currentDateRef = LocalDate.now().toEpochDay()
            val session = call.sessions.get<GameSession>()

            if (session == null || currentDateRef > session.dateRef) {
                call.sessions.set(GameSession(dateRef = currentDateRef, guesses = mutableListOf()))
            }

            val game = Game(
                proposedWord = generateWordForDate(LocalDate.now()),
                validator = String::isAValidCountry,
                previousGuesses = call.sessions.get<GameSession>()!!.guesses
            )

            call.respond(
                TextContent(
                    WebView.create()(game),
                    ContentType.Text.Html.withCharset(Charsets.UTF_8),
                    HttpStatusCode.OK
                )
            )
        }
        post("/") {
            runCatching {
                val session = call.sessions.get<GameSession>() ?: error("Game not found")

                val game = Game(
                    proposedWord = generateWordForDate(LocalDate.now()),
                    validator = String::isAValidCountry,
                    previousGuesses = session.guesses
                )

                call.receiveParameters()["guess"]
                    ?.trim()
                    ?.takeIf(String::isNotBlank)
                    ?.let(game::validateAndAddGuess)

                call.sessions.set<GameSession>(session)
                call.respondRedirect("/")
            }.onFailure { call.gameNotFound() }
        }
    }
}
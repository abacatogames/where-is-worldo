package io.github.cbaumont

import io.github.cbaumont.view.WebView
import io.github.cbaumont.view.gameNotFound
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.withCharset
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class GameSession(val id: String, val game: Game)

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(Sessions) {
            cookie<GameSession>("game_session") {
                cookie.path = "/"
                cookie.httpOnly = true
                cookie.extensions["SameSite"] = "lax"
            }
        }
        routing {
            get("/") {
                call.sessions.get<GameSession>() ?: call.sessions.set(
                    GameSession(
                        UUID.randomUUID().toString(),
                        Game(6, "BRAZIL")
                    )
                )
                val game = call.sessions.get<GameSession>()!!.game
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
                    call.receiveParameters()["guess"]
                        ?.trim()
                        ?.takeIf(String::isNotBlank)
                        ?.let(session.game::validateAndAddGuess)

                    call.sessions.set<GameSession>(session)
                    call.respondRedirect("/")
                }.onFailure { call.gameNotFound() }
            }
        }
    }.start(wait = true)
}

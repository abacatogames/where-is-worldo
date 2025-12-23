package io.github.cbaumont

import io.github.cbaumont.view.WebView
import io.github.cbaumont.view.gameNotFound
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.withCharset
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.util.UUID

val gameSessions: MutableMap<String, Game> = hashMapOf()

fun main() {
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                val sid = call.queryParameters["sid"] ?: UUID.randomUUID().toString()
                val game = gameSessions.getOrPut(sid) { Game(6, "BRAZIL", sid) }
                call.respond(
                    TextContent(
                        WebView.create()(game),
                        ContentType.Text.Html.withCharset(Charsets.UTF_8),
                        HttpStatusCode.OK
                    )
                )
            }
            post("/") {
                val sid = call.queryParameters["sid"] ?: UUID.randomUUID().toString()
                runCatching {
                    val game = gameSessions[sid] ?: error("Game not found")
                    call.receiveParameters()["guess"]
                        ?.trim()
                        ?.takeIf(String::isNotBlank)
                        ?.let(game::validateAndAddGuess)
                    call.respondRedirect("/?sid=${sid}")
                }.onFailure { call.gameNotFound(sid) }
            }
        }
    }.start(wait = true)
}

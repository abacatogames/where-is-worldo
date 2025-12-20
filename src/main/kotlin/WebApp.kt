package io.github.cbaumont

import io.github.cbaumont.view.WebView
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

fun main() {
    embeddedServer(Netty, port = 8080) {
        routing {
            val game = Game(
                maxAttempts = 6,
                proposedWord = "GREENLAND",
            )
            get("/") {
                call.respond(
                    TextContent(
                        WebView.create()(game),
                        ContentType.Text.Html.withCharset(Charsets.UTF_8),
                        HttpStatusCode.OK
                    )
                )
            }
            post("/") {
                call.receiveParameters()["guess"]
                    ?.trim()
                    ?.takeIf(String::isNotBlank)
                    ?.let(game::validateAndAddGuess)
                call.respondRedirect("/")
            }
        }
    }.start(wait = true)
}

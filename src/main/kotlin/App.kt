package io.github.cbaumont

fun main() {
    GameLoop(
        gameRendering = GameRendering.cliRendering(),
        proposedWord = "GREENLAND",
    ).mainLoop()
}

package io.github.cbaumont

import io.github.cbaumont.view.CLIView
import io.github.cbaumont.view.GameView

fun main() {
    GameLoop(
        gameView = GameView.CLIView(),
        proposedWord = "GREENLAND",
    ).mainLoop()
}

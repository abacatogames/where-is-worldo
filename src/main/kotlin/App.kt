package io.github.cbaumont

import io.github.cbaumont.view.CLIView

fun main() {
    val game = Game(6, "GREENLAND")

    println(CLIView.create()(game))
    while (game.state != GameState.WON && game.state != GameState.LOST) {
        readln()
            .trim()
            .takeIf(String::isNotBlank)
            ?.let(game::validateAndAddGuess)
        println(CLIView.create()(game))
    }
}

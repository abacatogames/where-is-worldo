package io.github.cbaumont.view

import io.github.cbaumont.WordGuess
import io.github.cbaumont.view.CLIColours.DEFAULT
import io.github.cbaumont.view.CLIColours.GREEN

private typealias CLIView = GameView

fun GameView.Companion.CLIView(writer: ConsoleWriter = ConsoleWriter.defaultWriter()): CLIView {
    return object : GameView {
        override fun displayGuess(guess: WordGuess): String = guess.matches.map {
            if (it.value) {
                guess.value[it.key].green
            } else guess.value[it.key]
        }.joinToString(" ")

        override fun readInput(): String = writer.read()

        override fun displayMessage(message: String) = writer.write(message)
    }
}

enum class CLIColours(val code: String) {
    GREEN("\u001b[32m"),
    DEFAULT("\u001b[0m")
}

private val Char.green
    get() = "${GREEN.code}$this${DEFAULT.code}"

interface ConsoleWriter {
    fun read(): String
    fun write(message: String)

    companion object {
        fun defaultWriter(): ConsoleWriter = object : ConsoleWriter {
            override fun read(): String = readln()

            override fun write(message: String) = println(message)

        }
    }
}
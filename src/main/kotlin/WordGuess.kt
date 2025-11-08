package io.github.cbaumont

data class WordGuess(
    val value: String,
    val correctWord: String
) {
    val matches: Map<Int, Boolean> by lazy {
        var wordCheck = correctWord.uppercase()

        value
            .uppercase()
            .foldIndexed(mutableMapOf()) { i, acc, ch ->
                if (ch in wordCheck) {
                    acc[i] = true
                    wordCheck = wordCheck.replaceFirst(ch, Char.MIN_VALUE, true)
                } else {
                    acc[i] = false
                }
                acc
            }
    }
    val fullMatch: Boolean = value.uppercase() == correctWord.uppercase()
}
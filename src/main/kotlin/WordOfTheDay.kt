package io.github.cbaumont

sealed interface WordOfTheDay {
    val value: String

    @JvmInline
    value class LocationOfTheDay internal constructor(override val value: String) : WordOfTheDay

    companion object {
        fun of(
            rawValue: String?,
            validation: (String) -> Boolean,
            constructor: (String) -> WordOfTheDay
        ): WordOfTheDay = rawValue?.takeIf { validation(it) }?.let { constructor(it) }
            ?: error("Unable to start the game with word: $rawValue")
    }
}

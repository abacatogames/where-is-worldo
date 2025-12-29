package io.github.cbaumont

sealed interface VerifiedWord {
    val value: String

    @JvmInline
    value class VerifiedLocation internal constructor(override val value: String) : VerifiedWord

    companion object {
        fun of(
            rawValue: String?,
            validation: (String) -> Boolean,
            constructor: (String) -> VerifiedWord
        ): VerifiedWord = rawValue?.takeIf { validation(it) }?.let { constructor(it) }
            ?: error("Unable to start the game with word: $rawValue")
    }
}

package io.github.cbaumont.word

@JvmInline
value class VerifiedWord internal constructor(val value: String) {

    companion object {
        fun of(rawValue: String?, validation: (String) -> Boolean): VerifiedWord? =
            rawValue
                ?.takeIf { validation(it) }
                ?.let { VerifiedWord(it) }
    }
}

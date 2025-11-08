import io.github.cbaumont.WordGuess
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WordMatcherTest {

    @Test
    fun `some letters in the word match`() {
        val correctWord = "ENGLAND"

        val guess = WordGuess(
            value = "GREENLAND",
            correctWord = correctWord
        )

        val expected = mapOf(
            0 to true,
            1 to false,
            2 to true,
            3 to false,
            4 to true,
            5 to true,
            6 to true,
            7 to true,
            8 to true,
        )
        assertEquals(expected, guess.matches)
    }

    @Test
    fun `all letters in the word match`() {
        val correctWord = "ENGLAND"

        val guess = WordGuess(
            value = "ENGLAND",
            correctWord = correctWord
        )

        val expected = mapOf(
            0 to true,
            1 to true,
            2 to true,
            3 to true,
            4 to true,
            5 to true,
            6 to true,
        )

        assertEquals(expected, guess.matches)
        assertTrue(guess.fullMatch)
    }

    @Test
    fun `all letters in the word match ignoring case`() {
        val correctWord = "ENGLAND"

        val guess = WordGuess(
            value = "england",
            correctWord = correctWord
        )

        val expected = mapOf(
            0 to true,
            1 to true,
            2 to true,
            3 to true,
            4 to true,
            5 to true,
            6 to true,
        )

        assertEquals(expected, guess.matches)
        assertTrue(guess.fullMatch)
    }

    @Test
    fun `does not match when guess letters are out of order`() {
        val correctWord = "BRAZIL"

        val guess = WordGuess(
            value = "RAZILB",
            correctWord = correctWord
        )

        assertFalse(guess.fullMatch)
    }
}


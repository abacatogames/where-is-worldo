import io.github.cbaumont.Game
import io.github.cbaumont.GameState
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.jupiter.api.assertThrows

class GameTest {

    @Test
    fun `does not start game with invalid location`() {
        val exception = assertThrows<IllegalStateException> {
            Game(
                maxAttempts = 6,
                proposedWord = "ENGLAND",
            )
        }

        assertContains(exception.message!!, "ENGLAND")
    }

    @Test
    fun `game starts`() {
        val game = Game(
            maxAttempts = 6,
            proposedWord = "GREENLAND",
        )

        assertEquals(expected = GameState.NEW, actual = game.state)
        assertEquals(expected = 6, actual = game.attemptsLeft)
        assertTrue(game.validGuesses.isEmpty())
        assertFalse(game.lastGuessWasInvalid)
    }

    @Test
    fun `game won`() {
        val game = Game(
            maxAttempts = 6,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("GREENLAND")

        assertEquals(expected = GameState.WON, actual = game.state)
        assertEquals(expected = 5, actual = game.attemptsLeft)
        assertEquals(1, game.validGuesses.size)
        assertFalse(game.lastGuessWasInvalid)
    }

    @Test
    fun `game lost`() {
        val game = Game(
            maxAttempts = 1,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("BRAZIL")

        assertEquals(expected = GameState.LOST, actual = game.state)
        assertEquals(expected = 0, actual = game.attemptsLeft)
        assertEquals(1, game.validGuesses.size)
        assertFalse(game.lastGuessWasInvalid)
    }

    @Test
    fun `invalid guess`() {
        val game = Game(
            maxAttempts = 2,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("ENGLAND")

        assertTrue(game.lastGuessWasInvalid)
        assertEquals(expected = GameState.IN_PROGRESS, actual = game.state)
        assertEquals(expected = 2, actual = game.attemptsLeft)
        assertEquals(0, game.validGuesses.size)
    }
}

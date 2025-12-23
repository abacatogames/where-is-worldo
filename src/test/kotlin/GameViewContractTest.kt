import io.github.cbaumont.Game
import kotlin.test.Test
import kotlin.test.assertContains

abstract class GameViewContractTest(val gameView: (Game) -> String) {
    @Test
    fun `new game displays correct game instructions`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
        )

        assertContains(gameView(game), "Start by making a guess.")
    }

    @Test
    fun `in progress game displays correct game instructions`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("GUYANA")

        assertContains(gameView(game), "You have 2 attempts left. Make another guess.")
    }

    @Test
    fun `invalid guess displays correct game instructions`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("GUESS")

        assertContains(gameView(game), "Invalid country, please make another guess.")
    }

    @Test
    fun `game won displays correct game instructions`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("GREENLAND")

        assertContains(gameView(game), "Congratulations, you found Worldo!")
    }

    @Test
    fun `game lost displays correct game instructions`() {
        val game = Game(
            maxAttempts = 1,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("GUYANA")

        assertContains(gameView(game), "You’re out of attempts for today — better luck tomorrow!")
    }

}
import io.github.cbaumont.Game
import io.github.cbaumont.view.CLIColours
import io.github.cbaumont.view.CLIView
import kotlin.test.Test
import kotlin.test.assertContains

class CLIViewTest : GameViewContractTest(gameView = CLIView.create()) {

    @Test
    fun `displays correct matches in green`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
            validator = { true }
        )
        val expected = "${CLIColours.GREEN.code}G${CLIColours.DEFAULT.code} U Y " +
                "${CLIColours.GREEN.code}A${CLIColours.DEFAULT.code} " +
                "${CLIColours.GREEN.code}N${CLIColours.DEFAULT.code} A"

        game.validateAndAddGuess("GUYANA")

        val result = gameView(game)

        assertContains(result, expected)
    }
}

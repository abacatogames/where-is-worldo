import io.github.cbaumont.Game
import io.github.cbaumont.view.CLIColours
import io.github.cbaumont.view.CLIView
import kotlin.test.Test
import kotlin.test.assertContains

class CLIViewTest {

    @Test
    fun `displays correct matches in green`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
        )
        val expected = "${CLIColours.GREEN.code}G${CLIColours.DEFAULT.code} U Y " +
                "${CLIColours.GREEN.code}A${CLIColours.DEFAULT.code} " +
                "${CLIColours.GREEN.code}N${CLIColours.DEFAULT.code} A"

        val cliView = CLIView.create()

        game.validateAndAddGuess("GUYANA")

        val result = cliView(game)

        assertContains(result, expected)
    }

    @Test
    fun `new game displays correct game instructions`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
        )

        assertContains(CLIView.create()(game), "Start by making a guess.")
    }

    @Test
    fun `in progress game displays correct game instructions`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("GUYANA")

        assertContains(CLIView.create()(game), "You have 2 attempts left. Make another guess.")
    }

    @Test
    fun `invalid guess displays correct game instructions`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("GUESS")

        assertContains(CLIView.create()(game), "Invalid country, please make another guess.")
    }

    @Test
    fun `game won displays correct game instructions`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("GREENLAND")

        assertContains(CLIView.create()(game), "Congratulations, you found Worldo!")
    }

    @Test
    fun `game lost displays correct game instructions`() {
        val game = Game(
            maxAttempts = 1,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("GUYANA")

        assertContains(CLIView.create()(game), "You’re out of attempts for today — better luck tomorrow!")
    }
}

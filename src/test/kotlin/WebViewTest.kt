import io.github.cbaumont.Game
import io.github.cbaumont.view.WebView
import kotlinx.html.FormMethod
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.stream.createHTML
import kotlinx.html.textInput
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test
import kotlin.test.assertContains

class WebViewTest : GameViewContractTest(gameView = WebView.create()) {

    @Test
    fun `game board is displayed`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
            validator = { true }
        )

        game.validateAndAddGuess("GUYANA")

        val result = gameView(game)

        val expected =
            listOf(
                createHTML().div("tile correct") { +"G" },
                createHTML().div("tile absent") { +"U" },
                createHTML().div("tile absent") { +"Y" },
                createHTML().div("tile correct") { +"A" },
                createHTML().div("tile correct") { +"N" },
                createHTML().div("tile absent") { +"A" }
            )
        assertTrue(expected.all { result.contains(it) })
    }

    @Test
    fun `form is displayed`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
            validator = { true }
        )

        val result = gameView(game)

        val expected = createHTML().form(action = "/", method = FormMethod.post) {
            textInput(name = "guess") {
                placeholder = "TYPE HERE"
                autoFocus = true
            }
        }
        assertContains(result, expected)
    }
}

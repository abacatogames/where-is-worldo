import io.github.cbaumont.Game
import io.github.cbaumont.view.WebView
import kotlinx.html.FormMethod
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h2
import kotlinx.html.stream.createHTML
import kotlinx.html.textInput
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test
import kotlin.test.assertContains

class WebViewTest {

    @Test
    fun `game board is displayed`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("GUYANA")

        val result = WebView.create()(game).trimIndent()

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
        )

        val result = WebView.create()(game).trimIndent()

        val expected = createHTML().form(action = "/", method = FormMethod.post) {
            textInput(name = "guess") {
                placeholder = "TYPE HERE"
                autoFocus = true
            }
        }
        assertContains(result, expected)
    }

    @Test
    fun `new game displays correct game instructions`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
        )

        val result = WebView.create()(game).trimIndent()

        val expected = createHTML().h2 { +"Start by making a guess." }
        assertContains(result, expected)
    }

    @Test
    fun `game won displays correct game instructions`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("GREENLAND")

        val result = WebView.create()(game).trimIndent()

        val expected = createHTML().h2("won") { +"Congratulations, you found Worldo!" }
        assertContains(result, expected)
    }

    @Test
    fun `game lost displays correct game instructions`() {
        val game = Game(
            maxAttempts = 1,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("GUYANA")

        val result = WebView.create()(game).trimIndent()

        val expected = createHTML().h2("lost") { +"You’re out of attempts for today — better luck tomorrow!" }
        assertContains(result, expected)
    }

    @Test
    fun `in progress game displays correct game instructions`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("GUYANA")

        val result = WebView.create()(game).trimIndent()

        val expected = createHTML().h2 { +"You have 2 attempts left. Make another guess." }
        assertContains(result, expected)
    }

    @Test
    fun `invalid game displays correct game instructions`() {
        val game = Game(
            maxAttempts = 3,
            proposedWord = "GREENLAND",
        )

        game.validateAndAddGuess("TEST")

        val result = WebView.create()(game).trimIndent()

        val expected = createHTML().h2("invalid") { +"Invalid country, please make another guess." }
        assertContains(result, expected)
    }

}
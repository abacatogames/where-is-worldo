import com.abacatogames.Game
import com.abacatogames.view.WebView
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertTrue
import kotlinx.html.FormMethod
import kotlinx.html.br
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.stream.createHTML
import kotlinx.html.textInput

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
                createHTML().div("tile correct slide") { +"G" },
                createHTML().div("tile absent slide") { +"U" },
                createHTML().div("tile absent slide") { +"Y" },
                createHTML().div("tile correct slide") { +"A" },
                createHTML().div("tile correct slide") { +"N" },
                createHTML().div("tile absent slide") { +"A" },
                createHTML().div("tile hint") {
                    +"↗️"
                    br
                    +"7510 KM"
                }
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
                maxLength = "100"
                required = true
                minLength = "3"
            }
        }
        assertContains(result, expected)
    }
}

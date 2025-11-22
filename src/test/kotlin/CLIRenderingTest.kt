import io.github.cbaumont.view.CLIColours
import io.github.cbaumont.view.ConsoleWriter
import io.github.cbaumont.view.GameView
import io.github.cbaumont.WordGuess
import io.github.cbaumont.view.CLIView
import kotlin.test.Test
import kotlin.test.assertEquals

class CLIRenderingTest {

    @Test
    fun `renders correct matches in green`() {
        val gameView = GameView.CLIView()

        val guess = WordGuess(
            value = "GUESS",
            correctWord = "GLASS"
        )

        val result = gameView.displayGuess(guess)

        val expected = "${CLIColours.GREEN.code}G${CLIColours.DEFAULT.code} U E " +
                "${CLIColours.GREEN.code}S${CLIColours.DEFAULT.code} " +
                "${CLIColours.GREEN.code}S${CLIColours.DEFAULT.code}"

        assertEquals(expected, result)
    }

    @Test
    fun `renders correct matches in green when full match`() {
        val gameView = GameView.CLIView()

        val guess = WordGuess(
            value = "GUI",
            correctWord = "GUI"
        )

        val result = gameView.displayGuess(guess)

        val expected = "${CLIColours.GREEN.code}G${CLIColours.DEFAULT.code} " +
                "${CLIColours.GREEN.code}U${CLIColours.DEFAULT.code} " +
                "${CLIColours.GREEN.code}I${CLIColours.DEFAULT.code}"

        assertEquals(expected, result)
    }

    @Test
    fun `does not render any characters in green when no matches`() {
        val gameView = GameView.CLIView()

        val guess = WordGuess(
            value = "ABC",
            correctWord = "GUI"
        )

        val result = gameView.displayGuess(guess)

        val expected = "A B C"
        assertEquals(expected, result)
    }

    @Test
    fun `reads and writes message to console`() {
        var readCount = 0
        var messageCheck = ""
        val consoleSpy = object : ConsoleWriter {
            override fun read(): String {
                readCount++
                return ""
            }

            override fun write(message: String) {
                messageCheck = message
            }
        }

        val gameView = GameView.CLIView(consoleSpy)

        gameView.readInput()
        gameView.displayMessage("Some message")

        assertEquals("Some message", messageCheck)
        assertEquals(1, readCount)
    }

}
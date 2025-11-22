import io.github.cbaumont.GameLoop
import io.github.cbaumont.view.GameView
import io.github.cbaumont.WordGuess
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFails

class GameLoopTest {

    @Test
    fun `invalid location is not allowed as word of the day`() {
        assertFails("Unable to start game: invalid location") {
            GameLoop(
                gameView = SpyView(""),
                proposedWord = "TEST",
            )
        }
    }

    @Test
    fun `intro message is displayed`() {
        val rendering = SpyView("ARGENTINA")
        GameLoop(rendering, proposedWord = "BRAZIL", gameIntro = "Intro message")

        assertContains(rendering.messages, "Intro message")
    }

    @Test
    fun `game lost main loop test`() {
        val rendering = SpyView("ARGENTINA")
        val game = GameLoop(gameView = rendering, maxAttempts = 2, proposedWord = "BRAZIL")

        game.mainLoop()

        assertContains(rendering.messages, "ARGENTINA")
        assertContains(rendering.messages, "Make another guess: ")
        assertContains(rendering.messages, "You lost :(")
        assertEquals(5, rendering.messages.size)
    }

    @Test
    fun `game won main loop test`() {
        val rendering = SpyView("BRAZIL")
        val game = GameLoop(gameView = rendering, maxAttempts = 1, proposedWord = "BRAZIL")

        game.mainLoop()

        assertContains(rendering.messages, "BRAZIL")
        assertContains(rendering.messages, "You won!")
        assertEquals(3, rendering.messages.size)
    }

    @Test
    fun `invalid location main loop test`() {
        val rendering = SpyView("ENGLAND")
        val game = GameLoop(gameView = rendering, maxAttempts = 1, proposedWord = "BRAZIL")

        game.mainLoop()

        assertContains(rendering.messages, "Invalid location :(")
        assertContains(rendering.messages, "You lost :(")
        assertEquals(3, rendering.messages.size)
    }

}

private class SpyView(
    val userInput: String,
) : GameView {
    val messages: MutableList<String> = mutableListOf()
    override fun displayGuess(guess: WordGuess): String = guess.value
    override fun readInput(): String = userInput
    override fun displayMessage(message: String) {
        messages.add(message)
    }
}

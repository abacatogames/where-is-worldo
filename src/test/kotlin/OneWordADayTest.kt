import io.github.cbaumont.generateWordForDate
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class OneWordADayTest {

    @Test
    fun `every day a new word is generated`() {
        val todaysWord = generateWordForDate(LocalDate.now())
        val tomorrowsWord = generateWordForDate(LocalDate.now().plusDays(1))

        assertNotEquals(todaysWord, tomorrowsWord)
    }

    @Test
    fun `same day same word`() {
        val todaysWord = generateWordForDate(LocalDate.now())
        val todaysWordAgain = generateWordForDate(LocalDate.now())

        assertEquals(todaysWord, todaysWordAgain)
    }

}
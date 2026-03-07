import com.abacatogames.geo.isAValidCountry
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidLocationsTest {
    @Test
    fun `finds location word in list of locations`() {
        val location = "Brazil"

        val result = location.isAValidCountry()

        assertTrue(result)
    }

    @Test
    fun `does not find location word in list of locations`() {
        val location = "Xique-xique"

        val result = location.isAValidCountry()

        assertFalse(result)
    }
}

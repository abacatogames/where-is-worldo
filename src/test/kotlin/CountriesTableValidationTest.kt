import com.abacatogames.geo.CountriesTable
import java.text.Normalizer
import java.util.Locale
import kotlin.test.Test
import kotlin.test.assertEquals

class CountriesTableValidationTest {

    @Test
    fun `countries table list should match ISO countries list`() {
        val isoCountries: Set<String> = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA2)
            .map { code ->
                Locale.Builder().setRegion(code).build().getDisplayCountry(Locale.ENGLISH).normaliseCountries()
            }
            .toSortedSet() + "Kosovo"

        val countriesFromTable = CountriesTable.map { it.name.normaliseCountries() }.toSortedSet()

        assertEquals(isoCountries, countriesFromTable)
    }
}

private fun String.normaliseCountries(): String {
    val firstStage = when (this) {
        "Cocos (Keeling) Islands" -> "Cocos Islands"
        "Congo - Brazzaville" -> "Republic of the Congo"
        "Congo - Kinshasa" -> "Democratic Republic of the Congo"
        "Hong Kong SAR China" -> "Hong Kong"
        "Macao SAR China" -> "Macau"
        "Myanmar (Burma)" -> "Myanmar"
        "Palestinian Territories" -> "Palestine"
        "Türkiye" -> "Turkey"
        "United States" -> "United States of America"
        else -> this
    }
    val secondStage = Normalizer.normalize(firstStage, Normalizer.Form.NFD)
        .replace("&", "and")
        .replace("St.", "Saint")
        .replace(".", "")
        .replace("'", "")
        .replace(Regex("[^\\p{ASCII}]"), "")
    return secondStage
}

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.random.Random

class DatabaseWithCacheTest {
    private val maxDatabaseSize = 5u
    private val maxCacheSize = 2u
    private val random = Random
    private lateinit var database: Database<Int, Int>

    @BeforeEach
    fun before() {
        database = DatabaseWithCache(LRUCache(maxCacheSize), DatabaseImpl(maxDatabaseSize))
    }

    @Test
    fun stressTest() {
        repeat(1000) {
            val i = random.nextInt(maxDatabaseSize.toInt())
            assert(database.get(i) == i + 1)
        }
    }
}
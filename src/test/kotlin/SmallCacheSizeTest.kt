import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import kotlin.random.Random

class SmallCacheSizeTest {
    private val maxDatabaseSize = 5u
    private val maxCacheSize = 2u
    private val random = Random
    private lateinit var database: Database<Int, Int>

    @BeforeEach
    fun before() {
        database = DatabaseWithCache(LRUCache(random.nextInt(maxCacheSize.toInt()).toUInt()), DatabaseImpl(maxDatabaseSize))
    }

    @RepeatedTest(1000)
    fun stressSmallTest() {
        repeat(5) {
            val i = random.nextInt(maxDatabaseSize.toInt())
            assert(database.get(i) == i + 1)
        }
    }

}
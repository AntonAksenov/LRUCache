import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LRUCacheTest {
    private val maxSize = 5u
    private lateinit var lruCache: LRUCache<Int, Int>

    @BeforeEach
    fun before() {
        lruCache = LRUCache(maxSize)
    }

    @Test
    fun setGetTest() {
        lruCache.setNew(1, 2)
        assert(lruCache.get(1) == 2)
        lruCache.setNew(2, 3)
        assert(lruCache.get(2) == 3)
        assert(lruCache.get(1) == 2)
        assertThrows<AssertionError> { lruCache.setNew(1, 3) }
    }

    @Test
    fun setToMuch() {
        for (i in 1..(maxSize + 1u).toInt())
            lruCache.setNew(i, i + 1)
        assert(lruCache.get(1) == null)
    }

    @Test
    fun containsOnlyLastN() {
        for (i in 1..(maxSize * 5u).toInt())
            lruCache.setNew(i, i + 1)
        for (i in 1..(maxSize * 4u).toInt())
            assert(lruCache.get(i) == null)
        for (i in (maxSize * 4u + 1u).toInt()..(maxSize * 5u).toInt())
            assert(lruCache.get(i) == i + 1)
    }
}
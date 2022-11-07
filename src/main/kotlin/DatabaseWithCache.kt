class DatabaseWithCache<K, V>(private val cache: Cache<K, V>, private val database: Database<K, V>) :
    Database<K, V> {
    override fun get(key: K): V? {
        return cache.get(key) ?: database.get(key)?.also { cache.setNew(key, it) }
    }
}
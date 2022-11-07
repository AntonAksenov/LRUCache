interface Database<K, V> {
    fun get(key: K): V?
}
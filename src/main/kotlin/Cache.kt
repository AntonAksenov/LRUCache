interface Cache<K, V> : Database<K, V> {
    fun setNew(key: K, value: V)
}

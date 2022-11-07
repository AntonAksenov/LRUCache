class DatabaseImpl(size: UInt) : Database<Int, Int> {
    private val list: List<Pair<Int, Int>>

    init {
        list = List(size.toInt()) {
            Pair(it, it + 1)
        }
    }

    override fun get(key: Int): Int? {
        return list.find {
            it.first == key
        }?.second
    }
}
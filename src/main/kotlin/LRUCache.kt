class LRUCache<K, V>(private val maxSize: UInt) : Cache<K, V> {
    private var first: Node<K, V> = TailNode()
    private val last = first
    private var size = 0u
    private val map = HashMap<K, RegularNode<K, V>>()

    override fun setNew(key: K, value: V) {
        if (maxSize == 0u)
            return
        assert(get(key) == null)
        if (size == maxSize) {
            last.prev?.let { removeFromCenter(it) } // if size == 0 let won't work
        }
        size++
        assert(size <= maxSize)

        val newFirst = RegularNode(value, key, null, first)
        first.prev = newFirst
        first = newFirst
        map[key] = newFirst
        assert(get(key) != null)
        assert(get(key) == value)
        assert(containsAll())
    }

    private fun removeFromCenter(node: RegularNode<K, V>) {
        size--
        map.remove(node.key)
        node.next?.prev = node.prev
        node.prev?.next = node.next
        if (node == first) {
            first = node.next!!
        }
        assert(!map.containsValue(node))
        assert(containsAll())
    }

    private fun containsAll(): Boolean {
        val nodes = getAllNodes()
        assert(nodes.size == size.toInt())
        return nodes.map { it.key }.containsAll(map.keys) &&
                map.values.map { it.value }.containsAll(nodes.map { it.value })

    }

    private fun getAllNodes(): Collection<RegularNode<K, V>> {
        return if (first is TailNode) {
            emptyList()
        } else {
            var cur: Node<K, V> = first
            val list = mutableListOf<RegularNode<K, V>>()
            while (cur is RegularNode) {
                list.add(cur)
                assert(cur.next != null)
                cur = cur.next!!
            }
            list
        }
    }

    override fun get(key: K): V? {
        return map[key]?.value
    }

    open class Node<K, V>(var prev: RegularNode<K, V>?, var next: Node<K, V>?)

    class RegularNode<K, V>(val value: V, val key: K, prev: RegularNode<K, V>?, next: Node<K, V>) :
        Node<K, V>(prev, next)

    class TailNode<K, V> : Node<K, V>(null, null)
}

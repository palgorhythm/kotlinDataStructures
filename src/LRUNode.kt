class LRUNode<K,V>(var key: K, var value: V, var next: LRUNode<K,V>? = null, var prev: LRUNode<K,V>? = null) {
    override fun toString(): String {
        return "key: ${key.toString()} value: ${value.toString()}"
    }
}
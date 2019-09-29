class LRUCache<K, V>(private val capacity: Int) {
    val doublyLinkedList = LRUDoublyLinkedList<K, V>()
    val map = mutableMapOf<K, LRUNode<K, V>>()
    private var size: Int = 0

    fun put(key: K, value: V) {
        if (map.containsKey(key)) {
            map[key]?.value = value
            doublyLinkedList.moveToHead(map[key]!!) // bad practice double bang..?
        } else {
            if (size >= capacity) {
                val keyOfTail = doublyLinkedList.tail!!.key
                doublyLinkedList.removeTail()
                map.remove(keyOfTail)
            } else {
                size++
            }
            val newNode = doublyLinkedList.addToHead(key, value)
            map[key] = newNode
        }
    }

    fun get(key: K): V? {
        if (map.contains(key)) {
            val node = map[key]!!
            doublyLinkedList.moveToHead(node)
            return node.value
        }
        return null
    }
}

fun main() {
    val lru = LRUCache<String, Int>(4)
    lru.put("a", 1)
    lru.put("b", 2)
    lru.put("c", 3)
    lru.put("d", 4) // after this, it's d c b a
    lru.put("e", 5) // after this, it's e d c b
    testArr("should return null for an element pushed out of the cache", listOf(lru.get("a")), listOf(null)) // null
    testArr(
        "should have pushed e to the front and b should be LRU item",
        listOf(lru.doublyLinkedList.head!!.key, lru.doublyLinkedList.tail!!.key),
        listOf("e", "b")
    ) // should be e b
    lru.get("b") // 2   b e d c
    lru.get("e") // 5   e b d c
    testArr(
        "should have e at the front and c at the back",
        listOf(lru.doublyLinkedList.head!!.key, lru.doublyLinkedList.tail!!.key),
        listOf("e", "c")
    )
    lru.put("f", 6) // f e b d
    testArr(
        "should have f at the front and d at the back",
        listOf(lru.doublyLinkedList.head!!.key, lru.doublyLinkedList.tail!!.key),
        listOf("f", "d")
    )
    lru.get("d") // 4    d f e b
    lru.get("c") // null
    println("final LL should be d f e b")
    lru.doublyLinkedList.forEach { println(it!!.key) } // d f e b
}

fun <T> testArr(assertion: String, testValues: List<T>, expectedValues: List<T>) {
    println("-----------------------------------")
    println("$assertion: ")
    var passed = true
    testValues.forEachIndexed { index, t ->
        if (t != expectedValues[index]) {
            passed = false
        }
        println("received $t, expected ${expectedValues[index]}")
        passed = passed || true
    }
    println(if (passed) "PASS" else "FAIL")
    println("-----------------------------------")
}
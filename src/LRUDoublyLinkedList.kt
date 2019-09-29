import java.lang.IllegalArgumentException

// https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/LinkedList.html


class LRUDoublyLinkedList<K,V>() : Iterator<LRUNode<K,V>?> {
    var head: LRUNode<K,V>? = null
    var tail: LRUNode<K,V>? = null
    private var size: Int = 0
    private var iterationNode: LRUNode<K,V>? = head

    private fun setIterationNode() {
        iterationNode = head
    }

    fun addToHead(key: K, value: V): LRUNode<K,V> { // when we put a new item in, it's the MRU
        val newNode = LRUNode(key, value)
        if (head == null) {
            head = newNode
            tail = newNode
        } else {
            addNodeToHead(newNode)
        }
        size++
        return newNode
    }

    private fun addNodeToHead(node: LRUNode<K,V>){
        head?.prev = node
        node.next = head
        head = node
        setIterationNode()
    }

    fun moveToHead(node: LRUNode<K,V>){ // when we use the item in LRU, so we move it to the front (MRU)
        node.prev?.next = node.next
        if(node.next == null && node.prev != null){ // if it's the tail and not also the head
            tail = node.prev
            addNodeToHead(node)
        } else if(node.prev != null) { // if it's not the head
            node.next!!.prev = node.prev
            node.prev!!.next = node.next
            addNodeToHead(node)
        }
        // if it's the head, do nothing
    }

    fun removeTail(){ // deletes the LRU item
        tail?.prev?.next = null
        tail = tail?.prev
        setIterationNode()
    }

    override fun hasNext(): Boolean {
        val hasNext = iterationNode != null
        if (!hasNext) iterationNode = head
        return hasNext
    }

    override fun next(): LRUNode<K,V>? {
        if (hasNext()) {
            val curIterationNode = iterationNode
            iterationNode = iterationNode?.next
            return curIterationNode
        } else {
            throw NoSuchElementException("No such element")
        }
    }
}

fun main() {
    val myLL = LRUDoublyLinkedList<String,Int>()
    test("head and tail should be null on initialization", myLL.head, null) // null
    test("head and tail should be null on initialization", myLL.tail, null) // null
    val tailNode = myLL.addToHead("a",1) // 1
    myLL.addToHead("b",2) // 2, 1
    myLL.addToHead("c", 3) // 3, 2, 1
    myLL.moveToHead(tailNode) // 1, 3, 2
    test("after moving the tail to the head", myLL.head?.value, 1)
    test("after moving the tail to the head", myLL.tail?.value, 2)
    myLL.removeTail()
    test("after removing the tail",myLL.head?.value, 1)
    test("after removing the tail", myLL.tail?.value, 3)
}

private fun <T> test(assertion: String, testValue: T, expectedValue: T){
    print("$assertion: ")
    println("received $testValue, expected $expectedValue -> ${if(testValue == expectedValue) "PASS" else "FAIL"}")

}
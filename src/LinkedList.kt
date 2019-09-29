import java.lang.IllegalArgumentException

// https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/LinkedList.html
class Node<T>(var value: T, var next: Node<T>? = null, var prev: Node<T>? = null) {
    override fun toString(): String {
        return value.toString()
    }
}

class LinkedList<T>(vararg list: T) : Iterator<Node<T>?> {
    private var head: Node<T>? = null
    private var size: Int = 0
    private var iterationNode: Node<T>? = head

    private fun setIterationNode() {
        iterationNode = head
    }

    fun add(element: T) {
        val newNode = Node(element)
        if (head == null) {
            head = newNode
        } else {
            findAt(size - 1)?.next = newNode
        }
        size++
        setIterationNode()
    }

    private fun findAt(index: Int): Node<T>? {
        if (index < 0 || index >= size) return null
        if (index == 0) {
            return head
        }
        var curIndex = 0
        var curNode = head
        while (curIndex < index) {
            curNode = curNode?.next
            curIndex++
        }
        return curNode
    }

    fun peek() = peekFirst()

    fun peekFirst(): T? {
        return head?.value
    }

    fun peekLast(): T? {
        return findAt(size - 1)?.value
    }

    fun removeFirst(): T? = removeAt(0)
    fun removeLast(): T? = removeAt(size - 1)

    fun removeAt(index: Int): T? {
        require(!(index < 0 || index >= size)) { "index is out of bounds" }
        val returnVal: T?
        if (size != 0) size--

        if (index == 0) {
            returnVal = head?.value
            head = head?.next
        } else {
            val nodeBeforeIndex = findAt(index - 1)
            returnVal = nodeBeforeIndex?.next?.value
            nodeBeforeIndex?.next = nodeBeforeIndex?.next?.next
        }
        setIterationNode()
        return returnVal
    }

    override fun hasNext(): Boolean {
        val hasNext = iterationNode != null
        if (!hasNext) iterationNode = head
        return hasNext
    }

    override fun next(): Node<T>? {
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
    val myLL = LinkedList<Int>()
    test(myLL.peek(), null) // null
    test(myLL.peekLast(), null) // null
    myLL.add(1)
    myLL.add(2)
    myLL.add(3)
    myLL.add(4)
    myLL.add(5)
    test(myLL.peek(), 1) // 1
    test(myLL.peekLast(), 5) // 5
    myLL.removeFirst()
    myLL.removeLast()
    test(myLL.peek(), 2) // 2
    test(myLL.peekLast(), 4) // 4

    myLL.forEach { println("element of LL: $it") } // prints 2, 3, 4
}

private fun <T> test(testValue: T, expectedValue: T){
    println("received $testValue, expected $expectedValue -> ${if(testValue == expectedValue) "PASS" else "FAIL"}")

}
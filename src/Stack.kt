class Stack<T : Comparable<T>>(list: MutableList<T>) : Iterator<T> {
    var itCounter: Int = 0
    var items: MutableList<T> = list

    private fun isEmpty(): Boolean = items.isEmpty()


    fun push(element: T) {
        items.add(element)
    }

    override fun toString() = items.toString()

    fun pop(): T? {
        return if (isEmpty()) {
            null
        } else {
            items.removeAt(items.size - 1)
        }
    }

    fun peek(): T? {
        return if (isEmpty()) {
            null
        } else {
            items[items.size - 1]
        }
    }

    override fun hasNext(): Boolean {
        val hasNext = itCounter < items.size
        if (!hasNext) itCounter = 0
        return hasNext
    }

    override fun next(): T {
        if (hasNext()) {
            val topPos: Int = (items.size - 1) - itCounter
            itCounter++
            return items[topPos]
        } else {
            throw NoSuchElementException("No such element")
        }
    }

}

fun main() {

    val initialValue = mutableListOf(10)
    val stack = Stack(initialValue)
    println(stack)
    stack.push(22)
    println(stack)
    stack.push(55)
    println(stack)
    stack.push(77)
    println(stack)
    stack.pop()
    println(stack)
}
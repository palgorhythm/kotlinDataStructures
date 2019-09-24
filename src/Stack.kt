class Stack<T>(list: MutableList<T>) : Iterable<T> {
    // generics allow our stack to hold any type, and we want it to extend iterator so we can iterate over it :)
    var items: MutableList<T> = list // don't mutate the input, store it here :)

    fun push(element: T) {
        items.add(element)
    }

    override fun toString() = items.toString()

    fun pop(): T? {
        return if (items.isEmpty()) {
            null
        } else {
            items.removeAt(items.size - 1)
        }
    }

    fun peek(): T? {
        return if (items.isEmpty()) {
            null
        } else {
            items[items.size - 1]
        }
    }

    override fun iterator(): Iterator<T>{
        // we could also make Stack extend Iterator and just have the hasNext() and next() overridden methods,
        // but it feels more conceptually correct for a stack to be "iterable" and to have it override the iterator func,
        // which just returns an object implementing the Iterator interface anyways.
        var itCounter = 0 // for the overloaded hasNext and next functions
        return object: Iterator<T>{
            override fun hasNext(): Boolean { // always needs to return a bool
                val hasNext = itCounter < this@Stack.items.size // can also use this@Stack to refer to the Stack's this, but can omit it
                if (!hasNext) itCounter = 0
                return hasNext
            }

            override fun next(): T { // will move backwards from the end of the MutableList. next should return the next el in items
                if (hasNext()) {
                    val topPos: Int = (items.size - 1) - itCounter
                    itCounter++
                    return items[topPos]
                } else {
                    throw NoSuchElementException("No such element")
                }
            }
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

    stack.forEach { println(it) }
}
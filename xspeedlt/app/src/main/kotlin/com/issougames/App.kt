package com.issougames

class App {

    // simple class to respresent a carton
    class Box(private val capacity: Int = 10) {
        private val items = mutableListOf<Int>()

        val currentSize: Int
            get() = items.sum()

        val remainingSpace: Int
            get() = capacity - currentSize

        fun add(item: Int) {
            if (item > remainingSpace) throw IllegalArgumentException("No more space")
            items.add(item)
        }

        override fun toString(): String = items.joinToString("")
    }

    // main function to optimize packing, the strategy is to always try to fit the item in the box with the least remaining space that can still accommodate it
    fun optimizePacking(input: String): String {
        val items = input.map { it.digitToInt() }
        
        val boxes = mutableListOf<Box>()

        for (item in items) {
            val bestBox = boxes
                .filter { it.remainingSpace >= item }
                .minByOrNull { it.remainingSpace - item }

            if (bestBox != null) {
                bestBox.add(item)
            } else {
                val newBox = Box()
                newBox.add(item)
                boxes.add(newBox)
            }
        }
        return boxes.joinToString("/")
    }
}
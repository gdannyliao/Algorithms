package com.ggdsn.algorithms.string

import java.util.*

object KeyIndexedCounting {
    /**
     * 排序这个数组，radix表示这个数组中有几种不同的元素（但其实应该是最大的那个数的值，否则count数组会越界）。
     */
    fun sort(array: IntArray, radix: Int) {
        val counts = IntArray(radix + 1)
        //计算每个元素的数量
        for (v in array) {
            counts[v + 1]++
        }
        //计算每个元素的起始位置
        for (i in 0 until radix) {
            counts[i + 1] += counts[i]
        }

        val aux = IntArray(array.size)
        for (v in array) {
            //把每个元素按它的起始位置填充，填充之后自然要移动起始位置（++的作用），
            // 移动后指针指向当前元素的结束位置。
            aux[counts[v]++] = v
        }

        //从辅助数组复制回原数组
        for ((i, v) in aux.withIndex()) {
            array[i] = v
        }
    }
}

object KeyIndexedCountingTest {
    @JvmStatic
    fun main(args: Array<String>) {
        testNormal()
        testSorted()
        test4Element()
        testEmpty()
    }

    fun testNormal() {
        val ints = intArrayOf(3, 4, 3, 2, 5, 6, 1, 1, 9, 8, 5)
        KeyIndexedCounting.sort(ints, 10)
        println("testNormal=${Arrays.toString(ints)}")
    }

    fun testSorted() {
        val ints = intArrayOf(1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4)
        KeyIndexedCounting.sort(ints, 10)
        println("testSorted=${ints.contentToString()}")
    }

    fun test4Element() {
        val ints = intArrayOf(3, 4, 2, 5, 5, 3, 2, 1, 1, 0)
        KeyIndexedCounting.sort(ints, 6)
        println("test4Elements()=${ints.contentToString()}")
    }

    fun testEmpty() {
        KeyIndexedCounting.sort(intArrayOf(), 10)
        println("testEmpty()")
    }
}
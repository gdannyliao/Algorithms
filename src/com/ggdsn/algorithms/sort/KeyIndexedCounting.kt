package com.ggdsn.algorithms.sort

object KeyIndexedCounting {
    /**
     * 排序这个数组，elementCount表示这个数组中有几种不同的元素。
     */
    fun sort(array: IntArray, elementCount: Int) {
        val counts = IntArray(elementCount + 1)
        //计算每个元素的数量
        for (v in array) {
            counts[v + 1]++
        }
        //计算每个元素的起始位置
        for (i in 0..elementCount) {
            counts[i + 1] += counts[i]
        }

        val aux = IntArray(array.size)
        for (v in array) {
            //把每个元素按它的起始位置填充，填充之后自然要移动其实位置（++的作用）
            aux[counts[v]++] = v
        }

        for ((i, v) in aux.withIndex()) {
            array[i] = v
        }
    }
}
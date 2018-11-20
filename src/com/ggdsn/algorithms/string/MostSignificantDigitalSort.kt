package com.ggdsn.algorithms.string

/**
 * MSD 高位优先基数排序算法
 */
object MostSignificantDigitalSort {
    private var aux: Array<String?>? = null
    private var radix = 256
    private var maxLength = 0
    fun sort(array: Array<String?>, radix: Int) {
        this.radix = radix
        aux = Array(array.size, { i ->
            val length = array[i]?.length ?: 0
            if (length > maxLength) {
                maxLength = length
            }
            null
        })
        sortColumn(array, 0, array.size, 0)
        aux = null
    }

    private fun sortColumn(array: Array<String?>, low: Int, high: Int, col: Int) {
        //变长的情况，如何判断处理完了呢？
        //高位优先和低位优先不一样，高位优先是先计算整体，再算局部，不像低位优先每一列都计算整体
        if (col >= maxLength) return
        val counts = IntArray(radix + 2)
        for (i in low until high) {
            //counts[1]空出来，用于计算没有这么长的字符串的数量
            counts[charAt(array[i], col) + 2]++
        }

        for (i in 0..radix) {
            counts[i + 1] += counts[i]
        }

        val aux = aux ?: return
        for (i in low until high) {
            aux[counts[charAt(array[i], col) + 1]++] = array[i]
        }

        for (i in low until high) {
            //low和high还是整体的index，但这里要取的是局部的index
            array[i] = aux[i - low]
        }

        for (r in 0 until radix) {
            sortColumn(array, low + counts[r], low + counts[r + 1], col + 1)
        }
    }

    private fun charAt(str: String?, col: Int): Int {
        str ?: return -1
        return if (col >= str.length) -1 else str[col].toInt()
    }
}
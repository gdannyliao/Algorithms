package com.ggdsn.algorithms.graph.shortestpath

import com.ggdsn.algorithms.alg4work.Alg4
import com.ggdsn.algorithms.graph.WeightDigraphImpl
import com.ggdsn.algorithms.graph.WeightDirectEdge
import java.io.File
import java.io.FileInputStream
import java.util.*

interface ShortestPath {
    fun pathTo(vertex: Int): Array<Int>
    fun destinationTo(vertex: Int): Double
    fun hasPathTo(vertex: Int): Boolean
}

class Test {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val scanner = Scanner(FileInputStream(Alg4.DATA_DIR + File.separator + args[0]))
            val graph = WeightDigraphImpl(scanner.nextInt())
            val edgeCount = scanner.nextInt()
            for (i in 0 until edgeCount) {
                graph.addEdge(WeightDirectEdge(scanner.nextInt(), scanner.nextInt(), scanner.nextDouble()))
            }

            val start = 0
            val path = ShortestPathImpl(graph, start)
            for (v in 0 until graph.vertexCount()) {
                if (path.hasPathTo(v)) {
                    print("$start to $v, weight=${path.destinationTo(v)} ")
                    val edges = path.pathTo(v)
                    print(" path=")
                    for (e in edges) {
                        print("$e ")
                    }
                    println()
                }
            }

        }
    }
}
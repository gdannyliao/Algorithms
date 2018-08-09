package com.ggdsn.algorithms.graph

import com.ggdsn.algorithms.alg4work.Alg4
import edu.princeton.cs.algs4.IndexMinPQ
import java.io.File
import java.io.FileInputStream
import java.util.*

class MinSpanningTreeAtOnce(private val graph: WeightGraph) : MinSpanningTree {
    private val queue = IndexMinPQ<Double>(graph.vertexCount())
    private val edgeTo: Array<WeightEdge?> = Array(graph.vertexCount(), { null })
    private val weightTo: Array<Double> = Array(graph.vertexCount(), { Double.MAX_VALUE })
    private val marked: Array<Boolean> = Array(graph.vertexCount(), { false })

    init {
        weightTo[0] = 0.0
        visit(0)
        while (!queue.isEmpty) {
            visit(queue.delMin())
        }
    }

    private fun visit(vertex: Int) {
        marked[vertex] = true
        val adj = graph.getAdjacent(vertex)
        for (e in adj) {
            val other = e.other(vertex)
            if (marked[other]) continue
            //如果一条边的权重比已知的最小权重要小，则用这条边替换旧边
            if (e.weight < weightTo[other]) {
                weightTo[other] = e.weight
                edgeTo[other] = e

                if (queue.contains(other))
                    queue.changeKey(other, e.weight)
                else queue.insert(other, e.weight)
            }
        }
    }

    override fun getEdges(): Iterable<WeightEdge> = edgeTo.filterNotNull()

    override fun allWeight(): Double = weightTo.sumByDouble { it }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val scanner = Scanner(FileInputStream(Alg4.DATA_DIR + File.separator + args[0]))
            val graph = WeightGraphImpl(scanner.nextInt())
            val edgeCount = scanner.nextInt()
            for (i in 0 until edgeCount) {
                graph.addEdge(WeightEdge(scanner.nextInt(), scanner.nextInt(), scanner.nextDouble()))
            }

            val mst = MinSpanningTreeAtOnce(graph)
            for (t in mst.getEdges()) {
                println(t)
            }
            println("weight sum =${mst.allWeight()}")
        }
    }
}
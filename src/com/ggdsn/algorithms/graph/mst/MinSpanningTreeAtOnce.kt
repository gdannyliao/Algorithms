package com.ggdsn.algorithms.graph.mst

import com.ggdsn.algorithms.graph.WeightEdge
import com.ggdsn.algorithms.graph.WeightGraph
import edu.princeton.cs.algs4.IndexMinPQ

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
                //edgeTo的另一个端点要从这条边中取出
                edgeTo[other] = e

                if (queue.contains(other))
                    queue.changeKey(other, e.weight)
                else queue.insert(other, e.weight)
            }
        }
    }

    override fun getEdges(): Iterable<WeightEdge> = edgeTo.filterNotNull()

    override fun allWeight(): Double = weightTo.sumByDouble { it }
}
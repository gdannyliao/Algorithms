package com.ggdsn.algorithms.graph.shortestpath

import com.ggdsn.algorithms.graph.WeightDigraph
import com.ggdsn.algorithms.graph.WeightDirectEdge
import java.util.*

class ShortestPathImpl(val graph: WeightDigraph, val start: Int) : ShortestPath {
    private val edgeTo: Array<WeightDirectEdge?> = Array(graph.vertexCount(), { null })
    private val destTo: Array<Double> = Array(graph.vertexCount(), { Double.MAX_VALUE })
    private val queue: LinkedList<Int> = LinkedList()

    init {
        //这个算法相当于dijkstra算法的延迟实现。因为并不在队列中删除边。失效边会被拿出来进行判断，但不会有更新操作
        queue.addLast(start)
        destTo[start] = 0.0
        while (queue.isNotEmpty()) {
            relax(queue.removeFirst())
        }
    }

    private fun relax(from: Int) {
        val edges = graph.getAdjacentEdges(from)
        for (e in edges) {
            val to = e.to
            //如果一条新路径的总权重更小，则把这条边记录到最短路径中
            if (e.weight + destTo[from] < destTo[to]) {
                destTo[to] = e.weight + destTo[from]
                edgeTo[to] = e
                queue.addLast(to)
            }
        }
    }

    override fun pathTo(vertex: Int): Array<Int> {
        val list = LinkedList<Int>()
        var i = vertex
        list.addFirst(vertex)
        while (true) {
            val edge = edgeTo[i] ?: break
            if (edge.from == start) break
            list.addFirst(edge.from)
            i = edge.from
        }
        return list.toArray(Array(list.size, { 0 }))
    }

    override fun destinationTo(vertex: Int): Double {
        var i = vertex
        var weightSum = 0.0
        while (true) {
            val edge = edgeTo[i] ?: break
            weightSum += edge.weight
            i = edge.from
        }
        return weightSum
    }

    override fun hasPathTo(vertex: Int): Boolean = destTo[vertex] != Double.MAX_VALUE
}
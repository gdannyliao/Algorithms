package com.ggdsn.algorithms.graph.mst

import com.ggdsn.algorithms.graph.WeightEdge
import com.ggdsn.algorithms.graph.WeightGraph
import com.ggdsn.algorithms.unionfind.QuickFind
import com.ggdsn.algorithms.unionfind.UnionFind
import java.util.*

class MinSpanningTreeKuskal(private val graph: WeightGraph) : MinSpanningTree {
    private val queue: PriorityQueue<WeightEdge> = PriorityQueue(Comparator<WeightEdge> { o1, o2 ->
        return@Comparator java.lang.Double.compare(o1.weight, o2.weight)
    })
    private val tree = mutableListOf<WeightEdge>()
    private val unionFind: UnionFind = QuickFind(graph.vertexCount())

    init {
        val count = graph.vertexCount()
        for (i in 0 until count) {
            //将所有节点插入队列中，以便排序。有重复插入，可以考虑消除
            queue.addAll(graph.getAdjacent(i))
        }
        while (queue.isNotEmpty()) {
            val edge = queue.poll()
            //用并查集来区分是否已经加入到树中
            if (unionFind.connected(edge.p1, edge.p2)) continue
            unionFind.union(edge.p1, edge.p2)
            tree.add(edge)
            //所有节点都连接上之后就可以结束了
            if (unionFind.count() == 1)
                break
        }
    }

    override fun getEdges(): Iterable<WeightEdge> = tree

    override fun allWeight(): Double = tree.sumByDouble { it.weight }
}
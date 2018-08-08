package com.ggdsn.algorithms.graph

interface WeightGraph {
    fun addEdge(edge: WeightEdge)
    fun getAdjacent(vertex: Int): Set<WeightEdge>
    fun edgeCount(): Int
    fun vertexCount():Int
}

data class WeightEdge(val p1: Int, val p2: Int, val weight: Double) {
    fun other(vertex: Int): Int = if (vertex == p1) p2 else p1
}

class WeightGraphImpl(vertexCount: Int) : WeightGraph {
    private val adj: Array<MutableSet<WeightEdge>> = Array(vertexCount, { mutableSetOf<WeightEdge>() })
    private var edgeCount: Int = 0
    override fun addEdge(edge: WeightEdge) {
        if (adj[edge.p1].contains(edge)) return

        adj[edge.p1].add(edge)
        adj[edge.p2].add(edge)
        edgeCount++
    }

    override fun getAdjacent(vertex: Int): Set<WeightEdge> {
        if (vertex >= adj.size || vertex < 0) throw IllegalArgumentException()
        return adj[vertex]
    }

    override fun edgeCount(): Int = edgeCount

    override fun vertexCount(): Int = adj.size
}
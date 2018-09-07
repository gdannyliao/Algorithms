package com.ggdsn.algorithms.graph

interface WeightDigraph {
    fun addEdge(edge: WeightDirectEdge)
    fun vertexCount(): Int
    fun edgeCount(): Int
    fun getAdjacentEdges(vertex: Int): Set<WeightDirectEdge>
}

class WeightDirectEdge(val from: Int, val to: Int, val weight: Double) {
    fun other(vertex: Int): Int = if (from == vertex) to else from

    override fun toString(): String {
        return "$from -> $to $weight"
    }
}

class WeightDigraphImpl(vertexCount: Int) : WeightDigraph {
    private val vertexs = Array(vertexCount, { mutableSetOf<WeightDirectEdge>() })
    private var edgeCount = 0
    override fun addEdge(edge: WeightDirectEdge) {
        val adj = vertexs[edge.from]
        if (adj.contains(edge)) return
        edgeCount++
        adj.add(edge)
    }

    override fun getAdjacentEdges(vertex: Int): Set<WeightDirectEdge> {
        if (vertex >= vertexs.size || vertex < 0) throw IllegalArgumentException()
        return vertexs[vertex]
    }

    override fun vertexCount() = vertexs.size

    override fun edgeCount(): Int = edgeCount
}
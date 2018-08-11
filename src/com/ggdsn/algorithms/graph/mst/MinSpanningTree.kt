package com.ggdsn.algorithms.graph.mst

import com.ggdsn.algorithms.graph.WeightEdge

interface MinSpanningTree {
    fun getEdges(): Iterable<WeightEdge>
    fun allWeight(): Double
}
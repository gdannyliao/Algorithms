package com.ggdsn.algorithms.graph.augmentingpath

import com.ggdsn.algorithms.alg4work.Alg4
import edu.princeton.cs.algs4.*
import edu.princeton.cs.algs4.FlowEdge
import java.io.FileInputStream

class AugmentingPath(val flowNetwork: FlowNetwork, val s: Int, val t: Int) {
    private lateinit var edgeTo: Array<FlowEdge?>
    private lateinit var marked: Array<Boolean>
    private var maxFlow = 0.0

    init {
        while (hasAugmentingPath()) {
            var bottle = Double.POSITIVE_INFINITY
            var v = t
            while (v != s) {
                val edge = edgeTo[v] ?: continue
                //取这条增广路上最小的流量，加入到所有路径中。直到没有增广路为止
                bottle = Math.min(bottle, edge.residualCapacityTo(v))
                v = edge.other(v)
            }
            v = t
            while (v != s) {
                val edge = edgeTo[v] ?: continue
                //如果有增广路，则尝试走这条路
                edge.addResidualFlowTo(v, bottle)
                v = edge.other(v)
            }
            maxFlow += bottle
        }
    }

    fun hasAugmentingPath(): Boolean {
        marked = Array(flowNetwork.V(), { false })
        edgeTo = Array(flowNetwork.V(), { null })
        //这里需要每次创建新的，因为每次计算了增广路之后，可走的路径可能会变化。
        val queue = Queue<Int>()
        marked[s] = true
        queue.enqueue(s)
        while (!queue.isEmpty && !marked[t]) {
            //广度优先搜索遍历整张网络，看看是否有增广路径
            //这里求的是单条增广路经，并不是所有的增广路径
            //在这里增广路径，我理解成能增加流量的路
            val v = queue.dequeue()
            for (e in flowNetwork.adj(v)) {
                val other = e.other(v)
                if (e.residualCapacityTo(other) > 0 && !marked[other]) {
                    edgeTo[other] = e
                    marked[other] = true
                    queue.enqueue(other)
                }
            }
        }
        return marked[t]
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            System.setIn(FileInputStream(Alg4.getFile(args[0])))
            // create flow network with V vertices and E edges
            val V = Integer.parseInt(StdIn.readString())
            val s = 0
            val t = V - 1
            val flowNetwork = FlowNetwork(In(Alg4.getFile(args[0])))
            StdOut.println(flowNetwork)

            // compute maximum flow and minimum cut
            val maxflow = AugmentingPath(flowNetwork, s, t)
            StdOut.println("Max flow from $s to $t")
            for (v in 0 until flowNetwork.V()) {
                for (e in flowNetwork.adj(v)) {
                    if (v == e.from() && e.flow() > 0)
                        StdOut.println("   $e")
                }
            }

            // print min-cut
            StdOut.print("Min cut: ")
            for (v in 0 until flowNetwork.V()) {
                if (maxflow.inCut(v)) StdOut.print(v.toString() + " ")
            }
            StdOut.println()

            StdOut.println("Max flow = " + maxflow.value())
        }
    }

    /**
     * 最后找到的一条增广路径包含的节点就是最小切分？
     */
    private fun inCut(v: Int): Boolean = marked[v]

    private fun value(): Double = maxFlow
}
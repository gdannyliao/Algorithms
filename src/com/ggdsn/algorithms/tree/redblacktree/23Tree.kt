package com.ggdsn.algorithms.tree.redblacktree

class TwoThreeTree<T : Comparable<T>> {
    private var root: Node<T>? = null

    fun insert(value: T) {
        root = insert(value, root)
        rtValue?.let {
            val newRoot = Node2(it.first)
            newRoot.left = it.second
            newRoot.right = it.third
            rtValue = null
            root = newRoot
        }
    }

    private fun insert(value: T, node: Node<T>?): Node<T> {
        if (node == null) return Node2(value)

        when (node) {
            is Node2 -> {
                when {
                    value < node.value -> {
                        if (node.left == null) {
                            return Node3(value, node.value)
                        } else {
                            node.left = insert(value, node.left)
                            rtValue?.let {
                                val newNode = Node3(it.first, node.value)
                                newNode.left = it.second
                                newNode.mid = it.third
                                newNode.right = node.right
                                rtValue = null
                                return newNode
                            }
                        }
                    }
                    value == node.value -> return node
                    value > node.value -> {
                        if (node.right == null) {
                            return Node3(node.value, value)
                        } else {
                            node.right = insert(value, node.right)
                            rtValue?.let {
                                val newNode = Node3(node.value, it.first)
                                newNode.left = node.left
                                newNode.mid = it.second
                                newNode.right = it.third
                                rtValue = null
                                return newNode
                            }
                        }
                    }
                }
            }
            is Node3 -> {
                when {
                    value < node.smaller -> {
                        if (node.left == null) {
                            val ret = divide(toNode4(node, value, node.smaller, node.bigger, 0))
                            rtValue = ret
                            return ret.second
                        } else {
                            val newLeft = insert(value, node.left)
                            val rt = rtValue
                            if (rt == null) {
                                node.left = newLeft
                            } else {
                                val node4 = toNode4(node, rt.first, node.smaller, node.bigger, 0)
                                node4.left = rt.second
                                node4.midLeft = rt.third
                                val ret = divide(node4)
                                rtValue = ret
                                return ret.second
                            }
                        }
                    }
                    value > node.smaller && value < node.bigger -> {
                        if (node.mid == null) {
                            val ret = divide(toNode4(node, node.smaller, value, node.bigger, 1))
                            rtValue = ret
                            return ret.second
                        } else {
                            val newMid = insert(value, node.mid)
                            val rt = rtValue
                            if (rt == null) node.mid = newMid
                            else {
                                val node4 = toNode4(node, node.smaller, rt.first, node.bigger, 1)
                                node4.midLeft = rt.second
                                node4.midRight = rt.third
                                val ret = divide(node4)
                                rtValue = ret
                                return ret.second
                            }
                        }
                    }
                    value > node.bigger -> {
                        if (node.right == null) {
                            val ret = divide(toNode4(node, node.smaller, node.bigger, value, 2))
                            rtValue = ret
                            return ret.second
                        } else {
                            val newRight =  insert(value, node.right)
                            val rt = rtValue
                            if (rt == null) node.right = newRight
                            else {
                                val node4 = toNode4(node, node.smaller, node.bigger, rt.first, 2)
                                node4.midRight = rt.second
                                node4.right = rt.third
                                val ret = divide(node4)
                                rtValue = ret
                                return ret.second
                            }
                        }
                    }
                    else -> return node
                }
            }
        }
        return node
    }

    private var rtValue: Triple<T, Node<T>, Node<T>>? = null

    private fun divide(node: Node4<T>): Triple<T, Node<T>, Node<T>> {
        val nodeLeft = Node2(node.smaller)
        nodeLeft.left = node.left
        nodeLeft.right = node.midLeft

        val nodeRight = Node2(node.bigger)
        nodeRight.left = node.midRight
        nodeRight.right = node.right
        return Triple<T, Node<T>, Node<T>>(node.center, nodeLeft, nodeRight)
    }

    private fun toNode4(node3: Node3<T>, smaller: T, center: T, bigger: T, from: Int): Node4<T> {
        return when (from) {
            0 -> {
                //左边的值更新
                val node4 = Node4(smaller, center, bigger)
                node4.midLeft = node3.left
                node4.midRight = node3.mid
                node4.right = node3.right
                node4
            }
            1 -> {
                //中间的值更新
                val node4 = Node4(smaller, center, bigger)
                node4.left = node3.left
                node4.right = node3.right
                val mid = node3.mid
                if (mid != null) {
                    val midVal = mid.valueAt(0)!!
                    if (midVal > center)
                        node4.midRight = mid
                    else node4.midLeft = mid
                }
                node4
            }
            2 -> {
                val node4 = Node4(smaller, center, bigger)
                node4.left = node3.left
                node4.midLeft = node3.mid
                node4.midRight = node3.right
                node4
            }
            else -> throw IllegalStateException()
        }
    }

    override fun toString(): String {
        val str = StringBuilder("[")
        print(str, root)
        str.append("]")
        return str.toString()
    }

    private fun print(sb: StringBuilder, node: Node<T>?) {
        if (node == null) return
        sb.append(node.toString())
        for (i in 0 until node.childCount())
            print(sb, node.childAt(i))
    }
}

interface Node<T> {
    fun valueAt(idx: Int): T?
    fun childAt(idx: Int): Node<T>?
    fun childCount(): Int
}

class Node2<T>(var value: T) : Node<T> {
    var left: Node<T>? = null
    var right: Node<T>? = null

    override fun valueAt(idx: Int): T? = if (idx == 0) value else throw IndexOutOfBoundsException()

    override fun childAt(idx: Int): Node<T>? = when (idx) {
        0 -> left
        1 -> right
        else -> throw IndexOutOfBoundsException()
    }

    override fun childCount(): Int {
        return 2
    }

    override fun toString(): String {
        return "[$value]"
    }
}

class Node3<T>(var smaller: T, var bigger: T) : Node<T> {
    var left: Node<T>? = null
    var mid: Node<T>? = null
    var right: Node<T>? = null

    override fun valueAt(idx: Int): T? = when (idx) {
        0 -> smaller
        1 -> bigger
        else -> throw IndexOutOfBoundsException()
    }

    override fun childAt(idx: Int): Node<T>? = when (idx) {
        0 -> left
        1 -> mid
        2 -> right
        else -> throw IndexOutOfBoundsException()
    }

    override fun childCount(): Int {
        return 3
    }

    override fun toString(): String {
        return "[$smaller,$bigger]"
    }
}

class Node4<T>(var smaller: T, var center: T, var bigger: T) : Node<T> {
    var left: Node<T>? = null
    var midLeft: Node<T>? = null
    var midRight: Node<T>? = null
    var right: Node<T>? = null

    override fun valueAt(idx: Int): T? = when (idx) {
        0 -> smaller
        1 -> center
        2 -> bigger
        else -> throw IndexOutOfBoundsException()
    }

    override fun childAt(idx: Int): Node<T>? = when (idx) {
        0 -> left
        1 -> midLeft
        2 -> midRight
        3 -> right
        else -> throw IndexOutOfBoundsException()
    }

    override fun childCount(): Int {
        return 4
    }
}
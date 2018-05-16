package com.ggdsn.algorithms.tree.redblacktree

class TwoThreeTree<T : Comparable<T>> {
    private var root: Node<T>? = null

    fun insert(value: T) {
        val rt = insert(value, root)
        if (rt is Node4) {
            val divide = divide(rt)
            val newRoot = Node2(divide.first)
            newRoot.left = divide.second
            newRoot.right = divide.third
            root = newRoot
        } else root = rt
    }

    /**
     * 插入一个值并返回它插入的节点。有可能返回的是4节点，此时需要调用者对这个4节点做分割
     */
    private fun insert(value: T, node: Node<T>?): Node<T> {
        if (node == null) return Node2(value)

        when (node) {
            is Node2 -> {
                when {
                    value < node.value -> {
                        if (node.left == null) {
                            return Node3(value, node.value)
                        } else {
                            val rt = insert(value, node.left)
                            if (rt is Node4) {
                                val divide = divide(rt)
                                val newNode = Node3(divide.first, node.value)
                                newNode.left = divide.second
                                newNode.mid = divide.third
                                newNode.right = node.right
                                return newNode
                            } else node.left = rt
                        }
                    }
                    value == node.value -> return node
                    value > node.value -> {
                        if (node.right == null) {
                            return Node3(node.value, value)
                        } else {
                            val rt = insert(value, node.right)
                            if (rt is Node4) {
                                val divide = divide(rt)
                                val newNode = Node3(node.value, divide.first)
                                newNode.left = node.left
                                newNode.mid = divide.second
                                newNode.right = divide.third
                                return newNode
                            } else node.right = rt
                        }
                    }
                }
            }
            is Node3 -> {
                when {
                    value < node.smaller -> {
                        if (node.left == null) {
                            return toNode4(LEFT, node, value, node.smaller, node.bigger)
                        } else {
                            val newLeft = insert(value, node.left)
                            if (newLeft is Node4) {
                                val divide = divide(newLeft)
                                val node4 = toNode4(LEFT, node, divide.first, node.smaller, node.bigger)
                                node4.left = divide.second
                                node4.midLeft = divide.third
                                return node4
                            } else node.left = newLeft
                        }
                    }
                    value > node.smaller && value < node.bigger -> {
                        if (node.mid == null) {
                            return toNode4(MID, node, node.smaller, value, node.bigger)
                        } else {
                            val newMid = insert(value, node.mid)
                            if (newMid is Node4) {
                                val divide = divide(newMid)
                                val node4 = toNode4(MID, node, node.smaller, divide.first, node.bigger)
                                node4.midLeft = divide.second
                                node4.midRight = divide.third
                                return node4
                            } else node.mid = newMid
                        }
                    }
                    value > node.bigger -> {
                        if (node.right == null) {
                            return toNode4(RIGHT, node, node.smaller, node.bigger, value)
                        } else {
                            val newRight = insert(value, node.right)
                            if (newRight is Node4) {
                                val divide = divide(newRight)
                                val node4 = toNode4(RIGHT, node, node.smaller, node.bigger, divide.first)
                                node4.midRight = divide.second
                                node4.right = divide.third
                                return node4
                            } else node.right = newRight
                        }
                    }
                    else -> return node
                }
            }
        }
        return node
    }

    private fun divide(node: Node4<T>): Triple<T, Node<T>, Node<T>> {
        val nodeLeft = Node2(node.smaller)
        nodeLeft.left = node.left
        nodeLeft.right = node.midLeft

        val nodeRight = Node2(node.bigger)
        nodeRight.left = node.midRight
        nodeRight.right = node.right
        return Triple<T, Node<T>, Node<T>>(node.center, nodeLeft, nodeRight)
    }

    private fun toNode4(from: Int, node3: Node3<T>, smaller: T, center: T, bigger: T): Node4<T> {
        return when (from) {
            LEFT -> {
                //左边的值更新
                val node4 = Node4(smaller, center, bigger)
                node4.midLeft = node3.left
                node4.midRight = node3.mid
                node4.right = node3.right
                node4
            }
            MID -> {
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
            RIGHT -> {
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

    companion object {
        const val LEFT = 0
        const val MID = 1
        const val RIGHT = 2
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
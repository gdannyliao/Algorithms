package com.ggdsn.algorithms.tree.redblacktree

class RedBlackTree<T : Comparable<T>> : Tree<T> {
    private var root: Node<T>? = null

    override fun insert(value: T) {
        root = insert(value, root)
        root?.color = false
    }

    private fun insert(value: T, node: Node<T>?): Node<T> {
        if (node == null) return Node(value)

        when {
            value < node.value -> node.left = insert(value, node.left)
            value == node.value -> return node
            value > node.value -> node.right = insert(value, node.right)
        }

        var head = node
        //插入右边左旋（2节点或3节点都需要）
        if (isRed(head.right) && !isRed(head.left))
            head = rotateLeft(head)
        //左旋之后，如果连续两个红链接，则需要右旋（准备4节点分裂）
        //因为要旋转头节点，但又要判断是否两个连续的红链接，所以这里必须向下探2层。只探一层无法达到想要的效果
        if (isRed(head.left) && isRed(head.left?.left))
            head = rotateRight(head)
        //开始分裂
        if (isRed(head.left) && isRed(head.right))
            flipColor(head)
        return head
    }

    private fun isRed(node: Node<T>?): Boolean {
        return node?.color ?: Black
    }

    private fun flipColor(node: Node<T>) {
        node.left?.color = Black
        node.right?.color = Black
        node.color = Red
    }

    private fun rotateRight(node: Node<T>): Node<T> {
        val newHead = node.left ?: return node
        node.left = newHead.right
        newHead.right = node
        newHead.color = node.color
        node.color = Red
        return newHead
    }

    private fun rotateLeft(node: Node<T>): Node<T> {
        val newHead = node.right ?: return node
        node.right = newHead.left
        newHead.left = node
        newHead.color = node.color
        node.color = Red
        return newHead
    }

    private class Node<T>(var value: T) {
        var left: Node<T>? = null
        var right: Node<T>? = null
        var color = true

        override fun toString(): String {
            val left = left
            if (left != null && left.color == Red)
                return "[${left.value},$value]"
            if (color != Red)
                return "[$value]"
            return ""
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
        print(sb, node.left)
        print(sb, node.right)
    }

    companion object {
        const val Red = true
        const val Black = false
    }
}
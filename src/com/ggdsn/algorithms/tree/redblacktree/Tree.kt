package com.ggdsn.algorithms.tree.redblacktree

interface Tree<T : Comparable<T>> {
    fun insert(value: T)
}
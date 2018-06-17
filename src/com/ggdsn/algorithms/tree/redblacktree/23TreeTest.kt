package com.ggdsn.algorithms.tree.redblacktree

import kotlin.test.assertTrue

fun main(args: Array<String>) {
    val tree = RedBlackTree<Char>()
    testRootDivide(tree)
    testFatherIs2NodeInsertLeft(tree)
    testFatherIs2NodeInsertRight(tree)
    testFatherIs3NodeInsertLeft(tree)
    testFatherIs3NodeInsertMid(tree)
    testFatherIs3NodeInsertRight(tree)
}

/**
 * 测试根节点分裂
 */
fun testRootDivide(tree: Tree<Char>) {
    tree.insert('a')
    tree.insert('c')
    tree.insert('b')
    assertTrue { tree.toString() == "[[b][a][c]]" }
}

/**
 * 父节点是2节点，左侧插入
 */
fun testFatherIs2NodeInsertLeft(tree: Tree<Char>) {
    tree.insert('f')
    tree.insert('e')
    tree.insert('a')
    tree.insert('b')
    tree.insert('c')
    assertTrue { tree.toString() == "[[b,e][a][c][f]]" }
}

fun testFatherIs2NodeInsertRight(tree: Tree<Char>) {
    tree.insert('f')
    tree.insert('e')
    tree.insert('a')
    tree.insert('h')
    tree.insert('g')
    assertTrue { tree.toString() == "[[e,g][a][f][h]]" }
}

fun testFatherIs3NodeInsertLeft(tree: Tree<Char>) {
    tree.insert('i')
    tree.insert('k')
    tree.insert('g')
    tree.insert('e')
    tree.insert('c')
    tree.insert('a')
    tree.insert('b')
    assertTrue { tree.toString() == "[[e][b][a][c][i][g][k]]" }
}

fun testFatherIs3NodeInsertMid(tree: Tree<Char>) {
    tree.insert('i')
    tree.insert('k')
    tree.insert('g')
    tree.insert('e')
    tree.insert('c')
    tree.insert('h')
    tree.insert('f')
    assertTrue { tree.toString() == "[[g][e][c][f][i][h][k]]" }
}

fun testFatherIs3NodeInsertRight(tree: Tree<Char>) {
    tree.insert('i')
    tree.insert('k')
    tree.insert('g')
    tree.insert('e')
    tree.insert('c')
    tree.insert('j')
    tree.insert('l')
    assertTrue { tree.toString() == "[[i][e][c][g][k][j][l]]" }
}
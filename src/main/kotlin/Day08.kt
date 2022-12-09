package day08

import java.io.File

val input = File("inputs/day08.txt").readText().lines()
val testInput = File("inputs/day08t.txt").readText()

data class Vec2(val x: Int, val y: Int)

var width = 0
var gameHeight = 0

fun main() {
    val myMap = mutableMapOf<Vec2, Int>()
    width = input[0].length
    gameHeight = input.size
    for ((y, line) in input.withIndex()) {
        for ((x, char) in line.withIndex()) {
            myMap[Vec2(x, y)] = char.digitToInt()
        }
    }
    println(myMap.count { it.isVisible(myMap) })
    println(myMap.maxOf { it.scenicScore(myMap) })
}

private fun Map.Entry<Vec2, Int>.scenicScore(forest: Map<Vec2, Int>): Int {
    val (coord, treeHeight) = this
    val (x, y) = coord
    if (x == 0 || y == 0 || x == width - 1 || y == gameHeight - 1) return 0

    val left = (x - 1 downTo 0)
    val lS = left.takeWhile { forest.getValue(Vec2(it, y)) < treeHeight }.count()
    val ols = if (lS < left.count()) 1 else 0
    val right = (x + 1 until width)
    val rS = right.takeWhile { forest.getValue(Vec2(it, y)) < treeHeight }.count()
    val ors = if (rS < right.count()) 1 else 0
    val top = (y - 1 downTo 0)
    val tS = top.takeWhile { forest.getValue(Vec2(x, it)) < treeHeight }.count()
    val trs = if (tS < top.count()) 1 else 0
    val bottom = (y + 1 until gameHeight)
    val bS = bottom.takeWhile { forest.getValue(Vec2(x, it)) < treeHeight }.count()
    val brs = if (bS < bottom.count()) 1 else 0
    val mul = (lS + ols) * (rS + ors) * (tS + trs) * (bS + brs)
    println("($x $y) $lS $rS $tS $bS = $mul")

    return mul
}

private fun Map.Entry<Vec2, Int>.isVisible(forest: Map<Vec2, Int>): Boolean {
    val (coord, treeHeight) = this
    val (x, y) = coord
    val left = (0 until x)
    if (!left.isEmpty() && left.all { forest.getValue(Vec2(it, y)) < treeHeight }) {
        println("left")
        return true
    }
    val right = (x + 1 until width)
    if (!right.isEmpty() && right.all { forest.getValue(Vec2(it, y)) < treeHeight }) {
        println("right")
        return true
    }
    val top = (0 until y)
    if (!top.isEmpty() && top.all { forest.getValue(Vec2(x, it)) < treeHeight }) {
        println("top")
        return true
    }
    val bottom = (y + 1 until gameHeight)
    if (!bottom.isEmpty() && bottom.all { forest.getValue(Vec2(x, it)) < treeHeight }) {
        println("bottom")
        return true
    }
    if (x == 0 || y == 0 || x == width - 1 || y == gameHeight - 1) return true
    return false
}

package day08c

import java.io.File

val input = File("inputs/day08.txt").readText().lines()
val testInput = File("inputs/day08t.txt").readText()

data class Vec2(val x: Int, val y: Int)

class GameField(private val field: List<List<Int>>) {
    operator fun get(x: Int, y: Int) = field[y][x]

    private val fieldWidth = field[0].size
    private val fieldHeight = field.size

    fun countVisible(): Int {
        val visibles = sequence {
            for (y in 0 until fieldHeight) {
                for (x in 0 until fieldWidth) {
                    yield(isVisible(x, y))
                }
            }
        }
        return visibles.count { it }
    }

    fun isVisible(x: Int, y: Int): Boolean {
        val treeHeight = get(x, y)
        val left = (0 until x)
        val right = (x + 1 until fieldWidth)
        val top = (0 until y)
        val bottom = (y + 1 until fieldHeight)
        return x == 0 ||
                y == 0 ||
                x == fieldWidth - 1 ||
                y == fieldHeight - 1 ||
                !left.isEmpty() && left.all { get(it, y) < treeHeight } ||
                !right.isEmpty() && right.all { get(it, y) < treeHeight } ||
                !top.isEmpty() && top.all { get(x, it) < treeHeight } ||
                !bottom.isEmpty() && bottom.all { get(x, it) < treeHeight }
    }

    fun maxScenicScore(): Int {
        val scores = sequence {
            for (y in 0 until fieldHeight) {
                for (x in 0 until fieldWidth) {
                    yield(scenicScore(x, y))
                }
            }
        }

        return scores.max()
    }

    fun scenicScore(x: Int, y: Int): Int {
        val treeHeight = get(x, y)
        if (x == 0 || y == 0 || x == fieldWidth - 1 || y == fieldHeight - 1) return 0

        val left = (x - 1 downTo 0)
        val leftTrees = left.takeWhile { get(it, y) < treeHeight }.count()
        val leftOffset = if (leftTrees < left.count()) 1 else 0

        val right = (x + 1 until fieldWidth)
        val rightTrees = right.takeWhile { get(it, y) < treeHeight }.count()
        val rightOffset = if (rightTrees < right.count()) 1 else 0

        val top = (y - 1 downTo 0)
        val topTrees = top.takeWhile { get(x, it) < treeHeight }.count()
        val topOffset = if (topTrees < top.count()) 1 else 0

        val bottom = (y + 1 until fieldHeight)
        val bottomTrees = bottom.takeWhile { get(x, it) < treeHeight }.count()
        val bottomOffset = if (bottomTrees < bottom.count()) 1 else 0

        return (leftTrees + leftOffset) * (rightTrees + rightOffset) * (topTrees + topOffset) * (bottomTrees + bottomOffset)
    }


    companion object {
        fun fromLines(lines: List<String>): GameField {
            val field = List(lines.size) { MutableList(lines[0].length) { 0 } }
            for ((y, line) in input.withIndex()) {
                for ((x, char) in line.withIndex()) {
                    field[y][x] = char.digitToInt()
                }
            }
            return GameField(field)
        }
    }
}

fun main() {
    val myGameField = GameField.fromLines(input)
    println(myGameField.countVisible())
    println(myGameField.maxScenicScore())
}

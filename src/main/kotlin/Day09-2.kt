package day092

import java.io.File
import day092.Direction.*
import kotlin.math.abs

val input = File("inputs/day09.txt").readText().lines()

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

data class Movement(val direction: Direction) {
    fun move(v: Vec2): Vec2 {
        val length = 1
        return when (direction) {
            UP -> Vec2(v.x, v.y + length)
            DOWN -> Vec2(v.x, v.y - length)
            LEFT -> Vec2(v.x - length, v.y)
            RIGHT -> Vec2(v.x + length, v.y)
        }
    }
}

fun String.toMovements(): List<Movement> {
    val (dir, len) = split(" ")
    val direction = when (dir) {
        "U" -> Direction.UP
        "D" -> Direction.DOWN
        "L" -> Direction.LEFT
        "R" -> Direction.RIGHT
        else -> error("what")
    }

    return List(len.toInt()) { Movement(direction) }
}


fun main() {
    val inst = input.flatMap { it.toMovements() }
    println(inst)
    val visited = hashSetOf<Vec2>()
    val snake = MutableList(10) { Vec2(0, 0) }
    println(snake)
    visited += Vec2(0, 0)
    for (i in inst) {
        val head = snake[0]
        val tail = snake[1]
        val newHead = i.move(head)
        snake[0] = newHead
        for (headIdx in 0..8) {
            val curTail = snake[headIdx+1]
            if(curTail.isAdjacent(snake[headIdx])) continue
            val offset = snake[headIdx] - snake[headIdx+1]
            val normalizedOffset = Vec2(
                offset.x.coerceIn(-1..1),
                offset.y.coerceIn(-1..1)
            )
//            println(normalizedOffset)
            val newTail = curTail + normalizedOffset
            println("moved ${headIdx+1} by $normalizedOffset")
            snake[headIdx + 1] = newTail
        }

        visited += snake[snake.lastIndex]
    }

    for (y in visited.minOf { it.y }..visited.maxOf { it.y }) {
        for (x in visited.minOf { it.x }..visited.maxOf { it.x }) {
            if (Vec2(x, y) in visited) print('#') else print('.')
        }
        println()
    }
    println(visited)
    println(visited.count())
}

data class Vec2(val x: Int, val y: Int) {
    fun isAdjacent(other: Vec2): Boolean {
        return abs(x - other.x) <= 1 && abs(y - other.y) <= 1
    }

    operator fun minus(other: Vec2): Vec2 {
        return Vec2(this.x - other.x, this.y - other.y)
    }

    fun manhattan(other: Vec2): Int {
        return abs(other.x - x) + abs(other.y - y)
    }

    operator fun plus(other: Vec2): Vec2 {
        return Vec2(x + other.x, y + other.y)
    }
}
package day092

import java.io.File
import day092.Direction.*
import kotlin.math.abs

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
        "U" -> UP
        "D" -> DOWN
        "L" -> LEFT
        "R" -> RIGHT
        else -> error("what")
    }

    return List(len.toInt()) { Movement(direction) }
}

val input = File("inputs/txt").readLines()
val instructions = input.flatMap { it.toMovements() }

fun main() {
    part1()
    part2()
}

fun part1() {
    val visited = hashSetOf<Vec2>()
    var head = Vec2(0, 0)
    var tail = Vec2(0, 0)
    visited += tail
    for (instruction in instructions) {
        head = instruction.move(head)
        if (head.isAdjacent(tail)) continue
        val offset = head - tail
        val normalizedOffset = Vec2(
            offset.x.coerceIn(-1..1),
            offset.y.coerceIn(-1..1)
        )
        tail += normalizedOffset
        visited += tail
    }
    debugPrint(visited)
    println(visited.count())
}

fun debugPrint(visited: Set<Vec2>) {
    for (y in visited.minOf { it.y }..visited.maxOf { it.y }) {
        for (x in visited.minOf { it.x }..visited.maxOf { it.x }) {
            if (Vec2(x, y) in visited) print('#') else print('.')
        }
        println()
    }
}

fun part2() {
    val visited = hashSetOf<Vec2>()
    val snake = MutableList(10) { Vec2(0, 0) }
    visited += Vec2(0, 0)
    for (instruction in instructions) {
        val head = snake[0]
        snake[0] = instruction.move(head)

        for (headIdx in 0 until 9) {
            val curTail = snake[headIdx + 1]
            val curHead = snake[headIdx]
            if (curTail.isAdjacent(curHead)) continue
            val offset = curHead - curTail
            val normalizedOffset = Vec2(
                offset.x.coerceIn(-1..1),
                offset.y.coerceIn(-1..1)
            )
            val newTail = curTail + normalizedOffset
            snake[headIdx + 1] = newTail
        }

        visited += snake[snake.lastIndex]
    }

    debugPrint(visited)
    println(visited.count())
}

data class Vec2(val x: Int, val y: Int) {
    fun isAdjacent(other: Vec2): Boolean {
        return abs(x - other.x) <= 1 && abs(y - other.y) <= 1
    }

    operator fun minus(other: Vec2): Vec2 {
        return Vec2(this.x - other.x, this.y - other.y)
    }

    operator fun plus(other: Vec2): Vec2 {
        return Vec2(x + other.x, y + other.y)
    }
}
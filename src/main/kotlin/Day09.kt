package day09
import java.io.File
import day09.Direction.*
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
        return when(direction) {
            UP -> Vec2(v.x, v.y + length)
            DOWN -> Vec2(v.x, v.y - length)
            LEFT -> Vec2(v.x - length, v.y)
            RIGHT -> Vec2(v.x + length, v.y)
        }
    }
}

fun String.toMovements(): List<Movement> {
    val (dir, len) = split(" ")
    val direction = when(dir) {
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
    var head = Vec2(0, 0)
    var tail = Vec2(0, 0)
    visited += tail
    for(i in inst) {
        head = i.move(head)
        if(head.isAdjacent(tail)) continue
        val offset = head - tail
        val normalizedOffset = Vec2(
            offset.x.coerceIn(-1..1),
            offset.y.coerceIn(-1..1)
        )
        println(normalizedOffset)
        tail += normalizedOffset
        visited += tail
        println(head.manhattan(tail))
    }

    println(visited)
    for(y in visited.minOf { it.y }..visited.maxOf { it.y }) {
        for(x in visited.minOf { it.x }..visited.maxOf { it.x }) {
            if(Vec2(x, y) in visited) print('#') else print('.')
        }
        println()
    }
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
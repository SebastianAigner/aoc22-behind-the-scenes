package day12

import java.io.File
import kotlin.math.exp

data class Vec2(val x: Int, val y: Int) {
    fun left() = Vec2(x - 1, y)
    fun right() = Vec2(x + 1, y)
    fun up() = Vec2(x, y - 1)
    fun down() = Vec2(x, y + 1)
}


val input = File("inputs/day12.txt").readText().lines()
val map = mutableMapOf<Vec2, Int>()
fun main() {
    var start: Vec2? = null
    var end: Vec2? = null
    for ((y, line) in input.withIndex()) {
        for ((x, col) in line.withIndex()) {
            if (col == 'S') {
                start = Vec2(x, y)
                map[start] = 0
            } else if (col == 'E') {
                end = Vec2(x, y)
                map[end] = 'z' - 'a'
            } else map[Vec2(x, y)] = col - 'a'
        }
    }
    println(start)
    println(end)
    println(map)
    fun findShortestPath(): Int {
        val q = ArrayDeque<Vec2>()
        val explored = hashSetOf<Vec2>()
        explored.add(start!!)
        q.addLast(start!!)
        val parent = mutableMapOf<Vec2, Vec2>()

        fun len(v: Vec2): Int {
            var curr = v
            var len = 0
            while (curr != start) {
                curr = parent[curr]!!
                len++
            }
            return len
        }

        while (q.isNotEmpty()) {
            println(q)
            val curr = q.removeFirst()
            if (curr == end) {
                return len(curr)
            }
            for (connection in listOf(
                curr.left(),
                curr.right(),
                curr.up(),
                curr.down()
            ).filter { it in map && map[it]!! - map[curr]!! in Int.MIN_VALUE..1 }) {
                println("exploring $connection")
                if (connection !in explored) {
                    explored += connection
                    parent[connection] = curr
                    q.addLast(connection)
                }
            }
        }
        error("oh no")
    }
    println(findShortestPath())

}
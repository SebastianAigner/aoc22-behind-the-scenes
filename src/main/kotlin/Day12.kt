package day12

import java.io.File

data class Vec2(val x: Int, val y: Int) {
    fun left() = Vec2(x - 1, y)
    fun right() = Vec2(x + 1, y)
    fun up() = Vec2(x, y - 1)
    fun down() = Vec2(x, y + 1)

    val adjacents get() = listOf(left(), right(), up(), down())
}

data class TopographicMap(val start: Vec2, val end: Vec2, val heights: Map<Vec2, Int>)

fun TopographicMap(input: List<String>): TopographicMap {
    var start: Vec2? = null
    var end: Vec2? = null
    val map = mutableMapOf<Vec2, Int>()
    for ((y, line) in input.withIndex()) {
        for ((x, col) in line.withIndex()) {
            when (col) {
                'S' -> {
                    start = Vec2(x, y)
                    map[start] = 0
                }
                'E' -> {
                    end = Vec2(x, y)
                    map[end] = 'z' - 'a'
                }
                else -> map[Vec2(x, y)] = col - 'a'
            }
        }
    }

    if(start == null) error("Start not set!")
    if(end == null) error("End not set!")
    return TopographicMap(start, end, map)
}

fun findShortestPath(startPositon: Vec2, endPosition: Vec2, heights: Map<Vec2, Int>): Int {
    val queue = ArrayDeque<Vec2>()
    val explored = hashSetOf<Vec2>()
    explored.add(startPositon)
    queue.addLast(startPositon)

    val parent = mutableMapOf<Vec2, Vec2>()

    fun pathLengthTo(v: Vec2): Int {
        var curr = v
        var len = 0
        while (curr != startPositon) {
            curr = parent.getValue(curr)
            len++
        }
        return len
    }

    tailrec fun pathLengthToRec(v: Vec2, acc: Int): Int {
        if(v == startPositon) return acc
        return pathLengthToRec(parent.getValue(v), acc + 1)
    }
    fun pathLengthToRec(v: Vec2): Int = pathLengthToRec(v, 0)

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        if (current == endPosition) {
            check(pathLengthTo(current) == pathLengthToRec(current))
            return pathLengthTo(current)
        }
        for (connection in current.adjacents.filter { it in heights && heights.getValue(it) - heights.getValue(current) in Int.MIN_VALUE..1 }) {
            if (connection !in explored) {
                explored += connection
                parent[connection] = current
                queue.addLast(connection)
            }
        }
    }
    return Int.MAX_VALUE
}

fun main() {
    val input = File("inputs/day12.txt").readText().lines()
    val topoMap = TopographicMap(input)
    println(findShortestPath(topoMap.start, topoMap.end, topoMap.heights))
    println(topoMap.heights.filterValues { it == 0 }.minOf { findShortestPath(it.key, topoMap.end, topoMap.heights) })
}
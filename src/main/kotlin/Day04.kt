package day04

import java.io.File

val input = File("inputs/day04.txt").readLines()

fun main() {
    val ranges = input.map {
        val (a, b) = it.split(",")
        a.toRange() to b.toRange()
    }
    val part1 = ranges.count { (a, b) ->
        a in b || b in a
    }
    println(part1)

    val part2 = ranges.count { (a, b) -> a.any { it in b } }
    println(part2)
}

fun String.toRange(): IntRange {
    val (start, end) = split('-')
    return start.toInt()..end.toInt()
}

operator fun IntRange.contains(other: IntRange): Boolean {
    return other.first >= this.first && other.last <= this.last
}
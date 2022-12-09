package day03

import java.io.File

val input = File("inputs/day03.txt").readLines()

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Int {
    val sharedItems = input.map {
        val first = it.substring(0 until it.length / 2)
        val second = it.substring(it.length / 2)
        (first intersect second).single()
    }
    return sharedItems.sumOf { it.priority }
}

val Char.priority: Int
    get(): Int {
        return when (this) {
            in 'a'..'z' -> this - 'a' + 1
            in 'A'..'Z' -> this - 'A' + 27
            else -> error("Check your input! $this")
        }
    }

fun part2(): Int {
    val keycards = input.chunked(3) {
        val (a, b, c) = it
        val keycard = a intersect b intersect c
        keycard.single()
    }
    return keycards.sumOf { it.priority }
}

// or, for fewer manual calls to `.toSet`:
inline infix fun String.intersect(other: String) = toSet() intersect other.toSet()
inline infix fun Set<Char>.intersect(other: String) = this intersect other.toSet()
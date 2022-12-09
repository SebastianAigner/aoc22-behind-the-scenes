package day06s

import java.io.File

val input = File("inputs/day06.txt").readText()
val testInput = File("inputs/day06t.txt").readText()

fun main() {
    println(input.windowed(4).indexOfFirst { it.allUnique() } + 4)
    println(input.windowed(14).indexOfFirst { it.allUnique() } + 14)
}

fun String.allUnique(): Boolean{
    val s = mutableSetOf<Char>()
    return all { s.add(it) }
}



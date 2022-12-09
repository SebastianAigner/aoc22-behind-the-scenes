package day06r
import java.io.File

val input = File("inputs/day06.txt").readText()
val testInput = File("inputs/day06t.txt").readText()

// Solution sent in by Alexander af Trolle
// Further optimizations: IntArray(?)

class Day6 {
    private fun String.startMessageIndex(uniqueLength: Int): Int {
        val duplicateIndexMap = mutableMapOf<Char, Int>()
        var mostRecentDuplicateIndex = 0
        var index = 0
        return indexOfFirst { char ->
            val lastSeenIndex = duplicateIndexMap.put(char, index) ?: 0
            mostRecentDuplicateIndex = mostRecentDuplicateIndex.coerceAtLeast(lastSeenIndex)
            index++ - mostRecentDuplicateIndex >= uniqueLength
        } + 1
    }

    fun part1(input: String): Int = input.startMessageIndex(4)
    fun part2(input: String): Int = input.startMessageIndex(14)
}

fun main() {
    val d = Day6()
    println(d.part1(input))
    println(d.part2(input))
}
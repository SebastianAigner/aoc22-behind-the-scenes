import java.io.File

fun main() {
    fun CharSequence.allUnique() = toSet().count() == length

    fun solve(input: String, windowSize: Int) =
        input.windowedSequence(windowSize).indexOfFirst { it.allUnique() } + windowSize

    fun solveNaively(input: String, windowSize: Int): Int {
        val windowed = input.windowed(windowSize)
        for ((index, window) in windowed.withIndex()) {
            if (window.toSet().count() == windowSize) {
                return index + windowSize
            }
        }
        return -1
    }

    val input = File("inputs/day06.txt").readLines()

    println(solve(input[0], 4)) //1531
    println(solve(input[0], 14)) //2518
}
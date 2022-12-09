package day05

import java.io.File

val input = File("inputs/day05.txt").readText().split("\n\n")

fun main() {
    println(input)
    val (initialStackDescription, instructions) = input
    val initialStacks = initialStackDescription.lines().dropLast(1).reversed().map { it.toCrates() }
    val workingStacks = List(9) { ArrayDeque<Char>() }

    for (row in initialStacks) {
        for ((idx, char) in row.withIndex()) {
            if (char == null) continue
            workingStacks[idx].addLast(char)
        }
    }

    val part1WorkingStacks = workingStacks.map { ArrayDeque(it) }
    val part2WorkingStacks = workingStacks.map { ArrayDeque(it) }

    instructions.lines().forEach { it.toInstruction().executeAsPart1(part1WorkingStacks) }
    println(part1WorkingStacks.joinToString("") { it.last().toString() })
    instructions.lines().forEach { it.toInstruction().executeAsPart2(part2WorkingStacks) }
    println(part2WorkingStacks.joinToString("") { "${it.last()}" })
}

data class Instruction(val count: Int, val from: Int, val to: Int) {
    fun executeAsPart1(stacks: List<ArrayDeque<Char>>) {
        val fromStack = stacks[from - 1]
        val toStack = stacks[to - 1]

        repeat(count) {
            val inCraneClaw = fromStack.removeLast()
            toStack.addLast(inCraneClaw)
        }
    }

    fun executeAsPart2(stacks: List<ArrayDeque<Char>>) {
        val fromStack = stacks[from - 1]
        val toStack = stacks[to - 1]

        val inCraneClaw = fromStack.takeLast(count)
        repeat(count) { fromStack.removeLast() }
        for (crate in inCraneClaw) {
            toStack.addLast(crate)
        }
    }
}

// move 3 from 6 to 2
fun String.toInstruction(): Instruction {
    val words = split(" ")
    val count = words[1].toInt()
    val from = words[3].toInt()
    val to = words[5].toInt()
    return Instruction(count, from, to)
}

fun String.toCrates(): List<Char?> {
    return ("$this ").chunked(4) {
        // parse strings of shape '[X] '
        if (it[1].isLetter()) it[1] else null
    }
}
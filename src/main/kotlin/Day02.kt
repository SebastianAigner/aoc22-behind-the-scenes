package day02

import java.io.File
import day02.Gesture.*
import day02.Outcome.*

enum class Gesture(val points: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);
}

fun Gesture.beats(): Gesture {
    return when (this) {
        ROCK -> SCISSORS
        PAPER -> ROCK
        SCISSORS -> PAPER
    }
}

fun Char.toGesture(): Gesture {
    return when (this) {
        'A', 'X' -> ROCK
        'B', 'Y' -> PAPER
        'C', 'Z' -> SCISSORS
        else -> error("Unknown input $this")
    }
}

val input = File("inputs/day02.txt")
    .readLines()
    .map {
        val (a, b) = it.split(" ")
        a.first() to b.first()
    }

fun main() {
    val part1 = input.sumOf { (opponent, you) ->
        calculateScore(opponent.toGesture(), you.toGesture())
    }
    println(part1) // 10816

    val part2 = input.sumOf { (opponent, you) ->
        val myHand = handForDesiredOutcome(opponent.toGesture(), you.toOutcome())
        calculateScore(opponent.toGesture(), myHand)
    }
    println(part2) // 11657
}

enum class Outcome {
    LOSS,
    DRAW,
    WIN
}

// Calculate the outcome from the perspective of `first`.
fun calculateOutcome(first: Gesture, second: Gesture): Outcome = when {
    first == second -> DRAW
    first.beats() == second -> WIN
    else -> LOSS
}

fun calculateScore(opponent: Gesture, you: Gesture): Int {
    val outcome = calculateOutcome(you, opponent)
    return you.points + when (outcome) {
        LOSS -> 0
        DRAW -> 3
        WIN -> 6
    }
}

//region PART 2

fun Char.toOutcome(): Outcome {
    return when (this) {
        'X' -> LOSS
        'Y' -> DRAW
        'Z' -> WIN
        else -> error("Unknown input $this")
    }
}

fun handForDesiredOutcome(opponentGesture: Gesture, desiredOutcome: Outcome): Gesture {
    return when (desiredOutcome) {
        DRAW -> opponentGesture
        LOSS -> opponentGesture.beats()
        WIN -> opponentGesture.beatenBy()
    }
}

fun Gesture.beatenBy(): Gesture {
    return when (this) {
        SCISSORS -> ROCK
        ROCK -> PAPER
        PAPER -> SCISSORS
    }
}

//endregion
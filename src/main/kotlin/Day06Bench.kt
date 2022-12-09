package day06

import day06r.Day6
import org.openjdk.jmh.annotations.*
import java.io.File
import java.util.concurrent.*

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 1)
@Measurement(iterations = 4, time = 1, timeUnit = TimeUnit.SECONDS)
class Day06Benchmark {
    lateinit var input: String
    lateinit var aat: Day6

    @Setup
    fun setUp() {
        input = File("inputs/day06.txt").readText()
        aat = Day6()
    }

    @Benchmark
    fun part1Naive(): Int {
        return input.windowed(4).indexOfFirst { it.toSet().size == 4 } + 4
    }

    @Benchmark
    fun part2Naive(): Int {
        return input.windowed(14).indexOfFirst { it.toSet().size == 14 } + 14
    }

    @Benchmark
    fun part1Sequence(): Int {
        return input.windowedSequence(4).indexOfFirst { it.toSet().size == 4 } + 4
    }

    @Benchmark
    fun part2Sequence(): Int {
        return input.windowedSequence(14).indexOfFirst { it.toSet().size == 14 } + 14
    }

    @Benchmark
    fun part1SequenceEphemeralSet(): Int {
        return input.windowedSequence(4).indexOfFirst { it.allUnique() } + 4
    }

    @Benchmark
    fun part2SequenceEphemeralSet(): Int {
        return input.windowedSequence(14).indexOfFirst { it.allUnique() } + 14
    }

    @Benchmark
    fun part1SequenceGlobalSet(): Int {
        return input.windowedSequence(4).indexOfFirst { it.allUniqueGlobalSet() } + 4
    }

    @Benchmark
    fun part2SequenceGlobalSet(): Int {
        return input.windowedSequence(14).indexOfFirst { it.allUniqueGlobalSet() } + 14
    }

    @Benchmark
    fun part1SequenceGlobalHashSet(): Int {
        return input.windowedSequence(4).indexOfFirst { it.allUniqueGlobalHashSet() } + 4
    }

    @Benchmark
    fun part2SequenceGlobalHashSet(): Int {
        return input.windowedSequence(14).indexOfFirst { it.allUniqueGlobalHashSet() } + 14
    }

    @Benchmark
    fun part1SequenceEphemeralHashSet(): Int {
        return input.windowedSequence(4).indexOfFirst { it.allUniqueHashSet() } + 4
    }

    @Benchmark
    fun part2SequenceEphemeralHashSet(): Int {
        return input.windowedSequence(14).indexOfFirst { it.allUniqueHashSet() } + 14
    }

    @Benchmark
    fun part1Viewer(): Int {
        return aat.part1(input)
    }

    @Benchmark
    fun part2Viewer(): Int {
        return aat.part2(input)
    }
}

fun String.allUnique(): Boolean {
    val s = mutableSetOf<Char>() // Default capacity of 16 is enough!
    return all { s.add(it) }
}

fun String.allUniqueHashSet(): Boolean {
    val s = hashSetOf<Char>() // Default capacity of 16 is enough!
    return all { s.add(it) }
}

val globalSet = mutableSetOf<Char>()
fun String.allUniqueGlobalSet(): Boolean {
    globalSet.clear()
    return all { globalSet.add(it) }
}

val globalHashSet = hashSetOf<Char>()
fun String.allUniqueGlobalHashSet(): Boolean {
    globalHashSet.clear()
    return all { globalHashSet.add(it) }
}
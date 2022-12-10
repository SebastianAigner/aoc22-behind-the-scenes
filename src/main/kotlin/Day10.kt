package day10

import java.io.File

val input = File("inputs/day10.txt").readLines()

sealed class Instruction()
class Noop : Instruction()
class Addx(val num: Int) : Instruction()

fun parse(l: String): Instruction {
    if (l.startsWith("noop")) {
        return Noop()
    } else {
        val (_, foo) = l.split(" ")
        return Addx(foo.toInt())
    }
}

val signals = mutableListOf<Int>()

object Part1 {
    var x = 1
    var cycle = 0

    fun signal() {
        cycle++
        if (cycle == 20 || (cycle + 20) % 40 == 0) {
            signals += cycle * x
        }
    }

    fun execute(insts: List<Instruction>) {
        for (i in insts) {
            when (i) {
                is Addx -> {
                    signal()
                    signal()
                    x += i.num
                }

                is Noop -> {
                    signal()
                }
            }
        }
        println(signals.sum())
    }
}

object Part2 {
    var x = 1
    var crt = 0

    fun crt() {
        val sprite = (x - 1..x + 1)
        if (crt > 0 && crt % 40 == 0) {
            println()
        }
        if (crt % 40 in sprite) {
            print('#')
        } else print('.')

        crt++
    }

    fun execute(insts: List<Instruction>) {
        for (i in insts) {
            when (i) {
                is Addx -> {
                    // begin executing addx
                    crt() // crt draws
                    crt() // crt draws
                    x += i.num // finish executing, CRT now something else
                }

                is Noop -> {
                    // begin executing noop
                    crt() // crt draws
                }
            }
        }
    }
}

fun main() {
    val insts = input.map { parse(it) }
    Part1.execute(insts)
    Part2.execute(insts)
}
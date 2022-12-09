package day01

import java.io.File
// hello :)
val textGroups = File("inputs/day01.txt").readText().split("\n\n")
val numberGroups = textGroups.map { it.lines().sumOf { it.toInt() } }
// DONT MIND THIS PART HAHA
// we tried it here yesterday :)

// Who needs a chat app when you have this
// :D
// Great, looks like we're all set!
fun main() {
    // Testing testing
    "hhaha".substringBefore("ha").let(::println)
    // Neat.


    val descendingNumberGroups = numberGroups.sortedDescending()
    // hmmmm
    // You are not permitted to execute action 'Function extraction'
    // try now, but might just currently not work in Code With Me
    // OK!
    // okay, seams to not work
    // Can IDE extract numberGroups.sortedDescending.take(k) to method?
    // i guess kinda?
    // I don't think it does that, hmm
    // I was expecting it to find take(3) is same and suggest an argument
    // Never mind
    // OK, we can try the rest sometime soon / LIVE!
    // Wonderful!
    // OK, meet you on sound check!
    println(descendingGroups(descendingNumberGroups, 1))
    println(descendingGroups(descendingNumberGroups, 3).sum())
}

private fun descendingGroups(descendingNumberGroups: List<Int>, k: Int) = descendingNumberGroups.take(k)

fun `can i write something`(): Boolean {
    // seems yes!
    // try the refactor... nice!
    return true
}
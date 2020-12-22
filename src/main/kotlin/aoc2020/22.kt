package main.kotlin.aoc2020

import java.io.File

fun main() {
    val input = mutableListOf<String>()
    File("input2020/input22.txt").forEachLine { input.add(it.trim()) }
    val decks = decks(input)
    val player1 = decks.first
    val player2 = decks.second
    println("part1: ${score(playGame(player1, player2).second)}")
    println("part2: ${score(playGame(player1, player2, true).second)}")
}

private fun score(final: List<Int>): Int {
    return final.mapIndexed { index, it -> (final.size - index) * it }.sum()
}

private fun playGame(deckP1: List<Int>, deckP2: List<Int>, part2: Boolean = false): Pair<Boolean, MutableList<Int>> {
    val player1 = deckP1.toMutableList()
    val player2 = deckP2.toMutableList()
    val previousRounds = mutableSetOf<Pair<List<Int>, List<Int>>>()
    while (player1.isNotEmpty() && player2.isNotEmpty()) {
        if (part2 && previousRounds.contains(Pair(player1, player2))) {
            return Pair(true, player1)
        }
        previousRounds.add(Pair(player1.toList(), player2.toList()))
        val p1 = player1.removeAt(0)
        val p2 = player2.removeAt(0)
        var p1Winner = p1 > p2
        if (part2 && p1 <= player1.size && p2 <= player2.size) {
            p1Winner = playGame(player1.take(p1), player2.take(p2), true).first
        }
        if (p1Winner) {
            player1.add(p1)
            player1.add(p2)
        } else {
            player2.add(p2)
            player2.add(p1)
        }
    }
    return if (player1.isNotEmpty()) {
        Pair(true, player1)
    } else {
        Pair(false, player2)
    }
}

private fun decks(input: List<String>): Pair<List<Int>, List<Int>> {
    val player1 = mutableListOf<Int>()
    val player2 = mutableListOf<Int>()
    var secondDeck = false
    for (line in input) {
        if (line.startsWith("Player 2")) {
            secondDeck = true
        } else if (secondDeck) {
            player2.add(line.toInt())
        } else if (line.isNotEmpty() && !line.startsWith("P")) {
            player1.add(line.toInt())
        }
    }
    return Pair(player1, player2)
}
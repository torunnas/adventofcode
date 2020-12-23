package main.kotlin.aoc2020

import java.lang.StringBuilder

fun main() {
    val input = "685974213"
    var start = System.currentTimeMillis()
    println("part1: ${solve(input, 9, 100)} ${System.currentTimeMillis() - start}ms")
    start = System.currentTimeMillis()
    println("part2: ${solve(input, 1000000, 10000000)} ${System.currentTimeMillis() - start}ms")
}

private fun solve(startCups: String, numberOfCups: Int, rounds: Int): String {
    val circle = getCircle(startCups, numberOfCups)
    val labelToNext = mutableMapOf<Int, Int>()
    for (i in circle.indices) {
        val next = (i + 1) % circle.size
        labelToNext[circle[i]] = circle[next]
    }

    var current = circle[0]
    for (i in 1..rounds) {
        val first = labelToNext[current]!!
        val second = labelToNext[first]!!
        val third = labelToNext[second]!!
        labelToNext[current] = labelToNext[third]!!
        val picked = setOf(first, second, third)
        val dest = getDest(current, picked, numberOfCups)
        labelToNext[third] = labelToNext[dest]!!
        labelToNext[dest] = first
        current = labelToNext[current]!!
    }
    return getResult(labelToNext, numberOfCups)
}

private fun getCircle(startCups: String, numberOfCups: Int): List<Int> {
    val circle = startCups.toCharArray().map { Character.getNumericValue(it) }.toMutableList()
    for (i in circle.size + 1..numberOfCups) {
        circle.add(i)
    }
    return circle
}

private fun getDest(currentCup: Int, picked: Set<Int>, numberOfCups: Int): Int {
    var dest = currentCup - 1
    while (dest < 1 || picked.contains(dest)) {
        if (dest < 1) {
            dest = numberOfCups
        } else {
            dest--
        }
    }
    return dest
}

private fun getResult(labelToLink: MutableMap<Int, Int>, numberOfCups: Int): String {
    val start = 1
    if (numberOfCups == 9) {
        var next = labelToLink[start]!!
        val res = StringBuilder()
        while (next != 1) {
            res.append(next)
            next = labelToLink[next]!!
        }
        return res.toString()
    }
    val a = labelToLink[start]!!
    val b = labelToLink[a]!!
    return (a.toLong() * b.toLong()).toString()
}
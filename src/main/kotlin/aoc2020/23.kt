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
    val links = mutableSetOf<Link>()
    val labelToLink = mutableMapOf<Int, Link>()
    for (i in circle.indices) {
        val next = (i + 1) % circle.size
        val link = Link(circle[i], circle[next])
        links.add(link)
        labelToLink[link.label] = link
    }

    var current = labelToLink[circle[0]]!!
    for (i in 1..rounds) {
        val first = labelToLink[current.next]!!
        val second = labelToLink[first.next]!!
        val third = labelToLink[second.next]!!
        current.next = third.next
        val picked = setOf(first, second, third).map { it.label }
        val dest = getDest(current.label, picked, numberOfCups)
        val destLink = labelToLink[dest]!!
        third.next = destLink.next
        destLink.next = first.label
        current = labelToLink[current.next]!!
    }
    return getResult(labelToLink, numberOfCups)
}

private fun getCircle(startCups: String, numberOfCups: Int): List<Int> {
    val circle = startCups.toCharArray().map { Character.getNumericValue(it) }.toMutableList()
    for (i in circle.size + 1..numberOfCups) {
        circle.add(i)
    }
    return circle
}

private fun getDest(currentCup: Int, picked: List<Int>, numberOfCups: Int): Int {
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

private fun getResult(labelToLink: MutableMap<Int, Link>, numberOfCups: Int): String {
    val start = labelToLink[1]!!
    if (numberOfCups == 9) {
        var next = labelToLink[start.next]!!
        val res = StringBuilder()
        while (next.label != 1) {
            res.append(next.label)
            next = labelToLink[next.next]!!
        }
        return res.toString()
    }
    val a = labelToLink[start.next]!!
    val b = labelToLink[a.next]!!
    return (a.label.toLong() * b.label.toLong()).toString()
}

data class Link(val label: Int, var next: Int)
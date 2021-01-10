package main.kotlin.aoc2020

import java.io.File

fun main() {
    val lines = File("input2020/input13.txt").readLines()
    var earliest = lines[0].toLong()
    var buses = lines[1].split(",").mapIndexed { index, s -> if (s != "x") s.toLong() to index.toLong() else null }
        .filterNotNull().toMap()
    println("part1: ${part1(earliest, buses.keys)}")
    println("part2: ${part2(buses)}")
    println("part2 - chinese: ${chineseRemainder(buses)}")
}

private fun part1(earliest: Long, buses: Set<Long>): Long? {
    return buses.map { Pair(it, Math.floorMod(-earliest, it)) }.minBy { it.second }?.let { it.first * it.second }
}

private fun part2(buses: Map<Long, Long>): Long {
    var time = buses.keys.max()!!
    var inc = 1L
    while (true) {
        val departures = buses.map { Pair(it.key, (time + it.value) % it.key) }.filter { it.second == 0L }
        if (departures.size == buses.size) {
            return time
        }
        if (departures.isNotEmpty()) {
            inc = departures.map { it.first }.reduce { acc, i -> findLCM(acc, i) }
        }
        time += inc
    }
}

private fun findLCM(a: Long, b: Long) = (a * b) / findGCD(a, b)

private fun findGCD(a: Long, b: Long): Long {
    if (a == 0L) return b
    return findGCD(b % a, a)
}

private fun chineseRemainder(buses: Map<Long, Long>): Long {
    val total = buses.keys.reduce { acc, i -> acc * i }
    return buses.map { ((modInverse(total / it.key, it.key) * (total / it.key) * -it.value) % total) + total }
        .sum() % total
}

private fun modInverse(a: Long, m: Long): Long {
    var a = a
    a %= m
    for (x in 1 until m) if (a * x % m == 1L) return x
    return 1
}
package main.kotlin.aoc2020

import java.io.File

fun main() {
    var list = mutableListOf<String>()
    File("input2020/input6.txt").forEachLine { list.add(it) }
    println("part1: ${part1(list)}")
    println("part2: ${part2(list)}")
}

private fun part1(answers: List<String>): Long {
    var sum = 0L
    var set = mutableSetOf<Char>()
    for (s in answers) {
        if (s.isBlank()) {
            sum += set.size
            set = mutableSetOf()
        } else {
            s.forEach { set.add(it) }
        }
    }
    sum += set.size
    return sum
}

private fun part2(answers: List<String>): Long {
    var sum = 0L
    var set = setOf<Char>()
    var first = true
    for (s in answers) {
        if (s.isBlank()) {
            sum += set.size
            set = mutableSetOf()
            first = true
        } else if (first) {
            set = s.toSet()
            first = false
        } else {
            set = set.intersect(s.toSet())
        }
    }

    sum += set.size
    return sum
}
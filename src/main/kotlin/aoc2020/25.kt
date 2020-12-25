package main.kotlin.aoc2020

import java.io.File

fun main() {
    val input = File("input2020/input25.txt").readLines().map { it.trim().toLong() }
    val cardPk = input[0]
    val doorPk = input[1]
    var subjectNumber = 1L
    var transformer = 7L
    var i = 0
    while (subjectNumber != cardPk && subjectNumber != doorPk) {
        subjectNumber = transform(subjectNumber, transformer)
        i++
    }
    var answer = 1L
    transformer = if (subjectNumber == cardPk) doorPk else cardPk
    for (j in 1..i) {
        answer = transform(answer, transformer)
    }
    println(answer)
}

private fun transform(subjectNumber: Long, transformer: Long) = (subjectNumber * transformer) % 20201227
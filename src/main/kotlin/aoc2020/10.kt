package main.kotlin.aoc2020

import java.io.File
import java.math.BigInteger

fun main() {
    var list = mutableListOf<Long>()
    File("input2020/input10.txt").forEachLine { list.add(it.toLong()) }
    list.sort()
    var last = 0L
    var one = 0L
    var three = 1L
    var count = 0
    var part2 = BigInteger.ONE
    for (l in list) {
        if (l - last == 1L) {
            one++
            count++
        } else if (l - last == 3L) {
            three++
            part2 = part2.multiply(getPaths(count))
            count= 0
        }
        last = l
    }
    part2 = part2.multiply(getPaths(count))
    println("part1: ${one * three}")
    println("part2: $part2")


}

private fun getPaths(count: Int) = when(count){
    4 -> BigInteger.valueOf(7)
    3 -> BigInteger.valueOf(4)
    2 -> BigInteger.valueOf(2)
    else -> BigInteger.ONE
}



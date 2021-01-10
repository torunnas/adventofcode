package main.kotlin.aoc2020

import java.io.File

fun main() {
    var list = mutableListOf<Long>()
    File("input2020/input9.txt").forEachLine { list.add(it.toLong()) }
    var start = System.currentTimeMillis()
    val part1 = part1(list)
    println("part1: $part1 " + (System.currentTimeMillis() - start) + "ms")
    start = System.currentTimeMillis()
    println("part2: ${part2(list, part1)} " + (System.currentTimeMillis() - start) + "ms")
}

private fun part1(list: MutableList<Long>): Long {
    var a = list.take(25).toMutableList()
    for (i in 25 until list.size) {
        var found = false
        start@ for (j in a.indices) {
            for (k in j + 1 until a.size) {
                if (a[j] + a[k] == list[i]) {
                    found = true
                    break@start
                }
            }
        }
        if (!found) {
            return list[i]
        }
        a = a.takeLast(a.size - 1).toMutableList()
        a.add(list[i])
    }
    return 0
}

private fun part2(list: MutableList<Long>, answer: Long): Long {
    for (i in list.indices) {
        var sum = 0L
        var j = i
        while (sum < answer) {
            sum += list[j]
            j++
        }
        if (sum == answer) {
            val max = list.subList(i, j).max()
            val min = list.subList(i, j).min()
            return max!! + min!!
        }
    }
    return 0
}

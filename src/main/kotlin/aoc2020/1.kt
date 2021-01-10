package `2020`

import java.io.File

fun main() {
    var list = mutableListOf<Int>()
    File("input2020/input1.txt").forEachLine { list.add(it.toInt()) }
    println("part1: ${part1(list)}")
    println("part2: ${part2(list)}")
}

private fun part1(list: List<Int>): Int {
    for (i in list) {
        for (j in list) {
            if (i + j == 2020) {
                return i * j
            }
        }
    }
    return 0
}

private fun part2(list: List<Int>): Int {
    for (i in list) {
        for (j in list) {
            for (k in list) {
                if (i + j + k == 2020) {
                    return i * j * k
                }
            }
        }
    }
    return 0
}

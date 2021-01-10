package main.kotlin.aoc2020

fun main() {
    val list = mutableListOf(0, 12, 6, 13, 20, 1, 17, 0)
    var start = System.currentTimeMillis()
    println("part1: ${playGame(list, 2020)} ${System.currentTimeMillis()-start}ms")
    start = System.currentTimeMillis()
    println("part2: ${playGame(list, 30000000)} ${System.currentTimeMillis()-start}ms")
    original()
}

private fun playGame(list: MutableList<Int>, number: Int): Int {
    val map = list.dropLast(1).mapIndexed { index, i -> i to index + 1 }.toMap().toMutableMap()
    var last = list.last()
    for (i in list.size + 1..number) {
        var next = 0
        if (map[last] != null) {
            next = i - 1 - map[last]!!
        }
        map[last] = i - 1
        list.add(next)
        last = next
    }
    return list.last()
}

private fun original() {
    val list = mutableListOf(0, 12, 6, 13, 20, 1, 17, 0)
    var map = mutableMapOf<Int, Int>()
    map[0] = 1
    map[12] = 2
    map[6] = 3
    map[13] = 4
    map[20] = 5
    map[1] = 6
    map[17] = 7
    var last = 0
    for (i in list.size + 1..30000000) {
        var next = 0
        if (map[last] != null) {
            next = i - 1 - map[last]!!
        }
        map[last] = i - 1
        list.add(next)
        last = next
    }
    println(list.last())
}
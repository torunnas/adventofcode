package main.kotlin.aoc2020

import java.io.File

fun main() {
    var list = mutableListOf<String>()
    File("input2020/input11.txt").forEachLine { list.add(it) }
    println("part1: ${part(list, true)}")
    println("part2: ${part(list, false)}")
}

private fun part(list: List<String>, part1: Boolean): Int {
    var old = list
    var new = mutableListOf<String>()
    var changed = true
    var threshold = if (part1) 4 else 5
    while (changed) {
        changed = false
        for (i in old.indices) {
            var s = mutableListOf<Char>()
            for (j in old[i].indices) {
                var entry = old[i][j]
                if (old[i][j] == '#' && occupied(i, j, old, part1) >= threshold) {
                    entry = 'L'
                    changed = true
                } else if (old[i][j] == 'L' && occupied(i, j, old, part1) == 0) {
                    entry = '#'
                    changed = true
                }
                s.add(entry)
            }
            new.add(String(s.toCharArray()))
        }
        old = new
        new = mutableListOf()
    }
    return old.sumBy { it.count { it == '#' } }
}

private fun occupied(i: Int, j: Int, list: List<String>, onlyNeighbors: Boolean): Int {
    val dirs =
        listOf(Pair(0, 1), Pair(0, -1), Pair(1, 1), Pair(1, 0), Pair(-1, 0), Pair(-1, -1), Pair(-1, 1), Pair(1, -1))
    var sum = 0
    for (k in dirs) {
        var x = i + k.first
        var y = j + k.second
        while (!onlyNeighbors && x in list.indices && y in list[0].indices && list[x][y] == '.') {
            x += k.first
            y += k.second
        }
        if (x in list.indices && y in list[0].indices && list[x][y] == '#') {
            sum++
        }
    }
    return sum
}
package `2020`

import java.io.File

fun main() {
    var list = mutableListOf<String>()
    File("input2020/input3.txt").forEachLine { list.add(it) }
    println("part1: ${trees(list, 3, 1)}")
    var part2 = trees(list, 1, 1) * trees(list, 3, 1) * trees(list, 5, 1) * trees(list, 7, 1) * trees(list, 1, 2)
    println("part2: $part2")
}

private fun trees(list: List<String>, right: Int, down: Int): Long {
    var j = 0
    var count: Long = 0
    var length = list[0].length
    for (i in list.indices step down) {
        if (list[i][j] == '#') {
            count++
        }
        j = (j + right) % length
    }
    return count
}


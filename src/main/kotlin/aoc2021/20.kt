import java.io.File
import java.lang.StringBuilder

fun main() {
    val lines = File("input2021/input20.txt").readLines()
    val enhancement = lines[0]
    var map = mutableMapOf<Pair<Int, Int>, Int>()
    for ((i, line) in lines.drop(2).withIndex()) {
        for ((j, c) in line.withIndex()) {
            if (c == '#') map[Pair(i, j)] = 1
        }
    }

    val max = 150
    val min = -50
    for (a in 1..50) {
        val newMap = mutableMapOf<Pair<Int, Int>, Int>()
        for (i in min..max) {
            for (j in min..max) {
                val binary = StringBuilder()
                for (di in -1..1) {
                    for (dj in -1..1) {
                        if (map[Pair(i + di, j + dj)] == null &&
                            ((i + di in min..max && j + dj in min..max) || a % 2 == 1)) {
                            binary.append(0)
                        } else {
                            binary.append(1)
                        }
                    }
                }
                val number = binary.toString().toInt(2)
                if (enhancement[number] == '#') newMap[Pair(i, j)] = 1
            }
        }
        map = newMap
        if (a == 2) {
            println("Part1: ${map.count { it.value == 1 }}")
        }
    }
    println("Part2: ${map.count { it.value == 1 }}")
}
import java.io.File
import kotlin.math.max
import kotlin.math.min

fun main() {
    val lines = File("input2022/input14.txt").readLines()
    val rock = mutableSetOf<Pair<Int, Int>>()
    for (a in lines) {
        val path = a.split("->").map { it.trim() }
        var prev = -1 to -1
        for (p in path) {
            val split = p.split(",").map { it.trim().toInt() }
            val coord = split.first() to split.last()
            if (prev != -1 to -1) {
                for (i in min(prev.first, coord.first)..max(coord.first, prev.first)) {
                    for (j in min(prev.second, coord.second)..max(coord.second, prev.second)) {
                        rock.add(i to j)
                    }
                }
            }
            prev = coord
        }
    }
    val floor = rock.maxBy { it.second }.second + 2
    var part1 = false
    val sand = mutableSetOf<Pair<Int, Int>>()
    val sandStart = 500 to 0
    var sandCurrent = sandStart
    val dirs = listOf(0 to 1, -1 to 1, 1 to 1)
    while (sandStart !in sand) {
        var moved = false
        for (dir in dirs) {
            val testPos = sandCurrent.plus(dir)
            if (testPos !in rock && testPos !in sand && testPos.second < floor) {
                sandCurrent = testPos
                moved = true
                break
            }
        }
        if (!moved) {
            sand.add(sandCurrent)
            sandCurrent = sandStart
        }

        if (sandCurrent.second >= floor - 1 && !part1) {
            println("part1: ${sand.size}")
            part1 = true
        }
    }
    println("part2: ${sand.size}")
}

private fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = this.first + other.first to this.second + other.second
import java.io.File
import kotlin.math.abs

fun main() {
    val numbers = File("input2021/input7.txt").readLines().first().split(",").map { it.toInt() }
    println("part1: ${calculateFuel(numbers)}")
    println("part2: ${calculateFuel(numbers, true)}")
}

private fun calculateFuel(positions: List<Int>, part2: Boolean = false): Int {
    val maxPosition = positions.maxOrNull() ?: 0
    var leastFuel = Int.MAX_VALUE
    for (i in 0..maxPosition) {
        val fuel = positions.map { abs(it - i) }.map { if (part2) (it * (it + 1)) / 2 else it }.sum()
        if (fuel < leastFuel) {
            leastFuel = fuel
        }
    }
    return leastFuel
}


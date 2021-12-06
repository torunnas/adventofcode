import java.io.File

fun main() {
    val list = File("input2021/input6.txt").readLines()
    val numbers = list[0].split(",").map { it.toInt() }
    var timerToFish = (0..8).map { n -> n to numbers.count { it == n }.toLong() }.toMap()
    for (day in 1..256) {
        val nextDay = mutableMapOf<Int, Long>()
        for (i in 0..8) {
            nextDay[i] = when (i) {
                8 -> timerToFish.getOrDefault(0, 0)
                6 -> timerToFish.getOrDefault(i + 1, 0) + timerToFish.getOrDefault(0, 0)
                else -> timerToFish.getOrDefault(i + 1, 0)
            }
        }
        timerToFish = nextDay
        if (day == 80) {
            println("part1: ${timerToFish.values.sum()}")
        }
    }
    println("part2: ${timerToFish.values.sum()}")

}
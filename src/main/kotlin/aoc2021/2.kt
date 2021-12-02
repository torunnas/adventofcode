import java.io.File

fun main() {
    val commands = File("input2021/input2.txt").readLines().map {
        val (inst, count) = it.split(" ")
        Pair(inst, count.toInt())
    }
    var pos = 0
    var depth = 0
    var aim = 0
    for (command in commands) {
        when (command.first) {
            "forward" -> {
                pos += command.second
                depth += command.second * aim
            }
            "down" -> aim += command.second

            "up" -> aim -= command.second

        }
    }
    println("part1: ${pos * aim}")
    println("part2: ${pos * depth}")
}
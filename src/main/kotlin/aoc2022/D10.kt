import java.io.File

fun main() {
    val commands = File("input2022/input10.txt").readLines().map { it.split(" ") }
        .map { it.first().trim() to if (it.size > 1) it.last().trim().toInt() else 0 }
    var register = 1
    var cycle = 0
    var part1 = 0L
    val crt = mutableListOf<String>()
    var crtRow = ""

    fun handleCycle() {
        cycle++
        crtRow += if ((cycle - 1) % 40 in register - 1..register + 1) "#" else " "
        if (cycle % 40 == 20) {
            part1 += cycle * register
        }
        if (cycle % 40 == 0) {
            crt.add(crtRow)
            crtRow = ""
        }
    }

    for (command in commands) {
        handleCycle()
        if (command.first == "addx") {
            handleCycle()
            register += command.second
        }

    }
    println("part1: $part1")
    println("part2:")
    crt.forEach { println(it) }
}
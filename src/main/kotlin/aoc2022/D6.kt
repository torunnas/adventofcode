import java.io.File

fun main() {
    val line = File("input2022/input6.txt").readLines().first()
    println("part1: ${solve(line, 4)}")
    println("part2: ${solve(line, 14)}")
}

private fun solve(datastream: String, marker: Int) =
    datastream.windowed(marker).indexOfFirst { it.toSet().size == marker } + marker

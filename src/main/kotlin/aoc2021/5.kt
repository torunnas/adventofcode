import java.io.File
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

data class Line(val x1: Int, val x2: Int, val y1: Int, val y2: Int)

fun main() {
    val list = File("input2021/input5.txt").readLines()
    val coord = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
    val lines = mutableListOf<Line>()
    for (l in list) {
        val split = l.split("->")
        val left = split[0].split(",").map { it.trim().toInt() }
        val right = split[1].split(",").map { it.trim().toInt() }
        coord.add(
            Pair(
                Pair(left[0], left[1]),
                Pair(right[0], right[1])
            )
        )
        lines.add(Line(left[0], right[0], left[1], right[1]))
    }
    val countVertHor = mutableMapOf<Pair<Int, Int>, Int>()
    val countDiagonal = mutableMapOf<Pair<Int, Int>, Int>()
    for (line in lines) {
        if (line.x1 == line.x2) {
            (min(line.y1, line.y2)..max(line.y1, line.y2)).forEach {
                countVertHor[Pair(line.x1, it)] = countVertHor.getOrDefault(Pair(line.x1, it), 0) + 1
            }
        } else if (line.y1 == line.y2) {
            (min(line.x1, line.x2)..max(line.x1, line.x2)).forEach {
                countVertHor[Pair(it, line.y1)] = countVertHor.getOrDefault(Pair(it, line.y1), 0) + 1
            }
        } else {
            val dy = (line.y2 - line.y1).sign
            val dx = (line.x2 - line.x1).sign
            var x = line.x1
            var y = line.y1
            while (x != line.x2) {
                countDiagonal[Pair(x, y)] = countDiagonal.getOrDefault(Pair(x, y), 0) + 1
                x += dx
                y += dy
            }
            countDiagonal[Pair(x, y)] = countDiagonal.getOrDefault(Pair(x, y), 0) + 1

        }
    }
    println("Part1: ${countVertHor.values.filter { it > 1 }.count()}")
    println(
        "Part2: ${
            (countDiagonal.keys + countVertHor.keys).associateWith {
                listOfNotNull(
                    countDiagonal[it],
                    countVertHor[it]
                )
            }.count { it.value.sum() > 1 }
        }"
    )

}
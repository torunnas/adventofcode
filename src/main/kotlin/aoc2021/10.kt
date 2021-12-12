import java.io.File

fun main() {
    val lines = File("input2021/input10.txt").readLines()
    val result = doStuff(lines)
    println("part1: ${result.first}")
    println("part2: ${result.second}")
}

fun doStuff(lines: List<String>): Pair<Int, Long> {
    val closeToOpen = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
    val closeToPoints = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val openToPointsPart2 = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)
    var part1 = 0
    val part2 = mutableListOf<Long>()
    for (line in lines) {
        val queue = mutableListOf<Char>()
        var corrupted = false
        for (c in line) {
            when (c) {
                '(', '[', '{', '<' -> queue.add(c)
                else -> {
                    val index = queue.lastIndexOf(closeToOpen[c])
                    if (index == -1 || index != queue.lastIndex) {
                        part1 += closeToPoints.getValue(c)
                        corrupted = true
                        break
                    }
                    queue.removeAt(index)
                }
            }
        }
        if (queue.isNotEmpty() && !corrupted) {
            var sum = 0L
            for (q in queue.reversed()) {
                sum = sum * 5 + openToPointsPart2.getValue(q)
            }
            part2.add(sum)
        }
    }
    part2.sort()
    return Pair(part1, part2[part2.size / 2])
}

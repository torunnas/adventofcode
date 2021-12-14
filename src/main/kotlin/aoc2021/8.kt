import java.io.File
import java.lang.StringBuilder

fun main() {
    val lines = File("input2021/input8.txt").readLines()
    var count = 0
    var sum = 0
    for (line in lines) {
        val patternMapping = mutableMapOf<Int, Set<Char>>()
        val split = line.split("|")
        val output = split[1].split(" ").map { it.trim() }.filterNot { it.isEmpty() }
        for (digit in output) {
            if (listOf(2, 4, 3, 7).contains(digit.length)) {
                count++
            }
        }

        val signalPatterns = split[0].split(" ").map { it.trim() }.map { it.toSet() }
        for (pattern in signalPatterns) {
            when (pattern.size) {
                2 -> patternMapping[1] = pattern
                4 -> patternMapping[4] = pattern
                3 -> patternMapping[7] = pattern
                7 -> patternMapping[8] = pattern
            }
        }
        for (pattern in signalPatterns) {
            if (pattern.size == 6 && pattern.containsAll(patternMapping.getValue(4))) {
                patternMapping[9] = pattern
            } else if (pattern.size == 6 && pattern.containsAll(patternMapping.getValue(1))) {
                patternMapping[0] = pattern
            } else if (pattern.size == 6) {
                patternMapping[6] = pattern
            } else if (pattern.size == 5 && pattern.containsAll(patternMapping.getValue(1))) {
                patternMapping[3] = pattern
            } else if (pattern.size == 5 && containsAllButOne(pattern, patternMapping.getValue(4))) {
                patternMapping[5] = pattern
            } else if (pattern.size == 5) {
                patternMapping[2] = pattern
            }
        }
        val result = StringBuilder()
        for (digit in output) {
            result.append(patternMapping.filter { it.value == digit.toCharArray().toSet() }.keys.first())
        }
        sum += result.toString().toInt()

    }
    println("part1: $count")
    println("part2: $sum")
}

fun containsAllButOne(toCheckContains: Set<Char>, contained: Set<Char>): Boolean {
    var count = 0
    for (c in contained) {
        if (toCheckContains.contains(c)) {
            count++
        }
    }
    if (count == contained.size - 1) return true
    return false
}



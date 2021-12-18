import java.io.File

fun main() {
    val lines = File("input2021/input18.txt").readLines()
    var current = ""
    for (line in lines) {
        if (current.isEmpty()) {
            current = line
        } else {
            current = "[$current,$line]"
        }
        current = reduce(current)
    }
    println("Part1: ${getMagnitude(current)}")
    var max = 0L
    for (l1 in lines) {
        for (l2 in lines) {
            if (l1 == l2) continue
            current = reduce("[$l1,$l2]")
            val magnitude = getMagnitude(current)
            if (magnitude > max) {
                max = magnitude
            }
        }
    }
    println("Part2: $max")
}

private fun reduce(snailFish : String):String{
    var current = snailFish
    var previous = ""
    while (previous != current) {
        while (previous != current) {
            previous = current
            current = explode(current)
        }
        previous = current
        current = split(current)
    }
    return current
}

private fun getMagnitude(snailFish: String): Long {
    if (snailFish.count { it == '[' } == 1) {
        val split = snailFish.split(",")
        var left = split[0].removePrefix("[").trim().toLong()
        var right = split[1].removeSuffix("]").trim().toLong()
        return 3 * left + 2 * right
    }
    if (!snailFish.contains("[")) {
        return snailFish.toLong()
    }
    var countStart = 0
    var countEnd = 0
    var split = -1
    for ((i, c) in snailFish.withIndex()) {
        if (c == '[') countStart++
        if (c == ']') countEnd++
        if (c == ',' && countStart - countEnd == 1) {
            split = i
            break
        }
    }
    return 3 * getMagnitude(snailFish.substring(1 until split)) +
            2 * getMagnitude(snailFish.substring(split + 1 until snailFish.length - 1))
}

private fun split(snailFish: String): String {
    val indexOfFirst = snailFish.windowed(2).indexOfFirst { a -> a.all { it.isDigit() } }
    if (indexOfFirst < 0) {
        return snailFish
    }
    val first = snailFish.windowed(2).first { a -> a.all { it.isDigit() } }.toInt()
    return snailFish.substring(0 until indexOfFirst) +
            "[" + (first / 2) + "," + (first + 1) / 2 + "]" +
            snailFish.substring(indexOfFirst + 2)
}

private fun explode(snailFish: String): String {
    var leftNumber = -1
    var countStart = 0
    var countEnd = 0
    var explodeIndex = -1
    for ((i, c) in snailFish.withIndex()) {
        if (c == '[') countStart++
        if (c == ']') countEnd++
        if (countStart - countEnd == 5) {
            explodeIndex = i
            break
        }
        if (c.isDigit() && !snailFish[i - 1].isDigit()) {
            leftNumber = snailFish.substring(i).takeWhile { it.isDigit() }.toInt()
        }
    }
    if (explodeIndex == -1) {
        return snailFish
    }
    val closeIndex = snailFish.substring(explodeIndex).indexOfFirst { it == ']' }
    val toExplode = snailFish.substring(explodeIndex + 1, explodeIndex + closeIndex).split(",").map { it.toInt() }
    val indexOfFirstNumber = snailFish.substring(explodeIndex + closeIndex + 1).indexOfFirst { it.isDigit() }
    val indexOfLeftNumber = snailFish.substring(0 until explodeIndex).lastIndexOf(leftNumber.toString())
    val beginning =
        if (leftNumber == -1) {
            snailFish.substring(0 until explodeIndex)
        } else {
            snailFish.substring(0 until indexOfLeftNumber) + (leftNumber + toExplode[0]) +
                    snailFish.substring(indexOfLeftNumber + leftNumber.toString().length until explodeIndex)
        }

    val end =
        if (indexOfFirstNumber == -1) {
            snailFish.substring(explodeIndex + closeIndex + 1)
        } else {
            val rightNumber =
                snailFish.substring(explodeIndex + closeIndex + 1 + indexOfFirstNumber).takeWhile { it.isDigit() }
                    .toInt()
            snailFish.substring(explodeIndex + closeIndex + 1 until explodeIndex + closeIndex + 1 + indexOfFirstNumber) +
                    (rightNumber + toExplode[1]) + snailFish.substring(explodeIndex + closeIndex + 1 + indexOfFirstNumber + rightNumber.toString().length)
        }
    return beginning + "0" + end

}



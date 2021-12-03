import java.io.File
import java.util.BitSet

fun main() {
    val list = File("input2021/input3.txt").readLines()
    println("part1: ${part1(list)}")
    println("part2: ${part2(list)}")
}

fun part1(binaries: List<String>): Int {
    val gamma = StringBuilder()
    for (i in binaries[0].indices) {
        val zero = binaries.count { it[i] == '0' }
        val one = binaries.count { it[i] == '1' }
        if (zero > one) {
            gamma.append(0)
        } else {
            gamma.append(1)
        }
    }
    val bitSet = BitSet.valueOf(longArrayOf(gamma.toString().toLong(2)))
    bitSet.flip(0, gamma.toString().length)
    return gamma.toString().toInt(2) * bitSet.toLongArray()[0].toInt()
}

fun part2(binaries: List<String>): Int {
    return reduce(binaries, '0') * reduce(binaries, '1')
}

fun reduce(binaries: List<String>, preferred: Char): Int {
    var copy = binaries.toList()
    for (i in binaries[0].indices) {
        if (copy.size == 1) return copy[0].toInt(2)
        val zero = copy.count { it[i] == '0' }
        val one = copy.count { it[i] == '1' }
        copy = if (zero <= one) {
            copy.filter { it[i] == preferred }
        } else {
            copy.filter { it[i] != preferred }
        }
    }
    return copy[0].toInt(2)
}
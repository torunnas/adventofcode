package `2020`

import java.io.File

fun main() {
    var list = mutableListOf<String>()
    File("input2020/input2.txt").forEachLine { list.add(it) }
    println("part1: ${part1(list)}")
    println("part2: ${part2(list)}")
}

private fun part1(list: List<String>): Int {
    var count = 0;
    for (s in list) {
        val policy = getPolicy(s)
        val letterCount = policy.password.count { c -> c == policy.letter }
        if (letterCount in policy.from..policy.to) {
            count++;
        }
    }
    return count
}

private fun part2(list: List<String>): Int {
    var count = 0;
    for (s in list) {
        val policy = getPolicy(s)
        if ((policy.password[policy.from - 1] == policy.letter).xor(policy.password[policy.to - 1] == policy.letter)) {
            count++;
        }
    }
    return count
}

private fun getPolicy(s: String): Policy {
    val split = s.split(" ")
    val password = split[2].trim()
    val letter = split[1].trim()[0]
    val fromTo = split[0].trim().split("-")
    val from = fromTo[0].toInt()
    val to = fromTo[1].toInt()
    return Policy(password, letter, from, to)
}

private data class Policy(val password: String, val letter: Char, val from: Int, val to: Int)

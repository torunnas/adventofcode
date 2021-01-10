package main.kotlin.aoc2020

import java.io.File

private val REQUIRED_FIELDS = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
private val VALID_ECL = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

fun main() {
    var lines = mutableListOf<String>()
    File("input2020/input4.txt").forEachLine { lines.add(it) }
    var part1 = 0
    var part2 = 0
    var passport = mutableListOf<String>()
    for (line in lines) {
        if (line.isBlank()) {
            if (checkPassport(passport, ::checkFieldPart1)) {
                part1++
            }
            if (checkPassport(passport, ::checkFieldPart2)) {
                part2++
            }
            passport = mutableListOf()
            continue
        }
        line.split(" ").forEach { passport.add(it) }
    }
    println("part1: $part1")
    println("part2: $part2")
}

private fun checkPassport(
    passport: List<String>,
    checkField: (word: String, value: String) -> Boolean
): Boolean {
    var validFields = mutableListOf<String>()
    for (field in passport) {
        val split = field.split(":")
        if (checkField(split[0], split[1])) {
            validFields.add(split[0])
        }
    }
    return (REQUIRED_FIELDS - validFields).isEmpty()
}

private fun checkFieldPart1(word: String, value: String) = true

private fun checkFieldPart2(word: String, value: String) = isValid(word, value)

private fun isValid(word: String, value: String) =
    when (word) {
        "byr" -> value.toInt() in 1920..2002
        "iyr" -> value.toInt() in 2010..2020
        "eyr" -> value.toInt() in 2020..2030
        "hgt" -> value.contains("cm") && value.take(value.length - 2).toInt() in 150..193
                || value.contains("in") && value.take(value.length - 2).toInt() in 59..76
        "hcl" -> value.matches("#[0-9a-f]{6}".toRegex())
        "ecl" -> VALID_ECL.contains(value)
        "pid" -> value.matches("\\d{9}".toRegex())
        else -> false
    }
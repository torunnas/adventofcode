package main.kotlin.aoc2020

import java.io.File
import java.math.BigInteger

fun main() {
    var list = mutableListOf<String>()
    File("input2020/input14.txt").forEachLine { list.add(it) }
    var start = System.currentTimeMillis()
    println("part1: ${part1(list)} ${System.currentTimeMillis() - start}ms")
    start = System.currentTimeMillis()
    println("part2: ${part2(list)} ${System.currentTimeMillis() - start}ms")
}

private fun part2(list: List<String>): Long {
    var mask: String = ""
    var map = mutableMapOf<BigInteger, Long>()
    for (a in list) {
        if (a.startsWith("mask")) {
            mask = a.split("=")[1].trim()
        } else if (a.startsWith("mem")) {
            val split = a.split("=")
            var mem = split[0].split("[")[1].split("]")[0].trim().toInt().toString(2).padStart(36, '0')
            var input = split[1].trim().toLong()
            var value = StringBuilder(mem)
            for (i in mask.indices) {
                if (mask[i] != '0') {
                    value[i] = mask[i]
                }
            }
            var combos = mutableListOf(value.toString())
            while (combos[0].contains('X')) {
                val newCombos = mutableListOf<String>()
                for (c in combos) {
                    newCombos.add(c.replaceFirst('X', '0'))
                    newCombos.add(c.replaceFirst('X', '1'))
                }
                combos = newCombos
            }
            for (c in combos) {
                map[BigInteger(c, 2)] = input
            }
        }
    }
    return map.values.sum()
}

private fun part1(list: List<String>): Long {
    var mask: String = ""
    var map = mutableMapOf<Int, Long>()
    for (a in list) {
        if (a.startsWith("mask")) {
            mask = a.split("=")[1].trim()
        } else if (a.startsWith("mem")) {
            val split = a.split("=")
            var mem = split[0].split("[")[1].split("]")[0].trim().toInt()
            var binary = split[1].trim().toLong().toString(2).padStart(36, '0')
            var value = StringBuilder(binary)
            for (i in mask.indices) {
                if (mask[i] != 'X') {
                    value[i] = mask[i]
                }
            }
            map[mem] = BigInteger(value.toString(), 2).toLong()
        }
    }
    return map.values.sum()
}
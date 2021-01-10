package main.kotlin.aoc2020

import java.io.File

fun main() {
    var lines = mutableListOf<String>()
    File("input2020/input16.txt").forEachLine { if (it.isNotEmpty()) lines.add(it) }
    var mine = false
    var others = false
    var errorRate = 0
    var validTickets = mutableListOf<List<Int>>()
    var fields = mutableListOf<Field>()
    var myTicket = listOf<Int>()
    var ranges = listOf<IntRange>()
    for (line in lines) {
        if (line.startsWith("your")) {
            mine = true

        } else if (line.startsWith("nearby")) {
            others = true
            ranges = fields.flatMap { it.range }
        } else if (line[0].isLetter()) {
            val split = line.split(":")
            val a = split[1].split("or")
            val b = a[0].trim().split("-")
            val c = a[1].trim().split("-")
            var f = mutableListOf<IntRange>()
            f.add(b[0].toInt()..b[1].toInt())
            f.add(c[0].toInt()..c[1].toInt())
            fields.add(Field(split[0].trim(), f))

        } else if (others) {
            val ticket = line.split(",").map { it -> Integer.parseInt(it) }.toMutableList()
            var validTicket = true
            ticket.forEach { if (ranges.none { r -> it in r }){ validTicket = false; errorRate += it }}
            if (validTicket) {
                validTickets.add(ticket)
            }
        } else if (mine) {
            myTicket = line.split(",").map { it -> Integer.parseInt(it) }.toList()
        }

    }
    println("part 1: $errorRate")
    var candidates = mutableMapOf<String, Set<Int>>()
    for (i in validTickets[0].indices) {
        for (t in fields) {
            var add = true
            a@ for (n in validTickets) {
                var found = false
                for (ra in t.range) {
                    if (n[i] in ra) {
                        found = true
                    }
                }
                if (!found) {
                    add = false
                    break@a
                }
            }
            if (add) {
                if (candidates[t.name] == null) {
                    candidates[t.name] = setOf()
                }
                candidates[t.name] = candidates[t.name]!! + i
            }

        }
    }
    val sortedBy = candidates.toList().sortedBy { (_, value) -> value.size }
    var found = mutableSetOf<Int>()
    var pos = mutableMapOf<String, Int>()
    for (h in sortedBy) {
        pos[h.first] = (h.second - found).first()
        found = h.second.toMutableSet()
    }
    var result = 1L
    for (p in pos) {
        if (p.key.startsWith("departure")) {
            result *= myTicket[p.value].toLong()
        }
    }
    println("part 2: $result")
}

data class Field(val name: String, val range: List<IntRange>)
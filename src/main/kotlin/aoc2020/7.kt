package main.kotlin.aoc2020

import java.io.File

private var visited = mutableSetOf<String>()

fun main() {
    var list = mutableListOf<String>()
    File("input2020/input7.txt").forEachLine { list.add(it) }
    var map = mutableMapOf<String, List<String>>()
    for (s in list) {
        val split = s.split("contain")
        var bag = split[0].trim().split("bags")[0].trim()
        var content = split[1].trim().split(",").toList()
        map[bag] = content
    }
    var bag = "1 shiny gold"
    var bags = bags(map, "shiny gold")
    println(bags)
    println(visited.size - 1)
    println(bags2(map, bag))
}

fun bags2(map: MutableMap<String, List<String>>, bag: String): Long {
    var count = 0L
    val trim = bag.trim().drop(1).trim().split("bag")[0].trim()
    for (e in map) {
        if (e.key.contains(trim)) {
            for (a in e.value) {
                if (!a.contains("no other bags")) {
                    var mult = a.trim()[0].toString().toInt()
                    count += mult + mult * bags2(map, a)
                }
            }
        }
    }
    return count
}


fun bags(map: MutableMap<String, List<String>>, bag: String): Long {
    var count = 1L
    visited.add(bag)
    for (e in map) {
        if (e.value.any { it.contains(bag) }) {
            count += bags(map, e.key)
        }
    }
    if (count == 0L) {
        return 1
    } else {
        return count
    }
}

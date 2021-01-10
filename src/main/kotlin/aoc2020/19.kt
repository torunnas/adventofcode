package main.kotlin.aoc2020

import java.io.File

fun main() {
    var start = System.currentTimeMillis()
    var list = mutableListOf<String>()
    File("input2020/input19.txt").forEachLine { list.add(it.trim()) }
    parseRules(list.subList(0, list.indexOf("")))
    val words = list.drop(list.indexOf("")).toSet()
    var sum = 0
    val options42 = findOptions(getRule(42))
    val options31 = findOptions(getRule(31))
    val options = findOptions(getRule(0))
    val leftovers = words - options
    words@ for (w in leftovers) {
        if (options.contains(w) || checkLoop(w, options42, options31)) {
            sum++
        }
    }
    println(words.intersect(options).size)
    println(sum + words.intersect(options).size)
    println("${System.currentTimeMillis() - start}")

}

fun checkLoop(
    word: String,
    options42: Set<String>,
    options31: Set<String>
): Boolean {
    var temp = word
    var c42 = 0
    var c31 = 0
    var done42 = false
    //count(word,options42)
    wh@ while (temp.isNotEmpty()) {
        if (!done42) {
            for (a in options42) {
                if (temp.startsWith(a)) {
                    temp = temp.drop(a.length)
                    c42++
                    continue@wh
                }
            }
        }
        done42 = true
        if (c31 >= c42 || c42 < 2) {
            return false
        }
        for (a in options31) {
            if (temp.startsWith(a)) {
                temp = temp.drop(a.length)
                c31++
                continue@wh
            }
        }
        break
    }
    if (temp.isEmpty() && c42 > 0 && c31 > 0 && c42 > c31) {
        return true
    }
    return false
}

//fun count(word: String, options: Set<String>) {
//    var temp = word
//    var count = 0
//    wh@ while (temp.isNotEmpty()) {
//            for (a in options42) {
//                if (temp.startsWith(a)) {
//                    temp = temp.drop(a.length)
//                    count++
//                    continue@wh
//                }
//            }
//        }
//}


fun findOptions(rule: String): Set<String> {
    return when {
        rule.contains('|') -> {
            val split = rule.split('|').map { it.trim() }
            findOptions(split[0]) + findOptions(split[1])
        }
        rule.contains(' ') -> {
            val split = rule.trim().split(' ').map { it.trim() }
            val a = findOptions(getRule(split[0].toInt()))
            val b = findOptions(getRule(split[1].toInt()))
            a.map { first -> b.map { first + it } }.flatten().toSet()
        }
        rule.toIntOrNull() != null -> findOptions(getRule(rule.toInt()))
        else -> setOf(rule)
    }
}

private var rules = mapOf<Int, String>()
fun getRule(number: Int) = rules[number] ?: error("RULE NOT FOUND")

fun parseRules(input: List<String>) {
    var temp = mutableMapOf<Int, String>()
    for (r in input) {
        var split = r.split(":").map { it.replace("\"", "").trim() }
        temp[split[0].toInt()] = split[1]
    }
    rules = temp
}


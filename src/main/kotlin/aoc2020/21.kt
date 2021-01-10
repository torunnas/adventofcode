package main.kotlin.aoc2020

import java.io.File

fun main() {
    val maps = parseInput()
    val allergensToIngredients = maps.first
    val allIngredients = maps.second
    val allergenCandidates =
        allergensToIngredients.map { it.key to it.value.reduce { acc, i -> acc.intersect(i) } }.toMap()

    println("part1: ${part1(allergenCandidates, allIngredients)}")
    println("part2: ${part2(allergenCandidates)}")
}

private fun part1(allergenCandidates: Map<String, Set<String>>, allIngredients: MutableList<String>): Int {
    val noAllergens = allIngredients.toSet() - allergenCandidates.flatMap { it.value }.toSet()
    return  allIngredients.count { noAllergens.contains(it) }
}

private fun part2(allergenCandidates: Map<String, Set<String>>): String {
    var allergenCandidatesLeft = allergenCandidates
    var final = mutableMapOf<String, String>()
    while (final.size < allergenCandidatesLeft.size) {
        val allergen = allergenCandidatesLeft.toList().first { it.second.size == 1 }
        final[allergen.first] = allergen.second.first()
        allergenCandidatesLeft = allergenCandidatesLeft.map { it.key to (it.value - allergen.second.first()) }.toMap()
    }
    final = final.toSortedMap(compareBy<String> { it })
    return final.map { it.value }.joinToString(",")
}

private fun parseInput(): Pair<MutableMap<String, List<Set<String>>>, MutableList<String>> {
    val lines = mutableListOf<String>()
    File("input2020/input21.txt").forEachLine { lines.add(it) }
    val allergensToIngredients = mutableMapOf<String, List<Set<String>>>()
    val allIngredients = mutableListOf<String>()
    for (line in lines) {
        val split = line.split("(").map {
            it.replace(")", "")
                .replace("contains", "")
        }.map { it.trim() }
        val ingredients = split[0].split(' ').map { it.trim() }.toSet()
        val allergens = split[1].split(",").map { it.trim() }.toSet()
        allIngredients.addAll(ingredients)
        allergens.forEach {
            allergensToIngredients.add(it, ingredients)
        }
    }
    return Pair(allergensToIngredients, allIngredients)
}

private fun <K,V> MutableMap<K, List<Set<V>>>.add(key: K, set: Set<V>) {
    this[key] = this.getOrDefault(key, listOf()) + listOf(set)
}
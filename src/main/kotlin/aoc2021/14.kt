import java.io.File

fun main() {
    val lines = File("input2021/input14.txt").readLines()
    val template = lines[0]
    val rules = getRules(lines)
    var pairsCount = getPairsCount(template)

    for (i in 1..40) {
        val newCount = mutableMapOf<String, Long>()
        for (a in pairsCount.keys) {
            val newPair1 = a.first() + rules.getValue(a)
            val newPair2 = rules.getValue(a) + a.last()
            newCount[newPair1] = newCount.getOrDefault(newPair1, 0) + pairsCount.getOrDefault(a, 0)
            newCount[newPair2] = newCount.getOrDefault(newPair2, 0) + pairsCount.getOrDefault(a, 0)
        }
        pairsCount = newCount
        if (i == 10) {
            println("part1: ${getMaxMinDiff(pairsCount, template)}")
        }
    }
    println("part2: ${getMaxMinDiff(pairsCount, template)}")
}

fun getMaxMinDiff(pairsCount: Map<String, Long>, template: String): Long {
    val charCount = mutableMapOf<Char, Long>()
    for ((pair, count) in pairsCount) {
        charCount[pair.first()] = charCount.getOrDefault(pair.first(), 0) + count
        charCount[pair.last()] = charCount.getOrDefault(pair.last(), 0) + count
    }
    //add 1 for first and last char in template to double count all chars. First and last char are only included in one pair
    charCount[template.first()] = charCount.getValue(template.first()) + 1
    charCount[template.last()] = charCount.getValue(template.last()) + 1

    val max = charCount.maxOf { it.value } / 2
    val min = charCount.minOf { it.value } / 2
    return max - min
}

fun getPairsCount(template: String): Map<String, Long> {
    val pairToCount = mutableMapOf<String, Long>()
    for (pair in template.windowed(2)) {
        pairToCount[pair] = pairToCount.getOrDefault(pair, 0) + 1
    }
    return pairToCount
}

fun getRules(lines: List<String>): Map<String, String> {
    val rules = mutableMapOf<String, String>()
    for (line in lines) {
        if (line.length > 10 || line.isEmpty()) continue
        val split = line.split("->")
        rules[split[0].trim()] = split[1].trim()
    }
    return rules
}
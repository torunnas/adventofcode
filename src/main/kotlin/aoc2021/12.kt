import java.io.File

fun main() {
    val lines = File("input2021/input12.txt").readLines()
    val map = mutableMapOf<String, Set<String>>()
    for (line in lines) {
        val split = line.split("-")
        map[split[0]] = map.getOrDefault(split[0], emptySet()) + split[1]
        map[split[1]] = map.getOrDefault(split[1], emptySet()) + split[0]

    }
    println("part1: ${visit(map["start"]!!, mapOf(), map, true)}")
    println("part2: ${visit(map["start"]!!, mapOf(), map, false)}")
}

fun visit(options: Set<String>, visited: Map<String, Int>, map: Map<String, Set<String>>, part1: Boolean): Int {
    var count = 0
    for (o in options) {
        when {
            o == "start" -> continue
            visited.keys.contains(o) && (part1 || visited.values.contains(2)) -> continue
            o == "end" -> count += 1
            o.first().isUpperCase() -> count += visit(map[o]!!, visited, map, part1)
            o.first().isLowerCase() -> {
                val newMap = visited.toMutableMap()
                newMap[o] = newMap.getOrDefault(o, 0) + 1
                count += visit(map[o]!!, newMap, map, part1)
            }
        }
    }
    return count
}

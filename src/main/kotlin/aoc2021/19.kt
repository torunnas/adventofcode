import java.io.File
import kotlin.math.abs


data class Coord(val x: Int, val y: Int, val z: Int, val rotations: List<Int> = emptyList())

fun main() {
    val lines = File("input2021/input19.txt").readLines()
    var scanner = mutableListOf<Coord>()
    val scanners = mutableListOf<MutableList<Coord>>()
    for (line in lines) {
        if (line.isEmpty()) continue
        if (line.startsWith("---")) {
            if (scanner.isNotEmpty()) scanners.add(scanner)
            scanner = mutableListOf()
        } else {
            val split = line.split(",").map { it.trim().toInt() }
            scanner.add(Coord(split[0], split[1], split[2]))
        }
    }
    scanners.add(scanner)
    val relativeScannerPositions = mutableMapOf<Pair<Int, Int>, Coord>()
    for ((i, s1) in scanners.withIndex()) {
        for ((j, s2) in scanners.withIndex()) {
            if (s1 == s2) continue
            val relScannerPosition = findOverlap(s1, s2, i, j)
            if (relScannerPosition != null) relativeScannerPositions[Pair(i, j)] = relScannerPosition;
        }
    }
    val absoluteScannerPositions = findRelativeTo0(relativeScannerPositions)

    val found = mutableSetOf<Coord>()
    for ((i, s) in scanners.withIndex()) {
        val scannerPosition = absoluteScannerPositions.getValue(i)
        for (c in s) {
            var temp = c
            for (rotation in scannerPosition.rotations) {
                temp = getRotations(temp).getValue(rotation)
            }
            found.add(Coord(temp.x + scannerPosition.x, temp.y + scannerPosition.y, temp.z + scannerPosition.z))
        }
    }

    var max = 0
    for (a in absoluteScannerPositions.values) {
        for (b in absoluteScannerPositions.values) {
            val distance = abs(a.x - b.x) + abs(a.y - b.y) + abs(a.z - b.z)
            if (distance > max) {
                max = distance
            }
        }
    }
    println("Part1: ${found.size}")
    println("Part2: $max")
}

fun findRelativeTo0(positions: MutableMap<Pair<Int, Int>, Coord>): MutableMap<Int, Coord> {
    val map = mutableMapOf<Int, Coord>()
    map[0] = Coord(0, 0, 0, listOf(1))
    while (map.size < 34) {
        for (p in positions) {
            if (!map.containsKey(p.key.second) && map.containsKey(p.key.first)) {
                val scannerPosition = map[p.key.first]!!
                var temp = p.value
                for (rotation in scannerPosition.rotations) {
                    temp = getRotations(temp).getValue(rotation)
                }
                map[p.key.second] = Coord(temp.x + scannerPosition.x, temp.y + scannerPosition.y, temp.z + scannerPosition.z, p.value.rotations + scannerPosition.rotations)
            }
        }
    }
    return map
}

private fun findOverlap(scanner1: List<Coord>, scanner2: List<Coord>, s1Number: Int, s2Number: Int): Coord? {
    val count = mutableMapOf<Coord, Int>()
    for (c1 in scanner1) {
        for (c2 in scanner2) {
            for (r2 in getRotations(c2).values) {
                val currentCord = Coord(c1.x - r2.x, c1.y - r2.y, c1.z - r2.z, r2.rotations)
                val currentCount = count.getOrDefault(currentCord, 0)
                count[currentCord] = currentCount + 1
            }
        }
    }
    if (count.count { it.value > 11 } > 0) {
        return count.filter { it.value > 11 }.keys.first()
    }
    return null
}

private fun getRotations(c: Coord): Map<Int, Coord> {
    return mapOf(
        1 to Coord(c.x, c.y, c.z, listOf(1)),
        2 to Coord(-c.y, c.x, c.z, listOf(2)),
        3 to Coord(-c.x, -c.y, c.z, listOf(3)),
        4 to Coord(c.y, -c.x, c.z, listOf(4)),

        5 to Coord(-c.x, c.y, -c.z, listOf(5)),
        6 to Coord(c.y, c.x, -c.z, listOf(6)),
        7 to Coord(c.x, -c.y, -c.z, listOf(7)),
        8 to Coord(-c.y, -c.x, -c.z, listOf(8)),

        9 to Coord(-c.z, c.y, c.x, listOf(9)),
        10 to Coord(-c.z, c.x, -c.y, listOf(10)),
        11 to Coord(-c.z, -c.y, -c.x, listOf(11)),
        12 to Coord(-c.z, -c.x, c.y, listOf(12)),

        13 to Coord(c.z, c.y, -c.x, listOf(13)),
        14 to Coord(c.z, c.x, c.y, listOf(14)),
        15 to Coord(c.z, -c.y, c.x, listOf(15)),
        16 to Coord(c.z, -c.x, -c.y, listOf(16)),

        17 to Coord(c.x, -c.z, c.y, listOf(17)),
        18 to Coord(-c.y, -c.z, c.x, listOf(18)),
        19 to Coord(-c.x, -c.z, -c.y, listOf(19)),
        20 to Coord(c.y, -c.z, -c.x, listOf(20)),

        21 to Coord(c.x, c.z, -c.y, listOf(21)),
        22 to Coord(-c.y, c.z, -c.x, listOf(22)),
        23 to Coord(-c.x, c.z, c.y, listOf(23)),
        24 to Coord(c.y, c.z, c.x, listOf(24))
    )
}

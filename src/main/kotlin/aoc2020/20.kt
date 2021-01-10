package main.kotlin.aoc2020

import java.io.File
import kotlin.math.sqrt

fun main() {
    var list = mutableListOf<String>()
    File("input2020/input20.txt").forEachLine { list.add(it.trim()) }
    var tileNumber = 0
    var tile = Array(10) { IntArray(10) }
    var tiles = mutableMapOf<Int, Array<IntArray>>()
    var row = 0
    for (a in list) {
        if (a.startsWith("Tile")) {
            val split = a.split(" ").map { it.replace(":", "").trim() }
            tileNumber = split[1].toInt()
        } else if (a.isEmpty()) {
            tiles[tileNumber] = tile
            row = 0
            tile = Array(10) { IntArray(10) }
        } else {
            for (i in a.indices) {
                if (a[i] == '#') {
                    tile[row][i] = 1
                }
            }
            row++
        }
    }
    if (tiles[tileNumber] == null) {
        tiles[tileNumber] = tile
    }
    var start = System.currentTimeMillis()
    val neighbours = mutableMapOf<Int, Set<Int>>()
    val corners = mutableListOf<Int>()
    for (t1 in tiles) {
        var numberOfNeighbours = 0
        val neigboursFound = mutableSetOf<Int>()
        for (t2 in tiles) {
            if (t1.key == t2.key) {
                continue
            }
            if (neighbours(t1.value, t2.value)) {
                numberOfNeighbours++
                neigboursFound.add(t2.key)
            }
        }
        neighbours[t1.key] = neigboursFound
        if (numberOfNeighbours == 2) {
            corners.add(t1.key)
        }
    }
    println("${System.currentTimeMillis()-start}ms")
    start = System.currentTimeMillis()
    var answers = mutableSetOf<Int>()
    for (ti in tiles.keys) {
        answers.addAll(solve(ti, tiles, neighbours))
    }

    println("${corners.map { it.toLong() }.reduce { acc, i -> acc * i }}")
    println(answers)
    println("${System.currentTimeMillis()-start}ms")
}

private fun solve(
    startTile: Int,
    tiles: MutableMap<Int, Array<IntArray>>,
    neighbours: MutableMap<Int, Set<Int>>
): MutableSet<Int> {
    val tilesPlaced = mutableSetOf<Int>()
    val finalRot = mutableMapOf<Int, Array<IntArray>>()
    val positions = mutableMapOf<Int, Pair<Int, Int>>()
    positions[startTile] = Pair(0, 0)
    finalRot[startTile] = tiles.getOrDefault(startTile, emptyArray())
    tilesPlaced.add(startTile)
    while (tilesPlaced.size < tiles.size) {
        var placed = mutableSetOf<Int>()
        for (h in tilesPlaced) {
            var nei = neighbours[h].orEmpty()
            var currentPos = positions[h]!!
            for (n in nei) {
                if (tilesPlaced.contains(n)) {
                    continue
                }
                val rotationSide = findRotation(finalRot[h], tiles[n])
                var pos = Pair(-1, -1)
                when (rotationSide.second) {
                    1 -> pos = currentPos.first + 1 to currentPos.second
                    2 -> pos = currentPos.first to currentPos.second + 1
                    3 -> pos = currentPos.first - 1 to currentPos.second
                    4 -> pos = currentPos.first to currentPos.second - 1
                    else -> println("ERROR")
                }
                positions[n] = pos
                finalRot[n] = rotationSide.first
                placed.add(n)
            }
        }
        tilesPlaced.addAll(placed)
    }
    val size = sqrt(tiles.size.toDouble()).toInt()
    val picture = Array(8 * size) { IntArray(8 * size) }
    val sortedTiles =
        positions.toList().sortedWith(compareBy({ -it.second.first }, { it.second.second })).map { it.first }
    var x = 0
    var y = 0
    for (id in sortedTiles) {
        val til = finalRot.getValue(id)
        for (a in 1..til.size - 2) {
            for (b in 1..til[0].size - 2) {
                picture[x][y] = til[a][b]
                y++
            }
            x++
            y -= 8
        }
        y = (y + 8) % (size * 8)
        if (y != 0) {
            x -= 8
        }
    }
    var options = mutableSetOf<Int>()
    var total = picture.flatMap { it.toList() }.sum()
    for (function in rotaflips) {
        val countMonsters = countMonsters(function(picture))
        if (countMonsters != 0) {
            options.add(total - 15 * countMonsters)
        }
    }
    return options
}

fun countMonsters(picture: Array<IntArray>): Int {
    var seaMonster = mutableListOf(
        Pair(0, 0), Pair(1, 1), Pair(1, 4), Pair(0, 5), Pair(0, 6), Pair(1, 7), Pair(1, 10),
        Pair(0, 11), Pair(0, 12), Pair(1, 13), Pair(1, 16), Pair(0, 17), Pair(0, 18), Pair(-1, 18), Pair(0, 19)
    )
    var monsterCount = 0
    for (i in picture.indices) {
        for (j in picture[i].indices) {
            if (picture[i][j] == 1) {
                var found = true
                for (s in seaMonster) {
                    if (picture.getOrNull(i + s.first)?.getOrNull(j + s.second) != 1) {
                        found = false
                        break
                    }
                }
                if (found) {
                    monsterCount++
                }
            }
        }
    }
    return monsterCount
}

private fun findRotation(t1: Array<IntArray>?, t2: Array<IntArray>?): Pair<Array<IntArray>, Int> {
    if (t1.isNullOrEmpty() || t2.isNullOrEmpty()) {
        return Pair(emptyArray(), 0)
    }
    for (function in rotaflips) {
        val side = t1.side(function(t2))
        if (side != 0) {
            return Pair(function(t2), side)
        }
    }
    return Pair(emptyArray(), 0)
}

private fun neighbours(t1: Array<IntArray>, t2: Array<IntArray>): Boolean {

    for (function in rotaflips) {
        if (t1.sidesEqual(function(t2))) {
            return true
        }
    }
    return false
}

private fun Array<IntArray>.side(other: Array<IntArray>) =
    when {
        this.top().contentEquals(other.bottom()) -> 1
        this.right().contentEquals(other.left()) -> 2
        this.bottom().contentEquals(other.top()) -> 3
        this.left().contentEquals(other.right()) -> 4
        else -> 0
    }

private fun Array<IntArray>.sidesEqual(other: Array<IntArray>) =
    this.top().contentEquals(other.bottom()) || this.left().contentEquals(other.right())
            || this.bottom().contentEquals(other.top()) || this.right().contentEquals(other.left())

private fun Array<IntArray>.top() = this.first()
private fun Array<IntArray>.bottom() = this.last()
private fun Array<IntArray>.left() = this.map { it.first() }.toIntArray()
private fun Array<IntArray>.right() = this.map { it.last() }.toIntArray()

var rotaflips = listOf(
    Array<IntArray>::flipCol,
    Array<IntArray>::flipRow,
    Array<IntArray>::rotate90Clockwise,
    Array<IntArray>::rotate90CounterClockwise,
    Array<IntArray>::identity,
    Array<IntArray>::rotate180Clockwise,
    Array<IntArray>::transpose,
    Array<IntArray>::diagonal

)

private fun Array<IntArray>.rotate90Clockwise() = this.transpose().flipCol()
private fun Array<IntArray>.rotate90CounterClockwise() = this.transpose().flipRow()
private fun Array<IntArray>.rotate180Clockwise() = this.rotate90Clockwise().rotate90Clockwise()
private fun Array<IntArray>.identity() = this
private fun Array<IntArray>.diagonal() = this.rotate180Clockwise().transpose()

private fun Array<IntArray>.flipCol(): Array<IntArray> {
    val new = Array(this.size) { IntArray(this[0].size) }
    for (i in this.indices) {
        for (j in this[i].indices) {
            new[i][j] = this[i][this[i].size - 1 - j]
        }
    }
    return new
}

private fun Array<IntArray>.flipRow(): Array<IntArray> {
    val new = Array(this.size) { IntArray(this[0].size) }
    for (i in this.indices) {
        for (j in this[i].indices) {
            new[i][j] = this[this.size - 1 - i][j]
        }
    }
    return new
}

private fun Array<IntArray>.transpose(): Array<IntArray> {
    val new = Array(this.size) { IntArray(this[0].size) }
    for (i in this.indices) {
        for (j in this[i].indices) {
            new[j][i] = this[i][j]
        }
    }
    return new
}

private fun Array<IntArray>.printNice() {
    for (i in this) {
        for (j in i) {
            print("$j")
        }
        println()
    }
    println()
}
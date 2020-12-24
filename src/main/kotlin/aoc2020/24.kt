package main.kotlin.aoc2020

import java.io.File

fun main() {
    val input = mutableListOf<String>()
    File("input2020/input24.txt").forEachLine { input.add(it.trim()) }
    var blackTiles = flipTilesPart1(input)
    println("part1: ${blackTiles.size}")
    blackTiles = flipTilesPart2(blackTiles)
    println("part2: ${blackTiles.size}")
}

private fun flipTilesPart1(instructions: List<String>): Set<Pair<Int, Int>> {
    val black = mutableSetOf<Pair<Int, Int>>()
    for (b in instructions) {
        val tile = findTile(b)
        if (black.contains(tile)) {
            black.remove(tile)
        } else {
            black.add(tile)
        }
    }
    return black
}

private fun flipTilesPart2(startTiles: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
    var blackTiles = startTiles
    for (i in 1..100) {
        val afterFlip = mutableSetOf<Pair<Int, Int>>()
        for (x in -100..100) {
            for (y in -100..100) {
                val tile = Pair(x, y)
                val c = countBlackNeighbours(x, y, blackTiles)
                if (!blackTiles.contains(tile) && c == 2) {
                    afterFlip.add(tile)
                } else if (blackTiles.contains(tile) && c in 1..2) {
                    afterFlip.add(tile)
                }
            }
        }
        blackTiles = afterFlip
    }
    return blackTiles
}

private fun findTile(instructions: String): Pair<Int, Int> {
    var left = instructions
    var x = 0
    var y = 0
    while (left.isNotEmpty()) {
        val instruction = getFirstInstruction(left)
        when (instruction) {
            "e" -> x++
            "w" -> x--
            "se" -> y++
            "sw" -> {
                x--;y++
            }
            "nw" -> y--
            "ne" -> {
                x++;y--
            }
        }
        left = left.drop(instruction.length)
    }
    return Pair(x, y)
}

private fun getFirstInstruction(inctructions: String): String {
    if (setOf('e', 'w').contains(inctructions.first())) {
        return inctructions.take(1)
    }
    return inctructions.take(2)
}

private fun countBlackNeighbours(x: Int, y: Int, black: Set<Pair<Int, Int>>): Int {
    var neighbours = 0
    val directions = setOf(0 to 1, 0 to -1, 1 to 0, -1 to 0, -1 to 1, 1 to -1)
    for (dir in directions) {
        if (black.contains(Pair(x + dir.first, y + dir.second))) {
            neighbours++
        }

    }
    return neighbours
}
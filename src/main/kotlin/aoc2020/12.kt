package main.kotlin.aoc2020

import java.io.File
import kotlin.math.abs

fun main() {
    var list = mutableListOf<String>()
    File("input2020/input12.txt").forEachLine { list.add(it) }
    println("part1: ${part1(list)}")
    println("part2: ${part2(list)}")

}

private fun part1(list: MutableList<String>): Int {
    var face = "E"
    var faceD = Pair(1, 0)
    var x = 0
    var y = 0
    for (s in list) {
        val action = s.take(1)
        val steps = s.substring(1).toInt()
        val dir = getDir(action, steps)
        if (action in listOf("N", "S", "W", "E")) {
            x += steps * dir.first
            y += steps * dir.second
        } else if (action == "L") {
            faceD = when (steps) {
                90 -> Pair(-faceD.second, faceD.first)
                180 -> Pair(-faceD.first, -faceD.second)
                270 -> Pair(faceD.second, -faceD.first)
                else -> faceD
            }
        } else if (action == "R") {
            faceD = when (steps) {
                90 -> Pair(faceD.second, -faceD.first)
                180 -> Pair(-faceD.first, -faceD.second)
                270 -> Pair(-faceD.second, faceD.first)
                else -> faceD
            }
        } else if (action == "F") {
            y += steps * faceD.second
            x += steps * faceD.first
        }

    }
    return abs(x) + abs(y)
}

fun getDir(dir: String, degree: Int) = when (dir) {
    "N" -> Pair(0, 1)
    "E" -> Pair(1, 0)
    "S" -> Pair(0, -1)
    "W" -> Pair(-1, 0)
    else -> Pair(0, 0)
}

private fun part2(list: List<String>): Int {
    var x = 10
    var y = 1
    var xs = 0
    var ys = 0
    for (s in list) {
        val action = s.take(1)
        val steps = s.substring(1).toInt()
        if (action == "N") {
            y += steps
        } else if (action == "S") {
            y -= steps
        } else if (action == "E") {
            x += steps
        } else if (action == "W") {
            x -= steps
        } else if (action == "L") {
            var tx = 0
            var ty = 0
            when (steps) {
                90 -> {
                    tx = -y; ty = x
                }
                180 -> {
                    tx = -x; ty = -y
                }
                270 -> {
                    tx = y; ty = -x
                }
            }
            x = tx
            y = ty
        } else if (action == "R") {
            var tx = 0
            var ty = 0
            when (steps) {
                90 -> {
                    tx = y; ty = -x
                }
                180 -> {
                    tx = -x; ty = -y
                }
                270 -> {
                    tx = -y; ty = x
                }
            }
            x = tx
            y = ty
        } else if (action == "F") {
            xs += steps * x
            ys += steps * y
        }

    }
    return abs(xs) + abs(ys)
}

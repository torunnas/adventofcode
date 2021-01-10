package main.kotlin.aoc2020

import java.io.File

fun main() {
    var start = System.currentTimeMillis()
    var list = mutableListOf<String>()
    File("input2020/input17.txt").forEachLine { list.add(it) }
    var grid = mutableMapOf<Int, MutableMap<Int,List<String>>>()
    var g = mutableMapOf<Int, List<String>>()
    g[0] =pad(list)
    g[-1] = getInactiveList(g[0]!!.size, g[0]!![0].length)
    g[1] = getInactiveList(g[0]!!.size, g[0]!![0].length)
    grid[0] = g
    grid[-1] = getInactiveGrid(grid[0]!!.keys.min()!!..grid[0]!!.keys.max()!!,grid[0]!![0]!!.size, grid[0]!![0]!![0].length)
    grid[1] = getInactiveGrid(grid[0]!!.keys.min()!!..grid[0]!!.keys.max()!!,grid[0]!![0]!!.size, grid[0]!![0]!![0].length)
    for (i in 1..6) {
        var new = mutableMapOf<Int, MutableMap<Int,List<String>>>()
        for(w in grid.keys) {
            var newZ = mutableMapOf<Int, List<String>>()
            for (z in grid[w]!!.keys) {
                var l = mutableListOf<String>()
                for (y in grid[w]!![z]!!.indices) {
                    var s = StringBuilder()
                    for (x in grid[w]!![z]!![y].indices) {
                        val active = active(x, y, z, w, grid)
                        if (grid[w]!![z]!![y][x] == '#') {
                            if (active in 2..3) {
                                s.append('#')
                            } else {
                                s.append('.')
                            }

                        } else if (grid[w]!![z]!![y][x] == '.') {
                            if (active == 3) {
                                s.append('#')

                            } else {
                                s.append('.')
                            }
                        }
                    }
                    l.add(s.toString())
                }
                newZ[z] = l
            }
            new[w]=newZ
        }
        grid = pad4Grid(new)
    }
    var res = 0
    for (a in grid.keys) {
        for(b in grid[a]!!.keys){
            res += grid[a]!![b]!!.sumBy { it.count { it == '#' } }
        }

    }
    println(res)
    println("${System.currentTimeMillis() - start}ms")
}

fun pad4Grid(grid: MutableMap<Int, MutableMap<Int, List<String>>>): MutableMap<Int, MutableMap<Int, List<String>>> {
    var min = grid.keys.min()!!
    var max = grid.keys.max()!!
    var new = mutableMapOf<Int, MutableMap<Int, List<String>>>()
    for (k in grid.keys) {
        new[k] = padGrid(grid[k]!!)
    }
    new[min - 1] =
        getInactiveGrid(new[0]!!.keys.min()!!..new[0]!!.keys.max()!!, new[0]!![0]!!.size, new[0]!![0]!![0].length)
    new[max + 1] =
        getInactiveGrid(new[0]!!.keys.min()!!..new[0]!!.keys.max()!!, new[0]!![0]!!.size, new[0]!![0]!![0].length)
    return new
}


fun padGrid(grid: MutableMap<Int, List<String>>): MutableMap<Int, List<String>> {
    var min = grid.keys.min()!!
    var max = grid.keys.max()!!
    var new = mutableMapOf<Int, List<String>>()
    for (k in grid.keys) {
        new[k] = pad(grid[k]!!.toMutableList())
    }
    new[min - 1] = getInactiveList(new[0]!!.size, new[0]!![0].length)
    new[max + 1] = getInactiveList(new[0]!!.size, new[0]!![0].length)
    return new

}

fun pad(list: MutableList<String>): List<String> {
    var new = mutableListOf<String>()
    new.add("".padEnd(list[0].length + 2, '.'))
    for (l in list) {
        var s = l.padStart(l.length + 1, '.')
        s = s.padEnd(s.length + 1, '.')
        new.add(s)
    }
    new.add("".padEnd(list[0].length + 2, '.'))
    return new
}

fun getInactiveGrid(size: IntRange, size1: Int, length: Int): MutableMap<Int, List<String>> {
    var new = mutableMapOf<Int, List<String>>()
    for (a in size) {
        new[a] = getInactiveList(size1, length)
    }
    return new
}

fun getInactiveList(size: Int, length: Int): List<String> {
    var list = mutableListOf<String>()
    for (i in 1..size) {
        var s = StringBuilder()
        for (j in 1..length) {
            s.append('.')
        }
        list.add(s.toString())
    }

    return list

}

private fun active(x: Int, y: Int, z: Int, w: Int, grid: MutableMap<Int, MutableMap<Int, List<String>>>): Int {
    val dirs =
        listOf(
            Pair(0, 1),
            Pair(0, -1),
            Pair(1, 1),
            Pair(1, 0),
            Pair(-1, 0),
            Pair(-1, -1),
            Pair(-1, 1),
            Pair(1, -1),
            Pair(0, 0)
        )
    var sum = 0
    for (k in dirs) {
        for (a in -1..1) {
            for (b in -1..1) {
                if (b == 0 && a == 0 && k.second == 0 && k.first == 0) {
                    continue
                }

                if (grid[w + b] != null && grid[w + b]!![z + a] != null && y + k.second in grid[w + b]!![z + a]!!.indices &&
                    x + k.first in grid[w + b]!![z + a]!![y + k.second].indices && grid[w + b]!![z + a]!![y + k.second][x + k.first] == '#'
                ) {
                    sum++
                }
            }
        }
    }
    return sum
}

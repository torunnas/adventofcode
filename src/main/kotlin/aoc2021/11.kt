import java.io.File

fun main() {
    val lines = File("input2021/input11.txt").readLines()
    val board = getBoard(lines)
    var count = 0
    for (step in 1..10000) {
        val toBeFlashed = mutableListOf<Pair<Int, Int>>()
        val flashed = mutableListOf<Pair<Int, Int>>()
        for (i in board.indices) {
            for (j in board[0].indices) {
                if (board[i][j] != 9) {
                    board[i][j] = board[i][j] + 1
                } else {
                    toBeFlashed.add(Pair(i, j))
                }
            }
        }
        while (toBeFlashed.isNotEmpty()) {
            val coordinate = toBeFlashed.removeFirst()
            flashed.add(coordinate)
            val i = coordinate.first
            val j = coordinate.second
            for (di in -1..1) {
                for (dj in -1..1) {
                    val ni = i + di
                    val nj = j + dj
                    if ((di == 0 && dj == 0) || ni < 0 || nj < 0 || ni > 9 || nj > 9) continue
                    if (board[ni][nj] != 9) {
                        board[ni][nj] = board[ni][nj] + 1
                    } else if (!flashed.contains(Pair(ni, nj)) && !toBeFlashed.contains(Pair(ni, nj))) {
                        toBeFlashed.add(Pair(ni, nj))
                    }
                }
            }
            count++
        }
        for (flash in flashed) {
            board[flash.first][flash.second] = 0
        }
        if (flashed.size == 100) {
            println("part2: $step")
            break
        }
        if (step == 100) {
            println("part1: $count")
        }
    }
}

fun getBoard(lines: List<String>): Array<IntArray> {
    val board = Array(lines.size) { IntArray(lines[0].length) }
    for ((i, line) in lines.withIndex()) {
        val row = IntArray(10)
        for (j in line.indices) {
            row[j] = line[j].toString().toInt()
        }
        board[i] = row
    }
    return board
}



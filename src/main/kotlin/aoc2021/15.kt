import java.io.File

fun main() {

    val lines = File("input2021/input15.txt").readLines()
    val board1 = getBoardPart1(lines)
    val board2 = getBoardPart2(board1, 5)
    println("Part1: ${travel(board1)}")
    println("Part2: ${travel(board2)}")
}

private fun travel(board: Array<IntArray>): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()
    val distances = mutableMapOf<Pair<Int, Int>, Int>()
    var x = 0
    var y = 0
    var minDistance = 0
    distances[Pair(0, 0)] = 0
    while (true) {
        for ((dx, dy) in listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)) {
            if (x + dx !in board.indices || y + dy !in board.indices || visited.contains(Pair(x + dx, y + dy))) continue
            val current = distances.getOrDefault(Pair(x + dx, y + dy), Int.MAX_VALUE)
            val newDistance = distances.getValue(Pair(x, y)) + board[x + dx][y + dy]
            if (current > newDistance) {
                distances[Pair(x + dx, y + dy)] = newDistance
            }
        }
        visited.add(Pair(x, y))
        distances.remove(Pair(x, y))
        val min = distances.minByOrNull { it.value }
        x = min!!.key.first
        y = min.key.second
        minDistance = min.value

        if (x == board.lastIndex && y == board[0].lastIndex) break
    }
    return minDistance
}

private fun getBoardPart2(board: Array<IntArray>, factor: Int): Array<IntArray> {
    val newBoard = Array(board.size * factor) { IntArray(board.size * factor) }
    for (k in 0 until factor) {
        for (a in board.indices) {
            val row = IntArray(board.size * factor)
            for (i in 0 until factor) {
                for (b in board[a].indices) {
                    row[i * board.size + b] = if ((board[a][b] + i + k) == 9) 9 else (board[a][b] + i + k) % 9
                }

            }
            newBoard[k * board.size + a] = row
        }
    }
    return newBoard
}

private fun getBoardPart1(lines: List<String>): Array<IntArray> {
    val board = Array(lines.size) { IntArray(lines[0].length) }
    for ((i, line) in lines.withIndex()) {
        val row = IntArray(line.length)
        for (j in line.indices) {
            row[j] = line[j].toString().toInt()
        }
        board[i] = row
    }
    return board
}
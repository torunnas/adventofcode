import java.io.File

fun main() {
    val lines = File("input2021/input9.txt").readLines()
    val board = getBoard(lines)
    var sum = 0
    val lowPoints = mutableListOf<Pair<Int, Int>>()
    for (i in board.indices) {
        for (j in board[i].indices) {
            val point = board[i][j]
            var lowPoint = true
            for ((di, dj) in listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)) {
                if (i + di !in board.indices || j + dj !in board[0].indices) continue
                if (board[i][j] >= board[i + di][j + dj]) {
                    lowPoint = false
                    break
                }
            }
            if (lowPoint) {
                sum += point + 1
                lowPoints.add(Pair(i, j))
            }
        }
    }
    val map = mutableMapOf<Pair<Int, Int>, Long>()
    for (point in lowPoints) {
        val basin = search(point, board)
        map[point] = basin.size.toLong()
    }
    val productOfLargestBasins = map.values.sorted().takeLast(3).reduce { acc, l -> acc * l }
    println("Part1: $sum")
    println("Part2: $productOfLargestBasins")
}


private fun search(point: Pair<Int, Int>, board: Array<IntArray>): Set<Pair<Int, Int>> {
    val i = point.first
    val j = point.second
    val result = mutableSetOf(Pair(i, j))
    for ((di, dj) in listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)) {
        if (i + di !in board.indices || j + dj !in board[0].indices) continue
        if (board[i][j] < board[i + di][j + dj] && board[i + di][j + dj] < 9) {
            result += search(Pair(i + di, j + dj), board)
        }
    }
    return result
}

private fun getBoard(lines: List<String>): Array<IntArray> {
    val board = Array(100) { IntArray(100) }
    for ((rowIndex, line) in lines.withIndex()) {
        val row = IntArray(100)
        for (a in line.indices) {
            row[a] = line[a].toString().toInt()
        }
        board[rowIndex] = row
    }
    return board
}

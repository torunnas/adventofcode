import java.io.File

fun main() {
    val lines = File("input2021/input4.txt").readLines()
    val numbersDrawn = lines[0].split(",").map { it.toInt() }
    val boards = mutableListOf<Array<IntArray>>()
    var board = Array(5) { IntArray(5) }
    var row= 0
    for (line in lines.drop(2)) {
        if(line.isEmpty()){
            boards.add(board)
            board = Array(5) { IntArray(5) }
            row=0
        }
        else{
            board[row] = line.split(" ").filter { it.isNotEmpty() }.map { it.trim().toInt() }.toIntArray()
            row++
        }
    }

    val winningBoards = mutableSetOf<Array<IntArray>>()
    for (n in 5..numbersDrawn.size) {
        val numbers = numbersDrawn.take(n)
        for (board in boards) {
            if (bingo(board , numbers) && !winningBoards.contains(board )) {
                winningBoards.add(board )
                val unMarkedSum = board .flatMap { it.toList() }.filter { it !in numbers }.sum()
                if (winningBoards.size == 1) {
                    println("Part1: ${unMarkedSum * numbers.last()}")
                } else if (winningBoards.size == boards.size) {
                    println("Part2: ${unMarkedSum * numbers.last()}")
                }
            }
        }
    }
}

fun bingo(board: Array<IntArray>, numbers: List<Int>): Boolean {
    for (i in 0..4) {
        if (numbers.containsAll(board[i].toSet())) {
            return true
        }
        if (numbers.containsAll(IntArray(board.size) { board[it][i] }.toSet())) {
            return true
        }
    }
    return false
}

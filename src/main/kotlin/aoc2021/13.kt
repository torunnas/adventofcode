import java.io.File

fun main() {
    val lines = File("input2021/input13.txt").readLines()
    var points = mutableSetOf<Pair<Int, Int>>()
    var folds = mutableListOf<Pair<String, Int>>()
    for (line in lines) {
        if (line.isEmpty()) continue
        if (line.startsWith("fold")) {
            val split = line.split(" ").last().split("=")
            folds.add(Pair(split[0], split[1].toInt()))
        } else {
            val split = line.split(",")
            points.add(Pair(split[0].trim().toInt(), split[1].trim().toInt()))
        }
    }

    for (fold in folds) {
        val newPoints = mutableSetOf<Pair<Int, Int>>()
        val foldValue = fold.second
        if (fold.first == "x") {
            points.forEach {
                if (it.first < foldValue) newPoints.add(it)
                else if (it.first > foldValue) newPoints.add(Pair(foldValue - (it.first - foldValue), it.second))
            }
        }
        if (fold.first == "y") {
            points.forEach {
                if (it.second < foldValue) newPoints.add(it)
                else if (it.second > foldValue) newPoints.add(Pair(it.first, foldValue - (it.second - foldValue)))
            }
        }
        points = newPoints
        if(foldValue == 655){
            println("part1: ${points.size}")
        }
    }
    println("part2: ")
    for (i in 0..points.maxOf { it.second }) {
        for (j in 0..points.maxOf { it.first }) {
            if (points.contains(Pair(j, i))) {
                print(1)
            } else {
                print(0)
            }
        }
        println()
    }


}
import java.io.File

fun main() {
    val lines = File("input2022/input4.txt").readLines()
    var part1 = 0
    var part2 = 0
    for (a in lines) {
        val split = a.split(",")
        val first = split[0].split("-")[0].toInt()..split[0].split("-")[1].toInt()
        val second = split[1].split("-")[0].toInt()..split[1].split("-")[1].toInt()
        val intersect = first.intersect(second)
        if (intersect == first.toSet() || intersect == second.toSet()) {
            part1++
        }
        if(first.intersect(second).isNotEmpty()){
            part2++
        }
    }
    println("part1: $part1")
    println("part2: $part2")
}
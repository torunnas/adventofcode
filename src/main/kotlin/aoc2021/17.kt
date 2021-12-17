import java.io.File

fun main() {
    //target area: x=128..160, y=-142..-88
    val lines = File("input2021/input17.txt").readLines()
    val xRange = 128..160
    val yRange = -142..-88
    var maxY = 0
    var count = 0
    for (i in -1000..1000) {
        for (j in -1000..1000) {
            var localMaxY = 0
            var dx = i
            var dy = j
            var x = 0
            var y = 0
            while (x < xRange.last && y > yRange.first) {
                x += dx
                y += dy
                if (dx > 0) dx-- else if (dx < 0) dx++
                dy--
                if (y > localMaxY) localMaxY = y
                if (x in xRange && y in yRange) {
                    count++
                    if (localMaxY > maxY) maxY = localMaxY
                    break
                }
            }
        }
    }
    println("Part1: $maxY")
    println("Part2: $count")

}
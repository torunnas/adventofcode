import java.io.File

fun main() {
    val list = File("input2021/input1.txt").readLines().map { it.toInt() }
    println("part1: ${countIncreases(list, 1)}")
    println("part2: ${countIncreases(list, 3)}")


}

fun countIncreases(numbers: List<Int>, windowSize: Int): Int {
    val windowSums = numbers.windowed(windowSize, 1).map { it.sum() }
    return windowSums.filterIndexed { index, sum -> index > 0 && sum > windowSums[index - 1] }.count()

}
import java.io.File

fun main() {
    val lines = File("input2022/input1.txt").readLines()
    val caloriesPerElf = mutableListOf<Int>()
    var sum = 0
    for(line in lines){
        if(line.isEmpty()){
            caloriesPerElf.add(sum)
            sum = 0
        }
        else sum += line.toInt()
    }
    caloriesPerElf.sort()
    println("part 1: ${caloriesPerElf.takeLast(1).sum()}")
    println("part 2: ${caloriesPerElf.takeLast(3).sum()}")
}
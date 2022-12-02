import java.io.File

fun main() {
    val lines = File("input2022/input2.txt").readLines()
    var part1 = 0
    var part2 = 0
    for (a in lines) {
        val elf = a.first()
        val me = a.last()
        part1 += getShapeValue(me)
        part1 += getScoreValue(elf, me)

        val chosenShape = getShape(elf, me)
        part2 += getShapeValue(chosenShape)
        part2 += getScoreValue(elf, chosenShape)
    }
    println(part1)
    println(part2)
}

fun getShapeValue(c: Char): Int {
    return when (c) {
        'A', 'X' -> 1
        'B', 'Y' -> 2
        'C', 'Z' -> 3
        else -> 0
    }
}

fun getScoreValue(elf: Char, me: Char): Int{
    val newMe = when(me){
        'X' -> 'A'
        'Y' -> 'B'
        'Z' -> 'C'
        else -> me
    }
    if(elf == newMe) return 3
    else if(elf == 'A' && newMe == 'B') return 6
    else if(elf == 'B' && newMe == 'C') return 6
    else if(elf == 'C' && newMe == 'A') return 6
    return 0
}

fun getShape(elf: Char, result: Char):Char{
    if(result == 'Y') return elf
    else if(elf == 'A' && result == 'X') return 'C'
    else if(elf == 'A' && result == 'Z') return 'B'
    else if(elf == 'B' && result == 'X') return 'A'
    else if(elf == 'B' && result == 'Z') return 'C'
    else if(elf == 'C' && result == 'X') return 'B'
    else if(elf == 'C' && result == 'Z') return 'A'
    return elf
}
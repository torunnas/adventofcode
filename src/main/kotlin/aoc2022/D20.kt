import java.io.File
import java.math.BigInteger

fun main() {
    val lines = File("input2022/input20.txt").readLines().map { it.trim().toBigInteger() }.toList()
    println("part1: ${doStuff(lines, BigInteger.ONE,1)}")
    println("part2: ${doStuff(lines, "811589153".toBigInteger(),10)}")

}

private fun doStuff(lines: List<BigInteger>, key: BigInteger, rounds: Int):BigInteger {
    val copy = lines.mapIndexed{index, b -> b.times(key) to index }.toMutableList()
    for(j in 1..rounds) {
        for (a in lines.mapIndexed{index, b -> b.times(key) to index }) {
            val i = copy.indexOf(a)
            copy.remove(a)
            var newi: BigInteger
            var move = a.first % copy.size.toBigInteger()
            if (i.toBigInteger() + move < BigInteger.ZERO) newi = i.toBigInteger() + move + copy.size.toBigInteger()
            else if (i.toBigInteger() + move > copy.size.toBigInteger()) newi = i.toBigInteger() + move - copy.size.toBigInteger()
            else newi = i.toBigInteger() + move
            copy.add(newi.toInt(), a)
        }
    }
    val i = copy.indexOfFirst { it.first== BigInteger.ZERO }
    val a = copy.elementAt((i + 1000) % lines.size).first
    val b = copy.elementAt((i + 2000) % lines.size).first
    val c = copy.elementAt((i + 3000) % lines.size).first
    return(a + b + c)

}

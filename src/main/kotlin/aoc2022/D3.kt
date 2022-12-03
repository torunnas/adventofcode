import java.io.File

fun main() {
    val lines = File("input2022/input3.txt").readLines()
    val part1Chunks =
        lines
            .map {
                val half = it.length / 2
                listOf(it.take(half).toSet(), it.takeLast(half).toSet())
            }
    println("part1: ${solve(part1Chunks)}")

    val part2Chunks = lines
        .map { it.toSet() }.chunked(3)
    println("part2: ${solve(part2Chunks)}")
}

private fun solve(chunks: List<List<Set<Char>>>): Int {
    return chunks
        .map { it.reduce { acc, s -> acc.intersect(s) }.first() }
        .sumOf { getPriority(it) }
}

private fun getPriority(item: Char) =
    if (item.isLowerCase()) item.code - 'a'.code + 1 else item.code - 'A'.code + 27

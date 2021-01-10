package main.kotlin.aoc2020

import main.kotlin.aoc2020.Operation.*
import java.io.File

fun main() {
    var list = mutableListOf<String>()
    File("input2020/input8.txt").forEachLine { list.add(it) }
    println("part1: ${part1(list)}")
    println("part2: ${part2(list)}")
}

private fun part1(instructions: List<String>): Long {
    return accumulate(instructions, false)
}

private fun part2(list: List<String>): Long {
    for (i in list.indices) {
        var instructions = switchInstruction(list, i)
        val result = accumulate(instructions, true)
        if (result > 0) {
            return result
        }
    }
    return 0L
}

fun accumulate(instructions: List<String>, terminate: Boolean): Long {
    var visited = mutableSetOf<Int>()
    var accumulator = 0L
    var i = 0
    while (i < instructions.size && visited.add(i)) {
        val instruction = getInstruction(instructions[i])
        when (instruction.op) {
            ACC -> {
                accumulator += instruction.arg
                i++
            }
            JMP -> i += instruction.arg
            NOP -> i++
        }
    }
    if (terminate && i < instructions.size) {
        return 0L
    }
    return accumulator
}

fun switchInstruction(list: List<String>, i: Int): List<String> {
    val instruction = getInstruction(list[i])
    var instructions = list.toMutableList()
    instructions[i] = instruction.flip().toString()
    return instructions
}

fun getInstruction(s: String): Instruction {
    val split = s.split(" ")
    val operation = Operation.getOperationByName(split[0].trim())
    val arg = split[1].trim().toInt()
    return Instruction(operation, arg)
}

data class Instruction(val op: Operation, val arg: Int) {
    override fun toString() = "${op.code} $arg"
    fun flip() =
        when (op) {
            Operation.JMP -> Instruction(Operation.NOP, arg)
            Operation.NOP -> Instruction(Operation.JMP, arg)
            else -> this
        }
}

enum class Operation(val code: String) {
    ACC("acc"),
    JMP("jmp"),
    NOP("nop");

    companion object {
        fun getOperationByName(name: String) = values().find { it.code == name }!!
    }
}




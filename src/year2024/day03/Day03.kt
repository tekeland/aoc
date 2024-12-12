package year2024.day03

import getPath
import println
import readInput
import readTestInput

val regex = Regex(pattern = """mul\((\d+),(\d+)\)""")

fun main() {
    fun part1(input: List<String>): Int = input.joinToString().compute()

    fun part2(input: List<String>): Int =
        input.joinToString()
            .split("do()")
            .map { it.split("don't()")[0].compute() }
            .reduce { acc, line -> acc + line }

    val path = getPath {}
    val testInput = readTestInput(path)
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    val input = readInput(path)
    part1(input).println()
    part2(input).println()
}

private fun String.compute(): Int =
    regex.findAll(this)
        .map { result -> result.groupValues[1].toInt() * result.groupValues[2].toInt() }
        .reduce { acc, value -> acc + value }

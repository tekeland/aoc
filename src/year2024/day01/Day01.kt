package year2024.day01

import getPath
import println
import readInput
import readTestInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val (left, right) = parseInput(input)
        val distances = left.zip(right).map { abs(it.first - it.second) }

        return distances.sum()
    }

    fun part2(input: List<String>): Int {
        val (left, right) = parseInput(input)
        var score = 0
        left.forEach { leftNumber -> score += right.count(leftNumber::equals) * leftNumber }

        return score
    }

    val path = getPath {}
    val testInput = readTestInput(path)
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput(path)
    part1(input).println()
    part2(input).println()
}

private fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
    val left = mutableListOf<Int>()
    val right = mutableListOf<Int>()
    input.map { line ->
        val (leftPart, rightPart) = line.split("   ")
        left.add(leftPart.toInt())
        right.add(rightPart.toInt())
    }

    left.sort()
    right.sort()

    return left to right
}

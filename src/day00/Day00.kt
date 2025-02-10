package day00

import getPath
import println
import readInput
import readTestInput

/**
 * Template file
 */
fun main() {

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val path = getPath {}
    val testInput = readTestInput(path)
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)

    val input = readInput(path)
    part1(input).println()
    part2(input).println()
}
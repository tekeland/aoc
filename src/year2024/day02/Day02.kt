package year2024.day02

import println
import readInput
import readTestInput

fun main() {
    fun part1(input: List<String>): Int {
        var safeReportsCount = 0
        input.forEach { report ->
            val reportNumbers = report.split(" ").map(String::toInt).asSequence()
            if (isSafeReport(reportNumbers)) safeReportsCount++
        }
        return safeReportsCount
    }

    fun part2(input: List<String>): Int {
        var safeReportsCount = 0
        input.forEach { report ->
            val reportNumbers = report.split(" ").map(String::toInt)
            if (isSafeReport(reportNumbers.asSequence())) {
                safeReportsCount++
            } else {
                reportNumbers.forEachIndexed { index, _ ->
                    val updatedReportNumbers = reportNumbers.toMutableList()
                    updatedReportNumbers.removeAt(index)
                    if (isSafeReport(updatedReportNumbers.asSequence())) {
                        safeReportsCount++
                        return@forEach
                    }
                }
            }
        }
        return safeReportsCount
    }

    val testInput = readTestInput("day02")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("day02")
    part1(input).println()
    part2(input).println()
}

private fun isSafeReport(reportNumbers: Sequence<Int>): Boolean {
    val differences = reportNumbers.zipWithNext { a, b -> b - a }
    return differences.all { it in 1..3 } || differences.all { it in -3..-1 }
}

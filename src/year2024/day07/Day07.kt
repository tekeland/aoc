package year2024.day07

import executeAndMeasureTime
import getPath
import readInput
import readTestInput

sealed interface Operation : (Long, Long) -> Long

data object Add : Operation {
    override fun invoke(p1: Long, p2: Long): Long = p1 + p2
}

data object Multiply : Operation {
    override fun invoke(p1: Long, p2: Long): Long = p1 * p2
}

data object Concatenate : Operation {
    override fun invoke(p1: Long, p2: Long): Long = "$p1$p2".toLong()
}

private fun test(
    testValue: Long,
    value: Long,
    operands: List<Long>,
    operators: List<Operation>,
): Boolean {
    if (operands.isEmpty()) return testValue == value
    if (value > testValue) return false

    val next = operands.first()
    val rest = operands.drop(1)

    return operators.any { operation ->
        test(testValue, operation(value, next), rest, operators)
    }
}

data class Record(val result: Long, val values: List<Long>) {
    companion object {
        fun fromString(input: String): Record {
            val parts = input.split(": ")
            return Record(parts[0].toLong(), parts[1].split(" ").map { it.toLong() })
        }
    }
}

fun main() {

    fun part1(input: List<String>): Long =
        input.map(Record::fromString)
            .filter { record -> test(record.result, 0L, record.values, listOf(Add, Multiply)) }
            .sumOf { it.result }

    fun part2(input: List<String>): Long =
        input.map(Record::fromString)
            .filter { record -> test(record.result, 0L, record.values, listOf(Add, Multiply, Concatenate)) }
            .sumOf { it.result }

    val path = getPath {}
    val testInput = readTestInput(path)
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput(path)
    executeAndMeasureTime({ part1(input).toString() })
    executeAndMeasureTime({ part2(input).toString() })
}
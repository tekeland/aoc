package year2024.day05

import println
import readInput
import readTestInput
import java.util.Collections

fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        val (rules, updates) = parseInput(input)

        updates.forEach { update ->
            if (isValidUpdate(rules, update)) {
                total += middleValue(update)
            }
        }

        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        val (rules, updates) = parseInput(input)

        updates.forEach { update ->
            if (!isValidUpdate(rules, update)) {
                val correctedUpdate = fixUpdate(update, rules)
                total += middleValue(correctedUpdate)
            }
        }

        return total
    }

    val testInput = readTestInput("day05")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("day05")
    part1(input).println()
    part2(input).println()
}

fun parseInput(input: List<String>): Pair<List<String>, List<String>> {
    val rules = input.takeWhile { it.isNotEmpty() }
    val updates = input.takeLastWhile { it.isNotEmpty() }

    return rules to updates
}

fun isValidUpdate(rules: List<String>, update: String) =
    rules.all { rule ->
        val (first, second) = rule.split('|')
        if (update.contains(first) && update.contains(second)) {
            update.indexOf(first) < update.indexOf(second)
        } else true
    }

fun middleValue(update: String): Int {
    val intUpdate = update.split(',').map { it.toInt() }
    return intUpdate[intUpdate.size / 2]
}

fun fixUpdate(update: String, rules: List<String>): String {
    var fixedUpdate = update
    val updateList = fixedUpdate.split(',').toMutableList()

    while (!isValidUpdate(rules, fixedUpdate)) {
        rules.forEach { rule ->
            val (first, second) = rule.split('|')
            val firstIndex = updateList.indexOf(first)
            val secondIndex = updateList.indexOf(second)

            if (firstIndex != -1 && secondIndex != -1 && firstIndex > secondIndex) {
                Collections.swap(updateList, firstIndex, secondIndex)
                fixedUpdate = updateList.joinToString(",")
            }
        }
    }
    return fixedUpdate
}
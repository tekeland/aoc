package year2024.day04

import getPath
import println
import readInput
import readTestInput
import removeSpaces

fun main() {
    fun part1(input: List<String>): Int {
        var result = count(input)

        val rotatedInput = rotateMatrix45Degrees(input)
        result += count(rotatedInput)

        val flippedInput = rotateMatrix90Degrees(input)
        result += count(flippedInput)

        val flippedRotatedInput = rotateMatrix45Degrees(flippedInput)
        result += count(flippedRotatedInput)

        return result
    }

    fun part2(input: List<String>): Int {
        var count = 0

        fun isValidPattern(r: Int, c: Int): Boolean =
            (input[r - 1][c - 1] == 'M' && input[r + 1][c + 1] == 'S' &&
                ((input[r - 1][c + 1] == 'M' && input[r + 1][c - 1] == 'S') ||
                    (input[r - 1][c + 1] == 'S' && input[r + 1][c - 1] == 'M'))) ||
                (input[r - 1][c - 1] == 'S' && input[r + 1][c + 1] == 'M' &&
                    ((input[r - 1][c + 1] == 'M' && input[r + 1][c - 1] == 'S') ||
                        (input[r - 1][c + 1] == 'S' && input[r + 1][c - 1] == 'M')))

        input.forEachIndexed { r, s ->
            if (r == 0 || r == input.size - 1) return@forEachIndexed
            s.forEachIndexed { c, ch ->
                if (c == 0 || c == s.length - 1) return@forEachIndexed
                if (ch == 'A' && isValidPattern(r, c)) count++
            }
        }
        count.println()
        return count
    }

    val path = getPath {}
    val testInput = readTestInput(path)
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput(path)
    part1(input).println()
    part2(input).println()
}

fun count(input: List<String>): Int {
    var count = 0
    input.forEach { row ->
        val string = row.removeSpaces()
        string.println()
        count += string.countXmas() + string.reversed().countXmas()
    }
    return count
}

fun rotateMatrix90Degrees(matrix: List<String>): List<String> {
    val n = matrix.size
    val rotatedMatrix = Array(n) { CharArray(n) }

    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            rotatedMatrix[j][n - 1 - i] = matrix[i][j]
        }
    }

    return rotatedMatrix.map { it.joinToString("") }
}

fun rotateMatrix45Degrees(matrix: List<String>): List<String> {
    val n = matrix.size
    val newSize = 2 * n - 1
    val rotatedMatrix = Array(newSize) { CharArray(newSize) { ' ' } }

    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            rotatedMatrix[i + j][n - 1 + i - j] = matrix[i][j]
        }
    }

    return rotatedMatrix.map { it.joinToString("") }
}

fun String.countXmas(): Int {
    return this.split("XMAS").size - 1
}
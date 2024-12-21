package year2024.day06

import getPath
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import println
import readInput
import readTestInput
import java.util.concurrent.atomic.AtomicInteger

data class Point(val x: Int, val y: Int)
data class Location(var direction: Direction, var point: Point) {
    fun turnRight() = copy(direction = direction.turnRight())
    fun getNext() = copy(point = point.copy(x = point.x + direction.dx, y = point.y + direction.dy))
}
enum class Direction(val dx: Int, val dy: Int) {
    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

    fun turnRight(): Direction =
        when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
}

fun main() {

    fun part1(input: List<String>): Int {
        val mapWidth = input[0].length
        val mapHeight = input.size
        lateinit var guardLocation: Location

        val obstacles = mutableListOf<Point>()
        val visited = mutableSetOf<Point>()
        input.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { charIndex, char ->
                when (char) {
                    '^' -> {
                        guardLocation = Location(Direction.UP, Point(charIndex, lineIndex))
                        visited.add(guardLocation.point)
                    }
                    '#' -> obstacles.add(Point(charIndex, lineIndex))
                }
            }
        }

        while (guardLocation.point.x in 0 until mapWidth - 1 && guardLocation.point.y in 0 until mapHeight - 1) {
            val nextPoint = Point(
                guardLocation.point.x + guardLocation.direction.dx,
                guardLocation.point.y + guardLocation.direction.dy
            )
            if (obstacles.contains(nextPoint)) {
                guardLocation = guardLocation.turnRight()
            } else {
                visited.add(nextPoint)
                guardLocation = Location(guardLocation.direction, nextPoint)
            }
        }
        return visited.count()
    }

    fun part2(input: List<String>): Int {
        val mapWidth = input[0].length
        val mapHeight = input.size

        val (initialGuardLocation, obstacles) = getInitialSetup(input)
        val possibleObstaclesCount = AtomicInteger(0)

        runBlocking(Dispatchers.Default) {
            input.forEachIndexed { lineIndex, line ->
                line.forEachIndexed { charIndex, char ->
                    launch {
                        val hasLoop =
                            findLoop(charIndex, lineIndex, initialGuardLocation, obstacles, mapWidth, mapHeight)
                        if (hasLoop) possibleObstaclesCount.incrementAndGet()
                    }
                }
            }
        }

        return possibleObstaclesCount.get()
    }

    val path = getPath {}
    val testInput = readTestInput(path)
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInput(path)
    part1(input).println()
    part2(input).println()
}

private fun getInitialSetup(input: List<String>): Pair<Location, MutableList<Point>> {
    lateinit var initialGuardLocation: Location
    val obstacles = mutableListOf<Point>()
    input.forEachIndexed { lineIndex, line ->
        line.forEachIndexed { charIndex, char ->
            when (char) {
                '^' -> {
                    initialGuardLocation = Location(Direction.UP, Point(charIndex, lineIndex))
                }
                '#' -> obstacles.add(Point(charIndex, lineIndex))
            }
        }
    }
    return initialGuardLocation to obstacles
}

private fun findLoop(
    charIndex: Int,
    lineIndex: Int,
    initialGuardLocation: Location,
    obstacles: MutableList<Point>,
    mapWidth: Int,
    mapHeight: Int,
): Boolean {
    val possibleObstacle = Point(charIndex, lineIndex)
    var guardLocation = initialGuardLocation
    val visited = mutableSetOf(initialGuardLocation)
    if (possibleObstacle !in obstacles && possibleObstacle != guardLocation.point) {
        while (guardLocation.point.x in 0 until mapWidth - 1 && guardLocation.point.y in 0 until mapHeight - 1) {
            val nextLocation = guardLocation.getNext()
            if (obstacles.contains(nextLocation.point) || possibleObstacle == nextLocation.point) {
                guardLocation = guardLocation.turnRight()
            } else {
                guardLocation = nextLocation
                if (visited.contains(guardLocation)) {
                    return true
                    break
                } else {
                    visited.add(guardLocation)
                }
            }
        }
    }

    return false
}
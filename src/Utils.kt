import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(day: String) = Path("src/$day/input.txt").readLines()

/**
 * Reads lines from the given input txt file.
 */
fun readTestInput(day: String) = Path("src/$day/test.txt").readLines()

/**
 * Returns the path of the current file (hacky way).
 */
inline fun getPath(noinline block: () -> Unit): String = block.javaClass.packageName.replace(".", "/")

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Remove spaces in string
 */
fun String.removeSpaces(): String {
    return this.replace("\\s".toRegex(), "")
}

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Rotate matrix 90 degrees.
 */
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

/**
 * Rotate matrix 45 degrees.
 */
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
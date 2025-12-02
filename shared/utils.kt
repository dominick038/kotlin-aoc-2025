@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package shared

import kotlinx.cinterop.*
import platform.posix.*
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import kotlinx.cinterop.toKString
import platform.posix.fclose
import platform.posix.fgets
import platform.posix.fopen

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> {
    val path = "resources/$name.txt"
    val file = fopen(path, "r") ?: error("Cannot open file: $path")
    try {
        val content = buildString {
            memScoped {
                val buffer = allocArray<ByteVar>(4096)
                while (fgets(buffer, 4096, file) != null) {
                    append(buffer.toKString())
                }
            }
        }
        return content.trim().lines()
    } finally {
        fclose(file)
    }
}

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

@OptIn(ExperimentalForeignApi::class)
fun readFile(path: String): String {
    val file = fopen(path, "r") ?: error("Cannot open file: $path")
    try {
        val buffer = ByteArray(4096)
        val result = StringBuilder()
        while (true) {
            val line = fgets(buffer.refTo(0), buffer.size, file)?.toKString() ?: break
            result.append(line)
        }
        return result.toString()
    } finally {
        fclose(file)
    }
}

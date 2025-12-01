@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package shared

import kotlinx.cinterop.*
import platform.posix.*

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

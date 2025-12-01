package day01

import shared.readInput
import kotlin.math.abs
import kotlin.math.floor

fun main() {
    val input = readInput("day01");
    var dialPos: Int = 50;
    var zeroCounts: Int = 0;
    var zeroPasses: Int = 0;
    
    for (line in input) {
        val direction = line[0];
        val number = line.substring(1).toInt();
        val oldPos = dialPos;
        
        if (direction == 'L') {
            dialPos = (dialPos - number) % 100
            zeroPasses += (oldPos - 1).floorDiv(100) - (oldPos - number).floorDiv(100)
        } else {
            dialPos = (dialPos + number) % 100
            zeroPasses += (oldPos + number - 1).floorDiv(100) - oldPos.floorDiv(100)
        }

        if (dialPos == 0) {
            zeroCounts += 1;
        }
    
    }

    println("Number of 0 counts: $zeroCounts");
    println("Number of 0 passes: $zeroPasses");
    println("Number of total zeroes: ${zeroCounts + zeroPasses}");
}

package day03

import shared.readInput;

fun main() {
    val input = readInput("day03");

    var result: Int = 0;
    for (line in input) {
        var largestNum = 0;
        var secondLargestNum = 0;
        var i = 0;
        line.forEach { char -> 
            val num = char.digitToInt();
            val isLastIndex = i == 99;

            var hasChanged = false;
            if (num > largestNum && !isLastIndex) {
                largestNum = num;
                secondLargestNum = 0;
                hasChanged = true;
            }

            if (!hasChanged && num > secondLargestNum) {
                secondLargestNum = num;
            } 
            
            i += 1; 
        }

        result += (largestNum.toString() + secondLargestNum.toString()).toInt();
    }

    println("Total joltage across banks???: $result");
}

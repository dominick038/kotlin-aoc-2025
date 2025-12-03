package day03

import shared.readInput;
import kotlin.text.toInt

fun main() {
    val input = readInput("day03");

    var result: Int = 0;
    var result2: Long = 0;
    for (line in input) {
        var i = 0;

        var largestNum = 0;
        var secondLargestNum = 0;
        
        val stack: ArrayDeque<Int> = ArrayDeque(128);
        var toRemove = line.length - 12;
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
            
            while (stack.isNotEmpty() &&
                   stack.first() < num &&
                   toRemove > 0) {
            
                stack.removeFirst();
                toRemove -= 1;
            }

            stack.addFirst(num)

            i += 1; 
        }

        result += (largestNum.toString() + secondLargestNum.toString()).toInt();
        result2 += stack.reversed().take(12).joinToString("").toLong();
    }

    println("Total joltage across 2  batteries: $result");
    println("Total joltage across 12 batteries: $result2");
}

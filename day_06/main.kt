package day06

import shared.readInput;
import kotlin.collections.mutableListOf
import day06.calculateResults

fun calculateResults(mathStackList: MutableList<MutableList<Int>>, line: String): Long {
    var columnIndex = 0;
    var totalResult: Long = 0;
    for (char in line) { 
        when (char) {
            '+' -> {
                val additionNumbers = mathStackList[columnIndex];     
                for (num in additionNumbers) {
                    totalResult += num;  
                }
                columnIndex++;
            }
            
            '*' -> {
                val multiplicationNumers = mathStackList[columnIndex];
                var mutliplicationResult: Long = multiplicationNumers[0].toLong();
                for (num in 1..multiplicationNumers.size - 1) {
                    mutliplicationResult = mutliplicationResult * multiplicationNumers[num];
                }
                totalResult += mutliplicationResult;
                columnIndex++;
            }
            
            else -> continue;
        }
    }
    
    return totalResult;
}

fun main() {
    val input = readInput("day06");
    
    val mathStackList = mutableListOf<MutableList<Int>>();
    var mathHomeworkResult: Long = 0;

    val sb = StringBuilder();
    val length = input.size - 1;
    for ((i, line) in input.withIndex()) {
        if (i == length) {
            mathHomeworkResult = calculateResults(mathStackList, line);
            break;
        }

        var columnIndex = 0;
        for (char in line) {

            if (char.isDigit()) {
                sb.append(char);
            }
            else if (sb.isNotEmpty()) {
                if (i == 0) {
                    val intArr = mutableListOf<Int>();
                    intArr.add(sb.toString().toInt());
                    mathStackList.add(intArr);
                }
                else {
                    mathStackList.get(columnIndex).add(sb.toString().toInt());
                }

                sb.clear();
                columnIndex++;
            }

        }

        if (sb.isNotEmpty()) {
            if (i == 0) {
                val intArr = mutableListOf<Int>();
                intArr.add(sb.toString().toInt());
                mathStackList.add(intArr);
            }
            else {
                mathStackList.get(columnIndex).add(sb.toString().toInt());
            }
            sb.clear();
        }
    }  

    println("Math homework result $mathHomeworkResult");
}

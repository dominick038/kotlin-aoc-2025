package day06

import shared.readInput;
import kotlin.collections.mutableListOf
import day06.calculateResults
import day06.calculateCephalopodResults

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

fun calculateCephalopodResults(digitsByXPosition: Map<Int, StringBuilder>, line: String): Long {
    var totalResult: Long = 0;

    val sortedXPositions = digitsByXPosition.keys.sorted();
    val maxX = sortedXPositions.last();

    var currentProblemNumbers = mutableListOf<Long>();
    var currentOperator: Char? = null;

    for (xPos in maxX downTo 0) {
        if (digitsByXPosition.containsKey(xPos)) {
            val verticalNumber = digitsByXPosition[xPos]!!.toString().toLong();
            currentProblemNumbers.add(verticalNumber);
        }

        if (xPos < line.length && (line[xPos] == '+' || line[xPos] == '*')) {
            if (currentProblemNumbers.isNotEmpty()) {
                val result = when (line[xPos]) {
                    '+' -> currentProblemNumbers.sum()
                    '*' -> currentProblemNumbers.reduce { acc, num -> acc * num }
                    else -> 0L
                }
                totalResult += result;
            }

            currentProblemNumbers.clear();
            currentOperator = line[xPos];
        }
    }

    if (currentProblemNumbers.isNotEmpty() && currentOperator != null) {
        val result = when (currentOperator) {
            '+' -> currentProblemNumbers.sum()
            '*' -> currentProblemNumbers.reduce { acc, num -> acc * num }
            else -> 0L
        }
        totalResult += result;
    }

    return totalResult;
}

fun main() {
    val input = readInput("day06");

    val mathStackList = mutableListOf<MutableList<Int>>();
    val digitsByXPosition = mutableMapOf<Int, StringBuilder>();

    var mathHomeworkResult: Long = 0;
    var cephalopodMathResult: Long = 0;

    val sb = StringBuilder();
    val length = input.size - 1;
    for ((i, line) in input.withIndex()) {
        if (i == length) {
            mathHomeworkResult = calculateResults(mathStackList, line);
            cephalopodMathResult = calculateCephalopodResults(digitsByXPosition, line);
            break;
        }

        var columnIndex = 0;
        for ((xPos, char) in line.withIndex()) {
            if (char.isDigit()) {
                digitsByXPosition.getOrPut(xPos) { StringBuilder() }.append(char);
            }

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

    println("Math homework result: $mathHomeworkResult");
    println("Cephalopod math result: $cephalopodMathResult");
}

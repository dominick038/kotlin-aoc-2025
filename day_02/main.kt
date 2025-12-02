package day02

import shared.readFile

fun parseMultiRep(lBound: Long, uBound: Long): Long {
    var result: Long = 0
    for (i in lBound..uBound) {
        val itemAsStr = i.toString()
        val lenItem = itemAsStr.length
        
        for (patternLen in 1..lenItem - 1) {
            if (lenItem % patternLen == 0) {
                var isRepeating = true
                
                for (j in 0..lenItem - 1) {
                    if (itemAsStr[j] != itemAsStr[j % patternLen]) {
                        isRepeating = false
                        break
                    }
                }
                
                if (isRepeating) {
                    result += i
                    break
                }
            }
        }
    }
    return result
}

fun parseSingleRep(lBound: Long, uBound: Long): Long {
    var result: Long = 0;
    for (i in lBound..uBound) {
        val itemAsStr = i.toString();

        val lenItem = itemAsStr.length;
        if ((lenItem % 2) != 0) continue;

        val middlePoint = lenItem.floorDiv(2);
        var L = 0;
        var R = middlePoint;

        while (true) {
            if (L == middlePoint) {
                result += i;
                break;
            }

            if (itemAsStr[L] != itemAsStr[R]) {
                break;
            }

            L += 1;
            R += 1;
        }
    }

    return result;
}

fun main() {
    val content = readFile("resources/day02.txt")
    var singleRepResult: Long = 0;
    var multiRepResult: Long = 0;

    val sb = StringBuilder();
    var lowerBound: Long = 0;
    var upperBound: Long;
    for (char in content) {
        if (char == ',' || char == '\n') {
            upperBound = sb.toString().toLong();
            sb.clear();
            singleRepResult += parseSingleRep(lowerBound, upperBound);
            multiRepResult  += parseMultiRep(lowerBound, upperBound);
            continue;
        }
        else if (char == '-') {
            lowerBound = sb.toString().toLong();
            sb.clear();
            continue;
        }

        sb.append(char);
    }

    println("Single repetition result: $singleRepResult");
    println("Multi repetition result: $multiRepResult");
}


package day05

import shared.readInput;

data class Range(val begin: Long, var end: Long) {}

fun isNumberInRange(targetNumber: Long, rangeList: MutableList<Range>): Boolean {
    var lowIndex  = 0;
    var highIndex = rangeList.size - 1;
    while (lowIndex <= highIndex) {
        val midIndex = (lowIndex + highIndex).floorDiv(2);
        val currentRange = rangeList[midIndex];

        if (targetNumber < currentRange.begin) {
            highIndex = midIndex - 1;
        }
        else if (targetNumber > currentRange.end) {
            lowIndex = midIndex + 1;
        }
        else {
            return true;
        }
    }

    return false;
}


fun main() {
    val input = readInput("day05");
    
    var freshIngredientCountResult = 0;
    var allFreshIngredientIdCountResultorsomethingidkaname: Long = 0;

    val ranges = mutableListOf<Range>();
    var emptySpaceIndex: Int = 0;
    for ((index, line) in input.withIndex()) {
        if (line == "") {
            emptySpaceIndex = index;
            break;
        }
        
        val beginEndArr = line.split('-');
        ranges.add(Range(beginEndArr[0].toLong(), beginEndArr[1].toLong()));
    }
    
    ranges.sortBy { it.begin };
    
    val mergedRanges = mutableListOf<Range>()
    for (range in ranges) {
        if (mergedRanges.isEmpty() || mergedRanges.last().end < range.begin) {
            mergedRanges.add(range)
        } else {
            mergedRanges.last().end = maxOf(mergedRanges.last().end, range.end)
        }
    }

    for (i in emptySpaceIndex+1..input.size-1) {
        val line = input[i];
        if (isNumberInRange(line.toLong(), mergedRanges)) {
            freshIngredientCountResult += 1;
        }
    }

    for (range in mergedRanges) {
        allFreshIngredientIdCountResultorsomethingidkaname += range.end - range.begin + 1;
    }

    println("Total fresh ingredient IDs: $freshIngredientCountResult");
    println("Total all fresnsdadhaksjds: $allFreshIngredientIdCountResultorsomethingidkaname");
}

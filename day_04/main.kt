package day04

import shared.readInput;

fun main() {
    val input = readInput("day04");
   
    var accessibleRollsOfPaper = 0;

    val hashOfPositions: HashSet<Pair<Int, Int>> = HashSet();
    for (i in 0..input.size - 1) {
        val line = input[i];
        for (j in 0..line.length - 1) {
            val char = line[j];
            if (char == '@') {
                hashOfPositions.add(Pair(j, i));
            }
        }
    }

    val directions = listOf(
       -1 to -1,  -1 to 0,  -1 to 1,
        0 to -1,             0 to 1,
        1 to -1,   1 to 0,   1 to 1
    );
    
    for ((x, y) in hashOfPositions) {
        var neighbours = 0;

        for ((dx, dy) in directions) {
            val nx = x + dx;
            val ny = y + dy;

            if (hashOfPositions.contains(Pair(nx, ny))) {
                neighbours += 1;            
            }
        }
        
        if (neighbours < 4) {
            accessibleRollsOfPaper += 1;
        }
    }

    println("Total amount of accesible rolls of paper: $accessibleRollsOfPaper");
}

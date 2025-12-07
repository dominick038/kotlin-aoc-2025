package day07

import shared.readInput;

data class Vector2D(var X: Int, var Y: Int) {}

fun main() {
    var totalSplitterHits: Int = 0;

    val input = readInput("day07");
    val inputLength = input.size - 1;
    val rowLength   = input[0].length;
    
    val positionOfS = input[0].indexOf('S');
    
    val visitedPositions: HashSet<Vector2D> = HashSet();
    val stack: ArrayDeque<Vector2D> = ArrayDeque(128);
    stack.addFirst(Vector2D(positionOfS, 0));
    
    while (stack.isNotEmpty()) {
        var currentBeam = stack.removeFirst();
        while (true) {
            currentBeam = currentBeam.copy(Y = currentBeam.Y + 1, X = currentBeam.X);
            if (currentBeam.Y > inputLength || visitedPositions.contains(currentBeam)) break;
            
            if (input[currentBeam.Y][currentBeam.X] == '.') {
                visitedPositions.add(currentBeam);
            } 
            else {
                val leftOfSplitter  = currentBeam.copy(Y = currentBeam.Y, X = currentBeam.X - 1);
                val rightOfSplitter = currentBeam.copy(Y = currentBeam.Y, X = currentBeam.X + 1);
                if (leftOfSplitter.X >= 0 && !visitedPositions.contains(leftOfSplitter)) {
                    stack.addFirst(leftOfSplitter);
                }

                if (rightOfSplitter.X < rowLength && !visitedPositions.contains(rightOfSplitter)) {
                    stack.addFirst(rightOfSplitter);
                }
                
                totalSplitterHits++;

                break;
            }
        }
    }


    println("Total splitter hits: $totalSplitterHits");
}


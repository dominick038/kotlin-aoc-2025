package day07

import shared.readInput;

data class Vector2D(var X: Int, var Y: Int) {}
data class BeamState(val X: Int, val Y: Int, val dX: Int, val dY: Int)

fun main() {
    var totalSplitterHits: Int = 0;

    val input = readInput("day07");
    val inputLength = input.size - 1;
    val rowLength   = input[0].length;

    val positionOfS = input[0].indexOf('S');

    val visitedPositions: HashSet<Vector2D> = HashSet();
    val pathCache: HashMap<BeamState, Long> = HashMap();

    fun countPaths(state: BeamState): Long {
        pathCache[state]?.let { return it }

        val newX = state.X + state.dX;
        val newY = state.Y + state.dY;

        if (newY > inputLength || newY < 0 || newX < 0 || newX >= rowLength) return 1;

        val cell = input[newY][newX];

        if (cell == '^') totalSplitterHits++;

        val newPos = Vector2D(newX, newY);
        if (!visitedPositions.contains(newPos)) visitedPositions.add(newPos);

        val result = if (cell == '.') {
            countPaths(BeamState(newX, newY, state.dX, state.dY))
        } else {
            var total = 0L;
            if (newX - 1 >= 0) total += countPaths(BeamState(newX - 1, newY, 0, 1));
            if (newX + 1 < rowLength) total += countPaths(BeamState(newX + 1, newY, 0, 1));
            total
        }

        pathCache[state] = result;
        return result;
    }

    val totalTimelines = countPaths(BeamState(positionOfS, 0, 0, 1));

    println("Total splitter hits: $totalSplitterHits");
    println("Total quantum timelines: $totalTimelines");
}


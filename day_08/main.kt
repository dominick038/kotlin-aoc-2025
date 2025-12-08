package day08

import shared.readInput;

fun Int.pow(exponent: Int): Long {
    var result: Long = 1;
    repeat(exponent) {
        result = result * this;
    }
    return result;
}

data class Vector3D(var X: Int, var Y: Int, var Z: Int) {
    fun squaredDistanceTo(targetVector: Vector3D): Long {
        return (this.X - targetVector.X).pow(2) + (this.Y - targetVector.Y).pow(2) + (this.Z - targetVector.Z).pow(2);
    }

    override fun toString(): String {
        return "${this.X}, ${this.Y}, ${this.Z}";
    }
}

data class distanceRecord(val distance: Long, val vectorAId: Int, val vectorBId: Int) {
    override fun toString(): String {
        return "distance: ${this.distance} id: ${this.vectorAId}, ${this.vectorBId}"
    }
};

class UnionFind(private val totalSize: Int) {
    private val parent = IntArray(totalSize) { it }
    private val size = IntArray(totalSize) { 1 }

    fun find(x: Int): Int {
        if (parent[x] != x) {
            parent[x] = find(parent[x])
        }
        return parent[x]
    }

    fun union(x: Int, y: Int): Boolean {
        val rootX = find(x)
        val rootY = find(y)

        if (rootX == rootY) return false

        parent[rootX] = rootY
        size[rootY] += size[rootX]
        return true
    }

    fun getUnionSizes(): Map<Int, Int> {
        val sizes = mutableMapOf<Int, Int>()
        for (i in parent.indices) {
            val root = find(i)
            sizes[root] = size[root]
        }
        return sizes
    }

    fun isFullyConnected(): Boolean {
        val root = find(0)
        return size[root] == totalSize
    }
}

fun main() {
    val input = readInput("day08");
    val inputSize = input.size;

    var largestUnionResult = 1;
    var lastConnectionProductResult: Long = 0;

    val listOfDistances = mutableListOf<distanceRecord>();
    val vectorArray = Array<Vector3D>(inputSize) { Vector3D(0, 0, 0) };
    
    val sb = StringBuilder();
    for ((index, line) in input.withIndex()) {
        val vector = vectorArray[index];
        
        for (char in line) {
            if (char != ',') {
                sb.append(char);
                continue;
            }
            
            if (vector.X == 0) { 
                vector.X = sb.toString().toInt();
            }
            else {
                vector.Y = sb.toString().toInt();
            }
            
            sb.clear();
        }
        
        vector.Z = sb.toString().toInt();
        sb.clear();
    }
    
    for ((i, vectorA) in vectorArray.withIndex()) {
        for (j in i+1..inputSize - 1) {
            val vectorB = vectorArray[j];
            
            val distanceRecord = distanceRecord(vectorA.squaredDistanceTo(vectorB), i, j);
            listOfDistances.add(distanceRecord);
        }
    }

    listOfDistances.sortBy { it.distance };

    val unionFind = UnionFind(inputSize);
    val connectionsToMake = if (inputSize == 20) 10 else 1000;

    for (i in 0 until minOf(connectionsToMake, listOfDistances.size)) {
        val distance = listOfDistances[i];
        unionFind.union(distance.vectorAId, distance.vectorBId);
    }

    val unionSizes = unionFind.getUnionSizes();
    val top3 = unionSizes.values.sortedDescending().take(3);
    for (size in top3) {
        largestUnionResult = largestUnionResult * size;
    }

    val unionFind2 = UnionFind(inputSize);
    for (distance in listOfDistances) {
        val wasUnified = unionFind2.union(distance.vectorAId, distance.vectorBId);

        if (wasUnified && unionFind2.isFullyConnected()) {
            val vectorA = vectorArray[distance.vectorAId];
            val vectorB = vectorArray[distance.vectorBId];
            lastConnectionProductResult = vectorA.X.toLong() * vectorB.X.toLong();
            break;
        }
    }

    println("Largest circuit union: $largestUnionResult");
    println("Last connection product: $lastConnectionProductResult");
}




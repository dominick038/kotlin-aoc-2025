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

fun main() {
    val input = readInput("day08");    
    val inputSize = input.size;

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
   
    for (distance in listOfDistances) {
    }
    println("total items in list ${listOfDistances.size}");
}




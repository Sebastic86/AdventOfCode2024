package day11

import readInput

fun main() {
    var input = readInput("day11/Day11")
    println(part1(input))
    println(part2(input))

}

fun part1(input: String): Int {
    var initialsStones = createStones(input)
    var count = 0
    repeat(25) {
        println(count++)
        initialsStones = blink(initialsStones)
    }

    return initialsStones.size
}

fun part2(input: String): Long {
    val cache = mutableMapOf<Pair<Long, Int>, Long>()

    fun count(stone: Long, iteration: Int): Long {
        if (iteration < 1) {
            return 1
        }

        cache[stone to iteration]?.let {
            return it
        }

        return stone.blink().sumOf {
            count(it, iteration - 1)
        }.also {
            cache[stone to iteration] = it
        }
    }

    return createStones(input).sumOf { count(it,75) }

}

fun blink(stones: List<Long>): List<Long>{
    return stones.flatMap { it.blink() }.toList()
}

fun createStones(input: String): List<Long> =
     input.split(" ").map { it.toLong() }

fun Long.blink(): List<Long>{
    if(this.toString().length % 2 == 0){
        return this.splitStone()
    }

    if(this == 0L){
        return listOf(1L)
    }

    return listOf(this * 2024)
}

fun Long.splitStone(): List<Long> =
    this.toString().splitInHalf().map { it.toLong() }.toList()

fun String.stripLeadingZeros(): String {
    return this.toInt().toString()
}

fun String.splitInHalf(): List<String> =
    listOf(this.substring(0,this.length / 2), this.substring(this.length / 2, this.length))

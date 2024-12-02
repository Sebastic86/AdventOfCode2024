package day2

import readInput

fun main() {
    var input = readInput("day2/Day02")
    println(part1(input))
    println(part2(input))
}


fun part1(input: List<String>): Int =
    input.count { isSafe(it) }

fun part2(input: List<String>): Int =
    input.count { isSafeWithTolerateBadLevel(it) }

fun isSafeWithTolerateBadLevel(input: String): Boolean {
    var levels = input.split(" ")
    var isSafe = isSafe(input)
    var index = 0
    while (index < levels.size && !isSafe) {
        isSafe = safeIncrease(removeIndexFromList(levels, index)) || safeDecrease(removeIndexFromList(levels, index))
        index++
    }
    return isSafe
}

fun removeIndexFromList(levels: List<String>, indexToFilter: Int): List<String> =
    levels.filterIndexed{
        index, _ -> index != indexToFilter
    }.toList()

fun isSafe(input : String) : Boolean =
    input
        .split(" ")
        .let{
            safeIncrease(it) || safeDecrease(it)
        }

fun safeDecrease(levels: List<String>) : Boolean =
    levels.filterIndexed {
            index, element ->
        if(index == levels.size - 1) true
        else element.toInt() - levels[index+1].toInt()  > 0 && element.toInt() - levels[index+1].toInt() < 4
    }
        .count() == levels.size

fun safeIncrease(levels: List<String>) : Boolean =
   levels.filterIndexed {
       index, element ->
       if(index == levels.size - 1) true
       else levels[index+1].toInt() - element.toInt() > 0 && levels[index+1].toInt() - element.toInt() < 4
   }
       .count() == levels.size



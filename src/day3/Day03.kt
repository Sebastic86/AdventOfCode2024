package day3

import readInput
import kotlin.math.max
import kotlin.math.min

fun main() {
    var input = readInput("day3/Day03")
    var resultPart1 = part1(input)
    var resultPart2 = part2(input)
    println("Part 1: $resultPart1")
    println("Part 2: $resultPart2")

}

fun part1(input: String): Int =
    "mul\\((\\d+),(\\d+)\\)"
        .toRegex().findAll(input)
        .map { match ->
            match.groupValues[1].toInt() * match.groupValues[2].toInt()
        }.sum()


fun part2(input: String): Int {
    var sum = 0
    var disableRegex = "don't\\(\\)".toRegex()
    var enabledRegex = "do\\(\\)".toRegex()
    var mulRegex = "mul\\((\\d+),(\\d+)\\)".toRegex()

    val disabledIndexes = disableRegex.findAll(input).map { it.range.start }.sorted().toSortedSet()
    val enabledIndexes = enabledRegex.findAll(input).map { it.range.start }.sorted().toSortedSet()

    var mulIndex = 0
    var match = mulRegex.find(input, mulIndex)

    while (match != null) {
        mulIndex = match.range.endInclusive
        if (isEnabledMatch(mulIndex, disabledIndexes, enabledIndexes)) {
            sum = sum + (match.groupValues[1].toInt() * match.groupValues[2].toInt())
        }
        match = mulRegex.find(input, mulIndex)
    }

    return sum
}

fun isEnabledMatch(mulIndex: Int, disabledIndexes: Set<Int>, enabledIndexes: Set<Int>): Boolean {

    val closestDisableIndex = disabledIndexes.filter { i -> i < mulIndex }.maxOrNull()
    val closestEnableIndex = enabledIndexes.filter { i -> i < mulIndex }.maxOrNull()

    return (closestEnableIndex ?: 1) > (closestDisableIndex ?: 0)
}







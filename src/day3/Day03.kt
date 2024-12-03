package day3

import day1.splitLists
import readInput

fun main() {
    var input = readInput("day3/Day03")
    var resultPart1 = part1(input)
    println("Part 1: $resultPart1")

}

fun part1(input: List<String>): Int =
    "mul\\((\\d+),(\\d+)\\)".toRegex().let{ regex ->
        input.sumOf {
            regex.findAll(it)
                .map {
                     match ->
                        match.groupValues[1].toInt() * match.groupValues[2].toInt()
                }.sum()

        }
    }








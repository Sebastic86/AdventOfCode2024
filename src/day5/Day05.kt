package day5

import readInput

fun main() {
    var input = readInput("day5/Day05")

    var splitOnEmptyLine = input.split("\n\n")
    var orderingRules = splitOnEmptyLine[0].split("\n")
        .map{
            it.split('|')
        }
        .toList()
        .groupBy({it[0].toInt()}, {it[1].toInt()})



        //.associate { it.split('|').let { it[0].toInt() to it[1].toInt() } }

    var updates = splitOnEmptyLine[1]
        .split("\n")
        .toList()
        .map {
            it.split(',')
                .map { it.toInt() }
                .toList()
        }.toList()

    var resultPart1 = part1(orderingRules, updates)
    println("Part 1: $resultPart1")

    var resultPart2 = part2(input)
    println("Part 2: $resultPart2")

}

fun part1(orderingRules: Map<Int, List<Int>>, updates: List<List<Int>>): Int {
    var result = 0
    updates.forEach {
        update ->
        if(isCorrectOrder(orderingRules, update)) {
            val middleNumber = findMiddleNumber(update)
            println("${update} has middle number: $middleNumber")
            result += middleNumber
        }
    }
    return result
}

fun findMiddleNumber(update: List<Int>) : Int{
    val middle = update.size / 2 + 1
    return update[middle-1]
}

fun isCorrectOrder(orderingRules: Map<Int, List<Int>>, update: List<Int>
): Boolean {
    println(" ")
    println(" ")
    println("checking update: $update")
    for (i in 0 until update.size) {
      var rules = orderingRules[update[i]]
        if(rules != null) {
            println("Found rules for ${update[i]} : $rules")
            for(j in rules.indices) {
                if(update.contains(rules[j]) && update.indexOf(rules[j]) < i ) {
                    println("update $update not valid because ${rules[j]} should be after ${update[i]} ")
                    return false
                }
            }
        }
    }
    return true
}

fun part2(matrix: String): Int {
    return 1
}







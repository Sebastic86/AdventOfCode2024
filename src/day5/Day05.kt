package day5

import readInput

fun main() {
    var input = readInput("day5/Day05")

    var splitOnEmptyLine = input.split("\n\n")
    var orderingRules = splitOnEmptyLine[0].split("\n")
        .map {
            it.split('|')
        }
        .toList()
        .groupBy({ it[0].toInt() }, { it[1].toInt() })


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

    var resultPart2 = part2(orderingRules, updates)
    println("Part 2: $resultPart2")

}

fun part1(orderingRules: Map<Int, List<Int>>, updates: List<List<Int>>): Int {
    var result = 0
    updates.forEach { update ->
        if (isCorrectOrder(orderingRules, update)) {
            val middleNumber = findMiddleNumber(update)
            result += middleNumber
        }
    }
    return result
}

fun part2(orderingRules: Map<Int, List<Int>>, updates: List<List<Int>>): Int {
    var result = 0
    updates.forEach { update ->
        if (!isCorrectOrder(orderingRules, update)) {
            var correctOrder = orderList(orderingRules, update)
            val middleNumber = findMiddleNumber(correctOrder)
            result += middleNumber
        }
    }
    return result
}

fun orderList(orderingRules: Map<Int, List<Int>>, update: List<Int>): List<Int> {
   return update.sortedWith(object: Comparator<Int> {
        override fun compare(first: Int, second: Int): Int {
            var rulesFirst = orderingRules[first]
            if(rulesFirst != null){
                if(rulesFirst.contains(second)) {
                    return -1
                }
            }
            var rulesSecond = orderingRules[second]
            if(rulesSecond != null){
                if(rulesSecond.contains(first)) {
                    return 1
                }
            }
            return 0
        }}).toList()
}


fun findMiddleNumber(update: List<Int>): Int {
    val middle = update.size / 2
    return update[middle]
}

fun isCorrectOrder(
    orderingRules: Map<Int, List<Int>>, update: List<Int>
): Boolean {
    for (i in 0 until update.size) {
        var rules = orderingRules[update[i]]
        if (rules != null) {
            for (j in rules.indices) {
                if (update.contains(rules[j]) && update.indexOf(rules[j]) < i) {
                    return false
                }
            }
        }
    }
    return true
}








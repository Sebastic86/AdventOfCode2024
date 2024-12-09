package day7

import readInput
import java.util.LinkedList
import java.util.Queue

fun main() {
    var input = readInput("day7/Day07").lines()
    var map = input
        .map { it.split(":") }
        .map { it[0].toLong() to it[1].trim().split(" ").map { it.toLong() } }

    println(part1And2(map))
}

fun part1And2(input: List<Pair<Long, List<Long>>>): Long =
    input.sumOf { pair ->
        testAllPaths(pair)
    }


fun testAllPaths(pair: Pair<Long, List<Long>>): Long {
    var total = pair.first
    var numbers = pair.second
    var index = 0

    var path = mutableListOf<Node>()

    process(Node(numbers[index]), index, numbers,path)

    if(path.any{ total == it.calculate()}){
        println("correct for ${total} and numbers $numbers")
        return total
    }

    return 0
}

fun process(current: Node, index: Int, numbers: List<Long>, path: MutableList<Node>) {
    if(index <= numbers.size - 2) {
        Operator.entries.forEach {  process(Node(numbers[index + 1], it, current), index + 1, numbers, path) }
    } else {
        path.add(current)
    }
}


//REMOVE CONCATENATE FOR PART 1
enum class Operator {
    PLUS, MULTIPLY, CONCATENATE
}

data class Node(val number: Long, val operator: Operator? = null, val next: Node? = null) {
    fun calculate() : Long {
        if(operator != null && next != null){
            return when(operator){
                Operator.PLUS -> number + next.calculate()
                Operator.MULTIPLY -> number * next.calculate()
                Operator.CONCATENATE -> "${next.calculate().toString()}${number.toString()}".toLong()
            }
        }
        return number
    }
}



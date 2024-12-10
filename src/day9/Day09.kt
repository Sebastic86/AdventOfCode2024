package day9

import readInput
import java.math.BigInteger

fun main() {
    var input = readInput("day9/Day09")
    println(part1(input))

}

fun part1(input: String): BigInteger {
    var result = ""
    var file = true
    var id: Long = 0

    println("Start transform diskmap")
    for(char in input) {
        var number = char.digitToInt()
        if(file){
            file = false
            result = result + ("("+id.toString()+")").repeat(number)
            id++
        } else{
            result = result + "(.)".repeat(number)
            file = true
        }
    }
    println("End transform diskmap")


    var digitRegex = "(\\d+)".toRegex()
    var dotRegex = "(\\.)".toRegex()

    var dotMatch = dotRegex.find(result)
    var lastDigitMatch = digitRegex.findAll(result).last()

    println("Start reorder result")

    var currentLoop: Long = 0

    while(dotMatch != null && dotMatch.range.start < lastDigitMatch.range.endInclusive){
        var lastDigitValue = lastDigitMatch.value
        result = result.replaceFirst(".", lastDigitValue)
        result = result.replaceLast(lastDigitValue, ".")

        dotMatch = dotRegex.find(result)
        lastDigitMatch = digitRegex.findAll(result).last()
        println("Loop: ${currentLoop++} - dotMatch: ${dotMatch?.range?.start} | lastDigitMatch: ${lastDigitMatch.range.start}")
    }

    println("End reorder result")

    println("Start calculate sum")


    var indexFirstFreeSpace = result.indexOfFirst { it == '.' }
    var sum: BigInteger = BigInteger.valueOf(0)
    var postition: Long = 0
    for(index in 0 until indexFirstFreeSpace){
        if(result[index] == '('){
            var digit = result.substring(index+1, result.indexOf(')', index))
            if(digit == "."){
                break;
            }
            //println("$sum + ${postition.toBigInteger()} * ${digit.toBigInteger()} = ${sum + (postition.toBigInteger() * digit.toBigInteger())}")
            sum = sum + (postition.toBigInteger() * digit.toBigInteger())


            postition++
        }

    }
    return sum
}


fun String.replaceLast(oldValue: String, newValue: String): String {
    val lastIndex = lastIndexOf(oldValue)
    if (lastIndex == -1) {
        return this
    }
    val prefix = substring(0, lastIndex)
    val suffix = substring(lastIndex + oldValue.length)
    return "$prefix$newValue$suffix"
}

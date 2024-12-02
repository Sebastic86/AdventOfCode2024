import kotlin.text.get

fun main() {

    var input = readInput("Day01")
    var resultPart1 = part1(input)
    var resultPart2 = part2(input)

    println(resultPart1)
    println(resultPart2)


}

fun part1(input: List<String>): Int {
    var splittedList = splitLists(input)
    var listOne = splittedList.first
    var listTwo = splittedList.second

    listOne.sort()
    listTwo.sort()

    var result = listOne.mapIndexed { index, element ->
        var max = listTwo[index].coerceAtLeast(element)
        var min = listTwo[index].coerceAtMost(element)
        max - min
    }.sum()

    return result
}

fun part2(input: List<String>): Int {
    var splittedList = splitLists(input)
    var listOne = splittedList.first
    var listTwo = splittedList.second

    return listOne.sumOf { element ->
        element * listTwo.count { element == it }
    }
}

fun splitLists(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
    var listOne = mutableListOf<Int>()
    var listTwo = mutableListOf<Int>()
    input.forEach { line ->
        val splitted = line.split("   ")

        if (splitted[0].isNotEmpty()) {
            listOne.add(splitted[0].toInt())
        }

        if (splitted[1].isNotEmpty()) {
            listTwo.add(splitted[1].toInt())
        }
    }
    return Pair(listOne, listTwo)
}


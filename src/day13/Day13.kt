package day13

import readInput
import java.util.LinkedList
import java.util.Queue

fun main() {
    var input = readInput("day13/Day13")
    println(part1(input))

}

fun part1(input: String): Int =
     convertInputToGames(input).sumOf { it.numberOfTokensForPrize() }

fun convertInputToGames(input: String) : List<Game> =
    input.split("\n\n")
        .map { part -> Game(part.lines()) }
        .toList()

class Game{
    val prize: Prize
    val buttonA: Button
    val buttonB: Button

    companion object {
        val BUTTON_REGEX = """X\+(\d+), Y\+(\d+)""".toRegex()
        val PRIZE_REGEX = """X=(\d+), Y=(\d+)""".toRegex()
    }

    constructor(input: List<String>) {
        val matchesButtonA = BUTTON_REGEX.find(input.get(0))
        val matchesButtonB = BUTTON_REGEX.find(input.get(1))
        val matchesPrize = PRIZE_REGEX.find(input.get(2))

        this.buttonA = Button(matchesButtonA?.groups[1]?.value?.toInt() ?: 1,matchesButtonA?.groups[2]?.value?.toInt() ?: 1, 3)
        this.buttonB = Button(matchesButtonB?.groups[1]?.value?.toInt() ?: 1,matchesButtonB?.groups[2]?.value?.toInt() ?: 1, 1)
        this.prize = Prize(matchesPrize?.groups[1]?.value?.toInt() ?: 1,matchesPrize?.groups[2]?.value?.toInt() ?: 1)
    }

    fun numberOfTokensForPrize() : Int {
        val b = (buttonA.x * prize.y - buttonA.y * prize.x) / (buttonA.x * buttonB.y - buttonA.y * buttonB.x)
        val a = (prize.y - buttonB.y * b) / buttonA.y
        val check = buttonA.y * a + buttonB.y * b == prize.y && buttonA.x * a + buttonB.x * b == prize.x
        return if (check) {
            a * 3 + b
        } else {
            0
        }
    }
}

data class Prize(val x: Int, val y: Int)

data class Button(val x: Int, val y: Int, val tokens: Int)

package day6

import readInput

fun main() {
    var input = readInput("day6/Day06")

    var sizeX = input.lines().first().length
    var sizeY = input.lines().size
    var startPoint = Point(0, 0, ' ', ' ')


    val map = Array(sizeY) { Array<Point>(sizeX) { Point(0, 0, ' ', ' ') } }
    input.lines().forEachIndexed { i, line ->
        line.forEachIndexed { j, c ->
            map[i][j] = Point(i, j, c, c)
            if (c == '^') {
                startPoint = map[i][j]
            }
            print(map[i][j].content)
        }
        println()
    }

    println("Starting on [${startPoint.x}, ${startPoint.y}]")


    var resultPart1 = part1(map, startPoint)
    println("Part 1: $resultPart1")

    var resultPart2 = part2(map, startPoint)
    println("Part 2: $resultPart2")

}

fun part1(map: Array<Array<Point>>, startPoint: Point): Int {
    var currentDirection = Motion.UP
    var currentPosition = startPoint
    var visitedPoints: MutableSet<Point> = mutableSetOf()

    while (nextStepIsValid(map, currentDirection, currentPosition)) {
        if (needsToTurn(map, currentDirection, currentPosition)) {
            currentDirection = getNextDirection(currentDirection)
            visitedPoints.add(currentPosition)
            currentPosition = getNextPosition(map, currentDirection, currentPosition)

        } else {
            visitedPoints.add(currentPosition)
            currentPosition = getNextPosition(map, currentDirection, currentPosition)
        }
    }



    return visitedPoints.size + 1
}

fun part2(map: Array<Array<Point>>, startPoint: Point): Int {
    var currentDirection = Motion.UP
    var currentPosition = startPoint
    var visitedPoints: MutableSet<Pair<Point, Motion>> = mutableSetOf()
    var possibleLoops = 0
    var turnMap: MutableMap<Int, Pair<Motion, Point>> = mutableMapOf()
    var turnNumber = 1

    while (nextStepIsValid(map, currentDirection, currentPosition)) {
        if (needsToTurn(map, currentDirection, currentPosition)) {
            turnMap.put(turnNumber, currentDirection to currentPosition)
            turnNumber++
            visitedPoints.add(currentPosition to currentDirection)
            currentDirection = getNextDirection(currentDirection)

            if(currentPosition.debugContent != '0'){
                currentPosition.debugContent = '+'
            }

            currentPosition = getNextPosition(map, currentDirection, currentPosition)
        } else {
            visitedPoints.add(currentPosition to currentDirection)
            if((currentDirection == Motion.UP || currentDirection == Motion.DOWN) && currentPosition.debugContent == '.') {
                currentPosition.debugContent = '|'
            }
            if((currentDirection == Motion.LEFT || currentDirection == Motion.RIGHT) && currentPosition.debugContent == '.') {
                currentPosition.debugContent = '-'
            }

            currentPosition = getNextPosition(map, currentDirection, currentPosition)
        }
        if (canCreateLoop(map, currentDirection, currentPosition, turnMap, visitedPoints)) {
            possibleLoops++
        }

    }

        for (i in 0 until map.size) {
        for (j in 0 until map[i].size) {
            print(map[i][j].debugContent)
        }
        println()
    }

    return possibleLoops
}

fun canCreateLoop(
    map: Array<Array<Point>>,
    currentDirection: Motion,
    currentPosition: Point,
    turnMap: MutableMap<Int, Pair<Motion, Point>>,
    visitedPoints: MutableSet<Pair<Point, Motion>>
): Boolean {
    var nextDirection = getNextDirection(currentDirection)
    var x = currentPosition.x
    var y = currentPosition.y
    var position = currentPosition
    var positionsOfTurns = turnMap.values.map{it.second}.toSet()
    var positionsToMotion = visitedPoints.groupBy({ it.first }, { it.second }).toMap()

    while( !(x < 0) && !(x >= map.size) && !(y < 0) && !(y >= map[0].size)) {
        if(!nextStepIsValid(map, nextDirection, map[x][y])){
            return false
        }
        position = getNextPosition(map, nextDirection, map[x][y])
        if(positionsOfTurns.contains(position) || positionsToMotion[position]?.contains(nextDirection) == true) {
            var blockadePoint = getNextPosition(map, currentDirection, currentPosition)
            blockadePoint.debugContent = '0'
            println("create loop by putting blockade on ${blockadePoint.x}, ${blockadePoint.y}")
            return true
        }
        if(canTurn(map, nextDirection, position) && needsToTurn(map, nextDirection, position)) {
            nextDirection = getNextDirection(currentDirection)
        }
        if(position.content == '#'){
            return false
        }
        when(nextDirection){
            Motion.UP -> x = x - 1
            Motion.DOWN -> x = x + 1
            Motion.LEFT -> y = y - 1
            Motion.RIGHT -> y = y + 1
        }
    }
    return false
}

fun canTurn(map: Array<Array<Point>>, currentMotion: Motion, currentPosition: Point) : Boolean{
    return when (currentMotion) {
        Motion.UP -> currentPosition.x - 1 >= 0
        Motion.DOWN -> currentPosition.x + 1 < map.size
        Motion.LEFT -> currentPosition.y - 1 >= 0
        Motion.RIGHT -> currentPosition.y + 1 < map[0].size
    }
}

fun getNextPosition(map: Array<Array<Point>>, currentMotion: Motion, currentPosition: Point): Point {
    return when (currentMotion) {
        Motion.UP -> map[currentPosition.x - 1][currentPosition.y]
        Motion.DOWN -> map[currentPosition.x + 1][currentPosition.y]
        Motion.LEFT -> map[currentPosition.x][currentPosition.y - 1]
        Motion.RIGHT -> map[currentPosition.x][currentPosition.y + 1]
    }
}

fun needsToTurn(map: Array<Array<Point>>, currentMotion: Motion, currentPosition: Point): Boolean {
    return when (currentMotion) {
        Motion.UP -> map[currentPosition.x - 1][currentPosition.y].content == '#'
        Motion.DOWN -> map[currentPosition.x + 1][currentPosition.y].content == '#'
        Motion.LEFT -> map[currentPosition.x][currentPosition.y - 1].content == '#'
        Motion.RIGHT -> map[currentPosition.x][currentPosition.y + 1].content == '#'
    }
}

fun nextStepIsValid(
    map: Array<Array<Point>>, currentMotion: Motion, currentPosition: Point
): Boolean {
    return when (currentMotion) {
        Motion.UP -> currentPosition.x - 1 >= 0
        Motion.DOWN -> currentPosition.x + 1 < map.size
        Motion.LEFT -> currentPosition.y - 1 >= 0
        Motion.RIGHT -> currentPosition.y + 1 < map[currentPosition.x].size
    }
}


enum class Motion(val character: Char) {
    UP('^'), DOWN('V'), LEFT('<'), RIGHT('>')
}

fun getNextDirection(motion: Motion): Motion {
    return when (motion) {
        Motion.UP -> Motion.RIGHT
        Motion.RIGHT -> Motion.DOWN
        Motion.DOWN -> Motion.LEFT
        Motion.LEFT -> Motion.UP
    }
}

data class Point(val x: Int, val y: Int, var content: Char, var debugContent: Char)






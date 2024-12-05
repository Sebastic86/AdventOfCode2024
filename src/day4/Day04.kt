package day4

import readInput

fun main() {
    var input = readInput("day4/Day04")

    var sizeX = input.lines().first().length
    var sizeY = input.lines().size

    val matrix = Array(sizeY) { Array<Char>(sizeX) { ' ' } }
    input.lines().forEachIndexed { i, line ->
        line.forEachIndexed { j, c ->
            matrix[i][j] = c
            print(matrix[i][j])
        }
        println()
    }

    var resultPart1 = part1(matrix)
    println("Part 1: $resultPart1")

    var resultPart2 = part2(matrix)
    println("Part 2: $resultPart2")

}

fun part1(matrix: Array<Array<Char>>): Int {
    var result = 0
    for (i in 0 until matrix.size) {
        for (j in 0 until matrix[i].size) {
            println("Searching on i $i j $j")
            result += searchAllDirections(matrix, i, j)
        }
    }

    return result
}

fun searchAllDirections(matrix: Array<Array<Char>>, i: Int, j: Int): Int {
    return findForward(matrix, i, j) +
            findBackward(matrix, i, j) +
            findUp(matrix, i, j) +
            findDown(matrix, i, j) +
            findDiagonalUpRight(matrix, i, j) +
            findDiagonalDownLeft(matrix, i, j) +
            findDiagonalDownRight(matrix, i, j) +
            findDiagonalUpLeft(matrix, i, j)
}

fun findForward(matrix: Array<Array<Char>>, i: Int, j: Int): Int {
    if ((j + 3) > matrix[j].size - 1) {
        return 0
    }
    if (matrix[i][j] == 'X' && matrix[i][j + 1] == 'M' && matrix[i][j + 2] == 'A' && matrix[i][j + 3] == 'S') {
        println("Found one forward starting on: $i , $j")
        return 1
    }
    return 0
}

fun findBackward(matrix: Array<Array<Char>>, i: Int, j: Int): Int {
    if ((j - 3) < 0) {
        return 0
    }
    if (matrix[i][j] == 'X' && matrix[i][j - 1] == 'M' && matrix[i][j - 2] == 'A' && matrix[i][j - 3] == 'S') {
        println("Found one backward starting on: $i , $j")
        return 1
    }
    return 0
}

fun findUp(matrix: Array<Array<Char>>, i: Int, j: Int): Int {
    if ((i - 3) < 0) {
        return 0
    }
    if (matrix[i][j] == 'X' && matrix[i - 1][j] == 'M' && matrix[i - 2][j] == 'A' && matrix[i - 3][j] == 'S') {
        println("Found one up starting on: $i , $j")
        return 1
    }
    return 0
}

fun findDown(matrix: Array<Array<Char>>, i: Int, j: Int): Int {
    if ((i + 3) > matrix.size - 1) {
        return 0
    }
    if (matrix[i][j] == 'X' && matrix[i + 1][j] == 'M' && matrix[i + 2][j] == 'A' && matrix[i + 3][j] == 'S') {
        println("Found one down starting on: $i , $j")
        return 1
    }
    return 0
}

fun findDiagonalUpRight(matrix: Array<Array<Char>>, i: Int, j: Int): Int {
    if (((i - 3) < 0) || ((j + 3) > matrix[j].size - 1)) {
        return 0
    }
    if (matrix[i][j] == 'X' && matrix[i - 1][j + 1] == 'M' && matrix[i - 2][j + 2] == 'A' && matrix[i - 3][j + 3] == 'S') {
        println("Found one diagonal up right starting on: $i , $j")
        return 1
    }
    return 0
}

fun findDiagonalUpLeft(matrix: Array<Array<Char>>, i: Int, j: Int): Int {
    if (((i - 3) < 0) || ((j - 3) < 0)) {
        return 0
    }
    if (matrix[i][j] == 'X' && matrix[i - 1][j - 1] == 'M' && matrix[i - 2][j - 2] == 'A' && matrix[i - 3][j - 3] == 'S') {
        println("Found one diagonal up left starting on: $i , $j")
        return 1
    }
    return 0
}

fun findDiagonalDownLeft(matrix: Array<Array<Char>>, i: Int, j: Int): Int {
    if (((i + 3) > matrix.size - 1) || ((j - 3) < 0)) {
        return 0
    }
    if (matrix[i][j] == 'X' && matrix[i + 1][j - 1] == 'M' && matrix[i + 2][j - 2] == 'A' && matrix[i + 3][j - 3] == 'S') {
        println("Found one diagonal down left starting on: $i , $j")
        return 1
    }
    return 0
}

fun findDiagonalDownRight(matrix: Array<Array<Char>>, i: Int, j: Int): Int {
    if (((i + 3) > matrix.size - 1) || ((j + 3) > matrix[j].size - 1)) {
        return 0
    }
    if (matrix[i][j] == 'X' && matrix[i + 1][j + 1] == 'M' && matrix[i + 2][j + 2] == 'A' && matrix[i + 3][j + 3] == 'S') {
        println("Found one diagonal down right starting on: $i , $j")
        return 1
    }
    return 0
}


fun part2(matrix: Array<Array<Char>>): Int {
    var result = 0
    for (i in 0 until matrix.size) {
        for (j in 0 until matrix[i].size) {
            if (matrix[i][j] == 'A') {
                println("Searching on i $i j $j")
                result += searchAllXMasVariants(matrix, i, j)
            }

        }
    }

    return result
}

fun searchAllXMasVariants(matrix: Array<Array<Char>>, i: Int, j: Int): Int {
    return findMatch(matrix, i, j, 'M', 'S','M','S') +
            findMatch(matrix, i, j, 'S', 'S','M','M') +
            findMatch(matrix, i, j, 'S', 'M','S','M') +
            findMatch(matrix, i, j, 'M', 'M','S','S')
}

fun findMatch(matrix: Array<Array<Char>>, i: Int, j: Int, a:Char, b:Char, c:Char, d: Char): Int {
    if (checkBoundary(matrix, i, j)) return 0

    if (
        matrix[i - 1][j - 1] == a &&
        matrix[i - 1][j + 1] == b &&
        matrix[i][j] == 'A' &&
        matrix[i + 1][j - 1] == c &&
        matrix[i + 1][j + 1] == d
    ) {
        return 1
    }
    return 0
}

fun checkBoundary(matrix: Array<Array<Char>>, i: Int, j: Int): Boolean {
    return i + 1 > matrix.size - 1 ||
            i - 1 < 0 ||
            j + 1 > matrix[j].size - 1 ||
            j - 1 < 0
}






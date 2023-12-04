package com.ashley

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.math.log

class Day3 {

    private fun readData(): List<String> {
        val readData = ArrayList<String>()
        val resource = ClassLoader.getSystemResource("day3/final.txt")
        resource.readText().lines().forEach { line ->
            if (line != "") {
                readData.add(line)
            }
        }

        return readData;
    }

    val partSymbolMatchList = HashSet<Char>()


    fun fillInSymbolMatchList(readData: List<String>) {
        for (line in readData) {
            for (character in line.toCharArray()) {
                if (!character.isDigit() && character != ignoreSymbol && !partSymbolMatchList.contains(character)) {
                    partSymbolMatchList.add(character)
                }
            }
        }
        println(partSymbolMatchList.toString())
    }

    val ignoreSymbol = '.'

    class PartNumber(var partValue: Int, var row: Int, var startCol: Int) {
        var colCount = 1
        var validNumber = false
        var neededCoords = ArrayList<Coord>()
        override fun toString(): String {
            return "PartNumber(partValue=$partValue, row=$row, startCol=$startCol, colCount=$colCount, validNumber=$validNumber)"
        }
    }

    class Coord(var symbol: Char, var x: Int, var y: Int) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Coord

            if (x != other.x) return false
            if (y != other.y) return false

            return true
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            return result
        }

        override fun toString(): String {
            return "Coord(x=$x, y=$y)"
        }
    }

    fun answer1(): Int {
        val data = readData()

        val maxWidth = data[0].length;
        val maxHeight = data.size;

        var buildingPartNumber = false
        lateinit var workingNumber: PartNumber

        val foundPartNumbers = ArrayList<PartNumber>()
        val partSymbols = ArrayList<Coord>()

        data.forEachIndexed { rowIndex, line ->
            line.toCharArray().forEachIndexed { colIndex, character ->
                if (character.isDigit()) {
                    if (buildingPartNumber) {
                        workingNumber.colCount++
                        workingNumber.partValue *= 10
                        workingNumber.partValue += character.digitToInt()
                    } else {
                        workingNumber = PartNumber(character.digitToInt(), rowIndex, colIndex)
                        buildingPartNumber = true
                    }
                } else if (character == ignoreSymbol) {
                    if (buildingPartNumber) {
                        foundPartNumbers.add(workingNumber)
                    }
                    buildingPartNumber = false
                } else {
                    partSymbols.add(Coord(character, colIndex, rowIndex))
                    if (buildingPartNumber) {
                        foundPartNumbers.add(workingNumber)
                    }
                    buildingPartNumber = false
                }
            }
        }

        val gearSymbols = partSymbols.filter { symbol -> symbol.symbol == '*' }
        val neededCoords = ArrayList<Coord>()

        for (num in foundPartNumbers) {
            for (i in -1..1) {
                for (j in -1..num.colCount) {
                    val top = num.row + i
                    val left = num.startCol + j

                    if (i == 0 && j != -1 && j != num.colCount) {
                        continue
                    }
                    if (left in 0..<maxWidth) {
                        if (top in 0..<maxHeight) {
                            neededCoords.add(Coord('-', left, top))
                        }
                    }
                }
            }

            num.validNumber = neededCoords.filter { coord -> partSymbols.contains(coord) }.isNotEmpty()
            neededCoords.clear()
        }

        return foundPartNumbers.filter { num -> num.validNumber }.sumOf { num -> num.partValue }
    }

    fun answer2(): Int {
        val data = readData()

        val maxWidth = data[0].length;
        val maxHeight = data.size;

        var buildingPartNumber = false
        lateinit var workingNumber: PartNumber

        val foundPartNumbers = ArrayList<PartNumber>()
        val partSymbols = ArrayList<Coord>()

        data.forEachIndexed { rowIndex, line ->
            line.toCharArray().forEachIndexed { colIndex, character ->
                if (character.isDigit()) {
                    if (buildingPartNumber) {
                        workingNumber.colCount++
                        workingNumber.partValue *= 10
                        workingNumber.partValue += character.digitToInt()
                    } else {
                        workingNumber = PartNumber(character.digitToInt(), rowIndex, colIndex)
                        buildingPartNumber = true
                    }
                } else if (character == ignoreSymbol) {
                    if (buildingPartNumber) {
                        foundPartNumbers.add(workingNumber)
                    }
                    buildingPartNumber = false
                } else {
                    partSymbols.add(Coord(character, colIndex, rowIndex))
                    if (buildingPartNumber) {
                        foundPartNumbers.add(workingNumber)
                    }
                    buildingPartNumber = false
                }
            }
        }

        val gearSymbols = partSymbols.filter { symbol -> symbol.symbol == '*' }
        val neededCoords = ArrayList<Coord>()

        for (num in foundPartNumbers) {
            for (i in -1..1) {
                for (j in -1..num.colCount) {
                    val top = num.row + i
                    val left = num.startCol + j

                    if (i == 0 && j != -1 && j != num.colCount) {
                        continue
                    }
                    if (left in 0..<maxWidth) {
                        if (top in 0..<maxHeight) {
                            neededCoords.add(Coord('-', left, top))
                        }
                    }
                }
            }

            num.neededCoords = neededCoords.clone() as ArrayList<Coord>

            neededCoords.clear()
        }

        var total = 0
        gearSymbols.forEach { symbol ->
            val surroundingNumbers = foundPartNumbers.filter { num -> num.neededCoords.contains(symbol) }
            if (surroundingNumbers.size == 2) {
                total += surroundingNumbers[0].partValue * surroundingNumbers[1].partValue
            }
        }

        return total
    }
}
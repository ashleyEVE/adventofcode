package com.ashley

import kotlin.collections.ArrayDeque
import kotlin.collections.ArrayList

class Day1 {

    private fun readData(): ArrayList<String> {
        val readData = ArrayList<String>()
        val resource = ClassLoader.getSystemResource("day1/final.txt")
        resource.readText().lines().forEach { line ->
            if (line != "") {
                readData.add(line);
            }
        }

        return readData;
    }

    fun answer1(): Int {
        val data = readData();
        var total = 0;

        for (line in data) {
            val (digits, notdigits) = line.toList().partition { it.isDigit() }
            total += digits[0].digitToInt() * 10;
            total += digits[digits.size - 1].digitToInt();
        }

        return total;
    }

    val numberStrings = listOf( "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" )

    fun answer2(): Int {

        val data = readData();

        var characterBuffer = ArrayDeque<Char>()

        var total = 0
        var foundFirst = false
        var firstValue = 0
        var tempVal1 = -1
        var tempVal2 = -1

        for (line in data) {
            line.toList().forEach{character ->
                if (tempVal1 == -1) {
                    characterBuffer.add(character)
                    if (characterBuffer.size > 3) {
                        characterBuffer.removeFirst();
                    }
                    tempVal1 = checkForValue(character, characterBuffer)
                }
            }

            characterBuffer.clear()

            line.toList().reversed().forEach{character ->
                if (tempVal2 == -1) {
                    characterBuffer.addFirst(character)
                    if (characterBuffer.size > 3) {
                        characterBuffer.removeLast();
                    }
                    tempVal2 = checkForValue(character, characterBuffer)
                }
            }
            if (tempVal1 == -1) {
                println("val1 fail")
            }
            if (tempVal2 == -1) {
                println("val2 fail")
            }
//            println(tempVal1.toString() + tempVal2.toString())
            total += tempVal1 * 10
            total += tempVal2
            tempVal1 = -1
            tempVal2 = -1
        }

        return total;
    }

    private fun checkForValue(character: Char, charArray: ArrayDeque<Char>): Int {
        var newValue = -1
        if (character.isDigit()) {
            newValue = character.digitToInt()
        }
        if (charArray.size == 3) {
            val match = numberStrings.filter { numString -> numString.startsWith(String(charArray.toCharArray())) }
            if (match.isNotEmpty()) {
                newValue = numberStrings.indexOf(match[0]) + 1;
            }
        }
        return newValue;
    }
}


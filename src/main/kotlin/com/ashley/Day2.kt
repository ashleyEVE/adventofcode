package com.ashley

import kotlin.math.log

class Day2 {

    private fun readData(): ArrayList<String> {
        val readData = ArrayList<String>()
        val resource = ClassLoader.getSystemResource("day2/final.txt")
        resource.readText().lines().forEach { line ->
            if (line != "") {
                readData.add(line)
            }
        }

        return readData;
    }

    fun readGameNumber(line: String): Pair<Int, List<String>> {
        val dataList = line.split(":")

        return Pair(dataList[0].substring(5, dataList[0].length).toInt(), dataList.subList(1, dataList.size))
    }

    fun answer1(): Int {
        val maxRed = 12
        val maxGreen = 13
        val maxBlue = 14

        var invalidGame = false
        var counter = 0

        val data = readData();

        for (line in data) {
            invalidGame = false
            val (gameIndex, cubeSetList) = readGameNumber(line)
            println(gameIndex)
            for (set in cubeSetList) {
                if (!invalidGame) {
                    for (game in set.split(';')) {
                        if (!invalidGame) {
                            val cubeList = game.split(',')
                            for (cube in cubeList) {
                                val cubeParsed = cube.trim().split(' ')
                                if (!invalidGame) {
                                    when (cubeParsed[1].trim()) {
                                        "green" -> invalidGame = cubeParsed[0].toInt() > maxGreen
                                        "red" -> invalidGame = cubeParsed[0].toInt() > maxRed
                                        "blue" -> invalidGame = cubeParsed[0].toInt() > maxBlue
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!invalidGame) {
                counter += gameIndex
            }
        }

        return counter
    }

    fun answer2(): Int {
        var counter = 0
        var maxGreen = 0
        var maxRed = 0
        var maxBlue = 0

        val data = readData();

        for (line in data) {
            maxGreen = 0
            maxRed = 0
            maxBlue = 0

            val (gameIndex, cubeSetList) = readGameNumber(line)
            println(gameIndex)
            for (set in cubeSetList) {
                for (game in set.split(';')) {
                    val cubeList = game.split(',')
                    for (cube in cubeList) {
                        val cubeParsed = cube.trim().split(' ')
                        when (cubeParsed[1].trim()) {
                            "green" -> if(cubeParsed[0].toInt() > maxGreen) maxGreen = cubeParsed[0].toInt()
                            "red" ->  if(cubeParsed[0].toInt() > maxRed) maxRed = cubeParsed[0].toInt()
                            "blue" ->  if(cubeParsed[0].toInt() > maxBlue) maxBlue = cubeParsed[0].toInt()
                        }
                    }
                }
            }

            counter += maxRed * maxBlue * maxGreen
        }

        return counter
    }
}
package com.ashley

import kotlin.math.log

class Day6 {
    fun answer1(): Int {
        val data = FileReader.readData(6, true)

        val timeList = data[0].split(':')[1].split(' ').filter{ str -> str.isNotEmpty() }.map { str -> str.trim().toInt() }
        val distList = data[1].split(':')[1].split(' ').filter{ str -> str.isNotEmpty() }.map { str -> str.trim().toInt() }

        var currentWins = 0
        var winsRatio = 0
        for (i in 0..<timeList.size) {
            currentWins = 0
            for (t in 0..timeList[i]) {
                val expectedDist = t * (timeList[i] - t)
                if (expectedDist > distList[i]) {
                    currentWins++
                }
            }
            println(currentWins)
            if (winsRatio != 0) {
                winsRatio *= currentWins
            } else {
                winsRatio = currentWins
            }
        }

        return winsRatio
    }

    fun List<String>.concat() = this.joinToString("") { it }.takeWhile { it.isDigit() }

    fun answer2(): Int {
        val data = FileReader.readData(6, true)


        val time = data[0].split(':')[1].split(' ').filter{ str -> str.isNotEmpty() }.concat().toLong()
        val dist = data[1].split(':')[1].split(' ').filter{ str -> str.isNotEmpty() }.concat().toLong()

        var currentWins = 0
        var winsRatio = 0

        for (t in 0..time) {
            val expectedDist = t * (time - t)
            if (expectedDist > dist) {
                currentWins++
            }
        }

        return currentWins
    }
}
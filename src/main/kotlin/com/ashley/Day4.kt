package com.ashley

import kotlin.math.log
import kotlin.math.pow

class Day4 {
    fun answer1(): Int {
        val data = FileReader.readData(4, true)
        var total = 0

        for (line in data) {
            val cardData = line.split('|')
            val winningData = cardData[0].trim().split(' ').filter { num -> num.isNotEmpty() }
            var winningParsed = winningData.subList(2, winningData.size).map { num -> num.toInt() }
            var myNumbers = cardData[1].trim().split(' ').filter { num -> num.isNotEmpty() }.map { num -> num.toInt() }

            total += 2.0.pow(myNumbers.filter { myNum -> winningParsed.contains(myNum) }.size-1.toDouble()).toInt()
        }

        return total
    }

    class cardMultiplier(var winningNums:Int, var multiplier: Int) {
        override fun toString(): String {
            return "cardMultiplier(winningNums=$winningNums, multiplier=$multiplier)"
        }
    }

    fun answer2(): Int {
        val data = FileReader.readData(4, true)

        var total = 0

        var cardList = ArrayList<cardMultiplier>()

        for (line in data) {
            cardList.add(cardMultiplier(0,1))
        }

        for (i in 0..<data.size) {
            val cardData = data[i].split('|')
            val winningData = cardData[0].trim().split(' ').filter { num -> num.isNotEmpty() }
            var winningParsed = winningData.subList(2, winningData.size).map { num -> num.toInt() }
            var myNumbers = cardData[1].trim().split(' ').filter { num -> num.isNotEmpty() }.map { num -> num.toInt() }

            cardList.get(i).winningNums = myNumbers.filter { myNum -> winningParsed.contains(myNum) }.size

            for (j in 1..cardList.get(i).winningNums) {
                cardList.get(i + j).multiplier += cardList.get(i).multiplier
            }
        }

        return cardList.sumOf { card -> card.multiplier }
    }
}
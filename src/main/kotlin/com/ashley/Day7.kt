package com.ashley

import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Day7 {

    val cardTypeList = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')

    val cardTypeListJoker =  listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
    enum class handType {
        fiveOfAKind,
        fourOfAKind,
        fullHouse,
        threeOfAKind,
        twoPair,
        onePair,
        highCard
    }

    class playerHand(var cards: List<Char>, var type: handType, var bid: Int) {
        var rank: Int = 0

        override fun toString(): String {
            var sortedCards = cards.sorted()
            return "playerHand(cards=$sortedCards, type=$type, rank=$rank, bid=$bid)"
        }
    }

    private fun detectHandType(cardList: List<Char>): handType {
        val cardTypeMap = HashMap<Char, Int>(5)

        for (card in cardList) {
            if (cardTypeMap.containsKey(card)) {
                cardTypeMap[card] = cardTypeMap[card]!! + 1
            } else {
                cardTypeMap.put(card, 1)
            }
        }

        var handTypeVal = handType.highCard
        var count1 = 0
        var count2 = 0
        var count3 = 0

        cardTypeMap.values.forEachIndexed { index, count ->
            run {
                when (index) {
                    0 -> count1 = count
                    1 -> count2 = count
                    2 -> count3 = count
                }
            }
        }

        if (cardTypeMap.keys.size == 1) {
            handTypeVal = handType.fiveOfAKind
        } else if (cardTypeMap.keys.size == 2) {
            if (count1 == 1 || count1 == 4) {
                handTypeVal = handType.fourOfAKind
            } else {
                handTypeVal = handType.fullHouse
            }
        } else if (cardTypeMap.keys.size == 3) {


            handTypeVal = if (count1 == 3 || count2 == 3 || count3 == 3) {
                handType.threeOfAKind
            } else {
                handType.twoPair
            }
        } else if (cardTypeMap.keys.size == 4) {
            handTypeVal = handType.onePair
        }

        return handTypeVal
    }

    private fun detectHandTypeJoker(cardList: List<Char>): handType {
        val cardTypeMap = HashMap<Char, Int>(5)

        for (card in cardList) {
            if (cardTypeMap.containsKey(card)) {
                cardTypeMap[card] = cardTypeMap[card]!! + 1
            } else {
                cardTypeMap.put(card, 1)
            }
        }

        var handTypeVal = handType.highCard
        var count1 = -1
        var count2 = -1
        var count3 = -1

        cardTypeMap.values.forEachIndexed { index, count ->
            run {
                when (index) {
                    0 -> count1 = count
                    1 -> count2 = count
                    2 -> count3 = count
                }
            }
        }

        if (cardTypeMap.keys.size == 1) {
            handTypeVal = handType.fiveOfAKind
        } else if (cardTypeMap.keys.size == 2) {
            if (count1 == 1 || count1 == 4) {
                handTypeVal = handType.fourOfAKind
            } else {
                handTypeVal = handType.fullHouse
            }
        } else if (cardTypeMap.keys.size == 3) {


            handTypeVal = if (count1 == 3 || count2 == 3 || count3 == 3) {
                handType.threeOfAKind
            } else {
                handType.twoPair
            }
        } else if (cardTypeMap.keys.size == 4) {
            handTypeVal = handType.onePair
        }

        if (handTypeVal != handType.fiveOfAKind && cardList.contains('J')) {
           var jokerCount = cardList.count { card -> card.equals('J') }
           if (jokerCount == 4) {
               handTypeVal = handType.fiveOfAKind
           } else if (jokerCount == 3) {
               if (handTypeVal == handType.threeOfAKind) {
                   handTypeVal = handType.fourOfAKind
               } else {
                   handTypeVal = handType.fiveOfAKind
               }
           } else if (jokerCount == 2) {
               if (handTypeVal == handType.fullHouse) {
                   handTypeVal = handType.fiveOfAKind
               } else if (handTypeVal == handType.twoPair) {
                   handTypeVal = handType.fourOfAKind
               } else {
                   handTypeVal = handType.threeOfAKind
               }
           } else if (jokerCount == 1) {
               if (handTypeVal == handType.fourOfAKind) {
                   handTypeVal = handType.fiveOfAKind
               } else if (handTypeVal == handType.twoPair){
                   handTypeVal = handType.fullHouse
               } else if (handTypeVal == handType.threeOfAKind) {
                   handTypeVal = handType.fourOfAKind
               } else if (handTypeVal == handType.onePair) {
                   handTypeVal = handType.threeOfAKind
               } else if (handTypeVal == handType.highCard) {
                   handTypeVal = handType.onePair
               }
           }
        }

        return handTypeVal
    }

    private fun findNextWinnder(typeList: List<playerHand>, curTypeList: List<Char>): playerHand {
        var currentHighestScore = curTypeList.size
        var winningHands = ArrayList<playerHand>()
        var testingHands = typeList

        for (i in 0..4) {
            currentHighestScore = curTypeList.size
            for (hand in testingHands) {
                var myscore = curTypeList.indexOf(hand.cards[i])

                if (myscore < currentHighestScore) {
                    currentHighestScore = myscore
                    winningHands.clear()
                    winningHands.add(hand)
                } else if (myscore == currentHighestScore) {
                    winningHands.add(hand)
                }
            }
            if (winningHands.size == 1) {
                return winningHands[0]
            }
            testingHands = ArrayList<playerHand>(winningHands)
        }
        println("SOMETHING WENT VERY WRONG")
        return typeList[0]
    }

    fun answer1(): Int {
        val data = FileReader.readData(7, true)

        val hands = ArrayList<playerHand>()
        val handTypeGroups = HashMap<handType, List<playerHand>>()

        for (line in data) {
            val splitLine = line.split(' ')
            val cardList = splitLine[0].toList()

            val type = detectHandType(cardList)

            if (!handTypeGroups.contains(type)) {
                handTypeGroups[type] = ArrayList()
            }

            val newHand = playerHand(cardList, type, splitLine[1].toInt())
            handTypeGroups[type]?.addLast(newHand)
            hands.add(newHand)
        }

        val orderedList = ArrayList<playerHand>()

        for (types in handTypeGroups.keys.sorted()) {
            println("$types TYPE")
            val typeList = handTypeGroups[types]?.let { ArrayList<playerHand>(it) }

            if (typeList != null) {
                while (typeList.size > 1) {
                    val winner = findNextWinnder(typeList, cardTypeList)
                    typeList.remove(winner)
                    orderedList.add(winner)
                }
                orderedList.add(typeList[0])
            }
        }

        var total = 0
        orderedList.reversed().forEachIndexed { index, hand ->
            run {
                println(hand.toString() + " " + total + " " + (hand.bid * (index + 1)).toString() + " " + hand.bid.toString() + " * (" + index.toString() + " + 1)")
                total += hand.bid * (index + 1)
            }
        }

        return total
    }

    fun answer2(): Int {
        val data = FileReader.readData(7, true)

        val hands = ArrayList<playerHand>()
        val handTypeGroups = HashMap<handType, List<playerHand>>()

        for (line in data) {
            val splitLine = line.split(' ')
            val cardList = splitLine[0].toList()

            val type = detectHandTypeJoker(cardList)

            if (!handTypeGroups.contains(type)) {
                handTypeGroups[type] = ArrayList()
            }

            val newHand = playerHand(cardList, type, splitLine[1].toInt())
            handTypeGroups[type]?.addLast(newHand)
            hands.add(newHand)
        }

        val orderedList = ArrayList<playerHand>()

        for (types in handTypeGroups.keys.sorted()) {
            println("$types TYPE")
            val typeList = handTypeGroups[types]?.let { ArrayList<playerHand>(it) }

            if (typeList != null) {
                while (typeList.size > 1) {
                    val winner = findNextWinnder(typeList, cardTypeListJoker)
                    typeList.remove(winner)
                    orderedList.add(winner)
                }
                orderedList.add(typeList[0])
            }
        }

        var total = 0
        orderedList.reversed().forEachIndexed { index, hand ->
            run {
                if (hand.cards.contains('J')) {
                    println(hand.toString() + " " + total + " " + (hand.bid * (index + 1)).toString() + " " + hand.bid.toString() + " * (" + index.toString() + " + 1)")
                }
                total += hand.bid * (index + 1)
            }
        }

        // 247508594 too low
        // 247707575 too low
        return total
    }
}
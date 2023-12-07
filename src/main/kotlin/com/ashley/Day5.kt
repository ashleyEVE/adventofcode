package com.ashley

import java.util.concurrent.TimeUnit
import kotlin.math.abs

class Day5 {
    private val seedsString = "seeds"
    private val seedToSoilString = "seed-to-soil map"
    private val soilToFertilizerString = "soil-to-fertilizer map"
    private val fertilizerToWaterString = "fertilizer-to-water map"
    private val waterToLightString = "water-to-light map"
    private val lightToTemperatureString = "light-to-temperature map"
    private val temperatureToHumidityString = "temperature-to-humidity map"
    private val humidityToLocationString = "humidity-to-location map"
    private val matchStrings = listOf(
        seedsString, seedToSoilString, soilToFertilizerString,
        fertilizerToWaterString, waterToLightString, lightToTemperatureString,
        temperatureToHumidityString, humidityToLocationString
    )

    class seedRange(var startSeed: Long, var stopSeed: Long) {
        override fun toString(): String {
            return "seedRange(startSeed=$startSeed, stopSeed=$stopSeed)"
        }
    }

    private fun parseSeeds(seedString: String): List<Long> {
        val seedList = ArrayList<Long>()
        val splitString = seedString.split(' ')
        splitString.forEachIndexed { index: Int, sub: String ->
            if (index > 0) {
                seedList.add(sub.trim().toLong())
            }
        }
        return seedList
    }

    private fun parseSeeds2(seedString: String): List<seedRange> {
        val seedList = ArrayList<seedRange>()
        val splitString = seedString.split(' ')
        splitString.forEachIndexed { index: Int, sub: String ->
            if (index > 0 && index % 2 == 1) {
                seedList.add(seedRange(sub.trim().toLong(),
                    sub.trim().toLong() + splitString[index + 1].trim().toLong()))
            }
        }
//        println(seedList)
        return seedList
    }

    class mapReferenceKey(var source: Long, var dest: Long, var count: Long) {
        override fun toString(): String {
            return "mapReferenceKey(source=$source, dest=$dest, count=$count)"
        }
    }

    private fun parseMap(data: List<String>): List<List<mapReferenceKey>> {
        val lists = ArrayList<List<mapReferenceKey>>()
        val seedToSoil = ArrayList<mapReferenceKey>()
        val soilToFertilizer = ArrayList<mapReferenceKey>()
        val fertilizerToWater = ArrayList<mapReferenceKey>()
        val waterToLight = ArrayList<mapReferenceKey>()
        val lightToTemperature = ArrayList<mapReferenceKey>()
        val temperatureToHumidity = ArrayList<mapReferenceKey>()
        val humidityToLocation = ArrayList<mapReferenceKey>()
        lists.add(seedToSoil)
        lists.add(soilToFertilizer)
        lists.add(fertilizerToWater)
        lists.add(waterToLight)
        lists.add(lightToTemperature)
        lists.add(temperatureToHumidity)
        lists.add(humidityToLocation)

        var listIndex = -2

        for (line in data) {
            if (matchStrings.contains(line.split(':')[0])) {
                listIndex++
                continue
            }
            val splitLine = line.split(' ')
            lists[listIndex].addLast(
                mapReferenceKey(
                    splitLine[1].toLong(),
                    splitLine[0].toLong(),
                    splitLine[2].toLong()
                )
            )
        }

        return lists
    }

    private fun findNextMapSource(source: Long, mapReferenceKeys: List<mapReferenceKey>): Long {
        var dest = -1L
        for (entry in mapReferenceKeys) {
            val min = entry.source
            val max = entry.source + entry.count
            if (source in min..max) {
                dest = entry.dest + abs(source - entry.source)
                break
            }
        }
        if (dest == -1L) {
            dest = source
        }
        return dest
    }

//    private fun findNextMapSourceFromRange(range: seedRange,
//                                           mapReferenceKeys: List<mapReferenceKey>): Long {
//
//    }

    fun answer1(): Long {
        val data = FileReader.readData(5, true)

        val seedList = parseSeeds(data[0])

        val lists = parseMap(data)

        var smallestLoc = Long.MAX_VALUE
        for (seed in seedList) {
//            print(" TEST " + seedList[1].toString() + "\n")
            val soil = findNextMapSource(seed, lists[0])
//            println(soil)
            val fertilizer = findNextMapSource(soil, lists[1])
//            println(fertilizer)
            val water = findNextMapSource(fertilizer, lists[2])
//            println(water)
            val light = findNextMapSource(water, lists[3])
//            println(light)
            val temperature = findNextMapSource(light, lists[4])
//            println(temperature)
            val humidity = findNextMapSource(temperature, lists[5])
//            println(humidity)
            val location = findNextMapSource(humidity, lists[6])
//            println(location)
            if (location < smallestLoc) {
                smallestLoc = location
            }
        }
        return smallestLoc
    }

    fun answer2(): Long {
        val data = FileReader.readData(5, true)

        val seedList = parseSeeds2(data[0])

        val lists = parseMap(data)

        var smallestLoc = Long.MAX_VALUE

        var startTime = System.currentTimeMillis()
        var iterations = 0
        for (range in seedList) {
//            print(" TEST " + seed.toString() + "\n")

            for (seed in range.startSeed..range.stopSeed) {
                iterations++
                if (iterations > 100000000) {
                    iterations = 0
                    val timeSpent = System.currentTimeMillis() - startTime
                    println(String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(timeSpent),
                        TimeUnit.MILLISECONDS.toMinutes(timeSpent) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeSpent)), // The change is in this line
                        TimeUnit.MILLISECONDS.toSeconds(timeSpent) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeSpent))))
                }
                val soil = findNextMapSource(seed, lists[0])
//                println(soil)
                val fertilizer = findNextMapSource(soil, lists[1])
//                println(fertilizer)
                val water = findNextMapSource(fertilizer, lists[2])
//                println(water)
                val light = findNextMapSource(water, lists[3])
//                println(light)
                val temperature = findNextMapSource(light, lists[4])
//                println(temperature)
                val humidity = findNextMapSource(temperature, lists[5])
//                println(humidity)
                val location = findNextMapSource(humidity, lists[6])
//                println(location)
                if (location < smallestLoc) {
                    smallestLoc = location
                }
            }
        }

        return smallestLoc
    }
}
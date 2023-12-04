package com.ashley

import kotlin.math.log

class DayTemplate {

    private fun readData(): List<String> {
        val readData = ArrayList<String>()
        val resource = ClassLoader.getSystemResource("dayX/test.txt")
        resource.readText().lines().forEach { line ->
            if (line != "") {
                readData.add(line)
            }
        }

        return readData;
    }

    fun answer1(): Int {
        val data = readData()

        return data.size
    }

    fun answer2(): Int {
        val data = readData()

        return data.size
    }
}
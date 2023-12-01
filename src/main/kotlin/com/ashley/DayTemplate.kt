package com.ashley

import kotlin.math.log

class DayTemplate {

    private fun readData(): Nothing? {
        val readData: Nothing? = null;
        val resource = ClassLoader.getSystemResource("dayX/test.txt")
        resource.readText().lines().forEach { line ->
            if (line != "") {
                println("We read things, yay!")
            }
        }

        return readData;
    }

    fun answer1(): Nothing? {
        val data = readData();

        return data;
    }

    fun answer2(): Nothing? {
        val data = readData();

        return data;
    }
}
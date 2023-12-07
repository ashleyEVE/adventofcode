package com.ashley

class FileReader {
    companion object {
        fun readData(day:Int, ready:Boolean): List<String> {
            val readData = ArrayList<String>()
            val fileName = if (ready) "final" else "test"
            val resource = ClassLoader.getSystemResource("day" + day
                .toString() + "/" + fileName + ".txt")
            resource.readText().lines().forEach { line ->
                if (line != "") {
                    readData.add(line)
                }
            }

            return readData;
        }
    }
}

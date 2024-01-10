package com.ashley

class Day8 {

    class Route(var routeName: String, var routeLeft: String, var routeRight: String) {
        override fun toString(): String {
            return "route(routeName='$routeName', routeLeft='$routeLeft', routeRight='$routeRight')"
        }
    }

    fun readRouteList(matchedStart:String, data: List<String>, routeList: HashMap<String, Route>): ArrayList<Route> {
        var currentRoute = ArrayList<Route>()
        //        println(directions)
        for (line in data.subList(1, data.size)) {
            //AAA = (BBB, CCC)
            var firstSplit = line.split('=')
            var leftAndRight = firstSplit[1].trim().split(',')
            val newRoute = Route(firstSplit[0].trim(), leftAndRight[0].substring(1, 4), leftAndRight[1].substring(1, 4))
            if (newRoute.routeName.endsWith(matchedStart)) {
                currentRoute.add(Route(firstSplit[0].trim(), leftAndRight[0].substring(1, 4), leftAndRight[1].substring(1, 4)))
            }
            routeList.put(firstSplit[0].trim(), newRoute)

//            println(route(firstSplit[0].trim(), leftAndRight[0].substring(1, 4), leftAndRight[1].substring(1, 4)))
        }

        return currentRoute
    }

    fun answer1(): Long {
        val data = FileReader.readData(8, true)

        var routeList = HashMap<String, Route>()
        var directions = data[0].toCharArray().toList()
        var currentRoute = readRouteList("AAA", data, routeList)

        var currentDirection = 0
        var stepCount = 0L
        if (currentRoute.size > 0) {

            while (currentRoute[0].routeName != "ZZZ") {
                stepCount++
                if (currentRoute[0].routeName == "SBF") {
//                println(stepCount.toString() + " Found the previous step " + directions[currentDirection])
                }
                if (stepCount % 1000000000L == 0L) {
//                println(stepCount.toString() + " " + currentRoute.toString() + " " + directions[currentDirection])
                }
                when (directions[currentDirection]) {
                    'R' -> {
                        currentRoute[0] = routeList.get(currentRoute[0].routeRight)!!
                    }

                    'L' -> {
                        currentRoute[0] = routeList.get(currentRoute[0].routeLeft)!!
                    }
                }
                currentDirection += 1
                if (currentDirection >= directions.size) {
                    currentDirection = 0
                }
            }
        }
//        println(currentRoute)

        return stepCount
    }

    fun answer2(): Long {
        val data = FileReader.readData(8, false)

        var routeList = HashMap<String, Route>()
        var directions = data[0].toCharArray().toList()
        var currentRoute = readRouteList("A", data, routeList)

        println(currentRoute)
        var currentDirection = 0
        var stepCount = 0L
//        while (currentRoute?.routeName != "ZZZ") {
//            stepCount++
//            if (currentRoute?.routeName == "SBF") {
//                println(stepCount.toString() + " Found the previous step " + directions[currentDirection])
//            }
//            if (stepCount % 1000000000L == 0L) {
//                println(stepCount.toString() + " " + currentRoute.toString() + " " + directions[currentDirection])
//            }
//            when (directions[currentDirection]) {
//                'R' -> {
//                    currentRoute = routeList.get(currentRoute?.routeRight)
//                }
//
//                'L' -> {
//                    currentRoute = routeList.get(currentRoute?.routeLeft)
//                }
//            }
//            currentDirection += 1
//            if (currentDirection >= directions.size) {
//                currentDirection = 0
//            }
//        }
//        println(currentRoute)

        return stepCount
    }
}
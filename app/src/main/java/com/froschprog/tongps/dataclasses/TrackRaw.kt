package com.froschprog.tongps.dataclasses

//Data Class for a Track
data class TrackRaw(
    var Liste: ArrayList<MyLocation> = arrayListOf()
) {




}

data class MyLocation(
    var Mytime: Long,
    var Ordre: Int,
    var Mylatitude: Double,
    var Mylongitude: Double,
    var Myaltitude: Double,
    var Myprecision: Float,
    var Unused1: Float,
    var unused2: String
) {

}

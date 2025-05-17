package com.example.blackboxinvaders

data class SensorData(var x:Float, var y:Float, var z:Float, var timestamp:Long ) {
    fun toCSVRow(): String{
        return "${x},${y},${z},${timestamp}"
    }
}
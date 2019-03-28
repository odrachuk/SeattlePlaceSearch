package com.softsandr.placesearch.utils

import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Created by Oleksandr Drachuk on 28.03.19.
 */
enum class DistanceUnit(val unit: String) {
    MILES("Miles"), KILOMETERS("Kilometers")
}

fun Double.roundOffDecimal(): Double? {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this).toDouble()
}

object DistanceUtil {

    @JvmStatic
    @JvmOverloads
    fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double,
                 unit: DistanceUnit = DistanceUnit.MILES): Double {
        if (lat1 == lat2 && lon1 == lon2) {
            return 0.0
        } else {
            val theta = lon1 - lon2
            var dist = Math.sin(Math.toRadians(lat1)) *
                    Math.sin(Math.toRadians(lat2)) +
                    Math.cos(Math.toRadians(lat1)) *
                    Math.cos(Math.toRadians(lat2)) *
                    Math.cos(Math.toRadians(theta))
            dist = Math.acos(dist)
            dist = Math.toDegrees(dist)
            dist *= 60.0 * 1.1515

            if (unit == DistanceUnit.KILOMETERS) {
                dist *= 1.609344
            }

            return dist
        }
    }
}
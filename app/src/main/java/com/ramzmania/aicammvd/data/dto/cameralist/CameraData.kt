package com.ramzmania.aicammvd.data.dto.cameralist
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CameraData (@Json(name = "Unique_id") val uniqueId: String,
                       @Json(name = "District") val district: String,
                       @Json(name = "Location") val location: String,
                       @Json(name = "Lat") val latitude: Double,
                       @Json(name = "Long") val longitude: Double,
                       @Json(name = "Type") val type: String)
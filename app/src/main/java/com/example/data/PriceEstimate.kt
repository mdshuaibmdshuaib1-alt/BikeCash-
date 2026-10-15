package com.example.data

data class PriceEstimate(
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val currency: String = "INR",
    val status: String = "indicative",
    val message: String = "Unable to estimate reliably yet",
    val lastUpdated: Long = System.currentTimeMillis()
)

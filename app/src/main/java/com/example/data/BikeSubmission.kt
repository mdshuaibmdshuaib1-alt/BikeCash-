package com.example.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class BikeSubmission(
    @DocumentId val id: String = "",
    val brand: String = "",
    val model: String = "",
    val registrationYear: String = "",
    val registrationState: String = "",
    val kilometers: Long = 0L,
    val condition: String = "",
    val expectedPrice: Double = 0.0,
    val description: String = "",
    val photos: List<String> = emptyList(),
    val sellerName: String = "",
    val sellerMobile: String = "",
    val sellerCity: String = "",
    val sellerArea: String = "",
    val sellerAddress: String = "",
    val status: String = "pending_review",
    val userId: String = "",
    val adminNotes: List<String> = emptyList(),
    val auditHistory: List<String> = emptyList(),
    val inspectionDate: com.google.firebase.Timestamp? = null,
    val inspectionNotes: String = "",
    val offerPrice: Double? = null,
    val paymentStatus: String = "not_paid",
    val purchaseDate: com.google.firebase.Timestamp? = null,
    val saleDetails: SaleDetails? = null,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null,
    val estimate: PriceEstimate? = null
)

data class SaleDetails(
    val askingPrice: Double = 0.0,
    val sellingPrice: Double = 0.0,
    val saleDate: com.google.firebase.Timestamp? = null,
    val buyerDetails: String = "",
    val status: String = "not_listed"
)

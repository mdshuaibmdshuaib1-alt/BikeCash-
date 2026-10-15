package com.example.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class Expense(
    @DocumentId val id: String = "",
    val amount: Double = 0.0,
    val category: String = "",
    val description: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null
)

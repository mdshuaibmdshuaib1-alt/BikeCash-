package com.example.data

import android.content.Context
import com.example.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.data.BikeSubmission
import com.example.data.Expense
import com.google.firebase.auth.FirebaseAuth

class AdminRepository(private val db: FirebaseFirestore) {
    constructor(context: Context) : this(
        BikeRepository.resolveFirestore(context)
    )

    suspend fun getAllSubmissions(): List<BikeSubmission> {
        return db.collection("bike_submissions").get().await().toObjects(BikeSubmission::class.java)
    }

    suspend fun addExpense(submissionId: String, expense: Expense) {
        db.collection("bike_submissions").document(submissionId)
            .collection("expenses").add(expense).await()
    }

    suspend fun getExpenses(submissionId: String): List<Expense> {
        return db.collection("bike_submissions").document(submissionId)
            .collection("expenses").get().await().toObjects(Expense::class.java)
    }

    suspend fun updateSubmission(submissionId: String, updates: Map<String, Any>) {
        db.collection("bike_submissions").document(submissionId)
            .update(updates).await()
    }

    suspend fun checkIsAdmin(uid: String): Boolean {
        val doc = db.collection("admins").document(uid).get().await()
        return doc.getBoolean("active") == true
    }
}

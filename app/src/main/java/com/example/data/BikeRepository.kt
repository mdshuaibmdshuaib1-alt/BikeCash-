package com.example.data

import android.content.Context
import android.net.Uri
import com.example.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class BikeRepository(private val db: FirebaseFirestore) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()

    suspend fun ensureUserId(): String {
        val current = auth.currentUser
        if (current != null) {
            return current.uid
        }
        return try {
            val result = auth.signInAnonymously().await()
            result.user?.uid ?: "anonymous_${System.currentTimeMillis()}"
        } catch (e: Exception) {
            "user_${System.currentTimeMillis()}"
        }
    }

    suspend fun uploadPhoto(uri: Uri): String {
        val uid = ensureUserId()
        val ref = storage.reference.child("bike_photos/$uid/${System.currentTimeMillis()}.jpg")
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }

    suspend fun submitBike(submission: BikeSubmission): String {
        val uid = ensureUserId()
        val submissionWithUserId = submission.copy(userId = uid)
        val docRef = db.collection("bike_submissions").add(submissionWithUserId).await()
        return docRef.id
    }
}

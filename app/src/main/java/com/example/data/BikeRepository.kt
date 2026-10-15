package com.example.data

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class BikeRepository(private val db: FirebaseFirestore) {
    constructor(context: Context) : this(
        resolveFirestore(context)
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
            result.user?.uid ?: "user_${System.currentTimeMillis()}"
        } catch (e: Exception) {
            Log.w("BikeRepository", "Anonymous auth failed or not enabled in console: ${e.message}")
            "user_${System.currentTimeMillis()}"
        }
    }

    suspend fun uploadPhotosSafe(uris: List<Uri>): List<String> {
        if (uris.isEmpty()) return emptyList()
        val uid = ensureUserId()
        val uploadedUrls = mutableListOf<String>()
        for ((index, uri) in uris.withIndex()) {
            try {
                val ref = storage.reference.child("bike_photos/$uid/${System.currentTimeMillis()}_$index.jpg")
                ref.putFile(uri).await()
                val url = ref.downloadUrl.await().toString()
                uploadedUrls.add(url)
            } catch (e: Exception) {
                Log.w("BikeRepository", "Could not upload photo ($uri): ${e.message}")
            }
        }
        return uploadedUrls
    }

    suspend fun uploadPhoto(uri: Uri): String {
        val uid = ensureUserId()
        val ref = storage.reference.child("bike_photos/$uid/${System.currentTimeMillis()}.jpg")
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }

    suspend fun submitBike(submission: BikeSubmission): String {
        val uid = ensureUserId()
        val submissionWithUserId = submission.copy(
            userId = uid,
            status = "pending_review"
        )
        return try {
            val docRef = db.collection("bike_submissions").add(submissionWithUserId).await()
            docRef.id
        } catch (e: Exception) {
            Log.e("BikeRepository", "Failed to add bike submission to Firestore", e)
            throw Exception(e.localizedMessage ?: "Failed to save submission to database")
        }
    }

    companion object {
        fun resolveFirestore(context: Context): FirebaseFirestore {
            val dbId = try {
                context.applicationContext.getString(R.string.firestore_database_id)
            } catch (e: Exception) {
                null
            }
            return if (!dbId.isNullOrBlank()) {
                try {
                    FirebaseFirestore.getInstance(dbId)
                } catch (e: Exception) {
                    FirebaseFirestore.getInstance()
                }
            } else {
                FirebaseFirestore.getInstance()
            }
        }
    }
}

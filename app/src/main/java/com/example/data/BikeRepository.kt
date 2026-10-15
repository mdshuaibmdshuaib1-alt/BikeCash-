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

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User must be signed in with Google before accessing Firestore.")
    }

    suspend fun uploadPhoto(uri: Uri): String {
        val uid = requireUserId()
        val ref = storage.reference.child("bike_photos/$uid/${System.currentTimeMillis()}.jpg")
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }

    suspend fun submitBike(submission: BikeSubmission): String {
        val uid = requireUserId()
        val submissionWithUserId = submission.copy(userId = uid)
        val docRef = db.collection("bike_submissions").add(submissionWithUserId).await()
        return docRef.id
    }
}

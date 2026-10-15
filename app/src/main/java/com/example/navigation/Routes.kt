package com.example.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object BikeDetails

@Serializable
object BikePhotos

@Serializable
object SellerContact

@Serializable
object Review

@Serializable
object HowItWorks

@Serializable
object AdminLogin

@Serializable
object AdminDashboard

@Serializable
data class AdminSubmissionDetail(val submissionId: String)

@Serializable
data class SubmissionSuccess(val submissionId: String)

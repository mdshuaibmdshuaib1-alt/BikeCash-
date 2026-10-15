package com.example.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.CustomCredential
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.example.R

@Composable
fun BikeCashButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(text, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun GoogleSignInButton(
    onAuthSuccess: () -> Unit,
    onAuthError: (String) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)
    var isLoading by remember { mutableStateOf(false) }

    BikeCashButton(
        onClick = {
            isLoading = true
            val clientId = context.getString(R.string.default_web_client_id)
            val signInOption = GetSignInWithGoogleOption.Builder(serverClientId = clientId).build()
            val request = GetCredentialRequest.Builder().addCredentialOption(signInOption).build()
            
            scope.launch {
                try {
                    val result = credentialManager.getCredential(context as android.app.Activity, request)
                    val credential = result.credential
                    if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
                        val authCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                        FirebaseAuth.getInstance().signInWithCredential(authCredential).await()
                        onAuthSuccess()
                    }
                } catch (e: Exception) {
                    onAuthError(e.message ?: "Auth failed")
                } finally {
                    isLoading = false
                }
            }
        },
        text = if (isLoading) "Signing in..." else "Sign in with Google to Submit",
        enabled = !isLoading
    )
}

@Composable
fun BikeCashOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary
        )
    )
}

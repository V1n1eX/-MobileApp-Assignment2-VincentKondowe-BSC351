package com.example.bdf.repository

import com.example.bdf.model.Donor
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

// Minimal Firestore repository. Requires `google-services.json` in `app/` and the
// Firebase project configured in the console. This implementation uses suspend
// functions and converts Firestore Task callbacks into coroutines.
class FirebaseRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun registerDonor(donor: Donor): Boolean = suspendCancellableCoroutine { cont ->
        val data = hashMapOf(
            "full_name" to donor.full_name,
            "phone" to donor.phone,
            "city" to donor.city,
            "blood_group" to donor.blood_group,
            "available" to donor.available,
            "last_donation" to donor.last_donation
        )

        db.collection("donors")
            .add(data)
            .addOnSuccessListener { _ ->
                if (!cont.isCompleted) cont.resume(true)
            }
            .addOnFailureListener { e ->
                if (!cont.isCompleted) cont.resumeWithException(e)
            }

        cont.invokeOnCancellation {
            // No direct cancellation support on Tasks here; left as-is for minimal setup
        }
    }

    suspend fun searchDonorsByBloodGroup(bloodGroup: String): List<Donor> = suspendCancellableCoroutine { cont ->
        db.collection("donors")
            .whereEqualTo("blood_group", bloodGroup)
            .get()
            .addOnSuccessListener { snapshot ->
                try {
                    val list = snapshot.documents.mapNotNull { doc ->
                        val fullName = doc.getString("full_name") ?: return@mapNotNull null
                        val phone = doc.getString("phone") ?: ""
                        val city = doc.getString("city") ?: ""
                        val bg = doc.getString("blood_group") ?: ""
                        val available = doc.getBoolean("available") ?: false
                        val lastDonation = doc.getString("last_donation")

                        // NOTE: the `Donor` model expects an Int id. Firestore document IDs are strings;
                        // we use 0 here for a minimal representation. Adjust model if you want to store
                        // Firestore doc id or use a different id type.
                        Donor(
                            id = 0,
                            full_name = fullName,
                            phone = phone,
                            city = city,
                            blood_group = bg,
                            available = available,
                            last_donation = lastDonation
                        )
                    }
                    if (!cont.isCompleted) cont.resume(list)
                } catch (e: Exception) {
                    if (!cont.isCompleted) cont.resumeWithException(e)
                }
            }
            .addOnFailureListener { e ->
                if (!cont.isCompleted) cont.resumeWithException(e)
            }

        cont.invokeOnCancellation {
            // left empty for minimal implementation
        }
    }
}

package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.R
import com.example.data.model.DamageReport
import com.example.data.model.OtherEquipment
import com.example.data.model.VehicleHeavy
import com.example.data.model.VehicleInspection
import com.example.data.model.VehicleLight
import com.example.data.model.WeldingMachine
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * Repositori data sederhana untuk menghubungkan aplikasi MPNFLEETWEB
 * dengan backend database cloud Firebase Firestore.
 */
class FirestoreBackendRepository(
    private val context: Context,
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(
        context.applicationContext.getString(R.string.firestore_database_id)
    )
) {
    companion object {
        private const val TAG = "FirestoreBackendRepo"
    }

    private val auth = Firebase.auth

    val currentUserId: String?
        get() = auth.currentUser?.uid

    val isUserAuthenticated: Boolean
        get() = auth.currentUser != null

    val databaseId: String
        get() = context.getString(R.string.firestore_database_id)

    /**
     * Menyimpan atau memperbarui data Kendaraan Ringan ke Firestore.
     */
    suspend fun saveVehicleLight(vehicle: VehicleLight, userId: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val docRef = firestore.collection("users")
                .document(userId)
                .collection("vehicles_light")
                .document(vehicle.id)

            val payload = mapOf(
                "userId" to userId,
                "id" to vehicle.id,
                "plateNumber" to vehicle.plateNumber,
                "model" to vehicle.model,
                "color" to vehicle.color,
                "year" to vehicle.year,
                "brand" to vehicle.brand,
                "engineCapacity" to vehicle.engineCapacity,
                "vehicleType" to vehicle.vehicleType,
                "chassisNumber" to vehicle.chassisNumber,
                "workArea" to vehicle.workArea,
                "badgeNumber" to vehicle.badgeNumber,
                "driverName" to vehicle.driverName,
                "taxExpiry" to vehicle.taxExpiry,
                "stnkExpiry" to vehicle.stnkExpiry,
                "kirExpiry" to vehicle.kirExpiry,
                "tpeExpiry" to vehicle.tpeExpiry,
                "redPassExpiry" to vehicle.redPassExpiry,
                "status" to vehicle.status,
                "updatedAt" to FieldValue.serverTimestamp()
            )

            docRef.set(payload).await()
            Log.d(TAG, "VehicleLight ${vehicle.id} successfully saved to Firestore")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error saving VehicleLight to Firestore: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Menyimpan atau memperbarui data Kendaraan Berat ke Firestore.
     */
    suspend fun saveVehicleHeavy(vehicle: VehicleHeavy, userId: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val docRef = firestore.collection("users")
                .document(userId)
                .collection("vehicles_heavy")
                .document(vehicle.id)

            val payload = mapOf(
                "userId" to userId,
                "id" to vehicle.id,
                "plateNumber" to vehicle.plateNumber,
                "model" to vehicle.model,
                "color" to vehicle.color,
                "year" to vehicle.year,
                "brand" to vehicle.brand,
                "engineCapacity" to vehicle.engineCapacity,
                "vehicleType" to vehicle.vehicleType,
                "chassisNumber" to vehicle.chassisNumber,
                "workArea" to vehicle.workArea,
                "badgeNumber" to vehicle.badgeNumber,
                "operatorName" to vehicle.operatorName,
                "taxExpiry" to vehicle.taxExpiry,
                "stnkExpiry" to vehicle.stnkExpiry,
                "kirExpiry" to vehicle.kirExpiry,
                "tpeExpiry" to vehicle.tpeExpiry,
                "redPassExpiry" to vehicle.redPassExpiry,
                "greenPassExpiry" to vehicle.greenPassExpiry,
                "siloExpiry" to vehicle.siloExpiry,
                "status" to vehicle.status,
                "updatedAt" to FieldValue.serverTimestamp()
            )

            docRef.set(payload).await()
            Log.d(TAG, "VehicleHeavy ${vehicle.id} successfully saved to Firestore")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error saving VehicleHeavy to Firestore: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Menyimpan laporan inspeksi kendaraan ke Firestore.
     */
    suspend fun saveInspection(inspection: VehicleInspection, userId: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val docId = if (inspection.id != 0L) inspection.id.toString() else "insp_${System.currentTimeMillis()}"
            val docRef = firestore.collection("users")
                .document(userId)
                .collection("inspections")
                .document(docId)

            val payload = mapOf(
                "userId" to userId,
                "inspectorName" to inspection.inspectorName,
                "toolId" to inspection.toolId,
                "inspectionDate" to inspection.inspectionDate,
                "inspectionLocation" to inspection.inspectionLocation,
                "findingsNotes" to inspection.findingsNotes,
                "findingsPhotoUrl" to inspection.findingsPhotoUrl,
                "inspectionResult" to inspection.inspectionResult,
                "createdAt" to FieldValue.serverTimestamp()
            )

            docRef.set(payload).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error saving Inspection to Firestore: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Menyimpan laporan kerusakan ke Firestore.
     */
    suspend fun saveDamageReport(damage: DamageReport, userId: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val docId = if (damage.id != 0L) damage.id.toString() else "dmg_${System.currentTimeMillis()}"
            val docRef = firestore.collection("users")
                .document(userId)
                .collection("damage_reports")
                .document(docId)

            val payload = mapOf(
                "userId" to userId,
                "toolNumber" to damage.toolNumber,
                "damageFindings" to damage.damageFindings,
                "reportedBy" to damage.reportedBy,
                "damageLocation" to damage.damageLocation,
                "reportDate" to damage.reportDate,
                "status" to damage.status,
                "updatedAt" to FieldValue.serverTimestamp()
            )

            docRef.set(payload).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error saving DamageReport to Firestore: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Sinkronisasi massal seluruh data armada lokal ke backend cloud Firestore.
     */
    suspend fun syncAllFleetToFirestore(
        userId: String,
        lights: List<VehicleLight>,
        heavies: List<VehicleHeavy>,
        weldings: List<WeldingMachine>,
        others: List<OtherEquipment>
    ): Result<Int> = withContext(Dispatchers.IO) {
        try {
            val batch = firestore.batch()
            var count = 0

            lights.forEach { v ->
                val ref = firestore.collection("users").document(userId)
                    .collection("vehicles_light").document(v.id)
                batch.set(ref, v)
                count++
            }

            heavies.forEach { v ->
                val ref = firestore.collection("users").document(userId)
                    .collection("vehicles_heavy").document(v.id)
                batch.set(ref, v)
                count++
            }

            weldings.forEach { w ->
                val ref = firestore.collection("users").document(userId)
                    .collection("welding_machines").document(w.id)
                batch.set(ref, w)
                count++
            }

            others.forEach { o ->
                val ref = firestore.collection("users").document(userId)
                    .collection("other_equipment").document(o.id.ifBlank { "eq_${o.unitNumber}" })
                batch.set(ref, o)
                count++
            }

            // Simpan catatan log sinkronisasi
            val logRef = firestore.collection("users").document(userId)
                .collection("sync_logs").document("last_sync")
            batch.set(
                logRef,
                mapOf(
                    "userId" to userId,
                    "lastSyncAt" to FieldValue.serverTimestamp(),
                    "totalItemsSynced" to count,
                    "status" to "SUCCESS"
                )
            )

            batch.commit().await()
            Log.d(TAG, "Successfully synced $count fleet items to Firestore backend")
            Result.success(count)
        } catch (e: Exception) {
            Log.e(TAG, "Batch sync to Firestore failed: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Mendengarkan (real-time stream) data Kendaraan Ringan dari Firestore.
     */
    fun observeCloudVehiclesLight(userId: String): Flow<List<VehicleLight>> {
        return firestore.collection("users")
            .document(userId)
            .collection("vehicles_light")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(VehicleLight::class.java)
            }
            .flowOn(Dispatchers.IO)
    }

    /**
     * Mendengarkan (real-time stream) data Kendaraan Berat dari Firestore.
     */
    fun observeCloudVehiclesHeavy(userId: String): Flow<List<VehicleHeavy>> {
        return firestore.collection("users")
            .document(userId)
            .collection("vehicles_heavy")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(VehicleHeavy::class.java)
            }
            .flowOn(Dispatchers.IO)
    }
}

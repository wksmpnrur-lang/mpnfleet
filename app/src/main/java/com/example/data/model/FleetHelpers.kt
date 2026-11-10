package com.example.data.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

enum class ExpirySeverity {
    EXPIRED,      // Merah / Danger
    EXPIRING_SOON,// Kuning / Warning (< 30 hari)
    VALID,        // Hijau / Safe
    NOT_AVAILABLE // Abu-abu
}

data class DocumentStatus(
    val title: String,
    val dateString: String,
    val severity: ExpirySeverity,
    val daysRemaining: Long
)

object ExpiryUtils {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun calculateStatus(dateStr: String?, docTitle: String): DocumentStatus {
        if (dateStr.isNullOrBlank() || dateStr == "-") {
            return DocumentStatus(docTitle, "-", ExpirySeverity.NOT_AVAILABLE, 0)
        }
        return try {
            val targetDate = dateFormat.parse(dateStr)
            val currentDate = Date()
            if (targetDate != null) {
                val diffMs = targetDate.time - currentDate.time
                val days = TimeUnit.MILLISECONDS.toDays(diffMs)
                val severity = when {
                    days < 0 -> ExpirySeverity.EXPIRED
                    days in 0..30 -> ExpirySeverity.EXPIRING_SOON
                    else -> ExpirySeverity.VALID
                }
                DocumentStatus(docTitle, dateStr, severity, days)
            } else {
                DocumentStatus(docTitle, dateStr, ExpirySeverity.NOT_AVAILABLE, 0)
            }
        } catch (_: Exception) {
            DocumentStatus(docTitle, dateStr, ExpirySeverity.NOT_AVAILABLE, 0)
        }
    }
}

enum class FleetCategory {
    ALL,
    KENDARAAN_RINGAN,
    KENDARAAN_BERAT,
    WELDING_MACHINE,
    ALAT_LAIN,
    EXPIRING_ALERT
}

enum class UserRole(val label: String, val badge: String) {
    ADMIN("Level Admin", "Admin"),
    STAFF("Level Staff", "Staff")
}

sealed class SelectedFleetDetail {
    data class LightVehicle(val item: VehicleLight) : SelectedFleetDetail()
    data class HeavyVehicle(val item: VehicleHeavy) : SelectedFleetDetail()
    data class Welding(val item: WeldingMachine) : SelectedFleetDetail()
    data class Other(val item: OtherEquipment) : SelectedFleetDetail()
    data class DriverDetail(val item: Driver) : SelectedFleetDetail()
    data class OperatorDetail(val item: Operator) : SelectedFleetDetail()
}

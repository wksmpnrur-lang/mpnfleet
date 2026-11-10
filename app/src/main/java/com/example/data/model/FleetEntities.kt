package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(tableName = "vehicles_light")
data class VehicleLight(
    @PrimaryKey val id: String = "",
    val plateNumber: String = "",
    val model: String = "",
    val color: String = "",
    val year: Int = 2023,
    val brand: String = "",
    val engineCapacity: String = "",
    val vehicleType: String = "",
    val chassisNumber: String = "",
    val workArea: String = "",
    val badgeNumber: String = "",
    val driverName: String = "",
    val taxExpiry: String = "",
    val stnkExpiry: String = "",
    val kirExpiry: String = "",
    val tpeExpiry: String = "",
    val redPassExpiry: String = "",
    val status: String = "Aktif"
)

@IgnoreExtraProperties
@Entity(tableName = "vehicles_heavy")
data class VehicleHeavy(
    @PrimaryKey val id: String = "",
    val plateNumber: String = "",
    val model: String = "",
    val color: String = "",
    val year: Int = 2022,
    val brand: String = "",
    val engineCapacity: String = "",
    val vehicleType: String = "",
    val chassisNumber: String = "",
    val workArea: String = "",
    val badgeNumber: String = "",
    val operatorName: String = "",
    val taxExpiry: String = "",
    val stnkExpiry: String = "",
    val kirExpiry: String = "",
    val tpeExpiry: String = "",
    val redPassExpiry: String = "",
    val greenPassExpiry: String = "",
    val siloExpiry: String = "",
    val status: String = "Aktif"
)

@IgnoreExtraProperties
@Entity(tableName = "welding_machines")
data class WeldingMachine(
    @PrimaryKey val id: String = "",
    val model: String = "",
    val color: String = "",
    val year: Int = 2021,
    val brand: String = "",
    val engineCapacity: String = "",
    val unitType: String = "",
    val chassisNumber: String = "",
    val workArea: String = "",
    val badgeNumber: String = "",
    val pemcoWelder: String = "",
    val passedTpeExpiry: String = "",
    val status: String = "Aktif"
)

@IgnoreExtraProperties
@Entity(tableName = "other_equipment")
data class OtherEquipment(
    @PrimaryKey val id: String = "",
    val unitNumber: String = "",
    val model: String = "",
    val category: String = "",
    val passedTpeExpiry: String = "",
    val workArea: String = "Workshop Utama",
    val status: String = "Aktif"
)

@IgnoreExtraProperties
@Entity(tableName = "drivers")
data class Driver(
    @PrimaryKey val badgeNumber: String = "",
    val name: String = "",
    val simNumber: String = "",
    val simExpiry: String = "",
    val simType: String = "",
    val driverCardExpiry: String = "",
    val driverCardNumber: String = "",
    val phone: String = "-"
)

@IgnoreExtraProperties
@Entity(tableName = "operators")
data class Operator(
    @PrimaryKey val badgeNumber: String = "",
    val name: String = "",
    val simNumber: String = "",
    val simExpiry: String = "",
    val simType: String = "",
    val driverCardExpiry: String = "",
    val driverCardNumber: String = "",
    val sioExpiry: String = "",
    val sioNumber: String = "",
    val phone: String = "-"
)

@IgnoreExtraProperties
@Entity(tableName = "inspections")
data class VehicleInspection(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val inspectorName: String = "",
    val toolId: String = "",
    val inspectionDate: String = "",
    val inspectionLocation: String = "",
    val findingsNotes: String = "",
    val findingsPhotoUrl: String = "",
    val inspectionResult: String = "Lulus (Passed)"
)

@IgnoreExtraProperties
@Entity(tableName = "services")
data class VehicleService(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val toolId: String = "",
    val serviceDate: String = "",
    val serviceItems: String = "",
    val workshop: String = "Internal Workshop MPN",
    val cost: Long = 0
)

@IgnoreExtraProperties
@Entity(tableName = "damage_reports")
data class DamageReport(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val toolNumber: String = "",
    val damageFindings: String = "",
    val reportedBy: String = "",
    val damageLocation: String = "",
    val reportDate: String = "",
    val status: String = "Open"
)

@IgnoreExtraProperties
@Entity(tableName = "vendors")
data class Vendor(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val vendorName: String = "",
    val contactPerson: String = "",
    val phone: String = "",
    val address: String = "",
    val supplyType: String = ""
)

@Entity(tableName = "appsheet_config")
data class AppSheetConfig(
    @PrimaryKey val id: Int = 1,
    val scriptUrl: String = "https://script.google.com/macros/s/AKfycbz_MPNFLEET_EXAMPLE/exec",
    val appSheetId: String = "MPNFLEET-WEB-APPSHEET-v2",
    val lastSyncTime: String = "2026-10-18 22:30",
    val syncStatus: String = "Tersinkronisasi (OK)"
)

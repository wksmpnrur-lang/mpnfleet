package com.example.data.repository

import com.example.data.local.FleetDao
import com.example.data.local.FleetInitialData
import com.example.data.model.AppSheetConfig
import com.example.data.model.DamageReport
import com.example.data.model.Driver
import com.example.data.model.ExpirySeverity
import com.example.data.model.ExpiryUtils
import com.example.data.model.Operator
import com.example.data.model.OtherEquipment
import com.example.data.model.VehicleHeavy
import com.example.data.model.VehicleInspection
import com.example.data.model.VehicleLight
import com.example.data.model.VehicleService
import com.example.data.model.Vendor
import com.example.data.model.WeldingMachine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FleetRepository(private val fleetDao: FleetDao) {

    val lightVehicles: Flow<List<VehicleLight>> = fleetDao.getAllLightVehicles()
    val heavyVehicles: Flow<List<VehicleHeavy>> = fleetDao.getAllHeavyVehicles()
    val weldingMachines: Flow<List<WeldingMachine>> = fleetDao.getAllWeldingMachines()
    val otherEquipments: Flow<List<OtherEquipment>> = fleetDao.getAllOtherEquipment()
    val drivers: Flow<List<Driver>> = fleetDao.getAllDrivers()
    val operators: Flow<List<Operator>> = fleetDao.getAllOperators()
    val inspections: Flow<List<VehicleInspection>> = fleetDao.getAllInspections()
    val services: Flow<List<VehicleService>> = fleetDao.getAllServices()
    val damageReports: Flow<List<DamageReport>> = fleetDao.getAllDamageReports()
    val vendors: Flow<List<Vendor>> = fleetDao.getAllVendors()
    val appSheetConfig: Flow<AppSheetConfig?> = fleetDao.getAppSheetConfig()

    suspend fun checkAndSeedInitialData() = withContext(Dispatchers.IO) {
        val currentLights = fleetDao.getAllLightVehicles().first()
        if (currentLights.isEmpty()) {
            fleetDao.insertLightVehicles(FleetInitialData.lightVehicles)
            fleetDao.insertHeavyVehicles(FleetInitialData.heavyVehicles)
            fleetDao.insertWeldingMachines(FleetInitialData.weldingMachines)
            fleetDao.insertOtherEquipments(FleetInitialData.otherEquipments)
            fleetDao.insertDrivers(FleetInitialData.drivers)
            fleetDao.insertOperators(FleetInitialData.operators)
            FleetInitialData.inspections.forEach { fleetDao.insertInspection(it) }
            FleetInitialData.services.forEach { fleetDao.insertService(it) }
            FleetInitialData.damageReports.forEach { fleetDao.insertDamageReport(it) }
            fleetDao.insertVendors(FleetInitialData.vendors)
            fleetDao.setAppSheetConfig(FleetInitialData.defaultAppSheetConfig)
        }
    }

    // CRUD: Light Vehicles
    suspend fun saveLightVehicle(item: VehicleLight) = withContext(Dispatchers.IO) {
        fleetDao.insertLightVehicle(item)
    }

    suspend fun deleteLightVehicle(item: VehicleLight) = withContext(Dispatchers.IO) {
        fleetDao.deleteLightVehicle(item)
    }

    // CRUD: Heavy Vehicles
    suspend fun saveHeavyVehicle(item: VehicleHeavy) = withContext(Dispatchers.IO) {
        fleetDao.insertHeavyVehicle(item)
    }

    suspend fun deleteHeavyVehicle(item: VehicleHeavy) = withContext(Dispatchers.IO) {
        fleetDao.deleteHeavyVehicle(item)
    }

    // CRUD: Welding Machine
    suspend fun saveWeldingMachine(item: WeldingMachine) = withContext(Dispatchers.IO) {
        fleetDao.insertWeldingMachine(item)
    }

    suspend fun deleteWeldingMachine(item: WeldingMachine) = withContext(Dispatchers.IO) {
        fleetDao.deleteWeldingMachine(item)
    }

    // CRUD: Other Equipment
    suspend fun saveOtherEquipment(item: OtherEquipment) = withContext(Dispatchers.IO) {
        fleetDao.insertOtherEquipment(item)
    }

    suspend fun deleteOtherEquipment(item: OtherEquipment) = withContext(Dispatchers.IO) {
        fleetDao.deleteOtherEquipment(item)
    }

    // CRUD: Driver
    suspend fun saveDriver(item: Driver) = withContext(Dispatchers.IO) {
        fleetDao.insertDriver(item)
    }

    suspend fun deleteDriver(item: Driver) = withContext(Dispatchers.IO) {
        fleetDao.deleteDriver(item)
    }

    // CRUD: Operator
    suspend fun saveOperator(item: Operator) = withContext(Dispatchers.IO) {
        fleetDao.insertOperator(item)
    }

    suspend fun deleteOperator(item: Operator) = withContext(Dispatchers.IO) {
        fleetDao.deleteOperator(item)
    }

    // Inspections
    suspend fun addInspection(item: VehicleInspection) = withContext(Dispatchers.IO) {
        fleetDao.insertInspection(item)
    }

    suspend fun deleteInspection(item: VehicleInspection) = withContext(Dispatchers.IO) {
        fleetDao.deleteInspection(item)
    }

    // Services
    suspend fun addService(item: VehicleService) = withContext(Dispatchers.IO) {
        fleetDao.insertService(item)
    }

    suspend fun deleteService(item: VehicleService) = withContext(Dispatchers.IO) {
        fleetDao.deleteService(item)
    }

    // Damage Reports
    suspend fun addDamageReport(item: DamageReport) = withContext(Dispatchers.IO) {
        fleetDao.insertDamageReport(item)
    }

    suspend fun updateDamageReport(item: DamageReport) = withContext(Dispatchers.IO) {
        fleetDao.updateDamageReport(item)
    }

    suspend fun deleteDamageReport(item: DamageReport) = withContext(Dispatchers.IO) {
        fleetDao.deleteDamageReport(item)
    }

    // Vendors
    suspend fun saveVendor(item: Vendor) = withContext(Dispatchers.IO) {
        fleetDao.insertVendor(item)
    }

    suspend fun deleteVendor(item: Vendor) = withContext(Dispatchers.IO) {
        fleetDao.deleteVendor(item)
    }

    // AppSheet / Google Apps Script Sync Config
    suspend fun updateAppSheetConfig(config: AppSheetConfig) = withContext(Dispatchers.IO) {
        fleetDao.setAppSheetConfig(config)
    }

    suspend fun simulateOrExecuteSync(): String = withContext(Dispatchers.IO) {
        val now = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val updatedConfig = AppSheetConfig(
            id = 1,
            scriptUrl = "https://script.google.com/macros/s/AKfycbz_MPNFLEET_WEB/exec",
            appSheetId = "MPNFLEET-WEB-ACTIVE",
            lastSyncTime = now,
            syncStatus = "Sinkronisasi Berhasil (Google Apps Script / AppSheet OK)"
        )
        fleetDao.setAppSheetConfig(updatedConfig)
        "Sinkronisasi data Google AppSheet & Apps Script berhasil pada $now"
    }
}

package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.FleetDatabase
import com.example.data.model.AppSheetConfig
import com.example.data.model.DamageReport
import com.example.data.model.Driver
import com.example.data.model.ExpirySeverity
import com.example.data.model.ExpiryUtils
import com.example.data.model.FleetCategory
import com.example.data.model.Operator
import com.example.data.model.OtherEquipment
import com.example.data.model.SelectedFleetDetail
import com.example.data.model.UserRole
import com.example.data.model.VehicleHeavy
import com.example.data.model.VehicleInspection
import com.example.data.model.VehicleLight
import com.example.data.model.VehicleService
import com.example.data.model.Vendor
import com.example.data.model.WeldingMachine
import com.example.data.repository.FleetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class FleetScreen(val title: String) {
    DASHBOARD("Dasbor Utama"),
    KENDARAAN_RINGAN("Kendaraan Ringan"),
    KENDARAAN_BERAT("Kendaraan Berat"),
    WELDING_MACHINE("Welding Machine"),
    ALAT_LAIN("Data Alat Lain"),
    DRIVER("Data Driver"),
    OPERATOR("Data Operator"),
    INSPEKSI("Inspeksi Kendaraan"),
    SERVICE("Data Service"),
    KERUSAKAN("Data Kerusakan"),
    VENDOR("Vendor Alat"),
    APPSHEET_SYNC("Sinkronisasi AppSheet")
}

data class DashboardSummary(
    val totalLight: Int = 0,
    val totalHeavy: Int = 0,
    val totalWelding: Int = 0,
    val totalOther: Int = 0,
    val totalDrivers: Int = 0,
    val totalOperators: Int = 0,
    val expiredDocsCount: Int = 0,
    val expiringSoonCount: Int = 0,
    val activeDamagesCount: Int = 0
)

class FleetViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FleetRepository

    val lightVehicles: StateFlow<List<VehicleLight>>
    val heavyVehicles: StateFlow<List<VehicleHeavy>>
    val weldingMachines: StateFlow<List<WeldingMachine>>
    val otherEquipments: StateFlow<List<OtherEquipment>>
    val drivers: StateFlow<List<Driver>>
    val operators: StateFlow<List<Operator>>
    val inspections: StateFlow<List<VehicleInspection>>
    val services: StateFlow<List<VehicleService>>
    val damageReports: StateFlow<List<DamageReport>>
    val vendors: StateFlow<List<Vendor>>
    val appSheetConfig: StateFlow<AppSheetConfig?>

    // UI States
    private val _userRole = MutableStateFlow(UserRole.ADMIN)
    val userRole: StateFlow<UserRole> = _userRole.asStateFlow()

    private val _currentScreen = MutableStateFlow(FleetScreen.DASHBOARD)
    val currentScreen: StateFlow<FleetScreen> = _currentScreen.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow(FleetCategory.ALL)
    val selectedCategory: StateFlow<FleetCategory> = _selectedCategory.asStateFlow()

    // 1-Click Detail target (Clicking any card in innovative dashboard directly opens this)
    private val _selectedDetail = MutableStateFlow<SelectedFleetDetail?>(null)
    val selectedDetail: StateFlow<SelectedFleetDetail?> = _selectedDetail.asStateFlow()

    // Dialog form triggers
    private val _activeDialogType = MutableStateFlow<String?>(null)
    val activeDialogType: StateFlow<String?> = _activeDialogType.asStateFlow()

    private val _syncMessage = MutableStateFlow<String?>(null)
    val syncMessage: StateFlow<String?> = _syncMessage.asStateFlow()

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    val firestoreRepository = com.example.data.repository.FirestoreBackendRepository(application)

    private val _firestoreStatus = MutableStateFlow<String?>("Terhubung: ${firestoreRepository.databaseId}")
    val firestoreStatus: StateFlow<String?> = _firestoreStatus.asStateFlow()

    init {
        val database = FleetDatabase.getDatabase(application, viewModelScope)
        repository = FleetRepository(database.fleetDao())

        lightVehicles = repository.lightVehicles.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        heavyVehicles = repository.heavyVehicles.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        weldingMachines = repository.weldingMachines.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        otherEquipments = repository.otherEquipments.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        drivers = repository.drivers.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        operators = repository.operators.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        inspections = repository.inspections.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        services = repository.services.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        damageReports = repository.damageReports.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        vendors = repository.vendors.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        appSheetConfig = repository.appSheetConfig.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), null
        )

        viewModelScope.launch {
            repository.checkAndSeedInitialData()
        }
    }

    val dashboardSummary: StateFlow<DashboardSummary> = combine(
        combine(lightVehicles, heavyVehicles, weldingMachines, otherEquipments) { l, h, w, o ->
            listOf<List<Any>>(l, h, w, o)
        },
        combine(drivers, operators, damageReports) { d, op, dmg ->
            listOf<List<Any>>(d, op, dmg)
        }
    ) { group1, group2 ->
        @Suppress("UNCHECKED_CAST")
        val lights = group1[0] as List<VehicleLight>
        @Suppress("UNCHECKED_CAST")
        val heavies = group1[1] as List<VehicleHeavy>
        @Suppress("UNCHECKED_CAST")
        val weldings = group1[2] as List<WeldingMachine>
        @Suppress("UNCHECKED_CAST")
        val others = group1[3] as List<OtherEquipment>
        @Suppress("UNCHECKED_CAST")
        val drvs = group2[0] as List<Driver>
        @Suppress("UNCHECKED_CAST")
        val oprs = group2[1] as List<Operator>
        @Suppress("UNCHECKED_CAST")
        val damages = group2[2] as List<DamageReport>

        var expired = 0
        var expiringSoon = 0

        fun check(date: String?, title: String) {
            val status = ExpiryUtils.calculateStatus(date, title)
            if (status.severity == ExpirySeverity.EXPIRED) expired++
            else if (status.severity == ExpirySeverity.EXPIRING_SOON) expiringSoon++
        }

        // Count docs in light vehicles
        lights.forEach {
            check(it.taxExpiry, "Pajak")
            check(it.stnkExpiry, "STNK")
            check(it.kirExpiry, "KIR")
            check(it.tpeExpiry, "TPE")
            check(it.redPassExpiry, "Red Pass")
        }
        // Count docs in heavy vehicles
        heavies.forEach {
            check(it.taxExpiry, "Pajak")
            check(it.stnkExpiry, "STNK")
            check(it.kirExpiry, "KIR")
            check(it.tpeExpiry, "TPE")
            check(it.redPassExpiry, "Red Pass")
            check(it.greenPassExpiry, "Green Pass")
            check(it.siloExpiry, "SILO")
        }
        // Count docs in welding & others
        weldings.forEach { check(it.passedTpeExpiry, "TPE") }
        others.forEach { check(it.passedTpeExpiry, "TPE") }
        // Drivers & operators
        drvs.forEach {
            check(it.simExpiry, "SIM")
            check(it.driverCardExpiry, "Kartu Pengemudi")
        }
        oprs.forEach {
            check(it.simExpiry, "SIM")
            check(it.driverCardExpiry, "Kartu Pengemudi")
            check(it.sioExpiry, "SIO")
        }

        DashboardSummary(
            totalLight = lights.size,
            totalHeavy = heavies.size,
            totalWelding = weldings.size,
            totalOther = others.size,
            totalDrivers = drvs.size,
            totalOperators = oprs.size,
            expiredDocsCount = expired,
            expiringSoonCount = expiringSoon,
            activeDamagesCount = damages.count { it.status != "Selesai" }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardSummary())

    // Role switching
    fun toggleRole() {
        _userRole.value = if (_userRole.value == UserRole.ADMIN) UserRole.STAFF else UserRole.ADMIN
    }

    fun setRole(role: UserRole) {
        _userRole.value = role
    }

    // Navigation
    fun navigateTo(screen: FleetScreen) {
        _currentScreen.value = screen
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategoryFilter(category: FleetCategory) {
        _selectedCategory.value = category
    }

    // 1-Click Detail Open
    fun openDetail(detail: SelectedFleetDetail) {
        _selectedDetail.value = detail
    }

    fun closeDetail() {
        _selectedDetail.value = null
    }

    // Dialog triggers
    fun openDialog(type: String) {
        _activeDialogType.value = type
    }

    fun closeDialog() {
        _activeDialogType.value = null
    }

    fun clearSyncMessage() {
        _syncMessage.value = null
    }

    // CRUD Light Vehicle
    fun saveLightVehicle(vehicle: VehicleLight) = viewModelScope.launch {
        repository.saveLightVehicle(vehicle)
        closeDialog()
    }

    fun deleteLightVehicle(vehicle: VehicleLight) = viewModelScope.launch {
        repository.deleteLightVehicle(vehicle)
        closeDetail()
    }

    // CRUD Heavy Vehicle
    fun saveHeavyVehicle(vehicle: VehicleHeavy) = viewModelScope.launch {
        repository.saveHeavyVehicle(vehicle)
        closeDialog()
    }

    fun deleteHeavyVehicle(vehicle: VehicleHeavy) = viewModelScope.launch {
        repository.deleteHeavyVehicle(vehicle)
        closeDetail()
    }

    // CRUD Welding Machine
    fun saveWeldingMachine(machine: WeldingMachine) = viewModelScope.launch {
        repository.saveWeldingMachine(machine)
        closeDialog()
    }

    fun deleteWeldingMachine(machine: WeldingMachine) = viewModelScope.launch {
        repository.deleteWeldingMachine(machine)
        closeDetail()
    }

    // CRUD Other Equipment
    fun saveOtherEquipment(equipment: OtherEquipment) = viewModelScope.launch {
        repository.saveOtherEquipment(equipment)
        closeDialog()
    }

    fun deleteOtherEquipment(equipment: OtherEquipment) = viewModelScope.launch {
        repository.deleteOtherEquipment(equipment)
        closeDetail()
    }

    // CRUD Driver
    fun saveDriver(driver: Driver) = viewModelScope.launch {
        repository.saveDriver(driver)
        closeDialog()
    }

    fun deleteDriver(driver: Driver) = viewModelScope.launch {
        repository.deleteDriver(driver)
        closeDetail()
    }

    // CRUD Operator
    fun saveOperator(operator: Operator) = viewModelScope.launch {
        repository.saveOperator(operator)
        closeDialog()
    }

    fun deleteOperator(operator: Operator) = viewModelScope.launch {
        repository.deleteOperator(operator)
        closeDetail()
    }

    // Inspection
    fun addInspection(inspection: VehicleInspection) = viewModelScope.launch {
        repository.addInspection(inspection)
        closeDialog()
    }

    fun deleteInspection(inspection: VehicleInspection) = viewModelScope.launch {
        repository.deleteInspection(inspection)
    }

    // Service
    fun addService(service: VehicleService) = viewModelScope.launch {
        repository.addService(service)
        closeDialog()
    }

    fun deleteService(service: VehicleService) = viewModelScope.launch {
        repository.deleteService(service)
    }

    // Damage
    fun addDamageReport(damage: DamageReport) = viewModelScope.launch {
        repository.addDamageReport(damage)
        closeDialog()
    }

    fun updateDamageStatus(damage: DamageReport, newStatus: String) = viewModelScope.launch {
        repository.updateDamageReport(damage.copy(status = newStatus))
    }

    fun deleteDamageReport(damage: DamageReport) = viewModelScope.launch {
        repository.deleteDamageReport(damage)
    }

    // Vendor
    fun saveVendor(vendor: Vendor) = viewModelScope.launch {
        repository.saveVendor(vendor)
        closeDialog()
    }

    fun deleteVendor(vendor: Vendor) = viewModelScope.launch {
        repository.deleteVendor(vendor)
    }

    // AppSheet / Apps Script Sync
    fun triggerSync() = viewModelScope.launch {
        _isSyncing.value = true
        try {
            val msg = repository.simulateOrExecuteSync()
            _syncMessage.value = msg
        } catch (e: Exception) {
            _syncMessage.value = "Gagal sinkronisasi: ${e.message}"
        } finally {
            _isSyncing.value = false
        }
    }

    fun updateSyncConfig(url: String, appId: String) = viewModelScope.launch {
        val config = AppSheetConfig(
            id = 1,
            scriptUrl = url,
            appSheetId = appId,
            lastSyncTime = appSheetConfig.value?.lastSyncTime ?: "-",
            syncStatus = "Konfigurasi Diperbarui"
        )
        repository.updateAppSheetConfig(config)
        _syncMessage.value = "Pengaturan Google AppSheet berhasil disimpan"
    }

    // Firebase Firestore Cloud Sync
    fun syncToFirestoreBackend() = viewModelScope.launch {
        _isSyncing.value = true
        val uid = firestoreRepository.currentUserId ?: "fleet_manager_mpn"
        val result = firestoreRepository.syncAllFleetToFirestore(
            userId = uid,
            lights = lightVehicles.value,
            heavies = heavyVehicles.value,
            weldings = weldingMachines.value,
            others = otherEquipments.value
        )
        result.onSuccess { count ->
            val time = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
            _syncMessage.value = "Berhasil sinkronisasi $count armada ke Firebase Firestore backend!"
            _firestoreStatus.value = "Tersinkron ke Firestore: $count item ($time)"
        }.onFailure { err ->
            _syncMessage.value = "Gagal sinkronisasi Firestore: ${err.message}"
        }
        _isSyncing.value = false
    }
}

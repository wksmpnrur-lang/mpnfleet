package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.AppSheetConfig
import com.example.data.model.DamageReport
import com.example.data.model.Driver
import com.example.data.model.Operator
import com.example.data.model.OtherEquipment
import com.example.data.model.VehicleHeavy
import com.example.data.model.VehicleInspection
import com.example.data.model.VehicleLight
import com.example.data.model.VehicleService
import com.example.data.model.Vendor
import com.example.data.model.WeldingMachine
import kotlinx.coroutines.flow.Flow

@Dao
interface FleetDao {
    // Kendaraan Ringan
    @Query("SELECT * FROM vehicles_light ORDER BY id ASC")
    fun getAllLightVehicles(): Flow<List<VehicleLight>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLightVehicles(items: List<VehicleLight>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLightVehicle(item: VehicleLight)

    @Update
    suspend fun updateLightVehicle(item: VehicleLight)

    @Delete
    suspend fun deleteLightVehicle(item: VehicleLight)

    // Kendaraan Berat
    @Query("SELECT * FROM vehicles_heavy ORDER BY id ASC")
    fun getAllHeavyVehicles(): Flow<List<VehicleHeavy>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeavyVehicles(items: List<VehicleHeavy>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeavyVehicle(item: VehicleHeavy)

    @Update
    suspend fun updateHeavyVehicle(item: VehicleHeavy)

    @Delete
    suspend fun deleteHeavyVehicle(item: VehicleHeavy)

    // Welding Machines
    @Query("SELECT * FROM welding_machines ORDER BY id ASC")
    fun getAllWeldingMachines(): Flow<List<WeldingMachine>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeldingMachines(items: List<WeldingMachine>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeldingMachine(item: WeldingMachine)

    @Update
    suspend fun updateWeldingMachine(item: WeldingMachine)

    @Delete
    suspend fun deleteWeldingMachine(item: WeldingMachine)

    // Alat Lain
    @Query("SELECT * FROM other_equipment ORDER BY category ASC, unitNumber ASC")
    fun getAllOtherEquipment(): Flow<List<OtherEquipment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOtherEquipments(items: List<OtherEquipment>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOtherEquipment(item: OtherEquipment)

    @Update
    suspend fun updateOtherEquipment(item: OtherEquipment)

    @Delete
    suspend fun deleteOtherEquipment(item: OtherEquipment)

    // Drivers
    @Query("SELECT * FROM drivers ORDER BY name ASC")
    fun getAllDrivers(): Flow<List<Driver>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrivers(items: List<Driver>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDriver(item: Driver)

    @Update
    suspend fun updateDriver(item: Driver)

    @Delete
    suspend fun deleteDriver(item: Driver)

    // Operators
    @Query("SELECT * FROM operators ORDER BY name ASC")
    fun getAllOperators(): Flow<List<Operator>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOperators(items: List<Operator>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOperator(item: Operator)

    @Update
    suspend fun updateOperator(item: Operator)

    @Delete
    suspend fun deleteOperator(item: Operator)

    // Inspections
    @Query("SELECT * FROM inspections ORDER BY inspectionDate DESC, id DESC")
    fun getAllInspections(): Flow<List<VehicleInspection>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInspection(item: VehicleInspection)

    @Delete
    suspend fun deleteInspection(item: VehicleInspection)

    // Services
    @Query("SELECT * FROM services ORDER BY serviceDate DESC, id DESC")
    fun getAllServices(): Flow<List<VehicleService>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(item: VehicleService)

    @Delete
    suspend fun deleteService(item: VehicleService)

    // Damage Reports
    @Query("SELECT * FROM damage_reports ORDER BY reportDate DESC, id DESC")
    fun getAllDamageReports(): Flow<List<DamageReport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDamageReport(item: DamageReport)

    @Update
    suspend fun updateDamageReport(item: DamageReport)

    @Delete
    suspend fun deleteDamageReport(item: DamageReport)

    // Vendors
    @Query("SELECT * FROM vendors ORDER BY vendorName ASC")
    fun getAllVendors(): Flow<List<Vendor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVendors(items: List<Vendor>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVendor(item: Vendor)

    @Delete
    suspend fun deleteVendor(item: Vendor)

    // AppSheet Config
    @Query("SELECT * FROM appsheet_config WHERE id = 1 LIMIT 1")
    fun getAppSheetConfig(): Flow<AppSheetConfig?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAppSheetConfig(config: AppSheetConfig)
}

package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        VehicleLight::class,
        VehicleHeavy::class,
        WeldingMachine::class,
        OtherEquipment::class,
        Driver::class,
        Operator::class,
        VehicleInspection::class,
        VehicleService::class,
        DamageReport::class,
        Vendor::class,
        AppSheetConfig::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FleetDatabase : RoomDatabase() {
    abstract fun fleetDao(): FleetDao

    companion object {
        @Volatile
        private var INSTANCE: FleetDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): FleetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FleetDatabase::class.java,
                    "mpnfleet_database"
                )
                    .addCallback(FleetDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class FleetDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.fleetDao())
                }
            }
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            // Ensure data exists
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    // Check if light vehicles empty
                    // InitialData will be inserted if empty
                }
            }
        }

        private suspend fun populateDatabase(dao: FleetDao) {
            dao.insertLightVehicles(FleetInitialData.lightVehicles)
            dao.insertHeavyVehicles(FleetInitialData.heavyVehicles)
            dao.insertWeldingMachines(FleetInitialData.weldingMachines)
            dao.insertOtherEquipments(FleetInitialData.otherEquipments)
            dao.insertDrivers(FleetInitialData.drivers)
            dao.insertOperators(FleetInitialData.operators)
            FleetInitialData.inspections.forEach { dao.insertInspection(it) }
            FleetInitialData.services.forEach { dao.insertService(it) }
            FleetInitialData.damageReports.forEach { dao.insertDamageReport(it) }
            dao.insertVendors(FleetInitialData.vendors)
            dao.setAppSheetConfig(FleetInitialData.defaultAppSheetConfig)
        }
    }
}

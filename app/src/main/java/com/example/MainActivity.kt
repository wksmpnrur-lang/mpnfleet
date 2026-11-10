package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.SelectedFleetDetail
import com.example.ui.AddDamageDialog
import com.example.ui.AddDriverDialog
import com.example.ui.AddHeavyVehicleDialog
import com.example.ui.AddInspectionDialog
import com.example.ui.AddLightVehicleDialog
import com.example.ui.AddOperatorDialog
import com.example.ui.AddOtherEquipmentDialog
import com.example.ui.AddServiceDialog
import com.example.ui.AddVendorDialog
import com.example.ui.AddWeldingDialog
import com.example.ui.AppSheetSyncScreen
import com.example.ui.DamageReportsScreen
import com.example.ui.DashboardScreen
import com.example.ui.DriversScreen
import com.example.ui.FleetScreen
import com.example.ui.FleetViewModel
import com.example.ui.HeavyVehiclesScreen
import com.example.ui.InspectionsScreen
import com.example.ui.LightVehiclesScreen
import com.example.ui.OperatorsScreen
import com.example.ui.OtherEquipmentScreen
import com.example.ui.ServicesScreen
import com.example.ui.VehicleDetailDialog
import com.example.ui.VendorsScreen
import com.example.ui.WeldingMachineScreen
import com.example.ui.theme.FleetLightBlueBackground
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    color = FleetLightBlueBackground
                ) {
                    FleetApp()
                }
            }
        }
    }
}

@Composable
fun FleetApp(viewModel: FleetViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val selectedDetail by viewModel.selectedDetail.collectAsState()
    val activeDialogType by viewModel.activeDialogType.collectAsState()
    val userRole by viewModel.userRole.collectAsState()

    var prefillToolId by remember { mutableStateOf("") }

    // Subscreen back handler
    if (currentScreen != FleetScreen.DASHBOARD) {
        BackHandler {
            viewModel.navigateTo(FleetScreen.DASHBOARD)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Main Screen Routing
        when (currentScreen) {
            FleetScreen.DASHBOARD -> DashboardScreen(viewModel = viewModel)
            FleetScreen.KENDARAAN_RINGAN -> LightVehiclesScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateTo(FleetScreen.DASHBOARD) }
            )
            FleetScreen.KENDARAAN_BERAT -> HeavyVehiclesScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateTo(FleetScreen.DASHBOARD) }
            )
            FleetScreen.WELDING_MACHINE -> WeldingMachineScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateTo(FleetScreen.DASHBOARD) }
            )
            FleetScreen.ALAT_LAIN -> OtherEquipmentScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateTo(FleetScreen.DASHBOARD) }
            )
            FleetScreen.DRIVER -> DriversScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateTo(FleetScreen.DASHBOARD) }
            )
            FleetScreen.OPERATOR -> OperatorsScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateTo(FleetScreen.DASHBOARD) }
            )
            FleetScreen.INSPEKSI -> InspectionsScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateTo(FleetScreen.DASHBOARD) }
            )
            FleetScreen.SERVICE -> ServicesScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateTo(FleetScreen.DASHBOARD) }
            )
            FleetScreen.KERUSAKAN -> DamageReportsScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateTo(FleetScreen.DASHBOARD) }
            )
            FleetScreen.VENDOR -> VendorsScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateTo(FleetScreen.DASHBOARD) }
            )
            FleetScreen.APPSHEET_SYNC -> AppSheetSyncScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateTo(FleetScreen.DASHBOARD) }
            )
        }

        // 1-Click Detail Dialog (Opens directly when any vehicle or equipment card is clicked)
        selectedDetail?.let { detail ->
            VehicleDetailDialog(
                detail = detail,
                userRole = userRole,
                onDismiss = { viewModel.closeDetail() },
                onAddInspection = { toolId ->
                    prefillToolId = toolId
                    viewModel.openDialog("INSPECTION")
                },
                onAddService = { toolId ->
                    prefillToolId = toolId
                    viewModel.openDialog("SERVICE")
                },
                onReportDamage = { toolId ->
                    prefillToolId = toolId
                    viewModel.openDialog("DAMAGE")
                },
                onDelete = {
                    when (detail) {
                        is SelectedFleetDetail.LightVehicle -> viewModel.deleteLightVehicle(detail.item)
                        is SelectedFleetDetail.HeavyVehicle -> viewModel.deleteHeavyVehicle(detail.item)
                        is SelectedFleetDetail.Welding -> viewModel.deleteWeldingMachine(detail.item)
                        is SelectedFleetDetail.Other -> viewModel.deleteOtherEquipment(detail.item)
                        is SelectedFleetDetail.DriverDetail -> viewModel.deleteDriver(detail.item)
                        is SelectedFleetDetail.OperatorDetail -> viewModel.deleteOperator(detail.item)
                    }
                }
            )
        }

        // Active Dialog Forms
        when (activeDialogType) {
            "LIGHT" -> AddLightVehicleDialog(
                onDismiss = { viewModel.closeDialog() },
                onSave = { viewModel.saveLightVehicle(it) }
            )
            "HEAVY" -> AddHeavyVehicleDialog(
                onDismiss = { viewModel.closeDialog() },
                onSave = { viewModel.saveHeavyVehicle(it) }
            )
            "WELDING" -> AddWeldingDialog(
                onDismiss = { viewModel.closeDialog() },
                onSave = { viewModel.saveWeldingMachine(it) }
            )
            "OTHER" -> AddOtherEquipmentDialog(
                onDismiss = { viewModel.closeDialog() },
                onSave = { viewModel.saveOtherEquipment(it) }
            )
            "DRIVER" -> AddDriverDialog(
                onDismiss = { viewModel.closeDialog() },
                onSave = { viewModel.saveDriver(it) }
            )
            "OPERATOR" -> AddOperatorDialog(
                onDismiss = { viewModel.closeDialog() },
                onSave = { viewModel.saveOperator(it) }
            )
            "INSPECTION" -> AddInspectionDialog(
                initialToolId = prefillToolId,
                onDismiss = {
                    prefillToolId = ""
                    viewModel.closeDialog()
                },
                onSave = {
                    prefillToolId = ""
                    viewModel.addInspection(it)
                }
            )
            "SERVICE" -> AddServiceDialog(
                initialToolId = prefillToolId,
                onDismiss = {
                    prefillToolId = ""
                    viewModel.closeDialog()
                },
                onSave = {
                    prefillToolId = ""
                    viewModel.addService(it)
                }
            )
            "DAMAGE" -> AddDamageDialog(
                initialToolId = prefillToolId,
                onDismiss = {
                    prefillToolId = ""
                    viewModel.closeDialog()
                },
                onSave = {
                    prefillToolId = ""
                    viewModel.addDamageReport(it)
                }
            )
            "VENDOR" -> AddVendorDialog(
                onDismiss = { viewModel.closeDialog() },
                onSave = { viewModel.saveVendor(it) }
            )
        }
    }
}

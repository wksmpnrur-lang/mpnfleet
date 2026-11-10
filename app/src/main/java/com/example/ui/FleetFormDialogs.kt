package com.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
import com.example.ui.theme.FleetDarkText
import com.example.ui.theme.FleetPrimary

@Composable
fun AddLightVehicleDialog(
    onDismiss: () -> Unit,
    onSave: (VehicleLight) -> Unit
) {
    var id by remember { mutableStateOf("KR-0105") }
    var plateNumber by remember { mutableStateOf("B 9801 MPN") }
    var model by remember { mutableStateOf("Hilux 4x4 Double Cabin") }
    var color by remember { mutableStateOf("Putih") }
    var year by remember { mutableStateOf("2023") }
    var brand by remember { mutableStateOf("Toyota") }
    var engineCapacity by remember { mutableStateOf("2400 cc") }
    var vehicleType by remember { mutableStateOf("Double Cabin") }
    var chassisNumber by remember { mutableStateOf("MHF21FE40N902812") }
    var workArea by remember { mutableStateOf("Area Tambang Utara") }
    var badgeNumber by remember { mutableStateOf("BDG-8812") }
    var driverName by remember { mutableStateOf("Ahmad Fauzi") }
    var taxExpiry by remember { mutableStateOf("2027-04-15") }
    var stnkExpiry by remember { mutableStateOf("2027-04-15") }
    var kirExpiry by remember { mutableStateOf("2026-12-10") }
    var tpeExpiry by remember { mutableStateOf("2026-12-20") }
    var redPassExpiry by remember { mutableStateOf("2026-11-30") }

    BaseFormDialog(
        title = "Tambah Kendaraan Ringan",
        onDismiss = onDismiss,
        onSave = {
            onSave(
                VehicleLight(
                    id = id.trim(),
                    plateNumber = plateNumber.trim(),
                    model = model.trim(),
                    color = color.trim(),
                    year = year.toIntOrNull() ?: 2023,
                    brand = brand.trim(),
                    engineCapacity = engineCapacity.trim(),
                    vehicleType = vehicleType.trim(),
                    chassisNumber = chassisNumber.trim(),
                    workArea = workArea.trim(),
                    badgeNumber = badgeNumber.trim(),
                    driverName = driverName.trim(),
                    taxExpiry = taxExpiry.trim(),
                    stnkExpiry = stnkExpiry.trim(),
                    kirExpiry = kirExpiry.trim(),
                    tpeExpiry = tpeExpiry.trim(),
                    redPassExpiry = redPassExpiry.trim()
                )
            )
        }
    ) {
        OutlinedTextField(value = id, onValueChange = { id = it }, label = { Text("Nomor ID Kendaraan") }, modifier = Modifier.fillMaxWidth().testTag("input_vehicle_id"))
        OutlinedTextField(value = plateNumber, onValueChange = { plateNumber = it }, label = { Text("Nomor Polisi") }, modifier = Modifier.fillMaxWidth().testTag("input_plate_number"))
        OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Model") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = brand, onValueChange = { brand = it }, label = { Text("Merek") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = color, onValueChange = { color = it }, label = { Text("Warna") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = year, onValueChange = { year = it }, label = { Text("Tahun") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = engineCapacity, onValueChange = { engineCapacity = it }, label = { Text("Kapasitas Mesin") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = vehicleType, onValueChange = { vehicleType = it }, label = { Text("Tipe") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = chassisNumber, onValueChange = { chassisNumber = it }, label = { Text("Nomor Rangka") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = workArea, onValueChange = { workArea = it }, label = { Text("Area Kerja") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = badgeNumber, onValueChange = { badgeNumber = it }, label = { Text("Nomor Badge Driver") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = driverName, onValueChange = { driverName = it }, label = { Text("Driver Name") }, modifier = Modifier.fillMaxWidth())
        Text("Data Dokumen (Format YYYY-MM-DD):", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FleetPrimary)
        OutlinedTextField(value = taxExpiry, onValueChange = { taxExpiry = it }, label = { Text("Expired Pajak") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = stnkExpiry, onValueChange = { stnkExpiry = it }, label = { Text("Expired STNK") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = kirExpiry, onValueChange = { kirExpiry = it }, label = { Text("Expired KIR") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tpeExpiry, onValueChange = { tpeExpiry = it }, label = { Text("Expired TPE") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = redPassExpiry, onValueChange = { redPassExpiry = it }, label = { Text("Expired Red Pass") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun AddHeavyVehicleDialog(
    onDismiss: () -> Unit,
    onSave: (VehicleHeavy) -> Unit
) {
    var id by remember { mutableStateOf("KB-0205") }
    var plateNumber by remember { mutableStateOf("KT 8199 MP") }
    var model by remember { mutableStateOf("Excavator SK200-10") }
    var color by remember { mutableStateOf("Tosca Kobelco") }
    var year by remember { mutableStateOf("2022") }
    var brand by remember { mutableStateOf("Kobelco") }
    var engineCapacity by remember { mutableStateOf("5120 cc (155 PS)") }
    var vehicleType by remember { mutableStateOf("Excavator") }
    var chassisNumber by remember { mutableStateOf("YQ15T129841") }
    var workArea by remember { mutableStateOf("Pit 4 Stripping Area") }
    var badgeNumber by remember { mutableStateOf("BDG-7703") }
    var operatorName by remember { mutableStateOf("Budi Santoso") }
    var taxExpiry by remember { mutableStateOf("2027-05-10") }
    var stnkExpiry by remember { mutableStateOf("2027-05-10") }
    var kirExpiry by remember { mutableStateOf("2026-11-20") }
    var tpeExpiry by remember { mutableStateOf("2026-12-05") }
    var redPassExpiry by remember { mutableStateOf("2026-12-15") }
    var greenPassExpiry by remember { mutableStateOf("2026-12-15") }
    var siloExpiry by remember { mutableStateOf("2027-01-20") }

    BaseFormDialog(
        title = "Tambah Kendaraan Berat",
        onDismiss = onDismiss,
        onSave = {
            onSave(
                VehicleHeavy(
                    id = id.trim(),
                    plateNumber = plateNumber.trim(),
                    model = model.trim(),
                    color = color.trim(),
                    year = year.toIntOrNull() ?: 2022,
                    brand = brand.trim(),
                    engineCapacity = engineCapacity.trim(),
                    vehicleType = vehicleType.trim(),
                    chassisNumber = chassisNumber.trim(),
                    workArea = workArea.trim(),
                    badgeNumber = badgeNumber.trim(),
                    operatorName = operatorName.trim(),
                    taxExpiry = taxExpiry.trim(),
                    stnkExpiry = stnkExpiry.trim(),
                    kirExpiry = kirExpiry.trim(),
                    tpeExpiry = tpeExpiry.trim(),
                    redPassExpiry = redPassExpiry.trim(),
                    greenPassExpiry = greenPassExpiry.trim(),
                    siloExpiry = siloExpiry.trim()
                )
            )
        }
    ) {
        OutlinedTextField(value = id, onValueChange = { id = it }, label = { Text("Nomor ID Kendaraan") }, modifier = Modifier.fillMaxWidth().testTag("input_heavy_id"))
        OutlinedTextField(value = plateNumber, onValueChange = { plateNumber = it }, label = { Text("Nomor Polisi") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Model") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = brand, onValueChange = { brand = it }, label = { Text("Merek") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = color, onValueChange = { color = it }, label = { Text("Warna") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = year, onValueChange = { year = it }, label = { Text("Tahun") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = engineCapacity, onValueChange = { engineCapacity = it }, label = { Text("Kapasitas Mesin") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = vehicleType, onValueChange = { vehicleType = it }, label = { Text("Tipe") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = chassisNumber, onValueChange = { chassisNumber = it }, label = { Text("Nomor Rangka") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = workArea, onValueChange = { workArea = it }, label = { Text("Area Kerja") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = badgeNumber, onValueChange = { badgeNumber = it }, label = { Text("No Badge Operator") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = operatorName, onValueChange = { operatorName = it }, label = { Text("Operator Name") }, modifier = Modifier.fillMaxWidth())
        Text("Data Dokumen (Format YYYY-MM-DD):", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FleetPrimary)
        OutlinedTextField(value = taxExpiry, onValueChange = { taxExpiry = it }, label = { Text("Expired Pajak") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = stnkExpiry, onValueChange = { stnkExpiry = it }, label = { Text("Expired STNK") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = kirExpiry, onValueChange = { kirExpiry = it }, label = { Text("Expired KIR") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tpeExpiry, onValueChange = { tpeExpiry = it }, label = { Text("Expired TPE") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = redPassExpiry, onValueChange = { redPassExpiry = it }, label = { Text("Expired Red Pass") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = greenPassExpiry, onValueChange = { greenPassExpiry = it }, label = { Text("Expired Green Pass") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = siloExpiry, onValueChange = { siloExpiry = it }, label = { Text("Expired SILO") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun AddWeldingDialog(
    onDismiss: () -> Unit,
    onSave: (WeldingMachine) -> Unit
) {
    var id by remember { mutableStateOf("WM-04") }
    var model by remember { mutableStateOf("Air Vantage 500") }
    var color by remember { mutableStateOf("Merah") }
    var year by remember { mutableStateOf("2022") }
    var brand by remember { mutableStateOf("Lincoln") }
    var engineCapacity by remember { mutableStateOf("500 Amp / Cummins") }
    var unitType by remember { mutableStateOf("Diesel Engine Welder") }
    var chassisNumber by remember { mutableStateOf("LNC-AV500-202201") }
    var workArea by remember { mutableStateOf("Pipeline Fab Area") }
    var badgeNumber by remember { mutableStateOf("BDG-6601") }
    var pemcoWelder by remember { mutableStateOf("Rudi Hartono") }
    var passedTpeExpiry by remember { mutableStateOf("2027-02-15") }

    BaseFormDialog(
        title = "Tambah Welding Machine",
        onDismiss = onDismiss,
        onSave = {
            onSave(
                WeldingMachine(
                    id = id.trim(),
                    model = model.trim(),
                    color = color.trim(),
                    year = year.toIntOrNull() ?: 2022,
                    brand = brand.trim(),
                    engineCapacity = engineCapacity.trim(),
                    unitType = unitType.trim(),
                    chassisNumber = chassisNumber.trim(),
                    workArea = workArea.trim(),
                    badgeNumber = badgeNumber.trim(),
                    pemcoWelder = pemcoWelder.trim(),
                    passedTpeExpiry = passedTpeExpiry.trim()
                )
            )
        }
    ) {
        OutlinedTextField(value = id, onValueChange = { id = it }, label = { Text("Nomor Unit") }, modifier = Modifier.fillMaxWidth().testTag("input_welding_id"))
        OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Model Unit") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = color, onValueChange = { color = it }, label = { Text("Warna Unit") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = year, onValueChange = { year = it }, label = { Text("Tahun Unit") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = brand, onValueChange = { brand = it }, label = { Text("Merek Unit") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = engineCapacity, onValueChange = { engineCapacity = it }, label = { Text("Kapasitas Mesin") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = unitType, onValueChange = { unitType = it }, label = { Text("Tipe Unit") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = chassisNumber, onValueChange = { chassisNumber = it }, label = { Text("Nomor Rangka Unit") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = workArea, onValueChange = { workArea = it }, label = { Text("Area Kerja") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = badgeNumber, onValueChange = { badgeNumber = it }, label = { Text("No Badge") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = pemcoWelder, onValueChange = { pemcoWelder = it }, label = { Text("Pemco / Welder") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = passedTpeExpiry, onValueChange = { passedTpeExpiry = it }, label = { Text("Masa Berlaku Passed TPE (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOtherEquipmentDialog(
    onDismiss: () -> Unit,
    onSave: (OtherEquipment) -> Unit
) {
    val categories = listOf(
        "Blower", "Asphal Cutter", "Concrete Mixer", "Jetting Pump",
        "Water Pump", "Hydrotest Pump", "Air Compressor", "Portable Genset",
        "Cable Locator", "Drop Hammer", "Jack Hummer"
    )

    var unitNumber by remember { mutableStateOf("BLW-03") }
    var model by remember { mutableStateOf("Axial Blower Portable 16\"") }
    var selectedCategory by remember { mutableStateOf(categories.first()) }
    var passedTpeExpiry by remember { mutableStateOf("2027-03-20") }
    var workArea by remember { mutableStateOf("Workshop Area Utama") }
    var expanded by remember { mutableStateOf(false) }

    BaseFormDialog(
        title = "Tambah Alat Lain",
        onDismiss = onDismiss,
        onSave = {
            val generatedId = "EQ-${selectedCategory.take(3).uppercase()}-${unitNumber.replace(" ", "")}"
            onSave(
                OtherEquipment(
                    id = generatedId,
                    unitNumber = unitNumber.trim(),
                    model = model.trim(),
                    category = selectedCategory,
                    passedTpeExpiry = passedTpeExpiry.trim(),
                    workArea = workArea.trim()
                )
            )
        }
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text("Kategori Alat Lain") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { cat ->
                    DropdownMenuItem(
                        text = { Text(cat) },
                        onClick = {
                            selectedCategory = cat
                            expanded = false
                        }
                    )
                }
            }
        }
        OutlinedTextField(value = unitNumber, onValueChange = { unitNumber = it }, label = { Text("Nomor Unit") }, modifier = Modifier.fillMaxWidth().testTag("input_other_unit"))
        OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Model Unit") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = passedTpeExpiry, onValueChange = { passedTpeExpiry = it }, label = { Text("Masa Berlaku Passed TPE (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = workArea, onValueChange = { workArea = it }, label = { Text("Area Kerja") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun AddDriverDialog(
    onDismiss: () -> Unit,
    onSave: (Driver) -> Unit
) {
    var badgeNumber by remember { mutableStateOf("BDG-8825") }
    var name by remember { mutableStateOf("Rian Firmansyah") }
    var simNumber by remember { mutableStateOf("920199120034") }
    var simExpiry by remember { mutableStateOf("2028-06-15") }
    var simType by remember { mutableStateOf("SIM B1 Umum") }
    var driverCardExpiry by remember { mutableStateOf("2027-08-20") }
    var driverCardNumber by remember { mutableStateOf("KP-MPN-2024-311") }
    var phone by remember { mutableStateOf("0812-3344-5566") }

    BaseFormDialog(
        title = "Tambah Data Driver",
        onDismiss = onDismiss,
        onSave = {
            onSave(
                Driver(
                    badgeNumber = badgeNumber.trim(),
                    name = name.trim(),
                    simNumber = simNumber.trim(),
                    simExpiry = simExpiry.trim(),
                    simType = simType.trim(),
                    driverCardExpiry = driverCardExpiry.trim(),
                    driverCardNumber = driverCardNumber.trim(),
                    phone = phone.trim()
                )
            )
        }
    ) {
        OutlinedTextField(value = badgeNumber, onValueChange = { badgeNumber = it }, label = { Text("No Badge") }, modifier = Modifier.fillMaxWidth().testTag("input_driver_badge"))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nama Driver") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = simNumber, onValueChange = { simNumber = it }, label = { Text("No SIM") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = simType, onValueChange = { simType = it }, label = { Text("Tipe SIM (SIM A / B1 Umum / B2)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = simExpiry, onValueChange = { simExpiry = it }, label = { Text("Tanggal Expired SIM") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = driverCardNumber, onValueChange = { driverCardNumber = it }, label = { Text("No Kartu Pengemudi") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = driverCardExpiry, onValueChange = { driverCardExpiry = it }, label = { Text("Tanggal Expired Kartu Pengemudi") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Nomor Telepon") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun AddOperatorDialog(
    onDismiss: () -> Unit,
    onSave: (Operator) -> Unit
) {
    var badgeNumber by remember { mutableStateOf("BDG-7725") }
    var name by remember { mutableStateOf("Surya Dinata") }
    var simNumber by remember { mutableStateOf("890312001192") }
    var simExpiry by remember { mutableStateOf("2028-09-10") }
    var simType by remember { mutableStateOf("SIM B2 Umum") }
    var driverCardExpiry by remember { mutableStateOf("2027-04-12") }
    var driverCardNumber by remember { mutableStateOf("KP-OPR-2024-089") }
    var sioExpiry by remember { mutableStateOf("2027-05-18") }
    var sioNumber by remember { mutableStateOf("SIO-KEMNAKER-EXC-I/2023/112") }
    var phone by remember { mutableStateOf("0852-7788-9900") }

    BaseFormDialog(
        title = "Tambah Data Operator",
        onDismiss = onDismiss,
        onSave = {
            onSave(
                Operator(
                    badgeNumber = badgeNumber.trim(),
                    name = name.trim(),
                    simNumber = simNumber.trim(),
                    simExpiry = simExpiry.trim(),
                    simType = simType.trim(),
                    driverCardExpiry = driverCardExpiry.trim(),
                    driverCardNumber = driverCardNumber.trim(),
                    sioExpiry = sioExpiry.trim(),
                    sioNumber = sioNumber.trim(),
                    phone = phone.trim()
                )
            )
        }
    ) {
        OutlinedTextField(value = badgeNumber, onValueChange = { badgeNumber = it }, label = { Text("Nomer Badge") }, modifier = Modifier.fillMaxWidth().testTag("input_operator_badge"))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nama Operator") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = simNumber, onValueChange = { simNumber = it }, label = { Text("Nomer SIM") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = simType, onValueChange = { simType = it }, label = { Text("Tipe SIM") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = simExpiry, onValueChange = { simExpiry = it }, label = { Text("Tanggal Expired SIM") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = driverCardNumber, onValueChange = { driverCardNumber = it }, label = { Text("Nomer Kartu Pengemudi") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = driverCardExpiry, onValueChange = { driverCardExpiry = it }, label = { Text("Tanggal Expired Kartu Pengemudi") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = sioNumber, onValueChange = { sioNumber = it }, label = { Text("Nomer SIO") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = sioExpiry, onValueChange = { sioExpiry = it }, label = { Text("Tanggal Expired SIO") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Nomor Telepon") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun AddInspectionDialog(
    initialToolId: String = "",
    onDismiss: () -> Unit,
    onSave: (VehicleInspection) -> Unit
) {
    var inspectorName by remember { mutableStateOf("Ir. Bagus Wicaksono") }
    var toolId by remember { mutableStateOf(initialToolId.ifBlank { "KR-0101" }) }
    var dateInspection by remember { mutableStateOf("2026-10-18") }
    var inspectionLocation by remember { mutableStateOf("Workshop Area Utama") }
    var findingsNotes by remember { mutableStateOf("Semua komponen utama normal, APAR dan First Aid siap pakai.") }
    var findingsPhotoUrl by remember { mutableStateOf("https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=400") }
    var inspectionResult by remember { mutableStateOf("Lulus (Passed)") }

    BaseFormDialog(
        title = "Catat Inspeksi Kendaraan / Alat",
        onDismiss = onDismiss,
        onSave = {
            onSave(
                VehicleInspection(
                    inspectorName = inspectorName.trim(),
                    toolId = toolId.trim(),
                    inspectionDate = dateInspection.trim(),
                    inspectionLocation = inspectionLocation.trim(),
                    findingsNotes = findingsNotes.trim(),
                    findingsPhotoUrl = findingsPhotoUrl.trim(),
                    inspectionResult = inspectionResult.trim()
                )
            )
        }
    ) {
        OutlinedTextField(value = inspectorName, onValueChange = { inspectorName = it }, label = { Text("Inspektor Name") }, modifier = Modifier.fillMaxWidth().testTag("input_inspector_name"))
        OutlinedTextField(value = toolId, onValueChange = { toolId = it }, label = { Text("Nomer ID Alat / Kendaraan") }, modifier = Modifier.fillMaxWidth().testTag("input_inspection_tool"))
        OutlinedTextField(value = dateInspection, onValueChange = { dateInspection = it }, label = { Text("Date Inspection (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = inspectionLocation, onValueChange = { inspectionLocation = it }, label = { Text("Lokasi Inspeksi") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = findingsNotes, onValueChange = { findingsNotes = it }, label = { Text("Catatan Temuan") }, modifier = Modifier.fillMaxWidth(), minLines = 2)
        OutlinedTextField(value = findingsPhotoUrl, onValueChange = { findingsPhotoUrl = it }, label = { Text("Foto Temuan (URL atau Keterangan)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = inspectionResult, onValueChange = { inspectionResult = it }, label = { Text("Hasil (Lulus / Catatan / Hold)") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun AddServiceDialog(
    initialToolId: String = "",
    onDismiss: () -> Unit,
    onSave: (VehicleService) -> Unit
) {
    var toolId by remember { mutableStateOf(initialToolId.ifBlank { "KR-0101" }) }
    var serviceDate by remember { mutableStateOf("2026-10-18") }
    var serviceItems by remember { mutableStateOf("Penggantian oli mesin, filter oli, balancing roda") }
    var workshop by remember { mutableStateOf("Internal Workshop MPN") }
    var cost by remember { mutableStateOf("1250000") }

    BaseFormDialog(
        title = "Catat Data Service",
        onDismiss = onDismiss,
        onSave = {
            onSave(
                VehicleService(
                    toolId = toolId.trim(),
                    serviceDate = serviceDate.trim(),
                    serviceItems = serviceItems.trim(),
                    workshop = workshop.trim(),
                    cost = cost.toLongOrNull() ?: 0L
                )
            )
        }
    ) {
        OutlinedTextField(value = toolId, onValueChange = { toolId = it }, label = { Text("Nomor ID Alat") }, modifier = Modifier.fillMaxWidth().testTag("input_service_tool"))
        OutlinedTextField(value = serviceDate, onValueChange = { serviceDate = it }, label = { Text("Service Date (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = serviceItems, onValueChange = { serviceItems = it }, label = { Text("Item Service") }, modifier = Modifier.fillMaxWidth(), minLines = 2)
        OutlinedTextField(value = workshop, onValueChange = { workshop = it }, label = { Text("Bengkel / Workshop") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = cost, onValueChange = { cost = it }, label = { Text("Biaya (Rp)") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun AddDamageDialog(
    initialToolId: String = "",
    onDismiss: () -> Unit,
    onSave: (DamageReport) -> Unit
) {
    var toolNumber by remember { mutableStateOf(initialToolId.ifBlank { "KR-0101" }) }
    var damageFindings by remember { mutableStateOf("Indikator oli menyala & rem tangan kurang pakem") }
    var reportedBy by remember { mutableStateOf("Staff Lapangan") }
    var damageLocation by remember { mutableStateOf("KM 15 Jalur Hauling") }
    var reportDate by remember { mutableStateOf("2026-10-18") }

    BaseFormDialog(
        title = "Lapor Temuan Kerusakan",
        onDismiss = onDismiss,
        onSave = {
            onSave(
                DamageReport(
                    toolNumber = toolNumber.trim(),
                    damageFindings = damageFindings.trim(),
                    reportedBy = reportedBy.trim(),
                    damageLocation = damageLocation.trim(),
                    reportDate = reportDate.trim(),
                    status = "Open"
                )
            )
        }
    ) {
        OutlinedTextField(value = toolNumber, onValueChange = { toolNumber = it }, label = { Text("No Alat / Kendaraan") }, modifier = Modifier.fillMaxWidth().testTag("input_damage_tool"))
        OutlinedTextField(value = damageFindings, onValueChange = { damageFindings = it }, label = { Text("Temuan Kerusakan") }, modifier = Modifier.fillMaxWidth(), minLines = 2)
        OutlinedTextField(value = reportedBy, onValueChange = { reportedBy = it }, label = { Text("Dilaporkan Oleh") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = damageLocation, onValueChange = { damageLocation = it }, label = { Text("Lokasi Kerusakan") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = reportDate, onValueChange = { reportDate = it }, label = { Text("Tanggal Laporan") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun AddVendorDialog(
    onDismiss: () -> Unit,
    onSave: (Vendor) -> Unit
) {
    var vendorName by remember { mutableStateOf("PT Solusi Alat Prima") }
    var contactPerson by remember { mutableStateOf("Iwan Setiawan") }
    var phone by remember { mutableStateOf("021-5544-7788") }
    var address by remember { mutableStateOf("Kawasan Industri Pulogadung, Jakarta") }
    var supplyType by remember { mutableStateOf("Sparepart & Kalibrasi Alat") }

    BaseFormDialog(
        title = "Tambah Data Vendor Alat",
        onDismiss = onDismiss,
        onSave = {
            onSave(
                Vendor(
                    vendorName = vendorName.trim(),
                    contactPerson = contactPerson.trim(),
                    phone = phone.trim(),
                    address = address.trim(),
                    supplyType = supplyType.trim()
                )
            )
        }
    ) {
        OutlinedTextField(value = vendorName, onValueChange = { vendorName = it }, label = { Text("Nama Vendor") }, modifier = Modifier.fillMaxWidth().testTag("input_vendor_name"))
        OutlinedTextField(value = contactPerson, onValueChange = { contactPerson = it }, label = { Text("Contact Person") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Nomor Telepon") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Alamat") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = supplyType, onValueChange = { supplyType = it }, label = { Text("Jenis Suplai / Servis") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun BaseFormDialog(
    title: String,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(vertical = 24.dp),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = FleetDarkText
                )

                content()

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(onClick = onDismiss) {
                        Text("Batal")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = onSave,
                        colors = ButtonDefaults.buttonColors(containerColor = FleetPrimary),
                        modifier = Modifier.testTag("dialog_submit_button")
                    ) {
                        Text("Simpan Data")
                    }
                }
            }
        }
    }
}

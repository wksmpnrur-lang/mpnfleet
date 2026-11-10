package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.DocumentStatus
import com.example.data.model.ExpirySeverity
import com.example.data.model.ExpiryUtils
import com.example.data.model.SelectedFleetDetail
import com.example.data.model.UserRole
import com.example.ui.theme.FleetDarkText
import com.example.ui.theme.FleetLightBlueBackground
import com.example.ui.theme.FleetMutedText
import com.example.ui.theme.FleetPrimary
import com.example.ui.theme.FleetStatusActive
import com.example.ui.theme.FleetStatusActiveBg
import com.example.ui.theme.FleetStatusDanger
import com.example.ui.theme.FleetStatusDangerBg
import com.example.ui.theme.FleetStatusWarning
import com.example.ui.theme.FleetStatusWarningBg

@Composable
fun VehicleDetailDialog(
    detail: SelectedFleetDetail,
    userRole: UserRole,
    onDismiss: () -> Unit,
    onAddInspection: (toolId: String) -> Unit,
    onAddService: (toolId: String) -> Unit,
    onReportDamage: (toolId: String) -> Unit,
    onDelete: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(vertical = 24.dp)
                .testTag("vehicle_detail_dialog"),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header with close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val icon = when (detail) {
                            is SelectedFleetDetail.LightVehicle -> Icons.Default.DirectionsCar
                            is SelectedFleetDetail.HeavyVehicle -> Icons.Default.Engineering
                            is SelectedFleetDetail.Welding -> Icons.Default.Build
                            is SelectedFleetDetail.Other -> Icons.Default.Handyman
                            is SelectedFleetDetail.DriverDetail -> Icons.Default.Person
                            is SelectedFleetDetail.OperatorDetail -> Icons.Default.Person
                        }
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(FleetPrimary.copy(alpha = 0.12f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = FleetPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            val title = when (detail) {
                                is SelectedFleetDetail.LightVehicle -> detail.item.id
                                is SelectedFleetDetail.HeavyVehicle -> detail.item.id
                                is SelectedFleetDetail.Welding -> detail.item.id
                                is SelectedFleetDetail.Other -> detail.item.unitNumber
                                is SelectedFleetDetail.DriverDetail -> detail.item.name
                                is SelectedFleetDetail.OperatorDetail -> detail.item.name
                            }
                            val subtitle = when (detail) {
                                is SelectedFleetDetail.LightVehicle -> "${detail.item.brand} ${detail.item.model}"
                                is SelectedFleetDetail.HeavyVehicle -> "${detail.item.brand} ${detail.item.model}"
                                is SelectedFleetDetail.Welding -> "${detail.item.brand} ${detail.item.model}"
                                is SelectedFleetDetail.Other -> "${detail.item.category} - ${detail.item.model}"
                                is SelectedFleetDetail.DriverDetail -> "Driver Badge: ${detail.item.badgeNumber}"
                                is SelectedFleetDetail.OperatorDetail -> "Operator Badge: ${detail.item.badgeNumber}"
                            }
                            Text(
                                text = title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = FleetDarkText
                            )
                            Text(
                                text = subtitle,
                                fontSize = 13.sp,
                                color = FleetMutedText
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("detail_close_button")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Tutup", tint = FleetMutedText)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Detail content per type
                when (detail) {
                    is SelectedFleetDetail.LightVehicle -> {
                        val v = detail.item
                        SpecsCard(
                            listOf(
                                "Nomor ID" to v.id,
                                "Nomor Polisi" to v.plateNumber,
                                "Model" to v.model,
                                "Warna" to v.color,
                                "Tahun" to v.year.toString(),
                                "Merek" to v.brand,
                                "Kapasitas Mesin" to v.engineCapacity,
                                "Tipe Kendaraan" to v.vehicleType,
                                "Nomor Rangka" to v.chassisNumber,
                                "Area Kerja" to v.workArea,
                                "Nomor Badge Driver" to v.badgeNumber,
                                "Nama Driver" to v.driverName,
                                "Status Unit" to v.status
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Data Dokumen & Perizinan",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = FleetPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        val docs = listOf(
                            ExpiryUtils.calculateStatus(v.taxExpiry, "Pajak"),
                            ExpiryUtils.calculateStatus(v.stnkExpiry, "STNK"),
                            ExpiryUtils.calculateStatus(v.kirExpiry, "KIR"),
                            ExpiryUtils.calculateStatus(v.tpeExpiry, "TPE"),
                            ExpiryUtils.calculateStatus(v.redPassExpiry, "Red Pass")
                        )
                        DocumentsStatusGrid(docs)
                    }

                    is SelectedFleetDetail.HeavyVehicle -> {
                        val v = detail.item
                        SpecsCard(
                            listOf(
                                "Nomor ID" to v.id,
                                "Nomor Polisi" to v.plateNumber,
                                "Model" to v.model,
                                "Warna" to v.color,
                                "Tahun" to v.year.toString(),
                                "Merek" to v.brand,
                                "Kapasitas Mesin" to v.engineCapacity,
                                "Tipe Alat Berat" to v.vehicleType,
                                "Nomor Rangka" to v.chassisNumber,
                                "Area Kerja" to v.workArea,
                                "No Badge Operator" to v.badgeNumber,
                                "Nama Operator" to v.operatorName,
                                "Status Unit" to v.status
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Data Dokumen & Sertifikasi K3/SILO",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = FleetPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        val docs = listOf(
                            ExpiryUtils.calculateStatus(v.taxExpiry, "Pajak"),
                            ExpiryUtils.calculateStatus(v.stnkExpiry, "STNK"),
                            ExpiryUtils.calculateStatus(v.kirExpiry, "KIR"),
                            ExpiryUtils.calculateStatus(v.tpeExpiry, "TPE"),
                            ExpiryUtils.calculateStatus(v.redPassExpiry, "Red Pass"),
                            ExpiryUtils.calculateStatus(v.greenPassExpiry, "Green Pass"),
                            ExpiryUtils.calculateStatus(v.siloExpiry, "SILO K3")
                        )
                        DocumentsStatusGrid(docs)
                    }

                    is SelectedFleetDetail.Welding -> {
                        val w = detail.item
                        SpecsCard(
                            listOf(
                                "Nomor Unit" to w.id,
                                "Model Unit" to w.model,
                                "Warna Unit" to w.color,
                                "Tahun Unit" to w.year.toString(),
                                "Merek Unit" to w.brand,
                                "Kapasitas Mesin" to w.engineCapacity,
                                "Tipe Unit" to w.unitType,
                                "Nomor Rangka" to w.chassisNumber,
                                "Area Kerja" to w.workArea,
                                "No Badge Pemco" to w.badgeNumber,
                                "Pemco / Welder" to w.pemcoWelder,
                                "Status Mesin" to w.status
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Masa Berlaku Dokumen TPE",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = FleetPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        DocumentsStatusGrid(
                            listOf(
                                ExpiryUtils.calculateStatus(w.passedTpeExpiry, "Passed TPE")
                            )
                        )
                    }

                    is SelectedFleetDetail.Other -> {
                        val o = detail.item
                        SpecsCard(
                            listOf(
                                "Nomor Unit" to o.unitNumber,
                                "Kategori Alat" to o.category,
                                "Model Unit" to o.model,
                                "Area Kerja" to o.workArea,
                                "Status" to o.status
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Masa Berlaku Passed TPE",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = FleetPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        DocumentsStatusGrid(
                            listOf(
                                ExpiryUtils.calculateStatus(o.passedTpeExpiry, "Passed TPE")
                            )
                        )
                    }

                    is SelectedFleetDetail.DriverDetail -> {
                        val d = detail.item
                        SpecsCard(
                            listOf(
                                "No Badge" to d.badgeNumber,
                                "Nama Driver" to d.name,
                                "No SIM" to d.simNumber,
                                "Tipe SIM" to d.simType,
                                "No Kartu Pengemudi" to d.driverCardNumber,
                                "Nomor Telepon" to d.phone
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Masa Berlaku Lisensi & Kartu",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = FleetPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        DocumentsStatusGrid(
                            listOf(
                                ExpiryUtils.calculateStatus(d.simExpiry, "Expired SIM"),
                                ExpiryUtils.calculateStatus(d.driverCardExpiry, "Expired Kartu Pengemudi")
                            )
                        )
                    }

                    is SelectedFleetDetail.OperatorDetail -> {
                        val op = detail.item
                        SpecsCard(
                            listOf(
                                "Nomer Badge" to op.badgeNumber,
                                "Nama Operator" to op.name,
                                "Nomer SIM" to op.simNumber,
                                "Tipe SIM" to op.simType,
                                "Nomer Kartu Pengemudi" to op.driverCardNumber,
                                "Nomer SIO" to op.sioNumber,
                                "Nomor Telepon" to op.phone
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Masa Berlaku Lisensi, Kartu & SIO",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = FleetPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        DocumentsStatusGrid(
                            listOf(
                                ExpiryUtils.calculateStatus(op.simExpiry, "Expired SIM"),
                                ExpiryUtils.calculateStatus(op.driverCardExpiry, "Expired Kartu Pengemudi"),
                                ExpiryUtils.calculateStatus(op.sioExpiry, "Expired SIO (Kemenaker)")
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Quick Action Buttons
                val targetId = when (detail) {
                    is SelectedFleetDetail.LightVehicle -> detail.item.id
                    is SelectedFleetDetail.HeavyVehicle -> detail.item.id
                    is SelectedFleetDetail.Welding -> detail.item.id
                    is SelectedFleetDetail.Other -> detail.item.unitNumber
                    is SelectedFleetDetail.DriverDetail -> detail.item.badgeNumber
                    is SelectedFleetDetail.OperatorDetail -> detail.item.badgeNumber
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { onAddInspection(targetId) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("action_inspect_button")
                    ) {
                        Icon(Icons.Default.VerifiedUser, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Inspeksi", fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = { onAddService(targetId) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("action_service_button")
                    ) {
                        Icon(Icons.Default.Build, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Service", fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = { onReportDamage(targetId) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("action_damage_button")
                    ) {
                        Icon(Icons.Default.ReportProblem, contentDescription = null, modifier = Modifier.size(16.dp), tint = FleetStatusDanger)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Kerusakan", fontSize = 12.sp, color = FleetStatusDanger)
                    }
                }

                if (userRole == UserRole.ADMIN) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onDelete,
                        colors = ButtonDefaults.buttonColors(containerColor = FleetStatusDangerBg, contentColor = FleetStatusDanger),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("action_delete_button")
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Hapus Data (Admin Role)", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
fun SpecsCard(specs: List<Pair<String, String>>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = FleetLightBlueBackground)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            specs.forEach { (label, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        color = FleetMutedText,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = value.ifBlank { "-" },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FleetDarkText,
                        modifier = Modifier.weight(1.3f)
                    )
                }
            }
        }
    }
}

@Composable
fun DocumentsStatusGrid(docs: List<DocumentStatus>) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        docs.forEach { doc ->
            val (bgColor, textColor, statusText) = when (doc.severity) {
                ExpirySeverity.EXPIRED -> Triple(
                    FleetStatusDangerBg,
                    FleetStatusDanger,
                    "KADALUARSA (${Math.abs(doc.daysRemaining)} hari lalu)"
                )
                ExpirySeverity.EXPIRING_SOON -> Triple(
                    FleetStatusWarningBg,
                    Color(0xFFB45309),
                    "Segera Habis (${doc.daysRemaining} hari lagi)"
                )
                ExpirySeverity.VALID -> Triple(
                    FleetStatusActiveBg,
                    FleetStatusActive,
                    "Berlaku (${doc.daysRemaining} hari lagi)"
                )
                ExpirySeverity.NOT_AVAILABLE -> Triple(
                    Color(0xFFF1F5F9),
                    Color(0xFF64748B),
                    "Tidak Ada Data"
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = bgColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = doc.title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = FleetDarkText
                        )
                        Text(
                            text = "Exp: ${doc.dateString}",
                            fontSize = 11.sp,
                            color = FleetMutedText
                        )
                    }
                    Text(
                        text = statusText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }
            }
        }
    }
}

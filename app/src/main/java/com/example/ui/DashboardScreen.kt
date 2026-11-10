package com.example.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ExpirySeverity
import com.example.data.model.ExpiryUtils
import com.example.data.model.FleetCategory
import com.example.data.model.OtherEquipment
import com.example.data.model.SelectedFleetDetail
import com.example.data.model.UserRole
import com.example.data.model.VehicleHeavy
import com.example.data.model.VehicleLight
import com.example.data.model.WeldingMachine
import com.example.ui.theme.FleetAdminPurple
import com.example.ui.theme.FleetAdminPurpleBg
import com.example.ui.theme.FleetBorder
import com.example.ui.theme.FleetDarkText
import com.example.ui.theme.FleetLightBlueBackground
import com.example.ui.theme.FleetMutedText
import com.example.ui.theme.FleetPrimary
import com.example.ui.theme.FleetStaffTeal
import com.example.ui.theme.FleetStaffTealBg
import com.example.ui.theme.FleetStatusActive
import com.example.ui.theme.FleetStatusActiveBg
import com.example.ui.theme.FleetStatusDanger
import com.example.ui.theme.FleetStatusDangerBg
import com.example.ui.theme.FleetStatusInfo
import com.example.ui.theme.FleetStatusInfoBg
import com.example.ui.theme.FleetStatusWarning
import com.example.ui.theme.FleetStatusWarningBg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: FleetViewModel
) {
    val summary by viewModel.dashboardSummary.collectAsState()
    val userRole by viewModel.userRole.collectAsState()
    val lightVehicles by viewModel.lightVehicles.collectAsState()
    val heavyVehicles by viewModel.heavyVehicles.collectAsState()
    val weldings by viewModel.weldingMachines.collectAsState()
    val others by viewModel.otherEquipments.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FleetLightBlueBackground)
    ) {
        // Innovative Header Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.mpnfleet_logo),
                        contentDescription = "MPNFLEETWEB Logo",
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "MPNFLEETWEB",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = FleetPrimary,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Industrial Fleet & Equipment",
                            fontSize = 11.sp,
                            color = FleetMutedText
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Role Switcher Pill
                    val (roleBg, roleColor, roleText, roleIcon) = if (userRole == UserRole.ADMIN) {
                        listOf(FleetAdminPurpleBg, FleetAdminPurple, "Admin", Icons.Default.AdminPanelSettings)
                    } else {
                        listOf(FleetStaffTealBg, FleetStaffTeal, "Staff", Icons.Default.SupervisorAccount)
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(roleBg as Color)
                            .clickable { viewModel.toggleRole() }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                            .testTag("role_switcher_pill"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = roleIcon as ImageVector,
                            contentDescription = null,
                            tint = roleColor as Color,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = roleText as String,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = roleColor
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { viewModel.navigateTo(FleetScreen.APPSHEET_SYNC) },
                        modifier = Modifier
                            .size(36.dp)
                            .background(FleetPrimary.copy(alpha = 0.1f), CircleShape)
                            .testTag("dashboard_sync_button")
                    ) {
                        Icon(Icons.Default.CloudSync, contentDescription = "Sync AppSheet", tint = FleetPrimary, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }

        // Scrollable Dashboard Body
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp)
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            // 1. KPI Summary Cards Carousel
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    KpiStatCard(
                        title = "Kendaraan Ringan",
                        value = "${summary.totalLight} Unit",
                        subtext = "Double/Single Cabin",
                        icon = Icons.Default.DirectionsCar,
                        color = FleetPrimary,
                        bgColor = FleetPrimary.copy(alpha = 0.1f),
                        onClick = { viewModel.navigateTo(FleetScreen.KENDARAAN_RINGAN) }
                    )
                }
                item {
                    KpiStatCard(
                        title = "Kendaraan Berat",
                        value = "${summary.totalHeavy} Unit",
                        subtext = "Excavator, DT, Crane",
                        icon = Icons.Default.Engineering,
                        color = Color(0xFFD97706),
                        bgColor = FleetStatusWarningBg,
                        onClick = { viewModel.navigateTo(FleetScreen.KENDARAAN_BERAT) }
                    )
                }
                item {
                    KpiStatCard(
                        title = "Mesin Las & Alat",
                        value = "${summary.totalWelding + summary.totalOther} Unit",
                        subtext = "Welding & 11 Alat Lain",
                        icon = Icons.Default.Build,
                        color = Color(0xFF2563EB),
                        bgColor = FleetStatusInfoBg,
                        onClick = { viewModel.navigateTo(FleetScreen.WELDING_MACHINE) }
                    )
                }
                item {
                    KpiStatCard(
                        title = "Dokumen Expired",
                        value = "${summary.expiredDocsCount} Dok",
                        subtext = "${summary.expiringSoonCount} Segera Habis",
                        icon = Icons.Default.Warning,
                        color = FleetStatusDanger,
                        bgColor = FleetStatusDangerBg,
                        onClick = { viewModel.setCategoryFilter(FleetCategory.EXPIRING_ALERT) }
                    )
                }
                item {
                    KpiStatCard(
                        title = "Personel Aktif",
                        value = "${summary.totalDrivers + summary.totalOperators} Org",
                        subtext = "${summary.totalDrivers} Driver / ${summary.totalOperators} Opr",
                        icon = Icons.Default.Person,
                        color = FleetStaffTeal,
                        bgColor = FleetStaffTealBg,
                        onClick = { viewModel.navigateTo(FleetScreen.DRIVER) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Search & Quick 1-Click Fleet Access Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Akses Langsung 1-Klik Data Armada",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = FleetDarkText
                            )
                            Text(
                                text = "Klik kendaraan mana saja untuk rincian lengkap & dokumen",
                                fontSize = 11.sp,
                                color = FleetMutedText
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(FleetPrimary.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(text = "1-Click Ready", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = FleetPrimary)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.setSearchQuery(it) },
                        placeholder = { Text("Cari ID, Plat Nomor, Model, Driver/Operator...", fontSize = 13.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = FleetMutedText) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("dashboard_search_input"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Category Filter Chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedCategory == FleetCategory.ALL,
                            onClick = { viewModel.setCategoryFilter(FleetCategory.ALL) },
                            label = { Text("Semua (${lightVehicles.size + heavyVehicles.size + weldings.size + others.size})", fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = FleetPrimary, selectedLabelColor = Color.White)
                        )
                        FilterChip(
                            selected = selectedCategory == FleetCategory.KENDARAAN_RINGAN,
                            onClick = { viewModel.setCategoryFilter(FleetCategory.KENDARAAN_RINGAN) },
                            label = { Text("Kendaraan Ringan (${lightVehicles.size})", fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = FleetPrimary, selectedLabelColor = Color.White)
                        )
                        FilterChip(
                            selected = selectedCategory == FleetCategory.KENDARAAN_BERAT,
                            onClick = { viewModel.setCategoryFilter(FleetCategory.KENDARAAN_BERAT) },
                            label = { Text("Kendaraan Berat (${heavyVehicles.size})", fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = FleetPrimary, selectedLabelColor = Color.White)
                        )
                        FilterChip(
                            selected = selectedCategory == FleetCategory.WELDING_MACHINE,
                            onClick = { viewModel.setCategoryFilter(FleetCategory.WELDING_MACHINE) },
                            label = { Text("Welding (${weldings.size})", fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = FleetPrimary, selectedLabelColor = Color.White)
                        )
                        FilterChip(
                            selected = selectedCategory == FleetCategory.ALAT_LAIN,
                            onClick = { viewModel.setCategoryFilter(FleetCategory.ALAT_LAIN) },
                            label = { Text("Alat Lain (${others.size})", fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = FleetPrimary, selectedLabelColor = Color.White)
                        )
                        FilterChip(
                            selected = selectedCategory == FleetCategory.EXPIRING_ALERT,
                            onClick = { viewModel.setCategoryFilter(FleetCategory.EXPIRING_ALERT) },
                            label = { Text("⚠️ Perlu Dokumen", fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = FleetStatusDanger, selectedLabelColor = Color.White)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 1-Click Cards List
                    val query = searchQuery.trim().lowercase()

                    val filteredLight = lightVehicles.filter {
                        (selectedCategory == FleetCategory.ALL || selectedCategory == FleetCategory.KENDARAAN_RINGAN) &&
                                (query.isBlank() || it.id.lowercase().contains(query) || it.plateNumber.lowercase().contains(query) || it.model.lowercase().contains(query) || it.driverName.lowercase().contains(query))
                    }

                    val filteredHeavy = heavyVehicles.filter {
                        (selectedCategory == FleetCategory.ALL || selectedCategory == FleetCategory.KENDARAAN_BERAT) &&
                                (query.isBlank() || it.id.lowercase().contains(query) || it.plateNumber.lowercase().contains(query) || it.model.lowercase().contains(query) || it.operatorName.lowercase().contains(query))
                    }

                    val filteredWelding = weldings.filter {
                        (selectedCategory == FleetCategory.ALL || selectedCategory == FleetCategory.WELDING_MACHINE) &&
                                (query.isBlank() || it.id.lowercase().contains(query) || it.model.lowercase().contains(query) || it.pemcoWelder.lowercase().contains(query))
                    }

                    val filteredOthers = others.filter {
                        (selectedCategory == FleetCategory.ALL || selectedCategory == FleetCategory.ALAT_LAIN) &&
                                (query.isBlank() || it.unitNumber.lowercase().contains(query) || it.model.lowercase().contains(query) || it.category.lowercase().contains(query))
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Light Vehicles
                        filteredLight.forEach { item ->
                            QuickFleetClickRow(
                                title = item.id,
                                subtitle = "${item.brand} ${item.model} • ${item.plateNumber}",
                                tag = item.driverName,
                                status = item.status,
                                icon = Icons.Default.DirectionsCar,
                                iconColor = FleetPrimary,
                                onClick = { viewModel.openDetail(SelectedFleetDetail.LightVehicle(item)) },
                                testTag = "quick_click_light_${item.id}"
                            )
                        }

                        // Heavy Vehicles
                        filteredHeavy.forEach { item ->
                            QuickFleetClickRow(
                                title = item.id,
                                subtitle = "${item.brand} ${item.model} • ${item.plateNumber}",
                                tag = item.operatorName,
                                status = item.status,
                                icon = Icons.Default.Engineering,
                                iconColor = Color(0xFFD97706),
                                onClick = { viewModel.openDetail(SelectedFleetDetail.HeavyVehicle(item)) },
                                testTag = "quick_click_heavy_${item.id}"
                            )
                        }

                        // Welding Machines
                        filteredWelding.forEach { item ->
                            QuickFleetClickRow(
                                title = item.id,
                                subtitle = "${item.brand} ${item.model} • ${item.unitType}",
                                tag = item.pemcoWelder,
                                status = item.status,
                                icon = Icons.Default.Build,
                                iconColor = Color(0xFF2563EB),
                                onClick = { viewModel.openDetail(SelectedFleetDetail.Welding(item)) },
                                testTag = "quick_click_welding_${item.id}"
                            )
                        }

                        // Other Equipments
                        filteredOthers.take(5).forEach { item ->
                            QuickFleetClickRow(
                                title = item.unitNumber,
                                subtitle = "${item.category} • ${item.model}",
                                tag = item.workArea,
                                status = item.status,
                                icon = Icons.Default.Handyman,
                                iconColor = FleetStatusInfo,
                                onClick = { viewModel.openDetail(SelectedFleetDetail.Other(item)) },
                                testTag = "quick_click_other_${item.unitNumber}"
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 3. Menu Utama di Dasbor
            Text(
                text = "Menu Utama Sistem",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = FleetDarkText,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MenuTile(
                        title = "Kendaraan Ringan",
                        count = "${summary.totalLight} Unit",
                        icon = Icons.Default.DirectionsCar,
                        color = FleetPrimary,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.navigateTo(FleetScreen.KENDARAAN_RINGAN) }
                    )
                    MenuTile(
                        title = "Kendaraan Berat",
                        count = "${summary.totalHeavy} Unit",
                        icon = Icons.Default.Engineering,
                        color = Color(0xFFD97706),
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.navigateTo(FleetScreen.KENDARAAN_BERAT) }
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MenuTile(
                        title = "Welding Machine",
                        count = "${summary.totalWelding} Unit",
                        icon = Icons.Default.Build,
                        color = Color(0xFF2563EB),
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.navigateTo(FleetScreen.WELDING_MACHINE) }
                    )
                    MenuTile(
                        title = "Data Alat Lain",
                        count = "${summary.totalOther} Unit (11 Kategori)",
                        icon = Icons.Default.Handyman,
                        color = FleetStatusInfo,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.navigateTo(FleetScreen.ALAT_LAIN) }
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MenuTile(
                        title = "Data Driver",
                        count = "${summary.totalDrivers} Pengemudi",
                        icon = Icons.Default.Person,
                        color = FleetStaffTeal,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.navigateTo(FleetScreen.DRIVER) }
                    )
                    MenuTile(
                        title = "Data Operator",
                        count = "${summary.totalOperators} Operator SIO",
                        icon = Icons.Default.SupervisorAccount,
                        color = Color(0xFF7C3AED),
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.navigateTo(FleetScreen.OPERATOR) }
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MenuTile(
                        title = "Inspeksi Kendaraan",
                        count = "Form & Riwayat",
                        icon = Icons.Default.VerifiedUser,
                        color = FleetStatusActive,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.navigateTo(FleetScreen.INSPEKSI) }
                    )
                    MenuTile(
                        title = "Data Service",
                        count = "Catatan Berkala",
                        icon = Icons.Default.Build,
                        color = FleetPrimary,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.navigateTo(FleetScreen.SERVICE) }
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MenuTile(
                        title = "Data Kerusakan",
                        count = "${summary.activeDamagesCount} Kasus Aktif",
                        icon = Icons.Default.ReportProblem,
                        color = FleetStatusDanger,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.navigateTo(FleetScreen.KERUSAKAN) }
                    )
                    MenuTile(
                        title = "Vendor Alat",
                        count = "Suplai & Sertifikasi",
                        icon = Icons.Default.Storefront,
                        color = Color(0xFF0F766E),
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.navigateTo(FleetScreen.VENDOR) }
                    )
                }

                // Google AppSheet Sync Tile
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.navigateTo(FleetScreen.APPSHEET_SYNC) }
                        .testTag("menu_tile_sync"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .background(FleetPrimary.copy(alpha = 0.12f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.CloudSync, contentDescription = null, tint = FleetPrimary)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Google AppSheet & Apps Script",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = FleetDarkText
                                )
                                Text(
                                    text = "Sinkronisasi database spreadsheet & webhook",
                                    fontSize = 12.sp,
                                    color = FleetMutedText
                                )
                            }
                        }
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = FleetMutedText)
                    }
                }
            }
        }
    }
}

@Composable
fun KpiStatCard(
    title: String,
    value: String,
    subtext: String,
    icon: ImageVector,
    color: Color,
    bgColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("kpi_card_${title.replace(" ", "_")}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(bgColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = FleetDarkText)
            Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = FleetDarkText)
            Text(text = subtext, fontSize = 10.sp, color = FleetMutedText, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun QuickFleetClickRow(
    title: String,
    subtitle: String,
    tag: String,
    status: String,
    icon: ImageVector,
    iconColor: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .border(1.dp, FleetBorder.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .testTag(testTag),
        color = FleetLightBlueBackground.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .background(iconColor.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = FleetDarkText)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "• $tag", fontSize = 11.sp, color = FleetMutedText, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    Text(text = subtitle, fontSize = 11.sp, color = FleetMutedText, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                StatusBadge(text = status)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Detail", tint = FleetMutedText, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun MenuTile(
    title: String,
    count: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("menu_tile_${title.replace(" ", "_")}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(color.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FleetDarkText)
            Text(text = count, fontSize = 11.sp, color = FleetMutedText)
        }
    }
}

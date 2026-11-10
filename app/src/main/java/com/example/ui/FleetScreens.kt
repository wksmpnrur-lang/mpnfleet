package com.example.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Search
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DamageReport
import com.example.data.model.Driver
import com.example.data.model.ExpirySeverity
import com.example.data.model.ExpiryUtils
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
import com.example.ui.theme.FleetBorder
import com.example.ui.theme.FleetDarkText
import com.example.ui.theme.FleetLightBlueBackground
import com.example.ui.theme.FleetMutedText
import com.example.ui.theme.FleetPrimary
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
fun SubScreenContainer(
    title: String,
    onBack: () -> Unit,
    onAddClick: (() -> Unit)? = null,
    addTag: String = "add_fab",
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        containerColor = FleetLightBlueBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = FleetDarkText
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("subscreen_back_button")) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = FleetDarkText)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            if (onAddClick != null) {
                FloatingActionButton(
                    onClick = onAddClick,
                    containerColor = FleetPrimary,
                    contentColor = Color.White,
                    modifier = Modifier.testTag(addTag)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah")
                }
            }
        }
    ) { padding ->
        content(padding)
    }
}

// 1. Screen Kendaraan Ringan
@Composable
fun LightVehiclesScreen(
    viewModel: FleetViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.lightVehicles.collectAsState()
    val userRole by viewModel.userRole.collectAsState()
    var search by remember { mutableStateOf("") }

    val filtered = items.filter {
        it.id.contains(search, ignoreCase = true) ||
                it.plateNumber.contains(search, ignoreCase = true) ||
                it.model.contains(search, ignoreCase = true) ||
                it.brand.contains(search, ignoreCase = true) ||
                it.driverName.contains(search, ignoreCase = true)
    }

    SubScreenContainer(
        title = "Data Kendaraan Ringan",
        onBack = onBack,
        onAddClick = if (userRole == UserRole.ADMIN) { { viewModel.openDialog("LIGHT") } } else null,
        addTag = "add_light_vehicle_fab"
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_light_vehicle"),
                placeholder = { Text("Cari ID, Plat Nomor, Model, Driver...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = FleetMutedText) },
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filtered, key = { it.id }) { item ->
                    LightVehicleCard(
                        item = item,
                        onClick = { viewModel.openDetail(SelectedFleetDetail.LightVehicle(item)) }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
fun LightVehicleCard(item: VehicleLight, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("card_vehicle_${item.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(FleetPrimary.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = FleetPrimary, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = item.id, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = FleetDarkText)
                        Text(text = item.plateNumber, fontSize = 12.sp, color = FleetPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }
                StatusBadge(text = item.status)
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "${item.brand} ${item.model} (${item.year})", fontWeight = FontWeight.Medium, fontSize = 14.sp, color = FleetDarkText)
            Text(text = "Tipe: ${item.vehicleType} • CC: ${item.engineCapacity}", fontSize = 12.sp, color = FleetMutedText)
            Text(text = "Area: ${item.workArea}", fontSize = 12.sp, color = FleetMutedText)
            Text(text = "Driver: ${item.driverName} (${item.badgeNumber})", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = FleetDarkText)

            Spacer(modifier = Modifier.height(10.dp))
            // Quick Mini Status Dots
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) {
                DocStatusMiniPill("Pajak", item.taxExpiry)
                DocStatusMiniPill("STNK", item.stnkExpiry)
                DocStatusMiniPill("KIR", item.kirExpiry)
                DocStatusMiniPill("TPE", item.tpeExpiry)
                DocStatusMiniPill("Red Pass", item.redPassExpiry)
            }
        }
    }
}

// 2. Screen Kendaraan Berat
@Composable
fun HeavyVehiclesScreen(
    viewModel: FleetViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.heavyVehicles.collectAsState()
    val userRole by viewModel.userRole.collectAsState()
    var search by remember { mutableStateOf("") }

    val filtered = items.filter {
        it.id.contains(search, ignoreCase = true) ||
                it.plateNumber.contains(search, ignoreCase = true) ||
                it.model.contains(search, ignoreCase = true) ||
                it.brand.contains(search, ignoreCase = true) ||
                it.operatorName.contains(search, ignoreCase = true)
    }

    SubScreenContainer(
        title = "Data Kendaraan Berat",
        onBack = onBack,
        onAddClick = if (userRole == UserRole.ADMIN) { { viewModel.openDialog("HEAVY") } } else null,
        addTag = "add_heavy_vehicle_fab"
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_heavy_vehicle"),
                placeholder = { Text("Cari ID, Plat, Merek, Operator...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = FleetMutedText) },
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filtered, key = { it.id }) { item ->
                    HeavyVehicleCard(
                        item = item,
                        onClick = { viewModel.openDetail(SelectedFleetDetail.HeavyVehicle(item)) }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
fun HeavyVehicleCard(item: VehicleHeavy, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("card_heavy_${item.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(FleetStatusWarning.copy(alpha = 0.15f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Engineering, contentDescription = null, tint = FleetStatusWarning, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = item.id, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = FleetDarkText)
                        Text(text = item.plateNumber, fontSize = 12.sp, color = FleetPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }
                StatusBadge(text = item.status)
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "${item.brand} ${item.model} (${item.year})", fontWeight = FontWeight.Medium, fontSize = 14.sp, color = FleetDarkText)
            Text(text = "Tipe: ${item.vehicleType} • CC: ${item.engineCapacity}", fontSize = 12.sp, color = FleetMutedText)
            Text(text = "Area: ${item.workArea}", fontSize = 12.sp, color = FleetMutedText)
            Text(text = "Operator: ${item.operatorName} (${item.badgeNumber})", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = FleetDarkText)

            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) {
                DocStatusMiniPill("SILO", item.siloExpiry)
                DocStatusMiniPill("TPE", item.tpeExpiry)
                DocStatusMiniPill("KIR", item.kirExpiry)
                DocStatusMiniPill("Pajak", item.taxExpiry)
                DocStatusMiniPill("Red Pass", item.redPassExpiry)
                DocStatusMiniPill("Green Pass", item.greenPassExpiry)
            }
        }
    }
}

// 3. Screen Welding Machine
@Composable
fun WeldingMachineScreen(
    viewModel: FleetViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.weldingMachines.collectAsState()
    val userRole by viewModel.userRole.collectAsState()

    SubScreenContainer(
        title = "Data Alat - Welding Machine",
        onBack = onBack,
        onAddClick = if (userRole == UserRole.ADMIN) { { viewModel.openDialog("WELDING") } } else null,
        addTag = "add_welding_fab"
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(items, key = { it.id }) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.openDetail(SelectedFleetDetail.Welding(item)) }
                        .testTag("card_welding_${item.id}"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(FleetPrimary.copy(alpha = 0.1f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Build, contentDescription = null, tint = FleetPrimary, modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(text = item.id, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = FleetDarkText)
                                    Text(text = "${item.brand} ${item.model}", fontSize = 13.sp, color = FleetPrimary)
                                }
                            }
                            StatusBadge(text = item.status)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Tipe: ${item.unitType} • ${item.engineCapacity}", fontSize = 12.sp, color = FleetMutedText)
                        Text(text = "Warna: ${item.color} • Tahun: ${item.year}", fontSize = 12.sp, color = FleetMutedText)
                        Text(text = "Area Kerja: ${item.workArea}", fontSize = 12.sp, color = FleetMutedText)
                        Text(text = "Pemco / Welder: ${item.pemcoWelder} (${item.badgeNumber})", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = FleetDarkText)
                        Spacer(modifier = Modifier.height(8.dp))
                        DocStatusMiniPill("Passed TPE", item.passedTpeExpiry)
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

// 4. Screen Alat Lain
@Composable
fun OtherEquipmentScreen(
    viewModel: FleetViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.otherEquipments.collectAsState()
    val userRole by viewModel.userRole.collectAsState()
    var selectedCat by remember { mutableStateOf("Semua") }

    val categories = listOf(
        "Semua", "Blower", "Asphal Cutter", "Concrete Mixer", "Jetting Pump",
        "Water Pump", "Hydrotest Pump", "Air Compressor", "Portable Genset",
        "Cable Locator", "Drop Hammer", "Jack Hummer"
    )

    val filtered = if (selectedCat == "Semua") items else items.filter { it.category == selectedCat }

    SubScreenContainer(
        title = "Data Alat Lain",
        onBack = onBack,
        onAddClick = if (userRole == UserRole.ADMIN) { { viewModel.openDialog("OTHER") } } else null,
        addTag = "add_other_fab"
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    FilterChip(
                        selected = selectedCat == cat,
                        onClick = { selectedCat = cat },
                        label = { Text(cat, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = FleetPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filtered, key = { it.id }) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { viewModel.openDetail(SelectedFleetDetail.Other(item)) }
                            .testTag("card_other_${item.unitNumber}"),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .background(FleetStatusInfo.copy(alpha = 0.15f), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.Handyman, contentDescription = null, tint = FleetStatusInfo, modifier = Modifier.size(18.dp))
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(text = item.unitNumber, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = FleetDarkText)
                                        Text(text = item.category, fontSize = 12.sp, color = FleetPrimary, fontWeight = FontWeight.Medium)
                                    }
                                }
                                StatusBadge(text = item.status)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "Model: ${item.model}", fontSize = 13.sp, color = FleetDarkText)
                            Text(text = "Lokasi: ${item.workArea}", fontSize = 12.sp, color = FleetMutedText)
                            Spacer(modifier = Modifier.height(6.dp))
                            DocStatusMiniPill("Passed TPE", item.passedTpeExpiry)
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

// 5. Screen Driver
@Composable
fun DriversScreen(
    viewModel: FleetViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.drivers.collectAsState()
    val userRole by viewModel.userRole.collectAsState()

    SubScreenContainer(
        title = "Data Driver",
        onBack = onBack,
        onAddClick = if (userRole == UserRole.ADMIN) { { viewModel.openDialog("DRIVER") } } else null,
        addTag = "add_driver_fab"
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(items, key = { it.badgeNumber }) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.openDetail(SelectedFleetDetail.DriverDetail(item)) }
                        .testTag("card_driver_${item.badgeNumber}"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(FleetPrimary.copy(alpha = 0.1f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = FleetPrimary, modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(text = item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = FleetDarkText)
                                    Text(text = "Badge: ${item.badgeNumber} • ${item.phone}", fontSize = 12.sp, color = FleetMutedText)
                                }
                            }
                            Text(text = item.simType, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = FleetPrimary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "No SIM: ${item.simNumber}", fontSize = 12.sp, color = FleetDarkText)
                        Text(text = "Kartu Pengemudi: ${item.driverCardNumber}", fontSize = 12.sp, color = FleetDarkText)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            DocStatusMiniPill("Exp SIM", item.simExpiry)
                            DocStatusMiniPill("Exp Kartu", item.driverCardExpiry)
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

// 6. Screen Operator
@Composable
fun OperatorsScreen(
    viewModel: FleetViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.operators.collectAsState()
    val userRole by viewModel.userRole.collectAsState()

    SubScreenContainer(
        title = "Data Operator Alat Berat",
        onBack = onBack,
        onAddClick = if (userRole == UserRole.ADMIN) { { viewModel.openDialog("OPERATOR") } } else null,
        addTag = "add_operator_fab"
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(items, key = { it.badgeNumber }) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.openDetail(SelectedFleetDetail.OperatorDetail(item)) }
                        .testTag("card_operator_${item.badgeNumber}"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(FleetStatusWarning.copy(alpha = 0.15f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Engineering, contentDescription = null, tint = FleetStatusWarning, modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(text = item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = FleetDarkText)
                                    Text(text = "Badge: ${item.badgeNumber} • ${item.phone}", fontSize = 12.sp, color = FleetMutedText)
                                }
                            }
                            Text(text = item.simType, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = FleetPrimary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "No SIM: ${item.simNumber}", fontSize = 12.sp, color = FleetDarkText)
                        Text(text = "No Kartu: ${item.driverCardNumber}", fontSize = 12.sp, color = FleetDarkText)
                        Text(text = "No SIO: ${item.sioNumber}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = FleetDarkText)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) {
                            DocStatusMiniPill("Exp SIO", item.sioExpiry)
                            DocStatusMiniPill("Exp SIM", item.simExpiry)
                            DocStatusMiniPill("Exp Kartu", item.driverCardExpiry)
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

// 7. Screen Inspeksi
@Composable
fun InspectionsScreen(
    viewModel: FleetViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.inspections.collectAsState()

    SubScreenContainer(
        title = "Data Inspeksi Kendaraan & Alat",
        onBack = onBack,
        onAddClick = { viewModel.openDialog("INSPECTION") },
        addTag = "add_inspection_fab"
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(items, key = { it.id }) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("card_inspection_${item.id}"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "ID Alat: ${item.toolId}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = FleetPrimary)
                            StatusBadge(text = item.inspectionResult)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Inspektor: ${item.inspectorName}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = FleetDarkText)
                        Text(text = "Tanggal: ${item.inspectionDate} • Lokasi: ${item.inspectionLocation}", fontSize = 12.sp, color = FleetMutedText)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Catatan Temuan: ${item.findingsNotes}", fontSize = 12.sp, color = FleetDarkText)
                        if (item.findingsPhotoUrl.isNotBlank()) {
                            Text(text = "Foto Temuan: Terlampir", fontSize = 11.sp, color = FleetPrimary, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

// 8. Screen Service
@Composable
fun ServicesScreen(
    viewModel: FleetViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.services.collectAsState()

    SubScreenContainer(
        title = "Data Service & Maintenance",
        onBack = onBack,
        onAddClick = { viewModel.openDialog("SERVICE") },
        addTag = "add_service_fab"
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(items, key = { it.id }) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("card_service_${item.id}"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Unit: ${item.toolId}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = FleetPrimary)
                            Text(text = item.serviceDate, fontSize = 12.sp, color = FleetMutedText)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Item Service: ${item.serviceItems}", fontSize = 13.sp, color = FleetDarkText)
                        Text(text = "Bengkel: ${item.workshop}", fontSize = 12.sp, color = FleetMutedText)
                        if (item.cost > 0) {
                            Text(text = "Biaya: Rp ${"%,d".format(item.cost)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = FleetPrimary)
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

// 9. Screen Kerusakan
@Composable
fun DamageReportsScreen(
    viewModel: FleetViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.damageReports.collectAsState()

    SubScreenContainer(
        title = "Data Temuan Kerusakan",
        onBack = onBack,
        onAddClick = { viewModel.openDialog("DAMAGE") },
        addTag = "add_damage_fab"
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(items, key = { it.id }) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("card_damage_${item.id}"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "No Alat: ${item.toolNumber}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = FleetStatusDanger)
                            StatusBadge(text = item.status)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Temuan: ${item.damageFindings}", fontSize = 13.sp, color = FleetDarkText, fontWeight = FontWeight.Medium)
                        Text(text = "Dilaporkan Oleh: ${item.reportedBy} • Lokasi: ${item.damageLocation}", fontSize = 12.sp, color = FleetMutedText)
                        Text(text = "Tanggal Laporan: ${item.reportDate}", fontSize = 12.sp, color = FleetMutedText)

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            if (item.status != "Selesai") {
                                OutlinedButton(
                                    onClick = {
                                        val next = if (item.status == "Open") "Sedang Diperbaiki" else "Selesai"
                                        viewModel.updateDamageStatus(item, next)
                                    },
                                    modifier = Modifier.testTag("btn_update_damage_${item.id}")
                                ) {
                                    Text(if (item.status == "Open") "Mulai Perbaikan" else "Tandai Selesai", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

// 10. Screen Vendor
@Composable
fun VendorsScreen(
    viewModel: FleetViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.vendors.collectAsState()
    val userRole by viewModel.userRole.collectAsState()

    SubScreenContainer(
        title = "Data Vendor Alat",
        onBack = onBack,
        onAddClick = if (userRole == UserRole.ADMIN) { { viewModel.openDialog("VENDOR") } } else null,
        addTag = "add_vendor_fab"
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(items, key = { it.id }) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("card_vendor_${item.id}"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = item.vendorName, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = FleetDarkText)
                        Text(text = "Spesialisasi: ${item.supplyType}", fontSize = 13.sp, color = FleetPrimary, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Kontak: ${item.contactPerson} (${item.phone})", fontSize = 12.sp, color = FleetDarkText)
                        Text(text = "Alamat: ${item.address}", fontSize = 12.sp, color = FleetMutedText)
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

// 11. Screen AppSheet Sync & Web Database Inspector
@Composable
fun AppSheetSyncScreen(
    viewModel: FleetViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current
    val config by viewModel.appSheetConfig.collectAsState()
    val isSyncing by viewModel.isSyncing.collectAsState()
    val syncMsg by viewModel.syncMessage.collectAsState()

    val lightVehicles by viewModel.lightVehicles.collectAsState()
    val heavyVehicles by viewModel.heavyVehicles.collectAsState()
    val weldingMachines by viewModel.weldingMachines.collectAsState()
    val otherEquipments by viewModel.otherEquipments.collectAsState()
    val drivers by viewModel.drivers.collectAsState()
    val operators by viewModel.operators.collectAsState()
    val inspections by viewModel.inspections.collectAsState()
    val services by viewModel.services.collectAsState()
    val damages by viewModel.damageReports.collectAsState()
    val vendors by viewModel.vendors.collectAsState()

    var url by remember(config) { mutableStateOf(config?.scriptUrl ?: "https://script.google.com/macros/s/AKfycbz_MPNFLEET_WEB/exec") }
    var appId by remember(config) { mutableStateOf(config?.appSheetId ?: "MPNFLEET-WEB-ACTIVE") }
    var selectedTable by remember { mutableStateOf("Kendaraan Ringan") }

    val tableNames = listOf(
        "Kendaraan Ringan", "Kendaraan Berat", "Welding Machine", "Alat Lain",
        "Driver", "Operator", "Inspeksi", "Service", "Kerusakan", "Vendor"
    )

    SubScreenContainer(
        title = "Database di Web & Sinkronisasi",
        onBack = onBack
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card: 1. Cara Melihat Database di Web (Panduan Langsung)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(FleetPrimary.copy(alpha = 0.12f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.OpenInBrowser, contentDescription = null, tint = FleetPrimary)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "Cara Melihat Database di Web", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = FleetDarkText)
                            Text(text = "Akses basis data melalui Google Sheets, AppSheet & Apps Script", fontSize = 12.sp, color = FleetMutedText)
                        }
                    }

                    Text(
                        text = "Karena sistem MPNFLEETWEB berbasis Google AppSheet dan Google Apps Script, database Anda tersimpan dalam Google Sheets di cloud dan dapat dibuka langsung melalui browser web:",
                        fontSize = 12.sp,
                        color = FleetDarkText,
                        lineHeight = 18.sp
                    )

                    // Web Access Buttons
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/"))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth().testTag("btn_open_google_sheets"),
                            colors = ButtonDefaults.buttonColors(containerColor = FleetStatusActive)
                        ) {
                            Icon(Icons.Default.TableChart, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("1. Buka Google Sheets di Web Browser")
                        }

                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.appsheet.com/"))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth().testTag("btn_open_appsheet"),
                            colors = ButtonDefaults.buttonColors(containerColor = FleetPrimary)
                        ) {
                            Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("2. Buka Dashboard Web AppSheet")
                        }

                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://script.google.com/"))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth().testTag("btn_open_apps_script")
                        ) {
                            Icon(Icons.Default.Code, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("3. Buka Google Apps Script Editor")
                        }
                    }
                }
            }

            // Card: Firebase Firestore Cloud Database Backend
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(FleetStatusWarning.copy(alpha = 0.15f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CloudSync, contentDescription = null, tint = FleetStatusWarning)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "Firebase Firestore Backend Database", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = FleetDarkText)
                            Text(text = "Database ID: ${viewModel.firestoreRepository.databaseId}", fontSize = 11.sp, color = FleetPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }

                    val firestoreStatus by viewModel.firestoreStatus.collectAsState()
                    Text(text = firestoreStatus ?: "Siap terhubung", fontSize = 12.sp, color = FleetMutedText)

                    Button(
                        onClick = { viewModel.syncToFirestoreBackend() },
                        enabled = !isSyncing,
                        modifier = Modifier.fillMaxWidth().testTag("btn_sync_firestore"),
                        colors = ButtonDefaults.buttonColors(containerColor = FleetPrimary)
                    ) {
                        if (isSyncing) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Menyinkronkan ke Firestore...")
                        } else {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Sinkronkan Data ke Firestore Backend")
                        }
                    }
                }
            }

            // Card: 2. Konfigurasi Sinkronisasi Real-Time
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(FleetPrimary.copy(alpha = 0.12f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CloudSync, contentDescription = null, tint = FleetPrimary)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "Koneksi Webhook AppSheet & Script", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = FleetDarkText)
                            Text(text = "Status: ${config?.syncStatus ?: "Tersedia"}", fontSize = 12.sp, color = FleetStatusActive)
                        }
                    }

                    Text(text = "Terakhir Sinkron: ${config?.lastSyncTime ?: "-"}", fontSize = 12.sp, color = FleetMutedText)

                    OutlinedTextField(
                        value = url,
                        onValueChange = { url = it },
                        label = { Text("Google Apps Script Web App URL") },
                        modifier = Modifier.fillMaxWidth().testTag("input_sync_script_url"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = appId,
                        onValueChange = { appId = it },
                        label = { Text("Google AppSheet Application ID") },
                        modifier = Modifier.fillMaxWidth().testTag("input_sync_app_id"),
                        singleLine = true
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                viewModel.updateSyncConfig(url, appId)
                                Toast.makeText(context, "URL Webhook disimpan!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f).testTag("btn_save_sync_config"),
                            colors = ButtonDefaults.buttonColors(containerColor = FleetPrimary)
                        ) {
                            Text("Simpan URL")
                        }

                        Button(
                            onClick = { viewModel.triggerSync() },
                            enabled = !isSyncing,
                            modifier = Modifier.weight(1f).testTag("btn_trigger_sync"),
                            colors = ButtonDefaults.buttonColors(containerColor = FleetStatusActive)
                        ) {
                            if (isSyncing) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Proses...")
                            } else {
                                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Sync Now")
                            }
                        }
                    }

                    if (syncMsg != null) {
                        Text(
                            text = syncMsg ?: "",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = FleetPrimary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            // Card: 3. Live Database Web Inspector & Spreadsheet Grid Viewer
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(text = "Web Database Inspector (Live Table Grid)", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = FleetDarkText)
                    Text(text = "Lihat tabel database secara langsung seperti pada tampilan Google Sheets:", fontSize = 12.sp, color = FleetMutedText)

                    // Table Selector Chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        tableNames.forEach { tName ->
                            FilterChip(
                                selected = selectedTable == tName,
                                onClick = { selectedTable = tName },
                                label = { Text(tName, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FleetPrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    // Content rows per selected table
                    val (headers, rows, csvContent) = when (selectedTable) {
                        "Kendaraan Ringan" -> {
                            val h = listOf("ID", "No Polisi", "Model", "Merek", "Tahun", "Driver", "Pajak", "STNK", "KIR", "TPE", "Red Pass")
                            val r = lightVehicles.map { listOf(it.id, it.plateNumber, it.model, it.brand, it.year.toString(), it.driverName, it.taxExpiry, it.stnkExpiry, it.kirExpiry, it.tpeExpiry, it.redPassExpiry) }
                            val csv = h.joinToString(",") + "\n" + r.joinToString("\n") { it.joinToString(",") }
                            Triple(h, r, csv)
                        }
                        "Kendaraan Berat" -> {
                            val h = listOf("ID", "No Polisi", "Model", "Merek", "Tahun", "Operator", "Pajak", "KIR", "SILO", "TPE")
                            val r = heavyVehicles.map { listOf(it.id, it.plateNumber, it.model, it.brand, it.year.toString(), it.operatorName, it.taxExpiry, it.kirExpiry, it.siloExpiry, it.tpeExpiry) }
                            val csv = h.joinToString(",") + "\n" + r.joinToString("\n") { it.joinToString(",") }
                            Triple(h, r, csv)
                        }
                        "Welding Machine" -> {
                            val h = listOf("Unit", "Model", "Merek", "Tahun", "Kapasitas", "Welder", "Passed TPE")
                            val r = weldingMachines.map { listOf(it.id, it.model, it.brand, it.year.toString(), it.engineCapacity, it.pemcoWelder, it.passedTpeExpiry) }
                            val csv = h.joinToString(",") + "\n" + r.joinToString("\n") { it.joinToString(",") }
                            Triple(h, r, csv)
                        }
                        "Alat Lain" -> {
                            val h = listOf("Unit", "Kategori", "Model", "Passed TPE", "Area")
                            val r = otherEquipments.map { listOf(it.unitNumber, it.category, it.model, it.passedTpeExpiry, it.workArea) }
                            val csv = h.joinToString(",") + "\n" + r.joinToString("\n") { it.joinToString(",") }
                            Triple(h, r, csv)
                        }
                        "Driver" -> {
                            val h = listOf("Badge", "Nama", "No SIM", "Exp SIM", "Tipe SIM", "Kartu Pengemudi")
                            val r = drivers.map { listOf(it.badgeNumber, it.name, it.simNumber, it.simExpiry, it.simType, it.driverCardNumber) }
                            val csv = h.joinToString(",") + "\n" + r.joinToString("\n") { it.joinToString(",") }
                            Triple(h, r, csv)
                        }
                        "Operator" -> {
                            val h = listOf("Badge", "Nama", "No SIM", "Exp SIM", "No SIO", "Exp SIO")
                            val r = operators.map { listOf(it.badgeNumber, it.name, it.simNumber, it.simExpiry, it.sioNumber, it.sioExpiry) }
                            val csv = h.joinToString(",") + "\n" + r.joinToString("\n") { it.joinToString(",") }
                            Triple(h, r, csv)
                        }
                        "Inspeksi" -> {
                            val h = listOf("ID Alat", "Inspektor", "Tanggal", "Lokasi", "Hasil")
                            val r = inspections.map { listOf(it.toolId, it.inspectorName, it.inspectionDate, it.inspectionLocation, it.inspectionResult) }
                            val csv = h.joinToString(",") + "\n" + r.joinToString("\n") { it.joinToString(",") }
                            Triple(h, r, csv)
                        }
                        "Service" -> {
                            val h = listOf("ID Alat", "Tanggal", "Item Service", "Bengkel", "Biaya")
                            val r = services.map { listOf(it.toolId, it.serviceDate, it.serviceItems, it.workshop, "Rp ${it.cost}") }
                            val csv = h.joinToString(",") + "\n" + r.joinToString("\n") { it.joinToString(",") }
                            Triple(h, r, csv)
                        }
                        "Kerusakan" -> {
                            val h = listOf("No Alat", "Temuan", "Pelapor", "Lokasi", "Status")
                            val r = damages.map { listOf(it.toolNumber, it.damageFindings, it.reportedBy, it.damageLocation, it.status) }
                            val csv = h.joinToString(",") + "\n" + r.joinToString("\n") { it.joinToString(",") }
                            Triple(h, r, csv)
                        }
                        else -> {
                            val h = listOf("Vendor", "Kontak", "Telepon", "Alamat", "Spesialisasi")
                            val r = vendors.map { listOf(it.vendorName, it.contactPerson, it.phone, it.address, it.supplyType) }
                            val csv = h.joinToString(",") + "\n" + r.joinToString("\n") { it.joinToString(",") }
                            Triple(h, r, csv)
                        }
                    }

                    // Spreadsheet Table View
                    Surface(
                        color = FleetLightBlueBackground,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .horizontalScroll(rememberScrollState())
                        ) {
                            // Header Row
                            Row(
                                modifier = Modifier
                                    .background(FleetPrimary.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                                    .padding(vertical = 8.dp, horizontal = 6.dp)
                            ) {
                                headers.forEach { header ->
                                    Text(
                                        text = header,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = FleetPrimary,
                                        modifier = Modifier.width(110.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            // Data Rows
                            rows.forEach { row ->
                                Row(
                                    modifier = Modifier
                                        .padding(vertical = 6.dp, horizontal = 6.dp)
                                ) {
                                    row.forEach { cell ->
                                        Text(
                                            text = cell,
                                            fontSize = 11.sp,
                                            color = FleetDarkText,
                                            modifier = Modifier.width(110.dp),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Copy CSV to paste into Google Sheet
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Total: ${rows.size} Baris Data", fontSize = 12.sp, color = FleetMutedText)

                        OutlinedButton(
                            onClick = {
                                clipboard.setText(AnnotatedString(csvContent))
                                Toast.makeText(context, "Data CSV '$selectedTable' disalin ke clipboard! Siap di-paste ke Google Sheets.", Toast.LENGTH_LONG).show()
                            },
                            modifier = Modifier.testTag("btn_copy_csv")
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Salin CSV ke Google Sheet", fontSize = 11.sp)
                        }
                    }
                }
            }

            // Card: 4. Script Template Google Apps Script (Code.gs)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Template Google Apps Script (Code.gs)", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = FleetDarkText)
                        OutlinedButton(
                            onClick = {
                                val scriptCode = """
function doGet(e) {
  var ss = SpreadsheetApp.getActiveSpreadsheet();
  var sheet = ss.getSheetByName(e.parameter.table || "Kendaraan_Ringan");
  var data = sheet.getDataRange().getValues();
  return ContentService.createTextOutput(JSON.stringify(data)).setMimeType(ContentService.MimeType.JSON);
}

function doPost(e) {
  var data = JSON.parse(e.postData.contents);
  var ss = SpreadsheetApp.getActiveSpreadsheet();
  var sheet = ss.getSheetByName(data.table || "Log");
  sheet.appendRow([new Date(), JSON.stringify(data)]);
  return ContentService.createTextOutput(JSON.stringify({status: "success"})).setMimeType(ContentService.MimeType.JSON);
}
""".trimIndent()
                                clipboard.setText(AnnotatedString(scriptCode))
                                Toast.makeText(context, "Script Code.gs disalin ke clipboard!", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Salin Kode", fontSize = 11.sp)
                        }
                    }

                    Text(
                        text = "Salin kode di atas dan tempelkan ke project Apps Script Anda di script.google.com untuk menghubungkan Google Sheet secara otomatis.",
                        fontSize = 12.sp,
                        color = FleetMutedText
                    )
                }
            }
        }
    }
}

@Composable
fun StatusBadge(text: String) {
    val (bgColor, textColor) = when (text) {
        "Aktif", "Lulus (Passed)", "Selesai" -> Pair(FleetStatusActiveBg, FleetStatusActive)
        "Operasi", "Sedang Diperbaiki" -> Pair(FleetStatusInfoBg, FleetStatusInfo)
        "Perlu Perpanjangan", "Catatan Ringan", "Lulus dengan Catatan", "TPE Expired" -> Pair(FleetStatusWarningBg, Color(0xFFB45309))
        "Kerusakan", "Hold", "Open", "Peringatan Dokumen" -> Pair(FleetStatusDangerBg, FleetStatusDanger)
        else -> Pair(FleetLightBlueBackground, FleetMutedText)
    }

    Box(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = textColor)
    }
}

@Composable
fun DocStatusMiniPill(label: String, dateStr: String?) {
    val status = ExpiryUtils.calculateStatus(dateStr, label)
    val (dotColor, textLabel) = when (status.severity) {
        ExpirySeverity.EXPIRED -> Pair(FleetStatusDanger, "$label: Expired")
        ExpirySeverity.EXPIRING_SOON -> Pair(FleetStatusWarning, "$label: ${status.daysRemaining}h")
        ExpirySeverity.VALID -> Pair(FleetStatusActive, "$label: OK")
        ExpirySeverity.NOT_AVAILABLE -> Pair(Color.Gray, "$label: -")
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(FleetLightBlueBackground, RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(dotColor, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = textLabel, fontSize = 10.sp, color = FleetDarkText, fontWeight = FontWeight.Medium)
    }
}

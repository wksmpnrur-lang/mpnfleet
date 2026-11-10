package com.example.data.local

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

object FleetInitialData {
    val lightVehicles = listOf(
        VehicleLight(
            id = "KR-0101",
            plateNumber = "B 9128 MPN",
            model = "Hilux 4x4 Double Cabin E",
            color = "Putih Solid",
            year = 2022,
            brand = "Toyota",
            engineCapacity = "2400 cc (2GD-FTV)",
            vehicleType = "Light Vehicle Double Cabin",
            chassisNumber = "MHF21FE40N0089214",
            workArea = "Area Barat - Workshop Utama",
            badgeNumber = "BDG-8812",
            driverName = "Ahmad Fauzi",
            taxExpiry = "2026-11-15",
            stnkExpiry = "2027-05-20",
            kirExpiry = "2026-11-02",
            tpeExpiry = "2026-11-28",
            redPassExpiry = "2026-10-30", // Expiring soon (<15 days from 2026-10-18)
            status = "Aktif"
        ),
        VehicleLight(
            id = "KR-0102",
            plateNumber = "B 9345 MPN",
            model = "Triton HDX 4x4 Single Cabin",
            color = "Silver Metalik",
            year = 2021,
            brand = "Mitsubishi",
            engineCapacity = "2500 cc DI-D",
            vehicleType = "Pick Up Single Cabin",
            chassisNumber = "MMBJRKL10NH007812",
            workArea = "Area Jetty & Stockpile",
            badgeNumber = "BDG-8815",
            driverName = "Hendra Gunawan",
            taxExpiry = "2026-10-10", // Expired!
            stnkExpiry = "2026-12-18",
            kirExpiry = "2026-10-05", // Expired!
            tpeExpiry = "2026-12-01",
            redPassExpiry = "2026-11-20",
            status = "Perlu Perpanjangan"
        ),
        VehicleLight(
            id = "KR-0103",
            plateNumber = "B 9556 MPN",
            model = "HiAce Commuter 16-Seat",
            color = "Putih Mutiara",
            year = 2023,
            brand = "Toyota",
            engineCapacity = "2800 cc D-4D",
            vehicleType = "Passenger Van / Crew Transport",
            chassisNumber = "JTDFX32P700142981",
            workArea = "Mess Camp - Site Head Office",
            badgeNumber = "BDG-8820",
            driverName = "Danang Prasetyo",
            taxExpiry = "2027-04-10",
            stnkExpiry = "2028-04-10",
            kirExpiry = "2027-03-15",
            tpeExpiry = "2027-03-30",
            redPassExpiry = "2027-02-15",
            status = "Aktif"
        ),
        VehicleLight(
            id = "KR-0104",
            plateNumber = "B 9710 MPN",
            model = "D-Max Rodeo 4x4",
            color = "Hitam Mica",
            year = 2020,
            brand = "Isuzu",
            engineCapacity = "1900 cc BluePower",
            vehicleType = "Light Patrol Vehicle",
            chassisNumber = "MP1TFR87JNT005519",
            workArea = "Patroli Security & Safety",
            badgeNumber = "BDG-8812",
            driverName = "Ahmad Fauzi",
            taxExpiry = "2026-12-25",
            stnkExpiry = "2027-06-30",
            kirExpiry = "2026-11-10",
            tpeExpiry = "2026-10-15", // Expired!
            redPassExpiry = "2026-12-05",
            status = "Service"
        )
    )

    val heavyVehicles = listOf(
        VehicleHeavy(
            id = "KB-0201",
            plateNumber = "KT 8876 MP",
            model = "Excavator PC200-8M0",
            color = "Kuning Standard",
            year = 2021,
            brand = "Komatsu",
            engineCapacity = "6690 cc (168 HP)",
            vehicleType = "Crawler Excavator",
            chassisNumber = "KMTPC2008C350291",
            workArea = "Pit 3 - South Hauling",
            badgeNumber = "BDG-7703",
            operatorName = "Budi Santoso",
            taxExpiry = "2027-01-15",
            stnkExpiry = "2027-01-15",
            kirExpiry = "2026-11-05", // Expiring soon
            tpeExpiry = "2026-11-15",
            redPassExpiry = "2026-10-25", // Expiring soon
            greenPassExpiry = "2026-10-25",
            siloExpiry = "2026-10-12", // Expired Silo!
            status = "Operasi"
        ),
        VehicleHeavy(
            id = "KB-0202",
            plateNumber = "KT 8912 MP",
            model = "Dump Truck FM 260 JD 6x4",
            color = "Hijau Stabilo",
            year = 2022,
            brand = "Hino",
            engineCapacity = "7684 cc (260 PS)",
            vehicleType = "Heavy Dump Truck",
            chassisNumber = "MJEFM8JNK0045618",
            workArea = "Overburden Hauling Route",
            badgeNumber = "BDG-7710",
            operatorName = "Joko Widodo",
            taxExpiry = "2027-03-20",
            stnkExpiry = "2027-03-20",
            kirExpiry = "2026-12-14",
            tpeExpiry = "2026-12-28",
            redPassExpiry = "2027-01-10",
            greenPassExpiry = "2027-01-10",
            siloExpiry = "2027-02-18",
            status = "Operasi"
        ),
        VehicleHeavy(
            id = "KB-0203",
            plateNumber = "KT 8105 MP",
            model = "Bulldozer D6R Series XL",
            color = "Kuning Caterpillar",
            year = 2020,
            brand = "Caterpillar",
            engineCapacity = "8800 cc (185 HP)",
            vehicleType = "Crawler Bulldozer",
            chassisNumber = "CAT00D6RXS003891",
            workArea = "Disposal Land Clearing",
            badgeNumber = "BDG-7718",
            operatorName = "Agus Pratama",
            taxExpiry = "2026-10-01", // Expired!
            stnkExpiry = "2027-08-10",
            kirExpiry = "2026-11-12",
            tpeExpiry = "2026-10-28", // Expiring soon
            redPassExpiry = "2026-11-04",
            greenPassExpiry = "2026-11-04",
            siloExpiry = "2026-12-01",
            status = "Perlu Sertifikasi"
        ),
        VehicleHeavy(
            id = "KB-0204",
            plateNumber = "KT 8440 MP",
            model = "Rough Terrain Crane GR-300EX (30T)",
            color = "Biru - Putih Tadano",
            year = 2019,
            brand = "Tadano",
            engineCapacity = "7200 cc (200 kW)",
            vehicleType = "Mobile Crane",
            chassisNumber = "TDNGR300EX009182",
            workArea = "Port Jetty Fabrication Yard",
            badgeNumber = "BDG-7710",
            operatorName = "Joko Widodo",
            taxExpiry = "2027-02-14",
            stnkExpiry = "2027-02-14",
            kirExpiry = "2027-01-08",
            tpeExpiry = "2027-02-20",
            redPassExpiry = "2026-11-18",
            greenPassExpiry = "2026-11-18",
            siloExpiry = "2026-11-25",
            status = "Aktif"
        )
    )

    val weldingMachines = listOf(
        WeldingMachine(
            id = "WM-01",
            model = "Big Blue 500X Clean Air Diesel",
            color = "Biru Miller",
            year = 2021,
            brand = "Miller Electric",
            engineCapacity = "500 Amp / Perkins 404D-22",
            unitType = "Engine Driven Welder Generator",
            chassisNumber = "MA500XPERK202188",
            workArea = "Fab Shop Area A",
            badgeNumber = "BDG-6601",
            pemcoWelder = "Rudi Hartono (6G SMAW/GTAW)",
            passedTpeExpiry = "2026-11-12",
            status = "Aktif"
        ),
        WeldingMachine(
            id = "WM-02",
            model = "Vantage 400 Multi-Process",
            color = "Merah Lincoln",
            year = 2020,
            brand = "Lincoln Electric",
            engineCapacity = "400 Amp / Kubota V2403",
            unitType = "Diesel Engine Driven Multi-Process",
            chassisNumber = "LNCVT400KUB202051",
            workArea = "Pipeline Section B",
            badgeNumber = "BDG-6604",
            pemcoWelder = "Supriyanto (3G/4G Welder)",
            passedTpeExpiry = "2026-10-14", // Expired TPE!
            status = "TPE Expired"
        ),
        WeldingMachine(
            id = "WM-03",
            model = "Trailblazer 325 Diesel",
            color = "Biru Miller",
            year = 2022,
            brand = "Miller Electric",
            engineCapacity = "325 Amp / Kubota Diesel",
            unitType = "Portable Engine Welder",
            chassisNumber = "MATB325KUB202293",
            workArea = "Maintenance Mobile Truck",
            badgeNumber = "BDG-6601",
            pemcoWelder = "Rudi Hartono",
            passedTpeExpiry = "2027-04-18",
            status = "Aktif"
        )
    )

    val otherEquipments = listOf(
        OtherEquipment(
            id = "EQ-BLW-01",
            unitNumber = "BLW-01",
            model = "Confined Space Axial Blower 12 Inch",
            category = "Blower",
            passedTpeExpiry = "2026-11-20",
            workArea = "Manhole Pipeline Section 2",
            status = "Aktif"
        ),
        OtherEquipment(
            id = "EQ-AC-01",
            unitNumber = "AC-01",
            model = "Floor Saw Asphalt Cutter Q450",
            category = "Asphal Cutter",
            passedTpeExpiry = "2026-10-28", // Expiring soon
            workArea = "Hauling Road Repair KM 14",
            status = "Aktif"
        ),
        OtherEquipment(
            id = "EQ-CM-01",
            unitNumber = "CM-01",
            model = "Molen Concrete Mixer 500L Yanmar TF85",
            category = "Concrete Mixer",
            passedTpeExpiry = "2026-12-10",
            workArea = "Culvert Foundation Pit 2",
            status = "Aktif"
        ),
        OtherEquipment(
            id = "EQ-JP-01",
            unitNumber = "JP-01",
            model = "Ultra High Pressure Jetting Pump 500 Bar",
            category = "Jetting Pump",
            passedTpeExpiry = "2026-10-08", // Expired!
            workArea = "Condenser Tube Cleaning",
            status = "TPE Expired"
        ),
        OtherEquipment(
            id = "EQ-WP-01",
            unitNumber = "WP-01",
            model = "Dewatering Water Pump 6 Inch Honda GX390",
            category = "Water Pump",
            passedTpeExpiry = "2027-03-15",
            workArea = "Sump Pit Dewatering",
            status = "Aktif"
        ),
        OtherEquipment(
            id = "EQ-HP-01",
            unitNumber = "HP-01",
            model = "Hydrotest Pressure Test Pump 1000 Bar",
            category = "Hydrotest Pump",
            passedTpeExpiry = "2026-11-05",
            workArea = "Pressure Vessel Testing Bay",
            status = "Aktif"
        ),
        OtherEquipment(
            id = "EQ-AIR-01",
            unitNumber = "AIR-01",
            model = "Atlas Copco XAS 186 Portable Compressor",
            category = "Air Compressor",
            passedTpeExpiry = "2026-12-04",
            workArea = "Sandblasting & Painting Yard",
            status = "Aktif"
        ),
        OtherEquipment(
            id = "EQ-GEN-01",
            unitNumber = "GEN-01",
            model = "Denyo DCA-25USI Portable Silent Genset",
            category = "Portable Genset",
            passedTpeExpiry = "2027-01-22",
            workArea = "Emergency Lighting Tower",
            status = "Aktif"
        ),
        OtherEquipment(
            id = "EQ-CL-01",
            unitNumber = "CL-01",
            model = "Radiodetection RD8100 Underground Locator",
            category = "Cable Locator",
            passedTpeExpiry = "2026-11-30",
            workArea = "Underground Cable Excavation Path",
            status = "Aktif"
        ),
        OtherEquipment(
            id = "EQ-DH-01",
            unitNumber = "DH-01",
            model = "Drop Hammer Piling 2.5 Ton",
            category = "Drop Hammer",
            passedTpeExpiry = "2026-10-02", // Expired!
            workArea = "Pier Jetty Foundation Yard",
            status = "TPE Expired"
        ),
        OtherEquipment(
            id = "EQ-JH-01",
            unitNumber = "JH-01",
            model = "Pneumatic Jack Hammer Toku TPB-60",
            category = "Jack Hummer",
            passedTpeExpiry = "2026-12-18",
            workArea = "Concrete Demolition Trench",
            status = "Aktif"
        )
    )

    val drivers = listOf(
        Driver(
            badgeNumber = "BDG-8812",
            name = "Ahmad Fauzi",
            simNumber = "940212450098",
            simExpiry = "2027-08-20",
            simType = "SIM B1 Umum",
            driverCardExpiry = "2026-11-10", // Expiring soon
            driverCardNumber = "KP-MPN-2024-088",
            phone = "0812-9844-3211"
        ),
        Driver(
            badgeNumber = "BDG-8815",
            name = "Hendra Gunawan",
            simNumber = "910518770023",
            simExpiry = "2026-10-09", // Expired SIM!
            simType = "SIM B2 Umum",
            driverCardExpiry = "2026-12-05",
            driverCardNumber = "KP-MPN-2023-145",
            phone = "0813-8899-7712"
        ),
        Driver(
            badgeNumber = "BDG-8820",
            name = "Danang Prasetyo",
            simNumber = "970928120067",
            simExpiry = "2028-02-14",
            simType = "SIM A",
            driverCardExpiry = "2027-05-18",
            driverCardNumber = "KP-MPN-2024-210",
            phone = "0852-1144-8890"
        )
    )

    val operators = listOf(
        Operator(
            badgeNumber = "BDG-7703",
            name = "Budi Santoso",
            simNumber = "880415330112",
            simExpiry = "2027-09-15",
            simType = "SIM B2 Umum",
            driverCardExpiry = "2026-11-20",
            driverCardNumber = "KP-OPR-2024-031",
            sioExpiry = "2026-10-29", // Expiring in 11 days
            sioNumber = "SIO-KEMNAKER-EXC-I/2021/782",
            phone = "0821-4567-9001"
        ),
        Operator(
            badgeNumber = "BDG-7710",
            name = "Joko Widodo",
            simNumber = "850619220045",
            simExpiry = "2026-11-30",
            simType = "SIM B2 Umum",
            driverCardExpiry = "2026-12-12",
            driverCardNumber = "KP-OPR-2023-019",
            sioExpiry = "2026-10-05", // Expired SIO!
            sioNumber = "SIO-KEMNAKER-CRN-II/2021/412",
            phone = "0812-7788-3344"
        ),
        Operator(
            badgeNumber = "BDG-7718",
            name = "Agus Pratama",
            simNumber = "920114880054",
            simExpiry = "2027-11-04",
            simType = "SIM B2 Umum",
            driverCardExpiry = "2027-03-01",
            driverCardNumber = "KP-OPR-2024-055",
            sioExpiry = "2027-06-25",
            sioNumber = "SIO-KEMNAKER-DZR-I/2022/901",
            phone = "0857-9912-3321"
        )
    )

    val inspections = listOf(
        VehicleInspection(
            inspectorName = "Ir. Bagus Wicaksono (HSE Fleet)",
            toolId = "KR-0101",
            inspectionDate = "2026-10-17",
            inspectionLocation = "Main Gate Workshop",
            findingsNotes = "Lampu rem belakang kanan redup, apar siap pakai, sabuk pengaman berfungsi normal.",
            findingsPhotoUrl = "https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=400",
            inspectionResult = "Lulus dengan Catatan"
        ),
        VehicleInspection(
            inspectorName = "Dedi Hermanto (Safety Officer)",
            toolId = "KB-0201",
            inspectionDate = "2026-10-16",
            inspectionLocation = "Pit 3 Loading Area",
            findingsNotes = "Sertifikat SILO teridentifikasi kadaluarsa per 12 Oktober 2026. Unit disarankan re-sertifikasi segera.",
            findingsPhotoUrl = "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=400",
            inspectionResult = "Peringatan Dokumen"
        ),
        VehicleInspection(
            inspectorName = "Ir. Bagus Wicaksono (HSE Fleet)",
            toolId = "WM-01",
            inspectionDate = "2026-10-15",
            inspectionLocation = "Fabrication Yard A",
            findingsNotes = "Insulasi kabel las prima, grounding rod terpasang, knalpot mesin tidak bocor.",
            findingsPhotoUrl = "",
            inspectionResult = "Lulus (Passed)"
        )
    )

    val services = listOf(
        VehicleService(
            toolId = "KR-0101",
            serviceDate = "2026-10-05",
            serviceItems = "Ganti oli mesin 15W-40, filter oli OEM, balancing roda depan, cek kampas rem",
            workshop = "Bengkel Resmi Toyota Kalla",
            cost = 1450000
        ),
        VehicleService(
            toolId = "KB-0202",
            serviceDate = "2026-09-28",
            serviceItems = "Service berkala 500 jam, ganti filter solar ganda, flushing sistem hidrolik",
            workshop = "Workshop Internal MPN Hauling",
            cost = 4200000
        ),
        VehicleService(
            toolId = "WM-01",
            serviceDate = "2026-09-15",
            serviceItems = "Ganti oli Perkins, pembersihan nozzle injektor bahan bakar, cek generator output 500A",
            workshop = "Workshop Fabrikasi MPN",
            cost = 950000
        )
    )

    val damageReports = listOf(
        DamageReport(
            toolNumber = "KR-0104",
            damageFindings = "Kopling selip saat tanjakan terjal bermuatan penuh, bunyi desis pada turbocharger",
            reportedBy = "Ahmad Fauzi",
            damageLocation = "KM 22 Hauling Access",
            reportDate = "2026-10-17",
            status = "Sedang Diperbaiki"
        ),
        DamageReport(
            toolNumber = "EQ-JP-01",
            damageFindings = "Seal selang tekanan tinggi 500 bar bocor halus dekat konektor inlet nozzle",
            reportedBy = "Supriyanto",
            damageLocation = "WTP Condenser Yard",
            reportDate = "2026-10-18",
            status = "Open"
        )
    )

    val vendors = listOf(
        Vendor(
            vendorName = "PT United Tractors Tbk",
            contactPerson = "Bambang Soetrisno",
            phone = "021-4605981",
            address = "Jl. Raya Bekasi KM 22, Cakung, Jakarta Timur",
            supplyType = "Dealer Resmi & Sparepart Komatsu Heavy Equipment"
        ),
        Vendor(
            vendorName = "PT Trakindo Utama",
            contactPerson = "Ferry Salim",
            phone = "021-7822373",
            address = "Gedung TMT 1, Cilandak Barat, Jakarta Selatan",
            supplyType = "Caterpillar Heavy Machinery & Undercarriage Parts"
        ),
        Vendor(
            vendorName = "PT Miller Welding Solutions Indonesia",
            contactPerson = "Anita Kusuma",
            phone = "021-89834112",
            address = "Kawasan Industri MM2100, Cikarang Barat",
            supplyType = "Mesin Las Industri & Suku Cadang Torch/Stang Las"
        ),
        Vendor(
            vendorName = "PT Sucofindo (Persero) - Jasa K3 & Uji Teknis",
            contactPerson = "Dr. Hendro Purnomo, ST",
            phone = "021-7983666",
            address = "Graha Sucofindo, Jl. Pasar Minggu Kav 34, Jakarta",
            supplyType = "Pemeriksaan dan Sertifikasi Berkala SILO, TPE, dan Kelayakan Alat Berat"
        )
    )

    val defaultAppSheetConfig = AppSheetConfig(
        id = 1,
        scriptUrl = "https://script.google.com/macros/s/AKfycbz_MPNFLEET_EXAMPLE/exec",
        appSheetId = "MPNFLEET-WEB-APPSHEET-v2",
        lastSyncTime = "2026-10-18 22:45",
        syncStatus = "Tersinkronisasi (OK)"
    )
}

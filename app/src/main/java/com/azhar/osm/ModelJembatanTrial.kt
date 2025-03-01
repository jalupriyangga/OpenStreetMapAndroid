package com.azhar.osm


data class ModelJembatanTrialItem(
    val alamat_jembatan: String = "",
    val ba_bahan: String? = null,
    val ba_kondisi: String = "",
    val ba_sifat: String? = null,
    val ba_tipe: String? = null,
    val created_at: String? = null,
    val created_by: Any? = null,
    val geom: List<Double> = listOf(),
    val id: Int = 0,
    val id_jalan: Int = 0,
    val is_permanen: Int = 0,
    val jumlah_bentang: String = "",
    val kepala_pilar_bahan: String? = null,
    val kepala_pilar_kondisi: String = "",
    val kepala_pilar_tipe: String? = null,
    val kode_jembatan: String = "",
    val kondisi: String = "",
    val koordinat_x: Double = 0.0,
    val koordinat_y: Double = 0.0,
    val lantai_bahan1: String? = null,
    val lantai_bahan2: String? = null,
    val lantai_kondisi: String = "",
    val lebar: Double = 0.0,
    val lebar_jalur: Any? = null,
    val nama_jembatan: String = "",
    val nama_ruas: String = "",
    val no: Int? = null,
    val nomor_ruas: Double? = null,
    val notes: String = "",
    val pal: String = "",
    val panjang: Double = 0.0,
    val pondasi_bahan: String? = null,
    val pondasi_kondisi: String = "",
    val pondasi_tipe: String? = null,
    val sandaran_kondisi: String = "",
    val sandaran_tipe1: String? = null,
    val sandaran_tipe2: String? = null,
    val thn_data: Int = 0,
    val tipe_penyebrangan: String = "",
    val updated_at: Any? = null,
    val updated_by: Any? = null,
    val workflow: String = ""
)

class ModelJembatanTrial : ArrayList<ModelJembatanTrialItem>() {
    lateinit var strName: String
    lateinit var strVicinity: String
    var latLoc = 0.0
    var longLoc = 0.0
}
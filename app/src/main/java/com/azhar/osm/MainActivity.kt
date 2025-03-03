package com.azhar.osm

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import androidx.lifecycle.ViewModelProvider
import org.osmdroid.views.overlay.Marker

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var searchView: AutoCompleteTextView
    private lateinit var btnSearch: Button
    private lateinit var tvBridgeName: TextView
    private lateinit var tvBridgeLocation: TextView
    private lateinit var infoContainer: LinearLayout

    private lateinit var jembatanViewModel: JembatanViewModel
    private val bridgeData = mutableMapOf<String, GeoPoint>() // Menyimpan lokasi jembatan berdasarkan nama

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi OSMDroid
        Configuration.getInstance().load(applicationContext, getSharedPreferences("osmdroid", MODE_PRIVATE))

        // Bind View
        mapView = findViewById(R.id.mapView)
        searchView = findViewById(R.id.searchView)
        btnSearch = findViewById(R.id.btnSearch)
        tvBridgeName = findViewById(R.id.tvBridgeName)
        tvBridgeLocation = findViewById(R.id.tvBridgeLocation)
        infoContainer = findViewById(R.id.infoContainer)

        // Konfigurasi MapView
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.controller.setZoom(15.0)

        // Inisialisasi ViewModel
        jembatanViewModel = ViewModelProvider(this)[JembatanViewModel::class.java]

        // Ambil data jembatan dari API dan tampilkan di AutoCompleteTextView
        jembatanViewModel.getJembatanData().observe(this) { jembatanList ->
            if (jembatanList != null) {
                val bridgeNames = jembatanList.map { it.nama_jembatan }
                val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, bridgeNames)
                searchView.setAdapter(adapter)

                // Simpan koordinat jembatan
                jembatanList.forEach { jembatan ->
                    bridgeData[jembatan.nama_jembatan] = GeoPoint(jembatan.koordinat_y, jembatan.koordinat_x)
                }
            }
        }

        // Handle Tombol Cari
        btnSearch.setOnClickListener {
            val selectedBridge = searchView.text.toString()

            if (bridgeData.containsKey(selectedBridge)) {
                val location = bridgeData[selectedBridge]!!

                // Tampilkan marker di peta
                mapView.overlays.clear()
                val marker = Marker(mapView)
                marker.position = location
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                marker.title = selectedBridge
                mapView.overlays.add(marker)

                mapView.controller.setCenter(location)

                // Tampilkan informasi di bawah
                tvBridgeName.text = selectedBridge
                tvBridgeLocation.text = "Lat: ${location.latitude}, Lon: ${location.longitude}"
                infoContainer.visibility = LinearLayout.VISIBLE
            } else {
                Toast.makeText(this, "Jembatan tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
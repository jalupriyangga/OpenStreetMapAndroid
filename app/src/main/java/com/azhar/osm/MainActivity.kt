package com.azhar.osm

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.azhar.osm.databinding.ActivityMainBinding
import androidx.activity.viewModels
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapController
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val jembatanViewModel: JembatanViewModel by viewModels()
    private lateinit var mapController: MapController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        val geoPoint = GeoPoint(-1.106699577, 114.153301002)
        binding.mapView.setMultiTouchControls(true)
        binding.mapView.controller.animateTo(geoPoint)
        binding.mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        binding.mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        mapController = binding.mapView.controller as MapController
        mapController.setCenter(geoPoint)
        mapController.zoomTo(12)

        // Observe the LiveData from the ViewModel
        jembatanViewModel.getJembatanData().observe(this) { jembatanList ->
            if (jembatanList != null) {
                initMarker(jembatanList)
                Toast.makeText(this, "ADA", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        }

        // Fetch data from the API
        jembatanViewModel.getJembatanData()
    }

    private fun initMarker(modelList: List<ModelJembatanTrialItem>) {
        for (item in modelList) {
            Log.d("MarkerInfo", "Adding marker: ${item.nama_jembatan} at (${item.koordinat_y}, ${item.koordinat_x})")

            val overlayItem = OverlayItem(item.nama_jembatan, item.alamat_jembatan, GeoPoint(item.koordinat_y, item.koordinat_x))
            val marker = Marker(binding.mapView)
            marker.icon = ContextCompat.getDrawable(this, R.drawable.ic_place)
            marker.position = GeoPoint(item.koordinat_y, item.koordinat_x)
            marker.relatedObject = item
            marker.infoWindow = CustomInfoWindow(binding.mapView)
            marker.setOnMarkerClickListener { item, _ ->
                item.showInfoWindow()
                true
            }

            binding.mapView.overlays.add(marker)
            binding.mapView.invalidate()
        }
    }

    override fun onResume() {
        super.onResume()
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        binding.mapView.onPause()
    }
}
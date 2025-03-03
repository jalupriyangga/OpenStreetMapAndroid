package com.azhar.osm

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow
import com.azhar.osm.databinding.LayoutTooltipBinding // Import your generated binding class

class CustomInfoWindow(mapView: MapView?) : InfoWindow(R.layout.layout_tooltip, mapView) {

    private var binding: LayoutTooltipBinding? = null

    override fun onOpen(item: Any) {
        val marker = item as Marker
        val infoWindowData = marker.relatedObject as ModelJembatanTrialItem // Change this line

        // Inflate the binding
        binding = LayoutTooltipBinding.bind(mView)

        // Set the data
        binding?.tvNamaLokasi?.text = infoWindowData.nama_jembatan // Use the correct property
        binding?.tvAlamat?.text = infoWindowData.kode_jembatan // Use the correct property

        binding?.imageClose?.setOnClickListener {
            marker.closeInfoWindow()
        }
    }

    override fun onClose() {
        // Clean up the binding to avoid memory leaks
        binding = null
    }
}
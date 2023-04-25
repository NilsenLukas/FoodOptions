package com.example.foodoptions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Get restaurant coordinates from the intent
        val restaurantName = intent.getStringExtra("restaurantName") ?: ""
        val restaurantLatitude = intent.getDoubleExtra("latitude", 0.0)
        val restaurantLongitude = intent.getDoubleExtra("longitude", 0.0)

        // Add a marker for the restaurant location and move the camera
        val restaurantLocation = LatLng(restaurantLatitude, restaurantLongitude)
        mMap.addMarker(MarkerOptions().position(restaurantLocation).title(restaurantName))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15f))
    }
}
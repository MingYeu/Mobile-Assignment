package com.example.warehouse

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class DetailsMapActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var map: GoogleMap
    private val TAG = DetailsMapActivity::class.java.simpleName
    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val stateName = intent.getStringExtra("state").toString()
        val spinner: Spinner = findViewById(R.id.location_spinner)
        val colors = arrayOf("Red", "Green", "Blue", "Yellow", "Black", "Crimson", "Orange")

        //Copies from the track fragments
        var items = ArrayList<String>()
        val database = FirebaseDatabase.getInstance()
        val trackRef = database.getReference("State").child(stateName)
        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (c in snapshot.children) {
                    var name = c.key.toString()
                    items.add(name)
                }
                items.remove("latitud")
                items.remove("longitud")
                if (spinner != null) {
                    val adapter = ArrayAdapter(
                        this@DetailsMapActivity,
                        android.R.layout.simple_spinner_item,
                        items
                    )
                    spinner.adapter = adapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@DetailsMapActivity,
                    "Fail to get data.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        trackRef.addValueEventListener(getData)
        var selectedCat = ""
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCat = items[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        //Spinner initialization done...
        //internal maps
        val internalMaps: Button = findViewById(R.id.internal_Warehouse_Map)
        internalMaps.setOnClickListener {
            val intent = Intent(this, InternalMapActivity::class.java)
            //intent.putExtra("stock_in_out", "2")
            startActivity(intent)
        }
        val retriveBtn: Button = findViewById(R.id.find_product)
        retriveBtn.setOnClickListener {
            val intent = Intent(this, RetriveProdActivity::class.java)
            intent.putExtra("productId", selectedCat)
            intent.putExtra("stateName", stateName)
            startActivity(intent)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val stateName = intent.getStringExtra("state").toString()
        map = googleMap
        val latitude = intent.getDoubleExtra("latitud", 0.0)
        val longitude = intent.getDoubleExtra("longitud", 0.0)

        val homeLatLng = LatLng(latitude, longitude)
        val zoomLevel = 15f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        map.addMarker(MarkerOptions().position(homeLatLng).title(stateName + " , Warehouse"))

        setMapLongClick(map)
        setPoiClick(map)
        setMapStyle(map)

        val overlaySize = 100f
        val androidOverlay = GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromResource(R.drawable.android))
            .position(homeLatLng, overlaySize)
        enableMyLocation()

    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            // A snippet is additional text that's displayed after the title.
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.dropped_pin))
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }
    }

    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker.showInfoWindow()
        }
    }

    private fun setMapStyle(map: GoogleMap) {
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.map_style
                )
            )
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
}
package com.example.localisationmaps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.emptyapplication.WSUtils
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.concurrent.thread

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

//        //Demande permission
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            //Pas la permission
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                0
//            )
//        }
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        refreshData()
//    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // appel fction getPlaces(user) pour recup liste
        thread {
            try {
                val myList = WSUtils.getPlacesTest()
                runOnUiThread {
                    for (user in myList) {
                        val userMarker = LatLng(user.lat, user.lon)
                        mMap.addMarker(MarkerOptions().position(userMarker).title(user.pseudo))
                    }
                }
            } catch (e: Exception) {
                println(e.message)
            }
        }
        // pour chaque point de la liste faire addMarker
    }

//    fun getLocation() {
//            if (mMap != null) {
//                runOnUiThread {
//
//                    //Si permission j'affiche
//                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                        mMap.isMyLocationEnabled = true
//                        //Récupération de la localisation
//                        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//                        val location = lm.getLastKnownLocation(lm.getBestProvider(Criteria(), false)!!)
//                        if (location != null) {
//                            try {
//
//                            } catch (e: Exception) {
//                                e.printStackTrace()
//                                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    }
//
//                }
//            }
//        }
}
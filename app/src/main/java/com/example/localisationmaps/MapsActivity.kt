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
    val data = ArrayList<UserBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Demande permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //Pas la permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        }
        loadData()
        thread {
            var location = getLocation()
            while (location == null) {
                Thread.sleep(5000)
                location = getLocation()
            }
            //TODO mise à jour localisation sur serveur
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        refreshMap()
    }


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
        println("c parti")
        mMap = googleMap
        refreshMap()
    }


    fun getLocation() {
        if (mMap != null) {

            //Si permission j'affiche
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mMap.isMyLocationEnabled = true
                //Récupération de la localisation
                val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val location = lm.getLastKnownLocation(lm.getBestProvider(Criteria(), false)!!)
                if (location != null) {
                    var myUser = UserBean(
                        1,
                        location.latitude,
                        location.longitude,
                        "Titi",
                        "blabla",
                        123456
                    )
                    thread {
                        try {
                            WSUtils.updatePlace(myUser)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }
    }


    fun refreshMap() {
        if (mMap == null) {
            return
        }
        runOnUiThread {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mMap.isMyLocationEnabled = true
            }
            mMap.clear()
            for (user in data) {
                val userMarker = LatLng(user.lat, user.lon)
                mMap.addMarker(MarkerOptions().position(userMarker).title(user.pseudo))
            }
        }
    }

    fun loadData() {
        thread {
            try {
                val myList = WSUtils.getPlaces()
                data.clear()
                data.addAll(myList)
                refreshMap()
            } catch (e: Exception) {
                e.printStackTrace()
//                TODO gérer affichage erreur sur IHM
            }
        }
    }
}
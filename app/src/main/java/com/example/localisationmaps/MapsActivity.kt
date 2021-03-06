package com.example.localisationmaps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.emptyapplication.WSUtils

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.concurrent.thread

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private val data = ArrayList<UserBean>()
    private var sessionId:Int = 0

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

        sessionId = intent.getIntExtra("sessionId",0)
        thread {
            while (true) {
                println("entree thread oncreate maps ******************************************")
                println("*************************** dans maps $sessionId")

                val location = getLocation()
                if (location != null) {
                    val myUser = UserBean(sessionId, location.lat, location.lon, null, null)
                    try {
                        WSUtils.updatePlace(myUser)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
                loadData()
                refreshMap()
                Thread.sleep(5000)
            }
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


    override fun onMapReady(googleMap: GoogleMap) {
        println("c parti*******************")
        mMap = googleMap
        println(mMap)
        refreshMap()
    }


    fun getLocation(): CoordBean? {
        println("****************************************************************")
        println(mMap)
        if (mMap != null) {

            //Si permission j'affiche
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                println("***********************g la permission")
                runOnUiThread {
                    mMap?.isMyLocationEnabled = true
                }
                //R??cup??ration de la localisation
                println("*************************je passe ?? recup de la localisation")
                val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val location = lm.getLastKnownLocation(lm.getBestProvider(Criteria(), false)!!)
                if (location != null) {
                    return CoordBean(location.latitude, location.longitude)
                }
            }
        }
        return null
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
                mMap?.isMyLocationEnabled = true
            }
            mMap?.clear()
            for (user in data) {
                val userMarker = LatLng(user.lat!!, user.lon!!)
                mMap?.addMarker(MarkerOptions().position(userMarker).title(user.pseudo))
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
//                TODO g??rer affichage erreur sur IHM
            }
        }
    }

    fun onBtnLogoutClick(view: View) {
        finish()
    }
}
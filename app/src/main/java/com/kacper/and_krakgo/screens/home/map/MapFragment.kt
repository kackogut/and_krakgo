package com.kacper.and_krakgo.screens.home.map

import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kacper.and_krakgo.R

/**
 * Created by kacper on 04/11/2017.
 */

class MapFragment : Fragment(), OnMapReadyCallback, LocationListener {

    private var mMap: GoogleMap? = null
    private val mLocationManager: LocationManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)
        val smf = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        smf.getMapAsync(this)

        val request = LocationRequest()
        request.interval = 10000
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val client = LocationServices.getFusedLocationProviderClient(context!!)
        val ref = FirebaseDatabase.getInstance().reference
        return rootView
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

    }

    override fun onLocationChanged(location: Location) {

    }

    companion object {

        private val MIN_TIME: Long = 400
        private val MIN_DISTANCE = 1000f
    }
}

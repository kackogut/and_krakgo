package com.kacper.and_krakgo.screens.home.map

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kacper.and_krakgo.Manifest
import com.kacper.and_krakgo.R
import com.kacper.and_krakgo.helpers.SnackbarHelper
import com.kacper.and_krakgo.model.Place
import com.kacper.and_krakgo.mvp.MvpFragment
import com.kacper.and_krakgo.screens.home.profile.ProfileContract
import kotlinx.android.synthetic.main.fragment_map.*

/**
 * Created by kacper on 04/11/2017.
 */

class MapFragment : MvpFragment<MapContract.View, MapContract.Presenter>(),
        MapContract.View, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    override var mPresenter: MapContract.Presenter = MapPresenter()
    private lateinit var mLastLocation: Location
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mGoogleMap: GoogleMap

    private val PERMISSION_READ_LOCATION = 101

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map_view.onCreate(savedInstanceState)
        connectToMap()
    }

    override fun onResume() {
        super.onResume()
        mGoogleApiClient.connect()
    }

    override fun onStop() {
        if (mGoogleApiClient.isConnected) {
            mGoogleApiClient.disconnect()
        }
        super.onStop()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onConnected(p0: Bundle?) {
        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity!!,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_READ_LOCATION);

        } else {
            getMarkers()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_READ_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getMarkers()
                } else {
                    SnackbarHelper.showError(R.string.error_location_not_granted, fragment_map_main_layout)
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMarkers() {
        mGoogleMap.clear()
        mGoogleMap.isMyLocationEnabled = true
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
        mPresenter.getPlaces()
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun setPlaces(places: ArrayList<Place>) {
        val builder = LatLngBounds.Builder()
        places.forEach({
            val marker = mGoogleMap.addMarker(MarkerOptions().position(LatLng(
                    it.latitude.toDouble(), it.longitude.toDouble())).title(it.display_name))
            marker.tag = it
            builder.include(marker.position)
        })
        val bounds = builder.build()
        val padding = 100
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        mGoogleMap.moveCamera(cu)
        showProgress(false)
    }

    private fun connectToMap() {
        map_view.onResume()

        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        map_view.getMapAsync({
            mGoogleMap = it
        })
        mGoogleApiClient = GoogleApiClient.Builder(context!!)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            pb_map_fragment?.visibility = View.VISIBLE
        } else {
            pb_map_fragment?.visibility = View.GONE
        }
        map_view.isEnabled = !show
    }
}

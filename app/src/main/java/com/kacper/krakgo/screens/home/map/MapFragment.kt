package com.kacper.krakgo.screens.home.map

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*

import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.SnackbarHelper
import com.kacper.krakgo.model.Place
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.mvp.MvpFragment
import com.kacper.krakgo.screens.dialogs.DialogUserInfo
import com.kacper.krakgo.screens.main.place_details.PlaceDetailsActivity
import kotlinx.android.synthetic.main.fragment_map.*

/**
 * Created by kacper on 04/11/2017.
 */

class MapFragment : MvpFragment<MapContract.View, MapContract.Presenter>(),
        MapContract.View, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private val CITY_LATITUDE = 50.0..50.12
    private val CITY_LONGITUDE = 19.8..20.1

    override var mPresenter: MapContract.Presenter = MapPresenter()
    private lateinit var mLastLocation: Location
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mGoogleMap: GoogleMap
    private var isUserInLocation = false
    private lateinit var locationCallback: LocationCallback
    private lateinit var mPlaces: ArrayList<Place>

    private val PERMISSION_READ_LOCATION = 101

    private val locationRequest = LocationRequest().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                if (mPresenter.getCurrentUser() == null) {
                    fusedLocationClient.removeLocationUpdates(this)
                    return
                }
                for (location in locationResult.locations) {
                    mLastLocation = location
                    mPresenter.setUserLocation(mLastLocation)
                }
            }
        }
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
        SnackbarHelper.showError(context, p0.errorMessage, fragment_map_main_layout)
    }

    override fun onConnected(p0: Bundle?) {
        if (ContextCompat.checkSelfPermission(context!!,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity!!,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_READ_LOCATION)

        } else {
            setLocationAndScroll()
            setMap()
            sendRequestLocation()
        }
    }

    override fun showError(error: String) {
        SnackbarHelper.showError(context, error, fragment_map_main_layout)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_READ_LOCATION -> {
                if ((grantResults.isNotEmpty()
                                && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    setLocationAndScroll()
                    setMap()
                    sendRequestLocation()
                } else {

                    SnackbarHelper.showError(context,
                            R.string.error_location_not_granted, fragment_map_main_layout)
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setMap() {
        mPresenter.getUsers()
        mPresenter.getPlaces()
        mGoogleMap.isMyLocationEnabled = true
        mGoogleMap.mapType
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.d(this.tag, "onConnectionSuspended")
    }

    @SuppressLint("MissingPermission")
    private fun setLocationAndScroll() {
        fusedLocationClient.lastLocation
                .addOnSuccessListener {
                    mLastLocation = it!!
                    if (mLastLocation.latitude in CITY_LATITUDE
                            && mLastLocation.longitude in CITY_LONGITUDE) {
                        isUserInLocation = true
                        val cu = CameraUpdateFactory.newLatLngZoom(LatLng(
                                mLastLocation.latitude, mLastLocation.longitude), 16f)
                        mGoogleMap.moveCamera(cu)
                    } else
                        isUserInLocation = false
                }

    }

    override fun setUsers(users: ArrayList<UserDetails>) {
        mGoogleMap.clear()
        if (::mPlaces.isInitialized && !mPlaces.isEmpty())
            setPlaces(mPlaces)
        users.forEach({
            if (it.map_visibility != 0L) {
                val marker = mGoogleMap.addMarker(MarkerOptions().position(LatLng(
                        it.latitude!!, it.longitude!!
                )))
                if (it.map_visibility == 1L)
                    marker.setIcon(
                            BitmapDescriptorFactory.fromResource(R.drawable.ic_user_map_visible))
                else
                    marker.setIcon(
                            BitmapDescriptorFactory.fromResource(R.drawable.ic_user_map_inviting))

                marker.tag = it
            }
        })
    }

    override fun setPlaces(places: ArrayList<Place>) {
        mPlaces = places
        val builder = LatLngBounds.Builder()
        places.forEach({
            val marker = mGoogleMap.addMarker(MarkerOptions().position(LatLng(
                    it.latitude, it.longitude))
                    .title(it.display_name))
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_beer_with_padding))
            marker.tag = it
            builder.include(marker.position)
        })
        if (!isUserInLocation) {
            val bounds = builder.build()
            val padding = 100
            val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            mGoogleMap.moveCamera(cu)
            isUserInLocation = true
        }


        mGoogleMap.setOnMarkerClickListener {
            if (it.tag != null) {
                if (it.tag is Place)
                    startActivity(PlaceDetailsActivity.newIntent(context!!, it.tag as Place))
                else if (it.tag is UserDetails)
                    DialogUserInfo(activity!!, it.tag as UserDetails).show()
                true
            } else
                false
        }
        showProgress(false)

    }

    @SuppressLint("MissingPermission")
    private fun sendRequestLocation() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                null)
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
            try {
                val success = it.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style))
                if (!success)
                    Log.e(this.tag, "Style parsing failed")
            } catch (e: Resources.NotFoundException) {
                Log.e(this.tag, "Can't find style")
            }

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
        map_view?.isEnabled = !show
    }
}

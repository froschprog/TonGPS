package com.froschprog.tongps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.Location.FORMAT_SECONDS
import android.location.Location.convert
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.froschprog.my_library.transitions.Slide
import com.froschprog.my_library.transitions.Transitions
import com.froschprog.tongps.dataclasses.MyLocation
import com.froschprog.tongps.dataclasses.TrackRaw
import kotlinx.android.synthetic.main.addgpsworkout.*



class AddGpsActivity: BaseActivity() {


    private lateinit var locationListener: LocationListener
    private lateinit var _lm: LocationManager
    private var latitude: Double = 0.0
    private var latitudestr: String =""
    private var longitudestr: String =""
    private var longitude: Double = 0.0
    private var altitude: Double = 0.0
    lateinit private var newtrack: TrackRaw




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.addgpsworkout)
        //Log.d("François", "Passage dans AddGpsActivity onCreate()")

// Location business
        // fill the text fields
        textViewAddGpsText.text= "Press start to begin"
        textViewAddGpsTitle.text= "GPS Tracking"
        // get access to the location manager
        _lm = getSystemService(LOCATION_SERVICE) as LocationManager
        setLocationListener()

//end of location business in onCreate


        // If click on Button "Start"
        ButtonStartGPS.setOnClickListener {
            addLocationListener()
            textViewAddGpsText.text="Waiting for GPS"
        }

        // If click on Button "Stop"
        ButtonStopGPS.setOnClickListener {
            textViewAddGpsText.text= "C'est fini !"
            removeLocationListener()
        }

        // If click on Button "Pause"
        ButtonPauseGPS.setOnClickListener {
            textViewAddGpsText.text= "Petite pause !"

        }

        // If click on Button "Save"
        ButtonSave.setOnClickListener {
            textViewAddGpsText.text= "On sauvergarde"

        }



        //If click on "back"
        ButtonBackGps.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            Transitions(this@AddGpsActivity).setAnimation(Slide().InLeft())
        }






    }


 /*   override fun onStart() {
        super.onStart()
        //.d("François", "Passage dans EditEventActivity onStart()")
        }
*/
    // onPause is always called BEFORE onStart or onResume of the next activity
    override fun onPause() {
        super.onPause()
        //Log.d("François", "Passage dans EditEvent onPause()")
        // save in TinyDB
        writeTinyDB(warray)
    }

    override fun onBackPressed() {
        ButtonBackGps.callOnClick()
    }

    // activates the location listener (on click button "start")
    private fun addLocationListener() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 1)
            // return after the call to request permissions as we don't know if the user has allowed it
            return
        }
        // private function that will add a location listener that will update every 1 second
        _lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, locationListener)
    }

    // deactivate the location listener (on click button "stop")
    private fun removeLocationListener(){
        _lm.removeUpdates(locationListener)
    }

    // defines the properties of the location listener
    private fun setLocationListener() {
        //addRotationListener()
        locationListener = object : LocationListener {
            override fun onLocationChanged(p0: Location) {
                // define local variables
                val prec: Float
                val p0time: Long
                // update the textviews with the current location
                latitudestr = Location.convert(p0.latitude, FORMAT_SECONDS)
                longitudestr = Location.convert(p0.longitude, FORMAT_SECONDS)
                altitude = p0.altitude
                textViewLongitude.text="Latitude : "+latitudestr
                textViewLatitude.text="Longitude : "+longitudestr
                textViewAltitude.text="Altitude : "+altitude.toString()
                //Precision
                prec = p0.accuracy
                textViewPrecision.text="Precision : "+prec.toString()
                //Time
                p0time = p0.time
                textViewTime.text="Time : "+p0time.toString()
                //Set the textview to indicate tracking
                textViewAddGpsText.text= "C'est parti !"
                //Save the location in the Track
                val Myloc = MyLocation(p0time,0,p0.latitude,p0.longitude,p0.altitude,prec,0.0F,"")


            }
        }
    }

    private fun retrieveDistanceInMetres(text: ArrayList<String>): Float{
        var latitudePoint: Double = text[0].toDouble()
        var longitudePoint: Double = text[1].toDouble()
        var destinationLocation = Location(LocationManager.GPS_PROVIDER)
        destinationLocation.latitude = latitudePoint
        destinationLocation.longitude = longitudePoint
        var currentLocation = Location(LocationManager.GPS_PROVIDER)
        currentLocation.latitude=latitude
        currentLocation.longitude=longitude
        return currentLocation.distanceTo(destinationLocation)
    }

 /*   private fun getLongitude(): Double{
        return longitude
    }

    private  fun getLatitude(): Double{
        return latitude
    }
*/
}


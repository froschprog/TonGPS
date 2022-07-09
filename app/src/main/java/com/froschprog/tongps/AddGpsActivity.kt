package com.froschprog.tongps

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.froschprog.my_library.transitions.Slide
import com.froschprog.my_library.transitions.Transitions
import kotlinx.android.synthetic.main.addgpsworkout.*
import kotlinx.android.synthetic.main.more_main_activity.*


class AddGpsActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.addgpsworkout)
        //Log.d("François", "Passage dans AddGpsActivity onCreate()")

        // If click on Button "Start"
        ButtonStartGPS.setOnClickListener {
            textViewAddGpsText.text= "C'est parti !"

        }

        // If click on Button "Stop"
        ButtonStopGPS.setOnClickListener {
            textViewAddGpsText.text= "C'est fini !"

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

    // Affichage dans onStart pour qu'il réaffichage en revenant
    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        //.d("François", "Passage dans EditEventActivity onStart()")
        // fill the text fields
        textViewAddGpsText.text= "C'est ici que la fonction du GPS devra être rentrée"
        textViewAddGpsTitle.text= "GPS Tracking"


        }

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
}


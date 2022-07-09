package com.froschprog.tongps

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.froschprog.my_library.Next_Date
import com.froschprog.my_library.myDate
import com.froschprog.my_library.today
import com.froschprog.my_library.transitions.*
import com.froschprog.my_library.utils.*
import com.froschprog.tongps.dataclasses.Stat
import com.froschprog.tongps.dataclasses.Workout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.util.*


class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //d("François", "Passage dans MainActivity onCreate()")
        //d("François", "username :"+ username +" "+ userheight +" "+ userweight)

        // If click on Button "..."
        ButtonMoreMain.setOnClickListener {
            val intent = Intent(this, MoreMainActivity::class.java)
            startActivity(intent)
            val transitions = Transitions(this@MainActivity)
            transitions.setAnimation(Slide().InRight())
        }

        // If click on Button "+"
        btnAddWorkout.setOnClickListener {

        }
        // If click on Button "gps"
        btnAddWorkoutgps.setOnClickListener {
            startActivity(Intent(this, AddGpsActivity::class.java))
            Transitions(this@MainActivity).setAnimation(Slide().InRight())
        }
        // If click on Button "List"
        ButtonList.setOnClickListener {

        }

        // If click on Button "User"
        ButtonUser.setOnClickListener {

       }

        //Tests

        //Fin des tests

    }

    override fun onStart() {
        super.onStart()
        //d("François", "Passage dans MainActivity onStart()")
        // set the title
        editTextTitle.text=getString(R.string.title, username)
       }

    // Android back button -> Home screen
    override fun onBackPressed() {
        finishAffinity()
    }


}
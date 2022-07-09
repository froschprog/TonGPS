package com.froschprog.tongps

import com.froschprog.my_library.utils.MyApplication
import com.froschprog.my_library.utils.TinyDB
import com.froschprog.tongps.dataclasses.Workout
import java.io.*
import java.text.NumberFormat



// defines notification time (here 9:00 in ms)
//const val notiftime= 9L*3600*1000

// Données user
var username = "Neo"
var userweight = 70 // kg
var userheight = 170 // cm

// number format
var nf: NumberFormat = NumberFormat.getInstance()

// defines an array of Workouts
var warray : ArrayList<Workout> = arrayListOf()

//conversion kj to kcal
fun kcal(kj: Int): Int{
    val a=kj.toDouble()/4.18
    return a.toInt()
}

// Defines list of workout icons

// value of the Icon selected in AddWorkout (not selected)
var selectedicon: Int = -1
var intensity: Int = -1

// function to read the array of workouts in TinyDB
fun readTinyDB(): ArrayList<Workout> {
    val entree = ArrayList<Workout>()
    // defines the databsase to save workouts
    val eventDB =
        TinyDB(MyApplication.getInstance()) //see: Android Development Tips - Ep #2 - Getting ApplicationContext (Youtube)
    //this gets the list of workouts from the TinyDB as a List of objects rather than "Event"
    val entreeobjects = eventDB.getListObject("eventarrayobj2", Workout::class.java)
    entree.clear()
    for (a in entreeobjects) {entree.add(a as Workout)} //converts Array of Objects in Array of Events
    return entree
}

// function to write the array of workouts in TinyDB
fun writeTinyDB(entree: ArrayList<Workout>) {
    // defines the databsase to save events
    //Log.d("François", "Passage dans writeTinyBD")
    val eventDB = TinyDB(MyApplication.getInstance())
    val entreeobjects = ArrayList<Any>() //this is the same variable but defined as List of objects rather than "Event"
    //converts Array of Events in Array of Objects
    for (a in entree) entreeobjects.add(a as Any)
    // save the ArrayList in TinyDB
    eventDB.putListObject("eventarrayobj2", entreeobjects)
}

// function to save the workouts in the file Workouts.txt
// location: /storage/emulated/0/Android/data/com.froschprog.tongps/files/TonTraining/Workouts.txt
fun Writetofile(entree: ArrayList<Workout>){
    val myExternalFile = File(MyApplication.getInstance()
            .getExternalFilesDir("/TonTraining/"),"Workouts.txt")
    //Log.d("François", "myExtfile : " + myExternalFile)
    try {
        myExternalFile.createNewFile()
        val fileOutputStream = FileOutputStream(myExternalFile)
        val sb = StringBuilder()
        sb.append(username + ", " + userheight + " cm, " + userweight + " kg\n")
        sb.append("Name, Description, Date, Energie (kJ), Power (W), Duration (s)," +
                " Distance (km), Intensity, Userweight (kg), icon_pos, unused1, unused2 \n")
        for (w in entree) sb.append(w.name + ", " + w.desc + ", " + w.date + ", " + w.kj + ", "
                + w.puissance + ", " + w.duree + ", " + w.dist + ", " + w.intensite
                + ", " + w.poids + ", " + w.icon_pos + ", " + w.unused1 + ", " + w.unused2).append("\n")
        fileOutputStream.write(sb.toString().toByteArray())
        fileOutputStream.close()
    } catch (e: IOException) {
        //Log.d("François", "Problem with file writing")
    }
}

// function to read the workouts in the file Workouts.txt
// location: /storage/emulated/0/Android/data/com.froschprog.tongps/files/TonTraining/Workouts.txt
@Suppress("UNUSED_VARIABLE")
fun Readfromfile():ArrayList<Workout>{
    val sb = StringBuilder()
    val wa = ArrayList<Workout>()
    try {
        val myExternalFile = File(MyApplication.getInstance()
                .getExternalFilesDir("/TonTraining/"),"Workouts.txt")
        //Log.d("François", "myExtfile read: " + myExternalFile)
        val fileInputStream = FileInputStream(myExternalFile)
        val iSR = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(iSR)
        var text:String? = null
        val l1 = bufferedReader.readLine() //first line: Userdata
        val l2 = bufferedReader.readLine() // second line: title of columns
        while ({ text = bufferedReader.readLine(); text }() != null) {
            val we:List<String> = text!!.split(",").map { it.trim() }
            wa.add(Workout(we[0], we[1], we[2], we[3].toInt(), we[4].toInt(), we[5].toInt(),
                    we[6].toDouble(), we[7].toInt(), we[8].toInt(), we[9].toInt(), we[10].toInt(), we[11]))
        }
        fileInputStream.close()
    } catch (e: IOException) {
        //Log.d("François", "No file found")
        sb.append("No file found")
    }
    return wa
}

/*fun isReadStoragePermissionGranted(actcontext : Activity): Boolean {
    return if (Build.VERSION.SDK_INT >= 23) {
        if (checkSelfPermission(MyApplication.getInstance(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.v("Fran", "Read Permission is granted1")
            true
        } else {
            Log.v("Fran", "Read Permission is revoked1")
            ActivityCompat.requestPermissions(actcontext, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 3)
            false
        }
    } else { //permission is automatically granted on sdk<23 upon installation
        Log.v("Fran", "Read Permission is granted2")
        true
    }
}*/

/*fun isWriteStoragePermissionGranted(actcontext : Activity): Boolean {
    return if (Build.VERSION.SDK_INT >= 23) {
        if (checkSelfPermission(MyApplication.getInstance(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.v("Fran", "Write Permission is granted1")
            true
        } else {
            Log.v("Fran", "Write Permission is revoked1")
            ActivityCompat.requestPermissions(actcontext, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 3)
            false
        }
    } else { //permission is automatically granted on sdk<23 upon installation
        Log.v("Fran", " Write Permission is granted2")
        true
    }
}*/


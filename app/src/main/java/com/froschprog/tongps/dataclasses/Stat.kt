package com.froschprog.tongps.dataclasses

import com.froschprog.my_library.myDate
import java.util.*
import kotlin.math.round

data class Stat(
        var warray: ArrayList<Workout>, // workout data on which stat is done
        var length: Int, // period in days of the stat
        var startdate: String, // start date
        var iconnr: Int,
        var type: Int, // type de stat
        var clicked: Boolean = false // if clicked on
) {
    // Les fonctions de stat
    //private var starttime = Date(formdate(startdate)).time-10000L
    private var starttime = myDate(startdate).ms-10000L
    private var lengthmillis = length.toLong()*24*3600*1000
    private var endtime = starttime+lengthmillis
    private val sa = subarray()
    //subarray
    private fun subarray(): ArrayList<Workout> {
        val sa: ArrayList<Workout> = arrayListOf()
        for (w in warray) {
            //val workouttime = Date(formdate(w.date)).time
            val workouttime = myDate(w.date!!).ms
            if (workouttime in (starttime + 1) until endtime) sa.add(w)
        }
        return sa
    }
    // total des kj sur tout les workouts dans la période
    fun totkj(): Int{
        var totkj = 0
        for (w in sa) totkj+=w.kj
        return totkj
    }
    // total kj sur un type de workout
    fun totkjtype(type: Int): Int{
        var totkj = 0
        for (w in sa) if (w.icon_pos==type) totkj+=w.kj
        return totkj
    }
    // nb de workouts pour un type
    fun nbtype(type: Int): Int{
        var totnb = 0
        for (w in sa) if (w.icon_pos==type) totnb++
        return totnb
    }
    // total km sur un type de workout
    fun totkmtype(type: Int): Double{
        var totkm = 0.0
        for (w in sa) if (w.icon_pos==type) totkm+=w.dist
        return if (type!=4) round(totkm) else round(totkm*10)/10.0
    //    return round(totkm*10)/10.0
    }
    // total des minutes sur tout les workouts dans la période
    fun totmin(): Int{
        var totmin = 0
        for (w in sa) totmin+=w.duree/60
        return totmin
    }
    // total minutes pour un type
    fun mintype(type: Int): Int{
        var totmin = 0
        for (w in sa) if (w.icon_pos==type) totmin+=w.duree/60
        return totmin
    }
    // puissance moyenne pour un type
    fun puissancemoy(type: Int): Int {
        var pow = 0
        var t=0
        for (w in sa) if (w.icon_pos==type) {pow+=w.puissance*w.duree;t+=w.duree}
        if (t!=0) pow /= t
        return pow
    }
    // vitesse moyenne pour un type
    fun vitessemoy(type: Int): Double {
        var vit = 0.0
        var t=0
        for (w in sa) if (w.icon_pos==type) {vit+=w.speed()*w.duree;t+=w.duree}
        if (t!=0) vit /= t.toDouble()
        return vit
    }
    // pace moyen pour un type
    fun pacemoy(type: Int): Double {
        var p = 0.0
        var t=0.0
        for (w in sa) if (w.icon_pos==type) {
            val (pm,ps) = w.pace()
            p+=(pm*60+ps).toDouble()*w.dist;t+=w.dist}
        if (t!=0.0) p /= t*60.0
        //passage to min.sec instead of minutes in decimal
        val pint = p.toInt().toDouble()
        p = pint + (p-pint)/100.0*60.0
        return p
    }
    // poids moyen sur la période de stat
    fun poids(): Int {
        var p = 0
        for (w in sa) p+=w.poids
        if (sa.size!=0) p /= sa.size
        return p
    }
}
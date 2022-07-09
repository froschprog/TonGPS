package com.froschprog.tongps.dataclasses

import com.froschprog.tongps.userheight

// defines a data class for Events
data class Workout(
    var name: String?,
    var desc: String?,
    var date: String?,
    var kj: Int, // kjoules dépensés
    var puissance: Int, // puissance utilisée
    var duree: Int, // durée du workout en secondes
    var dist: Double, // distance en km
    var intensite: Int, // intensité du workout entre 0 et 4
    var poids: Int, // poids en kilo de l'utilisateur au jour du workout
    var icon_pos: Int, // indice de l'icon dans le tableau d'icons. Définit le type de workout
    var unused1: Int,
    var unused2: String
) {
    // conversions ? kj -> kcal, minutes -> secondes...
    private fun rendement(): Double {
        var bmi= poids.toDouble()/ userheight.toDouble()/ userheight.toDouble()*10000.0
        if (bmi>30) bmi=30.0
        if (bmi<20) bmi=20.0
        return 3.7 + 0.6*(bmi-20.0)/10.0
    }
    private fun kj_to_power(){
        if (kj!=0) puissance=((kj.toDouble()/duree.toDouble())/rendement()*1000.0).toInt()
    }
    /*fun power_to_kj(){
        if (kj==0&&puissance!=0) kj=((puissance*duree).toDouble()*rendement()).toInt()
    }*/
    fun kcal(): Int{
        return (kj.toDouble()/4.18).toInt()
    }

    fun speed(): Double{
        return dist/duree.toDouble()*3600.0
    }

    fun pace(): Pair<Int,Int>{
        // returns minutes and seconds of the pace
        val p = duree.toDouble()/60.0/dist
        return Pair(p.toInt(),((p-p.toInt().toDouble())*60.0).toInt())
    }

    private fun calc_kj(){
        when (icon_pos){
            0 -> {val deniv: Double = when (intensite) {
                4 -> 0.9
                3 -> 0.6
                2 -> 0.3
                1 -> 0.1
                else -> 0.0}
                val vit = dist*3600.0/duree.toDouble()
                val coef = when (vit){
                    in (0.0..4.999) -> 0.70 //marche lente
                    in (5.0..5.999) -> 0.75 //marche moyenne
                    in (6.0..7.0) -> 0.85 //marche rapide ou course lente
                    else -> 0.95} // course
                kj=((dist*poids.toDouble()*coef*rendement()/4.0*4.18)
                        + deniv*9.8*poids*rendement()).toInt()
            }
            1-> {
                kj=((puissance*duree).toDouble()*rendement()/1000).toInt()
            }
            2 -> {val met: Double = when (intensite) {
                4 -> 10.5 // Cardio intense
                3 -> 7.5 // Cardio
                2 -> 5.5 // Workout normal
                1 -> 4.0 // Workout tranquille
                else -> 2.0} // Relaxation/Mobility
                kj=(met*duree.toDouble()/60*poids.toDouble()*3.5*rendement()/200.0/4.0*4.18).toInt()
            }
            3 -> {val deniv: Double = when (intensite) {
                4 -> 0.9
                3 -> 0.6
                2 -> 0.3
                1 -> 0.1
                else -> 0.0
            }
                val vit = dist*3600.0/duree.toDouble()
                kj = if (vit<=15.0) {
                    (dist*18.0*rendement()/4.0*4.18
                            + 0.8*deniv*9.8*(poids+13.0)*rendement()).toInt()
                } else {
                    (dist*(18.0+(vit-15.0)*(vit-15.0)*0.05)*rendement()/4.0*4.18
                            + 0.8*deniv*9.8*(poids+13.0)*rendement()).toInt()
                }
            }
            4 -> {
                kj=(dist*190.0*poids.toDouble()/55.0*rendement()/4*4.18).toInt()
            }
            5 -> {val met: Double = when (intensite) {
                4 -> 7.0 // Bucheron !
                3 -> 5.5 // Danse !
                2 -> 4.0 // Jardinage dur : brouette...
                1 -> 2.5 // Travaux intérieur normaux (peinture, electro...), jardinage cool
                else -> 1.5} // Ménage tranquille, vaisselle...
                kj=(met*duree.toDouble()/60*poids.toDouble()*3.5*rendement()/200.0/4.0*4.18).toInt()
            }
        }
    }
    // definition of initialisation
    init{
        //Log.d("François", "Passage dans Init de Workout")
        if (icon_pos==1 && kj!=0) {
            kj_to_power()
        } else {
            calc_kj()
            kj_to_power()
        }
    }
}
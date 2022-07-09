package com.froschprog.tongps

import android.content.res.Configuration
import android.util.Log.d
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AppCompatActivity
import com.froschprog.my_library.utils.MyApplication
import com.froschprog.my_library.utils.TinyDB
import java.text.NumberFormat
import java.util.*

open class BaseActivity : AppCompatActivity() {

    companion object{
        var Lang: Locale? = null
        var notif_on:Boolean=true
    }

    init{
        // si la langue n'est pas fixée -> au démarrage de l'app
        val mySettings = TinyDB(MyApplication.getInstance())
        if (Lang==null){
            // lecture de la valeur sauvegardée
            val lang = mySettings.getString("My_Lang_Str")
            if (lang.length==5)
                Lang= Locale(lang.substring(0..1),lang.substring(3..4))
            else
                Lang = Locale(lang)
        }
        // changement de la langue
        setLocate(this)
        // set notifications on
        setnotif()
        // set number format corresponding to Locale (language)
        //d("François", "decimal separator: "+DecimalFormatSymbols().getDecimalSeparator())
        nf = NumberFormat.getInstance()
    }

    private fun setLocate(wrapper: ContextThemeWrapper) {
        if ((Lang== Locale("")) || (Lang==null))  return
        Locale.setDefault(Lang!!)
        val config= Configuration()
        config.setLocale(Lang)
        d("François", "Lang: $Lang")
        wrapper.applyOverrideConfiguration(config)
        // sauvegarde de la langue
        val mySettings = TinyDB(MyApplication.getInstance())
        mySettings.putString("My_Lang_Str", Lang.toString())
    }

    fun setnotif(){
        // setting notifications
        val mySettings = TinyDB(MyApplication.getInstance())
        notif_on = mySettings.getBoolean("Notif")
    }




}
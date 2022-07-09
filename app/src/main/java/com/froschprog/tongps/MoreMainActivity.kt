package com.froschprog.tongps



import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getColor
import com.froschprog.my_library.transitions.*
import com.froschprog.my_library.utils.*
import kotlinx.android.synthetic.main.alertdialog_export.view.*
import kotlinx.android.synthetic.main.more_main_activity.*
import java.util.*

class MoreMainActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.more_main_activity)
        //Log.d("François", "Passage dans MoreMainActivity onCreate()")

        textviewApp.text = this.getString(R.string.app_presentation, BuildConfig.VERSION_NAME)

        // Language
        val listLangues = resources.getStringArray(R.array.Languages)
        val listlanguescodes = resources.getStringArray(R.array.Languages_codes)
        val a1 = listlanguescodes.indexOf(Locale.getDefault().toString().substring(0..1))
        val presentLangind = if (a1==-1) 2 else a1 // english if language not found in list

        val presentLang=listLangues[presentLangind]
        textViewLangueActuelle.text= getString(R.string.Present_Lang, presentLang)
        ButtonChangeLang.setOnClickListener {
            showChangeLang()
        }

        //Notifications
        switchNotif.visibility = View.GONE
    /*    switchNotif.setOnCheckedChangeListener(null)
        switchNotif.isChecked = notif_on
        switchNotif.setOnCheckedChangeListener { _, isChecked ->
            val mySettings = TinyDB(MyApplication.getInstance())
            if (isChecked) mySettings.putBoolean("Notif", true)
            else mySettings.putBoolean("Notif", false)
            BaseActivity().setnotif()
            // delete all alarms
            //if (!notif_on) AlarmGenerate().generateAlarms(eventarray,true)
        }*/

        // file export
        ButtonExport.setOnClickListener {
            //create custon title
            val tv = TextView(this)
            tv.text = getString(R.string.export_workouts_title)
            tv.textSize = 20F
            tv.setTextColor(getColor(this, R.color.texte))
            tv.setPadding(45, 16, 16, 16)
            //build dialog
            val mDialogView = layoutInflater.inflate(R.layout.alertdialog_export, null)
            val mBuilder = AlertDialog.Builder(this, R.style.CustomDialogTheme)
                    .setView(mDialogView)
                    .setCustomTitle(tv)
            // display dialog
            val mAlertDialog = mBuilder.show()
            // text in the dialog
            mDialogView.textViewExportAlert.text = getString(R.string.export_text,
                    MyApplication.getInstance().getExternalFilesDir(getString(R.string.app_name)))
            // click on Button "back" of dialog
            mDialogView.btnback.setOnClickListener {
                mAlertDialog.dismiss()
            }
            // click on Button "ok" of dialog
            mDialogView.btnok.setOnClickListener {
                //if (isWriteStoragePermissionGranted(this)){
                    // save in file
                    Writetofile(warray)
                    mAlertDialog.dismiss()
                //}
            }
        }
        // file Import
        ButtonImport.setOnClickListener {
            //create custon title
            val tv = TextView(this)
            tv.text = getString(R.string.import_workouts_dialog)
            tv.textSize = 20F
            tv.setTextColor(getColor(this, R.color.texte))
            tv.setPadding(45, 16, 16, 16)
            //build dialog
            val mDialogView = layoutInflater.inflate(R.layout.alertdialog_export, null)
            val mBuilder = AlertDialog.Builder(this, R.style.CustomDialogTheme)
                    .setView(mDialogView)
                    .setCustomTitle(tv)
            // display dialog
            val mAlertDialog = mBuilder.show()
            // text in the dialog
            mDialogView.textViewExportAlert.text = getString(R.string.import_text,
                    MyApplication.getInstance().getExternalFilesDir(getString(R.string.app_name)))
            // click on Button "back" of dialog
            mDialogView.btnback.setOnClickListener {
                mAlertDialog.dismiss()
            }
            // click on Button "ok" of dialog
            mDialogView.btnok.setOnClickListener {
                //if (isReadStoragePermissionGranted(this)) {
                    // save in file
                    val wa = Readfromfile()
                    for (w in wa) warray.add(w)
                    writeTinyDB(warray)
                    mAlertDialog.dismiss()
                //}
            }
        }

        // Back Button
        ButtonBackMoreMain.setOnClickListener {
            // return to MainActivity. Use of startActivity to reload the list and apply the change of language
            startActivity(Intent(this, MainActivity::class.java))
            Transitions(this@MoreMainActivity).setAnimation(Slide().InLeft())
        }

        // Help Button
        ButtonHelp.setOnClickListener {

        }
    }

    private fun showChangeLang() {
        val listLangues = resources.getStringArray(R.array.Languages)
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listLangues, -1) { dialog, which ->
            when (which) {
                0 -> Lang = Locale("fr","FR")
                1 -> Lang = Locale("de","DE")
                2 -> Lang = Locale("en")
            }
            dialog.dismiss()
            startActivity(intent)
            Transitions(this@MoreMainActivity).setAnimation(Fade().In())
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    override fun onBackPressed() {
        ButtonBackMoreMain.callOnClick()
    }

}



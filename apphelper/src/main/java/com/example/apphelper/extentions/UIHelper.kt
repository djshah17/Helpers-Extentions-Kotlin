package com.example.apphelper.extentions

import android.app.*
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.snackbar.Snackbar
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


private var progressBar: ProgressBar? = null
private var progressDialog: ProgressDialog? = null


fun Context.showAlertDialog(title: String?, message: String?) {
    try {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialog, which -> dialog.dismiss() }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun showSuccessSnackBar(view: View, message: String) {
    try {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(Color.GREEN)
        val textView =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        snackBar.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun showErrorSnackBar(view: View, message: String) {
    try {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(Color.RED)
        val textView =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        snackBar.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.showSuccessToast(message: String?) {
    try {
            val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)

            // set message color
            val textView = toast.view.findViewById(android.R.id.message) as? TextView
            textView?.setTextColor(Color.WHITE)
            textView?.gravity = Gravity.CENTER

            // set background color
            toast.view.setBackgroundColor(Color.GREEN)

            toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)

            toast.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun Context.showErrorToast(message: String?) {

    try {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)

        // set message color
        val textView = toast.view.findViewById(android.R.id.message) as? TextView
        textView?.setTextColor(Color.WHITE)
        textView?.gravity = Gravity.CENTER

        // set background color
        toast.view.setBackgroundColor(Color.RED)

        toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)

        toast.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun Context.changeStatusBarColor(colorId: Int) {
    try {
        val window = (this as Activity).window

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        // finally change the color
        window.statusBarColor = colorId
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// show progressbar
fun Context.showProgressBar() {
    try {
            val layout = (this as? Activity)?.findViewById<View>(android.R.id.content)?.rootView as? ViewGroup

            progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)
            progressBar?.let { it1 ->
                it1.isIndeterminate = true

                val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )

                val rl = RelativeLayout(this)

                rl.gravity = Gravity.CENTER
                rl.addView(it1)

                layout?.addView(rl, params)

                it1.visibility = View.VISIBLE
            }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// hide progressbar
fun hideProgressBar() {
    try {
        progressBar?.let {
            it.visibility = View.GONE
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// show progress dialog
fun Context.showProgressDialog(progressMessage: String) {
    try {
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }

            progressDialog = ProgressDialog(this)
            progressDialog?.setMessage(progressMessage)
            progressDialog?.setCancelable(false)
            progressDialog?.setCanceledOnTouchOutside(false)
            progressDialog?.show()

    } catch (e: Exception) {
        e.printStackTrace()
    }

}

// hide progress dialog
fun hideProgressDialog() {
    try {
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun Context.showDatePicker(editText: EditText?, dateFormat: String?) {
    val calendar = Calendar.getInstance()
    var currentYear = calendar.get(Calendar.YEAR)
    var currentMonth = calendar.get(Calendar.MONTH)
    var currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    if (editText?.text.toString() != "") {
        val df = SimpleDateFormat(dateFormat, Locale.US)
        try {
            val d = df.parse(editText?.text.toString())
            calendar.time = d
            currentYear = calendar.get(Calendar.YEAR)
            currentMonth = calendar.get(Calendar.MONTH)
            currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

    }

    val onDateSetListener = OnDateSetListener { view, year, month, dayOfMonth ->
        val sdf = SimpleDateFormat(dateFormat, Locale.US)
        val myCalendar = Calendar.getInstance()
        myCalendar.set(year, month, dayOfMonth)
        editText?.setText(sdf.format(myCalendar.time))
    }

    val datePickerDialog =
        DatePickerDialog(this, onDateSetListener, currentYear, currentMonth, currentDay)
    val datePicker = datePickerDialog.datePicker
    datePicker.minDate = System.currentTimeMillis() - 1000
    datePickerDialog.show()
}

fun Context.showTimePicker(editText: EditText?, timeFormat: String?, is24Hour: Boolean) {
    val calendar = Calendar.getInstance()
    var currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    var currentMinute = calendar.get(Calendar.MINUTE)

    if (editText?.text.toString() != "") {
        val df = SimpleDateFormat(timeFormat, Locale.US)
        try {
            val d = df.parse(editText?.text.toString())
            calendar.time = d
            currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            currentMinute = calendar.get(Calendar.MINUTE)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

    }

    val onTimeSetListener =
        TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
            var status = "AM"

            if (selectedHour > 11) {
                // If the hour is greater than or equal to 12
                // Then the current AM PM status is PM
                status = "PM"
            }

            // Initialize a new variable to hold 12 hour format hour value
            val hour_of_12_hour_format: Int

            if (selectedHour > 11) {
                // If the hour is greater than or equal to 12
                // Then we subtract 12 from the hour to make it 12 hour format time
                hour_of_12_hour_format = selectedHour - 12
            } else {
                hour_of_12_hour_format = selectedHour
            }
            var strTime = "";
            if (is24Hour) {
                strTime = selectedHour.toString() + ":" + selectedMinute.toString()
            } else {
                strTime = hour_of_12_hour_format.toString() + ":" + selectedMinute.toString() + " " + status
            }
            editText?.setText(strTime)
        }


    val timePickerDialog = TimePickerDialog(this, onTimeSetListener, currentHour, currentMinute, is24Hour)
    timePickerDialog.show()
}


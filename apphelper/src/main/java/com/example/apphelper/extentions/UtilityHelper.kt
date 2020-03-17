package com.example.apphelper.extentions

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.net.ConnectivityManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.apphelper.R
import java.util.concurrent.atomic.AtomicInteger

fun Context.isInternetAvailable(errorMessage: String?): Boolean {
    try {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return if (netInfo != null && netInfo.isConnected) {
            true
        } else {
            showErrorToast(errorMessage)
            false
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}

fun Context.isAppIsInBackground(): Boolean {
    var isInBackground = true

    try {
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses = am.runningAppProcesses
        for (processInfo in runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (activeProcess in processInfo.pkgList) {
                    if (activeProcess == packageName) {
                        isInBackground = false
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return isInBackground
}


// show keyboard
fun Context.showSoftKeyboard() {
    try {
        val view = (this as Activity).currentFocus
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// hide keyboard
fun Context.hideSoftKeyboard() {
    // Check if no view has focus:
    try {
        val view = (this as Activity).currentFocus
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.addFragment(
    containerId: Int?,
    fragment: Fragment?,
    isAddToBackStack: Boolean,
    backStackName: String?
) {
    try {
        containerId?.let {
            fragment?.let { it1 ->
                if (isAddToBackStack) {
                    (this as FragmentActivity).supportFragmentManager?.beginTransaction()?.add(
                        it, it1
                    )?.addToBackStack(backStackName)?.commitAllowingStateLoss()
                } else {
                    (this as FragmentActivity).supportFragmentManager?.beginTransaction()
                        ?.add(
                            it, it1
                        )?.commitAllowingStateLoss()
                }

            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.replaceFragment(
    containerId: Int?,
    fragment: Fragment?,
    isAddToBackStack: Boolean,
    backStackName: String?
) {
    try {
        containerId?.let {
            fragment?.let { it1 ->
                if (isAddToBackStack) {
                    (this as FragmentActivity).supportFragmentManager?.beginTransaction()?.replace(
                        it, it1
                    )?.addToBackStack(backStackName)?.commitAllowingStateLoss()
                } else {
                    (this as FragmentActivity).supportFragmentManager?.beginTransaction()
                        ?.replace(
                            it, it1
                        )?.commitAllowingStateLoss()
                }

            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


private val sNextGeneratedId = AtomicInteger(1)
fun generateViewId(): Int {
    var result = 0

    try {
        while (true) {
            result = sNextGeneratedId.get()
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            var newValue = result + 1
            if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return result
}


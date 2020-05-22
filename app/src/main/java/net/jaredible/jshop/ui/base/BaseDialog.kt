package net.jaredible.jshop.ui.base

import android.widget.Toast
import androidx.fragment.app.DialogFragment

abstract class BaseDialog : DialogFragment() {

    fun showMessage(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(activity, text, duration).show()
    }

}
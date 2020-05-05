package net.jaredible.shopper.ui.base

import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class BaseDialog : DialogFragment() {

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

    fun showMessage(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(activity, text, duration).show()
    }

}
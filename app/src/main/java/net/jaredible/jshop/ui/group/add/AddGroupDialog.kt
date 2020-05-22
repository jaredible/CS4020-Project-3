package net.jaredible.jshop.ui.group.add

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.dialog_add_group.*
import net.jaredible.jshop.R
import net.jaredible.jshop.ui.base.BaseDialog

class AddGroupDialog : BaseDialog() {

    companion object {
        val TAG = AddGroupDialog::class.java.simpleName
        private const val BUNDLE_GROUP_TITLE = "BUNDLE_GROUP_TITLE"

        fun newInstance(): AddGroupDialog {
            return AddGroupDialog()
        }
    }

    private lateinit var viewModel: AddGroupViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AddGroupViewModel::class.java)

        vAdd.setOnClickListener(onAddClicked())
        vCancel.setOnClickListener(onCancelClicked())

        savedInstanceState?.let {
            vTitle.setText(it.getString(BUNDLE_GROUP_TITLE))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(BUNDLE_GROUP_TITLE, vTitle.text.toString())
    }

    private fun onAddClicked(): View.OnClickListener {
        return View.OnClickListener {
            viewModel.addGroup(vTitle.text.toString())
            dismiss()
        }
    }

    private fun onCancelClicked(): View.OnClickListener {
        return View.OnClickListener {
            dismiss()
        }
    }

}
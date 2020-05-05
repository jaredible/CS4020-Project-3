package net.jaredible.shopper.ui.group.add

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import net.jaredible.shopper.R
import net.jaredible.shopper.ui.base.BaseDialog

class AddGroupDialog : BaseDialog() {

    companion object {
        val TAG = AddGroupDialog::class.java.simpleName

        fun newInstance(): AddGroupDialog {
            return AddGroupDialog()
        }
    }

    private lateinit var viewModel: AddGroupViewModel

    private lateinit var textTitle: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonCancel: Button

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
        return inflater.inflate(R.layout.dialog_addgroup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AddGroupViewModel::class.java)

        textTitle = view.findViewById(R.id.text_title)
        buttonAdd = view.findViewById(R.id.button_add)
        buttonCancel = view.findViewById(R.id.button_cancel)

        buttonAdd.setOnClickListener(onAddClicked())
        buttonCancel.setOnClickListener(onCancelClicked())
    }

    private fun onAddClicked(): View.OnClickListener {
        return View.OnClickListener {
            viewModel.addGroup(textTitle.text.toString())
            dismiss()
        }
    }

    private fun onCancelClicked(): View.OnClickListener {
        return View.OnClickListener {
            dismiss()
        }
    }

}
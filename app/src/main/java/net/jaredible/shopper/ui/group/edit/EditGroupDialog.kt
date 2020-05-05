package net.jaredible.shopper.ui.group.edit

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import net.jaredible.shopper.R
import net.jaredible.shopper.ui.base.BaseDialog

class EditGroupDialog : BaseDialog() {

    companion object {
        val TAG = EditGroupDialog::class.java.simpleName
        private const val BUNDLE_GROUP_TITLE = "BUNDLE_GROUP_TITLE"
        private const val ARGUMENT_GROUP_ID = "ARGUMENT_GROUP_ID"

        fun newInstance(groupId: Long): EditGroupDialog {
            val dialog = EditGroupDialog()
            val args = Bundle()
            args.putLong(ARGUMENT_GROUP_ID, groupId)
            dialog.arguments = args
            return dialog
        }
    }

    private lateinit var viewModel: EditGroupViewModel

    private lateinit var textTitle: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button
    private lateinit var buttonRemove: Button

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
        return inflater.inflate(R.layout.dialog_editgroup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val groupId = arguments!!.getLong(ARGUMENT_GROUP_ID, 0)
        val viewModelFactory = EditGroupModelFactory(activity!!.application, groupId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditGroupViewModel::class.java)

        textTitle = view.findViewById(R.id.text_title)
        buttonSave = view.findViewById(R.id.button_save)
        buttonCancel= view.findViewById(R.id.button_cancel)
        buttonRemove = view.findViewById(R.id.button_remove)

        buttonSave.setOnClickListener(onSaveClicked())
        buttonCancel.setOnClickListener(onCancelClicked())
        buttonRemove.setOnClickListener(onRemoveClicked())

        viewModel.getGroup().observeOnce(this, Observer { group ->
            textTitle.setText(group.title)

            savedInstanceState?.let { textTitle.setText(it.getString(BUNDLE_GROUP_TITLE)) }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(BUNDLE_GROUP_TITLE, textTitle.text.toString())
    }

    private fun onSaveClicked(): View.OnClickListener {
        return View.OnClickListener {
            viewModel.updateGroupTitle(textTitle.text.toString())
            dismiss()
        }
    }

    private fun onCancelClicked(): View.OnClickListener {
        return View.OnClickListener {
            dismiss()
        }
    }

    private fun onRemoveClicked(): View.OnClickListener {
        return View.OnClickListener {
            viewModel.deleteGroup()
            dismiss()
        }
    }

}
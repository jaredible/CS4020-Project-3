package net.jaredible.jshop.ui.group.edit

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.dialog_edit_group.*
import net.jaredible.jshop.R
import net.jaredible.jshop.ui.base.BaseDialog
import net.jaredible.jshop.util.observeOnce

class EditGroupDialog : BaseDialog() {

    companion object {
        val TAG = EditGroupDialog::class.java.simpleName
        private const val ARGUMENT_GROUP_ID = "ARGUMENT_GROUP_ID"
        private const val BUNDLE_GROUP_TITLE = "BUNDLE_GROUP_TITLE"

        fun newInstance(groupId: Long): EditGroupDialog {
            val dialog = EditGroupDialog()
            val args = Bundle()
            args.putLong(ARGUMENT_GROUP_ID, groupId)
            dialog.arguments = args
            return dialog
        }
    }

    private lateinit var viewModel: EditGroupViewModel

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
        return inflater.inflate(R.layout.dialog_edit_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val groupId = requireArguments().getLong(ARGUMENT_GROUP_ID, 0)
        val viewModelFactory = EditGroupModelFactory(requireActivity().application, groupId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditGroupViewModel::class.java)

        vSave.setOnClickListener(onSaveClicked())
        vCancel.setOnClickListener(onCancelClicked())
        vRemove.setOnClickListener(onRemoveClicked())

        viewModel.getGroup().observeOnce(this, Observer { group ->
            vTitle.setText(group.title)

            savedInstanceState?.let { vTitle.setText(it.getString(BUNDLE_GROUP_TITLE)) }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(BUNDLE_GROUP_TITLE, vTitle.text.toString())
    }

    private fun onSaveClicked(): View.OnClickListener {
        return View.OnClickListener {
            viewModel.updateGroupTitle(vTitle.text.toString())
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
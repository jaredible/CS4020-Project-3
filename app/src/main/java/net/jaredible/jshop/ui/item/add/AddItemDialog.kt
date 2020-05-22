package net.jaredible.jshop.ui.item.add

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.dialog_add_item.*
import net.jaredible.jshop.R
import net.jaredible.jshop.ui.base.BaseDialog

class AddItemDialog : BaseDialog() {

    companion object {
        val TAG = AddItemDialog::class.java.simpleName
        private const val ARGUMENT_GROUP_ID = "ARGUMENT_GROUP_ID"
        private const val BUNDLE_ITEM_NAME = "BUNDLE_ITEM_NAME"
        private const val BUNDLE_ITEM_PRICE = "BUNDLE_ITEM_PRICE"
        private const val BUNDLE_ITEM_QUANTITY = "BUNDLE_ITEM_QUANTITY"

        fun newInstance(groupId: Long): AddItemDialog {
            val dialog = AddItemDialog()
            val args = Bundle()
            args.putLong(ARGUMENT_GROUP_ID, groupId)
            dialog.arguments = args
            return dialog
        }
    }

    private lateinit var viewModel: AddItemViewModel

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
        return inflater.inflate(R.layout.dialog_add_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val groupId = requireArguments().getLong(ARGUMENT_GROUP_ID, 0)
        val viewModelFactory = AddItemViewModelFactory(requireActivity().application, groupId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddItemViewModel::class.java)

        vName.addTextChangedListener(onItemNameChanged())
        vMinus.setOnClickListener(onMinusClicked())
        vPlus.setOnClickListener(onPlusClicked())
        vAdd.setOnClickListener(onAddClicked())
        vCancel.setOnClickListener(onCancelClicked())

        vQuantity.text = "0"
        savedInstanceState?.let {
            vName.setText(it.getString(BUNDLE_ITEM_NAME))
            vPrice.setText(it.getString(BUNDLE_ITEM_PRICE))
            vQuantity.text = it.getString(BUNDLE_ITEM_QUANTITY)
        }

        setQuantityButtonsState()
        setAddButtonState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(BUNDLE_ITEM_NAME, vName.text.toString())
        outState.putString(BUNDLE_ITEM_PRICE, vPrice.text.toString())
        outState.putString(BUNDLE_ITEM_QUANTITY, vQuantity.text.toString())
    }

    private fun setQuantityButtonsState() {
        val quantity = vQuantity.text.toString().toInt()
        vMinus.isEnabled = quantity > 0
        vPlus.isEnabled = quantity < 10
    }

    private fun setAddButtonState() {
        vAdd.isEnabled = vName.text.toString().isNotBlank()
    }

    private fun onItemNameChanged(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { setAddButtonState() }
            override fun afterTextChanged(s: Editable?) {}
        }
    }

    private fun onMinusClicked(): View.OnClickListener {
        return View.OnClickListener {
            vQuantity.text = vQuantity.text.toString().toInt().dec().toString()
            setQuantityButtonsState()
        }
    }

    private fun onPlusClicked(): View.OnClickListener {
        return View.OnClickListener {
            vQuantity.text = vQuantity.text.toString().toInt().inc().toString()
            setQuantityButtonsState()
        }
    }

    private fun onAddClicked(): View.OnClickListener {
        return View.OnClickListener {
            viewModel.addItem(
                vName.text.toString(),
                vPrice.text.toString(),
                vQuantity.text.toString()
            )
            dismiss()
        }
    }

    private fun onCancelClicked(): View.OnClickListener {
        return View.OnClickListener {
            dismiss()
        }
    }

}
package net.jaredible.jshop.ui.item.edit

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.dialog_edit_item.*
import net.jaredible.jshop.R
import net.jaredible.jshop.ui.base.BaseDialog
import net.jaredible.jshop.util.CurrencyUtil
import net.jaredible.jshop.util.observeOnce

class EditItemDialog : BaseDialog() {

    companion object {
        val TAG = EditItemDialog::class.java.simpleName
        private const val ARGUMENT_GROUP_ID = "ARGUMENT_GROUP_ID"
        private const val ARGUMENT_ITEM_ID = "ARGUMENT_ITEM_ID"
        private const val BUNDLE_ITEM_NAME = "BUNDLE_ITEM_NAME"
        private const val BUNDLE_ITEM_PRICE = "BUNDLE_ITEM_PRICE"
        private const val BUNDLE_ITEM_QUANTITY = "BUNDLE_ITEM_QUANTITY"

        fun newInstance(groupId: Long, itemId: Long): EditItemDialog {
            val dialog = EditItemDialog()
            val args = Bundle()
            args.putLong(ARGUMENT_GROUP_ID, groupId)
            args.putLong(ARGUMENT_ITEM_ID, itemId)
            dialog.arguments = args
            return dialog
        }
    }

    private lateinit var viewModel: EditItemViewModel

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
        return inflater.inflate(R.layout.dialog_edit_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val groupId = requireArguments().getLong(ARGUMENT_GROUP_ID, 0)
        val itemId = requireArguments().getLong(ARGUMENT_ITEM_ID, 0)
        val viewModelFactory = EditItemViewModelFactory(requireActivity().application, groupId, itemId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditItemViewModel::class.java)

        vName.addTextChangedListener(onItemNameChanged())
        vMinus.setOnClickListener(onMinusClicked())
        vPlus.setOnClickListener(onPlusClicked())
        vSave.setOnClickListener(onSaveClicked())
        vCancel.setOnClickListener(onCancelClicked())
        vRemove.setOnClickListener(onRemoveClicked())

        viewModel.getItem().observeOnce(this, Observer { item ->
            vName.setText(item.name)
            vPrice.setText(CurrencyUtil.format(item.price))
            vQuantity.text = item.quantity.toString()

            savedInstanceState?.let {
                vName.setText(it.getString(BUNDLE_ITEM_NAME))
                vPrice.setText(it.getString(BUNDLE_ITEM_PRICE))
                vQuantity.text = it.getString(BUNDLE_ITEM_QUANTITY)
            }

            setQuantityButtonsState()
            setSaveButtonState()
        })
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

    private fun setSaveButtonState() {
        vSave.isEnabled = vName.text.toString().isNotBlank()
    }

    private fun onItemNameChanged(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { setSaveButtonState() }
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

    private fun onSaveClicked(): View.OnClickListener {
        return View.OnClickListener {
            viewModel.apply {
                updateItemName(vName.text.toString())
                updateItemPrice(vPrice.text.toString())
                updateItemQuantity(vQuantity.text.toString())
            }
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
            viewModel.deleteItem()
            dismiss()
        }
    }

}
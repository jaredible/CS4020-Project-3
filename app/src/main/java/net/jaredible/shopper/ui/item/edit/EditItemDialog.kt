package net.jaredible.shopper.ui.item.edit

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import net.jaredible.shopper.R
import net.jaredible.shopper.ui.base.BaseDialog
import net.jaredible.shopper.util.CurrencyUtil

class EditItemDialog : BaseDialog() {

    companion object {
        val TAG = EditItemDialog::class.java.simpleName
        private const val BUNDLE_QUANTITY = "BUNDLE_QUANTITY"
        private const val ARGUMENT_GROUP_ID = "ARGUMENT_GROUP_ID"
        private const val ARGUMENT_ITEM_ID = "ARGUMENT_ITEM_ID"

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

    private lateinit var textName: EditText
    private lateinit var textPrice: EditText
    private lateinit var textQuantity: TextView
    private lateinit var buttonMinus: ImageButton
    private lateinit var buttonPlus: ImageButton
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
        return inflater.inflate(R.layout.dialog_edititem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val groupId = arguments!!.getLong(ARGUMENT_GROUP_ID, 0)
        val itemId = arguments!!.getLong(ARGUMENT_ITEM_ID, 0)
        val viewModelFactory = EditItemViewModelFactory(activity!!.application, groupId, itemId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditItemViewModel::class.java)

        textName = view.findViewById(R.id.text_name)
        textPrice = view.findViewById(R.id.text_price)
        textQuantity = view.findViewById(R.id.text_quantity)
        buttonMinus = view.findViewById(R.id.button_minus)
        buttonPlus = view.findViewById(R.id.button_plus)
        buttonSave = view.findViewById(R.id.button_save)
        buttonCancel = view.findViewById(R.id.button_cancel)
        buttonRemove = view.findViewById(R.id.button_remove)

        buttonMinus.setOnClickListener(onMinusClicked())
        buttonPlus.setOnClickListener(onPlusClicked())
        buttonSave.setOnClickListener(onSaveClicked())
        buttonCancel.setOnClickListener(onCancelClicked())
        buttonRemove.setOnClickListener(onRemoveClicked())

        viewModel.getItem().observeOnce(this, Observer { item ->
            textName.setText(item.name)
            textPrice.setText(CurrencyUtil.convert(item.price))
            textQuantity.text = item.quantity.toString()

            savedInstanceState?.let { textQuantity.text = it.getString(BUNDLE_QUANTITY) }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(BUNDLE_QUANTITY, textQuantity.text.toString())
    }

    private fun onMinusClicked(): View.OnClickListener {
        return View.OnClickListener {
            textQuantity.text = textQuantity.text.toString().toInt().dec().toString()
        }
    }

    private fun onPlusClicked(): View.OnClickListener {
        return View.OnClickListener {
            textQuantity.text = textQuantity.text.toString().toInt().inc().toString()
        }
    }

    private fun onSaveClicked(): View.OnClickListener {
        return View.OnClickListener {
            viewModel.updateItemName(textName.text.toString())
            viewModel.updateItemPrice(textPrice.text.toString())
            viewModel.updateItemQuantity(textQuantity.text.toString())
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
package net.jaredible.shopper.ui.item.add

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import net.jaredible.shopper.R
import net.jaredible.shopper.ui.base.BaseDialog

class AddItemDialog : BaseDialog() {

    companion object {
        val TAG = AddItemDialog::class.java.simpleName
        private const val BUNDLE_QUANTITY = "BUNDLE_QUANTITY"
        private const val ARGUMENT_GROUP_ID = "ARGUMENT_GROUP_ID"

        fun newInstance(groupId: Long): AddItemDialog {
            val dialog = AddItemDialog()
            val args = Bundle()
            args.putLong(ARGUMENT_GROUP_ID, groupId)
            dialog.arguments = args
            return dialog
        }
    }

    private lateinit var viewModel: AddItemViewModel

    private lateinit var textName: EditText
    private lateinit var textPrice: EditText
    private lateinit var textQuantity: TextView
    private lateinit var buttonMinus: ImageButton
    private lateinit var buttonPlus: ImageButton
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
        return inflater.inflate(R.layout.dialog_additem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val groupId = arguments!!.getLong(ARGUMENT_GROUP_ID, 0)
        val viewModelFactory = AddItemViewModelFactory(activity!!.application, groupId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddItemViewModel::class.java)

        textName = view.findViewById(R.id.text_name)
        textPrice = view.findViewById(R.id.text_price)
        textQuantity = view.findViewById(R.id.text_quantity)
        buttonMinus = view.findViewById(R.id.button_minus)
        buttonPlus = view.findViewById(R.id.button_plus)
        buttonAdd = view.findViewById(R.id.button_add)
        buttonCancel = view.findViewById(R.id.button_cancel)

        buttonMinus.setOnClickListener(onMinusClicked())
        buttonPlus.setOnClickListener(onPlusClicked())
        buttonAdd.setOnClickListener(onAddClicked())
        buttonCancel.setOnClickListener(onCancelClicked())

        savedInstanceState?.let { textQuantity.text = it.getString(BUNDLE_QUANTITY) }
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

    private fun onAddClicked(): View.OnClickListener {
        return View.OnClickListener {
            viewModel.addItem(textName.text.toString(), textPrice.text.toString(), textQuantity.text.toString())
            dismiss()
        }
    }

    private fun onCancelClicked(): View.OnClickListener {
        return View.OnClickListener {
            dismiss()
        }
    }

}
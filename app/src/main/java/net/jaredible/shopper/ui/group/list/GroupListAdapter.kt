package net.jaredible.shopper.ui.group.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import net.jaredible.shopper.R
import net.jaredible.shopper.data.entity.Group
import net.jaredible.shopper.ui.base.BaseAdapter
import net.jaredible.shopper.ui.base.BaseViewHolder

class GroupListAdapter(private val groupListView: GroupListView) : BaseAdapter<GroupListAdapter.ListGroupViewHolder>() {

    private val groups = mutableListOf<Group>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListGroupViewHolder {
        val itemGroup = LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return ListGroupViewHolder(itemGroup)
    }

    override fun getItemCount() = groups.size

    override fun onBindViewHolder(holder: ListGroupViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setGroups(groups: List<Group>) {
        this.groups.clear()
        this.groups.addAll(groups)
        notifyDataSetChanged()
    }

    inner class ListGroupViewHolder(private val view: View) : BaseViewHolder(view) {

        private val textTitle: TextView = view.findViewById(R.id.text_title)
        private val buttonEdit: ImageButton = view.findViewById(R.id.button_edit)

        override fun bind(position: Int) {
            val group = groups[position]

            textTitle.text = group.title

            view.setOnClickListener(onGroupClicked(group))
            buttonEdit.setOnClickListener(onEditGroupClicked(group))
        }

        private fun onGroupClicked(group: Group): View.OnClickListener {
            return View.OnClickListener {
                groupListView.openItemListScreen(group.id)
            }
        }

        private fun onEditGroupClicked(group: Group): View.OnClickListener {
            return View.OnClickListener {
                groupListView.showEditGroupDialog(group.id)
            }
        }

    }

}
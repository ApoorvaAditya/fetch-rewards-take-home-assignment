package com.example.fetchrewardstakehome.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.fetchrewardstakehome.R
import com.example.fetchrewardstakehome.model.Item

class ItemExpandableListAdapter(
    private val ctx: Context,
    private val listIds: List<Int>,
    private val listIdsToItems: Map<Int, List<Item>>,
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return listIds.size
    }

    override fun getChildrenCount(listIdIndex: Int): Int {
        return listIdsToItems[getGroup(listIdIndex)]!!.size
    }

    override fun getGroup(listIdIndex: Int): Any {
        return listIds[listIdIndex]
    }

    override fun getChild(listIdIndex: Int, itemIndex: Int): Any {
        return listIdsToItems[getGroup(listIdIndex)]!![itemIndex]
    }

    override fun getGroupId(listIdIndex: Int): Long {
        return listIdIndex.toLong()
    }

    override fun getChildId(listIdIndex: Int, itemIndex: Int): Long {
        return itemIndex.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(listIdIndex: Int, p1: Boolean, view: View?, p3: ViewGroup?): View {
        // Get the listId for the group view
        val listId = getGroup(listIdIndex) as Int

        // Create a new ExpandableListGroupItem if the view is null
        val expandableListGroupItemView: View = if (view != null) {
            view
        } else {
            val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.expandable_list_group_item, null)
        }

        // Set the titleTextView text to the listId
        val listTitleTextView: TextView = expandableListGroupItemView.findViewById(R.id.titleTextView)
        listTitleTextView.text = "List ID: $listId"

        return expandableListGroupItemView
    }

    override fun getChildView(listIdIndex: Int, itemIndex: Int, p2: Boolean, view: View?, p4: ViewGroup?): View {
        // Get the item for the child view
        val item = getChild(listIdIndex, itemIndex) as Item

        // Create a new ExpandableListChildItem if the view is null
        val expandableListChildItemView: View = if (view != null) {
            view
        } else {
            val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.expandable_list_child_item, null)
        }

        // Set the idTextView text to the item id
        val idTextView: TextView = expandableListChildItemView.findViewById(R.id.idTextView)
        idTextView.text = item.id.toString()

        // Set the nameTextView text to the item name
        val nameTextView: TextView = expandableListChildItemView.findViewById(R.id.nameTextView)
        nameTextView.text = item.name.toString()

        return expandableListChildItemView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return false
    }
}
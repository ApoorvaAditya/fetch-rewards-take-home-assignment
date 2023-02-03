package com.example.fetchrewardstakehome.adapters

import android.content.Context
import android.graphics.Typeface
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
        val listTitle = getGroup(listIdIndex) as Int
        var expandableListGroupView: View
        if (view == null) {
            val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            expandableListGroupView = inflater.inflate(R.layout.expandable_list_group_item, null)
        } else {
            expandableListGroupView = view
        }
        val listTitleTextView: TextView = expandableListGroupView.findViewById(R.id.titleTextView)
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle.toString()
        return expandableListGroupView
    }

    override fun getChildView(listIdIndex: Int, itemIndex: Int, p2: Boolean, view: View?, p4: ViewGroup?): View {
        val item = getChild(listIdIndex, itemIndex) as Item
        var expandableListItemView: View
        if (view == null) {
            val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            expandableListItemView = inflater.inflate(R.layout.expandable_list_chid_item, null)
        } else {
            expandableListItemView = view
        }
        val idTextView: TextView = expandableListItemView.findViewById(R.id.idTextView)
        idTextView.text = item.id.toString()

        val nameTextView: TextView = expandableListItemView.findViewById(R.id.nameTextView)
        nameTextView.text = item.name.toString()
        return expandableListItemView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return false
    }
}
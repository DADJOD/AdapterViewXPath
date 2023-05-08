package com.example.adapterviewxpath

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu


@Suppress("NAME_SHADOWING")
class SaintAdapter(context: Context, resource: Int, private val saints: MutableList<Saint>) :
    ArrayAdapter<Saint>(context, resource) {
    override fun getCount(): Int {
        return saints.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val saint = saints[position]
        var holder: Holder

        if (convertView == null) {
            val inflater = LayoutInflater.from(parent.context)
            convertView = inflater.inflate(R.layout.listviewitem, parent, false)
            holder = Holder()

            holder.name = convertView.findViewById(R.id.text) as TextView
            holder.dob = convertView.findViewById(R.id.dob) as TextView
            holder.dod = convertView.findViewById(R.id.dod) as TextView
            holder.rating = convertView.findViewById(R.id.rating) as RatingBar
            holder.threedots = convertView.findViewById(R.id.threedots) as ImageView

            convertView.tag = holder
            Log.d("happySDK2","inflater.inflate")
        }

        holder = convertView!!.tag as Holder

        holder.name.text = saint.name
        holder.dob.text = saint.dob
        holder.dod.text = saint.dod
        holder.rating.rating = saint.rating
        holder.threedots.setOnClickListener {
            showPopup(it, position)
        }
        return convertView
    }

    private fun showPopup(view: View, position: Int) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.inflate(R.menu.context)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.context_delete -> {
                    saints.removeAt(position)
                    notifyDataSetChanged()
                    return@setOnMenuItemClickListener true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

//        popupMenu
//            .setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.context_delete -> {
//                        selection.remove(position)
//                        data.remove(position)
//                        notifyDataSetChanged()
//                        return@OnMenuItemClickListener true
//                    }
//                }
//                false
//            })
//        popupMenu.show()
//
//    }

    private class Holder {
        lateinit var name: TextView
        lateinit var dob: TextView
        lateinit var dod: TextView
        lateinit var rating: RatingBar
        lateinit var threedots: ImageView
    }
}

//class SaintAdapter(context: Context, resource: Int, private val saints: List<Saint>) :
//    ArrayAdapter<Saint?>(context, resource) {
//    override fun getCount(): Int {
//        return saints.size
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        var convertView = convertView
//        val s = saints[position]
//        var holder: Holder? = null
//        if (convertView == null) {
//            val inflater = LayoutInflater.from(parent.context)
//            convertView = inflater.inflate(R.layout.listviewitem, parent, false)
//            holder = Holder()
//            holder.name = convertView!!.findViewById<View>(R.id.text) as TextView?
//            holder.dob = convertView.findViewById<View>(R.id.dob) as TextView?
//            holder.dod = convertView.findViewById<View>(R.id.dod) as TextView?
//            holder.rating = convertView.findViewById<View>(R.id.rating) as RatingBar?
//            holder.three = convertView.findViewById<View>(R.id.threedots) as ImageView?
//            convertView.setTag(holder)
//            Log.d("happy", "inflater.inflate")
//        }
//        holder = convertView!!.tag as Holder
//        holder!!.name!!.text = s.name
//        holder.dob!!.text = s.dob
//        holder.dod!!.text = s.dod
//        holder.rating!!.rating = s.rating
//        return convertView
//    }
//
//    private class Holder {
//        var name: TextView? = null
//        var dob: TextView? = null
//        var dod: TextView? = null
//        var rating: RatingBar? = null
//        var three: ImageView? = null
//    }
//}
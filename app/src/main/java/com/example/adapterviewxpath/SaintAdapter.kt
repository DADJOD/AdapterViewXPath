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

class SaintAdapter(context: Context, resource: Int, private val saints: List<Saint>) :
    ArrayAdapter<Saint>(context, resource) {
    override fun getCount(): Int {
        return saints.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        var convertView = convertView
        val saint = saints[position]
        val holder: Holder

        if (convertView == null) {
            val inflater = LayoutInflater.from(parent.context)
            val convertView = inflater.inflate(R.layout.listviewitem, parent, false)
            holder = Holder()
            
            val name = convertView.findViewById(R.id.text) as TextView
            val dob = convertView.findViewById(R.id.dob) as TextView
            val dod = convertView.findViewById(R.id.dod) as TextView
            val rating = convertView.findViewById(R.id.rating) as RatingBar
            val threedots = convertView.findViewById(R.id.threedots) as ImageView
        }

        holder.name?.text = saint.name
        dob.text = saint.dob
        dod.text = saint.dod
        rating.rating = saint.rating

        return convertView
    }

    class Holder() {
        val name: TextView? = null
        val dob: TextView? = null
        val dod: TextView? = null
        val rating: RatingBar? = null
        val threedots: ImageView? = null
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
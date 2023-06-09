package com.example.adapterviewxpath

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.util.Collections.*
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

@Suppress("NAME_SHADOWING", "DEPRECATION")
class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener,
    AdapterView.OnItemLongClickListener {

    private var list: ListView? = null
    private val saints: MutableList<Saint> = ArrayList()
    private var adapter: SaintAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = findViewById(R.id.list)

        // Источник данных для парсера XML из ресурсов
        val mySaints = InputSource(resources.openRawResource(R.raw.saints))
        // Новый XPath запрос
        val xPath = XPathFactory.newInstance().newXPath()

        // Подробно об XPath
        // http://www.w3schools.com/xsl/xpath_syntax.asp

        // Собственно запрос
        val expression = "/saints/saint"
        val nodes: NodeList
        try {
            // Результат XPath запроса - набор узлов
            nodes = xPath.evaluate(expression, mySaints, XPathConstants.NODESET) as NodeList
            val numSaints = nodes.length
            // Для каждого из узлов
            for (i in 0 until numSaints) {
                // Узел
                val saint = nodes.item(i)
                val name = saint.firstChild.textContent
                val dob = saint.childNodes.item(1).textContent
                val dod = saint.childNodes.item(2).textContent
                val s = Saint(name, dob, dod, 0f)
                saints.add(s)
                Log.d("happySDK", "name: $name")
            }
        } catch (_: Exception) {
        }
        saints.sort()

        adapter = SaintAdapter(this, R.layout.listviewitem, saints)
        list!!.adapter = adapter

        list!!.onItemClickListener = this

        list!!.onItemLongClickListener = this
    }

    // Вызывается при создании контекстного меню
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo) {
        menuInflater.inflate(R.menu.context, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    // Вызывается при выборе элемента контекстного меню
//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        val info = item.menuInfo as AdapterContextMenuInfo?
//        ///
//        return super.onContextItemSelected(item)
//    }

    // Вызывается при создании меню
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (adapter!!.hasSelected()) {
            menuInflater.inflate(R.menu.delete, menu)
        } else {
            menuInflater.inflate(R.menu.main, menu)
        }

        return super.onCreateOptionsMenu(menu)

    }

    // Вызывается при выборе элемента меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                showAddDialog()
                true
            }

            R.id.menu_up -> {
                saints.sort()
                adapter!!.notifyDataSetChanged()
                true
            }

            R.id.menu_down -> {
//                sort(saints, reverseOrder())
                saints.reverse()
                adapter!!.notifyDataSetChanged()
                true
            }

            R.id.main_delete -> {
                adapter!!.deleteSelected()
                invalidateOptionsMenu()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("InflateParams", "MissingInflatedId")
    private fun showAddDialog() {
        val dialog = layoutInflater.inflate(R.layout.dialog_add, null)
        val text = dialog.findViewById(R.id.dialog_add) as EditText
        val builder = AlertDialog.Builder(this)

        builder
            .setView(dialog)
            .setTitle("Add a Saint")
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton("Create") { dialog, _ ->
                val name = text.text.toString()
                saints.add(
                    Saint(name, "", "", 0f)
                )
                adapter?.notifyDataSetChanged()
                dialog.dismiss()
            }
            .create()
            .show()
    }

    companion object {
        // Константы для передачи данных через Intent
        // Должны быть доступны "детальной" Activity
        const val SAINT_NAME = "SAINT_NAME"
        const val SAINT_ID = "SAINT_ID"
        const val SAINT_RATING = "SAINT_RATING"
        const val RATING_REQUEST = 777
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (!adapter!!.hasSelected()) {
            val intent = Intent(this, SaintDetail::class.java)

            val s = saints[position]
            intent.putExtra(SAINT_NAME, s.name)
            intent.putExtra(SAINT_ID, position)
            intent.putExtra(SAINT_RATING, s.rating)

            startActivityForResult(intent, RATING_REQUEST)
        } else {
            toggleSelection(position)
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RATING_REQUEST) {
            val rating = data?.getFloatExtra(SAINT_RATING, -1f)
            val id = data?.getIntExtra(SAINT_ID, -1)

            if (rating!! >= 0 && id!! >= 0) {
                val s = saints[id]
                s.rating = rating
                adapter!!.notifyDataSetChanged()
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ): Boolean {
        toggleSelection(position)
        return true
    }

    private fun toggleSelection(position: Int) {
        adapter!!.toggleSelection(position)
        invalidateOptionsMenu()
    }
}
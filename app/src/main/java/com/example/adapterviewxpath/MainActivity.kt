package com.example.adapterviewxpath

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

class MainActivity : AppCompatActivity() {

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
            if (nodes != null) {
                val numSaints = nodes.length
                // Для каждого из узлов
                for (i in 0 until numSaints) {
                    // Узел
                    val saint = nodes.item(i)
                    ///
                    val name = saint.firstChild.textContent
                    val dob = saint.childNodes.item(1).textContent
                    val dod = saint.childNodes.item(2).textContent
                    val s = Saint(name, dob, dod, 0f)
                    

//                    val name = saint.firstChild.textContent
//                    val dob = saint.childNodes.item(1).textContent
//                    val dod = saint.childNodes.item(2).textContent
//                    val s = Saint(name, dob, dod, 0f)
//                    saints.add(s)
//                    Log.d("happy", "name: $name")
                }
            }
        } catch (e: Exception) {
        }
        adapter = SaintAdapter(this, R.layout.listviewitem, saints)
        list!!.setAdapter(adapter)
    }

    // Вызывается при создании контекстного меню
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo) {
        menuInflater.inflate(R.menu.context, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    // Вызывается при выборе элемента контекстного меню
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterContextMenuInfo?
        ///
        return super.onContextItemSelected(item)
    }

    // Вызывается при создании меню
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        ///
        return super.onCreateOptionsMenu(menu)
    }

    // Вызывается при выборе элемента меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        // Константы для передачи данных через Intent
        // Должны быть доступны "детальной" Activity
        const val SAINT_NAME = "SAINT_NAME"
        const val SAINT_ID = "SAINT_ID"
        const val SAINT_RATING = "SAINT_RATING"
        const val RATING_REQUEST = 777
    }
}
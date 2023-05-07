package com.example.adapterviewxpath

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity


class SaintDetail : AppCompatActivity() {
    private var mSaintWebView: WebView? = null
    private var mSaintRating: RatingBar? = null
    private val mSaintId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saint_detail)

        // Находим элементы управления
        mSaintWebView = findViewById<View>(R.id.saint_detail) as WebView
        mSaintWebView!!.webViewClient = WebViewClient()
        mSaintRating = findViewById<View>(R.id.rating) as RatingBar

        // Получаем Intent, с которым была запущена Activity
        val intent = intent
        if (intent != null) {
            // Получаем из Intent переданные параметры

            ///
            var saint: String? = null
            ///
            if (saint != null) {
                // Формируем URL для википедии
                saint = saint.replace(" ", "_")
                val url = "https://en.m.wikipedia.org/wiki/$saint"
                mSaintWebView!!.loadUrl(url)
            }

            // Вначале проверяем, есть ли такое значение
            if (intent.hasExtra(MainActivity.SAINT_RATING)) {
                ///
                ///
            }
            if (intent.hasExtra(MainActivity.SAINT_ID)) {
                ///
                ///
            }
        }
    }

    // По нажатию на кнопку "Back"
    override fun onBackPressed() {
        // Формируем Intent, который будет возвращен в вызвавшую нас Activity
        ///
        val intent: Intent? = null

        // Добавляем в Intent нужные параметры
        ///
        ///


        // Устанавливаем результат
        ///
        ///

        // Вызываем onBackPressed суперкласса, закрывая Activity
        super.onBackPressed()
    }
}
package com.clickonometrics.sample.app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebView
import pl.speednet.euvictrackersdk.EuvicMobileSDK
import pl.speednet.euvictrackersdk.utils.Utils

const val API_KEY = "zGvjBvroFc7onruVlmSoy3foBHLG4Upq"
const val USER_TYPE = "AAID"

class PreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        supportActionBar?.apply {
            title = "Preview"

            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    /**
     * Display ad in WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    override fun onStart() {
        super.onStart()

        val webView = findViewById<WebView>(R.id.web_view)
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.settings.javaScriptEnabled = true

        webView.run {
            val encodedAdvertisingId = Utils.encodeToBase64(EuvicMobileSDK.getCurrentUserId)

            val url = "https://static.clickonometrics.pl/previews/campaignsPreview.html?key=$API_KEY&user_id=$encodedAdvertisingId&user_type=$USER_TYPE"

            post {
                clearCache(true)
                loadUrl(url)
            }
        }
    }

}
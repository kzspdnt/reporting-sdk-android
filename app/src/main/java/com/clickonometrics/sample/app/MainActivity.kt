package com.clickonometrics.sample.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import pl.speednet.euvictrackersdk.EuvicMobileSDK
import pl.speednet.euvictrackersdk.events.CustomParams
import pl.speednet.euvictrackersdk.events.Product

private lateinit var locationPermissionLauncher: ActivityResultLauncher<String>

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * SDK configuration.
         */
        EuvicMobileSDK.configure(
            context = applicationContext,
            apiKey = "zGvjBvroFc7onruVlmSoy3foBHLG4Upq",
            url = "https://delivery.clickonometrics.pl/tracker=multi/track/multi/track.json",
            userId = null,
            currency = null,
            allowSensitiveData = true
        )

        locationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isLocationPermissionGranted ->
            Boolean
            /**
             * Result of location permission rationale dialog. In this case we do not care about result.
             */
        }

        checkLocationPermissionStatus()
    }


    /**
     * Button handling to send events
     */
    override fun onStart() {
        super.onStart()

        val homepageVisitedButton = findViewById<Button>(R.id.homepage_visited_button)
        val productBrowsedButton = findViewById<Button>(R.id.product_browsed_button)
        val productAddedButton = findViewById<Button>(R.id.product_added_button)
        val productRemovedButton = findViewById<Button>(R.id.product_removed_button)
        val categoryBrowsedButton = findViewById<Button>(R.id.category_browsed_button)
        val cartButton = findViewById<Button>(R.id.cart_button)
        val orderStartedButton = findViewById<Button>(R.id.order_started_button)
        val productsOrderedButton = findViewById<Button>(R.id.products_ordered_button)
        val openPreviewFragmentButton = findViewById<Button>(R.id.preview_button)
        val openAppSettingsButton = findViewById<Button>(R.id.open_app_settings_button)

        homepageVisitedButton.setOnClickListener {
            EuvicMobileSDK.homepageVisitedEvent(
                custom = getCustomData()
            )
        }

        productBrowsedButton.setOnClickListener {
            EuvicMobileSDK.productBrowsedEvent(
                product = Product("3", "9.99", "PLN", 1),
                custom = getCustomData()
            )
        }

        productAddedButton.setOnClickListener {
            EuvicMobileSDK.productAddedEvent(
                product = Product("3", "9.99", "PLN", 1),
                custom = getCustomData()
            )
        }

        productRemovedButton.setOnClickListener {
            EuvicMobileSDK.productRemovedEvent(
                product = Product("3", "9.99", "PLN", 1),
                custom = getCustomData()
            )
        }

        categoryBrowsedButton.setOnClickListener {
            EuvicMobileSDK.browsedCategoryEvent(
                name = "Kategoria testowa",
                products = listOf(
                    Product("1", "12.37", "PLN", 10),
                    Product("2", "13.32", "PLN", 2)
                ),
                custom = getCustomData()
            )
        }

        cartButton.setOnClickListener {
            EuvicMobileSDK.cartEvent(
                products = listOf(
                    Product("1", "12.37", "PLN", 10),
                    Product("2", "13.32", "PLN", 2)
                ),
                custom = getCustomData()
            )
        }

        orderStartedButton.setOnClickListener {
            EuvicMobileSDK.orderStartedEvent(
                custom = getCustomData()
            )
        }

        productsOrderedButton.setOnClickListener {
            EuvicMobileSDK.productsOrderedEvent(
                orderId = "12345",
                saleValue = "4,02",
                currency = "PLN",
                products = listOf(
                    Product("1", "12.37", "PLN", 10),
                    Product("2", "13.32", "PLN", 2)
                ),
                custom = getCustomData()
            )
        }

        openPreviewFragmentButton.setOnClickListener {
            startActivity(Intent(this, PreviewActivity::class.java))
        }

        openAppSettingsButton.setOnClickListener {
            this.startActivity(
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).also {
                    it.data = Uri.parse("package:${this.packageName}")
                }
            )
        }
    }

    private fun getCustomData(): (CustomParams.() -> Unit)? {
        val customDataCheckbox = findViewById<CheckBox>(R.id.customDataCheckbox)
        return if (customDataCheckbox.isChecked) {
            {
                param("test_text", "text_value")
                param("test_int", 1234)
                param("test_double", 0.123)
            }
        } else {
            null
        }
    }

    private fun checkLocationPermissionStatus() {
        when {
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                /**
                 * Location permission is already granted. Nothing to do.
                 */
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) -> {
                /**
                 * Location permission is denied.
                 * You should display rationale dialog for user to grant location permission.
                 */
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            else -> {
                /**
                 * Is invoked when the app is launched for the first time.
                 * You should display rationale dialog for user to grant location permission.
                 */
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }
}
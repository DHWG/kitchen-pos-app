package dhwg.com.wgpos

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import dhwg.com.wgpos.data.DHWGManagementAPI

class SelectProductActivity : Activity() {

    val resetTimer = ResetToMainscreenTimer(15, this)

    inner class CancelButtonListener : View.OnClickListener {

        override fun onClick(v: View?) {
            val intent = Intent(this@SelectProductActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = application as WGPoSApplication

        val intent = intent
        val buyerId = intent.getIntExtra("buyerId", -1)

        setContentView(R.layout.activity_select_product)

        // enter immersive fullscreen mode to hide all navigation bards
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE)

        val glay = findViewById(R.id.select_product_griddy) as GridLayout
        glay.columnCount = 5
        glay.rowCount = 2
        application.apiClient!!.getProducts { products ->
            for (product in products) {
                val button = createButton(product, buyerId)
                glay.addView(button)
            }
            // cancel button
            val cancelButton = Button(this)
            cancelButton.text = "Cancel"
            cancelButton.width = 200
            cancelButton.height = 200
            cancelButton.setOnClickListener(CancelButtonListener())
            cancelButton.background.setColorFilter(Color.rgb(255, 0, 50), PorterDuff.Mode.MULTIPLY)
            val layoutParams = GridLayout.LayoutParams()
            layoutParams.columnSpec = GridLayout.spec(4)
            layoutParams.rowSpec = GridLayout.spec(1)
            glay.addView(cancelButton, layoutParams)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            resetTimer.start()
        } else {
            resetTimer.cancel()
        }
    }

    inner class ButtonListener(val product: DHWGManagementAPI.Product, val buyerId: Int) : View.OnClickListener {

        override fun onClick(v: View?) {
            val intent = Intent(this@SelectProductActivity, SummaryActivity::class.java)
            intent.putExtra("buyerId", buyerId)
            Log.i("PURCHASE", "Buyer $buyerId, Product ${product.id}")
            (application as WGPoSApplication).apiClient!!.addPurchase(buyerId, product.id)
            startActivity(intent)
        }

    }

    private fun createButton(product: DHWGManagementAPI.Product, buyerId: Int): Button {
        val b = Button(this)
        b.text = "${product.name} (${(product.unitPrice * 100).toInt()}ct)"
        b.width = 200
        b.height = 200
        b.setOnClickListener(ButtonListener(product, buyerId))
        return b
    }

}

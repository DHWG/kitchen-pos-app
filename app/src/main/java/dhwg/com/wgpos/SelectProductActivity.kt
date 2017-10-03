package dhwg.com.wgpos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import dhwg.com.wgpos.data.DHWGManagementAPI

class SelectProductActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val buyerId = intent.getIntExtra("buyerId", -1)

        setContentView(R.layout.activity_select_product)

        val glay = findViewById(R.id.select_product_griddy) as GridLayout
        DHWGManagementAPI.getProducts { products ->
            for (product in products) {
                val button = createButton(product, buyerId)
                glay.addView(button)
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // enter immersive fullscreen mode to hide all navigation bards
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    inner class ButtonListener(val product: DHWGManagementAPI.Product, val buyerId: Int) : View.OnClickListener {

        override fun onClick(v: View?) {
            val intent = Intent(this@SelectProductActivity, MainActivity::class.java)
            Log.i("PURCHASE", "Buyer $buyerId, Product ${product.id}")
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

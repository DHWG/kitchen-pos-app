package dhwg.com.wgpos

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import dhwg.com.wgpos.data.Product
import dhwg.com.wgpos.data.Purchase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectProductActivity : LifecycleActivity() {

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

        val self = this
        application.productsRepository!!.get().observe(this, object: Observer<List<Product>> {
            override fun onChanged(products: List<Product>?) {
                products!!.forEach { product ->
                    val button = createButton(product, buyerId)
                    glay.addView(button)
                }

                // cancel button
                val cancelButton = Button(self)
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
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            resetTimer.start()
        } else {
            resetTimer.cancel()
        }
    }

    inner class ButtonListener(val product: Product, val buyerId: Int) : View.OnClickListener {

        override fun onClick(v: View?) {
            val intent = Intent(this@SelectProductActivity, SummaryActivity::class.java)
            intent.putExtra("buyerId", buyerId)
            val application = application as WGPoSApplication
            Log.i("PURCHASE", "Buyer $buyerId, Product ${product.id}")
            val purchase = Purchase(buyerId, product.id)
            application.wgMgmtService!!.createPurchase(purchase).enqueue(object: Callback<Purchase> {
                override fun onFailure(call: Call<Purchase>?, t: Throwable?) {
                    Log.e("PURCHASE", "Not able to make purchase", t)
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(call: Call<Purchase>?, response: Response<Purchase>?) {
                    startActivity(intent)
                }

            })
        }

    }

    private fun createButton(product: Product, buyerId: Int): Button {
        val b = Button(this)
        b.text = "${product.name} (${(product.unitPrice * 100).toInt()}ct)"
        b.width = 200
        b.height = 200
        b.setOnClickListener(ButtonListener(product, buyerId))
        return b
    }

}

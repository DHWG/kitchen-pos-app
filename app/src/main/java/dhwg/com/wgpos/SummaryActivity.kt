package dhwg.com.wgpos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import dhwg.com.wgpos.data.DHWGManagementAPI

class SummaryActivity : Activity() {

    inner class ButtonListener : View.OnClickListener {

        override fun onClick(v: View?) {
            val intent = Intent(this@SummaryActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        // enter immersive fullscreen mode to hide all navigation bards
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE)

        val button = findViewById(R.id.back_button) as Button
        button.setOnClickListener(ButtonListener())
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            val amountText = findViewById(R.id.amount_text_view) as TextView
            val buyerId = intent.getIntExtra("buyerId", -1)
            (application as WGPoSApplication).apiClient!!.getInhabitant(buyerId, { inhabitant ->
                amountText.text = "${inhabitant.balance}€"
            })
        }
    }

}

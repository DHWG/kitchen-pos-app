package dhwg.com.wgpos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import dhwg.com.wgpos.data.Inhabitant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SummaryActivity : Activity() {

    val resetTimer = ResetToMainscreenTimer(15, this)

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
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE)

        val button = findViewById(R.id.back_button) as Button
        button.setOnClickListener(ButtonListener())
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            val amountText = findViewById(R.id.amount_text_view) as TextView
            val application = application as WGPoSApplication
            val buyerId = intent.getIntExtra("buyerId", -1)
            application.wgMgmtService!!.getInhabitant(buyerId).enqueue(object: Callback<Inhabitant> {
                override fun onFailure(call: Call<Inhabitant>?, t: Throwable?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(call: Call<Inhabitant>?, response: Response<Inhabitant>?) {
                    amountText.setText("" + response!!.body()!!.balance)
                }
            })
            resetTimer.start()
        } else {
            resetTimer.cancel()
        }
    }

}

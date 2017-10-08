package dhwg.com.wgpos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import dhwg.com.wgpos.data.DHWGManagementAPI


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = application as WGPoSApplication

        setContentView(R.layout.activity_main)

        // enter immersive fullscreen mode to hide all navigation bards
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE)

        val glay = findViewById(R.id.griddy) as GridLayout

        glay.columnCount = 5
        glay.rowCount = 2
        application.apiClient!!.getInhabitants { inhabitants ->
            for (inhabitant in inhabitants) {
                val button = createButton(inhabitant)
                glay.addView(button)
            }
        }
    }

    inner class ButtonListener(val inhabitant: DHWGManagementAPI.Inhabitant) : View.OnClickListener {

        override fun onClick(v: View?) {
            val intent = Intent(this@MainActivity, SelectProductActivity::class.java)
            intent.putExtra("buyerId", inhabitant.id)
            startActivity(intent)
        }

    }

    private fun createButton(inhabitant: DHWGManagementAPI.Inhabitant): Button {
        val b = Button(this)
        b.text = inhabitant.username
        b.width = 200
        b.height = 200
        b.setOnClickListener(ButtonListener(inhabitant))
        return b
    }

}

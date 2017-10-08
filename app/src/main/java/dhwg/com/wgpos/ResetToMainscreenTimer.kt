package dhwg.com.wgpos

import android.app.Activity
import android.content.Intent
import android.os.CountDownTimer

/**
 * Goes back to the main screen after timeut seconds.
 */
class ResetToMainscreenTimer(timeout: Long, val sourceActivity: Activity) : CountDownTimer(timeout * 1000, 1000) {

    override fun onTick(millisUntilFinished: Long) {
        // don't do anything
    }

    override fun onFinish() {
        val intent = Intent(sourceActivity, MainActivity::class.java)
        sourceActivity.startActivity(intent)
    }

}
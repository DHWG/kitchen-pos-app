package dhwg.com.wgpos

import android.app.Application
import dhwg.com.wgpos.data.DHWGManagementAPI
import java.util.*

/**
 * Application wrapper to do global stuff like parsing credentials and instantiate
 * a API access object.
 */
class WGPoSApplication : Application() {

    var apiClient: DHWGManagementAPI? = null

    override fun onCreate() {
        super.onCreate()
        val stream = resources.openRawResource(R.raw.credentials)
        val props = Properties()
        props.load(stream)
        stream.close()
        val apiRoot = props.getProperty("apiRoot")
        val user = props.getProperty("user")
        val password = props.getProperty("password")
        apiClient = DHWGManagementAPI(apiRoot, user, password)
    }

}
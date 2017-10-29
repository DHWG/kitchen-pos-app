package dhwg.com.wgpos

import android.app.Application
import android.util.Base64
import dhwg.com.wgpos.data.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Application wrapper to do global stuff like parsing credentials and instantiate
 * a API access object.
 */
class WGPoSApplication : Application() {

    var wgMgmtService: WgManagementService? = null
    var inhabitantsRepository: InhabitantRepository? = null
    var productsRepository: ProductRepository? = null

    override fun onCreate() {
        super.onCreate()
        val stream = resources.openRawResource(R.raw.credentials)
        val props = Properties()
        props.load(stream)
        stream.close()
        val apiRoot = props.getProperty("apiRoot")
        val user = props.getProperty("user")
        val password = props.getProperty("password")

        val authHeader = "Basic ${Base64.encodeToString("$user:$password".toByteArray(), Base64.NO_WRAP)}"

        val httpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Accept", "application/json")
                            .addHeader("Authorization", authHeader)
                            .build()
                    chain.proceed(request)
                }
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(apiRoot + "/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        wgMgmtService = retrofit.create(WgManagementService::class.java)

        inhabitantsRepository = InhabitantRepository(wgMgmtService)
        inhabitantsRepository!!.update()
        productsRepository = ProductRepository(wgMgmtService)
        productsRepository!!.update()
    }

}
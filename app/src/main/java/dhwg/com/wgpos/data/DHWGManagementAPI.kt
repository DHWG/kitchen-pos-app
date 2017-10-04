package dhwg.com.wgpos.data

import android.net.Credentials
import android.util.Log
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result

class DHWGManagementAPI(val apiRoot: String,
                        private val user: String,
                        private val password: String) {

    data class Inhabitant(val id: Int, val username: String, val balance: Double)
    data class Product(val id: Int, val name: String, val unitPrice: Double)

    fun getInhabitants(onSuccess: (ArrayList<Inhabitant>) -> Unit) {
        (apiRoot + "inhabitants/")
                .httpGet()
                .authenticate(user, password)
                .responseJson { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            // TODO
                        }
                        is Result.Success -> {
                            val json = result.get().array()
                            val result = arrayListOf<Inhabitant>()
                            for (i in 0 until json.length()) {
                                val inhabitant = json.getJSONObject(i)
                                result.add(Inhabitant(inhabitant.getInt("id"),
                                                      inhabitant.getString("username"),
                                                      inhabitant.getDouble("balance")))
                            }
                            onSuccess(result)
                        }
                    }
                }
    }

    fun getInhabitant(id: Int, onSuccess: (Inhabitant) -> Unit) {
        (apiRoot + "inhabitants/$id/")
                .httpGet()
                .authenticate(user, password)
                .responseJson { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            Log.e("GET INHABITANT", result.error.toString())
                        }
                        is Result.Success -> {
                            val inhabitant = result.get().obj()
                            val result = Inhabitant(inhabitant.getInt("id"),
                                                    inhabitant.getString("username"),
                                                    inhabitant.getDouble("balance"))
                            onSuccess(result)
                        }
                    }
                }
    }

    fun getProducts(onSuccess: (ArrayList<Product>) -> Unit) {
        (apiRoot + "pos/products/")
                .httpGet()
                .authenticate(user, password)
                .responseJson { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            // TODO
                        }
                        is Result.Success -> {
                            val json = result.get().array()
                            val result = arrayListOf<Product>()
                            for (i in 0 until json.length()) {
                                val product = json.getJSONObject(i)
                                result.add(Product(product.getInt("id"),
                                                   product.getString("name"),
                                                   product.getDouble("unit_price")))
                            }
                            onSuccess(result)
                        }
                    }
                }
    }

    fun addPurchase(buyerId: Int, productId: Int) {
        (apiRoot + "pos/purchases/")
                .httpPost()
                .authenticate(user, password)
                .header(Pair("Content-Type", "application/json"))
                .body("{ \"buyer\" : $buyerId, \"product\" : $productId }")
                .responseJson { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            Log.e("PURCHASE", result.error.toString())
                            Log.e("PURCHASE", request.cUrlString())
                        }
                        is Result.Success -> {
                            Log.i("Made purchase", result.get().toString())
                        }
                    }
                }
    }

}
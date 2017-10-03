package dhwg.com.wgpos.data

import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

object DHWGManagementAPI {

    data class Inhabitant(val id: Int, val username: String)
    data class Product(val id: Int, val name: String, val unitPrice: Double)

    val API_ROOT = "https://dhwg-management.herokuapp.com/api/"
    val USER = "tablet"
    val PASSWORD = "12blablub42"

    fun getInhabitants(onSuccess: (ArrayList<Inhabitant>) -> Unit) {
        (API_ROOT + "inhabitants/")
                .httpGet()
                .authenticate(USER, PASSWORD)
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
                                                      inhabitant.getString("username")))
                            }
                            onSuccess(result)
                        }
                    }
                }
    }

    fun getProducts(onSuccess: (ArrayList<Product>) -> Unit) {
        (API_ROOT + "pos/products/")
                .httpGet()
                .authenticate(USER, PASSWORD)
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

}
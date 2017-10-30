package dhwg.com.wgpos.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Interface to the WG management system.
 */
public interface WgManagementService {

    @GET("inhabitants/")
    Call<List<Inhabitant>> getInhabitants();

    @GET("inhabitants/{id}")
    Call<Inhabitant> getInhabitant(@Path("id") int id);

    @GET("pos/products/")
    Call<List<Product>> getProducts();

    @POST("pos/purchases/")
    Call<Purchase> createPurchase(@Body Purchase purchase);

}

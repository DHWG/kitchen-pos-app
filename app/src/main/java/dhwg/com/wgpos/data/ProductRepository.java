package dhwg.com.wgpos.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository of products.
 */

public class ProductRepository {

    private WgManagementService mgmtService;
    private MutableLiveData<List<Product>> cache = new MutableLiveData<>();

    public ProductRepository(WgManagementService mgmtService) {
        this.mgmtService = mgmtService;
        cache.setValue(new ArrayList<Product>());
    }

    public void update() {
        mgmtService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                cache.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("PRODUCTS", "Could not get.", t);
            }
        });
    }

    public LiveData<List<Product>> get() {
        return cache;
    }

}

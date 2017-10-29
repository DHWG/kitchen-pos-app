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
 * Repository of inhabitants.
 */
public class InhabitantRepository {

    private WgManagementService mgmtService;
    private MutableLiveData<List<Inhabitant>> cache = new MutableLiveData<>();

    public InhabitantRepository(WgManagementService mgmtService) {
        this.mgmtService = mgmtService;
        cache.setValue(new ArrayList<Inhabitant>());
    }

    public void update() {
        mgmtService.getInhabitants().enqueue(new Callback<List<Inhabitant>>() {
            @Override
            public void onResponse(Call<List<Inhabitant>> call, Response<List<Inhabitant>> response) {
                cache.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Inhabitant>> call, Throwable t) {
                Log.e("INHABITANTS", "Could not get.", t);
            }
        });
    }

    public LiveData<List<Inhabitant>> get() {
        return cache;
    }

    public Inhabitant getById(int id) {
        for (Inhabitant inhabitant : cache.getValue()) {
            if (inhabitant.getId() == id) {
                return inhabitant;
            }
        }
        return null;
    }

}

package testapp.android.com.testapplication.remote;


import retrofit2.Call;
import retrofit2.http.GET;
import testapp.android.com.testapplication.model.Rows;
/**
 * API used to request get Json data
 * */
public interface NewsApi {
    @GET("/bins/m47pd")
    Call<Rows> getNews();
}

package testapp.android.com.testapplication.remote;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import testapp.android.com.testapplication.model.News;
import testapp.android.com.testapplication.model.Rows;

public class RemoteService {
    private static RemoteService sInstance;
    private static final Object LOCK = new Object();
    private static String BASE_URL = "https://api.myjson.com/";
    private NewsApi webservice;

    public synchronized static RemoteService getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new RemoteService();
                }
            }
        }
        return sInstance;
    }
    private RemoteService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .setLenient()
                .serializeNulls()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.webservice = retrofit.create(NewsApi.class);
    }

    public void getNews(final Callback<Rows> newsCallback) {
        Call newsCall = webservice.getNews();
        newsCall.enqueue(new Callback<Rows>() {
            @Override
            public void onResponse(Call<Rows> call, Response<Rows> response) {
                if(response.isSuccessful()){
                    newsCallback.onResponse(call, response);
                }
            }

            @Override
            public void onFailure(Call<Rows> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }


}
